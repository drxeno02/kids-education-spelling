package com.blog.ljtatum.eekspellingi.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.anim.Shimmer;
import com.blog.ljtatum.eekspellingi.anim.ShimmerTextView;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.enums.WordCategory;
import com.blog.ljtatum.eekspellingi.helper.Messages;
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.MusicUtils;
import com.blog.ljtatum.eekspellingi.util.ShareAppUtil;
import com.blog.ljtatum.eekspellingi.util.Utils;
import com.blog.ljtatum.eekspellingi.view.CircleImageView;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

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
	private boolean isController = false;
	private boolean[] arrySelected = new boolean[25];
	private int mLevel = 0, mCorrectLetters = 0, mIncorrectLetters = 0, mSolvedWords = 0;
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
			mLevel = intent.getIntExtra(Constants.LV_SELECTED, 0);
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
		case R.id.pos0:
			if (!isController) {
				if (!arrySelected[0]) {
					checkLetter(0);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos1:
			if (!isController) {
				if (!arrySelected[1]) {
					checkLetter(1);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos2:
			if (!isController) {
				if (!arrySelected[2]) {
					checkLetter(2);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos3:
			if (!isController) {
				if (!arrySelected[3]) {
					checkLetter(3);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos4:
			if (!isController) {
				if (!arrySelected[4]) {
					checkLetter(4);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos5:
			if (!isController) {
				if (!arrySelected[5]) {
					checkLetter(5);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos6:
			if (!isController) {
				if (!arrySelected[6]) {
					checkLetter(6);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos7:
			if (!isController) {
				if (!arrySelected[7]) {
					checkLetter(7);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos8:
			if (!isController) {
				if (!arrySelected[8]) {
					checkLetter(8);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos9:
			if (!isController) {
				if (!arrySelected[9]) {
					checkLetter(9);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos10:
			if (!isController) {
				if (!arrySelected[10]) {
					checkLetter(10);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos11:
			if (!isController) {
				if (!arrySelected[11]) {
					checkLetter(11);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos12:
			if (!isController) {
				if (!arrySelected[12]) {
					checkLetter(12);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos13:
			if (!isController) {
				if (!arrySelected[13]) {
					checkLetter(13);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos14:
			if (!isController) {
				if (!arrySelected[14]) {
					checkLetter(14);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos15:
			if (!isController) {
				if (!arrySelected[15]) {
					checkLetter(15);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;
		case R.id.pos16:
			if (!isController) {
				if (!arrySelected[16]) {
					checkLetter(16);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;			
		case R.id.pos17:
			if (!isController) {
				if (!arrySelected[17]) {
					checkLetter(17);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;			
		case R.id.pos18:
			if (!isController) {
				if (!arrySelected[18]) {
					checkLetter(18);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;			
		case R.id.pos19:
			if (!isController) {
				if (!arrySelected[19]) {
					checkLetter(19);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;			
		case R.id.pos20:
			if (!isController) {
				if (!arrySelected[20]) {
					checkLetter(20);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;			
		case R.id.pos21:
			if (!isController) {
				if (!arrySelected[21]) {
					checkLetter(21);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;			
		case R.id.pos22:
			if (!isController) {
				if (!arrySelected[22]) {
					checkLetter(22);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;			
		case R.id.pos23:
			if (!isController) {
				if (!arrySelected[23]) {
					checkLetter(23);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;			
		case R.id.pos24:
			if (!isController) {
				if (!arrySelected[24]) {
					checkLetter(24);
				} else {
					vibrate(mContext, 500);
				}
			}
			break;				
		case R.id.iv_back:
			prepareMusicToChange();
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
	 * Method is used to prepare music to change
	 */
	private void prepareMusicToChange() {
		// prepare music to change
		if (sharedPref.getBooleanPref(Constants.PREF_MUSIC, true)) {
			try {
				MusicUtils.stop();
			} catch (IllegalStateException ise) {
				ise.printStackTrace();
			}
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
		
		// set image source
		setTiles();

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
		}

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
		while (arryPath.size() < num) {
			int pos = r.nextInt(25);
			while (arryPath.contains(pos)) {
				pos = r.nextInt(25);
			}
			arryPath.add(pos);
			Logger.i("TEST", "pos: " + pos);
		}
		
		// setup words to solve views
		if (num == 3) {
			Utils.setViewVisibility(true, v1, v2, v3,
					tvAnswer1, tvAnswer2, tvAnswer3);
		} else if (num == 4) {
			Utils.setViewVisibility(true, v1, v2, v3, v4,
					tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4);
		} else if (num == 5) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, v5,
					tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5);
		} else if (num == 6) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, v5, v6,
					tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5,
					tvAnswer6);
		} else if (num == 7) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, v5, v6, v7,
					tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5,
					tvAnswer6, tvAnswer7);
		} else if (num == 8) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, v5, v6, v7, v8,
					tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5,
					tvAnswer6, tvAnswer7, tvAnswer8);
		} else if (num == 9) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, v5, v6, v7, v8, v9,
					tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5,
					tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9);
		}		
	}
	
	/**
	 * Method is used for resetting visibility on views
	 */
	private void resetVisibility() {
		// reset correct and incorrect trackers
		mCorrectLetters = 0;
		mIncorrectLetters = 0;		
		
		// clear letter views
		Utils.clearText(tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5, 
				tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9);

		// reset views
		Utils.setViewVisibility(false, v1, v2, v3, v4, v5, v6, v7, v8, v9,
				tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5,
				tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9);
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
	 * Method will return the image version of the letter param
	 * @param index
	 * @return
	 */
	private int getDrawableLetter(int index) {	
		Logger.e("TEST", "String.valueOf(arryLetters[index]: " + String.valueOf(arryLetters[index]));
		if (String.valueOf(arryLetters[index]).equalsIgnoreCase("a")) {
			return R.drawable.a;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("b")) {
			return R.drawable.b;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("c")) {
			return R.drawable.c;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("d")) {
			return R.drawable.d;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("e")) {
			return R.drawable.e;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("f")) {
			return R.drawable.f;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("g")) {
			return R.drawable.g;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("h")) {
			return R.drawable.h;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("i")) {
			return R.drawable.i;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("j")) {
			return R.drawable.j;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("k")) {
			return R.drawable.k;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("l")) {
			return R.drawable.l;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("m")) {
			return R.drawable.m;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("n")) {
			return R.drawable.n;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("o")) {
			return R.drawable.o;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("p")) {
			return R.drawable.p;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("q")) {
			return R.drawable.q;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("r")) {
			return R.drawable.r;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("s")) {
			return R.drawable.s;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("t")) {
			return R.drawable.t;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("u")) {
			return R.drawable.u;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("v")) {
			return R.drawable.v;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("w")) {
			return R.drawable.w;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("x")) {
			return R.drawable.x;
		} else if (String.valueOf(arryLetters[index]).equalsIgnoreCase("y")) {
			return R.drawable.y;
		}
		return R.drawable.z;
	}	
	
	/**
	 * Method will return the reward image
	 * @param index
	 * @return
	 */
	private int getDrawableReward(int index) {	
		if (index == 0) {
			return R.drawable.reward_one;
		} else if (index == 1) {
			return R.drawable.reward_two;
		} else if (index == 2) {
			return R.drawable.reward_three;
		} else if (index == 3) {
			return R.drawable.reward_four;
		} else if (index == 4) {
			return R.drawable.reward_five;
		} else if (index == 5) {
			return R.drawable.reward_six;
		} else if (index == 6) {
			return R.drawable.reward_seven;
		} else if (index == 7) {
			return R.drawable.reward_eight;
		} else if (index == 8) {
			return R.drawable.reward_nine;
		} else if (index == 9) {
			return R.drawable.reward_ten;
		} else if (index == 10) {
			return R.drawable.reward_eleven;
		} else if (index == 11) {
			return R.drawable.reward_twelve;
		}
		return R.drawable.a;		
	}	
	
	/**
	 * Method is used to check the selected letter from the 
	 * word bank with the possible letters of the correct answer
	 * @param pos
	 */
	private void checkLetter(int pos) {
		isController = true;
		if (arryPath.contains(pos)) {
			if (pos == 0) {
				iv1.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 1) {
				iv2.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 2) {	
				iv3.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 3) {	
				iv4.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 4) {	
				iv5.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 5) {	
				iv6.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 6) {	
				iv7.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 7) {	
				iv8.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 8) {	
				iv9.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 9) {	
				iv10.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 10) {	
				iv11.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 11) {	
				iv12.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 12) {	
				iv13.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 13) {	
				iv14.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 14) {	
				iv15.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 15) {	
				iv16.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 16) {		
				iv17.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 17) {		
				iv18.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 18) {		
				iv19.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 19) {		
				iv20.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 20) {		
				iv21.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 21) {	
				iv22.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 22) {		
				iv23.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 23) {	
				iv24.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			} else if (pos == 24) {		
				iv25.setImageResource(getDrawableLetter(arryPath.indexOf(pos)));
			}		
			
			String c = String.valueOf(arryLetters[arryPath.indexOf(pos)]);
			ArrayList<Integer> arryMatch = new ArrayList<Integer>();
			if (mWord.contains(c)) {
				MusicUtils.playSound(mContext, R.raw.correct);
				
				// check the entire word for instances of the selected letter
				for (int i = -1; (i = mWord.indexOf(c, i + 1)) != -1;) {
					arryMatch.add(i);
				}
				
				// check the entire tile map for instances of the selected letter
				if (arryMatch.size() > 1) {
					for (int i = 0; i < arryMatch.size(); i++) {
						int value = arryMatch.get(i);
						int getPos = arryPath.get(value);
						
						if (getPos == 0) {
							iv1.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 1) {
							iv2.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 2) {	
							iv3.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 3) {	
							iv4.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 4) {	
							iv5.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 5) {	
							iv6.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 6) {	
							iv7.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 7) {	
							iv8.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 8) {	
							iv9.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 9) {	
							iv10.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 10) {	
							iv11.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 11) {	
							iv12.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 12) {	
							iv13.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 13) {	
							iv14.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 14) {	
							iv15.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 15) {	
							iv16.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 16) {		
							iv17.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 17) {		
							iv18.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 18) {		
							iv19.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 19) {		
							iv20.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 20) {		
							iv21.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 21) {	
							iv22.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 22) {		
							iv23.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 23) {	
							iv24.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						} else if (getPos == 24) {		
							iv25.setImageResource(getDrawableLetter(arryPath.indexOf(getPos)));
						}
					}
				}
		
				// set letter to correct position
				for (int i = 0; i < arryMatch.size(); i++) {
					mCorrectLetters++;
					
					if (arryMatch.get(i) == 0) {
						tvAnswer1.setText(c);
						startShimmerAnimation(tvAnswer1);
					} else if (arryMatch.get(i) == 1) {
						tvAnswer2.setText(c);
						startShimmerAnimation(tvAnswer2);
					} else if (arryMatch.get(i) == 2) {
						tvAnswer3.setText(c);
						startShimmerAnimation(tvAnswer3);
					} else if (arryMatch.get(i) == 3) {
						tvAnswer4.setText(c);
						startShimmerAnimation(tvAnswer4);
					} else if (arryMatch.get(i) == 4) {
						tvAnswer5.setText(c);
						startShimmerAnimation(tvAnswer5);
					} else if (arryMatch.get(i) == 5) {
						tvAnswer6.setText(c);
						startShimmerAnimation(tvAnswer6);
					} else if (arryMatch.get(i) == 6) {
						tvAnswer7.setText(c);
						startShimmerAnimation(tvAnswer7);
					} else if (arryMatch.get(i) == 7) {
						tvAnswer8.setText(c);
						startShimmerAnimation(tvAnswer8);
					} else if (arryMatch.get(i) == 8) {
						tvAnswer9.setText(c);
						startShimmerAnimation(tvAnswer9);
					}
					
					if (isController) {
						// delay before allowing tile selection
						mHandler.postDelayed(new Runnable() {
							@Override
							public void run() {
								isController = false;
							}
						}, 2500);
					}
				}
			}
		} else {
			vibrate(mContext, 500);
			mIncorrectLetters++;
			MusicUtils.playSound(mContext, R.raw.incorrect);
			String temp = Messages.msgPath(false, true);
			Crouton.showText(mActivity, temp, Style.ALERT);
			speakText(temp);
			
			if (pos == 0) {
				iv1.setImageResource(R.drawable.wrong);
			} else if (pos == 1) {
				iv2.setImageResource(R.drawable.wrong);
			} else if (pos == 2) {	
				iv3.setImageResource(R.drawable.wrong);
			} else if (pos == 3) {	
				iv4.setImageResource(R.drawable.wrong);
			} else if (pos == 4) {	
				iv5.setImageResource(R.drawable.wrong);
			} else if (pos == 5) {	
				iv6.setImageResource(R.drawable.wrong);
			} else if (pos == 6) {	
				iv7.setImageResource(R.drawable.wrong);
			} else if (pos == 7) {	
				iv8.setImageResource(R.drawable.wrong);
			} else if (pos == 8) {	
				iv9.setImageResource(R.drawable.wrong);
			} else if (pos == 9) {	
				iv10.setImageResource(R.drawable.wrong);
			} else if (pos == 10) {	
				iv11.setImageResource(R.drawable.wrong);
			} else if (pos == 11) {	
				iv12.setImageResource(R.drawable.wrong);
			} else if (pos == 12) {	
				iv13.setImageResource(R.drawable.wrong);
			} else if (pos == 13) {	
				iv14.setImageResource(R.drawable.wrong);
			} else if (pos == 14) {	
				iv15.setImageResource(R.drawable.wrong);
			} else if (pos == 15) {	
				iv16.setImageResource(R.drawable.wrong);
			} else if (pos == 16) {		
				iv17.setImageResource(R.drawable.wrong);
			} else if (pos == 17) {		
				iv18.setImageResource(R.drawable.wrong);
			} else if (pos == 18) {		
				iv19.setImageResource(R.drawable.wrong);
			} else if (pos == 19) {		
				iv20.setImageResource(R.drawable.wrong);
			} else if (pos == 20) {		
				iv21.setImageResource(R.drawable.wrong);
			} else if (pos == 21) {	
				iv22.setImageResource(R.drawable.wrong);
			} else if (pos == 22) {		
				iv23.setImageResource(R.drawable.wrong);
			} else if (pos == 23) {	
				iv24.setImageResource(R.drawable.wrong);
			} else if (pos == 24) {		
				iv25.setImageResource(R.drawable.wrong);
			}	
			
			if (mIncorrectLetters >= 10) {
				// level failed
				launchPrevActivity(2500);
			}
			
			if (isController) {
				// delay before allowing tile selection
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						isController = false;
					}
				}, 2500);
			}
		}
		
		// check if puzzle is solved
		if (mWord.length() == mCorrectLetters) {
			Logger.i(TAG, "word solved");
			mSolvedWords++;
			
			// speak the completed word
			reviewWord();
			
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {				
					speakText(mWord);
					if (mSolvedWords >= 3) {
						// TODO: play sounds, animations, messaging and add rewards for completing level
						boolean isLvUnlockRecent = false;
						String strPrefName = Constants.LV_COUNT.concat("_" + mLevel);
						String strPrefNameUnlock = Constants.LV_UNLOCKED.concat("_" + (mLevel+4));
						int lvCount = sharedPref.getIntPref(strPrefName, 0);				
						if (lvCount >= 3 && mLevel < 8) {
							boolean isUnlock = sharedPref.getBooleanPref(strPrefNameUnlock, false);
							if (!isUnlock) {
								isLvUnlockRecent = true;
								sharedPref.setPref(strPrefNameUnlock, true);											
							}
						}		
						lvCount++;
						sharedPref.setPref(strPrefName, lvCount);					
						startRewardAnim(isLvUnlockRecent);
					} else {
						// delay before generating the next level
						mHandler.postDelayed(new Runnable() {
							@Override
							public void run() {						
								generateLevel();
							}
						}, 2500);
					}		
				}
			}, 4000);			
		}	
	}
	
	/**
	 * Method is used to speak and animate the completed word
	 */
	private void reviewWord() {
		Crouton.showText(mActivity, mWord, Style.INFO);
		if (tvAnswer1.getVisibility() == View.VISIBLE) {
			startShimmerAnimation(tvAnswer1);
		} 
		
		if (tvAnswer2.getVisibility() == View.VISIBLE) {
			startShimmerAnimation(tvAnswer2);
		}

		if (tvAnswer3.getVisibility() == View.VISIBLE) {
			startShimmerAnimation(tvAnswer3);
		}
		
		if (tvAnswer4.getVisibility() == View.VISIBLE) {
			startShimmerAnimation(tvAnswer4);
		}		
	
		if (tvAnswer5.getVisibility() == View.VISIBLE) {
			startShimmerAnimation(tvAnswer5);
		}
		
		if (tvAnswer6.getVisibility() == View.VISIBLE) {
			startShimmerAnimation(tvAnswer6);
		}
		
		if (tvAnswer7.getVisibility() == View.VISIBLE) {
			startShimmerAnimation(tvAnswer7);
		}
		
		if (tvAnswer8.getVisibility() == View.VISIBLE) {
			startShimmerAnimation(tvAnswer8);
		}	
		
		if (tvAnswer9.getVisibility() == View.VISIBLE) {
			startShimmerAnimation(tvAnswer9);
		}	
	}	
	
	/**
	 * Method is used after failing level to go back to SelectActivity
	 * @param time
	 */
	private void launchPrevActivity(final int time) {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				goToActivityAnimRight(mContext, SelectActivity.class, -1);
			}
		}, time);
	}	
	
	/**
	 * Method is used to
	 * @param isLvUnlockRecent
	 */
	@SuppressLint("InflateParams")
	private void startRewardAnim(boolean isLvUnlockRecent) {
		final Dialog mDialog = new Dialog(mContext);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialog.setCancelable(false);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View mView = inflater.inflate(R.layout.custom_popup, null);
		mDialog.setContentView(mView);
		TextView tvMeta = (TextView) mView.findViewById(R.id.tv_meta);
		CircleImageView iv = (CircleImageView) mView.findViewById(R.id.iv1);
		iv.setBorderColor(getResources().getColor(R.color.white));
		iv.setBorderWidth(5);
		Button btnConfirm = (Button) mView.findViewById(R.id.btn_confirm);
		
		// setup rewards
		int rewardCount = 0;
		int mReward = r.nextInt(12);
		String strPrefName = Constants.REWARDS.concat("_" + mReward);
		rewardCount = sharedPref.getIntPref(strPrefName, 0);
		rewardCount++;
		sharedPref.setPref(strPrefName, rewardCount);
		iv.setImageResource(getDrawableReward(mReward));				
		
		// set text message
		if (isLvUnlockRecent) {
			int levelUnlocked = mLevel + 4;
			tvMeta.setText("Level " + levelUnlocked + " is now unlocked!");
		} else {
			tvMeta.setText("Good job! Keep practicing to learn new words");
		}

		// display dialog
		mDialog.show();
		
		btnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				prepareMusicToChange();
				mDialog.dismiss();
				goToActivityAnimRight(mContext, SelectActivity.class, -1);
			} 		
		});
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
			if (mLevel == 1) {
				MusicUtils.start(mContext, 3);
			} else if (mLevel == 5) {
				MusicUtils.start(mContext, 4);
			} else {
				MusicUtils.start(mContext, 5);
			}
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
		Crouton.cancelAllCroutons();
		Crouton.clearCroutonsForActivity(this);
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		prepareMusicToChange();
		super.onBackPressed();
		// transition animation
		overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
	}

}
