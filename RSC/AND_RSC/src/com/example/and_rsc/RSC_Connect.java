package com.example.and_rsc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RSC_Connect extends Activity {

	public String serverIP;
	public static final int SERVER_PORT = 7777;

	Socket socket;
	DataInputStream input;
	DataOutputStream output;

	EditText edit_ip;
	Button button_connect;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_connect);

		edit_ip = (EditText) findViewById(R.id.IP_EditText);
		button_connect = (Button) findViewById(R.id.Connect_Button);

		button_connect.setOnClickListener(click_connect);

	}

	private View.OnClickListener click_connect = new View.OnClickListener() {

		public void onClick(View v) {
			RSC_Select src_s = new RSC_Select();
			
			serverIP = edit_ip.getText().toString();
			/*			SERVER_IP = "192.168.25.3";


			try {
				socket = new Socket(SERVER_IP, 7777);

				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());

				input.close();
				output.close();
				socket.close();


			} catch (ConnectException ce) {
				ce.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}*/

//			RSC_Server server = new RSC_Server(edit_ip.getText().toString());
			final Intent it = new Intent(RSC_Connect.this,RSC_Select.class);

			AlertDialog.Builder alert = new AlertDialog.Builder(RSC_Connect.this);
			alert.setTitle(edit_ip.getText().toString());
			alert.setMessage("서버에 연결되었습니다.");
			alert.setPositiveButton("확인",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int which) {
					startActivity(it);
					finish();
				}
			});
			alert.show();

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.act_main, menu);
		return true;
	}
}
