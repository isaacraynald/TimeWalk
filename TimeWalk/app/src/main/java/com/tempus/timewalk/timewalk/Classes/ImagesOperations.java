package com.tempus.timewalk.timewalk.Classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Isaac on 26/10/17.
 */

public class ImagesOperations extends AsyncTask<Object, Void, String> {
    private final Context context;
    private ImageListener imageListener;

    public ImagesOperations(Context context, ImageListener imageListener) {
        this.context = context;
        this.imageListener = imageListener;
    }

    @Override
    protected String doInBackground(Object... params) {
        URL url = null;
        String place = (String) params[1];
        String response = null;
        String parameters = "ID=" + place;
        try {
            url = new URL ((String)params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestMethod("POST");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(parameters);
            writer.flush();
            writer.close();

            String line = "";
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            response = sb.toString();
            JSONObject jsonObject = new JSONObject(response);
            // Response from server after login process will be stored in response variable.
            response = sb.toString();
            // You can perform UI operations here
            isr.close();
            reader.close();
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(String str) {
        imageListener.setImages(str);


    }
}
