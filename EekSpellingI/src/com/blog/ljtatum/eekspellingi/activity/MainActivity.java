package com.blog.ljtatum.eekspellingi.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.MusicUtils;
import com.blog.ljtatum.eekspellingi.util.ShareAppUtil;
import com.blog.ljtatum.eekspellingi.util.Utils;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = MainActivity.class.getSimpleName();

	private Activity mActivity;
	private Context mContext;
	private ImageView ivChar, ivBanner;
	private Button btnLearn, btnRewards, btnExtras;
	private boolean isInitLaunch = false;

	private ShareAppUtil shareApp;
	private SharedPref sharedPref;

	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getIds();

	}

	private void getIds() {
		mActivity = MainActivity.this;
		mContext = MainActivity.this;
		initTTS(mContext);
		shareApp = new ShareAppUtil();
		sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
		mHandler = new Handler();
		ivChar = (ImageView) findViewById(R.id.iv_char);
		ivBanner = (ImageView) findViewById(R.id.iv_banner);
		btnLearn = (Button) findViewById(R.id.btn_learn);
		btnRewards = (Button) findViewById(R.id.btn_rewards);
		btnExtras = (Button) findViewById(R.id.btn_extras);

		btnLearn.setOnClickListener(this);
		btnRewards.setOnClickListener(this);
		btnExtras.setOnClickListener(this);
		ivBanner.setOnClickListener(this);

		// set default level settings
		boolean isDefaultLevelSet = sharedPref.getBooleanPref(Constants.PREF_DEFAULT_LV_SETTINGS, false);
		if (!isDefaultLevelSet) {
			setDefaultLevelSettings();
		}

		// set default banner
		setDefaultBanner(mContext, ivBanner);

		// set character image
		ivChar.setBackgroundResource(R.drawable.b_character_smile);

		// delay before welcome message to allow tts initialization
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (!isInitLaunch) {
					isInitLaunch = true;
				}
				speakInstructions();
			}
		}, 2000);
	}

	/**
	 * Method is used to speak instructions
	 */
	private void speakInstructions() {
		Crouton.showText(mActivity, "Lets Learn Together!", Style.INFO);
		speakText("Welcome. . . Lets learn together! Press learn to start");
	}

	/**
	 * Method is used to start animations
	 */
	private void startAnimations() {
		startButtonAnim(btnLearn);
		// startViewAnim();
		startBannerAnim(mContext, ivBanner);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		stopTTS();

		switch (v.getId()) {
		case R.id.btn_learn:
			goToActivity(mContext, SelectActivity.class, -1);
			break;
		case R.id.btn_rewards:
			goToActivity(mContext, RewardsActivity.class, -1);
			break;
		case R.id.btn_extras:
			goToActivity(mContext, ExtrasActivity.class, -1);
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
	 * Method is used to display exit app dialog
	 */
	private void dialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Exit");
		dialog.setMessage("Are you sure you want to exit");
		dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				destroyTTS();
				MusicUtils.release();
				finish();
			}

		});
		dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// DO NOTHING
			}

		});
		AlertDialog alertDialog = dialog.create();
		alertDialog.show();
	}

	/**
	 * Method is called to set up the initial locked level settings
	 */
	private void setDefaultLevelSettings() {
		sharedPref.setPref(Constants.PREF_DEFAULT_LV_SETTINGS, true);
		for (int i = 0; i < 12; i++) {
			String strPrefNameUnlock = Constants.LV_UNLOCKED.concat("_" + i);
			if (i < 4) {
				sharedPref.setPref(strPrefNameUnlock, true);
			} else {
				sharedPref.setPref(strPrefNameUnlock, false);
			}
		}

		if (Constants.DEBUG && Constants.DEBUG_HIGH_VERBOSITY) {
			for (int i = 0; i < 12; i++) {
				String strPrefNameUnlock = Constants.LV_UNLOCKED.concat("_" + i);
				Logger.d(TAG, "lv " + i + ": " + sharedPref.getBooleanPref(strPrefNameUnlock, false));
			}
		}

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

		if (isInitLaunch) {
			speakInstructions();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MusicUtils.pause();
		Crouton.cancelAllCroutons();
		Crouton.clearCroutonsForActivity(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		destroyTTS();
		MusicUtils.release();
		Crouton.cancelAllCroutons();
		Crouton.clearCroutonsForActivity(this);
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		dialog();
	}

}
