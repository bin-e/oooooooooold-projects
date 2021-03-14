package com.ftp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ftp.R;

public class MainActivity extends Activity {

	// Debugging
	private final String TAG = "TAG_Main";

	// FTP User Infomation
	private String ftp_Address = null;		// 210.115.226.214
	private int ftp_Port = 0;				// 21
	private String ftp_Id = null;			// pi
	private String ftp_Password = null;	// koo

	// UI
	private EditText edt_Ftp_Address;
	private EditText edt_Ftp_Port;
	private EditText edt_Ftp_Id;
	private EditText edt_Ftp_Password;
	private Button btn_Ftp_Connect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// synchronization UI with R.java
		edt_Ftp_Address = (EditText) findViewById(R.id.edt_ftp_address);
		edt_Ftp_Port = (EditText) findViewById(R.id.edt_ftp_port);
		edt_Ftp_Id = (EditText) findViewById(R.id.edt_ftp_id);
		edt_Ftp_Password = (EditText) findViewById(R.id.edt_ftp_password);
		btn_Ftp_Connect = (Button) findViewById(R.id.btn_ftp_connect);

		// Setting onClieckListner
		btn_Ftp_Connect.setOnClickListener(on_Main);
	}
	
	View.OnClickListener on_Main = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			ftp_Address = edt_Ftp_Address.getText().toString();
			ftp_Port = Integer.parseInt(edt_Ftp_Port.getText().toString());
			ftp_Id = edt_Ftp_Id.getText().toString();
			ftp_Password = edt_Ftp_Password.getText().toString();
			
			Intent intent_Main = new Intent(MainActivity.this, ConnectActivity.class);
			intent_Main.putExtra("FTP_ADDRESS", ftp_Address);
			intent_Main.putExtra("FTP_PORT", ftp_Port);
			intent_Main.putExtra("FTP_ID", ftp_Id);
			intent_Main.putExtra("FTP_PASSWORD", ftp_Password);
			startActivity(intent_Main);
			finish();
		}
	};
}
