package com.brokenpicinc.brokenpic;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class TakeTurnFragment extends Fragment {


    public TakeTurnFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_take_turn, container, false);

        ImageButton guessItBtn = (ImageButton) view.findViewById(R.id.guess_it_btn);
        guessItBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChooseDrawFragment chooseDrawFragment = new ChooseDrawFragment();
                MainActivity.MoveToFragment(chooseDrawFragment, true, false);
            }
        });

        ImageButton drawItBtn = (ImageButton) view.findViewById(R.id.draw_it_btn);
        drawItBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChooseGuessFragment chooseGuessFragment = new ChooseGuessFragment();
                MainActivity.MoveToFragment(chooseGuessFragment, true, false);
            }
        });

        return view;
    }

}
