package com.example.wbgame;

import java.io.*;
import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.sip.SipRegistrationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PositionActivity extends Activity {
	// Debugging
	private static final String TAG = "WB_PositionActivity";
	private static final boolean D = true;

	private BluetoothAdapter pBluetoothAdapter = null;

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	public static final int MESSAGE_Thread = 6;
	public static final int MESSAGE_ = 7;

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	public BluetoothConnection mConnection = null;

	// Layout Views
	LinearLayout layoutPosition;
	private TextView mTitle;

	// Stream TCP
	private BluetoothSocket pBlueToothSocket;
	private InputStream in;
	private OutputStream out;

	// Name of the connected device
	private String mConnectedDeviceName = null;

	// PowerManager
	PowerManager mPm = null;
	PowerManager.WakeLock mWakeLock = null;

	// senserManager
	SensorManager mSm;
	int SensorMsg = -1;
	int mAccelCount;
	int DeviceState;
	// TextView mTxtAccel;

	Button b1;
	Button Btn_start;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set up the window layout
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.position);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
		
		// SenserManager
		mSm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		// mTxtAccel =(TextView)findViewById(R.id.accel);

		layoutPosition = (LinearLayout) findViewById(R.id.Layout_Position);
		b1 = (Button) findViewById(R.id.button1);
		Btn_start = (Button) findViewById(R.id.btn_Start);

		// Set up the custom title
		mTitle = (TextView) findViewById(R.id.title_left_text);
		mTitle.setText(R.string.app_name);
		mTitle = (TextView) findViewById(R.id.title_right_text);

		// Toast.makeText(PositionActivity.this, "position",
		// Toast.LENGTH_LONG).show();

		pBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (pBluetoothAdapter == null) {
			Toast.makeText(PositionActivity.this, "Bluetooth is not available",	Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		
/*		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(200);
						mHandler.obtainMessage(PositionActivity.MESSAGE_Thread,
								SensorMsg).sendToTarget();
					} catch (Exception e) {
						Log.e("thread Error", " ");
					}
				}
			}
		});
		t.start();*/ 

		mPm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = mPm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "TAG");

		b1.setOnClickListener(btn_click);
		Btn_start.setOnClickListener(btn_click);
	}
	
	View.OnClickListener btn_click = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			
			case R.id.button1:
				Toast.makeText(PositionActivity.this, "Click Button1", Toast.LENGTH_SHORT).show();
				break;

			case R.id.btn_Start:
				if(DeviceState == 2){
					MsendMessage("5");
					Btn_start.setBackgroundResource(R.drawable.img_restart);
					
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							while (true) {
								/*try {
									Thread.sleep(5000);
								} catch (Exception e) {
									Log.e(">>>Thread Error", " ");
								}*/
								mHandler.obtainMessage(PositionActivity.MESSAGE_Thread, SensorMsg).sendToTarget();
							}
						}
					});
					t.start();
					
				}
				else{
					Toast.makeText(PositionActivity.this, "Enough Connected Android Device", Toast.LENGTH_SHORT).show();
				}
				break;

			default:
				Toast.makeText(PositionActivity.this, "Set Button Default", Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG, "+ ON RESUME +");

		mWakeLock.acquire();

		int delay = SensorManager.SENSOR_DELAY_UI;
		mSm.registerListener(mSensorListener, mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), delay);

		if (mConnection != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (mConnection.getState() == BluetoothConnection.STATE_NONE) {
				// Start the Bluetooth chat services
				mConnection.start();
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mWakeLock.isHeld()) {
			mWakeLock.release();
		}
		mSm.unregisterListener(mSensorListener);
	}

	SensorEventListener mSensorListener = new SensorEventListener() {
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// 특별히 처리할 필요없음
		}

		public void onSensorChanged(SensorEvent event) {
			float num1, num2;
			// 신뢰성없는 값은 무시
			if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
				// return ;
			}

			float[] v = event.values;
			switch (event.sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				int vectorX = (int) v[0];
				int vectorY = (int) v[1];

/*				if (vectorY >= -1 && vectorY <= 1) {
					SensorMsg = 0;
				}*/
				if (DeviceState == 1) {
					if (vectorX > 12)
						SensorMsg = 1;
					else if (vectorX < 9 && vectorX > 4)
						SensorMsg = 2;
				} else if (DeviceState == 2) {
					if (vectorX < -12)
						SensorMsg = 3;
					else if (vectorX < -4 && vectorX > -8)
						SensorMsg = 4;
				}
				// mHandler.obtainMessage(PositionActivity.MESSAGE_WRITE, SensorMsg).sendToTarget();
				// mTxtAccel.setText("가속 : \n  X:" + v[0] + "\n  Y:" + v[1] + "\n  Z:" + v[2]);
				break;
			}
		}
	};

	@Override
	public void onStart() {
		super.onStart();
			Log.e(TAG, "++ ON START ++");

		if (!pBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			/*
			  // Handler.postDelayed 를 이용하여 딜레이 시간을 줘서 활동을 하게함.
			  mHandler.postDelayed(new Runnable() {
			  
			  @Override public void run() { } }, 4000);
			 */
		} else {
			if (mConnection == null)
				setupConnection();
		}
	}

	private void setupConnection() {
		mConnection = new BluetoothConnection(this, mHandler);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
			Log.e(TAG, "--- ON DESTROY ---");
		if (mConnection != null)
			mConnection.stop();
	}

	public void MsendMessage(String mMsg) {
		if (mConnection.getState() != BluetoothConnection.STATE_CONNECTED) {
//			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
			return;
		}

		// Check that there's actually something to send
		if (mMsg.length() > 0) {
			byte[] send = mMsg.getBytes();
			mConnection.write(send);
		}
	}

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
					Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BluetoothConnection.STATE_CONNECTED:
					mTitle.setText(R.string.title_connected_to);
					mTitle.append(mConnectedDeviceName);
					break;
				case BluetoothConnection.STATE_CONNECTING:
					mTitle.setText(R.string.title_connecting);
					break;
				case BluetoothConnection.STATE_LISTEN:
				case BluetoothConnection.STATE_NONE:
					mTitle.setText(R.string.title_not_connected);
					layoutPosition.setBackgroundColor(color.black);
					Btn_start.setBackgroundResource(R.drawable.img_start);
					Btn_start.setVisibility(View.VISIBLE);
					break;
				}
				break;
			case MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				String writeMsg = new String(writeBuf);
				// Toast.makeText(getApplicationContext(), writeMsg,
				// Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				String readMessage = new String(readBuf, 0, msg.arg1);
				DeviceState = Integer.parseInt(readMessage);

				if (DeviceState == 1) {
					b1.setText(readMessage);
					Btn_start.setVisibility(View.INVISIBLE);
					layoutPosition.setBackgroundResource(R.drawable.screen_b_right);
				} else if (DeviceState == 2) {
					b1.setText(readMessage);
					layoutPosition.setBackgroundResource(R.drawable.screen_w_left);
				}
				b1.setText("rst:" + DeviceState);
				break;

			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),	"Connected to " + mConnectedDeviceName,	Toast.LENGTH_SHORT).show();
				break;
			// case MESSAGE_TOAST:
			// Toast.makeText(getApplicationContext(),
			// msg.getData().getString(TOAST),
			// Toast.LENGTH_SHORT).show();
			// break;
			case MESSAGE_Thread:
				String Str_obj = msg.obj.toString();
				MsendMessage(Str_obj+":");
				break;
			}
		}
	};

