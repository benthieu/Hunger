package com.example.thieu.hunger;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * Created by HugoCastanheiro on 07.11.15.
 */
public class about extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActionBar().setTitle(R.string.action_info);
        getActionBar().setIcon(R.drawable.main_logo);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }
}
