package com.example.and_rsc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Map;

import android.widget.EditText;

public class RSC_Server {

	static String SERVER_IP;
	static final int SERVER_PORT = 7777;

	Socket socket;
	DataInputStream input;
	DataOutputStream output;
	BufferedWriter BW;
	BufferedReader BR;

	RSC_Server(String serverIP) {

		SERVER_IP = serverIP;
		try {
			socket = new Socket(SERVER_IP, 7777);

			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());

/*			input.close();
			output.close();
			socket.close();*/

		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void MelonRun() throws Exception {
		output.writeUTF("Melon");
	}

}
