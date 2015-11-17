package com.example.thieu.hunger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.thieu.hunger.db.adapter.UserDataSource;
import com.example.thieu.hunger.db.object.User;

import java.util.List;

public class login extends AppCompatActivity {
    private UserDataSource uds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button btn = (Button) findViewById(R.id.loginBTN);

        uds = new UserDataSource(this);
        List<User> list = uds.getAllUsers();
        if (list.size() == 0) {
            User defaultUser = new User();
            defaultUser.setPassword("admin");
            defaultUser.setName("admin");
            defaultUser.setType(true);
            uds.createUser(defaultUser);
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<User> list = uds.getAllUsers();
                for (User u :list) {

                }
                goToMenuOrOrders(v);
            }
        });
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    }*/



    private void goToMenuOrOrders(View v) {
        Intent intent = new Intent(this, menu.class);
        startActivity(intent);
    }
}