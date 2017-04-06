package com.brokenpicinc.brokenpic.model;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.brokenpicinc.brokenpic.utils.MyAppContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by orgaf_000 on 2/4/2017.
 */

public class ModelSql {
    private SQLiteOpenHelper helper;
    private int version = 7;

    public ModelSql(){
        helper = new BrokenPicHelper(MyAppContext.getAppContext());
    }

    public double getPlayersLastUpdateDate()
    {
        return PlayerSql.getLastUpdateDate(helper.getReadableDatabase());
    }

    public void setPlayersLastUpdateDate(double recentUpdate){
        PlayerSql.setLastUpdateDate(helper.getWritableDatabase(), recentUpdate);
    }

    public void addPlayer(Player pl)
    {
        PlayerSql.addPlayer(helper.getWritableDatabase(), pl);
    }

    public List<Player> getAllPlayers()
    {
        return PlayerSql.getAllPlayers(helper.getReadableDatabase());
    }

    public void setPlayerPhoto(String playerID, Bitmap profile) {
        // save the profile in local storage, in path
        String path = playerID + "-profile.png";
        saveImageToSdCard(profile, path);

        PlayerSql.setPlayerProfileLocalPath(helper.getWritableDatabase(), playerID, path);
    }

    public String getPlayerProfileRemotePath(String playerID)
    {
        String remotePath = PlayerSql.getPlayerProfileRemotePath(helper.getReadableDatabase() ,playerID);
        return remotePath;
    }

    public Bitmap getPlayerProfileFromLocal(String playerID)
    {
        String localPath = PlayerSql.getPlayerProfileLocalPath(helper.getReadableDatabase() ,playerID);

        if (localPath.equals(""))
        {
            return null;
        }

        // get the image in from local storage, from localPath
        Bitmap profileLocal = getImageFromSdCard(localPath);
        return profileLocal;
    }

    public boolean saveImageToSdCard(Bitmap bitmap, String filename)
    {
//        File sdCardDirectory = Environment.getExternalStorageDirectory();
////        File sdCardDirectory = new File("/mnt");
//
//        File dir = new File(sdCardDirectory.getAbsolutePath() + "/images");
//        if (!dir.exists())
//        {
//            if (!dir.mkdirs())
//            {
//                Log.d("TAG", "failed to create dirs. path = " + dir.getAbsolutePath());
//            }
//        }
//        File file = new File(dir, filename);
        ContextWrapper cw = new ContextWrapper(MyAppContext.getAppContext());
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File mypath = new File(directory, filename);


        boolean success = false;

        // Encode the file as a PNG image.
        FileOutputStream outStream;
        try {
            outStream = new FileOutputStream(mypath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        /* 100 to keep full quality of the image */

            outStream.flush();
            outStream.close();
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (success) {
            Log.d("TAG", "Image saved success");
        } else {
            Log.d("TAG", "Error during image saving");
        }

        return success;
    }

    public Bitmap getImageFromSdCard(String filename)
    {
        ContextWrapper cw = new ContextWrapper(MyAppContext.getAppContext());
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        if (directory == null)
        {
            Log.d("TAG", "failed to open local directory: " + directory.getAbsolutePath());
        }

        File f = new File(directory, filename);

        if (f == null)
        {
            Log.d("TAG", "failed to open local file: " + f.getAbsolutePath());
        }
        Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());

        return bmp;
    }

    class BrokenPicHelper extends SQLiteOpenHelper
    {

        public BrokenPicHelper(Context context) {
            super(context, "database.db", null, version);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            PlayerSql.create(db);
            LastUpdateSql.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("TAG", "ModelSql version upgraded. old = " + oldVersion + ", new = " + newVersion);
            PlayerSql.dropTable(db);
            LastUpdateSql.dropTable(db);

            onCreate(db);
        }

    }


}
