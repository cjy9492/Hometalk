package com.cjy.hometalk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.AlteredCharSequence;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import com.cjy.hometalk.BaseActivity;
import com.cjy.hometalk.R;

public class SplashActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		LinearLayout ll_splash = (LinearLayout) findViewById(R.id.ll_splash);
		AlphaAnimation animation = new AlphaAnimation(0,1);
		animation.setDuration(3000);
		animation.start();
		ll_splash.setAnimation(animation);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				 SystemClock.sleep(4000);
				Intent intent = new Intent(getApplicationContext(),ChatLoginActivity.class);
				startActivity(intent);
				
			}
		}).start();
		
	}

}
