package com.brokenpicinc.brokenpic;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
        Log.d("stack", "onBackPressed before: " + fragmentManager.getBackStackEntryCount());

        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

        Log.d("stack", "onBackPressed before: " + fragmentManager.getBackStackEntryCount());

    }

    static void MoveToFragment(Fragment newFrag, boolean isNeedToSaveCurrFrag, boolean clearBackStack)
    {
        Log.d("stack", "MoveToFragment before: " + fragmentManager.getBackStackEntryCount());
        if (clearBackStack)
        {
            clearBackstack();
        }

        FragmentTransaction ftr = fragmentManager.beginTransaction();
        ftr.setCustomAnimations(R.animator.enter_from_left, R.animator.exit_to_left, R.animator.enter_from_right, R.animator.exit_to_right);
        ftr.replace(R.id.mainContainer, newFrag);
        if (isNeedToSaveCurrFrag)
        {
            Log.d("stack", "MoveToFragment saving: " + newFrag.getClass().getName());
            ftr.addToBackStack(newFrag.getClass().getName());
        }
        ftr.commit();

        Log.d("stack", "MoveToFragment after: " + fragmentManager.getBackStackEntryCount());

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
