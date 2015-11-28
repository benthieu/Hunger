package com.example.thieu.hunger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by HugoCastanheiro on 31.10.15.
 */
public class turnovers extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.turnovers);
        Intent i = this.getIntent();
    }

}
