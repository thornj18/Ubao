package com.somaubao.ubao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Start extends Activity implements OnClickListener{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		Button login = (Button) findViewById(R.id.btnlogin);
		Button sign = (Button) findViewById(R.id.btnsignup);
		
		
		login.setOnClickListener(this);
		
		sign.setOnClickListener(this);
			
			
	}
	@Override
	public void onClick(View v) {
		Intent mt ;
		switch (v.getId()) {
		case R.id.btnlogin:
			mt = new Intent(this, login.class);
			startActivity(mt);
			
			break;
		case R.id.btnsignup:
			 mt = new Intent(this, Signup.class);
			 startActivity(mt);
			break;
		default:
			break;
		}
	}

	
	
}
