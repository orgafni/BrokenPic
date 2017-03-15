package com.brokenpicinc.brokenpic.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by orgaf on 15/03/2017.
 */

public class PlayerSql {
    private static final String PLAYERS = "PLAYERS";
    private static final String PL_ID = "plId";
    private static final String NICK_NAME = "nickname";
    private static final String IMAGE_URL = "imageUrl";

    public static void create(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + PLAYERS + " (" + PL_ID + " TEXT PRIMARY KEY, " + NICK_NAME + " TEXT, " + IMAGE_URL + " TEXT)");
    }

    public static void dropTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE " + PLAYERS);
    }

    public static double getLastUpdateDate(SQLiteDatabase readableDatabase)
    {
        return LastUpdateSql.getLastUpdate(readableDatabase, PLAYERS);
    }

    public static void setLastUpdateDate(SQLiteDatabase writableDatabase, double recentUpdate)
    {
        LastUpdateSql.setLastUpdate(writableDatabase, PLAYERS, recentUpdate);
    }

    public static void addPlayer(SQLiteDatabase writableDatabase, Player player) {
        ContentValues values = new ContentValues();

        values.put(PL_ID, player.getUniqueID());
        values.put(NICK_NAME, player.getName());
        values.put(IMAGE_URL, player.getImage());

        long rowId = writableDatabase.insertWithOnConflict(PLAYERS, PL_ID, values, SQLiteDatabase.CONFLICT_REPLACE);
        if (rowId <= 0) {
            Log.e("TAG","fail to insert into player");
        }
    }

    public static List<Player> getAllPlayers(SQLiteDatabase readableDatabase)
    {
        SQLiteDatabase mDataBase;
        Cursor dbCursor = readableDatabase.query(PLAYERS, null, null, null, null, null, null);

        String[] columnNames = dbCursor.getColumnNames();

        List<Player> allPlayers = new LinkedList<>();
        if (dbCursor.moveToFirst())
        {
            do
            {
                Player newPl = new Player();
                newPl.setUniqueID(dbCursor.getString(dbCursor.getColumnIndex(PL_ID)));
                newPl.setName(dbCursor.getString(dbCursor.getColumnIndex(NICK_NAME)));
                newPl.setImage(dbCursor.getString(dbCursor.getColumnIndex(IMAGE_URL)));
                allPlayers.add(newPl);
            }while(dbCursor.moveToNext());
        }

        return allPlayers;
    }
}
