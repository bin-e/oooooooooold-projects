package com.example.rsa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;

public class ProgramMelonActivity extends Activity {

	ConnectActivity ca;		// TCP 통신을 위한 Connect 객체

	/* 상단 버튼 */
	Button btn_Melon_Mouse;	// 					마우스호출
	Button btn_Melon_Power; // 					ON / OFF

	ImageView img_Melon;	// 					중앙 이미지

	/* 중단 5개 버튼 */
	Button btn_Melon_Prev;	// Ctrl + Left		이전곡
	Button btn_Melon_Back;	// Alt + Left		5초 전으로
	Button btn_Melon_Play;	// Space			시작, 일시정지
	Button btn_Melon_Front;	// Alt + Right		5초 앞으로
	Button btn_Melon_Next;	// Ctrl + Right		다음곡

	/* 하단 뮤트 버튼 */
	Button btn_Melon_Mute;	// F6				뮤트, 언뮤트

	/* 볼륨조절은 디바이스 볼륨버튼으로 */
	// MELON_VLUME_UP		// Ctrl + Up		볼륨 업
	// MELON_VLUME_DOWN		// Ctrl + Down		볼륨 다운

	boolean state_Melon_Power = false;	// 멜론 on/off 유무 파악
	boolean state_Melon_Mute = false;	// 멜론 mute/unMute 유무 파악
	boolean state_Melon_Play = false;	// 멜론 play/pause 유무 파악

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.program_melon);

		ca = new ConnectActivity();

		/* R파일로부터 주소값 처리 */
		btn_Melon_Mouse = (Button) findViewById(R.id.button_melon_mouse);
		btn_Melon_Power = (Button) findViewById(R.id.button_melon_on);

		img_Melon = (ImageView) findViewById(R.id.imageView_melon);

		btn_Melon_Prev = (Button) findViewById(R.id.button_melon_prev);
		btn_Melon_Back = (Button) findViewById(R.id.button_melon_back);
		btn_Melon_Play = (Button) findViewById(R.id.button_melon_play);
		btn_Melon_Front = (Button) findViewById(R.id.button_melon_front);
		btn_Melon_Next = (Button) findViewById(R.id.button_melon_next);

		btn_Melon_Mute = (Button) findViewById(R.id.button_melon_mute);

		/* 모든 버튼 onClick리스너 연결 */
		btn_Melon_Mouse.setOnClickListener(onClick);
		btn_Melon_Power.setOnClickListener(onClick);

		btn_Melon_Prev.setOnClickListener(onClick);
		btn_Melon_Back.setOnClickListener(onClick);
		btn_Melon_Play.setOnClickListener(onClick);
		btn_Melon_Front.setOnClickListener(onClick);
		btn_Melon_Next.setOnClickListener(onClick);

		btn_Melon_Mute.setOnClickListener(onClick);
	}

	View.OnClickListener onClick = new View.OnClickListener() {	// onClick 리스너
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button_melon_mouse:		// 마우스 버튼 클릭시 
				final Intent it_MelonMouse = new Intent(ProgramMelonActivity.this, MouseActivity.class);
				startActivity(it_MelonMouse);		// 마우스 액티비티로 전환
				break;
			case R.id.button_melon_on:				// 전원 버튼 클릭
				if(!state_Melon_Power){				// 초기값이 false( == 전원이 꺼져있으면 )
					ca.sendMessages("MELON_ON");	// 전원을 켜고
					state_Melon_Power = true;		// 전원값을 true( == 켜져있는 상태 )로 전환
				} else{								// 그렇지 않은경우
					ca.sendMessages("MELON_OFF");	// 전원을 끄고
					state_Melon_Power = false;		// 전원값을 false( == 꺼져있는 상태)로 재전환
				}
				break;

			case R.id.button_melon_prev:
				ca.sendMessages("MELON_PREV");
				break;
			case R.id.button_melon_back:
				ca.sendMessages("MELON_BACK");
				break;
			case R.id.button_melon_play:
				if(!state_Melon_Play){
					btn_Melon_Play.setBackgroundResource(R.drawable.button_melon_play);
					state_Melon_Play = true;
				} else {
					btn_Melon_Play.setBackgroundResource(R.drawable.button_melon_pause);
					state_Melon_Play = false;
				}
				ca.sendMessages("MELON_PLAY");
				break;
			case R.id.button_melon_front:
				ca.sendMessages("MELON_FRONT");
				break;
			case R.id.button_melon_next:
				ca.sendMessages("MELON_NEXT");
				break;

			case R.id.button_melon_mute:
				if(!state_Melon_Mute){
					btn_Melon_Mute.setBackgroundResource(R.drawable.button_melon_mute);
					state_Melon_Mute = true;
				} else {
					btn_Melon_Mute.setBackgroundResource(R.drawable.button_melon_unmute);
					state_Melon_Mute = false;
				}
				ca.sendMessages("MELON_MUTE");
				break;
			}
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:			// 디바이스 볼륨버튼 업
			ca.sendMessages("MELON_VLUME_UP");
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:			// 디바이스 볼륨버튼 다운
			ca.sendMessages("MELON_VLUME_DOWN");
			break;
		case KeyEvent.KEYCODE_BACK:					// 디바이스 취소버튼
			finish();
		}
		return true;
	}
}
