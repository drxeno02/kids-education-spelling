package com.blog.ljtatum.eekspellingi.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.adapter.RewardsAdapter;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.MusicUtils;
import com.blog.ljtatum.eekspellingi.util.ShareAppUtil;
import com.blog.ljtatum.eekspellingi.util.Utils;

public class RewardsActivity extends BaseActivity implements OnClickListener {
	private final static String TAG = RewardsActivity.class.getSimpleName();
	
	private Context mContext;	
	private ImageView ivBack, ivBanner;
	private int dimens;
	private final int NUM_COLS = 3;
	private int[] arryImg = {R.drawable.reward_one, R.drawable.reward_two,
			R.drawable.reward_three, R.drawable.reward_four, R.drawable.reward_five,
			R.drawable.reward_six, R.drawable.reward_seven, R.drawable.reward_eight,
			R.drawable.reward_nine, R.drawable.reward_ten, R.drawable.reward_eleven,
			R.drawable.reward_twelve};
	private GridView gv;
	private ShareAppUtil shareApp;
	private SharedPref sharedPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reward);
		getIds();

	}
	
	private void getIds() {
		mContext = RewardsActivity.this;
		shareApp = new ShareAppUtil();
		sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
		ivBanner = (ImageView) findViewById(R.id.iv_banner);
		ivBack = (ImageView) findViewById(R.id.iv_back);
		gv = (GridView) findViewById(R.id.gv);
		
		ivBack.setOnClickListener(this);
		ivBanner.setOnClickListener(this);

		dimens = (getResources().getDisplayMetrics().widthPixels/NUM_COLS); 
		Logger.i(TAG, "dimens: " + dimens);
		gv.setAdapter(new RewardsAdapter(mContext, arryImg, dimens));
		
		// set default banner
		setDefaultBanner(mContext, ivBanner);		
	}	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			// transition animation
			overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
			break;
		case R.id.iv_banner:
			if (Utils.getBannerId() == 1) {
				goToStore(Constants.PKG_NAME_MATH);
			} else if (Utils.getBannerId() == 2) {
				goToStore(Constants.PKG_NAME_ELEMENTS);
			} else if (Utils.getBannerId() == 3) {
				goToStore(Constants.PKG_NAME_BANANA);
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * Method is used to start animations
	 */
	private void startAnimations() {
		startButtonAnim(ivBack);
		startBannerAnim(mContext, ivBanner);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.share:
			shareApp.shareIntent(mContext);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		startAnimations();
		if (sharedPref.getBooleanPref(Constants.PREF_MUSIC, true)) {
			MusicUtils.start(mContext, 2);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MusicUtils.pause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (sharedPref.getBooleanPref(Constants.PREF_MUSIC, true)) {
			try {
				MusicUtils.stop();
			} catch (IllegalStateException ise) {
				ise.printStackTrace();
			}
		}
		super.onBackPressed();
		// transition animation
		overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
	}

}
