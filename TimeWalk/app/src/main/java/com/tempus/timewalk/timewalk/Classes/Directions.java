package com.tempus.timewalk.timewalk.Classes;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.tempus.timewalk.timewalk.AppConfig;
import com.tempus.timewalk.timewalk.Models.Points;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * An immutable class representing the route with string variables representing its origin and last
 * landmark within a tour, and any landmarks inbetween are represented in string format within the
 * wayPoints string (split by " | ")
 * Created by Isaac on 5/9/17.
 */

public class Directions {

    /**
     * Variables
     */
    private DirectionListener directionListener;
    private String origin;
    private String destination;
    private String wayPoints;

    /**
     * Constructor method to create a card adapter
     * @param directionListener set the DirectionListener interface
     * @param origin First landmark of the tour
     * @param destination Last landmark of the tour
     * @param wayPoints Other landmarks inbetween origin and destination, split by " | "
     */
    public Directions (DirectionListener directionListener, String origin, String destination, String wayPoints){
        this.origin = origin;
        this.destination = destination;
        this.directionListener = directionListener;
        this.wayPoints = wayPoints;
    }

    /**
     * Send Url to map API to get data to construct the route in JSON format upon starting, parse
     * the downloaded data into DownloadData() class.
     * @throws UnsupportedEncodingException when Url can't be connected
     */
    public void execute() throws UnsupportedEncodingException {
        directionListener.onDirectionStart();
        new DownloadData().execute(createUrl());
    }

    /**
     * Construct Url to get map routing based on the landmarks
     * @return String representation of an url to connect with map API
     * @throws UnsupportedEncodingException when Url can't be connected
     */
    private String createUrl() throws UnsupportedEncodingException {
        String urlOrigin = URLEncoder.encode(origin, "utf-8");
        String urlDestination = URLEncoder.encode(destination, "utf-8");
        String urlWayPoints = URLEncoder.encode(wayPoints,"utf-8");

        return AppConfig.URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination +
                "&waypoints=" + urlWayPoints + "&mode=walking" + "&key=" +  AppConfig.API_KEY;
    }

    /**
     * A private immutable class to represent the downloaded map data get from Google Map API
     * extend AsyncTask class
     * @throws UnsupportedEncodingException when Url can't be connected
     */
    private class DownloadData extends AsyncTask<String, Void, String>{
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
            try {
                parseJson(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parse the downloaded script into a list of points to use with onDirectionSuccess and get
     * returned route for the map
     * @param str String representation of the downloaded script
     * @throws JSONException when JSON object is not found
     */
    private void parseJson(String str) throws JSONException {
        if (str ==null){
            return;
        }
        List<Points> points = new ArrayList<>();
        JSONObject jsonData = new JSONObject(str);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        for (int i = 0; i < jsonRoutes.length(); i++){
            Points point = new Points();
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);

            JSONObject overviewPolylineJson = jsonRoute.getJSONObject("overview_polyline");
            point.points= decodePolyLine(overviewPolylineJson.getString("points"));
            points.add(point);

        }
        directionListener.onDirectionSuccess(points);
    }

    /**
     * Decode the poly line into list of all coordinates
     * @param points String representation of all the points
     * @return List of the coordinates
     */
    private List<LatLng> decodePolyLine(String points) {
        int length = points.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<>();
        int latitude = 0;
        int longitude = 0;

        while (index < length) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = points.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            latitude += dlat;

            shift = 0;
            result = 0;
            do {
                b = points.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            longitude += dlng;

            decoded.add(new LatLng(
                    latitude / 100000d, longitude / 100000d
            ));
        }

        return decoded;
    }
}
