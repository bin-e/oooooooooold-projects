package com.ftp;

import com.ftp.activity.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash extends Activity {

	Thread thd_Splash;

	// UI
	TextView txv_Wait;
	ProgressBar pgb_Splash;
	int progress = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		// R파일과 연결
		txv_Wait = (TextView) findViewById(R.id.txv_wait);
		pgb_Splash = (ProgressBar) findViewById(R.id.pgb_splash);

		// 프로세스 초기화
		pgb_Splash.setProgress(0);

		// 스플래쉬 하면을 위한 쓰레드 생성
		thd_Splash = new Thread(new Runnable() {
			public void run() {
				progress = 0;
				pgb_Splash.setVisibility(View.VISIBLE);
				txv_Wait.setVisibility(View.VISIBLE);

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
				Intent endSplash = new Intent(Splash.this,	MainActivity.class);
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
			pgb_Splash.incrementProgressBy(progress);
		}
	};
}
