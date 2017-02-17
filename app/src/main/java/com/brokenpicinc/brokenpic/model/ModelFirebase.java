package com.brokenpicinc.brokenpic.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by orgaf_000 on 2/4/2017.
 */

public class ModelFirebase {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    final private String TAG = "ModelFirebase";
    FirebaseUser currentUser;
    final String storageURL = "gs://brokenpic-cff37.appspot.com";

//    public ModelFirebase()
//    {
//        mAuth = FirebaseAuth.getInstance();
//    }

    public interface GetMyTurnListener{
        void onSuccess(playerInGame res);
        void onFail();
    }

    public void registerUser(final String nickName, String email, String password, final Bitmap profilePhoto, final Model.RegisterUserListener listener)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                if (!task.isSuccessful()){
                    Log.d(TAG, "failed to register user");
                    listener.onFail(task.getException().getMessage());
                }
                else{

                    currentUser =  mAuth.getCurrentUser();
                    Log.d(TAG, "success get user: uid: " + currentUser.getUid()+ " displayName: " + currentUser.getDisplayName() + " email" + currentUser.getEmail());

                    String profilePath = "profilePhotos/" + currentUser.getUid() + ".jpg";
                    uploadImage(profilePhoto, profilePath);

                    Player pl = new Player(nickName, profilePath);
                    database.getReference("Players").child(currentUser.getUid()).setValue(pl);

                    listener.onSuccess();

                }
            }
        });
    }

    public void loginUser(String email, String password, final Model.LoginUserListener listener)
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                if (!task.isSuccessful()){
                    Log.d(TAG, "failed to sing in user");
                    listener.onFail(task.getException().getMessage());
                }
                else{

                    currentUser =  mAuth.getCurrentUser();
                    Log.d(TAG, "success get user: uid: " + currentUser.getUid()+ " displayName: " + currentUser.getDisplayName() + " email" + currentUser.getEmail());

                    listener.onSuccess();
                }
            }
        });
    }

    public void getAllPlayersAsync(final Model.GetAllPlayersListener listener){

        DatabaseReference myRef = database.getReference("Players");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Player> plList = new LinkedList<Player>();
                for (DataSnapshot plSnapshot : dataSnapshot.getChildren()) {
                    Player pl = plSnapshot.getValue(Player.class);
                    pl.setUniqueID(plSnapshot.getKey());
                    Log.d(TAG, pl.getName() + " - " + pl.getImage());
                    if (!pl.getUniqueID().equals(currentUser.getUid()))
                    {
                        plList.add(pl);
                    }
                }
                listener.onResult(plList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancel("Connection error");
            }
        });
    }

    public void getAllGamesToDrawAsync(final Model.GetAllGamesToDrawListener listener){
        Log.d(TAG, getCurrentUserID());
        DatabaseReference myRef = database.getReference("playerGames").child(getCurrentUserID()).child("pending");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> gameList = new LinkedList<>();
                for (DataSnapshot plSnapshot : dataSnapshot.getChildren()) {
                    if (plSnapshot.getValue(String.class).equals(Model.DrawType)) {
                        String gameID = plSnapshot.getKey();
                        gameList.add(gameID);
                    }
                }
                listener.onResult(gameList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onCancel("Connection error");
            }
        });
    }

    final private void getMyTurnToGuessByGameID(final String gameID, final GetMyTurnListener listener)
    {
        DatabaseReference myRef = database.getReference("Games").child(gameID).child("nextTurnIndex");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int nextTurnIndex = dataSnapshot.getValue(Integer.class);
                if (nextTurnIndex % 2 == 1)
                {
                    DatabaseReference wordRef = database.getReference("Games").child(gameID).child("playersInGame").child(Integer.toString(nextTurnIndex - 1));
                    wordRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            playerInGame myTurn = new playerInGame();
                            for (DataSnapshot plSnapshot : dataSnapshot.getChildren()) {
                                if (plSnapshot.getKey().equals("playerID"))
                                {
                                    myTurn.setPlayerID(plSnapshot.getValue(String.class));
                                }
                                else if(plSnapshot.getKey().equals("word"))
                                {
                                    myTurn.setWord(plSnapshot.getValue(String.class));
                                }
                            }
                            listener.onSuccess(myTurn);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void uploadImage(Bitmap image, String destPath)
    {
        // Create a storage reference from our app
        StorageReference profileStorageRef = storage.getReferenceFromUrl(storageURL).child(destPath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = profileStorageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d(TAG, "failed to upload photo");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }

    public void getImage(String url, final Model.GetImageListener listener){
        StorageReference httpsReference = storage.getReferenceFromUrl(storageURL).child(url);
        final long ONE_MEGABYTE = 1024 * 1024;
        httpsReference.getBytes(3* ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                listener.onSuccess(image);
                // Data for "images/island.jpg" is returns, use this as needed
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                Log.d("TAG",exception.getMessage());
                listener.onFail();
                // Handle any errors
            }
        });

    }

    public String addGame(Game newGame)
    {
        DatabaseReference myRef = database.getReference("Games").push();
        myRef.setValue(newGame);

        return myRef.getKey();
    }

    public String getCurrentUserID()
    {
        return currentUser.getUid();
    }

    public void addGameToPendingListOfPlayer(String gameID, String playerID, String GuessOrDraw)
    {
        database.getReference("playerGames").child(playerID).child("pending").child(gameID).setValue(GuessOrDraw);
    }

    public void getGameTurnIndex(String gameID, final Model.GetNumericResultListener listener)
    {
        DatabaseReference myRef = database.getReference("Games").child(gameID).child("nextTurnIndex");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int nextTurnIndex = dataSnapshot.getValue(Integer.class);
                listener.onSuccess(nextTurnIndex);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getPlayerInGameByIndex(String gameId, int playerIndexInGame, final ModelFirebase.GetMyTurnListener listener)
    {
        DatabaseReference myRef = database.getReference("Games").child(gameId).child("playersInGame").child(Integer.toString(playerIndexInGame));
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playerInGame plInGame = new playerInGame();
                for (DataSnapshot plSnapshot : dataSnapshot.getChildren()) {
                    if (plSnapshot.getKey().equals("playerID"))
                    {
                        plInGame.setPlayerID(plSnapshot.getValue(String.class));
                    }
                    else if(plSnapshot.getKey().equals("word"))
                    {
                        plInGame.setWord(plSnapshot.getValue(String.class));
                    }
                }
                listener.onSuccess(plInGame);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getPlayerDetailsByPlayerID(String playerID, final Model.GetPlayerListener listener){
        DatabaseReference myRef = database.getReference("Players").child(playerID);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Player pl = new Player();
                for (DataSnapshot plSnapshot : dataSnapshot.getChildren()) {
                    if (plSnapshot.getKey().equals("image"))
                    {
                        pl.setImage(plSnapshot.getValue(String.class));
                    }
                    else if(plSnapshot.getKey().equals("name"))
                    {
                        pl.setName(plSnapshot.getValue(String.class));
                    }
                }
                listener.onResult(pl);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void SetDrawToGame(String GameID, int CurrTurnIndex, Bitmap draw)
    {
        String imagePath = "games/" + GameID + "/" + getCurrentUserID() + ".jpg";
        uploadImage(draw, imagePath);
        database.getReference("Games").child(GameID).child("playersInGame").child(Integer.toString(CurrTurnIndex)).child("picturePath").setValue(imagePath);
    }

    void removePendingGame(String GameID)
    {
        database.getReference("playerGames").child(getCurrentUserID()).child("pending").child(GameID).removeValue();
    }

    void advancedGameTurnIndex(String GameID, int NextTurnIndex)
    {
        database.getReference("Games").child(GameID).child("nextTurnIndex").setValue(Integer.toString(NextTurnIndex));
    }
}
