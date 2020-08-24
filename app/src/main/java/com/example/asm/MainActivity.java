package com.example.asm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.asm.Fragment.Bill_Fragment;
import com.example.asm.Fragment.Cart_Fragment;
import com.example.asm.Fragment.Home_Fragment;
import com.example.asm.Fragment.Motor_Fragment;
import com.example.asm.Fragment.Profile_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    TextView tvNameUS, tvEmailUS;
    NavigationView navigationView;
    String usname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.nav_view);

//        tvNameUS = findViewById(R.id.tvNameUser);
//        String fullname = getIntent().getStringExtra("fullname").toString();
//        tvNameUS.setText(fullname);
        header();
        drawerLayout = findViewById(R.id.activity_main);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        openFragment(Home_Fragment.newInstance("", ""));


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.navi_home:
                openFragment(Home_Fragment.newInstance("", ""));
                break;
            case R.id.navi_product:
                openFragment(Motor_Fragment.newInstance("", ""));
                break;
            case R.id.navi_bill:
                openFragment(Bill_Fragment.newInstance("", ""));
                break;
            case R.id.navi_cart:
                openFragment(Cart_Fragment.newInstance("", ""));
                break;
            case R.id.navi_user:
                openFragment(Profile_Fragment.newInstance("", ""));
                break;
            case R.id.navi_logout:
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void header() {
        View headerview = navigationView.getHeaderView(0);
        initHeader(headerview);
        Intent intent = getIntent();
        usname = intent.getStringExtra("fullname");
        tvNameUS.setText(usname);
    }

    private void initHeader(View headerView) {
        headerView.findViewById(R.id.header);
        tvNameUS = headerView.findViewById(R.id.tvNameUser);
    }
}