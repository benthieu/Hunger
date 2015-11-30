package com.example.thieu.hunger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.example.thieu.hunger.db.object.Connect_Order_Prod;
import com.example.thieu.hunger.db.adapter.ProductDataSource;
import com.example.thieu.hunger.db.object.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Products view
 * Shows all existing products as button list
 * Buttons align automatically in lines of 3
 * Buttons are created dinamically
 */
public class products_view extends Activity {
    // init product data source
    private ProductDataSource pds;
    private LinearLayout mainLayout;
    private boolean wantsCallback;
    private ArrayList<Connect_Order_Prod> product_con_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_view);
        pds = new ProductDataSource(this);
        // link mainLayout
        mainLayout = (LinearLayout) findViewById(R.id.products_view_layout);
        Intent i = this.getIntent();
        // determine if other view (caller) wants a callback
        wantsCallback = i.getBooleanExtra("wantsCallback",false);
        if (wantsCallback) {
            // if so, get the product connection list
            product_con_list = (ArrayList<Connect_Order_Prod>)i.getSerializableExtra("connect_order_product_list");
        }
        this.recreateView();
    }

    @Override
    public void onResume() {
        super.onResume();
        // recreate view, possible that products changed
        this.recreateView();
    }

    /**
     * function to dinamically recreate view
     */
    private void recreateView() {
        // remove all layouts from existing view
        mainLayout.removeAllViews();
        // get all products
        List<Product> products = pds.getAllProducts();
        // create array of layouts (every line has 3 products)
        LinearLayout[] lines = new LinearLayout[(int)Math.ceil(products.size()/3)+1];
        for (int i = 0 ; i < lines.length; i++) {
            // set line layout
            lines[i] = new LinearLayout(this);
            LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            lines[i].setLayoutParams(params);
            lines[i].setOrientation(LinearLayout.HORIZONTAL);
            lines[i].setPadding(5, 10, 5, 0);
        }
        int c = 0;
        // create all products buttons
        for(Product p : products) {
            // set button
            ProductButton thisButton = new ProductButton(this);
            thisButton.setProduct_id(p.getId());
            LayoutParams params = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );
            params.setMargins(5, 0, 5, 0);
            params.weight = 3;
            thisButton.setLayoutParams(params);
            thisButton.setHeight(60);
            // get category color
            int ident = getResources().getIdentifier("product_cat" + String.valueOf(p.getCategory()), "color", "com.example.thieu.hunger");
            thisButton.setBackgroundColor(Color.parseColor(getResources().getString(ident)));
            // set button text
            thisButton.setText(p.getName());
            thisButton.setPadding(0, 30, 0, 30);
            // handle button click
            thisButton.setOnClickListener(new ProductClick());
            // add to specific line
            lines[(int)Math.ceil(c/3)].addView(thisButton);
            c++;
        }
        // add lines to layout
        for (int i = 0 ; i < lines.length; i++) {
            mainLayout.addView(lines[i]);
        }
        mainLayout.invalidate();
    }
    private class ProductClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // handle onclick
            ProductButton thisButton = (ProductButton)v;
            int thisProduct = thisButton.getProduct_id();
            // if the user wants a callback (means we are in selection mode)
            if (wantsCallback) {
                int c = 0;
                int pos = -1;
                // determine product position in arraylist
                for(Connect_Order_Prod cod : product_con_list) {
                    if (cod.getIdProduct() == thisProduct) {
                        pos = c;
                    }
                    c++;
                }
                if (pos == -1) {
                    // if the product doesnt already exist in array we create a new connection
                    Connect_Order_Prod tempcon = new Connect_Order_Prod();
                    // amount 1 because its a new product
                    tempcon.setAmount(1);
                    tempcon.setIdProduct(thisProduct);
                    product_con_list.add(tempcon);
                } else {
                    // product already in list, so we add 1 to the amount
                    product_con_list.get(pos).setAmount(product_con_list.get(pos).getAmount()+1);
                }
                // show text that product has been added
                Toast.makeText(products_view.this.getApplicationContext(), getResources().getString(R.string.product_added), Toast.LENGTH_SHORT).show();
            } else {
                // we are in edit/add mode
                // so we show a product edit view
                Intent myIntent = new Intent(products_view.this, product_add_modify.class);
                // product_id as parameter
                myIntent.putExtra("product_id", thisProduct);
                startActivity(myIntent);
            }
        }
    }

    /**
     * ProductButton class
     * to determine product id
     */
    private class ProductButton extends Button {
        private int product_id;
        public ProductButton(Context context) {
            super(context);
        }
        public int getProduct_id() {
            return product_id;
        }
        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // create menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_products_view, menu);
        if (wantsCallback) {
            // user wants callback
            // so we hide product add button
            MenuItem userM = menu.findItem(R.id.add_product_1);
            userM.setVisible(false);
        } else {
            // user is in edit/add mode
            // so we hide selection button
            MenuItem userM = menu.findItem(R.id.set_callback);
            userM.setVisible(false);
        }
        getActionBar().setTitle(R.string.products_view);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.main_logo);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_product_1) {
            Intent myIntent = new Intent(this, product_add_modify.class);
            startActivity(myIntent);
            return true;
        }

        if (id == R.id.set_callback) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("connect_order_product_list", product_con_list);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * modulo function
     * java cant handle f*** modulo
     */
    private int mod(int x, int y)
    {
        int result = x % y;
        if (result < 0)
            result += y;
        return result;
    }
}
