package com.example.thieu.hunger.db.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.thieu.hunger.db.HungerContract.UserEntry;
import com.example.thieu.hunger.db.SQLiteHelperClass;
import com.example.thieu.hunger.db.object.User;

import java.util.ArrayList;
import java.util.List;

public class UserDataSource {
    private SQLiteDatabase db;
    private Context context;

    public UserDataSource(Context context){
        SQLiteHelperClass sqliteHelper = SQLiteHelperClass.getInstance(context);
        // get database
        db = sqliteHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     * Insert a new user
     */
    public long createUser(User user){
        long id;
        ContentValues values = new ContentValues();
        values.put(UserEntry.KEY_NAME, user.getName());
        values.put(UserEntry.KEY_PASSWORD, user.getPassword());
        values.put(UserEntry.KEY_TYPE, user.getType() ? 1 : 0);
        values.put(UserEntry.KEY_ISLOGGED, user.isLogged() ? 1 : 0);

        id = this.db.insert(UserEntry.TABLE_USER, null, values);
        return id;
    }

    /**
     * Find active user
     */
    public User getLoggedInUser(){
        String sql = "SELECT * FROM " + UserEntry.TABLE_USER +
                " WHERE " + UserEntry.KEY_ISLOGGED + " = 1";

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        User user = new User();
        user.setId(cursor.getInt(cursor.getColumnIndex(UserEntry.KEY_ID)));
        user.setName(cursor.getString(cursor.getColumnIndex(UserEntry.KEY_NAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(UserEntry.KEY_PASSWORD)));
        user.setType(cursor.getInt(cursor.getColumnIndex(UserEntry.KEY_TYPE)) != 0);
        user.setIsLogged(cursor.getInt(cursor.getColumnIndex(UserEntry.KEY_ISLOGGED)) != 0);

        return user;
    }

    /**
     * Find one user by Id
     */
    public User getUserById(long id){
        String sql = "SELECT * FROM " + UserEntry.TABLE_USER +
                " WHERE " + UserEntry.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        User user = new User();
        user.setId(cursor.getInt(cursor.getColumnIndex(UserEntry.KEY_ID)));
        user.setName(cursor.getString(cursor.getColumnIndex(UserEntry.KEY_NAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(UserEntry.KEY_PASSWORD)));
        user.setType(cursor.getInt(cursor.getColumnIndex(UserEntry.KEY_TYPE)) != 0);
        user.setIsLogged(cursor.getInt(cursor.getColumnIndex(UserEntry.KEY_ISLOGGED)) != 0);

        return user;
    }

    /**
     * Get all Persons
     */
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<User>();
        String sql = "SELECT * FROM " + UserEntry.TABLE_USER + " ORDER BY " + UserEntry.KEY_ID;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(UserEntry.KEY_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(UserEntry.KEY_NAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(UserEntry.KEY_PASSWORD)));
                user.setType(cursor.getInt(cursor.getColumnIndex(UserEntry.KEY_TYPE)) != 0);
                user.setIsLogged(cursor.getInt(cursor.getColumnIndex(UserEntry.KEY_ISLOGGED)) != 0);

                users.add(user);
            } while(cursor.moveToNext());
        }

        return users;
    }

    /**
     *  Update a user
     */
    public int updateUser(User user){
        ContentValues values = new ContentValues();
        values.put(UserEntry.KEY_NAME, user.getName());
        values.put(UserEntry.KEY_PASSWORD, user.getPassword());
        values.put(UserEntry.KEY_TYPE, user.getType());
        values.put(UserEntry.KEY_ISLOGGED, user.isLogged());

        return this.db.update(UserEntry.TABLE_USER, values, UserEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }

    /**
     * Delete a user
     */
    public void deleteUser(long id){
        //delete the person
        this.db.delete(UserEntry.TABLE_USER, UserEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
}
