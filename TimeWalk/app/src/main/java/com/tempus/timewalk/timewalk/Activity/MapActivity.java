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

public class MapActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

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
