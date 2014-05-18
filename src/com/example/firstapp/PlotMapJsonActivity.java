/*
 * Copyright (c) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.firstapp;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Kiran Koduru
 */
@SuppressLint("NewApi") public class PlotMapJsonActivity extends FragmentActivity implements LocationListener, OnMyLocationChangeListener{
    private static final String LOG_TAG = "FirstApp";
    private static final String SERVICE_URL = "http://1-dot-cgajjar14.appspot.com/json?";
    private Location loc;
    protected GoogleMap map;
    private boolean isLocCheck = true;
    private StoredObject storedObj;
    private static String url;
    
    public PlotMapJsonActivity(){
    	storedObj = new StoredObject();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // get extra for intent
        Bundle getBundle = this.getIntent().getExtras();
        storedObj = (StoredObject) getBundle.getSerializable("object");
        Boolean restaurant = storedObj.getChkbox1();
        Boolean carrental = storedObj.getChkbox2();
        Boolean movies = storedObj.getChkbox3();
        Boolean shopping = storedObj.getChkbox4();
        
        url = createURL(restaurant, carrental, movies, shopping);
		
		setContentView(R.layout.activity_find_loc);
		setUpMapIfNeeded(url);
    }
    
    public String createURL(Boolean restaurant, Boolean carrental, Boolean movies, Boolean shopping){
    	StringBuffer url = new StringBuffer();

    	if(restaurant){
    		url.append("restaurant=1&");
    	}
    	if(carrental){
    		url.append("car=1&");
    	}
    	if(movies){
    		url.append("movies=1&");
    	}
    	if(shopping){
    		url.append("shopping=1");
    	}
    	
    	return SERVICE_URL + url;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded(url);
    }

    private void setUpMapIfNeeded(String url) {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            map.setMyLocationEnabled(true);
            map.setOnMyLocationChangeListener(this);
            if (map != null) {
                setUpMap(url);
            }
        }
    }

    private void setUpMap(final String url) {
        // Retrieve the city data from the web service
        // In a worker thread since it's a network operation.
        new Thread(new Runnable() {
            public void run() {
                try {
                    retrieveAndAddMarkers(url);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Cannot retrive cities", e);
                    return;
                }
            }
        }).start();
    }
    
    protected void retrieveAndAddMarkers(String createdURL) throws IOException {
        HttpURLConnection conn = null;
        final StringBuilder json = new StringBuilder();
        try {
            // Connect to the web service
            URL url = new URL(createdURL);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Read the JSON data into the StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                json.append(buff, 0, read);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to service", e);
            throw new IOException("Error connecting to service", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        // Create markers for the city data.
        // Must run this on the UI thread since it's a UI operation.
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    createMarkersFromJson(json.toString());
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Error processing JSON", e);
                }
            }
        });
    }
    
    void createMarkersFromJson(String json) throws JSONException {
        // De-serialize the JSON string into an array of city objects
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            // Create a marker for each city in the JSON data.
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            
            map.addMarker(new MarkerOptions()
                .title(jsonObj.getString("TheatreName"))
                .snippet(jsonObj.getString("PhoneNumber"))
                .position(new LatLng(
                        jsonObj.getDouble("Latitude"),
                        jsonObj.getDouble("Longitude")
                 ))
            );
        }
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMyLocationChange(Location location) {
		
		loc = new Location(location);
		double latitude=location.getLatitude();
		double longitude=location.getLongitude();
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		LatLng latLng=new LatLng(latitude,longitude);
		if(isLocCheck){
 		   map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
 		   map.animateCamera(CameraUpdateFactory.zoomTo(11));
 		   isLocCheck = false;
 	   }

		
	}
}