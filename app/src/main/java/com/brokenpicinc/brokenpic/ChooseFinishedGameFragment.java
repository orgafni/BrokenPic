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

import com.brokenpicinc.brokenpic.model.FinishedGame;
import com.brokenpicinc.brokenpic.model.Model;
import com.brokenpicinc.brokenpic.model.playerInGame;
import com.brokenpicinc.brokenpic.utils.DialogInterrupter;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseFinishedGameFragment extends Fragment {
    List<String> finishedGamesList;

    public ChooseFinishedGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_choose_finished_game, container, false);

        finishedGamesList = new LinkedList<>();

        final ListView list = (ListView) view.findViewById(R.id.ChooseFinishedGameListView);
        final FinishedGamesAdapter adapter = new FinishedGamesAdapter(getActivity());
        list.setAdapter(adapter);

        Model.getInstance().getAllFinishedGames(new Model.GetGamesListener() {
            @Override
            public void onResult(List<String> games) {
                finishedGamesList.addAll(games);
                adapter.refershData();
            }

            @Override
            public void onCancel(String msg) {
                DialogInterrupter.showNeturalDialog(msg, getActivity());
            }
        });

        return view;
    }

    class FinishedGamesAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private Context context;

        public FinishedGamesAdapter(Context myContext) {
            layoutInflater = LayoutInflater.from(myContext);
            context = myContext;
        }

        @Override
        public int getCount() {
            return finishedGamesList.size();
        }

        @Override
        public Object getItem(int i) {
            return finishedGamesList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.finished_game_row, null);
            }

            final TextView finishedTimeTextview = (TextView) view.findViewById(R.id.row_game_finished_time);
            final LinearLayout chooseFinishedGameLayout = (LinearLayout) view.findViewById(R.id.chooseFinishedGameLayout);
            final LinearLayout participentsLayout = (LinearLayout) view.findViewById(R.id.finished_game_row_participents);
            final TextView finishedGameStatusTextview = (TextView) view.findViewById(R.id.finishedGameStatus);

            final FinishedGame game = new FinishedGame();
            final String gameId = finishedGamesList.get(i);
            game.setIndex(i + 1);
            Model.getInstance().getFinishedGameDetails(gameId, new Model.GetFinishedGameDetailsListener() {
                @Override
                public void onResult(String gameTime, final List<playerInGame> participants, String startWord, boolean isVictory) {
                    game.setFinishTime(gameTime);
                    game.setVictory(isVictory);

                    finishedTimeTextview.setText(gameTime);
                    finishedGameStatusTextview.setText(startWord);

                    if (isVictory)
                    {
                        finishedGameStatusTextview.setBackgroundResource(R.drawable.finished_game_row_victory);
                    }
                    else
                    {
                        finishedGameStatusTextview.setBackgroundResource(R.drawable.finished_game_row_failure);
                    }

                    final int waitingImages = participants.size();

                    for (int i = 0; i < participants.size(); i++)
                    {
                        final String playerID = participants.get(i).getPlayerID();
                        game.allocatePlayer(playerID);
                        final ImageView participantProfileImageview = new ImageView(context);
                        participantProfileImageview.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 100/participants.size()));
                        participantProfileImageview.setPadding(3,3,3,3);
                        participentsLayout.addView(participantProfileImageview);

                        Model.getInstance().getPlayerNameAndProfile(participants.get(i).getPlayerID(), new Model.GetPlayerNameAndProfile() {
                            @Override
                            public void onResult(String playerName, Bitmap image) {
                                participantProfileImageview.setImageBitmap(image);
                                game.addPlayer(playerID, playerName, image, participants.get(0));
                            }

                            @Override
                            public void onCancel(String msg) {

                            }
                        });
                    }

                    chooseFinishedGameLayout.setVisibility(View.VISIBLE);

                }

                @Override
                public void onFail(String msg) {
                    DialogInterrupter.showNeturalDialog(msg, getActivity());
                }
            });

            chooseFinishedGameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FinishedGameFlowFragment finishedGameFlowFragment = new FinishedGameFlowFragment();
                    finishedGameFlowFragment.setChosenGame(game);
                    FragmentTransaction ftr = getFragmentManager().beginTransaction();
                    ftr.replace(R.id.mainContainer, finishedGameFlowFragment);
                    ftr.commit();
                }
            });

            Log.d("TAG", "presenting guess row: " + i);
            return view;
        }

        public final void refershData()
        {
            final ListView list = (ListView) getActivity().findViewById(R.id.ChooseFinishedGameListView);
            ((BaseAdapter)list.getAdapter()).notifyDataSetChanged();
        }

    }

}
