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

import com.brokenpicinc.brokenpic.model.DrawGame;
import com.brokenpicinc.brokenpic.model.GuessGame;
import com.brokenpicinc.brokenpic.model.Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawItFragment extends Fragment {
    DrawGame gameToDraw;

    public DrawItFragment() {
        // Required empty public constructor
    }

    public void setChosenGame(DrawGame chosenGame)
    {
        this.gameToDraw= chosenGame;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_draw_it, container, false);

        final ImageView writerImageView = (ImageView)view.findViewById(R.id.writer_image);
        final TextView writerNameTv = (TextView) view.findViewById(R.id.writer_name_textview);
        final TextView wordToDrawTv = (TextView) view.findViewById(R.id.word_to_draw_textview);
        final ImageButton yourPicGuessImageBtn = (ImageButton) view.findViewById(R.id.your_pic_guess_imagebtn);

        final ImageButton sendDrawBtn = (ImageButton) view.findViewById(R.id.send_your_draw);

        // TODO: set real drawer profile photo
        writerImageView.setImageResource(R.mipmap.ic_launcher);
        writerNameTv.setText(gameToDraw.getPlayerName());
        // TODO: set real drawer draw
        wordToDrawTv.setText(gameToDraw.getWordToDraw());

        yourPicGuessImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: open camera, or option to upload an photo, or maybe a blank page to draw on.
            }
        });

        sendDrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: get the draw picture path.
                String drawPicPath = "/asad/asdasd/asdd.png";

                // TODO: verify that there is a draw.
                if (drawPicPath.isEmpty())
                {
                    // TODO: alert
                }
                else
                {
                    Model.getInstance().advanceGame(gameToDraw, drawPicPath);
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
