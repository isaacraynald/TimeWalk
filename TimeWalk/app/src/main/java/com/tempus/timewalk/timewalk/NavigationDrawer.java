package com.tempus.timewalk.timewalk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class NavigationDrawer extends AppCompatActivity {

    public static final String TAG_HOME = "Home Page";
    public static final String TAG_RECOMMENDED = "Recommended";
    public static final String TAG_CUSTOMIZED = "Customized";
    public static final String TAG_FAVOURITES = "Favourites";
    public static final String TAG_SPOTS = "Spots";
    public static String CURRENT_TAG = TAG_HOME;
    public static int index = 0;
    boolean loadHomeOnBackPressed = true;

    private FloatingActionButton fab;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private String title;
    private Handler handler;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handler = new Handler();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        setUpNavigationView();

        sharedPreferences = getSharedPreferences("Key", MODE_PRIVATE);
        index = sharedPreferences.getInt("index", 0);
        CURRENT_TAG = sharedPreferences.getString("TAG", CURRENT_TAG);
        title = sharedPreferences.getString("TAG", CURRENT_TAG);
        loadFragment();


    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    CURRENT_TAG = TAG_HOME;
                    index = 0;
                    title = TAG_HOME;

                } else if (id == R.id.nav_recommended) {
                    CURRENT_TAG = TAG_RECOMMENDED;
                    index = 1;
                    title = TAG_RECOMMENDED;

                } else if (id == R.id.nav_customized) {
                    CURRENT_TAG = TAG_CUSTOMIZED;
                    index = 2;;
                    title = TAG_CUSTOMIZED;

                } else if (id == R.id.nav_spots) {
                    CURRENT_TAG = TAG_SPOTS;
                    index = 3;
                    title = TAG_SPOTS;

                } else if (id == R.id.nav_favourites) {
                    CURRENT_TAG = TAG_FAVOURITES;
                    index = 4;
                    title = TAG_FAVOURITES;

                } else if (id == R.id.nav_marked) {

                } else{
                    CURRENT_TAG = TAG_HOME;
                    index = 0;
                    title = TAG_HOME;
                }

                loadFragment();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void loadFragment() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getCurrentFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_home, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
                getSupportActionBar().setTitle(title);

            }
        };

        if(runnable!=null){
            handler.post(runnable);
        }
        sharedPreferences = getSharedPreferences("Key", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("index", index);
        editor.putString("TAG", CURRENT_TAG);
        editor.commit();

        navigationView.getMenu().getItem(index).setChecked(true);

    }

    private Fragment getCurrentFragment() {
        switch (index){
            case 0:
                 return new HomeFragment();
            case 1:
                return new RecommendedRoutesFragment();
            case 2:
                return new CustomizedRoutesFragment();
            case 3:
                return new SpotsFragment();
            case 4:
                return new FavouritesFragment();
            default:
                return new HomeFragment();
        }

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        HomeFragment homeFragment = new HomeFragment();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if(loadHomeOnBackPressed) {
            if (index != 0) {
                index = 0;
                CURRENT_TAG = TAG_HOME;
                title = TAG_HOME;
                loadFragment();
                navigationView.getMenu().getItem(0).setChecked(true);
                return;
            }
        }
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showFloatingActionButton() {
        fab.show();
    };

    public void hideFloatingActionButton() {
        fab.hide();
    };




}
