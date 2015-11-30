package com.example.thieu.hunger.db.object;

import java.util.ArrayList;

/**
 * Created by HugoCastanheiro on 07.11.15.
 */
public class Order {
    private int id;
    private int idUser;
    private int numTable;
    private int date;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
    public int getNumTable() {
        return numTable;
    }
    public void setNumTable(int numTable) {
        this.numTable = numTable;
    }
    public int getDate() {
        return date;
    }
    public void setDate(int date) {
        this.date = date;
    }
}
