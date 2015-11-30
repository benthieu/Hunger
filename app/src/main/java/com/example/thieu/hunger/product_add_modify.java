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

/**
 * View to edit/add/delete product
 */
public class product_add_modify extends Activity {
    private int product_id;
    // init product data source
    private ProductDataSource pds;
    private EditText product_name;
    private EditText product_price;
    private Spinner product_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_add_modify);
        pds = new ProductDataSource(this);

        // get input fields
        product_name = (EditText) findViewById(R.id.add_modify_product_name);
        product_price = (EditText) findViewById(R.id.add_modify_product_price);
        product_category = (Spinner) findViewById(R.id.add_modify_product_category);

        // set category spinner adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_category.setAdapter(adapter);

        Intent myIntent = getIntent(); // gets the previously created intent
        // get product id from parameter
        product_id = myIntent.getIntExtra("product_id", 0);
        if (product_id != 0) {
            // we are in edit mode
            // get product from database
            Product mod_product = pds.getProductById(product_id);
            // set product name
            product_name.setText(mod_product.getName());
            // set product text
            product_price.setText(String.valueOf(mod_product.getPrice()/100.00));
            // set category
            product_category.setSelection(mod_product.getCategory());
        }
        Button btn = (Button) findViewById(R.id.add_modify_product_save);
        // trigger save button click
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get name,price and category from view
                String name = product_name.getText().toString();
                int price = (int)Math.round(Float.valueOf(product_price.getText().toString())*100.00);
                int category = product_category.getSelectedItemPosition();
                // set to product
                Product temp_product = new Product();
                temp_product.setName(name);
                temp_product.setPrice(price);
                temp_product.setCategory(category);

                if (product_id != 0) {
                    // edit mode, so update product
                    temp_product.setId(product_id);
                    pds.updateProduct(temp_product);
                } else {
                    // add mode, so insert product
                    pds.createProduct(temp_product);
                }
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // create product
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_add_modfiy, menu);
        if (product_id == 0){
            // set activity add title
            getActionBar().setTitle(R.string.title_activity_product_add);
        } else {
            // set activity edit title
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
