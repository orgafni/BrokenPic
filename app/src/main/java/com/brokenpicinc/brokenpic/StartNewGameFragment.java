package com.brokenpicinc.brokenpic;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.brokenpicinc.brokenpic.model.Model;
import com.brokenpicinc.brokenpic.model.Player;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartNewGameFragment extends Fragment {
    List<Player> playersList;
    LayoutInflater inf;


    public StartNewGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_start_new_game, container, false);

        inf = inflater;
        playersList = Model.getInstance().getAllPlayers();

        ListView list = (ListView) view.findViewById(R.id.particpantsListListView);
        PlayersAdapter adapter = new PlayersAdapter(getActivity());
//        StudentsAdapter adapter = new StudentsAdapter();
        list.setAdapter(adapter);

//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("TAG","row selected " + i);
//                view.
//                Intent intent = new Intent(getApplicationContext(),StudentDetailsActivity.class);
//                intent.putExtra("id",studentsList.get(i).getId());
//                startActivity(intent);
//            }
//        });


        return view;
    }

    class PlayersAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public PlayersAdapter(Context context) {
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

            Player pl = playersList.get(i);
            TextView indexTv = (TextView) view.findViewById(R.id.row_participent_index);
            TextView nameTv = (TextView) view.findViewById(R.id.row_participent_name);
            ImageView imageView = (ImageView)view.findViewById(R.id.row_participent_image);
            ImageButton addRemoveBtn = (ImageButton) view.findViewById(R.id.row_participent_add_remove);

//            indexTv.setText("?");
            nameTv.setText(pl.getNickname());
            imageView.setImageResource(R.mipmap.ic_launcher);
            addRemoveBtn.setImageResource(R.drawable.add_btn);

            Log.d("TAG", "presenting row: " + i);
            return view;
        }
    }

    class StudentsAdapter extends BaseAdapter{

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
            if (view == null){
                view = inf.inflate(R.layout.students_list_row_del,null);

            }

            Player st = playersList.get(i);
            TextView nameTv = (TextView) view.findViewById(R.id.studentRowName);
            TextView idTv = (TextView) view.findViewById(R.id.studentRowId);
            nameTv.setText(st.getNickname());
            CheckBox cb = (CheckBox) view.findViewById(R.id.studentRowCheckBox);
            cb.setTag(new Integer(i));
            Log.d("TAG","presenting row: " + i);
            return view;
        }
    }

}
