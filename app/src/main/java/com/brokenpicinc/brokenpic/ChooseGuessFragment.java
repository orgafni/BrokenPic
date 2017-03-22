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

import com.brokenpicinc.brokenpic.model.DrawGame;
import com.brokenpicinc.brokenpic.model.Model;
import com.brokenpicinc.brokenpic.utils.DialogInterrupter;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseGuessFragment extends Fragment {
    List<String> gamesToDrawList;

    public ChooseGuessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_game, container, false);

        gamesToDrawList = new LinkedList<>();

        final ListView list = (ListView) view.findViewById(R.id.GamesToGuessListView);
        final GuessesAdapter adapter = new GuessesAdapter(getActivity());
        list.setAdapter(adapter);

        Model.getInstance().getGamesToDraw(new Model.GetGamesListener() {
            @Override
            public void onResult(List<String> games) {
                gamesToDrawList.addAll(games);
                adapter.refershData();
            }

            @Override
            public void onCancel(String msg) {
                DialogInterrupter.showNeturalDialog(msg, getActivity());
            }
        });

        return view;
    }

    class GuessesAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public GuessesAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return gamesToDrawList.size();
        }

        @Override
        public Object getItem(int i) {
            return gamesToDrawList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.choose_guess_row, null);
            }

            final LinearLayout rowLayout = (LinearLayout) view.findViewById(R.id.chooseGuessLayout);
            final TextView indexTv = (TextView) view.findViewById(R.id.row_guess_index);
            final ImageView participentImageView = (ImageView) view.findViewById(R.id.row_guess_participante_image);
            final TextView phraseTv = (TextView) view.findViewById(R.id.row_guess_phrase);
            final ImageButton playBtn = (ImageButton) view.findViewById(R.id.row_guess_play);

            final DrawGame game = new DrawGame();
            final String gameId = gamesToDrawList.get(i);
            Model.getInstance().getDrawGameDetails(gameId, new Model.GetDrawGameDetailsListener() {
                @Override
                public void onResult(Bitmap playerProfilePhoto, String playerName, String wordToDraw, String gameId, int currTurnIndex) {
                    indexTv.setText(Integer.toString(i + 1));
                    participentImageView.setImageBitmap(playerProfilePhoto);
                    phraseTv.setText(wordToDraw);

                    rowLayout.setVisibility(View.VISIBLE);
                    game.setWordToDraw(wordToDraw);
                    game.setPlayerName(playerName);
                    game.setPlayerProfile(playerProfilePhoto);
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
                    DrawItFragment drawItFragment = new DrawItFragment();
                    drawItFragment.setChosenGame(game);
                    MainActivity.MoveToFragment(drawItFragment, true, ChooseGuessFragment.class.getName());
                }
            });

            Log.d("TAG", "presenting guess row: " + i);
            return view;
        }

        public final void refershData()
        {
            if (isVisible())
            {
                final ListView list = (ListView) getActivity().findViewById(R.id.GamesToGuessListView);
                ((BaseAdapter)list.getAdapter()).notifyDataSetChanged();
            }
        }


    }
}
