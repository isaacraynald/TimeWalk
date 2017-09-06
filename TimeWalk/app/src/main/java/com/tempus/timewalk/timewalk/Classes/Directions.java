package com.tempus.timewalk.timewalk.Classes;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.tempus.timewalk.timewalk.AppConfig;
import com.tempus.timewalk.timewalk.Models.Points;
import com.tempus.timewalk.timewalk.Models.Routes;

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
 * Created by Isaac on 5/9/17.
 */

public class Directions {
    private DirectionListener directionListener;
    private String origin;
    private String destination;
    private String wayPoints;

    public Directions (DirectionListener directionListener, String origin, String destination, String wayPoints){
        this.origin = origin;
        this.destination = destination;
        this.directionListener = directionListener;
        this.wayPoints = wayPoints;
    }

    public void execute() throws UnsupportedEncodingException {
        directionListener.onDirectionStart();
        new DownloadData().execute(createUrl());
    }

    private String createUrl() throws UnsupportedEncodingException {
        String urlOrigin = URLEncoder.encode(origin, "utf-8");
        String urlDestination = URLEncoder.encode(destination, "utf-8");
        String urlWayPoints = URLEncoder.encode(wayPoints,"utf-8");

        return AppConfig.URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination +
                "&waypoints=" + urlWayPoints + "&mode=walking" + "&key=" +  AppConfig.API_KEY;
    }

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

    private void parseJson(String str) throws JSONException {
        if (str ==null){
            return;
        }
        List<Routes> routes = new ArrayList<>();
        List<Points> points = new ArrayList<>();
        JSONObject jsonData = new JSONObject(str);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        for (int i = 0; i < jsonRoutes.length(); i++){
            Routes route = new Routes();
            Points point = new Points();
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);

            JSONObject overviewPolylineJson = jsonRoute.getJSONObject("overview_polyline");
            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
            for(int j = 0;j < jsonLegs.length();j++){
                JSONObject jsonLeg = jsonLegs.getJSONObject(j);
                JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");
                JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");

                route.startAddress = jsonLeg.getString("start_address");
                route.endAddress = jsonLeg.getString("end_address");
                route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"),
                        jsonStartLocation.getDouble("lng"));
                route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"),
                        jsonEndLocation.getDouble("lng"));
                routes.add(route);
            }
            point.points= decodePolyLine(overviewPolylineJson.getString("points"));
            points.add(point);

        }
        directionListener.onDirectionSuccess(routes, points);
    }

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
