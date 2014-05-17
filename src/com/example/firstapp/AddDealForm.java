package com.example.firstapp;

import com.example.firstapp.R;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class AddDealForm extends ActionBarActivity implements LocationListener{
	private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json"; 
	//protected LocationManager locationManager;
	//protected LocationListener locationListener;
	//protected boolean gps_enabled,network_enabled;
	protected Context context;
	EditText locTxt;
	StoredObject ob;
	double currLatitude;
	double currLongitude;
	Location location1;
	public AddDealForm() {
		// TODO Auto-generated constructor stub
		ob = new StoredObject();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LocationManager locManager;
		setContentView(R.layout.activity_add_deal_form);
		locManager =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L,
            500.0f, locationListener);
        location1 = locManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location1 != null) {
            currLatitude = location1.getLatitude();
            currLongitude = location1.getLongitude();
         //   String latLongString = "Lat:" + latitude + "\nLong:" + longitude;
            ob.SetPlotDealLat(currLatitude);
            ob.SetPlotDealLong(currLongitude);
         //   myLocationText.setText("Your Current Position is:\n" + latLongString);
        }
		 //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	      StrictMode.setThreadPolicy(policy);
		Bundle getBundle = this.getIntent().getExtras();
		ob = (StoredObject) getBundle.getSerializable("object");
		final Button btnpin = (Button)findViewById(R.id.pinBtn);
		btnpin.setOnClickListener(new Button.OnClickListener(){

   @Override
   public void onClick(View arg0) {
	   CheckBox checkBox = (CheckBox) findViewById(R.id.currchkBox);
		 if (!checkBox.isChecked()) {
	   EditText addr1 = (EditText)findViewById(R.id.addrTxt1);
	   EditText addr2 = (EditText)findViewById(R.id.stateTxt2);
	   EditText zip = (EditText)findViewById(R.id.zipTxt);
	   String address = addr1.getText().toString().concat(" ").concat(addr2.getText().toString()).concat(" ").concat(zip.getText().toString());
	   ob.SetPlotDealLocAddr(address);
	   try {
		getJSONByGoogle(address);
	} catch (IOException | JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		 }
		 EditText locname = (EditText)findViewById(R.id.locTxt);
		   EditText dealdesc = (EditText)findViewById(R.id.dealTxt);
		 if(locname.getText().toString().trim().equals(""))
		 {
			 Toast.makeText(getApplicationContext(), "plz enter your Location name ", Toast.LENGTH_SHORT).show();
			    return;
		 }
		 else
		 {
			 ob.SetPlotDealLocName(locname.getText().toString());
		 }
		 if(dealdesc.getText().toString().trim().equals(""))
		 {
			 Toast.makeText(getApplicationContext(), "plz enter your deals ", Toast.LENGTH_SHORT).show();
			    return;
		 }
		 else
		 {
			 ob.SetPlotDealDetail(dealdesc.getText().toString());
		 }
	   Intent intent = new Intent();//getApplicationContext(), PlotLoc.class);
  	 intent.setClassName("com.example.firstapp", "com.example.firstapp.PlotLoc");
  	Bundle b = new Bundle();
	b.putSerializable("object",ob);
	 intent.putExtras(b);
    startActivity(intent);
   }
/*		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	});
	}
private void updateWithNewLocation(Location location) {
        
        String latLongString = "";
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            currLatitude = lat;
            currLongitude=lng;
            ob.SetPlotDealLat(currLatitude);
            ob.SetPlotDealLong(currLongitude);
            //latLongString = "Lat:" + lat + "\nLong:" + lng;
        } else {
        	LocationManager locManager =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000L,
                500.0f, locationListener);
            Location loc = locManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            currLatitude = loc.getLatitude();
            currLongitude=loc.getLongitude();
            ob.SetPlotDealLat(currLatitude);
            ob.SetPlotDealLong(currLongitude);
        }
        //myLocationText.setText("Your Current Position is:\n" + latLongString);
    }

    private final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {}

        public void onStatusChanged(String provider,int status,Bundle extras){}
    };

		
	
	public void onCurrLocChkboxClicked(View view) {
	    // Is the view now checked?
	//	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	//	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

		CheckBox checkBox = (CheckBox) findViewById(R.id.currchkBox);
		 if (checkBox.isChecked()) {
		//	 locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
			 ob.SetPlotDealLat(currLatitude);
	            ob.SetPlotDealLong(currLongitude);
			 EditText addr1 = (EditText)findViewById(R.id.addrTxt1);
			 addr1.setEnabled(false);
			   EditText addr2 = (EditText)findViewById(R.id.stateTxt2);
			   addr2.setEnabled(false);
			   EditText zip = (EditText)findViewById(R.id.zipTxt);
			   zip.setEnabled(false);
			    
		 }
		 else
		 {
			 EditText addr1 = (EditText)findViewById(R.id.addrTxt1);
		 addr1.setEnabled(true);
		   EditText addr2 = (EditText)findViewById(R.id.stateTxt2);
		   addr2.setEnabled(true);
		   EditText zip = (EditText)findViewById(R.id.zipTxt);
		   zip.setEnabled(true);
	}
	    //boolean checked = ((CheckBox)view).isChecked();
	}

	public void getJSONByGoogle(String fullAddress) throws IOException, JSONException {

		/*
		* Create an java.net.URL object by passing the request URL in constructor. 
		* Here you can see I am converting the fullAddress String in UTF-8 format. 
		* You will get Exception if you don't convert your address in UTF-8 format. Perhaps google loves UTF-8 format. :)
		* In parameter we also need to pass "sensor" parameter.
		* sensor (required parameter) — Indicates whether or not the geocoding request comes from a device with a location sensor. This value must be either true or false.
		*/
			//fullAddress = "1600 Amphitheatre Parkway,Mountain View,CA,94043";
		URL url = new URL(URL + "?address=" + URLEncoder.encode(fullAddress, "UTF-8")+ "&sensor=false");

		// Open the Connection 
		URLConnection conn = url.openConnection();
		conn.connect();
		InputStreamReader isr = new InputStreamReader(conn.getInputStream());
		StringBuffer sbLocation = new StringBuffer();

		for (int i=0; i != -1; i = isr.read())
		{   
		    sbLocation.append((char)i);
		}
		String getContent = sbLocation.toString().trim();   
		if(getContent.contains("results"))
		{
		    String temp = getContent.substring(getContent.indexOf("["));
		    JSONArray JSONArrayForAll = new JSONArray(temp);
		    String lng = JSONArrayForAll.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
		    String lat = JSONArrayForAll.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
		   ob.SetPlotDealLat(Double.parseDouble(lat));
		   ob.SetPlotDealLong(Double.parseDouble(lng));
		    // System.out.println(" Latitude : " + lat);
		   // System.out.println(" Longitude : " + lng);
		}
		/*
		//This is Simple a byte array output stream that we will use to keep the output data from google. 
		ByteArrayOutputStream output = new ByteArrayOutputStream(1024);

		// copying the output data from Google which will be either in JSON or XML depending on your request URL that in which format you have requested.
		IOUtils.copy(conn.getInputStream(), output);

		JSONObject jsonObj = new JSONObject(output.toString());
		jsonObj.get("geometry");
		//JSONArray data = jsonObj.("geometry");
		//close the byte array output stream now.
		output.close();
		//return jsonObj.getNames(jsonObj);

		return output.toString(); // This returned String is JSON string from which you can retrieve all key value pair and can save it in POJO.
		*/}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_deal_form, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_add_deal_form,
					container, false);
			return rootView;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		locTxt = (EditText)findViewById(R.id.locTxt);
	    locTxt.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
		CheckBox checkBox = (CheckBox) findViewById(R.id.currchkBox);
		 if (checkBox.isChecked()) {
			 ob.SetPlotDealLat(currLatitude);
	            ob.SetPlotDealLong(currLongitude);
		//ob.SetPlotDealLat(location.getLatitude());
		//ob.SetPlotDealLong(location.getLongitude());
		 }
		 
		
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

}
