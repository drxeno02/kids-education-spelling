package com.blog.ljtatum.eekspellingi.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class LetterTreeActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = LetterTreeActivity.class.getSimpleName();

	private Activity mActivity;
	private Context mContext;
	private ImageView ivBack, ivBanner;
	private View v1, v2, v3, v4, v5, v6, v7, v8, v9;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tvHint;
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
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		tv6 = (TextView) findViewById(R.id.tv6);
		tv7 = (TextView) findViewById(R.id.tv7);
		tv8 = (TextView) findViewById(R.id.tv8);
		tv9 = (TextView) findViewById(R.id.tv9);
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

		tv1.setOnClickListener(this);
		tv2.setOnClickListener(this);
		tv3.setOnClickListener(this);
		tv4.setOnClickListener(this);
		tv5.setOnClickListener(this);
		tv6.setOnClickListener(this);
		tv7.setOnClickListener(this);
		tv8.setOnClickListener(this);
		tv9.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		ivBanner.setOnClickListener(this);
		btnSubmit.setOnClickListener(this);
		
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
						edtAnswer.setTextColor(getResources().getColor(R.color.red_shade));
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
		case R.id.tv1:
			if (!isController) {
				if (!arrySelected[0]) {
					checkLetter(0);
				} else {
					vibrate(mContext, 500);
				}
			}		
			break;
		case R.id.tv2:
			if (!isController) {
				if (!arrySelected[1]) {
					checkLetter(1);
				} else {
					vibrate(mContext, 500);
				}
			}			
			break;
		case R.id.tv3:
			if (!isController) {
				if (!arrySelected[2]) {
					checkLetter(2);
				} else {
					vibrate(mContext, 500);
				}
			}					
			break;
		case R.id.tv4:
			if (!isController) {
				if (!arrySelected[3]) {
					checkLetter(3);
				} else {
					vibrate(mContext, 500);
				}
			}			
			break;
		case R.id.tv5:
			if (!isController) {
				if (!arrySelected[4]) {
					checkLetter(4);
				}
			}		
			break;
		case R.id.tv6:
			if (!isController) {
				if (!arrySelected[5]) {
					checkLetter(5);
				} else {
					vibrate(mContext, 500);
				}
			}			
			break;
		case R.id.tv7:
			if (!isController) {
				if (!arrySelected[6]) {
					checkLetter(6);
				} else {
					vibrate(mContext, 500);
				}
			}		
			break;
		case R.id.tv8:
			if (!isController) {
				if (!arrySelected[7]) {
					checkLetter(7);
				} else {
					vibrate(mContext, 500);
				}
			}			
			break;
		case R.id.tv9:
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
			tvHint.setTextColor(getResources().getColor(R.color.red_shade));
		} else {
			if (mWordCategory == WordCategory.CATEGORY_OBJECT_PLACE) {
				tvHint.setText("Object or Place");
			} else if (mWordCategory == WordCategory.CATEGORY_OBJECT_NUMBER) {
				tvHint.setText("Object or Number");
			} else if (mWordCategory == WordCategory.CATEGORY_OBJECT_COLOR) {
				tvHint.setText("Object or Color");
			} else if (mWordCategory == WordCategory.CATEGORY_PLACE_NUMBER_COLOR) {	
				tvHint.setText("Object, Number or Color");
			} else if (mWordCategory == WordCategory.CATEGORY_OBJECT_ACTION) {
				tvHint.setText("Object or Action");
			} else if (mWordCategory == WordCategory.CATEGORY_OBJECT_ANIMAL) {	
				tvHint.setText("Object or Animal");
			} else if (mWordCategory == WordCategory.CATEGORY_OBJECT_DAY_MONTH) {	
				tvHint.setText("Object, Day or Month");
			} else if (mWordCategory == WordCategory.CATEGORY_ACTION_ANIMAL_DAY_MONTH) {
				tvHint.setText("Action, Animal, Day or Month");
			}
			tvHint.setTextColor(getResources().getColor(R.color.black));
		}
		
		// retrieve list of usable words
		mArryWordBank = getWordBank(arryWordBankFull, mLevel);
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
			tv1.setText(String.valueOf(arryJumbled[0]));
			tv2.setText(String.valueOf(arryJumbled[1]));
			tv3.setText(String.valueOf(arryJumbled[2]));
			tv4.setText(String.valueOf(arryJumbled[3]));
			tv5.setText(String.valueOf(arryJumbled[4]));
			tv6.setText(String.valueOf(arryJumbled[5]));
			tv7.setText(String.valueOf(arryJumbled[6]));
			tv8.setText(String.valueOf(arryJumbled[7]));
			tv9.setText(String.valueOf(arryJumbled[8]));
			
			// set default selection
			for (int i = 0; i < arrySelected.length; i++) {
				arrySelected[i] = false;
			}
		} else {
			arryJumbled = mWord.toCharArray();
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

	private void resetVisibility() {
		// reset correct and incorrect trackers
		mCorrectLetters = 0;
		mIncorrectLetters = 0;

		// clear letter views
		Utils.clearText(tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4,
				tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9);

		// reset views that were gone to visible
		Utils.setViewVisibility(true, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9);
		
		// reset views color
		Utils.setTextViewColor(getResources().getColor(R.color.black), 
				tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9);
		
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
				tv1.setText("");
				tv1.setVisibility(View.GONE);
			} else if (pos == 1) {
				tv2.setText("");
				tv2.setVisibility(View.GONE);
			} else if (pos == 2) {
				tv3.setText("");
				tv3.setVisibility(View.GONE);
			} else if (pos == 3) {
				tv4.setText("");
				tv4.setVisibility(View.GONE);
			} else if (pos == 4) {
				tv5.setText("");
				tv5.setVisibility(View.GONE);
			} else if (pos == 5) {
				tv6.setText("");
				tv6.setVisibility(View.GONE);
			} else if (pos == 6) {
				tv7.setText("");
				tv7.setVisibility(View.GONE);
			} else if (pos == 7) {
				tv8.setText("");
				tv8.setVisibility(View.GONE);
			} else {
				tv9.setText("");
				tv9.setVisibility(View.GONE);
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
				// TODO: play sounds, animations and messaging
				vibrate(mContext, 500);
				mIncorrectLetters++;
				String temp = Messages.msgPath(false, true);
				Crouton.showText(mActivity, temp, Style.ALERT);
				speakText(temp);

				if (mLevel >= 8) {
					startShakeAnim(mContext, llEditAnswer);
					edtAnswer.setTextColor(getResources().getColor(R.color.red_shade));
				} else {
					// add color marker that letter is incorrect
					if (pos == 0) {
						tv1.setTextColor(getResources().getColor(R.color.red_shade));
					} else if (pos == 1) {
						tv2.setTextColor(getResources().getColor(R.color.red_shade));
					} else if (pos == 2) {
						tv3.setTextColor(getResources().getColor(R.color.red_shade));
					} else if (pos == 3) {
						tv4.setTextColor(getResources().getColor(R.color.red_shade));
					} else if (pos == 4) {
						tv5.setTextColor(getResources().getColor(R.color.red_shade));
					} else if (pos == 5) {
						tv6.setTextColor(getResources().getColor(R.color.red_shade));
					} else if (pos == 6) {
						tv7.setTextColor(getResources().getColor(R.color.red_shade));
					} else if (pos == 7) {
						tv8.setTextColor(getResources().getColor(R.color.red_shade));
					} else if (pos == 8) {
						tv9.setTextColor(getResources().getColor(R.color.red_shade));
					}
					
					// add selection marker that incorrect letter was selected
					arrySelected[pos] = true;
				}

				if (mLevel == 0) {
					if (mIncorrectLetters >= 4) {
						Logger.e(TAG, "level failed");
						mHandler.postDelayed(new Runnable() {
							@Override
							public void run() {
								goToActivityAnimRight(mContext, SelectActivity.class, -1);
							}
						}, 2500);
					}
				} else if (mLevel == 4) {
					if (mIncorrectLetters >= 3) {
						Logger.e(TAG, "level failed");
						mHandler.postDelayed(new Runnable() {
							@Override
							public void run() {
								goToActivityAnimRight(mContext, SelectActivity.class, -1);
							}
						}, 2500);
					}
				} else {
					if (mIncorrectLetters >= 4) {
						Logger.e(TAG, "level failed");
						mHandler.postDelayed(new Runnable() {
							@Override
							public void run() {
								goToActivityAnimRight(mContext, SelectActivity.class, -1);
							}
						}, 2500);
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
					String strPrefName = Constants.LV_COUNT.concat("_" + mLevel);
					String strPrefNameUnlock = Constants.LV_UNLOCKED.concat("_" + (mLevel+4));
					int lvCount = sharedPref.getIntPref(strPrefName, 0);				
					if (lvCount >= 3) {
						boolean isUnlock = sharedPref.getBooleanPref(strPrefNameUnlock, false);
						if (!isUnlock) {
							sharedPref.setPref(strPrefNameUnlock, true);
						}
					}		
					lvCount++;
					sharedPref.setPref(strPrefName, lvCount);
					goToActivityAnimRight(mContext, SelectActivity.class, -1);
				} else {
					// restart level
					// TODO: play sounds, animations, messaging and add rewards for completing level
					
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
