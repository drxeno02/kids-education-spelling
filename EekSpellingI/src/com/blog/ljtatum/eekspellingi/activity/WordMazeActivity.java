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
import android.view.animation.TranslateAnimation;
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

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class WordMazeActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = WordMazeActivity.class.getSimpleName();

	private Activity mActivity;
	private Context mContext;
	private ImageView ivBack, ivBanner, iv1;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11,
			tv12, tv13, tv14, tv15;
	private LinearLayout pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8, pos9,
			pos10, pos11, pos12, pos13, pos14, pos15;
	private View end1, end2, end3, end4, v1, v2, v3, v4, v5, v6, v7, v8, v9;
	private ShimmerTextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4,
			tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9;
	private Random r;
	private int mLevel = 0, mSolvedWords = 0, mSteps = 0, xStart = 0,
			yStart = 0, xEnd = 0, yEnd = 0, currPos = 0;
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
		setContentView(R.layout.activity_word_maze);
		getIds();
	}

	private void getIds() {
		mActivity = WordMazeActivity.this;
		mContext = WordMazeActivity.this;
		shareApp = new ShareAppUtil();
		sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
		mHandler = new Handler();
		arryPath = new ArrayList<Integer>();
		r = new Random();
		ivBanner = (ImageView) findViewById(R.id.iv_banner);
		ivBack = (ImageView) findViewById(R.id.iv_back);
		iv1 = (ImageView) findViewById(R.id.iv1);
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
		tv10 = (TextView) findViewById(R.id.tv10);
		tv11 = (TextView) findViewById(R.id.tv11);
		tv12 = (TextView) findViewById(R.id.tv12);
		tv13 = (TextView) findViewById(R.id.tv13);
		tv14 = (TextView) findViewById(R.id.tv14);
		tv15 = (TextView) findViewById(R.id.tv15);
		pos1 = (LinearLayout) findViewById(R.id.pos1);
		pos2 = (LinearLayout) findViewById(R.id.pos2);
		pos3 = (LinearLayout) findViewById(R.id.pos3);
		pos4 = (LinearLayout) findViewById(R.id.pos4);
		pos5 = (LinearLayout) findViewById(R.id.pos5);
		pos6 = (LinearLayout) findViewById(R.id.pos6);
		pos7 = (LinearLayout) findViewById(R.id.pos7);
		pos8 = (LinearLayout) findViewById(R.id.pos8);
		pos9 = (LinearLayout) findViewById(R.id.pos9);
		pos10 = (LinearLayout) findViewById(R.id.pos10);
		pos11 = (LinearLayout) findViewById(R.id.pos11);
		pos12 = (LinearLayout) findViewById(R.id.pos12);
		pos13 = (LinearLayout) findViewById(R.id.pos13);
		pos14 = (LinearLayout) findViewById(R.id.pos14);
		pos15 = (LinearLayout) findViewById(R.id.pos15);
		end1 = findViewById(R.id.end1);
		end2 = findViewById(R.id.end2);
		end3 = findViewById(R.id.end3);
		end4 = findViewById(R.id.end4);
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
			transverseToPos(1);
			break;
		case R.id.pos2:
			transverseToPos(2);
			break;
		case R.id.pos3:
			transverseToPos(3);
			break;
		case R.id.pos4:
			transverseToPos(4);
			break;
		case R.id.pos5:
			transverseToPos(5);
			break;
		case R.id.pos6:
			transverseToPos(6);
			break;
		case R.id.pos7:
			transverseToPos(7);
			break;
		case R.id.pos8:
			transverseToPos(8);
			break;
		case R.id.pos9:
			transverseToPos(9);
			break;
		case R.id.pos10:
			transverseToPos(10);
			break;
		case R.id.pos11:
			transverseToPos(11);
			break;
		case R.id.pos12:
			transverseToPos(12);
			break;
		case R.id.pos13:
			transverseToPos(13);
			break;
		case R.id.pos14:
			transverseToPos(14);
			break;
		case R.id.pos15:
			transverseToPos(15);
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
		default:
			break;
		}
	}

	private void transverseToPos(int pos) {
		if (pos == 1 && mSteps == 0) {
			setRightCords(pos);
		} else if (pos == 2 && mSteps == 1 && pos == arryPath.get(pos) - 1) {
			setRightCords(pos);
		} else if (pos == 3 && mSteps == 2 && pos == arryPath.get(pos) - 1) {
			setRightCords(pos);
		} else if (pos == 4 && mSteps == 1 && pos == arryPath.get(pos) - 1) {
			setDownCords(pos);
		} else if (pos == 5 && mSteps == 2 && pos == arryPath.get(pos) - 1) {
			setRightCords(pos);
		} else if (pos == 6 && mSteps == 3 && pos == arryPath.get(pos) - 3) {
			setDownCords(pos);
		} else if (pos == 7 && mSteps == 2 && pos == arryPath.get(pos) - 1) {
			setRightCords(pos);
		} else if (pos == 8 && mSteps == 3 && pos == arryPath.get(pos) - 1) {
		}   setRightCords(pos);
	
	}

	private void setRightCords(int pos) {
		Logger.i(TAG, "setRightCords()");
		yStart = yEnd;
		xStart = xEnd;
		if (currPos == 0 && pos == 1) {
			currPos = 1;
			xEnd = pos1.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 1 && pos == 2) {
			currPos = 2;
			xEnd = pos2.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 2 && pos == 3) {
			currPos = 3;
			xEnd = pos3.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 4 && pos == 5) {
			currPos = 5;
			xEnd = pos5.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 5 && pos == 6) { // can win from here
			currPos = 6;
			xEnd = pos6.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 7 && pos == 8) {
			currPos = 8;
			xEnd = pos8.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 8 && pos == 9) { // can win from here
			currPos = 9;
			xEnd = pos9.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 10 && pos == 11) {
			currPos = 11;
			startMovement();
			xEnd = pos11.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 11 && pos == 12) { // can win from here
			currPos = 12;
			xEnd = pos12.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 13 && pos == 14) {
			currPos = 14;
			xEnd = pos14.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 14 && pos == 15) { // can win from here
			currPos = 15;
			xEnd = pos15.getLeft();
			mSteps++;
			startMovement();
		}
	}

	private void setDownCords(int pos) {
		Logger.i(TAG, "setDownCords()" + pos);
		xStart = xEnd;
		yStart = yEnd;

		if (currPos == 1 && pos == 4) {
			currPos = 4;
			yEnd = pos4.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 2 && pos == 5) {
			currPos = 5;
			yEnd = pos5.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 3 && pos == 6) {
			currPos = 6;
			yEnd = pos6.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 4 && pos == 7) {
			currPos = 7;
			yEnd = pos7.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 5 && pos == 8) {
			currPos = 8;
			yEnd = pos8.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 6 && pos == 9) {
			currPos = 9;
			yEnd = pos9.getLeft();
			mSteps++;
			startMovement();
		}
	}

	private void startMovement() {
		Logger.d(TAG, "xStart:" + xStart + " //yStart:" + yStart + " //xEnd:"
				+ xEnd + " //yEnd: " + yEnd);
		TranslateAnimation animation = new TranslateAnimation(xStart, xEnd,
				yStart, yEnd);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		iv1.startAnimation(animation);

	}

	/**
	 * Method is used to initialize the game level; sets level, default word,
	 * and then calls method to generate the UI components.
	 *
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
		String[] arryWordBankFull = getWordBank(mLevel);
		mArryWordBank = getWordBank(arryWordBankFull, mLevel);
		mWord = mArryWordBank.get(r.nextInt(mArryWordBank.size()));
		Logger.i(TAG, mWord + " //count: " + mWord.length());
		generateLevel();
	}

	/**
	 * Method is used to speak instructions
	 */
	private void speakInstructions() {
		int temp = r.nextInt(3);
		if (temp >= 0) {
			speakText("Help blank get through the maze!");
		} else if (temp == 1) {
			speakText("Can you get through the maze?");
		} else if (temp == 2) {
			speakText("Spell the correct word to get through the maze!");
		} else {
			speakText("Lets get through this maze together!");
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
						mWord = mArryWordBank.get(r.nextInt(mArryWordBank
								.size()));
						i = 0;
					}
				}
				isCheck = true;
			}

			arryPrev.add(mWord);
			Logger.i(TAG, mWord + " //count: " + mWord.length());
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

		// setup maze map views
		if (num >= 6) {
			Utils.setViewVisibility(true, pos1, pos2, pos3, pos4, pos5, pos6,
					pos7, pos8, pos9, pos10, pos11, pos12, pos13, pos14, pos15,
					end4);
		}

		arryLetters = mWord.toCharArray();
		arryPath.clear();
		// setup words to solve views
		if (num == 3) {
			Utils.setViewVisibility(true, v1, v2, v3, tvAnswer1, tvAnswer2,
					tvAnswer3, pos1, pos2, pos4, pos5, end1);

			if (mLevel == 2) {
				tv1.setText(String.valueOf(arryLetters[0]));
				arryPath.add(1);
				int temp = r.nextInt(1);
				if (temp == 0) {
					tv2.setText(String.valueOf(arryLetters[1]));
					arryPath.add(2);
				} else {
					tv4.setText(String.valueOf(arryLetters[1]));
					arryPath.add(3);
				}
				tv5.setText(String.valueOf(arryLetters[2]));
				arryPath.add(4);
			}
		} else if (num == 4) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, tvAnswer1, tvAnswer2,
					tvAnswer3, tvAnswer4, pos1, pos2, pos3, pos4, pos5, pos6,
					end1);

			if (mLevel == 2) {
				tv1.setText(String.valueOf(arryLetters[0]));
				arryPath.add(1);
				int temp = r.nextInt(2);
				if (temp == 0) {
					tv2.setText(String.valueOf(arryLetters[1]));
					tv3.setText(String.valueOf(arryLetters[2]));
					arryPath.add(2);
					arryPath.add(3);
				} else if (temp == 1) {
					tv4.setText(String.valueOf(arryLetters[1]));
					tv5.setText(String.valueOf(arryLetters[2]));
					arryPath.add(4);
					arryPath.add(5);
				} else {
					tv2.setText(String.valueOf(arryLetters[1]));
					tv5.setText(String.valueOf(arryLetters[2]));
					arryPath.add(2);
					arryPath.add(5);
				}
				tv6.setText(String.valueOf(arryLetters[3]));
				arryPath.add(6);
			}
		} else if (num == 5) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, v5, tvAnswer1,
					tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5, pos1, pos2,
					pos3, pos4, pos5, pos6, pos7, pos8, pos9, end2);

			if (mLevel == 2) {
				tv1.setText(String.valueOf(arryLetters[0]));
				arryPath.add(1);
				int temp = r.nextInt(3);
				if (temp == 0) {
					tv2.setText(String.valueOf(arryLetters[1]));
					tv3.setText(String.valueOf(arryLetters[2]));
					tv6.setText(String.valueOf(arryLetters[3]));
					arryPath.add(2);
					arryPath.add(3);
					arryPath.add(6);
				} else if (temp == 1) {
					tv4.setText(String.valueOf(arryLetters[1]));
					tv7.setText(String.valueOf(arryLetters[2]));
					tv8.setText(String.valueOf(arryLetters[3]));
					arryPath.add(4);
					arryPath.add(7);
					arryPath.add(8);
				} else if (temp == 2) {
					tv4.setText(String.valueOf(arryLetters[1]));
					tv5.setText(String.valueOf(arryLetters[2]));
					tv8.setText(String.valueOf(arryLetters[3]));
					arryPath.add(4);
					arryPath.add(5);
					arryPath.add(8);
				} else {
					tv2.setText(String.valueOf(arryLetters[1]));
					tv5.setText(String.valueOf(arryLetters[2]));
					tv8.setText(String.valueOf(arryLetters[3]));
					arryPath.add(2);
					arryPath.add(5);
					arryPath.add(8);
				}
				tv9.setText(String.valueOf(arryLetters[4]));
				arryPath.add(9);
			}
		} else if (num == 6) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, v5, v6, tvAnswer1,
					tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5, tvAnswer6);

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
		// clear maze letter views
		Utils.clearText(tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10,
				tv11, tv12, tv13, tv14, tv15, tvAnswer1, tvAnswer2, tvAnswer3,
				tvAnswer4, tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8,
				tvAnswer9);

		// reset underscore views
		// reset maze views
		Utils.setViewVisibility(false, v1, v2, v3, v4, v5, v6, v7, v8, v9,
				tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5,
				tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9, pos1, pos2, pos3,
				pos4, pos5, pos6, pos7, pos8, pos9, pos10, pos11, pos12, pos13,
				pos14, pos15, end1, end2, end3, end4);

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
	}

}
