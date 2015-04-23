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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PictureDropActivity extends BaseActivity implements
		OnClickListener {
	private static final String TAG = PictureDropActivity.class.getSimpleName();

	private Activity mActivity;
	private Context mContext;

	private ImageView ivBack, ivBanner, ivDrag;
	private ShareAppUtil shareApp;
	private SharedPref sharedPref;
	private LinearLayout.LayoutParams layoutParams;
	
	//Drawable enterShape = getResources().getDrawable(R.drawable.a);
	//Drawable normalShape = getResources().getDrawable(R.drawable.ph_tree);

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
				Logger.i(TAG, "onLongClick registered");
				 ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
				 String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
				 ClipData dragData = new ClipData(v.getTag().toString(),
				 mimeTypes, item);
				 // Instantiates the drag shadow builder.
				 DragShadowBuilder mShadow = new DragShadowBuilder(ivDrag);
				 ivDrag.startDrag(dragData, mShadow, null, 0);

//				ClipData data = ClipData.newPlainText("", "");
//				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(ivDrag);
//				ivDrag.startDrag(data, shadowBuilder, ivDrag, 0);
//				ivDrag.setVisibility(View.INVISIBLE);

				return true;
			}
		});

		ivDrag.setOnDragListener(new OnDragListener() {

			 @Override
			 public boolean onDrag(View v, DragEvent event) {
			 // TODO Auto-generated method stub
			 switch (event.getAction()) {
			 case DragEvent.ACTION_DRAG_STARTED:
			 layoutParams = (LinearLayout.LayoutParams) v.getLayoutParams();
			 Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_STARTED");
			 // Do nothing
			 break;
			 case DragEvent.ACTION_DRAG_ENTERED:
			 Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_ENTERED");
			 int x_cord = (int) event.getX();
			 int y_cord = (int) event.getY();
			 break;
			 case DragEvent.ACTION_DRAG_EXITED :
			 Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_EXITED");
			 x_cord = (int) event.getX();
			 y_cord = (int) event.getY();
			 layoutParams.leftMargin = x_cord;
			 layoutParams.topMargin = y_cord;
			 v.setLayoutParams(layoutParams);
			 break;
			 case DragEvent.ACTION_DRAG_LOCATION :
			 Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_LOCATION");
			 x_cord = (int) event.getX();
			 y_cord = (int) event.getY();
			 break;
			 case DragEvent.ACTION_DRAG_ENDED :
			 Logger.i(TAG, "Action is DragEvent.ACTION_DRAG_ENDED");
			 // Do nothing
			 break;
			 case DragEvent.ACTION_DROP:
			 Logger.i(TAG, "ACTION_DROP event");
			 // Do nothing
			 break;
			 default:
			 break;
			 }
			 return true;
			 }

//			@Override
//			public boolean onDrag(View v, DragEvent event) {
//				int action = event.getAction();
//				switch (event.getAction()) {
//				case DragEvent.ACTION_DRAG_STARTED:
//					// do nothing
//					break;
//				case DragEvent.ACTION_DRAG_ENTERED:
//					v.setBackgroundDrawable(enterShape);
//					break;
//				case DragEvent.ACTION_DRAG_EXITED:
//					v.setBackgroundDrawable(normalShape);
//					break;
//				case DragEvent.ACTION_DROP:
//					// Dropped, reassign View to ViewGroup
//					View view = (View) event.getLocalState();
//					ViewGroup owner = (ViewGroup) view.getParent();
//					owner.removeView(view);
//					LinearLayout container = (LinearLayout) v;
//					container.addView(view);
//					view.setVisibility(View.VISIBLE);
//					break;
//				case DragEvent.ACTION_DRAG_ENDED:
//					v.setBackgroundDrawable(normalShape);
//				default:
//					break;
//				}
//				return true;
//			}

		});

	}

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

	/**
	 * Method is used to speak instructions
	 */
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
		speakInstructions();
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
	}

}
