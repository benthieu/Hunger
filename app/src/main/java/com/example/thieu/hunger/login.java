package com.example.thieu.hunger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thieu.hunger.db.adapter.UserDataSource;
import com.example.thieu.hunger.db.object.User;

import java.util.List;

public class login extends Activity {
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
        for (User u :list) {
            u.setIsLogged(false);
            uds.updateUser(u);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text_user = (EditText) findViewById(R.id.editTxtUser);
                EditText text_pass = (EditText) findViewById(R.id.editTxtPW);
                String username = (text_user).getText().toString();
                String password = (text_pass).getText().toString();
                List<User> list = uds.getAllUsers();
                boolean loginSuccess = false;
                for (User u : list) {
                    if (u.getName().toString().equals(username) && u.getPassword().toString().equals(password)) {
                        loginSuccess = true;
                        u.setIsLogged(true);
                        uds.updateUser(u);
                    }
                }
                if (loginSuccess) {
                    text_user.setText("");
                    text_pass.setText("");
                    goToMenuOrOrders(v);
                } else {
                    Toast.makeText(login.this.getApplicationContext(), getResources().getString(R.string.login_failure), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        getActionBar().setTitle(R.string.app_name);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setIcon(R.drawable.main_logo);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<User> list = uds.getAllUsers();
        for (User u :list) {
            u.setIsLogged(false);
            uds.updateUser(u);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.iconInfo) {
            Intent myIntent = new Intent(this, about.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToMenuOrOrders(View v) {
        Intent intent = new Intent(this, menu.class);
        startActivity(intent);
    }
}