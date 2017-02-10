package com.brokenpicinc.brokenpic;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.brokenpicinc.brokenpic.model.GuessGame;
import com.brokenpicinc.brokenpic.model.Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class GuessItFragment extends Fragment {
    GuessGame gameToGuess;

    public GuessItFragment() {
        // Required empty public constructor
    }

    public void setChosenGame(GuessGame chosenGame)
    {
        this.gameToGuess = chosenGame;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guess_it, container, false);

        final ImageView drawerImageView = (ImageView)view.findViewById(R.id.drawer_image);
        final TextView drawerNameTv = (TextView) view.findViewById(R.id.drawer_name_textview);
        final ImageView ImageToGuessView = (ImageView)view.findViewById(R.id.pic_to_guess);
        final EditText yourGuessEditText = (EditText) view.findViewById(R.id.your_guess_edittext);
        final ImageButton sendGuessBtn = (ImageButton) view.findViewById(R.id.send_your_guess);

        // TODO: set real drawer profile photo
        drawerImageView.setImageResource(R.mipmap.ic_launcher);
        drawerNameTv.setText(gameToGuess.getDrawerName());
        // TODO: set real drawer draw
        ImageToGuessView.setImageResource(R.mipmap.ic_launcher);

        sendGuessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guessWord = yourGuessEditText.getText().toString();
                if (guessWord.isEmpty())
                {
                    // TODO: alert
                }
                else
                {
                    Model.getInstance().advanceGame(gameToGuess, guessWord);


                    final MenuFragment menuFragment = new MenuFragment();
                    FragmentTransaction ftr = getFragmentManager().beginTransaction();
                    ftr.replace(R.id.mainContainer,menuFragment);
                    ftr.commit();
                }
            }
        });
        return view;
    }

}
