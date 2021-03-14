package com.example.wbgame;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	// Debugging
	private static final String TAG = "WB_MainActivity";
	private static final boolean D = true;

	Button Btn_main;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Btn_main = (Button) findViewById(R.id.Btn_Main);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (D)
			Log.e(TAG, "++ ON START ++");

		Toast.makeText(MainActivity.this, "Please touch it on display", Toast.LENGTH_LONG).show();

		Btn_main.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent positionIn = new Intent(MainActivity.this, PositionActivity.class);
				startActivity(positionIn);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater Maininflater = getMenuInflater();
		Maininflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_main_exit:
			finish();
			return true;
		}
		return false;
	}

}
