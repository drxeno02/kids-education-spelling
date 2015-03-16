package com.blog.ljtatum.eekspellingi.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.util.Config;
import com.blog.ljtatum.eekspellingi.util.Utils;

public class BaseActivity extends Activity {
	private final static String TAG = BaseActivity.class.getSimpleName();
	
	/**
	 * Method is used to re-direct to different Activity
	 * @param context
	 * @param activity
	 */
	protected void goToActivity(Context context, Class<?> activity, int level) {
		Intent intent = new Intent (context, activity);
		if (level >= 0) {
			intent.putExtra(Constants.LEVEL_SELECTED, level);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}
	
	/**
	 * Method is used to re-direct user to the app store
	 * @param pkgName
	 */
	protected void goToStore(String pkgName) {
		try{
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MARKET + pkgName)));
		} catch(android.content.ActivityNotFoundException anfe){
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GOOGLE_PLAY + pkgName)));

		}
	}
	
	/**
	 * Method is used to set an initial banner
	 * @param context
	 * @param ivBanner
	 */
	protected void setDefaultBanner(final Context context, final ImageView ivBanner) {
		// set default banner
		Drawable mDrawable = Utils.getBanner(context);
		ivBanner.setImageDrawable(mDrawable);
	}
	
	/**
	 * Method is used to randomly select a banner
	 * @param context
	 * @param ivBanner
	 */
	protected void startBannerAnim(final Context context, final ImageView ivBanner) {
		Timer mTimerBanner = new Timer();
		mTimerBanner.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Drawable mDrawable = Utils.getBanner(context);
						ivBanner.setImageDrawable(mDrawable);	
				
					}
				});
				
			}
		}, Config.BANNER_TIMER, Config.BANNER_TIMER);
	}
	
	/**
	 * Method is used to start animation on imageView
	 */
	protected void startViewAnim() {
		Timer mTimerView = new Timer();
		mTimerView.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
	
					}
				});
				
			}
		}, Config.VIEW_TIMER, Config.VIEW_TIMER);
	}	
	
	/**
	 * Method is used to start a repeating glow animation
	 * @param param Button widget
	 */
	protected void startButtonAnim(final Button... param) {	
		Animation animation = new AlphaAnimation(1.0F, 0.4F);
		animation.setDuration(400);
		animation.setInterpolator(new LinearInterpolator());
		// Repeat animation infinitely
		animation.setRepeatCount(Animation.INFINITE);
		// Reverse animation at the end so the button will fade back in
		animation.setRepeatMode(Animation.REVERSE);
		for (Button mBtn : param) {
			mBtn.startAnimation(animation);
		}
		
	}
	
	/**
	 * Method is used to start a repeating glow animation
	 * @param param Button widget
	 */
	protected void startButtonAnim(final ImageView... param) {	
		Animation animation = new AlphaAnimation(1.0F, 0.4F);
		animation.setDuration(400);
		animation.setInterpolator(new LinearInterpolator());
		// Repeat animation infinitely
		animation.setRepeatCount(Animation.INFINITE);
		// Reverse animation at the end so the button will fade back in
		animation.setRepeatMode(Animation.REVERSE);
		for (ImageView iv : param) {
			iv.startAnimation(animation);
		}
		
	}
	
	/**
	 * Method is used to clear button animation
	 * @param btn
	 */
	protected void clearButtonAnim(final Button... param) {
		for (Button mBtn : param) {
			mBtn.clearAnimation();
		}
	}
	
	/**
	 * Method is used to clear button animation
	 * @param btn
	 */
	protected void clearButtonAnim(final ImageView... param) {
		for (ImageView iv : param) {
			iv.clearAnimation();
		}
	}

	/**
	 * Method is used to return a list of words use for
	 * learning activities
	 * @param wordBank
	 * @param level
	 * @return
	 */
	protected List<String> getWordBank(String[] wordBank, int level) {
		List<String> mWordBank = new ArrayList<String>();

		for (int i = 0; i < wordBank.length; i++) {
			String metaStr = wordBank[i].toString().trim();
			if (level >= 0 && level <= 3) {
				if (metaStr.length() >= 3 && metaStr.length() <= 5) {
					mWordBank.add(metaStr);
				}
			} else if (level >= 4 && level <= 7) {
				if (metaStr.length() >= 4 && metaStr.length() <= 7) {
					mWordBank.add(metaStr);
				}
			} else if (level >= 8) {
				if (metaStr.length() >= 6 && metaStr.length() <= 9) {
					mWordBank.add(metaStr);
				}
			}	
		}

		for (int i = 0; i < mWordBank.size(); i++) {
			Logger.i(TAG, mWordBank.get(i).toString());
		}
		
		return mWordBank;		
	}
}
