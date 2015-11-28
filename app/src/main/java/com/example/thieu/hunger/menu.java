package com.example.thieu.hunger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.thieu.hunger.db.adapter.UserDataSource;
import com.example.thieu.hunger.db.object.User;

import java.util.List;

public class menu extends Activity {
    private UserDataSource uds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Intent i = this.getIntent();
        // init user data source
        uds = new UserDataSource(this);
        Button btnOrders = (Button) findViewById(R.id.btnOrders);
        Button btnProducts = (Button) findViewById(R.id.btnProducts);
        Button btnTurnovers = (Button) findViewById(R.id.btnDollar);

        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToOrders(v);
            }
        });

        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProducts(v);
            }
        });

        btnTurnovers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTurnovers(v);
            }
        });
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
            for (User u :list) {
                u.setIsLogged(false);
                uds.updateUser(u);
            }
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToOrders(View v) {
        Intent intent = new Intent(this, orders.class);
        startActivity(intent);
    }

    private void goToProducts(View v) {
        Intent intent = new Intent(this, products_view.class);
        startActivity(intent);
    }
    private void goToTurnovers(View v) {
        Intent intent = new Intent(this, turnovers.class);
        startActivity(intent);
    }
}
