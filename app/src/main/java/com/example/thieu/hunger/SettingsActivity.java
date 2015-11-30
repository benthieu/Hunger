package com.example.thieu.hunger;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.thieu.hunger.db.object.User;


public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction()
                .replace(android.R.id.content,new SettingsFragment())
                .commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getActionBar().setIcon(R.drawable.main_logo);
		return true;
	}
}
