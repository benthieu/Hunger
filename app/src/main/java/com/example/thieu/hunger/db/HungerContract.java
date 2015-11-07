package com.example.thieu.hunger.db;

import android.provider.BaseColumns;

/**
 * Created by benjamin on 07.11.2015.
 */
public final class HungerContract {
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_USER = "user";

        //User Column names
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_PASSWORD = "password";
        public static final String KEY_TYPE = "type";

        //Table User create
        public static final String CREATE_TABLE_USER = "CREATE TABLE "
                + TABLE_USER + "("
                + UserEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + UserEntry.KEY_NAME + " TEXT, "
                + UserEntry.KEY_PASSWORD + " TEXT, "
                + UserEntry.KEY_TYPE + " INTEGER "
                + ");";
    }
}
