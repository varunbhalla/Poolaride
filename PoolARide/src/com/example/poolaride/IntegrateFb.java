package com.example.poolaride;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.PushService;

public class IntegrateFb extends Application {

	static final String TAG = "MyApp";

	@Override
	public void onCreate() {
		super.onCreate();
		
		//Add your Parse application id and client key
		Parse.initialize(this, "", ""); 

		// Set your Facebook App Id in strings.xml
		ParseFacebookUtils.initialize(getString(R.string.app_id));
		PushService.setDefaultPushCallback(getBaseContext(), Response.class);
    }
 

	}

