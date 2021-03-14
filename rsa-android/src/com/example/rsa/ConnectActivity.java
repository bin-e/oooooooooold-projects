package com.example.rsa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConnectActivity extends Activity {

	// Message Type of Handler
	public static final int THREAD_ISCONNECT_OK = 0;
	public static final int MESSAGE_SEND = 1;

	EditText edt_Ip;
	Button btn_Connect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connect);

		edt_Ip = (EditText)findViewById(R.id.editText_ipNumber);
		//		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		//		imm.hideSoftInputFromWindow(edt_Ip.getWindowToken(), 0);

		btn_Connect = (Button) findViewById(R.id.button_connect);

		btn_Connect.setOnClickListener(onClick);
		edt_Ip.setOnClickListener(onClick);

	}

	View.OnClickListener onClick = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.editText_ipNumber:
				edt_Ip.setText("");
				break;

			case R.id.button_connect:
				ipAddress = edt_Ip.getText().toString();
				thd_isConnect.start();
				break;
			}
		}
	};

	public static String ipAddress;
	public final int SERVER_PORT = 7777;

	public Socket socket = null;
	public DataInputStream dis;
	public DataOutputStream dos;
	public BufferedWriter bw = null;
	public BufferedReader br = null;
	public PrintWriter pw = null;

	public void connecting() throws Exception{
		socket = new Socket(this.ipAddress, this.SERVER_PORT);


		//	Data IO Stream Connecting
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());


		// Buffer WR Stream Connecting
		bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// Print Writer Connecting
		pw = new PrintWriter(bw,true);

	}

	public void disConnecting() throws Exception{
		// All Close
		pw.close();
		br.close();
		bw.close();
		dos.close();
		dis.close();
		socket.close();
	}

	String messages;
	public void sendMessages(String msg){
		messages = msg;
		
		thd_sendMessage = new Thread(new Runnable() {
			public void run() {
				try {
					connecting();
					
					pw.println(messages);
					Log.e("*ConnectActivity", messages+"");
					
					disConnecting();
					
					thd_sendMessage.sleep(1000);
				} catch (Exception e) {
					Log.e("*ConnectActivity", e.toString());
				}
			}
		});
		thd_sendMessage.start();
	}
	
	Thread thd_sendMessage;
	
	Thread thd_isConnect = new Thread(new Runnable() {
		public void run() {
			try {
				connecting();

				pw.println("CONNECT");
				int result = br.read();
				// Btn_Connect.setText(result);

				Log.e("*ConnectActivity", result+"");
				hdr.sendMessage(hdr.obtainMessage(0, result));

				disConnecting();

				thd_isConnect.sleep(1000);
			} catch (Exception e) {
				Log.e("*ConnectActivity", e.toString());
			}
		}
	});
	

	private final Handler hdr = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what){
			case THREAD_ISCONNECT_OK:
				if(msg.obj.equals(49)){
					final Intent it_Main = new Intent(ConnectActivity.this, MainActivity.class);

					AlertDialog.Builder alert = new AlertDialog.Builder(ConnectActivity.this);
					alert.setTitle(ipAddress);
					alert.setMessage("서버에 연결되었습니다.");
					alert.setPositiveButton("확인",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int which) {
							startActivity(it_Main);
							finish();
						}
					});
					alert.show();
				}else{
					Toast.makeText(ConnectActivity.this, "Server IpAdress > Fail", Toast.LENGTH_LONG).show();
					finish();
				}
				break;

			case MESSAGE_SEND:
				break;
			}

		}	
	};
}
