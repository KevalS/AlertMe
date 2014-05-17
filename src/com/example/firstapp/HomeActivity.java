package com.example.firstapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class HomeActivity extends Activity {
	ImageView imageProfile;
	TextView textViewName, textViewEmail, textViewGender, textViewBirthday;
	String textName, textEmail, textGender, textBirthday, userImageUrl;
	StoredObject ob;
	public HomeActivity() {
		// TODO Auto-generated constructor stub
		ob = new StoredObject();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Bundle getBundle = this.getIntent().getExtras();
		ob = (StoredObject) getBundle.getSerializable("object");
		imageProfile = (ImageView) findViewById(R.id.imageView1);
		textViewName = (TextView) findViewById(R.id.textViewNameValue);
		textViewEmail = (TextView) findViewById(R.id.textViewEmailValue);
		/*List view for deals*/
		
		DealAdapter adapter = new DealAdapter(this, generateData());
		 
        // 2. Get ListView from activity_main.xml
        final ListView listView = (ListView) findViewById(R.id.listview);
 
        // 3. setListAdapter
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

           

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long posLng) {
				Item it =(Item) (listView.getItemAtPosition(pos));
				Log.i("Hello!", "Clicked! YAY!"+pos+"..."+posLng+"..."+it.getDescription()+"..tit.."+it.getTitle());
				// TODO Auto-generated method stub
				
			}

        });
		/*End of retrieval*/
		//textViewGender = (TextView) findViewById(R.id.textViewGenderValue);
		//textViewBirthday = (TextView) findViewById(R.id.textViewBirthdayValue);

		/**
		 * get user email using intent
		 */

		Intent intent = getIntent();
		textEmail = intent.getStringExtra("email_id");
		System.out.println(textEmail);
		textViewEmail.setText(textEmail);
		ob.SetEmail(textEmail.toString());

		/**
		 * get user data from google account
		 */

		try {
			System.out.println("On Home Page***"
					+ AbstractGetNameTask.GOOGLE_USER_DATA);
			JSONObject profileData = new JSONObject(
					AbstractGetNameTask.GOOGLE_USER_DATA);
			if (profileData.has("id")) {
				ob.SetAuthLink(profileData.getString("id"));
			}
			if (profileData.has("picture")) {
				userImageUrl = profileData.getString("picture");
				new GetImageFromUrl().execute(userImageUrl);
			}
			if (profileData.has("name")) {
				textName = profileData.getString("name");
				textViewName.setText(textName);
				ob.SetName(textName.toString());
			}
			/*if (profileData.has("gender")) {
				textGender = profileData.getString("gender");
				textViewGender.setText(textGender);
			}
			if (profileData.has("birthday")) {
				textBirthday = profileData.getString("birthday");
				textViewBirthday.setText(textBirthday);
			}*/

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ArrayList<Item> generateData(){
        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("location name 1","addr 1","20 % off Deal",1));
        items.add(new Item("location name 2","addr2","20 % off Deal",2));
        items.add(new Item("location name 3","addr3","20 % off Deal",3));
        items.add(new Item("location name 4","addr4","20 % off Deal",4));
        items.add(new Item("location name 5","addr5","20 % off Deal",5));
        items.add(new Item("location name 6","addr6","20 % off Deal",6));
        items.add(new Item("location name 7","","20 % off Deal",7));
       // items.add(new Item("location name 8","Address if any","20 % off Deal",40.0454,-74.0454,8));
       // items.add(new Item("Item 3","Third Item on the list"));
 
        return items;
    }
	public void onContinueClicked(View view) {
		Intent intent = new Intent();//getApplicationContext(), PlotLoc.class);
	  	 intent.setClassName("com.example.firstapp", "com.example.firstapp.AddDealForm");
	  	Bundle b = new Bundle();
		b.putSerializable("object",ob);
		 intent.putExtras(b);
	    startActivity(intent);
	}
	public void homeBtnClicked(View view) {
		Intent intent = new Intent();//getApplicationContext(), PlotLoc.class);
	  	 intent.setClassName("com.example.firstapp", "com.example.firstapp.MainActivity");
	  	Bundle b = new Bundle();
		b.putSerializable("object",ob);
		 intent.putExtras(b);
	    startActivity(intent);
	}
	public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... urls) {
			Bitmap map = null;
			for (String url : urls) {
				map = downloadImage(url);
			}
			return map;
		}

		// Sets the Bitmap returned by doInBackground
		@Override
		protected void onPostExecute(Bitmap result) {
			imageProfile.setImageBitmap(result);
		}

		// Creates Bitmap from InputStream and returns it
		private Bitmap downloadImage(String url) {
			Bitmap bitmap = null;
			InputStream stream = null;
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inSampleSize = 1;

			try {
				stream = getHttpConnection(url);
				bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
				stream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return bitmap;
		}

		// Makes HttpURLConnection and returns InputStream
		private InputStream getHttpConnection(String urlString)
				throws IOException {
			InputStream stream = null;
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			try {
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				httpConnection.setRequestMethod("GET");
				httpConnection.connect();

				if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					stream = httpConnection.getInputStream();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return stream;
		}
	}
}