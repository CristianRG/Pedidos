package com.cristian.controldepedidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.ui.activities.CustomerActivity;
import com.cristian.controldepedidos.ui.activities.OrderActivity;
import com.cristian.controldepedidos.ui.fragments.CustomerFragment;
import com.cristian.controldepedidos.ui.fragments.HistoryFragment;
import com.cristian.controldepedidos.ui.fragments.OrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private static final String NAV_CUSTOMERS_ID = "nav_clientes";
    private static final String NAV_ORDERS_ID = "nav_pedidos";
    private static final String NAV_HISTORY_ID = "nav_historial";
    private DatabaseHelper dbh;
    String currentFrameID = "";
    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbh = new DatabaseHelper(this);

        //Just for testing
        /*
        SQLiteDatabase db = dbh.getWritableDatabase();
        dbh.clearTestData(db);

         */





        if (savedInstanceState == null) {
            loadFragment(new CustomerFragment());
            currentFrameID = NAV_CUSTOMERS_ID;
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragment = null;

                if(item.getItemId() == R.id.nav_clientes){
                    fragment = new CustomerFragment();
                    currentFrameID = NAV_CUSTOMERS_ID;
                } else if (item.getItemId() == R.id.nav_pedidos) {
                    fragment = new OrderFragment();
                    currentFrameID = NAV_ORDERS_ID;
                } else if (item.getItemId() == R.id.nav_historial) {
                    fragment = new HistoryFragment();
                    currentFrameID = NAV_HISTORY_ID;
                }

                loadFragment(fragment);
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            if(currentFrameID.equals(NAV_CUSTOMERS_ID)){
                try {
                    Intent customerActivity = new Intent(this, CustomerActivity.class);
                    startActivity(customerActivity);
                } catch (RuntimeException e){
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            } else if (currentFrameID.equals(NAV_ORDERS_ID)) {
                try {
                    Intent orderActivity = new Intent(this, OrderActivity.class);
                    startActivity(orderActivity);
                } catch (RuntimeException e){
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            } else if (currentFrameID.equals(NAV_HISTORY_ID)) {
                Toast.makeText(MainActivity.this, "HISTORY!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "NO ACTION SELECTED!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_add_order, fragment)
                //.addToBackStack(null)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
