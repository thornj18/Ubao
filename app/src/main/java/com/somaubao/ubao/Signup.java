package com.somaubao.ubao;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class Signup extends Activity {

	EditText user , pass , email ; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singup);
        
        user = (EditText) findViewById(R.id.txtusernamesu);
        pass = (EditText) findViewById(R.id.txtpasswordsu);
        email = (EditText) findViewById(R.id.txtemailsu);
}

	public void clicked(View w )
	{
		switch (w.getId()) {
		case R.id.btnsubmitsu:
		
		String username = 	user.getText().toString() ;
		String password = pass.getText().toString();
		String mail = email.getText().toString();
		
		if (username == " " & password == " " & mail == " ") {
			
			Toast.makeText(getApplicationContext(), " P L E A S E \00A0 F I L L \00A0 I N \00A0 T H E \00A0 B L A N K S " , Toast.LENGTH_SHORT).show();
		} else {

	      Intent mt = new Intent(getApplicationContext(), FirstTime_Subscriptions.class);
	       mt.putExtra("user", username);
	       mt.putExtra("pass", password);
	       mt.putExtra("mail", mail);
	       startActivity(mt);
			
		}
			break;

	
		}
		
	}
    
}