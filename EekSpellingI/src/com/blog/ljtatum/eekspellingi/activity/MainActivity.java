package com.blog.ljtatum.eekspellingi.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.MusicUtils;
import com.blog.ljtatum.eekspellingi.util.ShareAppUtil;
import com.blog.ljtatum.eekspellingi.util.Utils;

public class MainActivity extends BaseActivity implements OnClickListener {
	
	private Context mContext;
	private ImageView ivChar, ivBanner;
	private Button btnLearn, btnRewards, btnExtras;
	
	private ShareAppUtil shareApp;
	private SharedPref sharedPref;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		getIds();
		
	}

	private void getIds() {
		mContext = MainActivity.this;
		shareApp = new ShareAppUtil();
		sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
		ivChar = (ImageView) findViewById(R.id.iv_char);
		ivBanner = (ImageView) findViewById(R.id.iv_banner);
		btnLearn = (Button) findViewById(R.id.btn_learn);
		btnRewards = (Button) findViewById(R.id.btn_rewards);
		btnExtras = (Button) findViewById(R.id.btn_extras);
		
		btnLearn.setOnClickListener(this);
		btnRewards.setOnClickListener(this);
		btnExtras.setOnClickListener(this);
		
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
		
		ivChar.setBackgroundResource(R.drawable.b_character_smile);
	}
	
	private void startAnimations() {
		startButtonAnim(btnLearn);
		// startViewAnim();
		startBannerAnim(mContext, ivBanner);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
	
	private void dialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Exit");
		dialog.setMessage("Are you sure you want to exit");
		dialog.setPositiveButton("Yes?", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		dialog.setNegativeButton("No!", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// DO NOTHING
			}
			
		});
		AlertDialog alertDialog = dialog.create();
		alertDialog.show();
		
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
		dialog();
	}

}
