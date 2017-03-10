package com.brokenpicinc.brokenpic;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.brokenpicinc.brokenpic.model.GuessGame;
import com.brokenpicinc.brokenpic.model.Model;
import com.brokenpicinc.brokenpic.utils.DialogInterrupter;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseDrawFragment extends Fragment {
    List<String> gamesToGuessList;

    public ChooseDrawFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_game, container, false);

        gamesToGuessList = new LinkedList<String>();

        final ListView list = (ListView) view.findViewById(R.id.GamesToGuessListView);
        final DrawsAdapter adapter = new DrawsAdapter(getActivity());
        list.setAdapter(adapter);

        Model.getInstance().getGamesToGuess(new Model.GetGamesListener() {
            @Override
            public void onResult(List<String> games) {
                gamesToGuessList.addAll(games);
                adapter.refershData();
            }

            @Override
            public void onCancel(String msg) {
                DialogInterrupter.showNeturalDialog(msg, getActivity());
            }
        });

        return view;
    }

    class DrawsAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public DrawsAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return gamesToGuessList.size();
        }

        @Override
        public Object getItem(int i) {
            return gamesToGuessList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.choose_draw_row, null);
            }

            final LinearLayout rowLayout = (LinearLayout) view.findViewById(R.id.chooseDrawLayout);
            final TextView indexTv = (TextView) view.findViewById(R.id.row_draw_index);
            final ImageView participentImageView = (ImageView)view.findViewById(R.id.row_draw_participante_image);
            final ImageView imageView = (ImageView)view.findViewById(R.id.row_draw_image);
            final ImageButton playBtn = (ImageButton) view.findViewById(R.id.row_draw_play);


            final GuessGame game = new GuessGame();
            String gameID = gamesToGuessList.get(i);

            Model.getInstance().getGuessGameDetails(gameID, new Model.GetGuessGameDetailsListener()
            {
                @Override
                public void onResult(Bitmap playerProfilePhoto, String playerName, Bitmap pictureToGuess, String gameId, int currTurnIndex) {
                indexTv.setText(Integer.toString(i + 1));
                participentImageView.setImageBitmap(playerProfilePhoto);
                imageView.setImageBitmap(pictureToGuess);

                rowLayout.setVisibility(View.VISIBLE);
                game.setPictureToGuess(pictureToGuess);
                game.setPlayerName(playerName);
                game.setPlayerProfilePhoto(playerProfilePhoto);
                game.setGameID(gameId);
                game.setCurrTurnIndex(currTurnIndex);
            }

                @Override
                public void onFail(String msg) {
                DialogInterrupter.showNeturalDialog(msg, getActivity());
            }
            });

            playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GuessItFragment guessItFragment = new GuessItFragment();
                    guessItFragment.setChosenGame(game);
                    MainActivity.MoveToFragment(guessItFragment, true);
                }
            });

            Log.d("TAG", "presenting draw row: " + i);
            return view;
        }

        public final void refershData()
        {
            if (isVisible()) {
                final ListView list = (ListView) getActivity().findViewById(R.id.GamesToGuessListView);
                ((BaseAdapter) list.getAdapter()).notifyDataSetChanged();
            }
        }

    }

}
