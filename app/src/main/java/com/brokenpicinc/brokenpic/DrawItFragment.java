package com.brokenpicinc.brokenpic;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawItFragment extends Fragment {
    final int TAKE_PICTURE = 0;
    ImageButton yourPicGuessImageBtn;
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
        yourPicGuessImageBtn = (ImageButton) view.findViewById(R.id.your_pic_guess_imagebtn);

        final ImageButton sendDrawBtn = (ImageButton) view.findViewById(R.id.send_your_draw);

        // TODO: set real drawer profile photo
        writerImageView.setImageBitmap(gameToDraw.getPlayerProfile());
        writerNameTv.setText(gameToDraw.getPlayerName());
        wordToDrawTv.setText(gameToDraw.getWordToDraw());

        yourPicGuessImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, TAKE_PICTURE);//zero can be replaced with any action code
                // TODO: open camera, or option to upload an photo, or maybe a blank page to draw on.
            }
        });

        sendDrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap playerDraw = ((BitmapDrawable)yourPicGuessImageBtn.getDrawable()).getBitmap();

                // TODO: get the draw picture path.
                String drawPicPath = "/asad/asdasd/asdd.png";

                // TODO: verify that there is a draw.
                if (drawPicPath.isEmpty())
                {
                    // TODO: alert
                }
                else
                {
                    Model.getInstance().advanceGame(gameToDraw, playerDraw);
                    final MenuFragment menuFragment = new MenuFragment();
                    MainActivity.MoveToFragment(menuFragment, false, true, DrawItFragment.class.getName());
                }
            }
        });
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
//                    Log.d("TAG", "selectedImage " + selectedImage.getPath());
                    yourPicGuessImageBtn.setImageBitmap(photo);

//                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
//                    Uri tempUri = getImageUri(getActivity().getApplicationContext(), photo);
//                    Log.d("TAG", "tempUri " + tempUri.getPath());
//                    // CALL THIS METHOD TO GET THE ACTUAL PATH
//                    File finalFile = new File(getRealPathFromURI(tempUri));
                }

                break;
        }
    }
}
