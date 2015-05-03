package com.blog.ljtatum.eekspellingi.activity;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.MusicUtils;
import com.blog.ljtatum.eekspellingi.util.ShareAppUtil;
import com.blog.ljtatum.eekspellingi.util.Utils;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class PictureDropActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = PictureDropActivity.class.getSimpleName();
	
	private Activity mActivity;
	private Context mContext;
	
	private View dragToView;
	private ImageView ivBack, ivBanner, ivDrag;
	private ShareAppUtil shareApp;
	private SharedPref sharedPref;
	private RelativeLayout.LayoutParams layoutParams;
	private boolean containDragable = false;
	
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
		dragToView = (View) findViewById(R.id.drag_view);
		ivBack = (ImageView) findViewById(R.id.iv_back);
		ivBanner = (ImageView) findViewById(R.id.iv_banner);
		ivDrag = (ImageView) findViewById(R.id.iv_drag);
		ivDrag.setTag(TAG);
		
		ivBack.setOnClickListener(this);
		ivBanner.setOnClickListener(this);	
		
		// set default banner
		setDefaultBanner(mContext, ivBanner);
		
		ivDrag.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				Logger.i(TAG, "onLongClick registered");
				ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
				String[] mineTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
				ClipData dragData = new ClipData(v.getTag().toString(), mineTypes, item);
				// instantiate the drag shadow builder
				DragShadowBuilder mShadow = new DragShadowBuilder(v);
				v.startDrag(dragData, mShadow, v, 0);
				dragToView.setOnDragListener(dragToListener);
				return true;
			}
		});
		
		ivDrag.setOnDragListener(new OnDragListener() {

			@Override
			public boolean onDrag(View v, DragEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:
					Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_STARTED");
					layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
					ivDrag.setVisibility(View.INVISIBLE);
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_ENTERED");
					containDragable = true;
					xCord = (int) event.getX();
					yCord = (int) event.getY();
					Logger.i(TAG, "xCord: " + xCord + " // yCord: " + yCord);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_EXITED");
					containDragable = false;
					xCord = (int) event.getX();
					yCord = (int) event.getY();
					Logger.i(TAG, "xCord: " + xCord + " // yCord: " + yCord);
					
					// update drag position
					layoutParams.leftMargin = xCord;
					layoutParams.topMargin = yCord;
					v.setLayoutParams(layoutParams);
					break;
				case DragEvent.ACTION_DRAG_LOCATION:	
					Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_LOCATION");
					xCord = (int) event.getX();
					yCord = (int) event.getY();
					Logger.i(TAG, "xCord: " + xCord + " // yCord: " + yCord);
					break;
				case DragEvent.ACTION_DRAG_ENDED:
					Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_ENDED");
					ivDrag.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Logger.d(TAG, "inside runnable");
							if (ivDrag.getVisibility() != View.VISIBLE) {
								ivDrag.setVisibility(View.VISIBLE);
							}
						}
					});
					break;
				case DragEvent.ACTION_DROP:	
					Logger.i(TAG, "Action is DragEvent.ACTION_DROP");
					// do nothing
					break;
				default:
					break;
				}
				return true;
			}			
		});
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
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				Logger.v(TAG, "Action is DragEvent.ACTION_DRAG_EXITED");
				break;
			case DragEvent.ACTION_DROP:
				Logger.e(TAG, "Action is DragEvent.ACTION_DRAG_DROP");
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
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
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
	
	
	private void speakInstructions() {
		Crouton.showText(mActivity, "Drag the image to the square!", Style.INFO);
		speakText("Drag the image to the square!");
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
