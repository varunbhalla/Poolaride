package com.example.poolaride;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Response extends Activity implements OnClickListener{
	Button acc,rej;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.response);
		
		acc = (Button) findViewById(R.id.bAccept);
		rej = (Button) findViewById(R.id.bReject);
		
		acc.setOnClickListener(this);
		rej.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bAccept:
			startSMSActivity();
			
			break;
		case R.id.bReject:
			finish();
			
			break;
		}
	}

	private void startSMSActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, SendSMS.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		
	}
}
		
		


