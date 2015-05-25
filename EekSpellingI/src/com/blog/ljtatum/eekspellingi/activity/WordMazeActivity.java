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
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
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
import com.blog.ljtatum.eekspellingi.view.CircleImageView;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class WordMazeActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = WordMazeActivity.class.getSimpleName();

	private Activity mActivity;
	private Context mContext;
	private ImageView ivBack, ivBanner, iv1;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11,
			tv12, tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20, tv21, tvHint;
	private LinearLayout pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8, pos9,
			pos10, pos11, pos12, pos13, pos14, pos15, pos16, pos17, pos18,
			pos19, pos20, pos21, ll1, ll2, ll3, ll4, ll5, ll6, ll7;
	private View end1, end2, end3, end4, end5, end6, v1, v2, v3, v4, v5, v6, v7, v8, v9;
	private ShimmerTextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4,
			tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9;
	private Random r;
	private int mLevel = 0, mSolvedWords = 0, mSteps = 0, xStart = 0,
			yStart = 0, xEnd = 0, yEnd = 0, currPos = 0, downDistance = 0;
	private String mWord;
	private boolean isController = false, isLvUnlockRecent = false;
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
		arryPrev = new ArrayList<String>();
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
		tv16 = (TextView) findViewById(R.id.tv16);
		tv17 = (TextView) findViewById(R.id.tv17);
		tv18 = (TextView) findViewById(R.id.tv18);
		tv19 = (TextView) findViewById(R.id.tv19);
		tv20 = (TextView) findViewById(R.id.tv20);
		tv21 = (TextView) findViewById(R.id.tv21);
		tvHint = (TextView) findViewById(R.id.tv_hint);
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
		pos16 = (LinearLayout) findViewById(R.id.pos16);
		pos17 = (LinearLayout) findViewById(R.id.pos17);
		pos18 = (LinearLayout) findViewById(R.id.pos18);
		pos19 = (LinearLayout) findViewById(R.id.pos19);
		pos20 = (LinearLayout) findViewById(R.id.pos20);
		pos21 = (LinearLayout) findViewById(R.id.pos21);
		ll1 = (LinearLayout) findViewById(R.id.ll1);
		ll2 = (LinearLayout) findViewById(R.id.ll2);
		ll3 = (LinearLayout) findViewById(R.id.ll3);
		ll4 = (LinearLayout) findViewById(R.id.ll4);
		ll5 = (LinearLayout) findViewById(R.id.ll5);
		ll6 = (LinearLayout) findViewById(R.id.ll6);
		ll7 = (LinearLayout) findViewById(R.id.ll7);
		end1 = findViewById(R.id.end1);
		end2 = findViewById(R.id.end2);
		end3 = findViewById(R.id.end3);
		end4 = findViewById(R.id.end4);
		end5 = findViewById(R.id.end5);
		end6 = findViewById(R.id.end6);
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
		case R.id.pos16:
			transverseToPos(16);
			break;
		case R.id.pos17:
			transverseToPos(17);
			break;
		case R.id.pos18:
			transverseToPos(18);
			break;
		case R.id.pos19:
			transverseToPos(19);
			break;			
		case R.id.pos20:
			transverseToPos(20);
			break;
		case R.id.pos21:
			transverseToPos(21);
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
	 * Method is used to move image object across canvas
	 * @param pos
	 */
	private void transverseToPos(int pos) {
		if (!isController) {
			// prevent register of action until animation ends
			isController = true;
			if (pos == 1 && mSteps == 0 && pos == arryPath.get(mSteps)) {
				Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //right");
				setRightCords(pos);
			} else if (pos == 2 && mSteps == 1 && pos == arryPath.get(mSteps)) {
				Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //right");
				setRightCords(pos);
			} else if (pos == 3 && mSteps == 2 && pos == arryPath.get(mSteps)) {
				Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //right");
				setRightCords(pos);
			} else if (pos == 4 && mSteps == 1 && pos == arryPath.get(mSteps)) {
				Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
				setDownCords(pos);
			} else if (pos == 5 && mSteps == 2 && pos == arryPath.get(mSteps)) {
				if (currPos == 2) {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
					setDownCords(pos); // from pos 2
				} else {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //right");
					setRightCords(pos); // from pos 4
				}	
			} else if (pos == 6 && mSteps == 3 && pos == arryPath.get(mSteps)) {
				if (currPos == 3) {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
					setDownCords(pos); // from pos 3
				} else {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //right");
					setRightCords(pos); // from pos 5
				}
			} else if (pos == 7 && mSteps == 2 && pos == arryPath.get(mSteps)) {
				Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
				setDownCords(pos);
			} else if (pos == 8 && mSteps == 3 && pos == arryPath.get(mSteps)) {
				if (currPos == 5) {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
					setDownCords(pos); // from pos 5
				} else {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //right");
					setRightCords(pos); // from pos 7
				}
			} else if (pos == 9 && mSteps == 4 && pos == arryPath.get(mSteps)) {
				if (currPos == 6) {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
					setDownCords(pos); // from pos 6
				} else {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //right");
					setRightCords(pos); // from pos 8
				}
			} else if (pos == 10 && mSteps == 3 && pos == arryPath.get(mSteps)) {
				Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
				setDownCords(pos);
			} else if (pos == 11 && mSteps == 4 && pos == arryPath.get(mSteps))	{
				if (currPos == 8) {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
					setDownCords(pos); // from pos 8
				} else {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //right");
					setRightCords(pos); // from pos 10
				}
			} else if (pos == 12 && mSteps == 5 && pos == arryPath.get(mSteps))	{
				if (currPos == 9) {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
					setDownCords(pos); // from pos 9
				} else {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //right");
					setRightCords(pos); // from pos 11
				}
			} else if (pos == 13 && mSteps == 4 && pos == arryPath.get(mSteps)) {
				Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
				setDownCords(pos);
			} else if (pos == 14 && mSteps == 5 && pos == arryPath.get(mSteps)) {
				if (currPos == 11) {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
					setDownCords(pos); // from pos 11
				} else {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //right");
					setRightCords(pos); // from pos 13
				}
			} else if (pos == 15 && mSteps == 6 && pos == arryPath.get(mSteps)) {
				if (currPos == 12) {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
					setDownCords(pos); // from pos 12
				} else {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //right");
					setRightCords(pos); // from pos 14
				}
			} else if (pos == 16 && mSteps == 5 && pos == arryPath.get(mSteps)) {
				Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
				setDownCords(pos);
			} else if (pos == 17 && mSteps == 6 && pos == arryPath.get(mSteps))	{
				if (currPos == 14) {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
					setDownCords(pos); // from pos 14
				} else {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //right");
					setRightCords(pos); // from pos 16
				}
			} else if (pos == 18 && mSteps == 7 && pos == arryPath.get(mSteps)) {
				if (currPos == 15) {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //down");
					setDownCords(pos); // from pos 15
				} else {
					Logger.v("TEST", "pos: " + pos + " //steps: " + mSteps + " //right");
					setRightCords(pos); // from pos 17
				}
			}
			
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					// reset controller
					if (isController) {
						isController = false;
					}
				}
			}, 3000);

		}
	}

	/**
	 * Method is used to set the right coordinates for
	 * right movement
	 * @param pos
	 */
	private void setRightCords(int pos) {
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
		} else if (currPos == 5 && pos == 6) {
			currPos = 6;
			xEnd = pos6.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 7 && pos == 8) {
			currPos = 8;
			xEnd = pos8.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 8 && pos == 9) {
			currPos = 9;
			xEnd = pos9.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 10 && pos == 11) {
			currPos = 11;
			xEnd = pos11.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 11 && pos == 12) {
			currPos = 12;
			xEnd = pos12.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 13 && pos == 14) {
			currPos = 14;
			xEnd = pos14.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 14 && pos == 15) {
			currPos = 15;
			xEnd = pos15.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 16 && pos == 17) {
			currPos = 17;
			xEnd = pos17.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 17 && pos == 18) {
			currPos = 18;
			xEnd = pos18.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 19 && pos == 20) {
			currPos = 20;
			xEnd = pos20.getLeft();
			mSteps++;
			startMovement();
		} else if (currPos == 20 && pos == 21) {
			currPos = 21;
			xEnd = pos21.getLeft();
			mSteps++;
			startMovement();
		}
	}

	/**
	 * Method is used to set the right coordinates for
	 * down movement
	 * @param pos
	 */
	private void setDownCords(int pos) {
		xStart = xEnd;
		yStart = yEnd;

		if (currPos == 1 && pos == 4) {
			currPos = 4;
			yEnd = ll1.getBottom() - ll1.getTop();	
			downDistance = yEnd;
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 2 && pos == 5) {
			currPos = 5;
			yEnd = ll1.getBottom() - ll1.getTop();	
			downDistance = yEnd;
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 3 && pos == 6) {			
			currPos = 6;
			yEnd = ll1.getBottom() - ll1.getTop();	
			downDistance = yEnd;
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 4 && pos == 7) {
			currPos = 7;
			yEnd = downDistance*2;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 5 && pos == 8) {
			currPos = 8;
			yEnd = downDistance*2;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 6 && pos == 9) {
			currPos = 9;
			yEnd = downDistance*2;
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 7 && pos == 10) {
			currPos = 10;
			yEnd = downDistance*3;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 8 && pos == 11) {			
			currPos = 11;
			yEnd = downDistance*3;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 9 && pos == 12) {
			currPos = 12;
			yEnd = downDistance*3;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 10 && pos == 13) {
			currPos = 13;
			yEnd = downDistance*4;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 11 && pos == 14) {
			currPos = 14;
			yEnd = downDistance*4;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 12 && pos == 15) {
			currPos = 15;
			yEnd = downDistance*4;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 13 && pos == 16) {
			currPos = 16;
			yEnd = downDistance*5;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 14 && pos == 17) {
			currPos = 17;
			yEnd = downDistance*5;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 15 && pos == 18) {
			currPos = 18;
			yEnd = downDistance*5;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();			
		} else if (currPos == 16 && pos == 19) {
			currPos = 19;
			yEnd = downDistance*6;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 17 && pos == 20) {
			currPos = 20;
			yEnd = downDistance*6;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		} else if (currPos == 18 && pos == 21) {
			currPos = 21;
			yEnd = downDistance*6;	
			mSteps++;
			Logger.e("TEST", "mSteps: " + mSteps + " yEnd: " + yEnd);
			startMovement();
		}
	}

	/**
	 * Method is used to do the movement animation
	 */
	private void startMovement() {
		Logger.v(TAG, "xStart:" + xStart + " //yStart:" + yStart + " //xEnd:"
				+ xEnd + " //yEnd: " + yEnd);
		TranslateAnimation animation = new TranslateAnimation(xStart, xEnd,
				yStart, yEnd);
		animation.setDuration(1000);
		animation.setFillAfter(true);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Logger.e(TAG, "onAnimationEnd");
				if (isController) {
					isController = false;
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			
		});		
		iv1.startAnimation(animation);	
		checkLetter();		
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
	 * Method is used to check for completion of activity 
	 * and to reset the level
	 */
	private void checkLetter() {
		// set letter to correct position
		if (mSteps == 1) {
			tvAnswer1.setText(String.valueOf(arryLetters[mSteps-1]));
			startShimmerAnimation(tvAnswer1);
		} else if (mSteps == 2) {
			tvAnswer2.setText(String.valueOf(arryLetters[mSteps-1]));
			startShimmerAnimation(tvAnswer2);
		} else if (mSteps == 3) {
			tvAnswer3.setText(String.valueOf(arryLetters[mSteps-1]));
			startShimmerAnimation(tvAnswer3);
		} else if (mSteps == 4) {
			tvAnswer4.setText(String.valueOf(arryLetters[mSteps-1]));
			startShimmerAnimation(tvAnswer4);
		} else if (mSteps == 5) {
			tvAnswer5.setText(String.valueOf(arryLetters[mSteps-1]));
			startShimmerAnimation(tvAnswer5);
		} else if (mSteps == 6) {
			tvAnswer6.setText(String.valueOf(arryLetters[mSteps-1]));
			startShimmerAnimation(tvAnswer6);
		} else if (mSteps == 7) {
			tvAnswer7.setText(String.valueOf(arryLetters[mSteps-1]));
			startShimmerAnimation(tvAnswer7);
		} else if (mSteps == 8) {
			tvAnswer8.setText(String.valueOf(arryLetters[mSteps-1]));
			startShimmerAnimation(tvAnswer8);
		} else if (mSteps == 9) {
			tvAnswer9.setText(String.valueOf(arryLetters[mSteps-1]));
			startShimmerAnimation(tvAnswer9);
		}
		
		// check if maze is completed
		if (mSteps >= arryPath.size()) {
			Logger.i(TAG, "maze completed");
			mSolvedWords++;		
			
			// speak the completed word
			reviewWord();
			
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {	
					speakText(mWord);
					if ( mSolvedWords>= 3) {
						// TODO: play sounds, animations, messaging and add rewards for completing level
						isLvUnlockRecent = false;
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
						mHandler.postDelayed(new Runnable() {
							@Override
							public void run() {
								startRewardAnim();
							}
						}, 3500);						
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
			}, 3500);				
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
	 * Method is used to
	 * @param isLvUnlockRecent
	 */
	@SuppressLint("InflateParams")
	private void startRewardAnim() {
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
	 * Method is used to initialize the game level; sets level, default word,
	 * and then calls method to generate the UI components.
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
		int temp = r.nextInt(4);
		if (temp >= 0) {
			speakText("Fly the spaceship through the maze!");
		} else if (temp == 1) {
			speakText("Can you get through the maze?");
		} else if (temp == 2) {
			speakText("Spell the correct word to get through the maze!");
		} else {
			speakText("Lets get through this maze together!");
		}
	}
	
	/**
	 * Method is used to do the movement animation
	 */
	private void reset() {
		xStart = 0;
		xEnd = 0;
		yStart = 0;
		yEnd = 0;
		downDistance = 0;
		TranslateAnimation animation = new TranslateAnimation(xStart, xEnd,
				yStart, yEnd);
		animation.setDuration(0);
		animation.setFillAfter(true);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Logger.e(TAG, "onAnimationEnd");
				if (isController) {
					isController = false;
				}
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}
			
		});		
		iv1.startAnimation(animation);	
		
		// reset color tiles
		pos1.setBackgroundColor(getResources().getColor(R.color.white));
		pos2.setBackgroundColor(getResources().getColor(R.color.white));
		pos3.setBackgroundColor(getResources().getColor(R.color.white));
		pos4.setBackgroundColor(getResources().getColor(R.color.white));
		pos5.setBackgroundColor(getResources().getColor(R.color.white));
		pos6.setBackgroundColor(getResources().getColor(R.color.white));
		pos7.setBackgroundColor(getResources().getColor(R.color.white));
		pos8.setBackgroundColor(getResources().getColor(R.color.white));
		pos9.setBackgroundColor(getResources().getColor(R.color.white));
		pos10.setBackgroundColor(getResources().getColor(R.color.white));
		pos11.setBackgroundColor(getResources().getColor(R.color.white));
		pos12.setBackgroundColor(getResources().getColor(R.color.white));
		pos13.setBackgroundColor(getResources().getColor(R.color.white));
		pos14.setBackgroundColor(getResources().getColor(R.color.white));
		pos15.setBackgroundColor(getResources().getColor(R.color.white));
		pos16.setBackgroundColor(getResources().getColor(R.color.white));
		pos17.setBackgroundColor(getResources().getColor(R.color.white));
		pos18.setBackgroundColor(getResources().getColor(R.color.white));
		pos19.setBackgroundColor(getResources().getColor(R.color.white));
		pos20.setBackgroundColor(getResources().getColor(R.color.white));
		pos21.setBackgroundColor(getResources().getColor(R.color.white));
	}

	/**
	 * Method is used to setup the game level
	 */
	private void generateLevel() {
		speakInstructions();
		
		// reset image position
		if (mSolvedWords > 0) {
			reset();
		}

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
		
		// setup maze path
		if (num == 3) {
			Utils.setViewVisibility(true, v1, v2, v3, tvAnswer1, tvAnswer2,
					tvAnswer3, pos1, pos2, pos4, pos5, end1, ll1, ll2);

			tv1.setText(String.valueOf(arryLetters[0]));
			pos1.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
			arryPath.add(1);
			int temp = r.nextInt(2);
			if (temp == 0) {
				tv2.setText(String.valueOf(arryLetters[1]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				arryPath.add(2);
			} else {				
				tv4.setText(String.valueOf(arryLetters[1]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				arryPath.add(4);
			}		
			tv5.setText(String.valueOf(arryLetters[2]));
			pos5.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
			arryPath.add(5);
		} else if (num == 4) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, tvAnswer1, tvAnswer2,
					tvAnswer3, tvAnswer4, pos1, pos2, pos3, pos4, pos5, pos6,
					end1, ll1, ll2);

			tv1.setText(String.valueOf(arryLetters[0]));
			pos1.setBackgroundColor(getResources().getColor(R.color.material_blue_500_color_code));
			arryPath.add(1);
			int temp = r.nextInt(3);
			if (temp == 0) {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv3.setText(String.valueOf(arryLetters[2]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos3.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
				arryPath.add(2);
				arryPath.add(3);
			} else if (temp == 1) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
				arryPath.add(4);
				arryPath.add(5);
			} else {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
				arryPath.add(2);
				arryPath.add(5);
			}
			tv6.setText(String.valueOf(arryLetters[3]));
			pos6.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
			arryPath.add(6);
		} else if (num == 5) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, v5, tvAnswer1,
					tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5, pos1, pos2,
					pos3, pos4, pos5, pos6, pos7, pos8, pos9, end2, ll1, ll2, ll3);

			tv1.setText(String.valueOf(arryLetters[0]));
			pos1.setBackgroundColor(getResources().getColor(R.color.material_lime_500_color_code));
			arryPath.add(1);
			int temp = r.nextInt(4);
			if (temp == 0) {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv3.setText(String.valueOf(arryLetters[2]));
				tv6.setText(String.valueOf(arryLetters[3]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos3.setBackgroundColor(getResources().getColor(R.color.material_blue_500_color_code));
				pos6.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				arryPath.add(2);
				arryPath.add(3);
				arryPath.add(6);
			} else if (temp == 1) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv7.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos7.setBackgroundColor(getResources().getColor(R.color.material_blue_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				arryPath.add(4);
				arryPath.add(7);
				arryPath.add(8);
			} else if (temp == 2) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_blue_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				arryPath.add(4);
				arryPath.add(5);
				arryPath.add(8);
			} else {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_blue_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				arryPath.add(2);
				arryPath.add(5);
				arryPath.add(8);
			}
			tv9.setText(String.valueOf(arryLetters[4]));
			pos9.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
			arryPath.add(9);
		} else if (num == 6) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, v5, v6, tvAnswer1,
					tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5, tvAnswer6,
					pos1, pos2, pos3, pos4, pos5, pos6, pos7, pos8, pos9,
					pos10, pos11, pos12, end3, ll1, ll2, ll3, ll4);

			tv1.setText(String.valueOf(arryLetters[0]));
			pos1.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
			arryPath.add(1);
			int temp = r.nextInt(5);
			if (temp == 0) {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv3.setText(String.valueOf(arryLetters[2]));
				tv6.setText(String.valueOf(arryLetters[3]));
				tv9.setText(String.valueOf(arryLetters[4]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));
				pos3.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));
				pos6.setBackgroundColor(getResources().getColor(R.color.material_lime_500_color_code));
				pos9.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				arryPath.add(2);
				arryPath.add(3);
				arryPath.add(6);
				arryPath.add(9);
			} else if (temp == 1) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv7.setText(String.valueOf(arryLetters[2]));
				tv10.setText(String.valueOf(arryLetters[3]));
				tv11.setText(String.valueOf(arryLetters[4]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));
				pos7.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));
				pos10.setBackgroundColor(getResources().getColor(R.color.material_lime_500_color_code));
				pos11.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				arryPath.add(4);
				arryPath.add(7);
				arryPath.add(10);
				arryPath.add(11);
			} else if (temp == 2) {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				tv9.setText(String.valueOf(arryLetters[4]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_lime_500_color_code));
				pos9.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				arryPath.add(2);
				arryPath.add(5);
				arryPath.add(8);
				arryPath.add(9);
			} else if (temp == 3) {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				tv11.setText(String.valueOf(arryLetters[4]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_lime_500_color_code));
				pos11.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				arryPath.add(2);
				arryPath.add(5);
				arryPath.add(8);
				arryPath.add(11);
			} else {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv7.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				tv11.setText(String.valueOf(arryLetters[4]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));
				pos7.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_lime_500_color_code));
				pos11.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				arryPath.add(4);
				arryPath.add(7);
				arryPath.add(8);
				arryPath.add(11);
			}				
			tv12.setText(String.valueOf(arryLetters[5]));
			pos12.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
			arryPath.add(12);
		} else if (num == 7) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, v5, v6, v7,
					tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5,
					tvAnswer6, tvAnswer7, pos1, pos2, pos3, pos4, pos5, 
					pos6, pos7, pos8, pos9,pos10, pos11, pos12, pos13, 
					pos14, pos15, end4, ll1, ll2, ll3, ll4, ll5);

			tv1.setText(String.valueOf(arryLetters[0]));
			pos1.setBackgroundColor(getResources().getColor(R.color.material_blue_500_color_code));
			arryPath.add(1);
			int temp = r.nextInt(6);
			if (temp == 0) {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv3.setText(String.valueOf(arryLetters[2]));
				tv6.setText(String.valueOf(arryLetters[3]));
				tv9.setText(String.valueOf(arryLetters[4]));
				tv12.setText(String.valueOf(arryLetters[5]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				pos3.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos6.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos9.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos12.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
				arryPath.add(2);
				arryPath.add(3);
				arryPath.add(6);
				arryPath.add(9);
				arryPath.add(12);
			} else if (temp == 1) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv7.setText(String.valueOf(arryLetters[2]));
				tv10.setText(String.valueOf(arryLetters[3]));
				tv13.setText(String.valueOf(arryLetters[4]));
				tv14.setText(String.valueOf(arryLetters[5]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				pos7.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos10.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos13.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos14.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));				
				arryPath.add(4);
				arryPath.add(7);
				arryPath.add(10);
				arryPath.add(13);
				arryPath.add(14);
			} else if (temp == 2) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				tv11.setText(String.valueOf(arryLetters[4]));
				tv12.setText(String.valueOf(arryLetters[5]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos11.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos12.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));				
				arryPath.add(4);
				arryPath.add(5);
				arryPath.add(8);
				arryPath.add(11);
				arryPath.add(12);
			} else if (temp == 3) {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				tv9.setText(String.valueOf(arryLetters[4]));
				tv12.setText(String.valueOf(arryLetters[5]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos9.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos12.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));				
				arryPath.add(2);
				arryPath.add(5);
				arryPath.add(8);
				arryPath.add(9);
				arryPath.add(12);
			} else if (temp == 4) {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				tv6.setText(String.valueOf(arryLetters[3]));
				tv9.setText(String.valueOf(arryLetters[4]));
				tv12.setText(String.valueOf(arryLetters[5]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos6.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos9.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos12.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
				arryPath.add(2);
				arryPath.add(5);
				arryPath.add(6);
				arryPath.add(9);
				arryPath.add(12);
			} else {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				tv11.setText(String.valueOf(arryLetters[4]));
				tv14.setText(String.valueOf(arryLetters[5]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_amber_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos11.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos14.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
				arryPath.add(2);
				arryPath.add(5);
				arryPath.add(8);
				arryPath.add(11);
				arryPath.add(14);
			}
			tv15.setText(String.valueOf(arryLetters[6]));
			pos15.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));
			arryPath.add(15);
		} else if (num == 8) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, v5, v6, v7, v8,
					tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5,
					tvAnswer6, tvAnswer7, tvAnswer8, pos1, pos2, pos3, pos4, 
					pos5, pos6, pos7, pos8, pos9, pos10, pos11, pos12, pos13, 
					pos14, pos15, pos16, pos17, pos18, end5, ll1, ll2, ll3,
					ll4, ll5, ll6);

			tv1.setText(String.valueOf(arryLetters[0]));
			pos1.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
			arryPath.add(1);
			int temp = r.nextInt(7);		
			if (temp == 0) {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv3.setText(String.valueOf(arryLetters[2]));
				tv6.setText(String.valueOf(arryLetters[3]));
				tv9.setText(String.valueOf(arryLetters[4]));
				tv12.setText(String.valueOf(arryLetters[5]));
				tv15.setText(String.valueOf(arryLetters[6]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos3.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));
				pos6.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
				pos9.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos12.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos15.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));			
				arryPath.add(2);
				arryPath.add(3);
				arryPath.add(6);
				arryPath.add(9);
				arryPath.add(12);
				arryPath.add(15);
			} else if (temp == 1) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv7.setText(String.valueOf(arryLetters[2]));
				tv10.setText(String.valueOf(arryLetters[3]));
				tv13.setText(String.valueOf(arryLetters[4]));
				tv16.setText(String.valueOf(arryLetters[5]));
				tv17.setText(String.valueOf(arryLetters[6]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos7.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));
				pos10.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
				pos13.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos16.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos17.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				arryPath.add(4);
				arryPath.add(7);
				arryPath.add(10);
				arryPath.add(13);
				arryPath.add(16);
				arryPath.add(17);
			} else if (temp == 2) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv7.setText(String.valueOf(arryLetters[2]));
				tv10.setText(String.valueOf(arryLetters[3]));
				tv11.setText(String.valueOf(arryLetters[4]));
				tv14.setText(String.valueOf(arryLetters[5]));
				tv15.setText(String.valueOf(arryLetters[6]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos7.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));
				pos10.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
				pos11.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos14.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos15.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				arryPath.add(4);
				arryPath.add(7);
				arryPath.add(10);
				arryPath.add(11);
				arryPath.add(14);
				arryPath.add(15);
			} else if (temp == 3) {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				tv11.setText(String.valueOf(arryLetters[4]));
				tv12.setText(String.valueOf(arryLetters[5]));
				tv15.setText(String.valueOf(arryLetters[6]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
				pos11.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos12.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos15.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				arryPath.add(2);
				arryPath.add(3);
				arryPath.add(6);
				arryPath.add(9);
				arryPath.add(12);
				arryPath.add(15);
			} else if (temp == 4) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv7.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				tv11.setText(String.valueOf(arryLetters[4]));
				tv14.setText(String.valueOf(arryLetters[5]));
				tv17.setText(String.valueOf(arryLetters[6]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos7.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
				pos11.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos14.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos17.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				arryPath.add(4);
				arryPath.add(7);
				arryPath.add(8);
				arryPath.add(11);
				arryPath.add(14);
				arryPath.add(17);
			} else if (temp == 5) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv7.setText(String.valueOf(arryLetters[2]));
				tv10.setText(String.valueOf(arryLetters[3]));
				tv13.setText(String.valueOf(arryLetters[4]));
				tv14.setText(String.valueOf(arryLetters[5]));
				tv17.setText(String.valueOf(arryLetters[6]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos7.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));
				pos10.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
				pos13.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos14.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos17.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				arryPath.add(4);
				arryPath.add(7);
				arryPath.add(10);
				arryPath.add(13);
				arryPath.add(14);
				arryPath.add(17);
			} else {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv7.setText(String.valueOf(arryLetters[2]));
				tv10.setText(String.valueOf(arryLetters[3]));
				tv13.setText(String.valueOf(arryLetters[4]));
				tv14.setText(String.valueOf(arryLetters[5]));
				tv15.setText(String.valueOf(arryLetters[6]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos7.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));
				pos10.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
				pos13.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos14.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos15.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				arryPath.add(4);
				arryPath.add(7);
				arryPath.add(10);
				arryPath.add(13);
				arryPath.add(14);
				arryPath.add(15);
			}
			tv18.setText(String.valueOf(arryLetters[7]));
			pos18.setBackgroundColor(getResources().getColor(R.color.material_indigo_500_color_code));	
			arryPath.add(18);
		} else if (num == 9) {
			Utils.setViewVisibility(true, v1, v2, v3, v4, v5, v6, v7, v8, v9,
					tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5,
					tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9, pos1, pos2, pos3,  
					pos4, pos5, pos6, pos7, pos8, pos9,pos10, pos11, pos12, pos13, 
					pos14, pos15, pos16, pos17, pos18, pos19, pos20, pos21, end6,
					ll1, ll2, ll3, ll4, ll5, ll6, ll7);
			
			tv1.setText(String.valueOf(arryLetters[0]));
			pos1.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));
			arryPath.add(1);	
			int temp = r.nextInt(8);
			if (temp == 0) {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv3.setText(String.valueOf(arryLetters[2]));
				tv6.setText(String.valueOf(arryLetters[3]));
				tv9.setText(String.valueOf(arryLetters[4]));
				tv12.setText(String.valueOf(arryLetters[5]));
				tv15.setText(String.valueOf(arryLetters[6]));
				tv18.setText(String.valueOf(arryLetters[7]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos3.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos6.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				pos9.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos12.setBackgroundColor(getResources().getColor(R.color.material_indigo_500_color_code));	
				pos15.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos18.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));			
				arryPath.add(2);
				arryPath.add(3);
				arryPath.add(6);
				arryPath.add(9);
				arryPath.add(12);
				arryPath.add(15);
				arryPath.add(18);
			} else if (temp == 1) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv7.setText(String.valueOf(arryLetters[2]));
				tv10.setText(String.valueOf(arryLetters[3]));
				tv13.setText(String.valueOf(arryLetters[4]));
				tv16.setText(String.valueOf(arryLetters[5]));
				tv19.setText(String.valueOf(arryLetters[6]));
				tv20.setText(String.valueOf(arryLetters[7]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos7.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos10.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				pos13.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos16.setBackgroundColor(getResources().getColor(R.color.material_indigo_500_color_code));	
				pos19.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos20.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));	
				arryPath.add(4);
				arryPath.add(7);
				arryPath.add(10);
				arryPath.add(13);
				arryPath.add(16);
				arryPath.add(19);
				arryPath.add(20);
			} else if (temp == 2) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv7.setText(String.valueOf(arryLetters[2]));
				tv10.setText(String.valueOf(arryLetters[3]));
				tv11.setText(String.valueOf(arryLetters[4]));
				tv14.setText(String.valueOf(arryLetters[5]));
				tv15.setText(String.valueOf(arryLetters[6]));
				tv18.setText(String.valueOf(arryLetters[7]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos7.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos10.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				pos11.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos14.setBackgroundColor(getResources().getColor(R.color.material_indigo_500_color_code));	
				pos15.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos18.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));	
				arryPath.add(4);
				arryPath.add(7);
				arryPath.add(10);
				arryPath.add(11);
				arryPath.add(14);
				arryPath.add(15);
				arryPath.add(18);
			} else if (temp == 3) {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				tv11.setText(String.valueOf(arryLetters[4]));
				tv14.setText(String.valueOf(arryLetters[5]));
				tv17.setText(String.valueOf(arryLetters[6]));
				tv18.setText(String.valueOf(arryLetters[7]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				pos11.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos14.setBackgroundColor(getResources().getColor(R.color.material_indigo_500_color_code));	
				pos17.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos18.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));	
				arryPath.add(2);
				arryPath.add(5);
				arryPath.add(8);
				arryPath.add(11);
				arryPath.add(14);
				arryPath.add(17);
				arryPath.add(18);
			} else if (temp == 4) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv7.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				tv9.setText(String.valueOf(arryLetters[4]));
				tv12.setText(String.valueOf(arryLetters[5]));
				tv15.setText(String.valueOf(arryLetters[6]));
				tv18.setText(String.valueOf(arryLetters[7]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos7.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				pos9.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos12.setBackgroundColor(getResources().getColor(R.color.material_indigo_500_color_code));	
				pos15.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos18.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));	
				arryPath.add(4);
				arryPath.add(7);
				arryPath.add(8);
				arryPath.add(9);
				arryPath.add(12);
				arryPath.add(15);
				arryPath.add(18);
			} else if (temp == 5) {
				tv2.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				tv11.setText(String.valueOf(arryLetters[4]));
				tv14.setText(String.valueOf(arryLetters[5]));
				tv17.setText(String.valueOf(arryLetters[6]));
				tv20.setText(String.valueOf(arryLetters[7]));
				pos2.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				pos11.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos14.setBackgroundColor(getResources().getColor(R.color.material_indigo_500_color_code));	
				pos17.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos20.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));	
				arryPath.add(2);
				arryPath.add(5);
				arryPath.add(8);
				arryPath.add(11);
				arryPath.add(14);
				arryPath.add(17);
				arryPath.add(20);
			} else if (temp == 6) {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv5.setText(String.valueOf(arryLetters[2]));
				tv8.setText(String.valueOf(arryLetters[3]));
				tv11.setText(String.valueOf(arryLetters[4]));
				tv12.setText(String.valueOf(arryLetters[5]));
				tv15.setText(String.valueOf(arryLetters[6]));
				tv18.setText(String.valueOf(arryLetters[7]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos5.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos8.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				pos11.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos12.setBackgroundColor(getResources().getColor(R.color.material_indigo_500_color_code));	
				pos15.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos18.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));	
				arryPath.add(4);
				arryPath.add(5);
				arryPath.add(8);
				arryPath.add(11);
				arryPath.add(12);
				arryPath.add(15);
				arryPath.add(18);
			} else {
				tv4.setText(String.valueOf(arryLetters[1]));
				tv7.setText(String.valueOf(arryLetters[2]));
				tv10.setText(String.valueOf(arryLetters[3]));
				tv13.setText(String.valueOf(arryLetters[4]));
				tv14.setText(String.valueOf(arryLetters[5]));
				tv17.setText(String.valueOf(arryLetters[6]));
				tv20.setText(String.valueOf(arryLetters[7]));
				pos4.setBackgroundColor(getResources().getColor(R.color.material_light_blue_500_color_code));
				pos7.setBackgroundColor(getResources().getColor(R.color.material_green_500_color_code));
				pos10.setBackgroundColor(getResources().getColor(R.color.material_purple_500_color_code));	
				pos13.setBackgroundColor(getResources().getColor(R.color.material_yellow_500_color_code));
				pos14.setBackgroundColor(getResources().getColor(R.color.material_indigo_500_color_code));	
				pos17.setBackgroundColor(getResources().getColor(R.color.material_light_green_500_color_code));
				pos20.setBackgroundColor(getResources().getColor(R.color.material_red_500_color_code));	
				arryPath.add(4);
				arryPath.add(7);
				arryPath.add(10);
				arryPath.add(13);
				arryPath.add(14);
				arryPath.add(17);
				arryPath.add(20);
			}
			tv21.setText(String.valueOf(arryLetters[8]));
			pos21.setBackgroundColor(getResources().getColor(R.color.material_orange_500_color_code));	
			arryPath.add(21);
		}
	}

	/**
	 * Method is used for resetting visibility on views
	 */
	private void resetVisibility() {
		// reset correct and incorrect trackers
		mSteps = 0;
		xStart = 0;
		yStart = 0; 
		xEnd = 0; 
		yEnd = 0; 
		currPos = 0;	
		
		// clear maze letter views
		Utils.clearText(tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10,
				tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20, tv21,
				tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5, tvAnswer6, 
				tvAnswer7, tvAnswer8, tvAnswer9);

		// reset maze views
		Utils.setViewVisibility(false, v1, v2, v3, v4, v5, v6, v7, v8, v9,
				tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5,
				tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9, pos1, pos2, pos3,
				pos4, pos5, pos6, pos7, pos8, pos9, pos10, pos11, pos12, pos13,
				pos14, pos15, pos16, pos17, pos18, pos19, pos20, pos21, 
				end1, end2, end3, end4, end5, end6);
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
			if (mLevel == 2) {
				MusicUtils.start(mContext, 3);
			} else if (mLevel == 6) {
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
