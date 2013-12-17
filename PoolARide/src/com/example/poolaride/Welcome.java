package com.example.poolaride;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Welcome extends Activity {
	@Override
	protected void onCreate(Bundle xyz) {
		// TODO Auto-generated method stub
		super.onCreate(xyz);
		try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.poolaride", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
		// full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome);
		// To track statistics around application
        ParseAnalytics.trackAppOpened(getIntent());
 
        // inform the Parse Cloud that it is ready for notifications
        PushService.setDefaultPushCallback(this, Welcome.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        
        
 

		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent i = new Intent("com.example.poolaride.FbLogin");
					startActivity(i);

				}

			}
		};
		timer.start();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
