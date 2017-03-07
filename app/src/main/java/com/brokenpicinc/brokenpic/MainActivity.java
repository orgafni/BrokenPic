package com.brokenpicinc.brokenpic;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mvc.imagepicker.ImagePicker;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImagePicker.setMinQuality(600, 600);

        final LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.add(R.id.mainContainer,loginFragment);
        ftr.commit();


        Button menuBtn = (Button) findViewById(R.id.menuBth);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MenuFragment menuFragment = new MenuFragment();
                FragmentTransaction ftr = getFragmentManager().beginTransaction();
                ftr.add(R.id.mainContainer,menuFragment);
                ftr.commit();
            }
        });
    }
}
