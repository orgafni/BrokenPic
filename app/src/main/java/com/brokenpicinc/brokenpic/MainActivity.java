package com.brokenpicinc.brokenpic;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.add(R.id.mainContainer,loginFragment);
        ftr.commit();
    }
}
