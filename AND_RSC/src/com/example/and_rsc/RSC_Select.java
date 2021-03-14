package com.example.and_rsc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class RSC_Select extends Activity {

	RSC_Server server;

	Button MelonON;
	Button EclipseON;
	Button LOLON;
	Button NateOnON;
	Button GOMON;
	Button Custom1ON;
	Button Custom2ON;

	Button MelonOFF;
	Button EclipseOFF;
	Button LOLOFF;
	Button NateOnOFF;
	Button GOMOFF;
	Button Custom1OFF;
	Button Custom2OFF;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_select);

		/* ON */
		MelonON = (Button) findViewById(R.id.Button_Melon_ON);
		EclipseON = (Button) findViewById(R.id.Button_Eclipse_ON);
		LOLON = (Button) findViewById(R.id.Button_LOL_ON);
		NateOnON = (Button) findViewById(R.id.Button_NateOn_ON);
		GOMON = (Button) findViewById(R.id.Button_GOM_ON);

		MelonON.setOnClickListener(click_select);
		EclipseON.setOnClickListener(click_select);
		NateOnON.setOnClickListener(click_select);
		LOLON.setOnClickListener(click_select);
		GOMON.setOnClickListener(click_select);

		/* OFF */
		MelonOFF = (Button) findViewById(R.id.Button_Melon_OFF);
		EclipseOFF = (Button) findViewById(R.id.Button_Eclipse_OFF);
		LOLOFF = (Button) findViewById(R.id.Button_LOL_OFF);
		NateOnOFF = (Button) findViewById(R.id.Button_NateOn_OFF);
		GOMOFF = (Button) findViewById(R.id.Button_GOM_OFF);

		MelonOFF.setOnClickListener(click_select);
		EclipseOFF.setOnClickListener(click_select);
		NateOnOFF.setOnClickListener(click_select);
		LOLOFF.setOnClickListener(click_select);
		GOMOFF.setOnClickListener(click_select);
	}

	private View.OnClickListener click_select = new View.OnClickListener() {
		public void onClick(View v) {

			try {
				setServer();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			PrintWriter client_out = new PrintWriter(clientWriter, true);

			try {
				if (v == MelonON) {
					client_out.println("Melon_ON");
				} else if (v == EclipseON) {
					client_out.println("Eclipse_ON");
				} else if (v == NateOnON) {
					client_out.println("NateOn_ON");
				} else if (v == LOLON) {
					client_out.println("LOL_ON");
				} else if (v == GOMON) {
					client_out.println("GOM_ON");

				} else if (v == MelonOFF) {
					client_out.println("Melon_OFF");
				} else if (v == EclipseOFF) {
					client_out.println("Eclipse_OFF");
				} else if (v == NateOnOFF) {
					client_out.println("NateOn_OFF");
				} else if (v == LOLOFF) {
					client_out.println("LOL_OFF");
				} else if (v == GOMOFF) {
					client_out.println("GOM_OFF");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.act_select, menu);
		return true;
	}

	public String SERVER_IP = "210.115.240.250";
	public static final int SERVER_PORT = 7777;

	Socket socket;
	DataInputStream input;
	DataOutputStream output;
	BufferedWriter clientWriter;
	BufferedReader clientReader;

	public void setServer() throws IOException {

//		RSC_Connect rsc_c = new RSC_Connect();
//		SERVER_IP = rsc_c.serverIP;
		try {
			socket = new Socket(SERVER_IP, SERVER_PORT);

			clientWriter = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			clientReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			// input = new DataInputStream(socket.getInputStream());
			// output = new DataOutputStream(socket.getOutputStream());
			//
			// input.close();
			// output.close();
			// socket.close();

		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
