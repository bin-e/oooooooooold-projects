package com.example.rsa;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;

public class ProgramActivity extends TabActivity {
	final TabHost tabHost = getTabHost();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tabHost.addTab(tabHost
				.newTabSpec("tab1")
				.setIndicator("PPT", getResources().getDrawable(R.drawable.ic_tab_ppt))
				.setContent(new Intent(ProgramActivity.this, ProgramPptActivity.class)));

		tabHost.addTab(tabHost
				.newTabSpec("tab2")
				.setIndicator("Melon", getResources().getDrawable(R.drawable.ic_tab_melon))
				.setContent(new Intent(ProgramActivity.this, ProgramMelonActivity.class)));

		tabHost.addTab(tabHost
				.newTabSpec("tab3")
				.setIndicator("Gom", getResources().getDrawable(R.drawable.ic_tab_gom))
				.setContent(new Intent(ProgramActivity.this, ProgramGomActivity.class)));

		// 클릭할때 마다 리플레쉬
		tabHost.addTab(tabHost
				.newTabSpec("tab4")
				.setIndicator("ETC", getResources().getDrawable(R.drawable.ic_tab_etc))
				.setContent(new Intent(ProgramActivity.this, ProgramEtcActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));

	}
}
