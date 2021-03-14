package com.example.rsa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends Activity {
	
	TextView txt_Help_Title;
	TextView txt_Help_Info1;
	TextView txt_Help_Info2;
	TextView txt_Help_Info3;
	TextView txt_Help_Info4;
	TextView txt_Help_Info5;
	TextView txt_Help_Info6;
	Button btn_Help_Exit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		txt_Help_Title = (TextView) findViewById(R.id.textView_help_title);
		txt_Help_Info1 = (TextView) findViewById(R.id.textView_help_info1);
		txt_Help_Info2 = (TextView) findViewById(R.id.textView_help_info2);
		txt_Help_Info3 = (TextView) findViewById(R.id.textView_help_info3);
		txt_Help_Info4 = (TextView) findViewById(R.id.textView_help_info4);
		txt_Help_Info5 = (TextView) findViewById(R.id.textView_help_info5);
		txt_Help_Info6 = (TextView) findViewById(R.id.textView_help_info6);
		btn_Help_Exit = (Button)findViewById(R.id.button_help_exit);
		
		txt_Help_Title.setTypeface(Typeface.createFromAsset(getAssets(), "wonderland.ttf"));
		txt_Help_Info1.setTypeface(Typeface.createFromAsset(getAssets(), "wonderland.ttf"));
		txt_Help_Info2.setTypeface(Typeface.createFromAsset(getAssets(), "wonderland.ttf"));
		txt_Help_Info3.setTypeface(Typeface.createFromAsset(getAssets(), "wonderland.ttf"));
		txt_Help_Info4.setTypeface(Typeface.createFromAsset(getAssets(), "wonderland.ttf"));
		txt_Help_Info5.setTypeface(Typeface.createFromAsset(getAssets(), "wonderland.ttf"));
		txt_Help_Info6.setTypeface(Typeface.createFromAsset(getAssets(), "wonderland.ttf"));
		
		btn_Help_Exit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.help, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/* ipº¯°æ */
		case R.id.item_help_ipChange:
			// Launch the DeviceListActivity to see devices and do scan
			Intent POIntent = new Intent(this, ConnectActivity.class);
			startActivity(POIntent);
			return true;
		}
		return false;
	}
}
