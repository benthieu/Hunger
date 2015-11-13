package com.example.thieu.hunger.db.object;

/**
 * Created by HugoCastanheiro on 12.11.15.
 */
public class Connect_Order_Prod {
    private int idOrder;
    private int idProduct;
    private String amount;

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
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
}
