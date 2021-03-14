package com.example.rsa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

public class ProgramGomActivity extends Activity {

	ConnectActivity ca; 		// TCP 통신을 위한 Connect 객체

	/* 상단 3버튼 */
	Button btn_Gom_File_Open;	// Alt + i			간편 열기
	Button btn_Gom_Mouse;		//					마우스
	Button btn_Gom_Power;		//					Power

	/* 중단 이미지 */
	ImageView img_Gom;			//					중앙 이미지

	/* 중하단 5버튼 */
	Button btn_Gom_Bback;		// Shift + Left		더더 많이 후진
	Button btn_Gom_Back;		// Left				후진
	Button btn_Gom_Play;		// Space			재생, 일시정지
	Button btn_Gom_Front;		// Right			전진
	Button btn_Gom_Ffront;		// Shift + Right	더더 많이 전진

	/* 하단 3버튼 */
	Button btn_Gom_Prev;		// PageUp			이전파일
	Button btn_Gom_Playlist;	// F8				플레이리스트
	Button btn_Gom_Next;		// PageDown			다음 파일

	/* 최하단 뮤트버튼 */
	Button btn_Gom_Mute;		// M				뮤트, 언뮤트

	/* 볼륨 조절키를 사용한 볼륨버튼 + ESC 특수버튼 */
	// GOM_VLUME_UP 			// Up 				볼륨 업
	// GOM_VLUME_DOWN 			// Down 			볼륨 다운
	// GOM_ESC 					// ESC				종료
	
	boolean state_Gom_File_Open = false;
	boolean state_Gom_Power = false;
	boolean state_Gom_Play = false;
	boolean state_Gom_Playlist = false;
	boolean state_Gom_Mute = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.program_gom);
		
		ca = new ConnectActivity();
		
		/* R파일로부터 주소값 처리 */
		btn_Gom_File_Open = (Button) findViewById(R.id.button_gom_file_open);
		btn_Gom_Mouse = (Button) findViewById(R.id.button_gom_mouse);
		btn_Gom_Power = (Button) findViewById(R.id.button_gom_on);

		img_Gom = (ImageView) findViewById(R.id.imageView_gom);

		btn_Gom_Bback = (Button) findViewById(R.id.button_gom_bback);
		btn_Gom_Back = (Button) findViewById(R.id.button_gom_back);
		btn_Gom_Play = (Button) findViewById(R.id.button_gom_play);
		btn_Gom_Front = (Button) findViewById(R.id.button_gom_front);
		btn_Gom_Ffront = (Button) findViewById(R.id.button_gom_ffront);

		btn_Gom_Prev = (Button) findViewById(R.id.button_gom_prev);
		btn_Gom_Playlist = (Button) findViewById(R.id.button_gom_playlist);
		btn_Gom_Next = (Button) findViewById(R.id.button_gom_next);

		btn_Gom_Mute = (Button) findViewById(R.id.button_gom_mute);

		/* 모든 버튼 onClick리스너 연결 */
		btn_Gom_File_Open.setOnClickListener(onClick);
		btn_Gom_Mouse.setOnClickListener(onClick);
		btn_Gom_Power.setOnClickListener(onClick);

		btn_Gom_Bback.setOnClickListener(onClick);
		btn_Gom_Back.setOnClickListener(onClick);
		btn_Gom_Play.setOnClickListener(onClick);
		btn_Gom_Front.setOnClickListener(onClick);
		btn_Gom_Ffront.setOnClickListener(onClick);

		btn_Gom_Prev.setOnClickListener(onClick);
		btn_Gom_Playlist.setOnClickListener(onClick);
		btn_Gom_Next.setOnClickListener(onClick);

		btn_Gom_Mute.setOnClickListener(onClick);
	}
	View.OnClickListener onClick = new View.OnClickListener() {	// onClick 리스너
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button_gom_file_open:
				if(!state_Gom_File_Open){
					ca.sendMessages("GOM_FILE_OPEN");
					state_Gom_File_Open = true;
					btn_Gom_File_Open.setText("Cancel");
				} else {
					ca.sendMessages("GOM_ESC");
					state_Gom_File_Open = false;
					btn_Gom_File_Open.setText("FileOpen");
				}
				break;
			case R.id.button_gom_mouse:
				final Intent it_GomMouse = new Intent(ProgramGomActivity.this, MouseActivity.class);
				startActivity(it_GomMouse);		// 마우스 액티비티로 전환
				break;
			case R.id.button_gom_on:
				if (!state_Gom_Power) {
					ca.sendMessages("GOM_ON");
					state_Gom_Power = true;
					btn_Gom_Power.setText("off");
				} else {
					ca.sendMessages("GOM_OFF");
					state_Gom_Power = false;
					btn_Gom_Power.setText("on");
				}
				break;

			case R.id.button_gom_bback:
				ca.sendMessages("GOM_BBACK");
				break;
			case R.id.button_gom_back:
				ca.sendMessages("GOM_BACK");
				break;
			case R.id.button_gom_play:
				if (!state_Gom_Play) {
					state_Gom_Play = true;
					btn_Gom_Play.setText("Pause");
				} else {
					state_Gom_Play = false;
					btn_Gom_Play.setText("Play");
				}
				ca.sendMessages("GOM_PLAY");
				break;
			case R.id.button_gom_front:
				ca.sendMessages("GOM_FRONT");
				break;
			case R.id.button_gom_ffront:
				ca.sendMessages("GOM_FFRONT");
				break;

			case R.id.button_gom_prev:
				ca.sendMessages("GOM_PREV");
				break;
			case R.id.button_gom_playlist:
				if(!state_Gom_Playlist){
					ca.sendMessages("GOM_PLAYLIST");
					state_Gom_Playlist = true;
					btn_Gom_Playlist.setText("Cancel");
				} else {
					state_Gom_Playlist = false;
					btn_Gom_Playlist.setText("PlayList");
				}
				ca.sendMessages("GOM_PLAYLIST");
				break;
			case R.id.button_gom_next:
				ca.sendMessages("GOM_NEXT");
				break;

			case R.id.button_gom_mute:
				if (!state_Gom_Mute) {
					state_Gom_Mute = true;
					btn_Gom_Play.setText("UnMute");
				} else {
					state_Gom_Play = false;
					btn_Gom_Play.setText("Mute");
				}
				ca.sendMessages("GOM_MUTE");
				break;
			}
		}
	};
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:			// 디바이스 볼륨버튼 업
			ca.sendMessages("GOM_VLUME_UP");
			break;
		case KeyEvent.KEYCODE_VOLUME_DOWN:			// 디바이스 볼륨버튼 다운
			ca.sendMessages("GOM_VLUME_DOWN");
			break;
		case KeyEvent.KEYCODE_BACK:					// 디바이스 취소버튼
			finish();
		}
		return true;
	}
}
