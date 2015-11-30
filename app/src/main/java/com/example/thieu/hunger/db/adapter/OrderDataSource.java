package com.example.thieu.hunger.db.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.thieu.hunger.db.HungerContract.OrderEntry;
import com.example.thieu.hunger.db.HungerContract.ProductEntry;
import com.example.thieu.hunger.db.SQLiteHelperClass;
import com.example.thieu.hunger.db.object.Order;
import com.example.thieu.hunger.db.object.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HugoCastanheiro on 07.11.15.
 */
public class OrderDataSource {
    private SQLiteDatabase db;
    private Context context;

    public OrderDataSource(Context context){
        SQLiteHelperClass sqliteHelper = SQLiteHelperClass.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     * Insert a new Order
     */
    public long createOrder(Order order){
        long id;
        ContentValues values = new ContentValues();
        values.put(OrderEntry.KEY_IDUSER, order.getIdUser());
        values.put(OrderEntry.KEY_NUMTABLE, order.getNumTable());
        values.put(OrderEntry.KEY_DATE, order.getDate());

        id = this.db.insert(OrderEntry.TABLE_ORDER, null, values);

        return id;
    }

    /**
     * Find one order by Id
     */
    public Order getOrderById(long id){
        String sql = "SELECT * FROM " + OrderEntry.TABLE_ORDER +
                " WHERE " + OrderEntry.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Order order = new Order();
        order.setId(cursor.getInt(cursor.getColumnIndex(OrderEntry.KEY_ID)));
        order.setIdUser(cursor.getInt(cursor.getColumnIndex(OrderEntry.KEY_IDUSER)));
        order.setNumTable(cursor.getInt(cursor.getColumnIndex(OrderEntry.KEY_NUMTABLE)));
        order.setDate(cursor.getInt(cursor.getColumnIndex(OrderEntry.KEY_DATE)));
        return order;
    }

    /**
     * Get all Orders
     */
    public List<Order> getAllOrders(){
        List<Order> orders = new ArrayList<Order>();
        String sql = "SELECT * FROM " + OrderEntry.TABLE_ORDER + " ORDER BY " + OrderEntry.KEY_DATE + " DESC";

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                Order order = new Order();
                order.setId(cursor.getInt(cursor.getColumnIndex(OrderEntry.KEY_ID)));
                order.setIdUser(cursor.getInt(cursor.getColumnIndex(OrderEntry.KEY_IDUSER)));
                order.setNumTable(cursor.getInt(cursor.getColumnIndex(OrderEntry.KEY_NUMTABLE)));
                order.setDate(cursor.getInt(cursor.getColumnIndex(OrderEntry.KEY_DATE)));

                orders.add(order);
            } while(cursor.moveToNext());
        }

        return orders;
    }

    /**
     * Get all Orders
     */
    public ArrayList<Order> getAllOrdersNewerThan(int day){
        ArrayList<Order> orders = new ArrayList<Order>();
        String sql = "SELECT * FROM " + OrderEntry.TABLE_ORDER + " WHERE "+OrderEntry.KEY_DATE+" >= "+day+" ORDER BY " + OrderEntry.KEY_DATE + " DESC";

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                Order order = new Order();
                order.setId(cursor.getInt(cursor.getColumnIndex(OrderEntry.KEY_ID)));
                order.setIdUser(cursor.getInt(cursor.getColumnIndex(OrderEntry.KEY_IDUSER)));
                order.setNumTable(cursor.getInt(cursor.getColumnIndex(OrderEntry.KEY_NUMTABLE)));
                order.setDate(cursor.getInt(cursor.getColumnIndex(OrderEntry.KEY_DATE)));

                orders.add(order);
            } while(cursor.moveToNext());
        }

        return orders;
    }

    /**
     *  Update a order
     */
    public int updateOrder(Order order){
        ContentValues values = new ContentValues();
        values.put(OrderEntry.KEY_IDUSER, order.getIdUser());
        values.put(OrderEntry.KEY_NUMTABLE, order.getNumTable());
        values.put(OrderEntry.KEY_DATE, order.getDate());

        return this.db.update(OrderEntry.TABLE_ORDER, values, OrderEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(order.getId()) });
    }

    /**
     * Delete a order
     */
    public void deleteOrder(long id){
        //delete the order
        this.db.delete(OrderEntry.TABLE_ORDER, OrderEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
}
