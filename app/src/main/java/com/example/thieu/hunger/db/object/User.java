package com.example.thieu.hunger.db.object;

/**
 * Created by benjamin on 07.11.2015.
 */
public class User {
    private int id;
    private String name;
    private String password;
    private boolean type;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean getType() {
        return type;
    }
    public void setType(boolean type) {
        this.type = type;
    }
}