/*	class mThread extends Thread implements Runnable {
		private boolean isPlay = false;

		public mThread() {
			isPlay = true;
		}

		public void isThreadState(boolean isPlay) {
			this.isPlay = isPlay;
		}

		public void stopThread() {
			isPlay = !isPlay;
		}

		@Override
		public void run() {
			super.run();

			int i = 0;

			while (isPlay) {
				try {
					Thread.sleep(500);
					mHandler.obtainMessage(MESSAGE_Thread, SensorMsg.toString());
				} catch (Exception e) {
					Log.e("thread Error", " ");
				}
			}
		}
	}*/ 

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE_SECURE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				// Get the BLuetoothDevice object
				BluetoothDevice device = pBluetoothAdapter.getRemoteDevice(address);
				// Attempt to connect to the device
				mConnection.connect(device);
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				Toast.makeText(this, "waiting for enabled bluetooth", Toast.LENGTH_SHORT).show();
				setupConnection();
			} else {
				// User did not enable Bluetooth or an error occured
				Log.d(TAG, "BT not enabled");
				Toast.makeText(this, "Bluetooth was not enabled. Leaving WB_Game", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_position, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent POIntent = null;
		switch (item.getItemId()) {
		case R.id.item_secure_connect_scan:
			// Launch the DeviceListActivity to see devices and do scan
			POIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(POIntent, REQUEST_CONNECT_DEVICE_SECURE);
			return true;
		case R.id.item_discoverable:
			// Ensure this device is discoverable by others
			ensureDiscoverable();
			return true;
		}
		return false;
	}

	private void ensureDiscoverable() {
		if (D)
			Log.d(TAG, "ensure discoverable");
		if (pBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	@Override
	public void onBackPressed() {
		Builder d = new AlertDialog.Builder(this);
		MediaPlayer player = MediaPlayer.create(PositionActivity.this, R.raw.jinsu_end);

		d.setMessage("종료하시겠습니까 ???!");
		player.start();

		d.setPositiveButton("예", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});

		d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		d.show();
	}
}