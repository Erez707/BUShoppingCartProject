package com.example.bushoppingcartproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bushoppingcartproject.StoreShelf.Shelf;
import com.example.bushoppingcartproject.UserData.DisplayName;
import com.google.android.material.navigation.NavigationView;

public class ShoppingActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private StoreFragment Store = new StoreFragment();  // should I create an instance for the cart and orders fragments?? and an instance doesn't change my issue!!
    private Shelf createStoreShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        // populate store shelf with all items to the database
        createStoreShelf = new Shelf();
        createStoreShelf.createStoreCatalog();

        FragmentManager.enableDebugLogging(true);

        //this sets the toolbar as the actionbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        //ensures that the app stays in the same fragment if phone is rotated
        if (savedInstanceState == null) {
            // opens the store fragment initially
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Store).commit();
            navigationView.setCheckedItem(R.id.nav_store);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_store:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Store).commit();
                        break;
                    case R.id.nav_cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
                        break;
                    case R.id.nav_orders:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OrdersFragment()).commit();
                        break;
                    case R.id.nav_logout:
                        Intent logoutIntent = new Intent(ShoppingActivity.this, MainActivity.class);
                        startActivity(logoutIntent);
                        Toast.makeText(ShoppingActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // toggle open and close toolbar menu
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //update displayed username based on the logged in user
        View headerView = navigationView.getHeaderView(0);
        TextView profileName = headerView.findViewById(R.id.profileName);
        profileName.setText(DisplayName.currentUser.username);

    }

    // close the menu after option is selected
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}

