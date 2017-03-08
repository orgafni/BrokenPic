package com.brokenpicinc.brokenpic;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mvc.imagepicker.ImagePicker;

public class MainActivity extends Activity {
    static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();

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

        Button backBtn = (Button) findViewById(R.id.NavigateBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    static void MoveToFragment(Fragment newFrag, boolean isNeedToSaveCurrFrag, boolean clearBackStack)
    {
        if (clearBackStack)
        {
            clearBackstack();
        }

        FragmentTransaction ftr = fragmentManager.beginTransaction();
        ftr.replace(R.id.mainContainer, newFrag);
        if (isNeedToSaveCurrFrag)
        {
            ftr.addToBackStack(newFrag.getClass().getName());
        }
        ftr.commit();
    }

    static void MoveToFragment(Fragment newFrag, boolean isNeedToSaveCurrFrag)
    {
        MoveToFragment(newFrag, isNeedToSaveCurrFrag, false);
    }

    public static void clearBackstack() {

        if (fragmentManager.getBackStackEntryCount() > 0 ){

            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(
                    0);
            fragmentManager.popBackStack(entry.getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.executePendingTransactions();
        }

    }
}
