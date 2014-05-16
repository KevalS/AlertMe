package com.example.firstapp;

import com.example.firstapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class PlotLoc extends android.support.v4.app.FragmentActivity {
	
	GoogleMap map;
	StoredObject ob;
	TextView myLocationText;
	public PlotLoc() {
		// TODO Auto-generated constructor stub
		ob = new StoredObject();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plot_loc);
		Bundle getBundle = this.getIntent().getExtras();
		ob = (StoredObject) getBundle.getSerializable("object");
		TextView head = (TextView)findViewById(R.id.header);
	//	head.setText(ob.getDealLat()+","+ob.getDealLong());
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();
		LatLng sydney = new LatLng(ob.getDealLat(), ob.getDealLong());
		
		map.animateCamera(CameraUpdateFactory.zoomTo(15));
	    map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
	    
	    MarkerOptions options = new MarkerOptions();
    	options.title(ob.getDealLocName());
    	options.snippet(ob.getDealDetail());
    	options.position(sydney);
    	Marker marker = map.addMarker(options);
    	head.setText(ob.getDealLat()+","+ob.getDealLong());
    	map.setInfoWindowAdapter(new InfoWindowAdapter(){

			@Override
			public View getInfoContents(Marker marker) {
				View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
				TextView title = (TextView) v.findViewById(R.id.title);
			//	TextView deal = (TextView) v.findViewById(R.id.titledesc);
				TextView link = (TextView) v.findViewById(R.id.link);
				title.setText(marker.getTitle());
			//	deal.setText(ob.getDealDetail());
				link.setText(Html.fromHtml("<a href=\"http://www.google.com\">Click Here</a>"));
				link.setMovementMethod(LinkMovementMethod.getInstance());
				return v;
			}

			@Override
			public View getInfoWindow(Marker marker11) {
				return null;
			}
    		
    	});
	}
	public void onConfirmClicked(View view) {
		//write insert query for insertion of new deal
		 Intent intent = new Intent();//getApplicationContext(), PlotLoc.class);
	  	 intent.setClassName("com.example.firstapp", "com.example.firstapp.SplashActivity");
	  	Bundle b = new Bundle();
		b.putSerializable("object",ob);
		 intent.putExtras(b);
	    startActivity(intent);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.plot_loc, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_plot_loc,
					container, false);
			return rootView;
		}
	}

}
