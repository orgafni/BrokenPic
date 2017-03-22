package com.brokenpicinc.brokenpic;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.brokenpicinc.brokenpic.model.Model;
import com.brokenpicinc.brokenpic.model.ModelFirebase;
import com.brokenpicinc.brokenpic.utils.DialogInterrupter;
import com.mvc.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {
    final int TAKE_PICTURE = 0;
    final int CHOOSE_FROM_GALLERY = 1;
    ImageButton profileImageBtn;
    final boolean isProfileLoaded = false;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_signup, container, false);

        profileImageBtn = (ImageButton)view.findViewById(R.id.register_profile_photo_imagebtn);
        profileImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ImagePicker.pickImage(getActivity(), "choose your image bro!");
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(takePicture, TAKE_PICTURE);//zero can be replaced with any action code

//                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(pickIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {takePicture});

                startActivityForResult(chooserIntent, CHOOSE_FROM_GALLERY);
            }
        });

        ImageButton signupContinueBtn = (ImageButton)view.findViewById(R.id.register_continue_btn);
        signupContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nicknameEditText = ((EditText)view.findViewById(R.id.register_nickname_edittext));
                final EditText emailEditText = ((EditText)view.findViewById(R.id.register_email_edittext));
                final EditText passwordEditText = ((EditText)view.findViewById(R.id.register_password_edittext));
                final EditText confirmPasswordEditText = ((EditText)view.findViewById(R.id.register_confirmPass_edittext));

                final String nickname = nicknameEditText.getText().toString();
                final String email = emailEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                final String confirmPassword = confirmPasswordEditText.getText().toString();
                ImageButton profilePhotoBtn = ((ImageButton)view.findViewById(R.id.register_profile_photo_imagebtn));
                Bitmap profilePhoto = ((BitmapDrawable)profilePhotoBtn.getDrawable()).getBitmap();
                // TODO: verify that email is not exists on Model (or firebase), verify passwords are equal, and register user Model (or firebase)
                Model.getInstance().registerNewUser(nickname, email, password, confirmPassword, profilePhoto, new Model.RegisterUserListener() {
                    @Override
                    public void onSuccess() {
                        final MenuFragment menuFragment = new MenuFragment();
                        MainActivity.MoveToFragment(menuFragment, false, true, SignupFragment.class.getName());
                    }

                    @Override
                    public void onFail(String msg) {
                        DialogInterrupter.showNeturalDialog(msg, getActivity());
                        nicknameEditText.setText("");
                        emailEditText.setText("");
                        passwordEditText.setText("");
                        confirmPasswordEditText.setText("");

                    }
                });
            }
        });

        return view;
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Bitmap gotImage = ImagePicker.getImageFromResult(getActivity(), requestCode, resultCode, data);
//        profileImageBtn.setImageBitmap(gotImage);
//
////        Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
//        // TODO do something with the bitmap
//    }


    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case TAKE_PICTURE:
                if(resultCode == Activity.RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
//                    Log.d("TAG", "selectedImage " + selectedImage.getPath());
                    profileImageBtn.setImageBitmap(photo);

                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(getActivity().getApplicationContext(), photo);
                    Log.d("TAG", "tempUri " + tempUri.getPath());
                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(tempUri));

                }

                break;
            case CHOOSE_FROM_GALLERY:
                if(resultCode == Activity.RESULT_OK){
                    if (imageReturnedIntent.getExtras() != null) {
                        Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
//                    Log.d("TAG", "selectedImage " + selectedImage.getPath());
                        profileImageBtn.setImageBitmap(photo);
                    }
                    else
                    {
                        Uri selectedImage = imageReturnedIntent.getData();
                        profileImageBtn.setImageURI(selectedImage);
                    }

                }
                break;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

}
