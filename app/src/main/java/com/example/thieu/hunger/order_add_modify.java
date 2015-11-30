package com.example.thieu.hunger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thieu.hunger.db.adapter.Connect_Order_Product_DataSource;
import com.example.thieu.hunger.db.adapter.OrderDataSource;
import com.example.thieu.hunger.db.adapter.ProductDataSource;
import com.example.thieu.hunger.db.adapter.UserDataSource;
import com.example.thieu.hunger.db.object.Connect_Order_Prod;
import com.example.thieu.hunger.db.object.Order;
import com.example.thieu.hunger.db.object.Product;

import java.util.ArrayList;

/**
 * Created by HugoCastanheiro on 31.10.15.
 */
public class order_add_modify extends Activity {
    private int order_id;
    private OrderDataSource ods;
    private ProductDataSource pds;
    private Connect_Order_Product_DataSource cds;
    private UserDataSource uds;
    private TextView table_select_view;
    private Button order_table_1;
    private Button order_table_2;
    private Button order_table_3;
    private Button order_table_4;
    private Button order_table_5;
    private Button order_table_6;
    private Button order_table_7;
    private Button order_table_8;

    private Button add_product;
    private ListView order_product_view;
    private ArrayList<Connect_Order_Prod> temp_con;
    private int temp_table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_add_modify);
        ods = new OrderDataSource(this);
        cds = new Connect_Order_Product_DataSource(this);
        uds = new UserDataSource(this);
        pds = new ProductDataSource(this);
        table_select_view = (TextView) findViewById(R.id.order_add_modify_table_select);
        order_table_1 = (Button) findViewById(R.id.order_table_1);
        order_table_1.setOnClickListener(new TableClick());
        order_table_2 = (Button) findViewById(R.id.order_table_2);
        order_table_2.setOnClickListener(new TableClick());
        order_table_3 = (Button) findViewById(R.id.order_table_3);
        order_table_3.setOnClickListener(new TableClick());
        order_table_4 = (Button) findViewById(R.id.order_table_4);
        order_table_4.setOnClickListener(new TableClick());
        order_table_5 = (Button) findViewById(R.id.order_table_5);
        order_table_5.setOnClickListener(new TableClick());
        order_table_6 = (Button) findViewById(R.id.order_table_6);
        order_table_6.setOnClickListener(new TableClick());
        order_table_7 = (Button) findViewById(R.id.order_table_7);
        order_table_7.setOnClickListener(new TableClick());
        order_table_8 = (Button) findViewById(R.id.order_table_8);
        order_table_8.setOnClickListener(new TableClick());

        add_product = (Button) findViewById(R.id.order_add_product);
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(order_add_modify.this,products_view.class);
                i.putExtra("wantsCallback", true);
                i.putExtra("connect_order_product_list", temp_con);
                startActivityForResult(i, 1);
            }
        });

        Intent myIntent = getIntent(); // gets the previously created intent
        order_id = myIntent.getIntExtra("order_id", 0); // will return "FirstKeyValue"
        if (order_id != 0) {
            Order mod_order = ods.getOrderById(order_id);
            setActiveTable(mod_order.getNumTable());
            temp_con = cds.getAllConnectionsByOrderId(order_id);
        } else {
            temp_con = new ArrayList<Connect_Order_Prod>();
        }

        order_product_view = (ListView) findViewById(R.id.order_product_view);
        order_product_view.setAdapter(this.createList());

        order_product_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Connect_Order_Prod temp_prod_con = temp_con.get(position);
                temp_prod_con.setAmount(temp_prod_con.getAmount() - 1);
                if (temp_prod_con.getAmount() <= 0) {
                    temp_con.remove(position);
                }
                order_product_view.setAdapter(order_add_modify.this.createList());
            }
        });
    }

    private class TableClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Button b = (Button)v;
            int buttonText = Integer.parseInt(b.getText().toString());
            setActiveTable(buttonText);
        }
    }

    private void setActiveTable(int table) {
        table_select_view.setText(getResources().getString(R.string.selected_table) + " " + String.valueOf(table));
        temp_table = table;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    temp_con = (ArrayList<Connect_Order_Prod>)data.getSerializableExtra("connect_order_product_list");
                    order_product_view.setAdapter(this.createList());
                }
                break;
            }
        }
    }


    public ArrayAdapter<String> createList() {
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, R.layout.order_prod_list_elem, new String[temp_con.size()]){

            // Call for every entry in the ArrayAdapter
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                //If View doesn't exist create a new view
                if (convertView == null) {
                    // Create the Layout
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.order_prod_list_elem, parent, false);
                } else {
                    view = convertView;
                }
                Product list_prod = pds.getProductById(temp_con.get(position).getIdProduct());
                //Add Text to the layout
                TextView textView1 = (TextView) view.findViewById(R.id.listview_product_name);
                textView1.setText(list_prod.getName().toString());

                TextView textView2 = (TextView) view.findViewById(R.id.listview_product_amount);
                textView2.setText(String.valueOf(temp_con.get(position).getAmount())+"x");

                return view;
            }
        };
        return adapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_order_add_modfiy, menu);
        if (order_id == 0){
            getActionBar().setTitle(R.string.title_activity_order_add);
        } else {
            getActionBar().setTitle(R.string.title_activity_order_modify);
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
        if (id == R.id.delete_order_button) {
            if (order_id != 0){
                ods.deleteOrder(order_id);
            }
            finish();
            return true;
        }
        if (id == R.id.add_order_button) {
            Long tsLong = System.currentTimeMillis()/1000;
            Order save_order = new Order();
            save_order.setDate(tsLong.intValue());
            save_order.setNumTable(temp_table);
            save_order.setIdUser(uds.getLoggedInUser().getId());
            if (order_id == 0) {
                order_id = (int)ods.createOrder(save_order);
            } else {
                save_order.setId(order_id);
                ods.updateOrder(save_order);
            }
            cds.deleteConnectionByOrderId(order_id);
            for (Connect_Order_Prod c : temp_con) {
                c.setIdOrder(order_id);
                cds.createConnection(c);
            }
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
