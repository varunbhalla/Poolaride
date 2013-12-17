package com.example.poolaride;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;




import com.facebook.HttpMethod;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class Search extends Activity implements OnClickListener {
	
	TextView start, end, mode;
	EditText etstart, etend;
	Button auto, taxi;
	ParseObject records;
	String source, dest, username, shour, smin;
	int hour,min;
	static final int TIME_DIALOG_ID=0;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//Add your Parse application id and client key
		Parse.initialize(this, "", ""); 

	
		setContentView(R.layout.search);
		records = new ParseObject("Records");
		Calendar c=Calendar.getInstance();
        hour=c.get(Calendar.HOUR);
        min=c.get(Calendar.MINUTE);
        if(hour<10)
        	shour = "0" + String.valueOf(hour);
        else
        	shour = String.valueOf(hour);
        if(min<10)
        	smin = "0" + String.valueOf(min);
        else
        	smin = String.valueOf(min);
        
        EditText et=(EditText)findViewById(R.id.editText1);
		et.setText(shour+" : "+smin);
		initialize();
	}

	
	 public void showTimeDialog(View v)
	    {
	    	showDialog(TIME_DIALOG_ID);
	    }
	    protected Dialog onCreateDialog(int id)
	    {
	    	switch(id)
	    	{
	    	case TIME_DIALOG_ID:
	    		return new TimePickerDialog(this, timeSetListener, hour, min, true);
	    	}
	    	return null;
	    }
	    private TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				hour=hourOfDay;
				min=minute;
			    if(hour<10)
		        	shour = "0" + String.valueOf(hour);
		        else
		        	shour = String.valueOf(hour);
		        if(min<10)
		        	smin = "0" + String.valueOf(min);
		        else
		        	smin = String.valueOf(min);
		    
				EditText et=(EditText)findViewById(R.id.editText1);
				et.setText(shour+" : "+smin);

			}
		};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		source = etstart.getText().toString();
		dest = etend.getText().toString();
		records.put("Start", source);
		records.put("End", dest);
		records.put("Time", hour+""+min);
		ParseUser user = ParseUser.getCurrentUser();
		String facebookId = null ;
		Bitmap bitmap = null;
		JSONObject userProfile = user.getJSONObject("profile");
//
		try {
			records.put("Name", userProfile.getString("name"));
			username = userProfile.getString("name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch(v.getId()){
		case R.id.bAuto:
			records.put("mode", "Auto");
			break;
		case R.id.bTaxi:
			records.put("mode", "Taxi");
			break;
		
		}
		records.saveInBackground();
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();

		try {
			installation.put("Name", userProfile.getString("name"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		installation.saveInBackground();
		startResults();
		
	}
	private void initialize() {
		// TODO Auto-generated method stub
		
		end = (TextView) findViewById(R.id.tVEnd);
		
		start = (TextView) findViewById(R.id.tVStart);
		etstart = (EditText) findViewById(R.id.eTStart);
		etend = (EditText) findViewById(R.id.eTEnd);
		auto = (Button) findViewById(R.id.bAuto);
		taxi = (Button) findViewById(R.id.bTaxi);
		
		auto.setOnClickListener(this);
		taxi.setOnClickListener(this);
		
	}
	
	

private void startResults() {
	Intent intent = new Intent(this, Results.class);
	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	intent.putExtra("St", source);
	intent.putExtra("End", dest);
	intent.putExtra("Name", username);
	startActivity(intent);
}
}
