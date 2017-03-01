package com.brokenpicinc.brokenpic;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brokenpicinc.brokenpic.model.FinishedGame;


/**
 * A simple {@link Fragment} subclass.
 */
public class FinishedGameFlowFragment extends Fragment {
    FinishedGame game;

    public FinishedGameFlowFragment() {
        // Required empty public constructor
    }

    void setChosenGame(FinishedGame chosenGame)
    {
        game = chosenGame
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finished_game_flow, container, false);

        
        return view;
    }

}
