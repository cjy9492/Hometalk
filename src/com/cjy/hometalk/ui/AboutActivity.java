package com.cjy.hometalk.ui;

import com.cjy.hometalk.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutActivity extends Activity{
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_about);
	Button about_back=(Button) findViewById(R.id.about_back);
	about_back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			finish();
			
		}
	});;
	}
	
}
