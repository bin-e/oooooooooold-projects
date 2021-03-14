// EchoServer.java
// Andrew Davison, ad@fivedots.coe.psu.ac.th, February 2011

/* EchoServer is a threaded RFCOMM service
 with the specified UUID and name. When a client
 connects, it's input is echoed back in uppercase until
 it sends "bye$". 
 */

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.xml.ws.FaultAction;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class WB_server{
	
	// swing 
	static Myform mf_main = new Myform("C:\\Users\\jangbi1\\Desktop\\WBServer_byOneDevice(20131122)\\WB_Image\\BG_Main.png", 1);

	// UUID and name of the echo service
	private static final String UUID_STRING = "0000110100001000800000805F9B34FB";
	// 32 hex digits which will become a 128 bit ID
	private static final String SERVICE_NAME = "Blue White Flag Game Server";

	private StreamConnectionNotifier server;
	private ArrayList<ThreadedEchoHandler> handlers;
	private volatile boolean isRunning = false;

	private StreamConnection conn;
	private InputStream in;
	private OutputStream out;

	public WB_server() {
		mf_main.setV(true);
		handlers = new ArrayList<ThreadedEchoHandler>();

		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				closeDown();
			}
		});

		initDevice();
		createRFCOMMConnection();
		processClients();
	}
	
	private void initDevice() {
		try { 
			LocalDevice local = LocalDevice.getLocalDevice();
			System.out.println("Device name: " + local.getFriendlyName());
			System.out.println("Bluetooth Address: " + local.getBluetoothAddress());
			boolean res = local.setDiscoverable(DiscoveryAgent.GIAC);
			System.out.println("Discoverability set: " + res);
		} catch (BluetoothStateException e) {
			System.out.println(e);
			System.exit(1);
		}
	} // end of initDevice()

	private void createRFCOMMConnection(){
		try {
			System.out.println("Start advertising " + SERVICE_NAME + "...");
			server = (StreamConnectionNotifier) Connector.open("btspp://localhost:" + UUID_STRING + ";name=" + SERVICE_NAME + ";authenticate=false");
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	} // end of createRFCOMMConnection()

	private void processClients(){
		isRunning = true;
		try {
			while (isRunning) {
				System.out.println("Waiting for incoming connection...");
				StreamConnection conn = server.acceptAndOpen();
				System.out.println("Connection requested...");

				ThreadedEchoHandler hand = new ThreadedEchoHandler(conn);
				// create client handler
				handlers.add(hand);
				hand.start();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	} // end of processClients()

	private void closeDown(){
		System.out.println("Closing down server");
		if (isRunning) {
			isRunning = false;
			try {
				server.close();
				// close connection, and remove service record from SDDB
			} catch (IOException e) {
				System.out.println(e);
			}

			// close down all the handlers
			for (ThreadedEchoHandler hand : handlers)
				hand.closeDown();
			handlers.clear();
		}
	} // end of closeDown();

	private boolean sendMessage(String msg)	{
		try {
			out.write(msg.length());
			out.write(msg.getBytes());
			out.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	} // end of sendMessage()

	public static void main(String args[]) {
		new WB_server();
	}
} // end of EchoServer class
