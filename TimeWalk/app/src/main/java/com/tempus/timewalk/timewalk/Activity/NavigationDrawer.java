package com.tempus.timewalk.timewalk.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tempus.timewalk.timewalk.Fragment.CustomizedRoutesFragment;
import com.tempus.timewalk.timewalk.Fragment.FavouritesFragment;
import com.tempus.timewalk.timewalk.Fragment.HomeFragment;
import com.tempus.timewalk.timewalk.Fragment.LocationFragment;
import com.tempus.timewalk.timewalk.Fragment.RecommendedRoutesFragment;
import com.tempus.timewalk.timewalk.Fragment.SpotsFragment;
import com.tempus.timewalk.timewalk.R;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    FloatingActionButton fab;
    Toolbar toolbar;
    String title = "Home Page";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        HomeFragment homeFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_home, homeFragment,
                homeFragment.getTag()).commit();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        HomeFragment homeFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(fragmentManager.getBackStackEntryCount() > 1){
            fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0).getId(),
                    fragmentManager.POP_BACK_STACK_INCLUSIVE);
            title = "Home Page";
            getSupportActionBar().setTitle(title);
            fragmentManager.beginTransaction().replace(R.id.content_home, homeFragment,
                    homeFragment.getTag()).commit();


        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        Class fragmentClass = null;

        if (id == R.id.nav_home) {
            fragmentClass = HomeFragment.class;
            fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0).getId(),
                    fragmentManager.POP_BACK_STACK_INCLUSIVE);
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            title = "Home Page";
            fragmentManager.beginTransaction().replace(R.id.content_home, fragment,
                    fragment.getTag()).commit();

        } else {
            if (id == R.id.nav_recommended) {
                fragmentClass = RecommendedRoutesFragment.class;
                title = "Recommended";

            } else if (id == R.id.nav_customized) {
                fragmentClass = CustomizedRoutesFragment.class;
                title = "Customized";

            } else if (id == R.id.nav_spots) {
                fragmentClass = SpotsFragment.class;
                title = "Spots";

            } else if (id == R.id.nav_favourites) {
                fragmentClass = FavouritesFragment.class;
                title = "Favourites";

            } else if (id == R.id.nav_marked) {
                fragmentClass = LocationFragment.class;
                title = "Marked Location";
            }

            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fragmentManager.beginTransaction().replace(R.id.content_home, fragment,
                    fragment.getTag()).addToBackStack("a").commit();
        }

        getSupportActionBar().setTitle(title);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
