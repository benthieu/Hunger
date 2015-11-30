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
 * Created by HugoCastanheiro on 31.10.15.
 */
public class products_view extends Activity {
    private ProductDataSource pds;
    private LinearLayout mainLayout;
    private boolean wantsCallback;
    private ArrayList<Connect_Order_Prod> product_con_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_view);
        pds = new ProductDataSource(this);
        mainLayout = (LinearLayout) findViewById(R.id.products_view_layout);
        Intent i = this.getIntent();
        wantsCallback = i.getBooleanExtra("wantsCallback",false);
        if (wantsCallback) {
            product_con_list = (ArrayList<Connect_Order_Prod>)i.getSerializableExtra("connect_order_product_list");
        }
        this.recreateView();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        this.recreateView();
    }

    private void recreateView() {
        mainLayout.removeAllViews();

        List<Product> products = pds.getAllProducts();
        LinearLayout[] lines = new LinearLayout[(int)Math.ceil(products.size()/3)+1];
        for (int i = 0 ; i < lines.length; i++) {
            lines[i] = new LinearLayout(this);
            LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            lines[i].setLayoutParams(params);
            lines[i].setOrientation(LinearLayout.HORIZONTAL);
            lines[i].setPadding(5, 10, 5, 0);
        }
        int c = 0;
        for(Product p : products) {
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
            int ident = getResources().getIdentifier("product_cat" + String.valueOf(p.getCategory()), "color", "com.example.thieu.hunger");
            thisButton.setBackgroundColor(Color.parseColor(getResources().getString(ident)));
            thisButton.setText(p.getName());
            thisButton.setPadding(0, 30, 0, 30);
            thisButton.setOnClickListener(new ProductClick());
            lines[(int)Math.ceil(c/3)].addView(thisButton);
            c++;
        }
        for (int i = 0 ; i < lines.length; i++) {
            mainLayout.addView(lines[i]);
        }
        mainLayout.invalidate();
    }
    private class ProductClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ProductButton thisButton = (ProductButton)v;
            int thisProduct = thisButton.getProduct_id();
            if (wantsCallback) {
                int c = 0;
                int pos = -1;
                for(Connect_Order_Prod cod : product_con_list) {
                    if (cod.getIdProduct() == thisProduct) {
                        pos = c;
                    }
                    c++;
                }
                if (pos == -1) {
                    Connect_Order_Prod tempcon = new Connect_Order_Prod();
                    tempcon.setAmount(1);
                    tempcon.setIdProduct(thisProduct);
                    product_con_list.add(tempcon);
                } else {
                    product_con_list.get(pos).setAmount(product_con_list.get(pos).getAmount()+1);
                }
                Toast.makeText(products_view.this.getApplicationContext(), getResources().getString(R.string.product_added), Toast.LENGTH_SHORT).show();
            } else {
                Intent myIntent = new Intent(products_view.this, product_add_modify.class);
                myIntent.putExtra("product_id", thisProduct);
                startActivity(myIntent);
            }
        }
    }
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_products_view, menu);
        if (wantsCallback) {
            MenuItem userM = menu.findItem(R.id.add_product_1);
            userM.setVisible(false);
        } else {
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

    private int mod(int x, int y)
    {
        int result = x % y;
        if (result < 0)
            result += y;
        return result;
    }
}
