package com.example.thieu.hunger;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.thieu.hunger.db.adapter.UserDataSource;
import com.example.thieu.hunger.db.object.User;

import java.util.List;
import java.util.Locale;

public class menu extends Activity {
    private UserDataSource uds;
    private Button btnOrders;
    private Button btnProducts;
    private Button btnTurnovers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Intent i = this.getIntent();
        // init user data source
        uds = new UserDataSource(this);
        btnOrders = (Button) findViewById(R.id.btnOrders);
        btnProducts = (Button) findViewById(R.id.btnProducts);
        btnTurnovers = (Button) findViewById(R.id.btnDollar);

        User activeUser = uds.getLoggedInUser();
        if (!activeUser.getType()){
            btnTurnovers.setVisibility(View.GONE);
        }

        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOrders();
            }
        });

        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProducts();
            }
        });

        btnTurnovers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTurnovers();
            }
        });
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        changeLanguage(sharedPrefs.getString("pref_lang", "de"));
    }


    public void changeLanguage(String lang){
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        btnOrders.setText(R.string.orders);
        btnProducts.setText(R.string.products);
        btnTurnovers.setText(R.string.turnovers);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        //userManagementButton
        User activeUser = uds.getLoggedInUser();
        if (!activeUser.getType()){
            MenuItem userM = menu.findItem(R.id.userManagementButton);
            userM.setVisible(false);
        }
        getActionBar().setTitle(R.string.app_name);
        getActionBar().setDisplayHomeAsUpEnabled(false);
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
        if (id == R.id.userManagementButton) {
            User activeUser = uds.getLoggedInUser();
            if (activeUser.getType()){
                Intent myIntent = new Intent(this, user_management.class);
                startActivity(myIntent);
                return true;
            }
        }
        if (id == R.id.logoutMenu) {
            List<User> list = uds.getAllUsers();
            for (User u : list) {
                u.setIsLogged(false);
                uds.updateUser(u);
            }
            this.finish();
            return true;
        }

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void goToOrders() {
        Intent intent = new Intent(this, orders_view.class);
        startActivity(intent);
    }
    private void goToProducts() {
        Intent intent = new Intent(this, products_view.class);
        startActivity(intent);
    }
    private void goToTurnovers() {
        Intent intent = new Intent(this, turnovers.class);
        startActivity(intent);
    }
}
