package com.example.thieu.hunger;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
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
import android.widget.Toast;

import com.example.thieu.hunger.db.adapter.UserDataSource;
import com.example.thieu.hunger.db.object.User;

import java.util.List;

/**
 * view to manage users
 */
public class user_management extends Activity {
    private UserDataSource uds;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_management);
        // init user data source
        uds = new UserDataSource(this);

        list = (ListView) findViewById(R.id.userManagementList);
        // set list adapter
        list.setAdapter(this.recreateList());

        // trigger user list click
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // open user modify view
                Intent myIntent = new Intent(user_management.this, user_add_modify.class);
                // pass user id
                myIntent.putExtra("user_id", uds.getAllUsers().get(position).getId());
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // on resume recreate list (data could have been changed)
        list.setAdapter(this.recreateList());
    }

    public ArrayAdapter<String> recreateList() {
        // get all users from database
        List<User> users = uds.getAllUsers();
        // write in array
        final String [] users_arr = new String[users.size()];
        int c = 0;
        for (User u :users) {
            users_arr[c] = u.getName();
            c++;
        }
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, R.layout.user_list_elem, users_arr){
            // Call for every entry in the ArrayAdapter
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                //If View doesn't exist create a new view
                if (convertView == null) {
                    // Create the Layout
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.user_list_elem, parent, false);
                } else {
                    view = convertView;
                }
                // set username to view
                TextView textView1 = (TextView) view.findViewById(R.id.listview_user);
                textView1.setText(users_arr[position]);
                return view;
            }
        };
        return adapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // create menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user_management, menu);
        getActionBar().setTitle(R.string.user_management);
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
        if (id == R.id.add_user) {
            // we want to add a new user
            // start user add activity
            Intent myIntent = new Intent(this, user_add_modify.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
