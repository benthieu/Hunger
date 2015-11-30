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
import com.example.thieu.hunger.db.object.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by HugoCastanheiro on 31.10.15.
 */
public class turnovers extends Activity {
    private OrderDataSource ods;
    private Connect_Order_Product_DataSource cds;
    private ProductDataSource pds;
    private UserDataSource uds;
    private ListView list;
    private Button day_button;
    private Button month_button;
    private Button year_button;
    private List<User> users;
    private int list_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.turnovers);
        list_type = 0;
        ods = new OrderDataSource(this);
        cds = new Connect_Order_Product_DataSource(this);
        pds = new ProductDataSource(this);
        uds = new UserDataSource(this);
        users = uds.getAllUsers();
        //Calendar set to the current date
        Calendar calendarday=Calendar.getInstance();
        calendarday.add(Calendar.DAY_OF_YEAR, -1);
        ArrayList<Order> ordersday = ods.getAllOrdersNewerThan((int) (calendarday.getTimeInMillis() / 1000));
        Calendar calendarmonth=Calendar.getInstance();
        calendarmonth.add(Calendar.DAY_OF_YEAR, -30);
        ArrayList<Order> ordersmonth = ods.getAllOrdersNewerThan((int) (calendarmonth.getTimeInMillis() / 1000));
        Calendar calendaryear=Calendar.getInstance();
        calendaryear.add(Calendar.DAY_OF_YEAR, -360);
        ArrayList<Order> ordersyear = ods.getAllOrdersNewerThan((int) (calendaryear.getTimeInMillis() / 1000));
        int dayval = 0;
        for (Order o : ordersday) {
            int count = 0;
            int userpos = -1;
            for (User u : users) {
                if (u.getId() == o.getIdUser()) {
                    userpos = count;
                }
                count++;
            }
            ArrayList<Connect_Order_Prod> c_list = cds.getAllConnectionsByOrderId(o.getId());
            for (Connect_Order_Prod c : c_list) {
                Product thisProd = pds.getProductById(c.getIdProduct());
                dayval += c.getAmount()*thisProd.getPrice();
                if (userpos != -1) {
                    users.get(userpos).val_day += c.getAmount()*thisProd.getPrice();
                }
            }
        }
        day_button = (Button) findViewById(R.id.can_today);
        day_button.setText(String.format("%.2f", dayval / 100.00)+ " " + getResources().getString(R.string.currency));
        day_button.setOnClickListener(new turnClick());

        int monthval = 0;
        for (Order o : ordersmonth) {
            int count = 0;
            int userpos = -1;
            for (User u : users) {
                if (u.getId() == o.getIdUser()) {
                    userpos = count;
                }
                count++;
            }
            ArrayList<Connect_Order_Prod> c_list = cds.getAllConnectionsByOrderId(o.getId());
            for (Connect_Order_Prod c : c_list) {
                Product thisProd = pds.getProductById(c.getIdProduct());
                monthval += c.getAmount()*thisProd.getPrice();
                if (userpos != -1) {
                    users.get(userpos).val_month += c.getAmount()*thisProd.getPrice();
                }
            }
        }
        month_button = (Button) findViewById(R.id.can_month);
        month_button.setText(String.format("%.2f", monthval/100.00)+" "+getResources().getString(R.string.currency));
        month_button.setOnClickListener(new turnClick());

        int yearval = 0;
        for (Order o : ordersyear) {
            int count = 0;
            int userpos = -1;
            for (User u : users) {
                if (u.getId() == o.getIdUser()) {
                    userpos = count;
                }
                count++;
            }
            ArrayList<Connect_Order_Prod> c_list = cds.getAllConnectionsByOrderId(o.getId());
            for (Connect_Order_Prod c : c_list) {
                Product thisProd = pds.getProductById(c.getIdProduct());
                yearval += c.getAmount()*thisProd.getPrice();
                if (userpos != -1) {
                    users.get(userpos).val_year += c.getAmount()*thisProd.getPrice();
                }
            }
        }
        year_button = (Button) findViewById(R.id.can_year);
        year_button.setText(String.format("%.2f", yearval/100.00)+" "+getResources().getString(R.string.currency));
        year_button.setOnClickListener(new turnClick());

        list = (ListView) findViewById(R.id.listview_turnovers);
        list.setAdapter(this.recreateList());
    }

    public class turnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.can_today:
                    list_type = 0;
                    break;
                case R.id.can_month:
                    list_type = 1;
                    break;
                case R.id.can_year:
                    list_type = 2;
                    break;
            }
            list.setAdapter(recreateList());
        }
    }

    public ArrayAdapter<String> recreateList() {
        final List<Order> orders = ods.getAllOrders();
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, R.layout.turnover_list_elem, new String[users.size()]){

            // Call for every entry in the ArrayAdapter
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                //If View doesn't exist create a new view
                if (convertView == null) {
                    // Create the Layout
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.turnover_list_elem, parent, false);
                } else {
                    view = convertView;
                }
                //Add Text to the layout
                TextView textView1 = (TextView) view.findViewById(R.id.listview_turnover_user);
                textView1.setText(users.get(position).getName());

                TextView textView2 = (TextView) view.findViewById(R.id.listview_turnover_amount);
                int amount = 0;
                if (list_type == 0) {
                    amount = users.get(position).val_day;
                } else if (list_type == 1) {
                    amount = users.get(position).val_month;
                } else if (list_type == 2) {
                    amount = users.get(position).val_year;
                }
                textView2.setText(String.format("%.2f", amount/100.00)+getResources().getString(R.string.currency));
                return view;
            }
        };
        return adapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActionBar().setTitle(R.string.turnovers_title);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.main_logo);
        return true;
    }

}
