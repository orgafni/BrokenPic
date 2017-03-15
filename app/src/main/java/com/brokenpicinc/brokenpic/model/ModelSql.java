package com.brokenpicinc.brokenpic.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brokenpicinc.brokenpic.utils.MyAppContext;

import java.util.List;

/**
 * Created by orgaf_000 on 2/4/2017.
 */

public class ModelSql {
    private SQLiteOpenHelper helper;
    private int version = 1;

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
            PlayerSql.dropTable(db);
            LastUpdateSql.create(db);

            onCreate(db);
        }


    }
}
