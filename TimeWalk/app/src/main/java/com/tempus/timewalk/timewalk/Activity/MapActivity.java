package com.tempus.timewalk.timewalk.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.tempus.timewalk.timewalk.Fragment.HomeFragment;
import com.tempus.timewalk.timewalk.Fragment.LocationFragment;
import com.tempus.timewalk.timewalk.Fragment.MapsFragment;
import com.tempus.timewalk.timewalk.R;

/**
 * A {@Link Activity} subclass
 * Acts as container for {@Link MapsFragment} and {@Link LocationFragment}
 */

public class MapActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    /**
     * Use this to fires when the system first creates the activity
     * Display Map Fragment upon starting up
     *
     * @param savedInstanceState a Bundle object containing the activity's previously saved state.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        MapsFragment mapsFragment = new MapsFragment();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.maps_container, mapsFragment,
                mapsFragment.getTag()).addToBackStack("map").commit();
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
        switch(item.getItemId()){
            case android.R.id.home :
                MapsFragment maps = new MapsFragment();
                if(fragmentManager.getBackStackEntryCount() > 1){
                    fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0).getId(),
                            fragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction().replace(R.id.maps_container, maps,
                            maps.getTag()).addToBackStack("map").commit();
                }
                else {

                    Intent intent = new Intent(this, NavigationDrawer.class);
                    intent.putExtra("fragment", 1);
                    startActivity(intent);
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     *
     */
    @Override
    public void onBackPressed() {
        MapsFragment maps = new MapsFragment();
        if(fragmentManager.getBackStackEntryCount() > 1){
            fragmentManager.popBackStack("map",
                    fragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.maps_container, maps,
                    maps.getTag()).addToBackStack("map").commit();
    }
    else {

            Intent intent = new Intent(this, NavigationDrawer.class);
            intent.putExtra("fragment", 1);
            startActivity(intent);
            finish();
        }
    }
}
