package com.example.poolaride;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.PushService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
 
public class SingleItemView extends Activity implements OnClickListener{
    // Declare Variables
    TextView txtname;
    Button sr;
    String name;
    
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.singleitemview);
 
        // Retrieve data from Results on item click event
        Intent i = getIntent();
 
        // Get the name
        name = i.getStringExtra("Name");
 
        // Locate the TextView in singleitemview.xml
        txtname = (TextView) findViewById(R.id.name);
        sr = (Button) findViewById(R.id.bSendReq);
       
 
        // Load the text into the TextView
        txtname.setText(name);
        sr.setOnClickListener(this);
 
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		ParseQuery pushQuery = ParseInstallation.getQuery();
		pushQuery.whereEqualTo("Name", name);
		 
		// Send push notification to query
		ParsePush push = new ParsePush();
		push.setQuery(pushQuery); // Set our Installation query

		push.setMessage("You have received a travel request!");
		push.sendInBackground();
	}
}