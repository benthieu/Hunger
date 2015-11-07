package com.example.thieu.hunger.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import com.example.thieu.hunger.db.HungerContract.ProductEntry;
import com.example.thieu.hunger.db.HungerContract.UserEntry;
import com.example.thieu.hunger.db.HungerContract.OrderEntry;



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
        db.execSQL(ProductEntry.CREATE_TABLE_PRODUCT);
        db.execSQL(OrderEntry.CREATE_TABLE_ORDER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + UserEntry.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + ProductEntry.TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + OrderEntry.TABLE_ORDER);

        //create new tables
        onCreate(db);
    }
}
