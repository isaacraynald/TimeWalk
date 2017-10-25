package com.tempus.timewalk.timewalk.Classes;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Isaac on 19/10/17.
 */

public class DataOperations extends AsyncTask <String, Void, String> {
    private final Context context;
    private  SharedPreferences sharedPrefs;

    public DataOperations(Context context) {
        this.context = context;
        sharedPrefs = context.getSharedPreferences("json", 0);
    }

    @Override
    protected String doInBackground(String... params) {
        String link = params[0];
        String line;
        try {
            URL url = new URL(link);
            InputStream inputStream = url.openConnection().getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            return stringBuffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String str) {
        SharedPreferences.Editor sfc = context.getSharedPreferences("Datas", Context.MODE_PRIVATE).edit();
        sfc.putString("json", str).apply();

    }



}
