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

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        ImageButton loginContinueBtn = (ImageButton) view.findViewById(R.id.login_continue_btn);
        loginContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText)view.findViewById(R.id.login_email_edittext)).getText().toString();
                String password = ((EditText)view.findViewById(R.id.login_password_edittext)).getText().toString();

                // TODO: verify that email and password correct (use Model or Firebase)

                final MenuFragment menuFragment = new MenuFragment();
                FragmentTransaction ftr = getFragmentManager().beginTransaction();
                ftr.replace(R.id.mainContainer,menuFragment);
                ftr.commit();
            }
        });

        TextView signupLink = (TextView) view.findViewById(R.id.login_singup_link);
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SignupFragment signupFragment = new SignupFragment();
                FragmentTransaction ftr = getFragmentManager().beginTransaction();
                ftr.replace(R.id.mainContainer,signupFragment);
                ftr.commit();
            }
        });



        return view;
    }

}
