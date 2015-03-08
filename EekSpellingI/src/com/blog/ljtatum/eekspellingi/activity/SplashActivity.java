package com.blog.ljtatum.eekspellingi.activity;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.anim.Shimmer;
import com.blog.ljtatum.eekspellingi.anim.ShimmerTextView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
	private final int SPLASH_TIMER = 3000;

	private ShimmerTextView tv3;
	private Shimmer shimmer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);	
		getIds();
	}
	
	private void getIds() {
		shimmer = new Shimmer();	
		tv3 = (ShimmerTextView) findViewById(R.id.tv3);
		shimmer.setRepeatCount(0);
		shimmer.setDuration(1800);
		shimmer.setStartDelay(500);
		shimmer.start(tv3);
		
		new Handler().postDelayed(new Runnable() {		
			@Override
			public void run() {
				Intent i = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(i);
				finish();				
			}
		}, SPLASH_TIMER);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}
	
}
	