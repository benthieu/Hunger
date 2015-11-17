package com.example.thieu.hunger.db.object;

/**
 * Created by HugoCastanheiro on 07.11.15.
 */
public class Order {
    private int id;
    private int idUser;
    private int numTable;
    private String date;
    private boolean accomplished;

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
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public boolean getAccomplished() {
        return accomplished;
    }
    public void setAccomplished(boolean accomplished) {
        this.accomplished = accomplished;
    }
}
