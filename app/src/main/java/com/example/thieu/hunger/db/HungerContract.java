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

    public static abstract class ProductEntry implements BaseColumns {

        public static final String TABLE_PRODUCT = "product";

        //Product Column names
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_PRICE = "price";
        public static final String KEY_CATEGORY = "category";


        //Table Product create
        public static final String CREATE_TABLE_PRODUCT = "CREATE TABLE "
                + TABLE_PRODUCT + "("
                + ProductEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + ProductEntry.KEY_NAME + " TEXT, "
                + ProductEntry.KEY_PRICE + " TEXT, "
                + ProductEntry.KEY_CATEGORY + " TEXT, "
                + ");";
    }

    public static abstract class OrderEntry implements BaseColumns {

        public static final String TABLE_ORDER = "order";

        //Product Column names
        public static final String KEY_ID = "id";
        public static final String KEY_IDUSER = "idUser";
        public static final String KEY_NUMTABLE = "numTable";
        public static final String KEY_DATE = "date";
        public static final String KEY_ACCOMPLISHED = "accomplished";


        //Table Order create
        public static final String CREATE_TABLE_ORDER = "CREATE TABLE "
                + TABLE_ORDER + "("
                + OrderEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + OrderEntry.KEY_IDUSER+ " INTEGER FOREIGN KEY,"
                + OrderEntry.KEY_NUMTABLE + " TEXT, "
                + OrderEntry.KEY_DATE + " TEXT, "
                + OrderEntry.KEY_ACCOMPLISHED + " TEXT, "
                + ");";
    }
}
