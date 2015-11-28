package com.example.thieu.hunger;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thieu.hunger.db.adapter.ProductDataSource;
import com.example.thieu.hunger.db.adapter.UserDataSource;
import com.example.thieu.hunger.db.object.Product;
import com.example.thieu.hunger.db.object.User;

public class product_add_modify extends Activity {
    private int product_id;
    private ProductDataSource pds;
    private EditText product_name;
    private EditText product_price;
    private Spinner product_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_add_modify);
        pds = new ProductDataSource(this);

        product_name = (EditText) findViewById(R.id.add_modify_product_name);
        product_price = (EditText) findViewById(R.id.add_modify_product_price);
        product_category = (Spinner) findViewById(R.id.add_modify_product_category);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_category.setAdapter(adapter);

        Intent myIntent = getIntent(); // gets the previously created intent
        product_id = myIntent.getIntExtra("product_id", 0); // will return "FirstKeyValue"
        if (product_id != 0) {
            Product mod_product = pds.getProductById(product_id);
            product_name.setText(mod_product.getName());
            product_price.setText(String.valueOf(mod_product.getPrice()/100.00));
            product_category.setSelection(mod_product.getCategory());
        }
        Button btn = (Button) findViewById(R.id.add_modify_product_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = product_name.getText().toString();
                int price = (int)Math.round(Float.valueOf(product_price.getText().toString())*100.00);
                int category = product_category.getSelectedItemPosition();

                Product temp_product = new Product();
                temp_product.setName(name);
                temp_product.setPrice(price);
                temp_product.setCategory(category);

                if (product_id != 0) {
                    temp_product.setId(product_id);
                    pds.updateProduct(temp_product);
                } else {
                    pds.createProduct(temp_product);
                }
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_add_modfiy, menu);
        if (product_id == 0){
            getActionBar().setTitle(R.string.title_activity_product_add);
        } else {
            getActionBar().setTitle(R.string.title_activity_product_modify);
        }
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
        if (id == R.id.delete_product_button) {
            if (product_id != 0){
                pds.deleteProduct(product_id);
            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
