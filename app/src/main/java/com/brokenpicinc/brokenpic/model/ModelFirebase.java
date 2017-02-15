package com.brokenpicinc.brokenpic.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

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

    public interface RegisterUserListener{
        public void onSuccess();
        public void onFail();
    }

    public interface LoginUserListener{
        public void onSuccess();
        public void onFail();
    }

    public void registerUser(final String nickName, String email, String password, final Bitmap profilePhoto, final RegisterUserListener listener)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                if (!task.isSuccessful()){
                    Log.d(TAG, "failed to register user");
                    listener.onFail();
                }
                else{

                    currentUser =  mAuth.getCurrentUser();
                    Log.d(TAG, "success get user: uid: " + currentUser.getUid()+ " displayName: " + currentUser.getDisplayName() + " email" + currentUser.getEmail());

                    String profilePath = "profilePhotos/" + currentUser.getUid() + ".jpg";
                    // Create a storage reference from our app
                    StorageReference profileStorageRef = storage.getReferenceFromUrl(storageURL).child("profilePhotos/" + currentUser.getUid() + ".jpg");

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    profilePhoto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = profileStorageRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Log.d(TAG, "failed to upload photo");
                            listener.onFail();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    });

                    database.getReference("Players").child(currentUser.getUid()).child("name").setValue(nickName);
                    database.getReference("Players").child(currentUser.getUid()).child("image").setValue(profilePath);

                    database.getReference("playerGames").child(currentUser.getUid()).child("finished");
                    listener.onSuccess();

                }
            }
        });
    }

    public void loginUser(String email, String password, final LoginUserListener listener)
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                if (!task.isSuccessful()){
                    Log.d(TAG, "failed to sing in user");
                    listener.onFail();
                }
                else{

                    currentUser =  mAuth.getCurrentUser();
                    Log.d(TAG, "success get user: uid: " + currentUser.getUid()+ " displayName: " + currentUser.getDisplayName() + " email" + currentUser.getEmail());

                    listener.onSuccess();
                }
            }
        });
    }


}
