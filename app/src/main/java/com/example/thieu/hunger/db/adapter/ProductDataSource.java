package com.example.thieu.hunger.db.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thieu.hunger.db.HungerContract.ProductEntry;
import com.example.thieu.hunger.db.SQLiteHelperClass;
import com.example.thieu.hunger.db.object.Product;
import com.example.thieu.hunger.db.object.User;

import java.util.ArrayList;
import java.util.List;

public class ProductDataSource {
    private SQLiteDatabase db;
    private Context context;

    public ProductDataSource(Context context){
        SQLiteHelperClass sqliteHelper = SQLiteHelperClass.getInstance(context);
        // get database
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     * Insert a new Product
     */
    public long createProduct(Product product){
        long id;
        ContentValues values = new ContentValues();
        values.put(ProductEntry.KEY_NAME, product.getName());
        values.put(ProductEntry.KEY_PRICE, product.getPrice());
        values.put(ProductEntry.KEY_CATEGORY, product.getCategory());

        id = this.db.insert(ProductEntry.TABLE_PRODUCT, null, values);

        return id;
    }

    /**
     * Find one product by Id
     */
    public Product getProductById(long id){
        String sql = "SELECT * FROM " + ProductEntry.TABLE_PRODUCT +
                " WHERE " + ProductEntry.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Product product = new Product();
        product.setId(cursor.getInt(cursor.getColumnIndex(ProductEntry.KEY_ID)));
        product.setName(cursor.getString(cursor.getColumnIndex(ProductEntry.KEY_NAME)));
        product.setPrice(cursor.getInt(cursor.getColumnIndex(ProductEntry.KEY_PRICE)));
        product.setCategory(cursor.getInt(cursor.getColumnIndex(ProductEntry.KEY_CATEGORY)));


        return product;
    }

    /**
     * Get all Products
     */
    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<Product>();
        String sql = "SELECT * FROM " + ProductEntry.TABLE_PRODUCT + " ORDER BY " + ProductEntry.KEY_CATEGORY +"," + ProductEntry.KEY_NAME;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex(ProductEntry.KEY_ID)));
                product.setName(cursor.getString(cursor.getColumnIndex(ProductEntry.KEY_NAME)));
                product.setPrice(cursor.getInt(cursor.getColumnIndex(ProductEntry.KEY_PRICE)));
                product.setCategory(cursor.getInt(cursor.getColumnIndex(ProductEntry.KEY_CATEGORY)));


                products.add(product);
            } while(cursor.moveToNext());
        }

        return products;
    }

    /**
     *  Update a Product
     */
    public int updateProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(ProductEntry.KEY_NAME, product.getName());
        values.put(ProductEntry.KEY_PRICE, product.getPrice());
        values.put(ProductEntry.KEY_CATEGORY, product.getCategory());


        return this.db.update(ProductEntry.TABLE_PRODUCT, values, ProductEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(product.getId()) });
    }

    /**
     * Delete a product
     */
    public void deleteProduct(long id){
        //delete the product
        this.db.delete(ProductEntry.TABLE_PRODUCT, ProductEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

}
