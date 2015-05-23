package com.blog.ljtatum.eekspellingi.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.anim.Shimmer;
import com.blog.ljtatum.eekspellingi.anim.ShimmerTextView;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.helper.Messages;
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.MusicUtils;
import com.blog.ljtatum.eekspellingi.util.ShareAppUtil;
import com.blog.ljtatum.eekspellingi.util.Utils;
import com.blog.ljtatum.eekspellingi.view.CircleImageView;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class PictureDropActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = PictureDropActivity.class.getSimpleName();
	
	private Activity mActivity;
	private Context mContext;
	
	private RelativeLayout rlParent;
	private FrameLayout dragToView;
	private TextView tvHint;
	private ImageView ivBack, ivBanner, ivDrag;
	private View v1, v2, v3, v4, v5, v6, v7, v8, v9;
	private ShimmerTextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4,
		tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9;
	private ShareAppUtil shareApp;
	private SharedPref sharedPref;
	private boolean containDraggable = false, earnPoint = false;;
	private Random r;
	private int mLevel = 0, itemDragCount = 0;
	private String mWord;
	private char[] arryChar = null;
	private int[] arryDropImg = {R.drawable.drop_ball, R.drawable.drop_bone, R.drawable.drop_car,
			R.drawable.drop_cup, R.drawable.drop_fish, R.drawable.drop_dolphin, R.drawable.drop_earth,
			R.drawable.drop_glasses, R.drawable.drop_pencil, R.drawable.drop_helmet, 
			R.drawable.drop_airplane, R.drawable.drop_butterfly, R.drawable.drop_elephant,
			R.drawable.drop_pyramid, R.drawable.drop_scissors};
	private String[] arryDropWord = {"ball", "bone", "car", "cup", "fish", "dolphin", "earth",
			"glasses", "pencil", "helmet", "airplane", "butterfly", "elephant", "pyramid", "scissors"};
	
	private Handler mHandler;
	private List<String> arryPrev;
	private int xCord, yCord;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_drop);
		getIds();	
	}
	
	
	private void getIds() {
		mActivity = PictureDropActivity.this;
		mContext = PictureDropActivity.this;
		shareApp = new ShareAppUtil();
		sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
		mHandler = new Handler();
		r = new Random();
		rlParent = (RelativeLayout) findViewById(R.id.rl_parent);
		dragToView = (FrameLayout) findViewById(R.id.drag_view);
		v1 = findViewById(R.id.v1);
		v2 = findViewById(R.id.v2);
		v3 = findViewById(R.id.v3);
		v4 = findViewById(R.id.v4);
		v5 = findViewById(R.id.v5);
		v6 = findViewById(R.id.v6);
		v7 = findViewById(R.id.v7);
		v8 = findViewById(R.id.v8);
		v9 = findViewById(R.id.v9);
		tvAnswer1 = (ShimmerTextView) findViewById(R.id.tv_answer_1);
		tvAnswer2 = (ShimmerTextView) findViewById(R.id.tv_answer_2);
		tvAnswer3 = (ShimmerTextView) findViewById(R.id.tv_answer_3);
		tvAnswer4 = (ShimmerTextView) findViewById(R.id.tv_answer_4);
		tvAnswer5 = (ShimmerTextView) findViewById(R.id.tv_answer_5);
		tvAnswer6 = (ShimmerTextView) findViewById(R.id.tv_answer_6);
		tvAnswer7 = (ShimmerTextView) findViewById(R.id.tv_answer_7);
		tvAnswer8 = (ShimmerTextView) findViewById(R.id.tv_answer_8);
		tvAnswer9 = (ShimmerTextView) findViewById(R.id.tv_answer_9);
		tvHint = (TextView) findViewById(R.id.tv_hint);
		ivBack = (ImageView) findViewById(R.id.iv_back);
		ivBanner = (ImageView) findViewById(R.id.iv_banner);
		ivDrag = (ImageView) findViewById(R.id.iv_drag);
		ivDrag.setTag(TAG);
		
		ivBack.setOnClickListener(this);
		ivBanner.setOnClickListener(this);	
		
		// retrieve level
		Intent intent = getIntent();
		if (!Utils.checkIfNull(intent)) {
			mLevel = intent.getIntExtra(Constants.LV_SELECTED, 0);
			Logger.i(TAG, "level: " + mLevel);
		}
		
		// set default banner
		setDefaultBanner(mContext, ivBanner);
		
		// set default drag object
		ivDrag.setImageResource(getDragDrawable());		
		
		// set click listeners
		ivDrag.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				Logger.i(TAG, "onLongClick registered");
				if (!containDraggable) {
					ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
					String[] mineTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
					ClipData dragData = new ClipData(v.getTag().toString(), mineTypes, item);
					// instantiate the drag shadow builder
					DragShadowBuilder mShadow = new DragShadowBuilder(v);
					v.startDrag(dragData, mShadow, v, 0);
					dragToView.setOnDragListener(dragToListener);
					return true;
				}
				
				Logger.d(TAG, "RETURNING FALSE!");
				return false;
				
			}
		});
		
		ivDrag.setOnDragListener(new OnDragListener() {

			@Override
			public boolean onDrag(View v, DragEvent event) {
				// TODO Auto-generated method stub
				
				// defines a variable to store the action type for the incoming event
				final int action = event.getAction();
				
				switch (action) {
				case DragEvent.ACTION_DRAG_STARTED:
					Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_STARTED");
					ivDrag.setVisibility(View.INVISIBLE);
					
					if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
	                    // Invalidate the view to force a redraw in the new tint 
	                    v.invalidate();
	 
	                    // returns true to indicate that the View can accept the dragged data 
	                    return true;
						
					}
					
					/*
					 * return false. During the current drag and drop operation, this
					 * view will not receive events until ACTION_DRAG_ENDED is sent
					 */
					return false;
				case DragEvent.ACTION_DRAG_ENTERED:
					Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_ENTERED");

					// Invalidate the view to force a redraw in the new tint 
	                v.invalidate();
					
					return true;
				case DragEvent.ACTION_DRAG_EXITED:
					Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_EXITED");

					// Invalidate the view to force a redraw in the new tint 
	                v.invalidate();
	 
	                return true; 
				case DragEvent.ACTION_DRAG_LOCATION:	
					Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_LOCATION");
					xCord = (int) event.getX();
					yCord = (int) event.getY();
					Logger.i(TAG, "xCord: " + xCord + " // yCord: " + yCord);

	                return true; 
				case DragEvent.ACTION_DRAG_ENDED:
					Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_ENDED");
					Logger.i(TAG, "xCord: " + xCord + " // yCord: " + yCord);

					// Invalidates the view to force a redraw 
	                v.invalidate();
	                
	                // set visibility
					if (ivDrag.getVisibility() != View.VISIBLE) {
						ivDrag.setVisibility(View.VISIBLE);
					}

	                if (event.getResult()) {
	                	Logger.d(TAG, "The drop was successful");
	                	if (!earnPoint) {
	                		earnPoint = true;
	                		itemDragCount++;
	                		Logger.d(TAG, "itemDragCount: " + itemDragCount);
	                	}	                	                	
	                	
	                	String c = "";
	                	for (int i = 0; i < arryChar.length; i++) {               		
		                	c = String.valueOf(arryChar[i]);
		                	
		                	if (i == 0) {
        						tvAnswer1.setText(c);
        						startShimmerAnimation(tvAnswer1);
        					} else if (i == 1) {
        						tvAnswer2.setText(c);
        						startShimmerAnimation(tvAnswer2);
        					} else if (i == 2) {
        						tvAnswer3.setText(c);
        						startShimmerAnimation(tvAnswer3);
        					} else if (i == 3) {
        						tvAnswer4.setText(c);
        						startShimmerAnimation(tvAnswer4);
        					} else if (i == 4) {
        						tvAnswer5.setText(c);
        						startShimmerAnimation(tvAnswer5);
        					} else if (i == 5) {
        						tvAnswer6.setText(c);
        						startShimmerAnimation(tvAnswer6);
        					} else if (i == 6) {
        						tvAnswer7.setText(c);
        						startShimmerAnimation(tvAnswer7);
        					} else if (i == 7) {
        						tvAnswer8.setText(c);
        						startShimmerAnimation(tvAnswer8);
        					} else if (i == 8) {
        						tvAnswer9.setText(c);
        						startShimmerAnimation(tvAnswer9);
        					}
	                	}
	                	
	                	Crouton.showText(mActivity, mWord, Style.INFO);
	            		speakText(mWord);
	            		
	            		mHandler.postDelayed(new Runnable() {
	    					@Override
	    					public void run() {	
	    			
	    						if (itemDragCount >= 3) {
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
	    							// remove view from drop container back to parent
	    							ViewGroup parent = (ViewGroup) ivDrag.getParent();
									parent.removeView(ivDrag);
									RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
									params.addRule(RelativeLayout.CENTER_HORIZONTAL);								
									ivDrag.setLayoutParams(params);

									generateLevel();
	    						}   				
	    					}
	    				}, 4000);
	            		
	                } else { 
	                	Logger.d(TAG, "The drop didn't work");
	                } 

	                // returns true; the value is ignored. 
	                return true;
				default:
					Logger.e("DragDrop Example","Unknown action type received by OnDragListener.");
					break;
				}
				return false;
			}			
		});
		
		// generate level
		generateLevel();
	}
	
	/**
	 * Method is implements an onDragListener on for the space to be dragged into
	 */
	private View.OnDragListener dragToListener = new View.OnDragListener() {	
		@Override
		public boolean onDrag(View v, DragEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				Logger.v(TAG, "Action is DragEvent.ACTION_DRAG_STARTED");
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				Logger.v(TAG, "Action is DragEvent.ACTION_DRAG_ENTERED");
				containDraggable = true;
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				Logger.v(TAG, "Action is DragEvent.ACTION_DRAG_EXITED");
				containDraggable = false;
				break;
			case DragEvent.ACTION_DROP:
				Logger.v(TAG, "Action is DragEvent.ACTION_DRAG_DROP");
				Logger.v(TAG, "xCord: " + xCord + " // yCord: " + yCord);

				if (containDraggable) {
					View mView = (View) event.getLocalState();
					ViewGroup parent = (ViewGroup) mView.getParent();
					parent.removeView(mView);
					dragToView.addView(mView);	
				}
				
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				Logger.v(TAG, "Action is DragEvent.ACTION_DRAG_ENDED");
				break;
			default:
				return false;			
			}
			return true;
		}
	};	
	
	/**
	 * Method is used to setup the game level
	 */
	private void generateLevel() {
		// set new drag object image
		if (itemDragCount > 0) {
			ivDrag.setImageResource(getDragDrawable());
		}
		
		// reset point controller boolean
		if (earnPoint) {
    		earnPoint = false;
    	}
		
		// reset drag controller boolean
		if (containDraggable) {
			containDraggable = false;
		}

		// set visibility of views
		setVisibility(mWord.length());
		
		// reset jumbled array
		if (!Utils.checkIfNull(arryChar)) {
			arryChar = null;
		}
		
		arryChar = mWord.toCharArray();		
	}
	
	/**
	 * Sets the number of needed visible views to form the correct word
	 *
	 * @param num
	 */
	private void setVisibility(int num) {
		if (itemDragCount > 0) {
			resetVisibility();
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
		// clear letter views
		Utils.clearText(tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4,
				tvAnswer5, tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9);

		// reset letter views
		Utils.setViewVisibility(false, v1, v2, v3, v4, v5, v6, v7, v8, v9,
				tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvAnswer5,
				tvAnswer6, tvAnswer7, tvAnswer8, tvAnswer9);
	}
	
	/**
	 * Method will return a drag object
	 * @return
	 */
	private int getDragDrawable() {
		int temp = 0;
		String instructions = "";
		if (mLevel == 3) {
			temp = r.nextInt(5);
			if (temp == 0) {
				mWord = arryDropWord[0];
				instructions = "Drop the " + arryDropWord[0] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[0];
			} else if (temp == 1) {
				mWord = arryDropWord[1];
				instructions = "Drop the " + arryDropWord[1] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[1];
			} else if (temp == 2) {
				mWord = arryDropWord[2];
				instructions = "Drop the " + arryDropWord[2] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[2];
			} else if (temp == 3) {
				mWord = arryDropWord[3];
				instructions = "Drop the " + arryDropWord[3] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[3];
			} else {
				mWord = arryDropWord[4];
				instructions = "Drop the " + arryDropWord[4] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[4];
			}
		} else if (mLevel == 7) {
			temp = r.nextInt(5) + 5;
			if (temp == 5) {
				mWord = arryDropWord[5];
				instructions = "Drop the " + arryDropWord[5] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[5];
			} else if (temp == 6) {
				mWord = arryDropWord[6];
				instructions = "Drop the " + arryDropWord[6] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[6];
			} else if (temp == 7) {
				instructions = "Drop the " + arryDropWord[7] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[7];
			} else if (temp == 8) {
				mWord = arryDropWord[8];
				instructions = "Drop the " + arryDropWord[8] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[8];
			} else {
				mWord = arryDropWord[9];
				instructions = "Drop the " + arryDropWord[9] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[9];
			}
		} else {
			temp = r.nextInt(5) + 10;
			if (temp == 10) {
				mWord = arryDropWord[10];
				instructions = "Drop the " + arryDropWord[10] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[10];
			} else if (temp == 11) {
				mWord = arryDropWord[11];
				instructions = "Drop the " + arryDropWord[11] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[11];
			} else if (temp == 12) {
				mWord = arryDropWord[12];
				instructions = "Drop the " + arryDropWord[12] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[12];
			} else if (temp == 13) {
				mWord = arryDropWord[13];
				instructions = "Drop the " + arryDropWord[13] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[13];
			} else {
				mWord = arryDropWord[14];
				instructions = "Drop the " + arryDropWord[14] + " into the boat!";
				tvHint.setText(instructions);
				speakText(instructions);
				Crouton.showText(mActivity, instructions, Style.INFO);
				return arryDropImg[14];
			}
		}	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
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
		startViewAnim(dragToView);
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
		Crouton.cancelAllCroutons();
		Crouton.clearCroutonsForActivity(this);
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		// transition animation
		overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
	}

}
