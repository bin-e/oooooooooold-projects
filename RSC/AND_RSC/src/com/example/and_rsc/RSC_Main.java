package com.example.and_rsc;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RSC_Main extends Activity {

	Button button_main;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);

		Toast toast = Toast.makeText(this, "아무 키나 눌러주세요", Toast.LENGTH_SHORT);
		toast.show();

		button_main = (Button) findViewById(R.id.Main_Button);
		
		button_main.setOnClickListener(click_main);
	}
	
	private View.OnClickListener click_main = new View.OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(RSC_Main.this, RSC_Connect.class));
			finish();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.act_main, menu);
		return true;
	}
}
