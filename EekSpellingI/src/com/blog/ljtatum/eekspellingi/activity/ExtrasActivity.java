package com.blog.ljtatum.eekspellingi.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.MusicUtils;
import com.blog.ljtatum.eekspellingi.util.ShareAppUtil;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ExtrasActivity extends BaseActivity implements OnClickListener {

	private Activity mActivity;
	private Context mContext;
	private ImageView ivBack, ivBanner;
	private ShareAppUtil shareApp;
	private SharedPref sharedPref;
	private TextView tvMeta;
	private RelativeLayout rl1, rl2, rl3, rl4, rl5, rl6, rlBackground;
	private ToggleButton mToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extras);
		getIds();
	}

	private void getIds() {
		mActivity = ExtrasActivity.this;
		mContext = ExtrasActivity.this;
		shareApp = new ShareAppUtil();
		sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
		tvMeta = (TextView) findViewById(R.id.extras_tv_meta);
		ivBanner = (ImageView) findViewById(R.id.iv_banner);
		ivBack = (ImageView) findViewById(R.id.extras_iv_back);
		mToggle = (ToggleButton) findViewById(R.id.toggleButton);
		rl1 = (RelativeLayout) findViewById(R.id.extras_rl4);
		rl2 = (RelativeLayout) findViewById(R.id.extras_rl6);
		rl3 = (RelativeLayout) findViewById(R.id.extras_rl7);
		rl4 = (RelativeLayout) findViewById(R.id.extras_rl9);
		rl5 = (RelativeLayout) findViewById(R.id.extras_rl8);
		rl6 = (RelativeLayout) findViewById(R.id.extras_rl10);
		rlBackground =  (RelativeLayout) findViewById(R.id.extras_rl2);
		rl1.setOnClickListener(this);
		rl2.setOnClickListener(this);
		rl3.setOnClickListener(this);
		rl4.setOnClickListener(this);
		rl5.setOnClickListener(this);
		rl6.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		ivBanner.setOnClickListener(this);
		mToggle.setOnClickListener(this);
		
		if (sharedPref.getBooleanPref(Constants.PREF_MUSIC, true)) {
			mToggle.setChecked(true);
			mToggle.setSelected(true);
			mToggle.setText("ON");
			tvMeta.setText("ON");
			mToggle.setTextColor(getResources().getColor(R.color.green_shade));
			rlBackground.setBackgroundColor(getResources().getColor(R.color.green_overlay3));
		} else {
			mToggle.setChecked(false);
			mToggle.setSelected(false);
			mToggle.setText("OFF");
			tvMeta.setText("OFF");
			mToggle.setTextColor(getResources().getColor(R.color.white));
			rlBackground.setBackgroundColor(getResources().getColor(R.color.red_overlay3));
		}
		
		// set default banner
		setDefaultBanner(mContext, ivBanner);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.toggleButton:
			if (sharedPref.getBooleanPref(Constants.PREF_MUSIC, true) && !mToggle.isChecked()) {
				Crouton.showText(mActivity, "Music turned OFF", Style.INFO);
				mToggle.setChecked(false);
				mToggle.setSelected(false);
				mToggle.setText("OFF");
				mToggle.setTextColor(getResources().getColor(R.color.white));
				rlBackground.setBackgroundColor(getResources().getColor(R.color.red_overlay3));
				sharedPref.setPref(Constants.PREF_MUSIC, false);
				MusicUtils.stop();
			} else {
				Crouton.showText(mActivity, "Music turned ON", Style.INFO);
				mToggle.setChecked(true);
				mToggle.setSelected(true);
				mToggle.setText("ON");
				mToggle.setTextColor(getResources().getColor(R.color.green_shade));
				rlBackground.setBackgroundColor(getResources().getColor(R.color.green_overlay3));
				sharedPref.setPref(Constants.PREF_MUSIC, true);
				MusicUtils.start(mContext, 1);
			}
			break;
		case R.id.extras_rl4:
			try {
			    startActivity(new Intent(Intent.ACTION_VIEW, 
			    		Uri.parse("market://details?id=" + Constants.PKG_NAME_BANANA)));
			} catch (android.content.ActivityNotFoundException anfe) {
			    startActivity(new Intent(Intent.ACTION_VIEW, 
			    		Uri.parse("http://play.google.com/store/apps/details?id=" + Constants.PKG_NAME_ELEMENTS)));
			}			
			break;
		case R.id.extras_rl8:
			try {
			    startActivity(new Intent(Intent.ACTION_VIEW, 
			    		Uri.parse("market://details?id=" + Constants.PKG_NAME_MATH)));
			} catch (android.content.ActivityNotFoundException anfe) {
			    startActivity(new Intent(Intent.ACTION_VIEW, 
			    		Uri.parse("http://play.google.com/store/apps/details?id=" + Constants.PKG_NAME_ELEMENTS)));
			}			
			break;	
		case R.id.extras_rl10:
			try {
			    startActivity(new Intent(Intent.ACTION_VIEW, 
			    		Uri.parse("market://details?id=" + Constants.PKG_NAME_ELEMENTS)));
			} catch (android.content.ActivityNotFoundException anfe) {
			    startActivity(new Intent(Intent.ACTION_VIEW, 
			    		Uri.parse("http://play.google.com/store/apps/details?id=" + Constants.PKG_NAME_ELEMENTS)));
			}			
			break;	
		case R.id.extras_rl6:
			Drawable mDrawable = this.getResources().getDrawable(R.drawable.ic_launcher);
			Bitmap bm = ((BitmapDrawable) mDrawable).getBitmap();
			Uri contentUriFile = getImageUri(ExtrasActivity.this, bm);
			
			try {
				final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setPackage("com.facebook.katana");
				intent.setType("image/*");
				intent.putExtra(Intent.EXTRA_TEXT, Constants.SHARE_MESSAGE);
				intent.putExtra(android.content.Intent.EXTRA_STREAM,
						contentUriFile);
				ExtrasActivity.this.startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.extras_rl7:
			Drawable mDrawable1 = this.getResources().getDrawable(R.drawable.ic_launcher);
			Bitmap bm1 = ((BitmapDrawable) mDrawable1).getBitmap();
			Uri contentUriFile1 = getImageUri(ExtrasActivity.this, bm1);
			
			try {
				final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.setPackage("com.twitter.android");
				intent.setType("image/*");
				intent.putExtra(Intent.EXTRA_TEXT, Constants.SHARE_MESSAGE);
				intent.putExtra(android.content.Intent.EXTRA_STREAM,
						contentUriFile1);
				ExtrasActivity.this.startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.extras_rl9:	
			String emailMsg = "Hello Leonard Tatum, I have something to tell " 
					+ "you about your application....\n\n";
			
			try {
				final Intent intent = new Intent(
						android.content.Intent.ACTION_SEND_MULTIPLE);
				intent.setType("text/plain");
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, 
						"Eek! Math I");
				intent.putExtra(
						android.content.Intent.EXTRA_EMAIL,
						new String[] { "ljtatum@hotmail.com" });
				intent.putExtra(android.content.Intent.EXTRA_TEXT,
						emailMsg);
				ExtrasActivity.this.startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}		
			break;
		case R.id.extras_iv_back:
			finish();
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
			MusicUtils.start(mContext, 1);
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
		Crouton.cancelAllCroutons();
		Crouton.clearCroutonsForActivity(this);
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		destroyTTS();
		super.onBackPressed();
		// transition animation
		overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
	}
}
