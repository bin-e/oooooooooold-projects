package com.example.rsa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	Button btn_Mouse;
	Button btn_Help;
	Button btn_Program;
	Button btn_Exit;
	
	Intent it_Main;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		btn_Mouse = (Button) findViewById(R.id.button_main_mouse);
		btn_Help = (Button) findViewById(R.id.button_main_help);
		btn_Program = (Button) findViewById(R.id.button_main_program);
		btn_Exit = (Button) findViewById(R.id.button_main_exit);
		
		btn_Mouse.setOnClickListener(onClick);
		btn_Help.setOnClickListener(onClick);
		btn_Program.setOnClickListener(onClick);
		btn_Exit.setOnClickListener(onClick);
	}
	
	View.OnClickListener onClick = new View.OnClickListener() {
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.button_main_mouse:
				it_Main = new Intent(MainActivity.this, MouseActivity.class);
				startActivity(it_Main);
//				finish();
				break;
			case R.id.button_main_help:
				it_Main = new Intent(MainActivity.this, HelpActivity.class);
				startActivity(it_Main);
//				finish();
				break;
			case R.id.button_main_program:
				it_Main = new Intent(MainActivity.this, ProgramActivity.class);
				startActivity(it_Main);
//				finish();
				break;
			case R.id.button_main_exit:
				finish(); // Activity 종료
				android.os.Process.killProcess(android.os.Process.myPid()); // 어플 강제종료
				break;
			}
		}
	};
}
