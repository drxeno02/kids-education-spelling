package com.blog.ljtatum.eekspellingi.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.MusicUtils;
import com.blog.ljtatum.eekspellingi.util.ShareAppUtil;
import com.blog.ljtatum.eekspellingi.util.Utils;

public class LetterTreeActivity extends BaseActivity implements OnClickListener {
	private final static String TAG = LetterTreeActivity.class.getSimpleName();

	private Context mContext;
	private ImageView ivBack, ivBanner;
	private View v1, v2, v3, v4, v5, v6, v7, v8, v9;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;
	private ShimmerTextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4,
		tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9;
	private LinearLayout ll;
	private Random r;
	private int mLevel, mCorrectLetters, mIncorrectLetters, mSolvedWords = 0;
	private String origStr;
	private char[] arryJumbled = null;
	private List<String> wordBank, arryPrev;

	private ShareAppUtil shareApp;
	private SharedPref sharedPref;

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
		mContext = LetterTreeActivity.this;
		shareApp = new ShareAppUtil();
		sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
		wordBank = new ArrayList<String>();
		arryPrev = new ArrayList<String>();
		r = new Random();
		ll = (LinearLayout) findViewById(R.id.ll_word_bank);
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
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		tv5 = (TextView) findViewById(R.id.tv5);
		tv6 = (TextView) findViewById(R.id.tv6);
		tv7 = (TextView) findViewById(R.id.tv7);
		tv8 = (TextView) findViewById(R.id.tv8);
		tv9 = (TextView) findViewById(R.id.tv9);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv1:
			checkLetter(0);
			break;
		case R.id.tv2:
			checkLetter(1);
			break;
		case R.id.tv3:
			checkLetter(2);
			break;
		case R.id.tv4:
			checkLetter(3);
			break;
		case R.id.tv5:
			checkLetter(4);
			break;
		case R.id.tv6:
			checkLetter(5);
			break;
		case R.id.tv7:
			checkLetter(6);
			break;
		case R.id.tv8:
			checkLetter(7);
			break;
		case R.id.tv9:
			checkLetter(8);
			break;
		default:
			break;
		}
	}

	/**
	 * Method is used to initialize the game level; sets level, default word,
	 * and then calls method to generate the UI components. 
	 * @Note Only needs to be called once
	 */
	private void initLesson() {
		// retrieve level
		Intent intent = getIntent();
		if (!Utils.checkIfNull(intent)) {
			mLevel = intent.getIntExtra(Constants.LEVEL_SELECTED, 0);
			Logger.i(TAG, "level: " + mLevel);
		}

		// retrieve full word bank
		String[] arryWordBankFull = getResources().getStringArray(R.array.arryWordBank);
		wordBank = getWordBank(arryWordBankFull, mLevel);
		origStr = wordBank.get(r.nextInt(wordBank.size()));
		Logger.i(TAG, origStr + " //count: " + origStr.length());
		generateLevel();
	}

	/**
	 * Method is used to setup the game level
	 */
	private void generateLevel() {
		// confirm that next set of words are unique
		if (!Utils.checkIfNull(arryPrev)) {
			boolean isCheck = false;
			while (!isCheck) {
				int i = 0;
				for (i = 0; i < arryPrev.size(); i++) {
					if (arryPrev.get(i).equalsIgnoreCase(origStr)) {
						origStr = wordBank.get(r.nextInt(wordBank.size()));	
						i = 0;
					}
				}
				isCheck = true;						
			}
			
			arryPrev.add(origStr);
			Logger.i(TAG, origStr + " //count: " + origStr.length());
		}
		
		// reset jumbled array
		if (!Utils.checkIfNull(arryJumbled)) {
			arryJumbled = null;
		}

		// set visibility of views
		setVisibility(origStr.length());
		
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
		} else {
			arryJumbled = origStr.toCharArray();
		}
	}

	/**
	 * Method is used to add random letters to word bank
	 * @return array of jumbled letters
	 */
	private char[] getJumbledWord() {
		StringBuilder builder = new StringBuilder();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		// remove duplicate values from origStr
		String noDup = Utils.removeDuplicates(origStr);
				
		// append random letters to work bank pool
		if (mLevel == 0) {
			for (int i = 0; i <= 8 - noDup.length(); i++) {
				char temp = alphabet.charAt(r.nextInt(alphabet.length()));
				while (noDup.indexOf(temp) != -1) {
					temp = alphabet.charAt(r.nextInt(alphabet.length()));
				}
				builder.append(temp);
			}
		} else if (mLevel == 4) {
			for (int i = 0; i <= 10 - noDup.length(); i++) {
				char temp = alphabet.charAt(r.nextInt(alphabet.length()));
				while (noDup.indexOf(temp) != -1) {
					temp = alphabet.charAt(r.nextInt(alphabet.length()));
				}
				builder.append(temp);
			}
		}
		String matchStr = builder.append(noDup).toString();
		char[] arryChar = matchStr.toCharArray();
		char[] arryCharJumbled = shuffle(arryChar);

		return arryCharJumbled;
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

		if (mLevel == 0 || mLevel == 4) {

			// add word bank view
			if (ll.getVisibility() == View.GONE) {
				ll.setVisibility(View.VISIBLE);
			}

			if (num == 3) {
				v1.setVisibility(View.VISIBLE);
				v2.setVisibility(View.VISIBLE);
				v3.setVisibility(View.VISIBLE);
				tvAnswer1.setVisibility(View.VISIBLE);
				tvAnswer2.setVisibility(View.VISIBLE);
				tvAnswer3.setVisibility(View.VISIBLE);
			} else if (num == 4) {
				v1.setVisibility(View.VISIBLE);
				v2.setVisibility(View.VISIBLE);
				v3.setVisibility(View.VISIBLE);
				v4.setVisibility(View.VISIBLE);
				tvAnswer1.setVisibility(View.VISIBLE);
				tvAnswer2.setVisibility(View.VISIBLE);
				tvAnswer3.setVisibility(View.VISIBLE);
				tvAnswer4.setVisibility(View.VISIBLE);
			} else if (num == 5) {
				v1.setVisibility(View.VISIBLE);
				v2.setVisibility(View.VISIBLE);
				v3.setVisibility(View.VISIBLE);
				v4.setVisibility(View.VISIBLE);
				v5.setVisibility(View.VISIBLE);
				tvAnswer1.setVisibility(View.VISIBLE);
				tvAnswer2.setVisibility(View.VISIBLE);
				tvAnswer3.setVisibility(View.VISIBLE);
				tvAnswer4.setVisibility(View.VISIBLE);
				tvAnswer5.setVisibility(View.VISIBLE);
			} else if (num == 6) {
				v1.setVisibility(View.VISIBLE);
				v2.setVisibility(View.VISIBLE);
				v3.setVisibility(View.VISIBLE);
				v4.setVisibility(View.VISIBLE);
				v5.setVisibility(View.VISIBLE);
				v6.setVisibility(View.VISIBLE);
				tvAnswer1.setVisibility(View.VISIBLE);
				tvAnswer2.setVisibility(View.VISIBLE);
				tvAnswer3.setVisibility(View.VISIBLE);
				tvAnswer4.setVisibility(View.VISIBLE);
				tvAnswer5.setVisibility(View.VISIBLE);
				tvAnswer6.setVisibility(View.VISIBLE);
			} else if (num == 7) {
				v1.setVisibility(View.VISIBLE);
				v2.setVisibility(View.VISIBLE);
				v3.setVisibility(View.VISIBLE);
				v4.setVisibility(View.VISIBLE);
				v5.setVisibility(View.VISIBLE);
				v6.setVisibility(View.VISIBLE);
				v7.setVisibility(View.VISIBLE);
				tvAnswer1.setVisibility(View.VISIBLE);
				tvAnswer2.setVisibility(View.VISIBLE);
				tvAnswer3.setVisibility(View.VISIBLE);
				tvAnswer4.setVisibility(View.VISIBLE);
				tvAnswer5.setVisibility(View.VISIBLE);
				tvAnswer6.setVisibility(View.VISIBLE);
				tvAnswer7.setVisibility(View.VISIBLE);
			}
		} else {
			// remove word bank view
			if (ll.getVisibility() == View.VISIBLE) {
				ll.setVisibility(View.GONE);
			}
		}
	}

	private void resetVisibility() {
		// clear letter views
		tvAnswer1.setText("");
		tvAnswer2.setText("");
		tvAnswer3.setText("");
		tvAnswer4.setText("");
		tvAnswer5.setText("");
		tvAnswer6.setText("");
		tvAnswer7.setText("");
		tvAnswer8.setText("");
		tvAnswer9.setText("");
		
		// reset views there were gone
		tv1.setVisibility(View.VISIBLE);
		tv2.setVisibility(View.VISIBLE);
		tv3.setVisibility(View.VISIBLE);
		tv4.setVisibility(View.VISIBLE);
		tv5.setVisibility(View.VISIBLE);
		tv6.setVisibility(View.VISIBLE);
		tv7.setVisibility(View.VISIBLE);
		tv8.setVisibility(View.VISIBLE);
		tv9.setVisibility(View.VISIBLE);

		// reset underscore views
		v1.setVisibility(View.GONE);
		v2.setVisibility(View.GONE);
		v3.setVisibility(View.GONE);
		v4.setVisibility(View.GONE);
		v5.setVisibility(View.GONE);
		v6.setVisibility(View.GONE);
		v7.setVisibility(View.GONE);
		v8.setVisibility(View.GONE);
		v9.setVisibility(View.GONE);

		// reset letter views
		tvAnswer1.setVisibility(View.GONE);
		tvAnswer2.setVisibility(View.GONE);
		tvAnswer3.setVisibility(View.GONE);
		tvAnswer4.setVisibility(View.GONE);
		tvAnswer5.setVisibility(View.GONE);
		tvAnswer6.setVisibility(View.GONE);
		tvAnswer7.setVisibility(View.GONE);
		tvAnswer8.setVisibility(View.GONE);
		tvAnswer9.setVisibility(View.GONE);
	}

	/**
	 * Method is used to remove letters from word bank
	 * @param pos
	 */
	private void updateVisibility(int pos) {
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

	private void checkLetter(int pos) {

		if (!Utils.checkIfNull(arryJumbled)) {
			String c = String.valueOf(arryJumbled[pos]);
			ArrayList<Integer> arryDuplicates = new ArrayList<Integer>();
			if (origStr.contains(c)) {
				// check the entire word for instances of the selected letter
				for (int i = -1; (i = origStr.indexOf(c, i + 1)) != -1;) {
					arryDuplicates.add(i);
				}

				// set letter to correct position
				for (int i = 0; i < arryDuplicates.size(); i++) {
					mCorrectLetters++;
					if (arryDuplicates.get(i) == 0) {
						tvAnswer1.setText(c);
						startShimmerAnimation(tvAnswer1);
					} else if (arryDuplicates.get(i) == 1) {
						tvAnswer2.setText(c);
						startShimmerAnimation(tvAnswer2);
					} else if (arryDuplicates.get(i) == 2) {
						tvAnswer3.setText(c);
						startShimmerAnimation(tvAnswer3);
					} else if (arryDuplicates.get(i) == 3) {
						tvAnswer4.setText(c);
						startShimmerAnimation(tvAnswer4);
					} else if (arryDuplicates.get(i) == 4) {
						tvAnswer5.setText(c);
						startShimmerAnimation(tvAnswer5);
					} else if (arryDuplicates.get(i) == 5) {
						tvAnswer6.setText(c);
						startShimmerAnimation(tvAnswer6);
					} else if (arryDuplicates.get(i) == 6) {
						tvAnswer7.setText(c);
						startShimmerAnimation(tvAnswer7);
					} else if (arryDuplicates.get(i) == 7) {
						tvAnswer8.setText(c);
						startShimmerAnimation(tvAnswer8);
					} else if (arryDuplicates.get(i) == 8) {
						tvAnswer9.setText(c);
						startShimmerAnimation(tvAnswer9);
					}

					// update views
					updateVisibility(pos);
				}
			} else {
				// TODO: play sounds, animations and messaging
				mIncorrectLetters++;
			}

			// check if puzzle is solved
			if (origStr.length() == mCorrectLetters) {
				Logger.i(TAG, "word solved");
				mSolvedWords++;
				if (mSolvedWords >= 3) {
					// TODO: play sounds, animations, messaging and add rewards for completing level
					goToActivity(mContext, SelectActivity.class, -1);
				} else {
					// restart level
					// TODO: play sounds, animations, messaging and add rewards for completing level
					generateLevel();
				}
			}
		}
	}

	private void startShimmerAnimation(ShimmerTextView stv) {
		Shimmer shimmer = new Shimmer();
		shimmer.setRepeatCount(1);
		shimmer.setDuration(1500);
		shimmer.setStartDelay(250);
		shimmer.start(stv);
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
