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
import android.widget.ListView;
import android.widget.TextView;

import com.brokenpicinc.brokenpic.model.Model;
import com.brokenpicinc.brokenpic.model.Player;
import com.brokenpicinc.brokenpic.utils.DialogInterrupter;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartNewGameFragment extends Fragment {
    List<Player> playersList;

    List<Player> chosenPlayersList;


    public StartNewGameFragment() {
        // Required empty public constructor

        playersList = new LinkedList<Player>();
        chosenPlayersList = new LinkedList<Player>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_start_new_game, container, false);

        final ListView list = (ListView) view.findViewById(R.id.particpantsListListView);
        final PlayersAdapter adapter = new PlayersAdapter(getActivity());
        list.setAdapter(adapter);

        ImageButton cleanBtn = (ImageButton)view.findViewById(R.id.new_game_clean_btn);
        cleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenPlayersList.clear();
                adapter.refershData();
            }
        });

        ImageButton startBtn = (ImageButton)view.findViewById(R.id.new_game_start);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inforce that there are even players in the game (except me, the creator)
                if (chosenPlayersList.size() % 2 != 0 || chosenPlayersList.size() == 0)
                {
                    DialogInterrupter.showNeturalDialog("Invalid amount players chosen", getActivity());
                }
                else
                {
                    final ChooseWordFragment chooseWordFragment = new ChooseWordFragment();
                    chooseWordFragment.SetChosenPlayers(chosenPlayersList);
                    MainActivity.MoveToFragment(chooseWordFragment, true);
                }
            }
        });

        Model.getInstance().getAllPlayers(new Model.GetAllPlayersListener() {
            @Override
            public void onResult(List<Player> players) {
                if (playersList.size() == 0)
                {
                    playersList.addAll(players);
                }
                adapter.refershData();
            }

            @Override
            public void onCancel(String msg) {
                DialogInterrupter.showNeturalDialog(msg, getActivity());
            }
        });


        return view;
    }

    class PlayersAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public PlayersAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return playersList.size();
        }

        @Override
        public Object getItem(int i) {
            return playersList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = layoutInflater.inflate(R.layout.participant_row, null);
            }

            final Player pl = playersList.get(i);
            final TextView indexTv = (TextView) view.findViewById(R.id.row_participent_index);
            final TextView nameTv = (TextView) view.findViewById(R.id.row_participent_name);
            final ImageView imageView = (ImageView)view.findViewById(R.id.row_participent_image);
            final ImageButton addRemoveBtn = (ImageButton) view.findViewById(R.id.row_participent_add_remove);

            nameTv.setText(pl.getName());
            // Todo: set real user photo

            Model.getInstance().getImageByPath(pl.getImage(), new Model.GetImageListener() {
                @Override
                public void onSuccess(Bitmap image) {
                    imageView.setImageBitmap(image);
                }

                @Override
                public void onFail() {

                }
            });

            final int playerIndex = chosenPlayersList.indexOf(pl);
            final String emptyIndex = " ";

            // not in chosen list
            if (playerIndex == -1)
            {
                indexTv.setText(emptyIndex);
                addRemoveBtn.setImageResource(R.drawable.add_btn);
            }
            else
            {
                indexTv.setText(Integer.toString(playerIndex + 1));
                addRemoveBtn.setImageResource(R.drawable.remove_part);
            }

            addRemoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Add to chosen list
                    if (playerIndex == -1)
                    {
                        //addRemoveBtn.setImageResource(R.drawable.remove_part);
                        chosenPlayersList.add(pl);
                        //indexTv.setText(chosenPlayersList.size());
                    }
                    else
                    {
                        //addRemoveBtn.setImageResource(R.drawable.add_btn);
                        chosenPlayersList.remove(pl);
                        //indexTv.setText(emptyIndex);
                    }
                    refershData();
                }
            });

            Log.d("TAG", "presenting row: " + i);
            return view;
        }
        public final void refershData()
        {
            if (isVisible()) {
                final ListView list = (ListView) getActivity().findViewById(R.id.particpantsListListView);
                ((BaseAdapter) list.getAdapter()).notifyDataSetChanged();
            }
        }

    }

}


