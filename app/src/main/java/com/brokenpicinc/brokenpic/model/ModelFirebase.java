package com.brokenpicinc.brokenpic.model;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by orgaf_000 on 2/4/2017.
 */

public class ModelFirebase {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    final private String TAG = "ModelFirebase";
    FirebaseUser currentUser;

//    public ModelFirebase()
//    {
//        mAuth = FirebaseAuth.getInstance();
//    }

    public interface RegisterUserListener{
        public void onSuccess();
        public void onFail();
    }

    public void registerUser(final String nickName, String email, String password, final RegisterUserListener listener)
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

                    database.getReference("Players").child(currentUser.getUid()).child("image").setValue("None so far");
                    database.getReference("Players").child(currentUser.getUid()).child("name").setValue(nickName);

                    database.getReference("playerGames").child(currentUser.getUid()).child("finished");
                    listener.onSuccess();

                }
            }
        });

//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Log.d(TAG, "failed to register user");
////                            Toast.makeText(EmailPasswordActivity.this, R.string.auth_failed,
////                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                        // [START_EXCLUDE]
//                        Log.d(TAG, "asdasd");
////                        hideProgressDialog();
//                        // [END_EXCLUDE]
//                    }
//                });

    }
}
