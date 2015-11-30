package com.example.thieu.hunger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thieu.hunger.db.adapter.Connect_Order_Product_DataSource;
import com.example.thieu.hunger.db.adapter.OrderDataSource;
import com.example.thieu.hunger.db.adapter.ProductDataSource;
import com.example.thieu.hunger.db.adapter.UserDataSource;
import com.example.thieu.hunger.db.object.Connect_Order_Prod;
import com.example.thieu.hunger.db.object.Order;
import com.example.thieu.hunger.db.object.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HugoCastanheiro on 31.10.15.
 */
public class orders_view extends Activity {
    private OrderDataSource ods;
    private Connect_Order_Product_DataSource cds;
    private ProductDataSource pds;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_view);

        ods = new OrderDataSource(this);
        cds = new Connect_Order_Product_DataSource(this);
        pds = new ProductDataSource(this);

        list = (ListView) findViewById(R.id.listview_orders);
        list.setAdapter(this.recreateList());

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent myIntent = new Intent(orders_view.this, order_add_modify.class);
                myIntent.putExtra("order_id", ods.getAllOrders().get(position).getId());
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        list.setAdapter(this.recreateList());
    }

    public ArrayAdapter<String> recreateList() {
        final List<Order> orders = ods.getAllOrders();
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, R.layout.order_list_elem, new String[orders.size()]){

            // Call for every entry in the ArrayAdapter
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                //If View doesn't exist create a new view
                if (convertView == null) {
                    // Create the Layout
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.order_list_elem, parent, false);
                } else {
                    view = convertView;
                }
                Order thisOrder = orders.get(position);
                //Add Text to the layout
                TextView textView1 = (TextView) view.findViewById(R.id.listview_order_date);
                textView1.setText(getDate(thisOrder.getDate()));

                TextView textView2 = (TextView) view.findViewById(R.id.listview_order_table);
                textView2.setText(getResources().getText(R.string.order_view_table)+ " " + thisOrder.getNumTable());

                ArrayList<Connect_Order_Prod> con_list = cds.getAllConnectionsByOrderId(thisOrder.getId());
                int total = 0;
                for (Connect_Order_Prod cl : con_list) {
                    total += cl.getAmount()*pds.getProductById(cl.getIdProduct()).getPrice();
                }
                TextView textView3 = (TextView) view.findViewById(R.id.listview_order_amount);
                textView3.setText(String.format("%.2f", total/100.00)+getResources().getString(R.string.currency));

                return view;
            }
        };
        return adapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_order_management, menu);
        getActionBar().setTitle(R.string.order_management);
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
        if (id == R.id.add_order) {
            Intent myIntent = new Intent(this, order_add_modify.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getDate(long timeStamp){
        try{
            DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date netDate = (new Date(timeStamp*1000));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }
}
