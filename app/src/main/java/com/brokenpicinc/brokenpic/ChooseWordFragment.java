package com.brokenpicinc.brokenpic;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.brokenpicinc.brokenpic.model.Model;
import com.brokenpicinc.brokenpic.model.Player;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseWordFragment extends Fragment {
    List<Player> chosenPlayers;

    public ChooseWordFragment() {
        // Required empty public constructor

        chosenPlayers = new LinkedList<Player>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_choose_word, container, false);
        final EditText chosenWordEditText = (EditText) view.findViewById(R.id.chosen_word_edittext);

        ImageButton sendBtn = (ImageButton) view.findViewById(R.id.send_word_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chosenWord = chosenWordEditText.getText().toString();
                if (chosenWord.isEmpty())
                {
                    // TODO: alert
                }
                else
                {
                    Model.getInstance().createNewGame(chosenWord, chosenPlayers);

                    final MenuFragment menuFragment = new MenuFragment();
                    FragmentTransaction ftr = getFragmentManager().beginTransaction();
                    ftr.replace(R.id.mainContainer,menuFragment);
                    ftr.commit();
                }
            }
        });

        return view;
    }

    public void SetChosenPlayers(List<Player> chosenPlayerList)
    {
        chosenPlayers.addAll(chosenPlayerList);
    }

}
