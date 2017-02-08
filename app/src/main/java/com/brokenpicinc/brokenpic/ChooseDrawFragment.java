package com.brokenpicinc.brokenpic;


import android.app.Fragment;
import android.content.Context;
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

import com.brokenpicinc.brokenpic.model.GuessGame;
import com.brokenpicinc.brokenpic.model.Model;
import com.brokenpicinc.brokenpic.model.Player;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseDrawFragment extends Fragment {

    LayoutInflater inf;
    List<GuessGame> gamesToGuess;


    public ChooseDrawFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_game, container, false);
        inf = inflater;

        gamesToGuess = Model.getInstance().getGamesToGuess();

        return view;
    }

    class DrawsAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public DrawsAdapter(Context context) {
            layoutInflater = inf;
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

            nameTv.setText(pl.getNickname());
            // Todo: set real user photo
            imageView.setImageResource(R.mipmap.ic_launcher);


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
            final ListView list = (ListView) getActivity().findViewById(R.id.particpantsListListView);
            ((BaseAdapter)list.getAdapter()).notifyDataSetChanged();
        }

    }

}
