package com.example.thieu.hunger.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import com.example.thieu.hunger.db.HungerContract.ProductEntry;
import com.example.thieu.hunger.db.HungerContract.UserEntry;
import com.example.thieu.hunger.db.HungerContract.OrderEntry;
import com.example.thieu.hunger.db.HungerContract.Connect_Order_Product_Entry;




public class SQLiteHelperClass extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "hunger";
    private static final int DATABASE_VERSION = 2;
    private static SQLiteHelperClass instance;

    private SQLiteHelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // get database
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
        // create all tablse
        db.execSQL(UserEntry.CREATE_TABLE_USER);
        db.execSQL(ProductEntry.CREATE_TABLE_PRODUCT);
        db.execSQL(OrderEntry.CREATE_TABLE_ORDER);
        db.execSQL(Connect_Order_Product_Entry.CREATE_TABLE_CONNECT_ORDER_PROD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + ProductEntry.TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + OrderEntry.TABLE_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + Connect_Order_Product_Entry.TABLE_CONNECT_ORDER_PROD);
        //create new tables
        onCreate(db);
    }
}
