package com.example.thieu.hunger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Intent i = this.getIntent();

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
