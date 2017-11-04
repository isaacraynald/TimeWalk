package com.tempus.timewalk.timewalk.Activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.tempus.timewalk.timewalk.Classes.Data;
import com.tempus.timewalk.timewalk.Classes.DataOperations;
import com.tempus.timewalk.timewalk.Fragment.MapsFragment;
import com.tempus.timewalk.timewalk.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A {@Link Activity} subclass
 * Acts as container for {@Link MapsFragment} and {@Link LocationFragment}
 */

public class MapActivity extends AppCompatActivity{
    private FragmentManager fragmentManager;
    private ArrayList<Data> array;
    List<Data> datas;
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

        //for(Data data : dataList){
            //if id data in value:
            // newList.append(data);
        //}

        //newList
        SharedPreferences sfc = getSharedPreferences("Datas", MODE_PRIVATE);
        String str = sfc.getString("json", "");

        try {
            datas = parseJson(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MapsFragment mapsFragment = new MapsFragment();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        fragmentManager = getSupportFragmentManager();
        array = new ArrayList<>(datas.size());
        array.addAll(datas);
        Bundle b = new Bundle();
        b.putParcelableArrayList("data", array);
        mapsFragment.setArguments(b);
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
                Bundle b = new Bundle();
                b.putParcelableArrayList("data", array);
                maps.setArguments(b);
                if(fragmentManager.getBackStackEntryCount() > 1){
                    fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0).getId(),
                            fragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction().replace(R.id.maps_container, maps,
                            maps.getTag()).addToBackStack("map").commit();
                }
                else if(getIntent().getStringExtra("type").equals("recommended")){

                    Intent intent = new Intent(this, NavigationDrawer.class);
                    intent.putExtra("fragment", 1);
                    startActivity(intent);
                    finish();
                }
                else {

                    Intent intent = new Intent(this, NavigationDrawer.class);
                    intent.putExtra("fragment", 2);
                    startActivity(intent);
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Data> parseJson(String str) throws JSONException {
        if (str == null) {
            return null;
        }
        List<Data> dataList = new ArrayList<>();
        HashMap<String, String> images = new HashMap<>();
        JSONObject jsonData = new JSONObject(str);
        if (jsonData.getInt("success") == 1) {
            JSONArray jsonArray = jsonData.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Data data = new Data(jsonObject.getInt("ID"), jsonObject.getString("Name"), jsonObject.getString("Latitude"),
                        jsonObject.getString("Longitude"), jsonObject.getString("Description"), jsonObject.getInt("ImageId"));
                dataList.add(data);

            }

            } else {
            String errorMsg = jsonData.getString("message");
        }
        return dataList;
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     *
     */
    @Override
    public void onBackPressed() {
        MapsFragment maps = new MapsFragment();
        Bundle b = new Bundle();
        b.putParcelableArrayList("data", array);
        maps.setArguments(b);
        if(fragmentManager.getBackStackEntryCount() > 1){
            fragmentManager.popBackStack("map",
                    fragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.maps_container, maps,
                    maps.getTag()).addToBackStack("map").commit();
    }
        else if(getIntent().getStringExtra("type").equals("recommended")){

            Intent intent = new Intent(this, NavigationDrawer.class);
            intent.putExtra("fragment", 1);
            startActivity(intent);
            finish();
        }
        else {

            Intent intent = new Intent(this, NavigationDrawer.class);
            intent.putExtra("fragment", 2);
            startActivity(intent);
            finish();
        }
    }

}
