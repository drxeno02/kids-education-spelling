package com.blog.ljtatum.eekspellingi.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

@SuppressLint("NewApi")
public class LetterTreeActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = LetterTreeActivity.class.getSimpleName();

	private Activity mActivity;
	private Context mContext;
	private ImageView ivBack, ivBanner, ivTree, iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9;
	private View v1, v2, v3, v4, v5, v6, v7, v8, v9;
	private TextView tvHint;
	private ShimmerTextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4,
		tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9;
	private LinearLayout llWordBank, llEditAnswer;
	private Button btnSubmit;
	private EditText edtAnswer;
	private Random r;
	private int mLevel = 0, mCorrectLetters = 0, mIncorrectLetters = 0, mSolvedWords = 0;
	private String mWord, mLetterEditText;
	private boolean isController = false;
	private char[] arryJumbled = null;
	private boolean[] arrySelected = new boolean[9];
	private List<String> mArryWordBank, arryPrev;

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
		setContentView(R.layout.activity_letter_tree);
		getIds();

	}
	
	private void getIds() {
		mActivity = LetterTreeActivity.this;
		mContext = LetterTreeActivity.this;
		shareApp = new ShareAppUtil();
		sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
		mHandler = new Handler();
		mArryWordBank = new ArrayList<String>();
		arryPrev = new ArrayList<String>();
		r = new Random();
		llWordBank = (LinearLayout) findViewById(R.id.ll_word_bank);
		llEditAnswer = (LinearLayout) findViewById(R.id.ll_edit_answer);
		ivBanner = (ImageView) findViewById(R.id.iv_banner);
		ivBack = (ImageView) findViewById(R.id.iv_back);
		ivTree = (ImageView) findViewById(R.id.iv_tree);
		btnSubmit = (Button) findViewById(R.id.btn_submit);
		edtAnswer = (EditText) findViewById(R.id.edt_answer_1);
		v1 = findViewById(R.id.v1);
		v2 = findViewById(R.id.v2);
		v3 = findViewById(R.id.v3);
		v4 = findViewById(R.id.v4);
		v5 = findViewById(R.id.v5);
		v6 = findViewById(R.id.v6);
		v7 = findViewById(R.id.v7);
		v8 = findViewById(R.id.v8);
		v9 = findViewById(R.id.v9);
		iv1 = (ImageView) findViewById(R.id.iv1);
		iv2 = (ImageView) findViewById(R.id.iv2);
		iv3 = (ImageView) findViewById(R.id.iv3);
		iv4 = (ImageView) findViewById(R.id.iv4);
		iv5 = (ImageView) findViewById(R.id.iv5);
		iv6 = (ImageView) findViewById(R.id.iv6);
		iv7 = (ImageView) findViewById(R.id.iv7);
		iv8 = (ImageView) findViewById(R.id.iv8);
		iv9 = (ImageView) findViewById(R.id.iv9);
		tvHint = (TextView) findViewById(R.id.tv_hint);
		tvAnswer1 = (ShimmerTextView) findViewById(R.id.tv_answer_1);
		tvAnswer2 = (ShimmerTextView) findViewById(R.id.tv_answer_2);
		tvAnswer3 = (ShimmerTextView) findViewById(R.id.tv_answer_3);
		tvAnswer4 = (ShimmerTextView) findViewById(R.id.tv_answer_4);
		tvAnswer5 = (ShimmerTextView) findViewById(R.id.tv_answer_5);
		tvAnswer6 = (ShimmerTextView) findViewById(R.id.tv_answer_6);
		tvAnswer7 = (ShimmerTextView) findViewById(R.id.tv_answer_7);
		tvAnswer8 = (ShimmerTextView) findViewById(R.id.tv_answer_8);
		tvAnswer9 = (ShimmerTextView) findViewById(R.id.tv_answer_9);

		iv1.setOnClickListener(this);
		iv2.setOnClickListener(this);
		iv3.setOnClickListener(this);
		iv4.setOnClickListener(this);
		iv5.setOnClickListener(this);
		iv6.setOnClickListener(this);
		iv7.setOnClickListener(this);
		iv8.setOnClickListener(this);
		iv9.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		ivBanner.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		
		// set default tree
		if (mLevel == 4) {
			ivTree.setImageResource(R.drawable.tree_three);
		} else {
			ivTree.setImageResource(R.drawable.tree_four);
		}
		
		// retrieve level
		Intent intent = getIntent();
		if (!Utils.checkIfNull(intent)) {
			mLevel = intent.getIntExtra(Constants.LEVEL_SELECTED, 0);
			Logger.i(TAG, "level: " + mLevel);
		}
		
		// setup editText listener
		if (mLevel >= 8) {		
			edtAnswer.addTextChangedListener(new TextWatcher() {

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					// do nothing
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					Logger.i(TAG, "onTextChanged");
					if (edtAnswer != null && Utils.isNumericRegex(edtAnswer.getText().toString().trim())) {
						vibrate(mContext, 500);
						edtAnswer.setTextColor(getResources().getColor(R.color.material_red_500_color_code));
						String temp = getResources().getString(R.string.txt_no_numbers);
						hideKeyboard();
						Crouton.showText(mActivity, temp, Style.ALERT);
						speakText(temp);
					}
					
					if (edtAnswer.getText().toString().equals("")) {
						edtAnswer.setTextColor(getResources().getColor(R.color.black));
					}
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					// do nothing
				}
				
			});
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
		case R.id.iv1:
			if (!isController) {
				if (!arrySelected[0]) {
					checkLetter(0);
				} else {
					vibrate(mContext, 500);
				}
			}		
			break;
		case R.id.iv2:
			if (!isController) {
				if (!arrySelected[1]) {
					checkLetter(1);
				} else {
					vibrate(mContext, 500);
				}
			}			
			break;
		case R.id.iv3:
			if (!isController) {
				if (!arrySelected[2]) {
					checkLetter(2);
				} else {
					vibrate(mContext, 500);
				}
			}					
			break;
		case R.id.iv4:
			if (!isController) {
				if (!arrySelected[3]) {
					checkLetter(3);
				} else {
					vibrate(mContext, 500);
				}
			}			
			break;
		case R.id.iv5:
			if (!isController) {
				if (!arrySelected[4]) {
					checkLetter(4);
				}
			}		
			break;
		case R.id.iv6:
			if (!isController) {
				if (!arrySelected[5]) {
					checkLetter(5);
				} else {
					vibrate(mContext, 500);
				}
			}			
			break;
		case R.id.iv7:
			if (!isController) {
				if (!arrySelected[6]) {
					checkLetter(6);
				} else {
					vibrate(mContext, 500);
				}
			}		
			break;
		case R.id.iv8:
			if (!isController) {
				if (!arrySelected[7]) {
					checkLetter(7);
				} else {
					vibrate(mContext, 500);
				}
			}			
			break;
		case R.id.iv9:
			if (!isController) {
				if (!arrySelected[8]) {
					checkLetter(8);
				} else {
					vibrate(mContext, 500);
				}
			}			
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
		case R.id.btn_submit:	
			if (edtAnswer != null && !edtAnswer.getText().toString().equalsIgnoreCase("")) {
				if (!isController) {
					mLetterEditText = edtAnswer.getText().toString().toLowerCase(Locale.US).trim();
					hideKeyboard();
					checkLetter(-1);
				} else {
					vibrate(mContext, 500);
				}				
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
		mArryWordBank = getWordBank(arryWordBankFull, mLevel, false);
		// select a word from usable word list
		mWord = mArryWordBank.get(r.nextInt(mArryWordBank.size()));
		generateLevel();
	}

	/**
	 * Method is used to speak instructions
	 */
	private void speakInstructions() {
		int temp = r.nextInt(3);
		if (temp >= 0) {
			speakText("Try to solve the word!");
		} else if (temp == 1) {
			speakText("Can you solve this word?");
		} else if (temp == 2) {
			speakText("What can this word possibly be?");
		} else {
			speakText("Lets solve this word together!");
		}
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
		}

		// reset jumbled array
		if (!Utils.checkIfNull(arryJumbled)) {
			arryJumbled = null;
		}

		// set visibility of views
		setVisibility(mWord.length());

		// populate an array of jumbled letters or the letters to the correct word
		if (mLevel == 0 || mLevel == 4) {
			arryJumbled = getJumbledWord();

			// set char values of word bank
			iv1.setImageResource(getDrawableLetter(0, false));
			iv2.setImageResource(getDrawableLetter(1, false));
			iv3.setImageResource(getDrawableLetter(2, false));
			iv4.setImageResource(getDrawableLetter(3, false));
			iv5.setImageResource(getDrawableLetter(4, false));
			iv6.setImageResource(getDrawableLetter(5, false));
			iv7.setImageResource(getDrawableLetter(6, false));
			iv8.setImageResource(getDrawableLetter(7, false));
			iv9.setImageResource(getDrawableLetter(8, false));
			
			// set default selection
			for (int i = 0; i < arrySelected.length; i++) {
				arrySelected[i] = false;
			}
		} else {
			arryJumbled = mWord.toCharArray();
		}	
	}
	
	/**
	 * Method will return the image version of the letter param
	 * @param index
	 * @return
	 */
	private int getDrawableLetter(int index, boolean isWrong) {	
		if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("a")) {
			if (!isWrong) {
				return R.drawable.a;
			} else {
				return R.drawable.a_wrong;
			}		
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("b")) {
			if (!isWrong) {
				return R.drawable.b;
			} else {
				return R.drawable.b_wrong;
			}		
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("c")) {
			if (!isWrong) {
				return R.drawable.c;
			} else {
				return R.drawable.c_wrong;
			}		
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("d")) {
			if (!isWrong) {
				return R.drawable.d;
			} else {
				return R.drawable.d_wrong;
			}		
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("e")) {
			if (!isWrong) {
				return R.drawable.e;
			} else {
				return R.drawable.e_wrong;
			}		
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("f")) {
			if (!isWrong) {
				return R.drawable.f;
			} else {
				return R.drawable.f_wrong;
			}			
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("g")) {
			if (!isWrong) {
				return R.drawable.g;
			} else {
				return R.drawable.g_wrong;
			}		
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("h")) {
			if (!isWrong) {
				return R.drawable.h;
			} else {
				return R.drawable.h_wrong;
			}		
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("i")) {
			if (!isWrong) {
				return R.drawable.i;
			} else {
				return R.drawable.i_wrong;
			}		
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("j")) {
			if (!isWrong) {
				return R.drawable.j;
			} else {
				return R.drawable.j_wrong;
			}			
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("k")) {
			if (!isWrong) {
				return R.drawable.k;
			} else {
				return R.drawable.k_wrong;
			}			
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("l")) {
			if (!isWrong) {
				return R.drawable.l;
			} else {
				return R.drawable.l_wrong;
			}			
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("m")) {
			if (!isWrong) {
				return R.drawable.m;
			} else {
				return R.drawable.m_wrong;
			}			
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("n")) {
			if (!isWrong) {
				return R.drawable.n;
			} else {
				return R.drawable.n_wrong;
			}			
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("o")) {
			if (!isWrong) {
				return R.drawable.o;
			} else {
				return R.drawable.o_wrong;
			}			
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("p")) {
			if (!isWrong) {
				return R.drawable.p;
			} else {
				return R.drawable.p_wrong;	
			}		
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("q")) {
			if (!isWrong) {
				return R.drawable.q;
			} else {
				return R.drawable.q_wrong;
			}			
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("r")) {
			if (!isWrong) {
				return R.drawable.r;
			} else {
				return R.drawable.r_wrong;
			}		
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("s")) {
			if (!isWrong) {
				return R.drawable.s;
			} else {
				return R.drawable.s_wrong;
			}		
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("t")) {
			if (!isWrong) {
				return R.drawable.t;
			} else {
				return R.drawable.t_wrong;
			}			
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("u")) {
			if (!isWrong) {
				return R.drawable.u;
			} else {
				return R.drawable.u_wrong;
			}		
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("v")) {
			if (!isWrong) {
				return R.drawable.v;
			} else {
				return R.drawable.v_wrong;
			}			
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("w")) {
			if (!isWrong) {
				return R.drawable.w;
			} else {
				return R.drawable.w_wrong;
			}		
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("x")) {
			if (!isWrong) {
				return R.drawable.x;
			} else {
				return R.drawable.x_wrong;
			}			
		} else if (String.valueOf(arryJumbled[index]).equalsIgnoreCase("y")) {
			if (!isWrong) {
				return R.drawable.y;
			} else {
				return R.drawable.y_wrong;
			}			
		}
		
		if (!isWrong) {
			return R.drawable.z;
		} else {
			return R.drawable.z_wrong;
		}		
	}	

	/**
	 * Method is used to add random letters to word bank
	 *
	 * @return array of jumbled letters
	 */
	private char[] getJumbledWord() {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		// remove duplicate values from origStr
		String noDup = Utils.removeDuplicates(mWord);
		int len = 0;
		if(mLevel == 0) {
			len = 8 - noDup.length();
		} else {
			len = 10 - noDup.length();
		}

		// append random letters to word bank pool
		if (mLevel == 0) {
			for (int i = 0; i <= len; i++) {
				char temp = alphabet.charAt(r.nextInt(alphabet.length()));
				while (noDup.indexOf(temp) != -1) {
					temp = alphabet.charAt(r.nextInt(alphabet.length()));
				}
				noDup = noDup.concat(String.valueOf(temp));
			}
		} else if (mLevel == 4) {
			for (int i = 0; i <= len; i++) {
				char temp = alphabet.charAt(r.nextInt(alphabet.length()));
				while (noDup.indexOf(temp) != -1) {
					temp = alphabet.charAt(r.nextInt(alphabet.length()));
				}
				noDup = noDup.concat(String.valueOf(temp));
			}
		}

		return shuffle(noDup.toCharArray());
	}

	/**
	 * Using Fisher-Yates shuffling algorithm, shuffle items in char array
	 *
	 * @param arryChar
	 * @return
	 */
	private char[] shuffle(char[] arryChar) {
		for (int i = arryChar.length - 1; i > 0; i--) {
			int index = r.nextInt(i + 1);
			char c = arryChar[index];
			arryChar[index] = arryChar[i];
			arryChar[i] = c;
		}

		return arryChar;
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

		// setup visibility view for word bank
		if (mLevel == 0 || mLevel == 4) {
			// add word bank view
			if (llWordBank.getVisibility() != View.VISIBLE) {
				llWordBank.setVisibility(View.VISIBLE);
			}
		} else {
			// remove word bank view
			if (llWordBank.getVisibility() != View.GONE) {
				llWordBank.setVisibility(View.GONE);
			}
			
			// add editText view for letter input
			if (llEditAnswer.getVisibility() != View.VISIBLE) {
				llEditAnswer.setVisibility(View.VISIBLE);
			}
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
		Utils.clearText(tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4,
				tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9);

		// reset views that were gone to visible
		Utils.setViewVisibility(true, iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9);
		
		// reset imageViews color
		Utils.setImageViewBkgColor(getResources().getColor(R.color.transparent), 
				iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9);
		
		// reset letter views
		Utils.setViewVisibility(false, v1, v2, v3, v4, v5, v6, v7, v8, v9,
				tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5,
				tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9);
	}


	/**
	 * Method is used to remove letters from word bank
	 *
	 * @param pos
	 */
	private void updateVisibility(int pos) {
		if (mLevel >= 8) {
			edtAnswer.setText("");
		} else {
			if (pos == 0) {
				iv1.setVisibility(View.GONE);
			} else if (pos == 1) {
				iv2.setVisibility(View.GONE);
			} else if (pos == 2) {
				iv3.setVisibility(View.GONE);
			} else if (pos == 3) {
				iv4.setVisibility(View.GONE);
			} else if (pos == 4) {
				iv5.setVisibility(View.GONE);
			} else if (pos == 5) {
				iv6.setVisibility(View.GONE);
			} else if (pos == 6) {
				iv7.setVisibility(View.GONE);
			} else if (pos == 7) {
				iv8.setVisibility(View.GONE);
			} else {
				iv9.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * Method is used to check the selected letter from the 
	 * word bank with the possible letters of the correct answer
	 * @param pos
	 */
	private void checkLetter(int pos) {
		isController = true;
		if (!Utils.checkIfNull(arryJumbled)) {
			String c = "";
			if (mLevel >= 8) {
				c = mLetterEditText;
			} else {
				c = String.valueOf(arryJumbled[pos]);
			}
			ArrayList<Integer> arryMatch = new ArrayList<Integer>();
			if (mWord.contains(c)) {
				// check the entire word for instances of the selected letter
				for (int i = -1; (i = mWord.indexOf(c, i + 1)) != -1;) {
					arryMatch.add(i);
				}

				// set letter to correct position
				for (int i = 0; i < arryMatch.size(); i++) {
					mCorrectLetters++;
					String temp = Messages.msgPath(true, true);
					Crouton.showText(mActivity, temp, Style.CONFIRM);
					speakText(temp);
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

					// update views
					updateVisibility(pos);
					
					if (isController) {
						// delay before allowing word bank selection
						mHandler.postDelayed(new Runnable() {
							@Override
							public void run() {
								isController = false;
							}
						}, 2500);
					}
				}
			} else {
				vibrate(mContext, 500);
				mIncorrectLetters++;
				String temp = Messages.msgPath(false, true);
				Crouton.showText(mActivity, temp, Style.ALERT);
				speakText(temp);

				if (mLevel >= 8) {
					startShakeAnim(mContext, llEditAnswer);
					edtAnswer.setTextColor(getResources().getColor(R.color.material_red_500_color_code));
				} else {
					// add color marker that letter is incorrect
					if (pos == 0) {
						iv1.setImageResource(getDrawableLetter(0, true));
					} else if (pos == 1) {
						iv2.setImageResource(getDrawableLetter(1, true));
					} else if (pos == 2) {
						iv3.setImageResource(getDrawableLetter(2, true));
					} else if (pos == 3) {
						iv4.setImageResource(getDrawableLetter(3, true));
					} else if (pos == 4) {
						iv5.setImageResource(getDrawableLetter(4, true));
					} else if (pos == 5) {
						iv6.setImageResource(getDrawableLetter(5, true));
					} else if (pos == 6) {
						iv7.setImageResource(getDrawableLetter(6, true));
					} else if (pos == 7) {
						iv8.setImageResource(getDrawableLetter(7, true));
					} else if (pos == 8) {
						iv9.setImageResource(getDrawableLetter(8, true));
					}
					
					// add selection marker that incorrect letter was selected
					arrySelected[pos] = true;
				}
				
				// update tree
				if (mIncorrectLetters == 1) {
					ivTree.setBackground(getResources().getDrawable(R.drawable.tree_three));
				} else if (mIncorrectLetters == 2) {
					ivTree.setBackground(getResources().getDrawable(R.drawable.tree_two));
				} else if (mIncorrectLetters == 3) {
					ivTree.setBackground(getResources().getDrawable(R.drawable.tree_one));
				} else {
					ivTree.setBackground(getResources().getDrawable(R.drawable.tree_zero));
				}

				if (mLevel == 0) {
					if (mIncorrectLetters >= 4) {
						// level failed
						launchPrevActivity(2500);
					}
				} else if (mLevel == 4) {
					if (mIncorrectLetters >= 3) {
						// level failed
						launchPrevActivity(2500);
					}
				} else {
					if (mIncorrectLetters >= 4) {
						// level failed
						launchPrevActivity(2500);
					}
				}
				
				if (isController) {
					// delay before allowing word bank selection
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
				if (mSolvedWords >= 3) {
					// TODO: play sounds, animations, messaging and add rewards for completing level
					boolean isLvUnlockRecent = false;
					String strPrefName = Constants.LV_COUNT.concat("_" + mLevel);
					String strPrefNameUnlock = Constants.LV_UNLOCKED.concat("_" + (mLevel+4));
					int lvCount = sharedPref.getIntPref(strPrefName, 0);				
					if (lvCount >= 3) {
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
							speakInstructions();
							generateLevel();
						}
					}, 2500);
				}
			}
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
		
		// set text message
		if (isLvUnlockRecent) {
			int levelUnlocked = mLevel + 4;
			tvMeta.setText("Level " + levelUnlocked + 
					" is now unlocked! More difficult levels will have more challenging words to learn");
		} else {
			tvMeta.setText("Good job! Keep practicing to learn new words");
		}

		// display dialog
		mDialog.show();
		
		btnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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
