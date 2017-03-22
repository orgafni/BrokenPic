package com.brokenpicinc.brokenpic;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.brokenpicinc.brokenpic.model.Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        ImageButton startNewGameBtn = (ImageButton) view.findViewById(R.id.start_new_game_btn);
        startNewGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StartNewGameFragment startNewGameFragment = new StartNewGameFragment();
                MainActivity.MoveToFragment(startNewGameFragment, true, MenuFragment.class.getName());
            }
        });

        ImageButton takeYourTurnBtn = (ImageButton) view.findViewById(R.id.take_your_turn_btn);
        takeYourTurnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TakeTurnFragment takeTurnFragment = new TakeTurnFragment();
                MainActivity.MoveToFragment(takeTurnFragment, true, MenuFragment.class.getName());
            }
        });

        ImageButton ShowFinishedGamesBtn = (ImageButton) view.findViewById(R.id.show_finished_games);
        ShowFinishedGamesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ChooseFinishedGameFragment chooseFinishedGame = new ChooseFinishedGameFragment();
                MainActivity.MoveToFragment(chooseFinishedGame, true, MenuFragment.class.getName());
            }
        });

        Model.getInstance().LoadData();

        return view;
    }

}
