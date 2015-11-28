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
        public static final String KEY_ISLOGGED = "islogged";

        //Table User create
        public static final String CREATE_TABLE_USER = "CREATE TABLE "
                + TABLE_USER + " ("
                + UserEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + UserEntry.KEY_NAME + " TEXT, "
                + UserEntry.KEY_PASSWORD + " TEXT, "
                + UserEntry.KEY_TYPE + " INTEGER, "
                + UserEntry.KEY_ISLOGGED + " INTEGER "
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
                + TABLE_PRODUCT + " ("
                + ProductEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + ProductEntry.KEY_NAME + " TEXT, "
                + ProductEntry.KEY_PRICE + " TEXT, "
                + ProductEntry.KEY_CATEGORY + " INT "
                + ");";
    }

    public static abstract class OrderEntry implements BaseColumns {

        public static final String TABLE_ORDER = "order_table";

        //Product Column names
        public static final String KEY_ID = "id";
        public static final String KEY_IDUSER = "idUser";
        public static final String KEY_NUMTABLE = "numTable";
        public static final String KEY_DATE = "orderDate";
        public static final String KEY_ACCOMPLISHED = "accomplished";


        //Table Order create
        public static final String CREATE_TABLE_ORDER = "CREATE TABLE "
                + TABLE_ORDER + " ("
                + OrderEntry.KEY_ID + " INTEGER PRIMARY KEY, "
                + OrderEntry.KEY_IDUSER+ " INTEGER, "
                + OrderEntry.KEY_NUMTABLE + " TEXT, "
                + OrderEntry.KEY_DATE + " TEXT, "
                + OrderEntry.KEY_ACCOMPLISHED + " TEXT "
                + ");";
    }

    public static abstract class Connect_Order_Product_Entry implements BaseColumns {

        public static final String TABLE_CONNECT_ORDER_PROD = "connect_order_prod";

        //Product Column names
        public static final String KEY_IDORDER = "idOrder";
        public static final String KEY_IDPRODUCT = "idProduct";
        public static final String KEY_AMOUNT= "amount";

        //Table Order create
        public static final String CREATE_TABLE_CONNECT_ORDER_PROD = "CREATE TABLE "
                + TABLE_CONNECT_ORDER_PROD + "( "
                + Connect_Order_Product_Entry.KEY_IDORDER + " INTEGER, "
                + Connect_Order_Product_Entry.KEY_IDPRODUCT+ " INTEGER, "
                + Connect_Order_Product_Entry.KEY_AMOUNT + " TEXT "
                + ");";
    }
}
