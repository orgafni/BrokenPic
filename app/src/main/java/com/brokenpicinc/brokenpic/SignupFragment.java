package com.brokenpicinc.brokenpic;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.brokenpicinc.brokenpic.model.Model;
import com.brokenpicinc.brokenpic.model.ModelFirebase;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_signup, container, false);

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
//                String profilePhoto = ((ImageButton)view.findViewById(R.id.register_profile_photo_imagebtn)).getResour
                // TODO: verify that email is not exists on Model (or firebase), verify passwords are equal, and register user Model (or firebase)
                Model.getInstance().registerNewUser(nickname, email, password, new ModelFirebase.RegisterUserListener() {
                    @Override
                    public void onSuccess() {
                        final MenuFragment menuFragment = new MenuFragment();
                        FragmentTransaction ftr = getFragmentManager().beginTransaction();
                        ftr.replace(R.id.mainContainer,menuFragment);
                        ftr.commit();
                    }

                    @Override
                    public void onFail() {
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

}
