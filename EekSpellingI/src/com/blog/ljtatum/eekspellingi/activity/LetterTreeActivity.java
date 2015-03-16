package com.blog.ljtatum.eekspellingi.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.anim.ShimmerTextView;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.MusicUtils;
import com.blog.ljtatum.eekspellingi.util.ShareAppUtil;
import com.blog.ljtatum.eekspellingi.util.Utils;

public class LetterTreeActivity extends BaseActivity {
	private final static String TAG = LetterTreeActivity.class.getSimpleName();
	
	private Context mContext;
	private ImageView ivBack, ivBanner;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8;
	private ShimmerTextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4,
		tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8;
	
	private int mLevel = 0;
	private int mSolvedWords = 0;
	private String[] wordBankFull; 
	private List<String> wordBank;

	private ShareAppUtil shareApp;
	private SharedPref sharedPref;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_letter_tree);	
		getIds();
		
	}
	
	private void getIds() {
		mContext = LetterTreeActivity.this;
		shareApp = new ShareAppUtil();
		sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
		wordBank = new ArrayList<String>();
		ivBanner = (ImageView) findViewById(R.id.iv_banner);
		ivBack = (ImageView) findViewById(R.id.iv_back);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		tv6 = (TextView) findViewById(R.id.tv6);
		tv7 = (TextView) findViewById(R.id.tv7);
		tv8 = (TextView) findViewById(R.id.tv8);
		tvAnswer1 = (ShimmerTextView) findViewById(R.id. tv_answer_1);
		tvAnswer2 = (ShimmerTextView) findViewById(R.id. tv_answer_2);
		tvAnswer3 = (ShimmerTextView) findViewById(R.id. tv_answer_3);
		tvAnswer4 = (ShimmerTextView) findViewById(R.id. tv_answer_4);
		tvAnswer5 = (ShimmerTextView) findViewById(R.id. tv_answer_5);
		tvAnswer6 = (ShimmerTextView) findViewById(R.id. tv_answer_6);
		tvAnswer7 = (ShimmerTextView) findViewById(R.id. tv_answer_7);
		tvAnswer8 = (ShimmerTextView) findViewById(R.id. tv_answer_8);
			
		// initialize lesson
		initLesson();
				
		// set default banner
		setDefaultBanner(mContext, ivBanner);
		ivBanner.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Utils.getBannerId() == 1) {
					goToStore(Constants.PKG_NAME_MATH);
				} else if (Utils.getBannerId() == 2) {
					goToStore(Constants.PKG_NAME_ELEMENTS);
				} else if (Utils.getBannerId() == 3) {
					goToStore(Constants.PKG_NAME_BANANA);
				}
			}
		});
	}
	
	private void initLesson() {
		// retrieve level
		Intent intent = getIntent();
		if (intent != null) {		
			mLevel = intent.getIntExtra(Constants.LEVEL_SELECTED, 0);
			Logger.i(TAG, "level: " + mLevel);
		}
				
		// retrieve full word bank
		wordBankFull = getResources().getStringArray(R.array.arryWordBank);
		wordBank = getWordBank(wordBankFull, mLevel);
		Collections.shuffle(wordBank);	
		
		
	}
	
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
			MusicUtils.start(mContext, 1);
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
		super.onBackPressed();
	}

}
