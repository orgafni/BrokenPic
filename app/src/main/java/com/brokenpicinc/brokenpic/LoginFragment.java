package com.brokenpicinc.brokenpic;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.brokenpicinc.brokenpic.model.Model;
import com.brokenpicinc.brokenpic.model.ModelFirebase;
import com.brokenpicinc.brokenpic.utils.DialogInterrupter;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        ImageButton loginContinueBtn = (ImageButton) view.findViewById(R.id.login_continue_btn);
        loginContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText emailEditText = ((EditText) view.findViewById(R.id.login_email_edittext));
                final EditText passwordEditText = ((EditText) view.findViewById(R.id.login_password_edittext));
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // TODO: verify that email and password correct (use Model or Firebase)
                Model.getInstance().loginNewUser(email, password, new Model.LoginUserListener() {
                    @Override
                    public void onSuccess() {
                        final MenuFragment menuFragment = new MenuFragment();
                        MainActivity.MoveToFragment(menuFragment, false, true);
                    }

                    @Override
                    public void onFail(String msg) {
                        DialogInterrupter.showNeturalDialog(msg, getActivity());
                        emailEditText.setText("");
                        passwordEditText.setText("");
                    }

                });
            }
        });

        TextView signupLink = (TextView) view.findViewById(R.id.login_singup_link);
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SignupFragment signupFragment = new SignupFragment();
                MainActivity.MoveToFragment(signupFragment, true, false);
            }
        });



        return view;
    }

}
