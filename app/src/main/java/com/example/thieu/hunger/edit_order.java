package com.example.thieu.hunger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by HugoCastanheiro on 31.10.15.
 */
public class edit_order extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_order);
        Intent i = this.getIntent();
    }

}
