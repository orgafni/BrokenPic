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

import com.brokenpicinc.brokenpic.model.DrawGame;
import com.brokenpicinc.brokenpic.model.GuessGame;
import com.brokenpicinc.brokenpic.model.Model;

import org.w3c.dom.Text;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseGuessFragment extends Fragment {
    List<DrawGame> gamesToDrawList;

    public ChooseGuessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_game, container, false);

        gamesToDrawList = Model.getInstance().getGamesToDraw();

        final ListView list = (ListView) view.findViewById(R.id.GamesToGuessListView);
        final GuessesAdapter adapter = new GuessesAdapter(getActivity());
        list.setAdapter(adapter);

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

            final DrawGame game = gamesToDrawList.get(i);
            final TextView indexTv = (TextView) view.findViewById(R.id.row_guess_index);
            final ImageView participentImageView = (ImageView) view.findViewById(R.id.row_guess_participante_image);
            final TextView phraseTv = (TextView) view.findViewById(R.id.row_guess_phrase);
            final ImageButton playBtn = (ImageButton) view.findViewById(R.id.row_guess_play);


            indexTv.setText(Integer.toString(i));
            // Todo: set real user photo
            participentImageView.setImageResource(R.mipmap.ic_launcher);
            phraseTv.setText(game.getWordToDraw());
            playBtn.setImageResource(R.drawable.play_btn);
            ;

            playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrawItFragment drawItFragment = new DrawItFragment();
                    drawItFragment.setChosenGame(game);
                    FragmentTransaction ftr = getFragmentManager().beginTransaction();
                    ftr.replace(R.id.mainContainer, drawItFragment);
                    ftr.commit();
                }
            });

            Log.d("TAG", "presenting guess row: " + i);
            return view;
        }

    }
}
