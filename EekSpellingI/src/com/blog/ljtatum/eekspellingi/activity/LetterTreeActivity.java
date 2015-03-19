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
	private int mLevel = 0;
	private int mCorrectLetters = 0;
	private int mIncorrectLetters = 0;
	private int mSolvedWords = 0;
	private char[] arryJumbled = null;
	private String[] wordBankFull = null; 
	private String origStr;
	private List<String> wordBank;

	private ShareAppUtil shareApp;
	private SharedPref sharedPref;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
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
		r = new Random();
		ll = (LinearLayout) findViewById(R.id.ll_word_bank);
		ivBanner = (ImageView) findViewById(R.id.iv_banner);
		ivBack = (ImageView) findViewById(R.id.iv_back);
		v1 = (View) findViewById(R.id.v1);
		v2 = (View) findViewById(R.id.v2);
		v3 = (View) findViewById(R.id.v3);
		v4 = (View) findViewById(R.id.v4);
		v5 = (View) findViewById(R.id.v5);
		v6 = (View) findViewById(R.id.v6);
		v7 = (View) findViewById(R.id.v7);
		v8 = (View) findViewById(R.id.v8);
		v9 = (View) findViewById(R.id.v9);
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
		switch(v.getId()) {
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
	
	private void initLesson() {
		// retrieve level
		Intent intent = getIntent();
		if (intent != null) {		
			mLevel = intent.getIntExtra(Constants.LEVEL_SELECTED, 0);
			Logger.i(TAG, "level: " + mLevel);
		}
				
		// retrieve full word bank
		wordBankFull = getResources().getStringArray(R.array.arryWordBank);
		wordBank = getWordBank(wordBankFull, mLevel);
		generateLevel();
	}
	
	private void generateLevel() {
		origStr = wordBank.get(r.nextInt(wordBank.size()));
		if (arryJumbled != null && arryJumbled.length > 0) {
			arryJumbled = null;
		}

		if (mLevel == 0 || mLevel == 4) {
			arryJumbled = getJumbledWord(origStr);
		} else {
			arryJumbled = origStr.toCharArray();
		}
		
		// set visibility	
		setVisibility(origStr.length());
		Logger.i(TAG, origStr + " //count: " + origStr.length());
		tv1.setText(String.valueOf(arryJumbled[0]));
		tv2.setText(String.valueOf(arryJumbled[1]));
		tv3.setText(String.valueOf(arryJumbled[2]));
		tv4.setText(String.valueOf(arryJumbled[3]));
		tv5.setText(String.valueOf(arryJumbled[4]));
		tv6.setText(String.valueOf(arryJumbled[5]));
		tv7.setText(String.valueOf(arryJumbled[6]));
		tv8.setText(String.valueOf(arryJumbled[7]));
		tv9.setText(String.valueOf(arryJumbled[8]));

	}
	
	private char[] getJumbledWord(String origStr) {
		StringBuilder builder = new StringBuilder();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		 
		if (mLevel == 0) {
			for (int i = 0; i <= (8 - origStr.length()); i++) {
				builder.append(alphabet.charAt(r.nextInt(alphabet.length())));
			}
		} else if (mLevel == 4) {
			for (int i = 0; i <= (10 - origStr.length()); i++) {
				builder.append(alphabet.charAt(r.nextInt(alphabet.length())));
			}
		}
		String matchStr = builder.append(origStr).toString();
		char[] arryChar = matchStr.toCharArray();
		char[] arryJumbled = shuffle(arryChar);
		
		return arryJumbled;
	}
	
	/**
	 * Using Fisher-Yates shuffling algorithm, shuffle items
	 * in char array
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
	 * Sets the number of needed visible views to form
	 * the correct word
	 * @param num
	 */
	private void setVisibility(int num) {
		if (mSolvedWords > 0) {
			resetVisibility();
		}

		if (mLevel == 0 || mLevel == 4) {
			
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
		} 		
	}
	
	private void resetVisibility() {
		v1.setVisibility(View.GONE);
		v2.setVisibility(View.GONE);
		v3.setVisibility(View.GONE);
		v4.setVisibility(View.GONE);
		v5.setVisibility(View.GONE);
		v6.setVisibility(View.GONE);
		v7.setVisibility(View.GONE);
		v8.setVisibility(View.GONE);
		v9.setVisibility(View.GONE);
		tvAnswer1.setVisibility(View.VISIBLE);
		tvAnswer2.setVisibility(View.VISIBLE);
		tvAnswer3.setVisibility(View.VISIBLE);
		tvAnswer4.setVisibility(View.VISIBLE);
		tvAnswer5.setVisibility(View.VISIBLE);
		tvAnswer6.setVisibility(View.VISIBLE);
		tvAnswer7.setVisibility(View.VISIBLE);
		tvAnswer8.setVisibility(View.VISIBLE);
		tvAnswer9.setVisibility(View.VISIBLE);
	}
	
	private void checkLetter(int pos) {
		
		if (arryJumbled != null) {
			String c = String.valueOf(arryJumbled[pos]);
			if (origStr.contains(c)) {
				mCorrectLetters++;
				int location = origStr.indexOf(c);
				if (location == 0) {
					tvAnswer1.setText(c);
					tv1.setVisibility(View.GONE);
				} else if (location == 1) {
					tvAnswer2.setText(c);
					tv2.setVisibility(View.GONE);
				} else if (location == 2) {
					tvAnswer3.setText(c);
					tv3.setVisibility(View.GONE);
				} else if (location == 3) {
					tvAnswer4.setText(c);
					tv4.setVisibility(View.GONE);
				} else if (location == 4) {
					tvAnswer5.setText(c);
					tv5.setVisibility(View.GONE);
				} else if (location == 5) {
					tvAnswer6.setText(c);
					tv6.setVisibility(View.GONE);
				} else if (location == 6) {
					tvAnswer7.setText(c);
					tv7.setVisibility(View.GONE);
				} else if (location == 7) {
					tvAnswer8.setText(c);
					tv8.setVisibility(View.GONE);
				} else if (location == 8) {
					tvAnswer9.setText(c);
					tv9.setVisibility(View.GONE);
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
					
				}
			}
		}
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
