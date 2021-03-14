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

	/* 디버깅 */
	private static final String TAG = "WB_PositionActivity";

	/* 핸들러 처리 */
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	public static final int MESSAGE_Thread = 6;

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	private BluetoothAdapter pBluetoothAdapter = null;
	public BluetoothConnection pBluetoothConnection = null;

	// Layout Views
	LinearLayout layoutPosition;
	private TextView mTitle;

	private String mConnectedDeviceName = null; // Name of the connected device

	/* PowerManager */
	PowerManager mPm = null;
	PowerManager.WakeLock mWakeLock = null;

	// senserManager
	SensorManager mSm;
	int SensorMsg = -1;
	int mAccelCount;
	String DeviceState = null;

	Button btnStart;
	Button btnBlueUp;
	Button btnBlueDown;
	Button btnWhiteUp;
	Button btnWhiteDown;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* Set up the window layout */
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.position);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.custom_title);

		mSm = (SensorManager) getSystemService(Context.SENSOR_SERVICE); // SenserManager

		/* Set up the custom title */
		mTitle = (TextView) findViewById(R.id.title_left_text);
		mTitle.setText(R.string.app_name);
		mTitle = (TextView) findViewById(R.id.title_right_text);

		/* 블루투스 어댑터를 사용가능한 디바이스 인지 판단 */
		pBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (pBluetoothAdapter == null) {
			Toast.makeText(PositionActivity.this, "Bluetooth is not available",
					Toast.LENGTH_LONG).show();
			finish(); // 사용하지 못할경우 액티비티 종료
			return;
		}

		mPm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = mPm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "TAG");

		layoutPosition = (LinearLayout) findViewById(R.id.Layout_Position);
		btnStart = (Button) findViewById(R.id.btn_Start);
		btnBlueUp = (Button) findViewById(R.id.btn_bu);
		btnBlueDown = (Button) findViewById(R.id.btn_bd);
		btnWhiteUp = (Button) findViewById(R.id.btn_wu);
		btnWhiteDown = (Button) findViewById(R.id.btn_wd);

		btnBlueUp.setVisibility(View.INVISIBLE);
		btnBlueDown.setVisibility(View.INVISIBLE);
		btnWhiteUp.setVisibility(View.INVISIBLE);
		btnWhiteDown.setVisibility(View.INVISIBLE);

		btnStart.setOnClickListener(clickListener);
		btnBlueUp.setOnClickListener(clickListener);
		btnBlueDown.setOnClickListener(clickListener);
		btnWhiteUp.setOnClickListener(clickListener);
		btnWhiteDown.setOnClickListener(clickListener);

	}

	View.OnClickListener clickListener = new View.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {

			/* 청기 올렸을때 */
			case R.id.btn_bu:
				MsendMessage("1");
				/* Toast.makeText(PositionActivity.this, "click 1", Toast.LENGTH_SHORT).show(); //클릭확인 */
				break;

			/* 청기 내렸을때 */
			case R.id.btn_bd:
				MsendMessage("2");
				break;

			/* 백기 올렸을때 */
			case R.id.btn_wu:
				MsendMessage("3");
				break;

			/* 백기 올렸을때 */
			case R.id.btn_wd:
				MsendMessage("4");
				break;

			/* 시작버튼을 눌렀을때 */
			case R.id.btn_Start:
				MsendMessage("5");
				btnStart.setBackgroundResource(R.drawable.img_restart); // start 버튼 모양을 restart로 변경
				break;

			default:
				Toast.makeText(PositionActivity.this, "잘못된 버튼 선택 입니다.", Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		mWakeLock.acquire();

		int delay = SensorManager.SENSOR_DELAY_UI;
		mSm.registerListener(mSensorListener,
				mSm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), delay);

		if (pBluetoothConnection != null) {
			// Only if the state is STATE_NONE, do we know that we haven't
			// started already
			if (pBluetoothConnection.getState() == BluetoothConnection.STATE_NONE) {
				pBluetoothConnection.start();
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
		}
	};

	@Override
	public void onStart() {
		super.onStart();
		if (!pBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		} else {
			if (pBluetoothConnection == null)
				setupConnection();
		}
	}

	private void setupConnection() {
		pBluetoothConnection = new BluetoothConnection(this, mHandler);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (pBluetoothConnection != null)
			pBluetoothConnection.stop();
	}

	public void MsendMessage(String mMsg) {
		if (pBluetoothConnection.getState() != BluetoothConnection.STATE_CONNECTED) {
			// Toast.makeText(this, R.string.not_connected,
			// Toast.LENGTH_SHORT).show();
			return;
		}

		// Check that there's actually something to send
		if (mMsg.length() > 0) {
			byte[] send = mMsg.getBytes();
			pBluetoothConnection.write(send);
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
					btnStart.setBackgroundResource(R.drawable.img_start);
					btnStart.setVisibility(View.VISIBLE);
					btnBlueUp.setVisibility(View.INVISIBLE);
					btnBlueDown.setVisibility(View.INVISIBLE);
					btnWhiteUp.setVisibility(View.INVISIBLE);
					btnWhiteDown.setVisibility(View.INVISIBLE);
					break;
				}
				break;
				
				/* write : 읽어온 byte정보를 string으로 변환한다 */
			case MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				String writeMsg = new String(writeBuf);
				break;
				
			case MESSAGE_READ:
				byte[] readBuf = (byte[]) msg.obj;
				String readMessage = new String(readBuf, 0, msg.arg1);
				DeviceState = readMessage;

//				if (DeviceState == "OK") {
//				} else{}
				
				btnStart.setVisibility(View.VISIBLE);
				layoutPosition.setBackgroundResource(R.drawable.screen_new);
				btnBlueUp.setVisibility(View.VISIBLE);
				btnBlueDown.setVisibility(View.VISIBLE);
				btnWhiteUp.setVisibility(View.VISIBLE);
				btnWhiteDown.setVisibility(View.VISIBLE);
				break;

			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_Thread:
				MsendMessage(msg.obj.toString());
				break;
			}
		}
	};

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE_SECURE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS); // 디바이스 물리주소값을 읽어옴
				BluetoothDevice device = pBluetoothAdapter.getRemoteDevice(address); // 블루투스 디바이스 정보를 읽어옴
				pBluetoothConnection.connect(device); // 읽어온 디바이스 정보로 커넥트함
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				Toast.makeText(this, "waiting for enabled bluetooth", Toast.LENGTH_SHORT).show();
				setupConnection();
			} else {
				// User did not enable Bluetooth or an error occured
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
		/* device scan */
		case R.id.item_secure_connect_scan:
			// Launch the DeviceListActivity to see devices and do scan
			POIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(POIntent, REQUEST_CONNECT_DEVICE_SECURE);
			return true;
			
		/* Discoverable */
		case R.id.item_discoverable:
			ensureDiscoverable();
			return true;
		}
		return false;
	}

	private void ensureDiscoverable() { // 디바이스 Discoverable
		if (pBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300); // 300초 동안 디바이스 검색을 가능하게함
			startActivity(discoverableIntent);
		}
	}

	@Override
	public void onBackPressed() {
		Builder d = new AlertDialog.Builder(this);
		d.setMessage("종료하시겠습니까 ???!");

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