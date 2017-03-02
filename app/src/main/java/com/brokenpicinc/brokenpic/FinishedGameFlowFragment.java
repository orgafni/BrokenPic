package com.brokenpicinc.brokenpic;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brokenpicinc.brokenpic.model.FinishedGame;
import com.brokenpicinc.brokenpic.model.Model;
import com.brokenpicinc.brokenpic.model.ModelFirebase;
import com.brokenpicinc.brokenpic.model.PlayerInFinishedGame;

import org.w3c.dom.Text;


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
        game = chosenGame;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finished_game_flow, container, false);

        TextView finishGameNumberTextView = (TextView) view.findViewById(R.id.finish_game_number_textview);
        finishGameNumberTextView.setText(Integer.toString(game.getIndex()));
        LinearLayout finishGameContainerLayout = (LinearLayout) view.findViewById(R.id.finished_game_flow_container_layout);
        float totalContainerWeight = 1000;
        finishGameContainerLayout.setWeightSum(totalContainerWeight);

        float realRowUnits = 3;
        float arrowRowUnits = 1;

        int playersAmount = game.getPlayers().size();

        float amountOfUnits = (realRowUnits*playersAmount) + (arrowRowUnits*(playersAmount-1));
        float unitSize = totalContainerWeight/amountOfUnits;

        float weightForRealRow = realRowUnits * unitSize;
        float weightForArrowRow = arrowRowUnits * unitSize;

        for (int i = 0; i < playersAmount; i++)
        {

            PlayerInFinishedGame playerInFinishedGame = (PlayerInFinishedGame) game.getPlayers().values().toArray()[i];
            LinearLayout realRowLayout = new LinearLayout(getActivity());
            realRowLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0 , weightForRealRow));
            realRowLayout.setOrientation(LinearLayout.HORIZONTAL);
            realRowLayout.setWeightSum(100);
            ImageView profilePhotoImageView = new ImageView(getActivity());
            profilePhotoImageView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 50));
            profilePhotoImageView.setImageBitmap(playerInFinishedGame.getPlayerProfile());

            realRowLayout.addView(profilePhotoImageView);

            final View wordOrPictureView;

            // If the index is even, then this is word, otherwise it is picture
            if (i%2 == 0)
            {
                wordOrPictureView = new TextView(getActivity());
                ((TextView)wordOrPictureView).setText(playerInFinishedGame.getPlInGame().getWord());
                ((TextView)wordOrPictureView).setTextColor(getResources().getColor(R.color.colorWhite));
                ((TextView)wordOrPictureView).setGravity(Gravity.CENTER);
                ((TextView)wordOrPictureView).setTextSize(20);
            }
            else
            {
                wordOrPictureView = new ImageView(getActivity());

                Model.getInstance().getImageByPath(playerInFinishedGame.getPlInGame().getPicturePath(), new Model.GetImageListener() {
                    @Override
                    public void onSuccess(Bitmap image) {
                        ((ImageView)wordOrPictureView).setImageBitmap(image);
                    }
                    @Override
                    public void onFail() {

                    }
                });
            }
            wordOrPictureView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 50));

            realRowLayout.addView(wordOrPictureView);
            finishGameContainerLayout.addView(realRowLayout);

            // In this case we need another arrows row
            if (i != playersAmount - 1)
            {
                LinearLayout arrowRowLayout = new LinearLayout(getActivity());
                arrowRowLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0 , weightForArrowRow));
                arrowRowLayout.setOrientation(LinearLayout.HORIZONTAL);
                arrowRowLayout.setWeightSum(100);
                ImageView firstArrowImageView = new ImageView(getActivity());
                firstArrowImageView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 50));
                firstArrowImageView.setImageResource(R.drawable.down_arrow);

                ImageView secondArrowImageView = new ImageView(getActivity());
                secondArrowImageView.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 50));
                secondArrowImageView.setImageResource(R.drawable.down_arrow);

                arrowRowLayout.addView(firstArrowImageView);
                arrowRowLayout.addView(secondArrowImageView);

                finishGameContainerLayout.addView(arrowRowLayout);
            }
        }
        finishGameContainerLayout.setVisibility(View.VISIBLE);

        return view;
    }

}
