package com.example.thieu.hunger;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thieu.hunger.db.adapter.UserDataSource;
import com.example.thieu.hunger.db.object.User;

import java.util.List;

public class user_add_modify extends Activity {
    private int user_id;
    private UserDataSource uds;
    private EditText user_name;
    private EditText user_password;
    private CheckBox user_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_add_modify);
        uds = new UserDataSource(this);

        user_name = (EditText) findViewById(R.id.add_modify_user_name);
        user_password = (EditText) findViewById(R.id.add_modify_user_password);
        user_type = (CheckBox) findViewById(R.id.add_modify_user_is_admin);

        Intent myIntent = getIntent(); // gets the previously created intent
        user_id = myIntent.getIntExtra("user_id", 0); // will return "FirstKeyValue"
        if (user_id != 0) {
            User mod_user = uds.getUserById(user_id);
            user_name.setText(mod_user.getName());
            user_type.setChecked(mod_user.getType());
        }
        Button btn = (Button) findViewById(R.id.add_modify_user_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User activeUser = uds.getLoggedInUser();
                if (activeUser.getId() == user_id){
                    Toast.makeText(user_add_modify.this.getApplicationContext(), getResources().getString(R.string.cant_modify_logged_in_user), Toast.LENGTH_SHORT).show();
                    return;
                }
                String username = user_name.getText().toString();
                String password = user_password.getText().toString();
                boolean type = user_type.isChecked();
                User temp_user = new User();
                temp_user.setName(username);
                temp_user.setPassword(password);
                temp_user.setType(type);
                if (user_id != 0) {
                    temp_user.setId(user_id);
                    uds.updateUser(temp_user);
                } else {
                    uds.createUser(temp_user);
                }
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user_add_modfiy, menu);
        if (user_id == 0){
            getActionBar().setTitle(R.string.title_activity_user_add);
        } else {
            getActionBar().setTitle(R.string.title_activity_user_modify);
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
        if (id == R.id.delete_user_button) {
            if (user_id != 0){
                User active_user = uds.getLoggedInUser();
                if (user_id == active_user.getId()) {
                    Toast.makeText(user_add_modify.this.getApplicationContext(), getResources().getString(R.string.cant_delete_logged_in_user), Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    uds.deleteUser(user_id);
                }
            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
