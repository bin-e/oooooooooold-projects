package com.example.rsa;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends Activity {

	Thread thd_Splash;

	ProgressBar pgb;
	int progress = 0;
	TextView txt_Wait;
	TextView txt_MyInfo;
	TextView txt_Version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		pgb = (ProgressBar) findViewById(R.id.progressBar_splash);
		txt_Wait = (TextView) findViewById(R.id.textView_wait);
		txt_MyInfo = (TextView) findViewById(R.id.textView_myInfo);
		txt_Version = (TextView) findViewById(R.id.textView_version);

		pgb.setProgress(0);

		txt_MyInfo.setTypeface(Typeface.createFromAsset(getAssets(), "wonderland.ttf"));
		txt_Version.setTypeface(Typeface.createFromAsset(getAssets(), "SourceCodePro.ttf"));

		thd_Splash = new Thread(new Runnable() {
			public void run() {
				progress = 0;
				pgb.setVisibility(View.VISIBLE);
				txt_Wait.setVisibility(View.VISIBLE);

				while (progress < 100) {
					progress += 34; // 넘어갈 시간지정
					handler.sendMessage(handler.obtainMessage());
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				/** 스플래쉬 후 넘어가는 부분 */
				Intent endSplash = new Intent(SplashActivity.this,	ConnectActivity.class);
				startActivity(endSplash);
				finish();
			}
		});
		thd_Splash.start();

		/*
		// Handler.postDelayed 를 이용하여 딜레이 시간을 줘서 넘어감
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

			}
		}, 3000);
		 */
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			pgb.incrementProgressBy(progress);
		}
	};
}
