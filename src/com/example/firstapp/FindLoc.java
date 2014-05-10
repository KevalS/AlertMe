package com.example.firstapp;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.annotation.SuppressLint;
import android.content.Intent;

import com.example.firstapp.R;
import com.google.android.maps.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.model.*;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


//@SuppressLint("NewApi")
public class FindLoc extends android.support.v4.app.FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener, OnMyLocationChangeListener, OnMarkerClickListener  {
//	TextView mTextview;
	GoogleMap map;
	Location loc;
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	TextView txtLat;
	String lat;
	String provider;
	protected boolean gps_enabled,network_enabled;
	boolean isLocCheck = true;
	StoredObject ob;
	//Location currentLocation;
	//LocationClient locClient;
//	@SuppressLint("NewApi")
	public FindLoc() {
		// TODO Auto-generated constructor stub
		ob = new StoredObject();
	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_loc);
		Bundle getBundle = this.getIntent().getExtras();
		ob = (StoredObject) getBundle.getSerializable("object");
		//Intent i = getIntent();
	//	String id = i.getStringExtra("value");
		String id = ob.getChkbox1()+" "+ob.getChkbox2()+" "+ob.getChkbox3()+" "+ob.getChkbox4();
		TextView getTxt = (TextView)findViewById(R.id.header);
		getTxt.setText(id);
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();

        map.setMyLocationEnabled(true);
      //  map.animateCamera(CameraUpdateFactory.zoomTo(15));
        map.setOnMyLocationChangeListener(this);
        map.setOnMarkerClickListener(this);

       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find_loc, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
 

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_find_loc,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMyLocationChange(Location location) {
		// TODO Auto-generated method stub
		loc = new Location(location);
		double latitude=location.getLatitude();
		double longitude=location.getLongitude();
		loc.setLatitude(latitude);
		loc.setLongitude(longitude);
		LatLng latLng=new LatLng(latitude,longitude);
		if(isLocCheck)
 	   {
 		 //  LatLng latLng=new LatLng(loc.getLatitude(),loc.getLongitude());
 		   map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
 		   map.animateCamera(CameraUpdateFactory.zoomTo(11));
 		   isLocCheck = false;
 		//   break;
 	   }
	//	map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
	//	map.animateCamera(CameraUpdateFactory.zoomTo(15));
		map.addMarker(new MarkerOptions()
        .title("MyLoc")
        .snippet("I am here!!")
        .position(latLng));
		isShoppingClicked();
		isRestaurantClicked();
		isMovieClicked();
		//tvLocation.setText("Latitude:"+latitude+", Longitude:"+longitude);
	}
	
	public void isRestaurantClicked() {
		Location resLoc = new Location(loc);
		for(double i = 0.0010; i<0.0200; i+=0.0030)
		{
			resLoc.setLatitude(loc.getLatitude()+i);
			markLocation(resLoc,50);
		}
		
	}
	
public void isShoppingClicked() {
	Location resLoc = new Location(loc);
	for(double i = 0.0010; i<0.02; i+=0.0030)
	{
		resLoc.setLongitude(loc.getLongitude()+i);
		markLocation(resLoc,100);
	}
	}

public void isMovieClicked() {
	Location resLoc = new Location(loc);
	for(double i = 0.0010; i<0.0200; i+=0.0030)
	{
		resLoc.setLatitude(loc.getLatitude()+i);
		resLoc.setLongitude(loc.getLongitude()+i);
		markLocation(resLoc,150);
	}
}
	public void markLocation(Location location, int colorValue) {

		// TODO Auto-generated method stub
		//String title="";
		String title1;
		if(colorValue == 50)
			title1 = "Restaurant";
		else if(colorValue == 100)
			title1 = "Shopping";
		else if(colorValue == 150)
			title1 = "Movie";
		else
			title1 = "Anonymous";
		double latitude=location.getLatitude();
		double longitude=location.getLongitude();
		LatLng latLng=new LatLng(latitude,longitude);
		//map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		//map.animateCamera(CameraUpdateFactory.zoomTo(15));
		Marker customMarker = map.addMarker(new MarkerOptions()
        .title(title1)
        .snippet("Nearby place")
        .position(latLng)
        .icon(BitmapDescriptorFactory.defaultMarker(colorValue)));
		 customMarker.showInfoWindow();
		//tvLocation.setText("Latitude:"+latitude+", Longitude:"+longitude);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		Log.i("GoogleMapActivity", "onMarkerClick");
	    Toast.makeText(getApplicationContext(),
	        "Marker Clicked: " + marker.getTitle(), Toast.LENGTH_LONG)
	        .show();
		return false;
	}

}

