package com.example.thieu.hunger.db.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thieu.hunger.db.HungerContract;
import com.example.thieu.hunger.db.HungerContract.OrderEntry;
import com.example.thieu.hunger.db.HungerContract.ProductEntry;
import com.example.thieu.hunger.db.HungerContract.Connect_Order_Product_Entry;
import com.example.thieu.hunger.db.SQLiteHelperClass;
import com.example.thieu.hunger.db.object.Connect_Order_Prod;
import com.example.thieu.hunger.db.object.Order;
import com.example.thieu.hunger.db.object.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HugoCastanheiro on 12.11.15.
 */
public class Connect_Order_Product_DataSource {
        private SQLiteDatabase db;
        private Context context;

        public Connect_Order_Product_DataSource(Context context){
            SQLiteHelperClass sqliteHelper = SQLiteHelperClass.getInstance(context);
            db = sqliteHelper.getWritableDatabase();
            this.context = context;
        }

        /**
         * Insert a new Connection
         */
        public long createConnection(Connect_Order_Prod connectOrderProd){
            long id;
            ContentValues values = new ContentValues();
            values.put(HungerContract.Connect_Order_Product_Entry.KEY_IDORDER, connectOrderProd.getIdOrder());
            values.put(Connect_Order_Product_Entry.KEY_IDPRODUCT, connectOrderProd.getIdProduct());
            values.put(Connect_Order_Product_Entry.KEY_AMOUNT, connectOrderProd.getAmount());

            id = this.db.insert(Connect_Order_Product_Entry.TABLE_CONNECT_ORDER_PROD, null, values);

            return id;
        }

        /**
         * Find one Connection by Id
         */
        public Connect_Order_Prod getConnectionByIdOrder(long id){
            String sql = "SELECT * FROM " + Connect_Order_Product_Entry.TABLE_CONNECT_ORDER_PROD +
                    " WHERE " + Connect_Order_Product_Entry.KEY_IDORDER + " = " + id;

            Cursor cursor = this.db.rawQuery(sql, null);

            if(cursor != null){
                cursor.moveToFirst();
            }

            Connect_Order_Prod connectOrderProd = new Connect_Order_Prod();
            connectOrderProd.setIdOrder(cursor.getInt(cursor.getColumnIndex(Connect_Order_Product_Entry.KEY_IDORDER)));
            connectOrderProd.setIdProduct(cursor.getInt(cursor.getColumnIndex(Connect_Order_Product_Entry.KEY_IDPRODUCT)));
            connectOrderProd.setAmount(cursor.getInt(cursor.getColumnIndex(Connect_Order_Product_Entry.KEY_AMOUNT)));
            return connectOrderProd;
        }

        /**
         * Get all Connections
         */
        public List<Connect_Order_Prod> getAllConnections(){
            List<Connect_Order_Prod> connectOrderProds = new ArrayList<Connect_Order_Prod>();
            String sql = "SELECT * FROM " + Connect_Order_Product_Entry.TABLE_CONNECT_ORDER_PROD + " ORDER BY " + Connect_Order_Product_Entry.KEY_IDORDER;

            Cursor cursor = this.db.rawQuery(sql, null);

            if(cursor.moveToFirst()){
                do{
                    Connect_Order_Prod connectOrderProd = new Connect_Order_Prod();
                    connectOrderProd.setIdOrder(cursor.getInt(cursor.getColumnIndex(Connect_Order_Product_Entry.KEY_IDORDER)));
                    connectOrderProd.setIdProduct(cursor.getInt(cursor.getColumnIndex(Connect_Order_Product_Entry.KEY_IDPRODUCT)));
                    connectOrderProd.setAmount(cursor.getInt(cursor.getColumnIndex(Connect_Order_Product_Entry.KEY_AMOUNT)));

                    connectOrderProds.add(connectOrderProd);
                } while(cursor.moveToNext());
            }

            return connectOrderProds;
        }

    /**
     * Get all Connections
     */
    public ArrayList<Connect_Order_Prod> getAllConnectionsByOrderId(int order_id){
        ArrayList<Connect_Order_Prod> connectOrderProds = new ArrayList<Connect_Order_Prod>();
        String sql = "SELECT * FROM " +
                    Connect_Order_Product_Entry.TABLE_CONNECT_ORDER_PROD +
                    " WHERE " + Connect_Order_Product_Entry.KEY_IDORDER + " = "+order_id+
                    " ORDER BY " + Connect_Order_Product_Entry.KEY_IDORDER;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                Connect_Order_Prod connectOrderProd = new Connect_Order_Prod();
                connectOrderProd.setIdOrder(cursor.getInt(cursor.getColumnIndex(Connect_Order_Product_Entry.KEY_IDORDER)));
                connectOrderProd.setIdProduct(cursor.getInt(cursor.getColumnIndex(Connect_Order_Product_Entry.KEY_IDPRODUCT)));
                connectOrderProd.setAmount(cursor.getInt(cursor.getColumnIndex(Connect_Order_Product_Entry.KEY_AMOUNT)));

                connectOrderProds.add(connectOrderProd);
            } while(cursor.moveToNext());
        }

        return connectOrderProds;
    }
        /**
         *  Update a Connection
         */
        public int updateConnection(Connect_Order_Prod connectOrderProd){
            ContentValues values = new ContentValues();
            values.put(Connect_Order_Product_Entry.KEY_IDORDER, connectOrderProd.getIdOrder());
            values.put(Connect_Order_Product_Entry.KEY_IDORDER, connectOrderProd.getIdProduct());
            values.put(Connect_Order_Product_Entry.KEY_AMOUNT, connectOrderProd.getAmount());

            return this.db.update(Connect_Order_Product_Entry.TABLE_CONNECT_ORDER_PROD, values, Connect_Order_Product_Entry.KEY_IDORDER + " = ?",
                    new String[] { String.valueOf(connectOrderProd.getIdOrder()) });
        }

        /**
         * Delete a Connection
         */
        public void deleteConnection(long id) {
            //delete the Connection
            this.db.delete(Connect_Order_Product_Entry.TABLE_CONNECT_ORDER_PROD, Connect_Order_Product_Entry.KEY_IDORDER + " = ?",
                    new String[]{String.valueOf(id)});
        }

        /**
         * Delete a Connection
         */
        public void deleteConnectionByOrderId(long id) {
            //delete the Connection
            this.db.delete(Connect_Order_Product_Entry.TABLE_CONNECT_ORDER_PROD, Connect_Order_Product_Entry.KEY_IDORDER + " = ?",
                    new String[]{String.valueOf(id)});
        }

}
