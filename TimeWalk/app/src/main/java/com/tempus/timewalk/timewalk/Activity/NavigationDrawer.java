package com.tempus.timewalk.timewalk.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
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
import com.tempus.timewalk.timewalk.Fragment.HomeFragment;
import com.tempus.timewalk.timewalk.Fragment.LocationFragment;
import com.tempus.timewalk.timewalk.Fragment.RecommendedRoutesFragment;
import com.tempus.timewalk.timewalk.R;

/**
 * A {@Link Activity} subclass
 * Display the side Drawer
 */

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Variables
     */
    FragmentManager fragmentManager;
    FloatingActionButton fab;
    Toolbar toolbar;
    NavigationView navigationView;

    /**
     * Use this to fires when the system first creates the activity
     * Display the Drawer upon clicking
     *
     * @param savedInstanceState a Bundle object containing the activity's previously saved state.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {

           // @Override
            //public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
            //}
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        HomeFragment homeFragment = new HomeFragment();
        RecommendedRoutesFragment recommendedRoutesFragment = new RecommendedRoutesFragment();
        fragmentManager = getSupportFragmentManager();
        if(getIntent().getIntExtra("fragment",0) == 1){
            fragmentManager.beginTransaction().replace(R.id.content_home, recommendedRoutesFragment,
                    recommendedRoutesFragment.getTag()).addToBackStack("a").commit();
        }
        else {
            fragmentManager.beginTransaction().replace(R.id.content_home, homeFragment,
                    homeFragment.getTag()).commit();
        }
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        NotificationManager notificationmgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, NavigationDrawer.class);
        intent.putExtra("fragment", 1);
        PendingIntent pintent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Notification notif = new Notification.Builder(this)
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle("Have a walk to the past, today!")
                .setContentText("You are near one of our recommended routes.")
                .setContentIntent(pintent)
                .build();


        notificationmgr.notify(0,notif);
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        HomeFragment homeFragment = new HomeFragment();
        navigationView.setNavigationItemSelectedListener(this);
        changeDrawerItem(R.id.nav_home);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0).getId(),
                    fragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.content_home, homeFragment,
                    homeFragment.getTag()).commit();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Highlight the selected item in the drawer.
     *
     */
    public void changeDrawerItem(int position){
        navigationView.setCheckedItem(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected
     *
     * @param item The menu item that was selected.
     * @return false to have the normal processing happen
     *
     */
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
    /**
    public void showFloatingActionButton() {
        fab.show();
    };

    public void hideFloatingActionButton() {
        fab.hide();
    };
     **/

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The menu item that was selected.
     * @return true to display the item as the selected item
     *
     */
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
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                if(fragmentManager.getBackStackEntryCount() > 1) {
                    fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0).getId(),
                            fragmentManager.POP_BACK_STACK_INCLUSIVE);

                    fragmentManager.beginTransaction().replace(R.id.content_home, fragment,
                            fragment.getTag()).commit();}
                else {
                    fragmentManager.beginTransaction().replace(R.id.content_home, fragment,
                            fragment.getTag()).commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (id == R.id.nav_recommended) {
                fragmentClass = RecommendedRoutesFragment.class;

            } else if (id == R.id.nav_customized) {
                fragmentClass = CustomizedRoutesFragment.class;
            }
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragmentManager.beginTransaction().replace(R.id.content_home, fragment,
                        fragment.getTag()).addToBackStack("a").commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
