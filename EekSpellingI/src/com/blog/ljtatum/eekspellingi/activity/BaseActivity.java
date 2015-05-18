package com.blog.ljtatum.eekspellingi.activity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.MediaStore.Images;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.enums.WordCategory;
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.util.Config;
import com.blog.ljtatum.eekspellingi.util.Utils;

public class BaseActivity extends Activity implements OnInitListener {
	private final static String TAG = BaseActivity.class.getSimpleName();

	private Context mContext;
	protected WordCategory mWordCategory;
	private static TextToSpeech textToSpeech;
	private static HashMap<String, String> map = new HashMap<String, String>();

	/**
	 * Method is used to re-direct to different Activity with a
	 * transition animation slide in from left
	 *
	 * @param context
	 * @param activity
	 */
	protected void goToActivityAnimLeft(Context context, Class<?> activity, int level) {
		Intent intent = new Intent(context, activity);
		if (level >= 0) {
			intent.putExtra(Constants.LV_SELECTED, level);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		// transition animation
		overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
	}
	
	/**
	 * Method is used to re-direct to different Activity with a
	 * transition animation slide in from right
	 *
	 * @param context
	 * @param activity
	 */
	protected void goToActivityAnimRight(Context context, Class<?> activity, int level) {
		Intent intent = new Intent(context, activity);
		if (level >= 0) {
			intent.putExtra(Constants.LV_SELECTED, level);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		// transition animation
		overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
	}	
	
	/**
	 * Method is used to re-direct to different Activity
	 *
	 * @param context
	 * @param activity
	 */
	protected void goToActivity(Context context, Class<?> activity, int level) {
		Intent intent = new Intent(context, activity);
		if (level >= 0) {
			intent.putExtra(Constants.LV_SELECTED, level);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}

	/**
	 * Method is used to re-direct user to the app store
	 *
	 * @param pkgName
	 */
	protected void goToStore(String pkgName) {
		try {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.MARKET + pkgName)));
		} catch (android.content.ActivityNotFoundException anfe) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GOOGLE_PLAY + pkgName)));

		}
	}

	/**
	 * Method is used to set an initial banner
	 *
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
	 *
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
	 *
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
	 *
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
	 * Method is used to shake a view
	 * @param context
	 * @param param
	 */
	protected void startShakeAnim(Context context, final View... param) {
		Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_shake);
		for (View v : param) {
			v.startAnimation(animation);
		}
	}
	
	/**
	 * Method is used to hide virtual keyboard
	 */
	protected void hideKeyboard() {
		// hide virtual keyboard 						
		InputMethodManager imm = (InputMethodManager) getSystemService(
				Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	}

	/**
	 * Method is used to clear button animation
	 *
	 * @param btn
	 */
	protected void clearButtonAnim(final Button... param) {
		for (Button mBtn : param) {
			mBtn.clearAnimation();
		}
	}

	/**
	 * Method is used to clear button animation
	 *
	 * @param btn
	 */
	protected void clearButtonAnim(final ImageView... param) {
		for (ImageView iv : param) {
			iv.clearAnimation();
		}
	}

	/**
	 * Method is used to return a list of words used for learning activities
	 * that uses a level parameter to filter out words of specific length
	 *
	 * @param wordBank
	 * @param level
	 * @return
	 */
	protected List<String> getWordBank(String[] wordBank, int level, boolean reverseLengthOrder) {
		List<String> mWordBank = new ArrayList<String>();

		// populate word bank based on length
		for (int i = 0; i < wordBank.length; i++) {
			String metaStr = wordBank[i].toString().trim();
			if (level >= 0 && level <= 3) {
				if (!reverseLengthOrder) {
					if (metaStr.length() >= 3 && metaStr.length() <= 5) {
						mWordBank.add(metaStr);
					}
				} else {
					if (metaStr.length() >= 6 && metaStr.length() <= 9) {
						mWordBank.add(metaStr);
					}
				}			
			} else if (level >= 4 && level <= 7) {
				if (metaStr.length() >= 4 && metaStr.length() <= 7) {
					mWordBank.add(metaStr);
				}
			} else if (level >= 8) {
				if (!reverseLengthOrder) {
					if (metaStr.length() >= 6 && metaStr.length() <= 9) {
						mWordBank.add(metaStr);
					}
				} else {
					if (metaStr.length() >= 3 && metaStr.length() <= 5) {
						mWordBank.add(metaStr);
					}
				}				
			}
		}

		if (Constants.DEBUG && Constants.DEBUG_HIGH_VERBOSITY) {
			for (int i = 0; i < mWordBank.size(); i++) {
				Logger.i(TAG, mWordBank.get(i).toString());
			}
		}

		return mWordBank;
	}
	
	/**
	 * Method is used to randomly return an array of words for learning activities
	 * that uses a level parameter to filter out words of specific category. The 
	 * returned array is a full array that should be truncated
	 * @param level
	 * @return
	 */
	protected String[] getWordBank(int level) {
		Random r = new Random();
		int rand = r.nextInt(6);	
		String[] arryTempA;
		String[] arryTempB;
		if (level >= 0 && level <= 3) {
			if (rand == 0) {
				// object 
				mWordCategory = WordCategory.CATEGORY_OBJECT;
				arryTempA = getResources().getStringArray(R.array.arryWordBankObj);
				return arryTempA;
			} else if (rand == 1) {
				// place
				mWordCategory = WordCategory.CATEGORY_PLACE;
				arryTempA = getResources().getStringArray(R.array.arryWordBankPlaces);
				return arryTempA;
			} else if (rand == 2) {
				// number
				mWordCategory = WordCategory.CATEGORY_NUMBER;
				arryTempA = getResources().getStringArray(R.array.arryWordBankNumbers);
				return arryTempA;
			} else if (rand == 3) {
				// color
				mWordCategory = WordCategory.CATEGORY_COLOR;
				arryTempA = getResources().getStringArray(R.array.arryWordBankColors);
				return arryTempA;
			} else if (rand == 4) {
				// place + number
				mWordCategory = WordCategory.CATEGORY_PLACE_NUMBER;
				arryTempA = getResources().getStringArray(R.array.arryWordBankPlaces);
				arryTempB = getResources().getStringArray(R.array.arryWordBankNumbers);
				return Utils.concatenate(arryTempA, arryTempB);
			} else {
				// color + number 
				mWordCategory = WordCategory.CATEGORY_COLOR_NUMBER;
				arryTempA = getResources().getStringArray(R.array.arryWordBankColors);
				arryTempB = getResources().getStringArray(R.array.arryWordBankNumbers);			
				return Utils.concatenate(arryTempA, arryTempB);
			}
		} else if (level >= 4 && level <= 7) {
			if (rand == 0) {
				// object 
				mWordCategory = WordCategory.CATEGORY_OBJECT;
				arryTempA = getResources().getStringArray(R.array.arryWordBankObj);
				return arryTempA;
			} else if (rand == 1) {
				// action
				mWordCategory = WordCategory.CATEGORY_ACTION;
				arryTempA = getResources().getStringArray(R.array.arryWordBankActions);
				return arryTempA;
			} else if (rand == 2) {
				// animal
				mWordCategory = WordCategory.CATEGORY_ANIMAL;
				arryTempA = getResources().getStringArray(R.array.arryWordBankAnimals);
				return arryTempA;
			} else if (rand == 3) {
				// object + animal
				mWordCategory = WordCategory.CATEGORY_OBJECT_ANIMAL;
				arryTempA = getResources().getStringArray(R.array.arryWordBankObj);
				arryTempB = getResources().getStringArray(R.array.arryWordBankAnimals);
				return Utils.concatenate(arryTempA, arryTempB);
			} else if (rand == 4) {				
				// day + month
				mWordCategory = WordCategory.CATEGORY_DAY_MONTH;
				arryTempA = getResources().getStringArray(R.array.arryWordBankDays);
				arryTempB = getResources().getStringArray(R.array.arryWordBankMonths);
				return Utils.concatenate(arryTempA, arryTempB);			
			} else {
				// action + animal
				mWordCategory = WordCategory.CATEGORY_ACTION_ANIMAL;
				arryTempA = getResources().getStringArray(R.array.arryWordBankActions);
				arryTempB = getResources().getStringArray(R.array.arryWordBankAnimals);
				return Utils.concatenate(arryTempA, arryTempB);	
			}
		} else if (level >= 8) {
			if (rand == 0) {
				// object
				mWordCategory = WordCategory.CATEGORY_OBJECT;
				arryTempA = getResources().getStringArray(R.array.arryWordBankObj);
				return arryTempA;
			} else if (rand == 1) {
				// career
				mWordCategory = WordCategory.CATEGORY_CAREER;
				arryTempA = getResources().getStringArray(R.array.arryWordBankCareer);
				return arryTempA;
			} else if (rand == 2) {
				// math
				mWordCategory = WordCategory.CATEGORY_MATH;
				arryTempA = getResources().getStringArray(R.array.arryWordBankMath);
				return arryTempA;
			} else if (rand == 3) {	
				// science
				mWordCategory = WordCategory.CATEGORY_SCIENCE;
				arryTempA = getResources().getStringArray(R.array.arryWordBankScience);
				return arryTempA;
			} else if (rand == 4) {	
				// math + science
				mWordCategory = WordCategory.CATEGORY_MATH_SCIENCE;
				arryTempA = getResources().getStringArray(R.array.arryWordBankMath);
				arryTempB = getResources().getStringArray(R.array.arryWordBankScience);
				return Utils.concatenate(arryTempA, arryTempB);
			} else {
				// career + science
				mWordCategory = WordCategory.CATEGORY_CAREER_SCIENCE;
				arryTempA = getResources().getStringArray(R.array.arryWordBankCareer);
				arryTempB = getResources().getStringArray(R.array.arryWordBankScience);
				return Utils.concatenate(arryTempA, arryTempB);
			}
		}
		return getResources().getStringArray(R.array.arryWordBankObj);
	}

	/**
	 * Initialize Text-To-Speech engine
	 *
	 * @param context
	 */
	protected void initTTS(Context context) {
		mContext = context;
		textToSpeech = new TextToSpeech(context, (OnInitListener) context);
		textToSpeech.setLanguage(Locale.US);
		textToSpeech.setPitch(10 / 10);
		textToSpeech.setSpeechRate(17 / 12);
	}

	/**
	 * Method is used to speak the String using the specified queuing strategy and speech parameters
	 *
	 * @param text
	 */
	@SuppressWarnings("deprecation")
	protected static void speakText(String text) {
		if (textToSpeech.isSpeaking()) {
			return;
		}
		textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, map);
	}

	/**
	 * Method is used to stop the TTS Engine
	 */
	protected void stopTTS() {
		while (textToSpeech.isSpeaking()) {
			textToSpeech.stop();
		}
	}

	/**
	 * Method is used to destroy the TTS Engine
	 */
	protected void destroyTTS() {
		textToSpeech.stop();
		textToSpeech.shutdown();
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if (status == TextToSpeech.SUCCESS) {
			map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");

		} else if (status == TextToSpeech.ERROR) {
			// initialization of TTS failed so reinitialize new TTS Engine
			Logger.e(TAG, "Initialization of TTS Engine fail");
			initTTS(mContext);
		}
	}
	
	/**
	 * Method is used to vibrate phone
	 */
	protected void vibrate(Context context, int milliseconds) {
		Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(milliseconds);
	}
	
	protected Uri getImageUri(Context context, Bitmap bitmap) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(context.getContentResolver(), bitmap, "image", null);
		return Uri.parse(path);
	}
}
