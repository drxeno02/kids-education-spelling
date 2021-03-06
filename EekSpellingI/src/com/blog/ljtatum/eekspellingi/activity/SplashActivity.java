package com.blog.ljtatum.eekspellingi.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.anim.Shimmer;
import com.blog.ljtatum.eekspellingi.anim.ShimmerTextView;

public class SplashActivity extends BaseActivity {
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
		setContentView(R.layout.activity_splash);	
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
				goToActivity(SplashActivity.this, MainActivity.class, -1);
				finish();				
			}
		}, SPLASH_TIMER);
	}
}
	