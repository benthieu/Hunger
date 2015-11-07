package com.example.thieu.hunger.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import com.example.thieu.hunger.db.HungerContract.UserEntry;

/**
 * Created by benjamin on 07.11.2015.
 */
public class SQLiteHelperClass extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "hunger";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteHelperClass instance;

    private SQLiteHelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    public static SQLiteHelperClass getInstance(Context context){
        if(instance == null){
            instance = new SQLiteHelperClass(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserEntry.CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_USER);
        //create new tables
        onCreate(db);
    }
}
