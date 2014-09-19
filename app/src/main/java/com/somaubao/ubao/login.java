package com.somaubao.ubao;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity{

	EditText mail , pass ; 
	String usernam , email , pwd; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mail = (EditText) findViewById(R.id.txtemaillg);
		pass = (EditText) findViewById(R.id.txtpasswordlg);
		Button login = (Button) findViewById(R.id.btnsumbitlg);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 
			     email = mail.getText().toString();
			     pwd = pass.getText().toString();
			    
			    validateUserTask task = new validateUserTask();
			    task.execute(new String[]{email, pwd});
			}
		});
	}

    public void pressed(View view) {
        //On the event of a forgotten password
        Toast.makeText(this, "Dumbass", Toast.LENGTH_SHORT).show();
    }

    private class validateUserTask  extends AsyncTask<String , Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			  ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
              postParameters.add(new BasicNameValuePair("email", params[0] ));
              postParameters.add(new BasicNameValuePair("password", params[1] ));
              String res = null;
              
              try {
            	// here validation and shit 
            	  
            	  
			} 
              catch (Exception e) {
				// TODO: handle exception
            	  Toast.makeText(getApplicationContext(), "Error : \00A0 "+e.toString(), Toast.LENGTH_SHORT).show();
			}
			
			
			
			return res;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result.equals(1)){
				SharedPreferences pref = getApplicationContext().getSharedPreferences("credentials", 0);
				Editor editr = pref.edit();
				
				editr.putString("username", usernam);
				editr.putString("email", email);
				editr.commit();
//				Intent mt = new Intent(getApplicationContext(), main.class);
//				startActivity(mt);
			}
			else {
				 Toast.makeText(getApplicationContext(), "E M A I L \00A0 P A S S W O R D \00A0 A R E \0A00 I N C O R R E  T ", Toast.LENGTH_SHORT).show();

				
			}
		}
    	
    	
    }

}
