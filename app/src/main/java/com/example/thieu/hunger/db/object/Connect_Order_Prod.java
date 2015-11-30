package com.example.thieu.hunger.db.object;

import java.io.Serializable;

/**
 * Created by HugoCastanheiro on 12.11.15.
 */
public class Connect_Order_Prod implements Serializable {
    private int idOrder;
    private int idProduct;
    private int amount;

    public int getIdOrder() {
        return idOrder;
    }
    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }
    public int getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
