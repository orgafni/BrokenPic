package com.brokenpicinc.brokenpic.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by orgaf on 15/03/2017.
 */

public class LastUpdateSql {
    final static String LAST_UPDATE_TABLE = "last_update";
    final static String LAST_UPDATE_TABLE_TNAME = "table_name";
    final static String LAST_UPDATE_TABLE_DATE = "date";

    static public void create(SQLiteDatabase db) {
        db.execSQL("create table " + LAST_UPDATE_TABLE + " (" +
                LAST_UPDATE_TABLE_TNAME + " TEXT PRIMARY KEY," +
                LAST_UPDATE_TABLE_DATE + " NUMERIC);" );
    }
    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("drop table " + LAST_UPDATE_TABLE + ";");
    }

    public static void setLastUpdate(SQLiteDatabase db, String table, double date) {
        ContentValues values = new ContentValues();
        values.put(LAST_UPDATE_TABLE_TNAME, table);
        values.put(LAST_UPDATE_TABLE_DATE, date);
        db.insertWithOnConflict(LAST_UPDATE_TABLE,
                LAST_UPDATE_TABLE_TNAME,
                values,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static double getLastUpdate(SQLiteDatabase db, String tableName) {
        String[] args = {tableName};
        Cursor cursor = db.query(LAST_UPDATE_TABLE, null,
                LAST_UPDATE_TABLE_TNAME + " = ?",
                args , null, null, null);
        if (cursor.moveToFirst()) {
            return
                    cursor.getDouble(cursor.getColumnIndex(LAST_UPDATE_TABLE_DATE));
        }
        return 0;
    }
}
