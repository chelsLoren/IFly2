package com.a14541565.chelsey.ifly;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chelsey on 01/08/2017.
 */

public class DbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE = "create table "+DbPhoto.TABLE_NAME+
            "(id integer primary key autoincrement, "+DbPhoto.NAME+ " text,"+DbPhoto.DESCRIPTION+
            " text,"+DbPhoto.LOCATION+ " text,"+DbPhoto.SYNC_STATUS+" integer);";
    public static final String DROP_TABLE = "drop table if exists "+DbPhoto.TABLE_NAME;

    public DbHelper(Context context){
        super(context, DbPhoto.DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void saveToLocalDatabase(String name, String description, String location, int sync_status, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbPhoto.NAME, name);
        contentValues.put(DbPhoto.DESCRIPTION, description);
        contentValues.put(DbPhoto.LOCATION, location);
        contentValues.put(DbPhoto.SYNC_STATUS, sync_status);

        database.insert(DbPhoto.TABLE_NAME, null, contentValues);
    }

    public Cursor readFromLocalDatabase(SQLiteDatabase database){
        String[] readfromTable = {DbPhoto.NAME, DbPhoto.DESCRIPTION, DbPhoto.LOCATION, DbPhoto.SYNC_STATUS};

        return (database.query(DbPhoto.TABLE_NAME, readfromTable, null, null, null, null, null));
    }

    public void updateLocalDatabase(String name, String description, String location, int sync_status, SQLiteDatabase database){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbPhoto.SYNC_STATUS, sync_status);
        String selection = DbPhoto.NAME+DbPhoto.DESCRIPTION+DbPhoto.LOCATION+ " LIKE ?";
        String[] selection_args = {name, description, location};
        database.update(DbPhoto.TABLE_NAME, contentValues, selection, selection_args);
    }
}
