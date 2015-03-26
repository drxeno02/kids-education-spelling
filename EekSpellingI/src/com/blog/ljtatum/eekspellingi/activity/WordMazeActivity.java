package com.blog.ljtatum.eekspellingi.activity;

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
	private ImageView ivBack, ivBanner;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11,
	tv12, tv13, tv14, tv15;
	private LinearLayout pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8, pos9,
		pos10, pos11, pos12, pos13, pos14, pos15;
	private View end1, end2, end3, end4, v1, v2, v3, v4, v5, v6, v7, v8, v9;
	private ShimmerTextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4,
	tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9;
	private Random r;
	private int mLevel = 0, mSolvedWords = 0, mSteps = 0;
	private String origStr;
	private List<String> wordBank, arryPrev;

	private ShareAppUtil shareApp;
	private SharedPref sharedPref;

	private Handler mHandler;

	private LinearLayout ll;

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
			mSteps++;
			moveRight(pos);
		} else {
			moveRight(pos);
		}

	}

	private void moveRight(int pos) {
		Logger.i(TAG, "moveRight()");
		int xStart = 0, xEnd = 0, yStart = 0, yEnd = 0;
		if (pos == 1) {
			Logger.e(TAG, "height: " + pos1.getHeight() + " // width: " + pos1.getWidth() + " // x: " + pos1.getLeft() + " // y: " + pos1.getTop());
		} else if (pos == 2) {
			Logger.e(TAG, "height: " + pos2.getHeight() + " // width: " + pos2.getWidth() + " // x: " + pos2.getLeft() + " // y: " + pos2.getTop());
		} else if (pos == 3) {
			Logger.e(TAG, "height: " + pos3.getHeight() + " // width: " + pos3.getWidth() + " // x: " + pos3.getLeft() + " // y: " + pos3.getTop());
		} else if (pos == 4) {
			Logger.e(TAG, "height: " + pos4.getHeight() + " // width: " + pos4.getWidth() + " // x: " + pos4.getLeft() + " // y: " + pos4.getTop());
		} else if (pos == 5) {
			Logger.e(TAG, "height: " + pos5.getHeight() + " // width: " + pos5.getWidth() + " // x: " + pos5.getLeft() + " // y: " + pos5.getTop());
		} else if (pos == 6) {
			Logger.e(TAG, "height: " + pos6.getHeight() + " // width: " + pos6.getWidth() + " // x: " + pos6.getLeft() + " // y: " + pos6.getTop());
		}

		int[] imgCords = new int[2];

		if (pos == 1) {
			pos1.getLocationOnScreen(imgCords);
			int x = imgCords[0];
			int y = imgCords[1];
			Logger.e(TAG, "new x: " + x + " // new y: " + y);
		} else if (pos == 2) {
			pos2.getLocationOnScreen(imgCords);
			int x = imgCords[0];
			int y = imgCords[1];
			Logger.e(TAG, "new x: " + x + " // new y: " + y);
		} else if (pos == 3) {
			pos3.getLocationOnScreen(imgCords);
			int x = imgCords[0];
			int y = imgCords[1];
			Logger.e(TAG, "new x: " + x + " // new y: " + y);
		} else if (pos == 4) {
			pos4.getLocationOnScreen(imgCords);
			int x = imgCords[0];
			int y = imgCords[1];
			Logger.e(TAG, "new x: " + x + " // new y: " + y);
		} else if (pos == 5) {
			pos5.getLocationOnScreen(imgCords);
			int x = imgCords[0];
			int y = imgCords[1];
			Logger.e(TAG, "new x: " + x + " // new y: " + y);
		} else if (pos == 6) {
			pos6.getLocationOnScreen(imgCords);
			int x = imgCords[0];
			int y = imgCords[1];
			Logger.e(TAG, "new x: " + x + " // new y: " + y);
		}

		TranslateAnimation animation = new TranslateAnimation(xStart, xEnd, yStart, yEnd);
		animation.setDuration(1500);
		animation.setFillAfter(true);
	}

	/**
	 * Method is used to initialize the game level; sets level, default word, and then calls method
	 * to generate the UI components.
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
		String[] arryWordBankFull = getResources().getStringArray(R.array.arryWordBankObj);
		wordBank = getWordBank(arryWordBankFull, mLevel);
		origStr = wordBank.get(r.nextInt(wordBank.size()));
		Logger.i(TAG, origStr + " //count: " + origStr.length());
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

		// set visibility of views
		setVisibility(origStr.length());
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
			pos1.setVisibility(View.VISIBLE);
			pos2.setVisibility(View.VISIBLE);
			pos3.setVisibility(View.VISIBLE);
			pos4.setVisibility(View.VISIBLE);
			pos5.setVisibility(View.VISIBLE);
			pos6.setVisibility(View.VISIBLE);
			pos7.setVisibility(View.VISIBLE);
			pos8.setVisibility(View.VISIBLE);
			pos9.setVisibility(View.VISIBLE);
			pos10.setVisibility(View.VISIBLE);
			pos11.setVisibility(View.VISIBLE);
			pos12.setVisibility(View.VISIBLE);
			pos13.setVisibility(View.VISIBLE);
			pos14.setVisibility(View.VISIBLE);
			pos15.setVisibility(View.VISIBLE);
			end4.setVisibility(View.VISIBLE);
		}

		char[] arryLetters = origStr.toCharArray();
		// setup words to solve views
		if (num == 3) {
			v1.setVisibility(View.VISIBLE);
			v2.setVisibility(View.VISIBLE);
			v3.setVisibility(View.VISIBLE);
			tvAnswer1.setVisibility(View.VISIBLE);
			tvAnswer2.setVisibility(View.VISIBLE);
			tvAnswer3.setVisibility(View.VISIBLE);

			pos1.setVisibility(View.VISIBLE);
			pos2.setVisibility(View.VISIBLE);
			pos4.setVisibility(View.VISIBLE);
			pos5.setVisibility(View.VISIBLE);
			end1.setVisibility(View.VISIBLE);

			if (mLevel == 2) {
				tv1.setText(String.valueOf(arryLetters[0]));
				int temp = r.nextInt(1);
				if (temp == 0) {
					tv2.setText(String.valueOf(arryLetters[1]));
				} else {
					tv3.setText(String.valueOf(arryLetters[1]));
				}
				tv4.setText(String.valueOf(arryLetters[2]));
			}
		} else if (num == 4) {
			v1.setVisibility(View.VISIBLE);
			v2.setVisibility(View.VISIBLE);
			v3.setVisibility(View.VISIBLE);
			v4.setVisibility(View.VISIBLE);
			tvAnswer1.setVisibility(View.VISIBLE);
			tvAnswer2.setVisibility(View.VISIBLE);
			tvAnswer3.setVisibility(View.VISIBLE);
			tvAnswer4.setVisibility(View.VISIBLE);

			pos1.setVisibility(View.VISIBLE);
			pos2.setVisibility(View.VISIBLE);
			pos3.setVisibility(View.VISIBLE);
			pos4.setVisibility(View.VISIBLE);
			pos5.setVisibility(View.VISIBLE);
			pos6.setVisibility(View.VISIBLE);
			end1.setVisibility(View.VISIBLE);

			if (mLevel == 2) {
				tv1.setText(String.valueOf(arryLetters[0]));
				int temp = r.nextInt(2);
				if (temp == 0) {
					tv2.setText(String.valueOf(arryLetters[1]));
					tv3.setText(String.valueOf(arryLetters[2]));
				} else if (temp == 1) {
					tv4.setText(String.valueOf(arryLetters[1]));
					tv5.setText(String.valueOf(arryLetters[2]));
				} else {
					tv2.setText(String.valueOf(arryLetters[1]));
					tv5.setText(String.valueOf(arryLetters[2]));
				}
				tv6.setText(String.valueOf(arryLetters[3]));
			}
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

			pos1.setVisibility(View.VISIBLE);
			pos2.setVisibility(View.VISIBLE);
			pos3.setVisibility(View.VISIBLE);
			pos4.setVisibility(View.VISIBLE);
			pos5.setVisibility(View.VISIBLE);
			pos6.setVisibility(View.VISIBLE);
			pos7.setVisibility(View.VISIBLE);
			pos8.setVisibility(View.VISIBLE);
			pos9.setVisibility(View.VISIBLE);
			end2.setVisibility(View.VISIBLE);
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
		} else if (num == 8) {
			v1.setVisibility(View.VISIBLE);
			v2.setVisibility(View.VISIBLE);
			v3.setVisibility(View.VISIBLE);
			v4.setVisibility(View.VISIBLE);
			v5.setVisibility(View.VISIBLE);
			v6.setVisibility(View.VISIBLE);
			v7.setVisibility(View.VISIBLE);
			v8.setVisibility(View.VISIBLE);
			tvAnswer1.setVisibility(View.VISIBLE);
			tvAnswer2.setVisibility(View.VISIBLE);
			tvAnswer3.setVisibility(View.VISIBLE);
			tvAnswer4.setVisibility(View.VISIBLE);
			tvAnswer5.setVisibility(View.VISIBLE);
			tvAnswer6.setVisibility(View.VISIBLE);
			tvAnswer7.setVisibility(View.VISIBLE);
			tvAnswer8.setVisibility(View.VISIBLE);
		} else if (num == 9) {
			v1.setVisibility(View.VISIBLE);
			v2.setVisibility(View.VISIBLE);
			v3.setVisibility(View.VISIBLE);
			v4.setVisibility(View.VISIBLE);
			v5.setVisibility(View.VISIBLE);
			v6.setVisibility(View.VISIBLE);
			v7.setVisibility(View.VISIBLE);
			v8.setVisibility(View.VISIBLE);
			v9.setVisibility(View.VISIBLE);
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
	}

	private void resetVisibility() {
		// clear maze letter views
		tv1.setText("");
		tv2.setText("");
		tv3.setText("");
		tv4.setText("");
		tv5.setText("");
		tv6.setText("");
		tv7.setText("");
		tv8.setText("");
		tv9.setText("");
		tv10.setText("");
		tv11.setText("");
		tv12.setText("");
		tv13.setText("");
		tv14.setText("");
		tv15.setText("");

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

		// reset maze views
		pos1.setVisibility(View.GONE);
		pos2.setVisibility(View.GONE);
		pos3.setVisibility(View.GONE);
		pos4.setVisibility(View.GONE);
		pos5.setVisibility(View.GONE);
		pos6.setVisibility(View.GONE);
		pos7.setVisibility(View.GONE);
		pos8.setVisibility(View.GONE);
		pos9.setVisibility(View.GONE);
		pos10.setVisibility(View.GONE);
		pos11.setVisibility(View.GONE);
		pos12.setVisibility(View.GONE);
		pos13.setVisibility(View.GONE);
		pos14.setVisibility(View.GONE);
		pos15.setVisibility(View.GONE);
		end1.setVisibility(View.GONE);
		end2.setVisibility(View.GONE);
		end3.setVisibility(View.GONE);
		end4.setVisibility(View.GONE);
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
