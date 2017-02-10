package com.brokenpicinc.brokenpic;


import android.app.Fragment;
import android.app.FragmentTransaction;
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
    List<GuessGame> gamesToGuessList;

    public ChooseDrawFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_game, container, false);

        gamesToGuessList = Model.getInstance().getGamesToGuess();

        final ListView list = (ListView) view.findViewById(R.id.GamesToGuessListView);
        final DrawsAdapter adapter = new DrawsAdapter(getActivity());
        list.setAdapter(adapter);

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

            final GuessGame game = gamesToGuessList.get(i);
            final TextView indexTv = (TextView) view.findViewById(R.id.row_draw_index);
            final ImageView participentImageView = (ImageView)view.findViewById(R.id.row_draw_participante_image);
            final ImageView imageView = (ImageView)view.findViewById(R.id.row_draw_image);
            final ImageButton playBtn = (ImageButton) view.findViewById(R.id.row_draw_play);


            indexTv.setText(Integer.toString(i));
            // Todo: set real user photo
            participentImageView.setImageResource(R.mipmap.ic_launcher);
            // Todo: set real draw picture
            imageView.setImageResource(R.mipmap.ic_launcher);
            playBtn.setImageResource(R.drawable.play_btn);;

            playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GuessItFragment guessItFragment = new GuessItFragment();
                    guessItFragment.setChosenGame(game);
                    FragmentTransaction ftr = getFragmentManager().beginTransaction();
                    ftr.replace(R.id.mainContainer,guessItFragment);
                    ftr.commit();
                }
            });

            Log.d("TAG", "presenting draw row: " + i);
            return view;
        }



    }

}
