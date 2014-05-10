package com.example.firstapp;

//import com.example.alertme.R;
//import com.example.alertme.MainActivity.PlaceholderFragment;
import com.example.firstapp.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Button;
import android.widget.PopupWindow;
//import com.google.android.gms.maps.*;
//import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;

public class MainActivity extends ActionBarActivity {
	public final static String PREFS_NAME = "MyPreferencesFile";
	Button btnStartAnotherActivity;
	StoredObject obj;
	public MainActivity() {
		obj = new StoredObject();
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        final Button btnOpenPopup = (Button)findViewById(R.id.openpopup);
        btnOpenPopup.setOnClickListener(new Button.OnClickListener(){

   @Override
   public void onClick(View arg0) {
	   Intent intent = new Intent();//getApplicationContext(), PlotLoc.class);
  	 intent.setClassName("com.example.firstapp", "com.example.firstapp.LoginActivity");
  	Bundle b = new Bundle();
	b.putSerializable("object",obj);
	 intent.putExtras(b);
    startActivity(intent);
  	// startActivity(intent);
 
         
   }});
	
   final Button btnJSONRead = (Button)findViewById(R.id.readjson);
        btnJSONRead.setOnClickListener(new Button.OnClickListener(){
	   @Override
	   public void onClick(View view) {
	   Intent intent = new Intent();
  	 intent.setClassName("com.example.firstapp", "com.example.firstapp.ReadJsonActivity");
     	 startActivity(intent);
       
  	}});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onNextClicked(View view) {
		
	
		CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox1);
		 if (checkBox.isChecked()) {
		     obj.SetChkbox1(true);
		 }
		 else
		 {
			 obj.SetChkbox1(false);
		 }
		 checkBox = (CheckBox) findViewById(R.id.checkBox2);
		 if (checkBox.isChecked()) {
			 obj.SetChkbox2(true);
		     
		 }
		 else
		 {
			 obj.SetChkbox2(false);
		 }
		 
		 checkBox = (CheckBox) findViewById(R.id.checkBox3);
		 if (checkBox.isChecked()) {
			 obj.SetChkbox3(true);
		 }
		 else
		 {
			 obj.SetChkbox3(false);
		 }
		 checkBox = (CheckBox) findViewById(R.id.checkBox4);
		 if (checkBox.isChecked()) {
			 obj.SetChkbox4(true);
		 }
		 else
		 {
			 obj.SetChkbox4(false);
		 }
		Intent intent = new Intent(this, FindLoc.class);

		Bundle b = new Bundle();
		b.putSerializable("object",obj);
		 intent.putExtras(b);
	    startActivity(intent);

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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
		
	}

}

