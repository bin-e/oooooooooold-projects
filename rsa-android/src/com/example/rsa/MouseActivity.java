package com.example.rsa;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MouseActivity extends Activity {

	ConnectActivity ca;
	Thread thd_sendPoint;

	LinearLayout lout_Mouse;
	TextView txt_mouse;
	Button btn_Mouse_Click;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mouse);

		ca = new ConnectActivity();

		lout_Mouse = (LinearLayout) findViewById(R.id.linearLayout_mouse);
		txt_mouse = (TextView) findViewById(R.id.textView_mouse);
		btn_Mouse_Click = (Button) findViewById(R.id.button_mouse_click);

		btn_Mouse_Click.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ca.sendMessages("MOSE_PRESS");
			}
		});

		lout_Mouse.setOnTouchListener(onTouch);
	}

	int touchedX = 0;
	int touchedY = 0;
	String point = "";
	View.OnTouchListener onTouch = new View.OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// case MotionEvent.ACTION_MOVE:
				touchedX = (int) event.getX();
				touchedY = (int) event.getY();
				thd_sendPoint = new Thread(new Runnable() {
					public void run() {
						point = touchedX + ":" + touchedY;

						// txt_mouse.setText(point);
						ca.sendMessages(point);
						try {
							Thread.sleep(100);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				thd_sendPoint.start();
				break;
			case MotionEvent.ACTION_UP:
				touchedX = (int) event.getX();
				touchedY = (int) event.getY();
				thd_sendPoint = new Thread(new Runnable() {
					public void run() {
						point = touchedX + ";" + touchedY;

						// txt_mouse.setText(point);
						ca.sendMessages(point);
						try {
							Thread.sleep(100);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				thd_sendPoint.start();
				break;
			}
			return false;
		}
	};
}
