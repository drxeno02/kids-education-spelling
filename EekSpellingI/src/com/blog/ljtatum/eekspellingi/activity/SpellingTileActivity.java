package com.blog.ljtatum.eekspellingi.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.anim.Shimmer;
import com.blog.ljtatum.eekspellingi.anim.ShimmerTextView;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.enums.WordCategory;
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.MusicUtils;
import com.blog.ljtatum.eekspellingi.util.ShareAppUtil;
import com.blog.ljtatum.eekspellingi.util.Utils;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class SpellingTileActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = SpellingTileActivity.class.getSimpleName();
	
	private Activity mActivity;
	private Context mContext;
	private ImageView ivBack, ivBanner, iv1, iv2, iv3, iv4, iv5, iv6, iv7, 
			iv8, iv9, iv10, iv11, iv12, iv13, iv14, iv15, iv16, iv17, iv18, 
			iv19, iv20, iv21, iv22, iv23, iv24, iv25;
	private View v1, v2, v3, v4, v5, v6, v7, v8, v9;
	private TextView tvHint;
	private LinearLayout pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8, pos9,
			pos10, pos11, pos12, pos13, pos14, pos15, pos16, pos17, pos18, pos19,
			pos20, pos21, pos22, pos23, pos24, pos25;
	private ShimmerTextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4,
			tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9;
	private Random r;
	private int mLevel = 0, mSolvedWords = 0;
	private String mWord;
	private List<String> mArryWordBank, arryPrev;
	private List<Integer> arryPath;

	private ShareAppUtil shareApp;
	private SharedPref sharedPref;

	private Handler mHandler;
	private char[] arryLetters;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spelling_tile);
		getIds();
	}

	private void getIds() {
		mActivity = SpellingTileActivity.this;
		mContext = SpellingTileActivity.this;
		shareApp = new ShareAppUtil();
		sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
		mHandler = new Handler();
		arryPath = new ArrayList<Integer>();
		r = new Random();
		ivBanner = (ImageView) findViewById(R.id.iv_banner);
		ivBack = (ImageView) findViewById(R.id.iv_back);
		v1 = findViewById(R.id.v1);
		v2 = findViewById(R.id.v2);
		v3 = findViewById(R.id.v3);
		v4 = findViewById(R.id.v4);
		v5 = findViewById(R.id.v5);
		v6 = findViewById(R.id.v6);
		v7 = findViewById(R.id.v7);
		v8 = findViewById(R.id.v8);
		v9 = findViewById(R.id.v9);
		iv1 = (ImageView) findViewById(R.id.iv0);
		iv2 = (ImageView) findViewById(R.id.iv1);
		iv3 = (ImageView) findViewById(R.id.iv2);
		iv4 = (ImageView) findViewById(R.id.iv3);
		iv5 = (ImageView) findViewById(R.id.iv4);
		iv6 = (ImageView) findViewById(R.id.iv5);
		iv7 = (ImageView) findViewById(R.id.iv6);
		iv8 = (ImageView) findViewById(R.id.iv7);
		iv9 = (ImageView) findViewById(R.id.iv8);
		iv10 = (ImageView) findViewById(R.id.iv9);
		iv11 = (ImageView) findViewById(R.id.iv10);
		iv12 = (ImageView) findViewById(R.id.iv11);
		iv13 = (ImageView) findViewById(R.id.iv12);
		iv14 = (ImageView) findViewById(R.id.iv13);
		iv15 = (ImageView) findViewById(R.id.iv14);
		iv16 = (ImageView) findViewById(R.id.iv15);
		iv17 = (ImageView) findViewById(R.id.iv16);
		iv18 = (ImageView) findViewById(R.id.iv17);
		iv19 = (ImageView) findViewById(R.id.iv18);
		iv20 = (ImageView) findViewById(R.id.iv19);
		iv21 = (ImageView) findViewById(R.id.iv20);
		iv22 = (ImageView) findViewById(R.id.iv21);
		iv23 = (ImageView) findViewById(R.id.iv22);
		iv24 = (ImageView) findViewById(R.id.iv23);
		iv25 = (ImageView) findViewById(R.id.iv24);
		tvHint = (TextView) findViewById(R.id.tv_hint);
		pos1 = (LinearLayout) findViewById(R.id.pos0);
		pos2 = (LinearLayout) findViewById(R.id.pos1);
		pos3 = (LinearLayout) findViewById(R.id.pos2);
		pos4 = (LinearLayout) findViewById(R.id.pos3);
		pos5 = (LinearLayout) findViewById(R.id.pos4);
		pos6 = (LinearLayout) findViewById(R.id.pos5);
		pos7 = (LinearLayout) findViewById(R.id.pos6);
		pos8 = (LinearLayout) findViewById(R.id.pos7);
		pos9 = (LinearLayout) findViewById(R.id.pos8);
		pos10 = (LinearLayout) findViewById(R.id.pos9);
		pos11 = (LinearLayout) findViewById(R.id.pos10);
		pos12 = (LinearLayout) findViewById(R.id.pos11);
		pos13 = (LinearLayout) findViewById(R.id.pos12);
		pos14 = (LinearLayout) findViewById(R.id.pos13);
		pos15 = (LinearLayout) findViewById(R.id.pos14);
		pos16 = (LinearLayout) findViewById(R.id.pos15);
		pos17 = (LinearLayout) findViewById(R.id.pos16);
		pos18 = (LinearLayout) findViewById(R.id.pos17);
		pos19 = (LinearLayout) findViewById(R.id.pos18);
		pos20 = (LinearLayout) findViewById(R.id.pos19);
		pos21 = (LinearLayout) findViewById(R.id.pos20);
		pos22 = (LinearLayout) findViewById(R.id.pos21);
		pos23 = (LinearLayout) findViewById(R.id.pos22);
		pos24 = (LinearLayout) findViewById(R.id.pos23);
		pos25 = (LinearLayout) findViewById(R.id.pos24);
		tvAnswer1 = (ShimmerTextView) findViewById(R.id.tv_answer_1);
		tvAnswer2 = (ShimmerTextView) findViewById(R.id.tv_answer_2);
		tvAnswer3 = (ShimmerTextView) findViewById(R.id.tv_answer_3);
		tvAnswer4 = (ShimmerTextView) findViewById(R.id.tv_answer_4);
		tvAnswer5 = (ShimmerTextView) findViewById(R.id.tv_answer_5);
		tvAnswer6 = (ShimmerTextView) findViewById(R.id.tv_answer_6);
		tvAnswer7 = (ShimmerTextView) findViewById(R.id.tv_answer_7);
		tvAnswer8 = (ShimmerTextView) findViewById(R.id.tv_answer_8);
		tvAnswer9 = (ShimmerTextView) findViewById(R.id.tv_answer_9);

		ivBack.setOnClickListener(this);
		ivBanner.setOnClickListener(this);
		pos1.setOnClickListener(this);
		pos2.setOnClickListener(this);
		pos3.setOnClickListener(this);
		pos4.setOnClickListener(this);
		pos5.setOnClickListener(this);
		pos6.setOnClickListener(this);
		pos7.setOnClickListener(this);
		pos8.setOnClickListener(this);
		pos9.setOnClickListener(this);
		pos10.setOnClickListener(this);
		pos11.setOnClickListener(this);
		pos12.setOnClickListener(this);
		pos13.setOnClickListener(this);
		pos14.setOnClickListener(this);
		pos15.setOnClickListener(this);
		pos16.setOnClickListener(this);
		pos17.setOnClickListener(this);
		pos18.setOnClickListener(this);
		pos19.setOnClickListener(this);
		pos20.setOnClickListener(this);
		pos21.setOnClickListener(this);
		pos22.setOnClickListener(this);
		pos23.setOnClickListener(this);
		pos24.setOnClickListener(this);
		pos25.setOnClickListener(this);
		
		// retrieve level
		Intent intent = getIntent();
		if (!Utils.checkIfNull(intent)) {
			mLevel = intent.getIntExtra(Constants.LEVEL_SELECTED, 0);
			Logger.i(TAG, "level: " + mLevel);
		}		

		// initialize lesson
		initLesson();

		// set default banner
		setDefaultBanner(mContext, ivBanner);

	}	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.pos1:
			Logger.i(TAG, "pos1");
			break;
		case R.id.pos2:
			Logger.i(TAG, "pos2");
			break;
		case R.id.pos3:
			Logger.i(TAG, "pos3");
			break;
		case R.id.pos4:
			Logger.i(TAG, "pos4");
			break;
		case R.id.pos5:
			Logger.i(TAG, "pos5");
			break;
		case R.id.pos6:
			Logger.i(TAG, "pos6");
			break;
		case R.id.pos7:
			Logger.i(TAG, "pos7");
			break;
		case R.id.pos8:
			Logger.i(TAG, "pos8");
			break;
		case R.id.pos9:
			Logger.i(TAG, "pos9");
			break;
		case R.id.pos10:
			Logger.i(TAG, "pos10");
			break;
		case R.id.pos11:
			Logger.i(TAG, "pos11");
			break;
		case R.id.pos12:
			Logger.i(TAG, "pos12");
			break;
		case R.id.pos13:
			Logger.i(TAG, "pos13");
			break;
		case R.id.pos14:
			Logger.i(TAG, "pos14");
			break;
		case R.id.pos15:
			Logger.i(TAG, "pos15");
			break;
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
	 * Method is used to initialize the game level; sets level, default word, 
	 * and then calls method to generate the UI components.
	 *
	 * @Note Only needs to be called once
	 */
	private void initLesson() {
		// retrieve full word bank
		String[] arryWordBankFull = getWordBank(mLevel);
		
		// set hint
		if (mLevel >= 8) {
			tvHint.setText("????");
			tvHint.setTextColor(getResources().getColor(R.color.material_red_500_color_code));
		} else {
			if (mWordCategory == WordCategory.CATEGORY_OBJECT) {
				tvHint.setText("Object");
			} else if (mWordCategory == WordCategory.CATEGORY_PLACE) {	
				tvHint.setText("Place");
			} else if (mWordCategory == WordCategory.CATEGORY_NUMBER) {	
				tvHint.setText("Number");
			} else if (mWordCategory == WordCategory.CATEGORY_COLOR) {	
				tvHint.setText("Color");
			} else if (mWordCategory == WordCategory.CATEGORY_PLACE_NUMBER) {		
				tvHint.setText("Place or Number");
			} else if (mWordCategory == WordCategory.CATEGORY_COLOR_NUMBER) {		
				tvHint.setText("Color or Number");
			} else if (mWordCategory == WordCategory.CATEGORY_ACTION) {
				tvHint.setText("Action");
			} else if (mWordCategory == WordCategory.CATEGORY_ANIMAL) {
				tvHint.setText("Animal");
			} else if (mWordCategory == WordCategory.CATEGORY_OBJECT_ANIMAL) {	
				tvHint.setText("Object or Animal");
			} else if (mWordCategory == WordCategory.CATEGORY_DAY_MONTH) {
				tvHint.setText("Day or Month");
			} else if (mWordCategory == WordCategory.CATEGORY_ACTION_ANIMAL) {
				tvHint.setText("Action or Animal");				
			} else if (mWordCategory == WordCategory.CATEGORY_CAREER) {	
				tvHint.setText("Career");
			} else if (mWordCategory == WordCategory.CATEGORY_MATH) {	
				tvHint.setText("Math");
			} else if (mWordCategory == WordCategory.CATEGORY_SCIENCE) {	
				tvHint.setText("Science");	
			} else if (mWordCategory == WordCategory.CATEGORY_MATH_SCIENCE) {	
				tvHint.setText("Math or Science");
			} else if (mWordCategory == WordCategory.CATEGORY_CAREER_SCIENCE) {
				tvHint.setText("Career or Science");
			}
			tvHint.setTextColor(getResources().getColor(R.color.black));
		}	
		
		// retrieve list of usable words	
		mArryWordBank = getWordBank(arryWordBankFull, mLevel, true);
		// select a word from usable word list
		mWord = mArryWordBank.get(r.nextInt(mArryWordBank.size()));
		generateLevel();
	}
	
	/**
	 * Method is used to speak instructions
	 */
	private void speakInstructions() {
		speakText("Select a tile!");
	}

	/**
	 * Method is used to setup the game level
	 */
	private void generateLevel() {
		speakInstructions();

		// confirm that next set of words are unique
		if (!Utils.checkIfNull(arryPrev)) {
			boolean isCheck = false;
			while (!isCheck) {
				int i = 0;
				for (i = 0; i < arryPrev.size(); i++) {
					if (arryPrev.get(i).equalsIgnoreCase(mWord)) {
						mWord = mArryWordBank.get(r.nextInt(mArryWordBank.size()));
						i = 0;
					}
				}
				isCheck = true;
			}

			arryPrev.add(mWord);
			Logger.i(TAG, mWord + " //count: " + mWord.length());
		}

		// set image source
		setTiles();
		
		// set visibility of views
		setVisibility(mWord.length());
	}	
	
	/**
	 * Sets the number of needed visible views to form the correct word
	 * 
	 * @param num
	 */
	private void setVisibility(int num) {
		if (mSolvedWords > 0) {
			resetVisibility();
		}
		
		arryLetters = mWord.toCharArray();
		// reset path list
		if (arryPath.size() > 0) {
			arryPath.clear();
		}
		
		// setup letter positions on map
		if (num == 3) {
			
		}
		

	}
	
	/**
	 * Method is used for resetting visibility on views
	 */
	private void resetVisibility() {
		// reset correct and incorrect trackers

		// clear maze letter views
		Utils.clearText(tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5, 
				tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9);

		// reset maze views
		Utils.setViewVisibility(false, v1, v2, v3, v4, v5, v6, v7, v8, v9,
				tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5,
				tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9, pos1, pos2, pos3,
				pos4, pos5, pos6, pos7, pos8, pos9, pos10, pos11, pos12, pos13,
				pos14, pos15, pos16, pos17, pos18, pos19, pos20, pos21);

	}
	
	/**
	 * Method sets the tile colors of views
	 */
	private void setTiles() {
		iv1.setImageResource(getDrawableSrc(0));
		iv2.setImageResource(getDrawableSrc(1));
		iv3.setImageResource(getDrawableSrc(2));
		iv4.setImageResource(getDrawableSrc(3));
		iv5.setImageResource(getDrawableSrc(4));
		iv6.setImageResource(getDrawableSrc(5));
		iv7.setImageResource(getDrawableSrc(6));
		iv8.setImageResource(getDrawableSrc(7));
		iv9.setImageResource(getDrawableSrc(8));
		iv10.setImageResource(getDrawableSrc(9));
		iv11.setImageResource(getDrawableSrc(10));
		iv12.setImageResource(getDrawableSrc(11));
		iv13.setImageResource(getDrawableSrc(12));
		iv14.setImageResource(getDrawableSrc(13));
		iv15.setImageResource(getDrawableSrc(14));
		iv16.setImageResource(getDrawableSrc(15));
		iv17.setImageResource(getDrawableSrc(16));
		iv18.setImageResource(getDrawableSrc(17));
		iv19.setImageResource(getDrawableSrc(18));
		iv20.setImageResource(getDrawableSrc(19));
		iv21.setImageResource(getDrawableSrc(20));
		iv22.setImageResource(getDrawableSrc(21));
		iv23.setImageResource(getDrawableSrc(22));
		iv24.setImageResource(getDrawableSrc(23));
		iv25.setImageResource(getDrawableSrc(24));
	}
	
	/**
	 * Method will return a drawable 
	 * @param pos
	 * @return
	 */
	private int getDrawableSrc(int pos) {
		if (pos % 2 == 0) {
			if (mLevel == 1) {
				return R.drawable.tile_a;
			} else if (mLevel == 5) {
				return R.drawable.tile_b;
			} else {
				return R.drawable.tile_c;
			}
		}
		
		if (mLevel == 1) {
			return R.drawable.tile_b;
		} else if (mLevel == 5) {
			return R.drawable.tile_c;
		} else {
			return R.drawable.tile_d;
		}	
	}
	
	
	/**
	 * Method is used to start shimmer animation
	 * 
	 * @param stv
	 */
	@SuppressWarnings("static-method")
	private void startShimmerAnimation(ShimmerTextView stv) {
		Shimmer shimmer = new Shimmer();
		shimmer.setRepeatCount(1);
		shimmer.setDuration(1500);
		shimmer.setStartDelay(250);
		shimmer.start(stv);
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
