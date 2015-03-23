package com.blog.ljtatum.eekspellingi.activity;


import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.adapter.SelectAdapter;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.model.SelectModel;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.MusicUtils;
import com.blog.ljtatum.eekspellingi.util.ShareAppUtil;
import com.blog.ljtatum.eekspellingi.util.Utils;

public class SelectActivity extends BaseActivity {
	private final static String TAG = SelectActivity.class.getSimpleName();

	private Context mContext;	
	private ImageView ivBack, ivBanner;
	private ListView lv;

	private ShareAppUtil shareApp;
	private SharedPref sharedPref;
	private SelectAdapter selectAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select);	
		getIds();
	}
	
	private void getIds() {
		mContext = SelectActivity.this;
		shareApp = new ShareAppUtil();
		sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
		ivBack = (ImageView) findViewById(R.id.iv_back);
		ivBanner = (ImageView) findViewById(R.id.iv_banner);
		lv = (ListView) findViewById(R.id.lv);
		
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

		ArrayList<SelectModel> arrySelect = populateSelectModel();
		
		// set up adapter
		selectAdapter = new SelectAdapter(mContext, arrySelect);
		lv.setAdapter(selectAdapter);	
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub	
				if (position == 0 || position == 4 || position == 8) {
					Logger.i(TAG, "launching LetterTree :: pos " + position);
					goToActivity(mContext, LetterTreeActivity.class, position);
				} else if (position == 1 || position == 5 || position == 9) {
					Logger.i(TAG, "launching SpellingTile :: pos " + position);
					goToActivity(mContext, SpellingTileActivity.class, position);
				} else if (position == 2 || position == 6 || position == 10) {
					Logger.i(TAG, "launching WordMaze :: pos " + position);
					goToActivity(mContext, WordMazeActivity.class, position);
				} else if (position == 3 || position == 7 || position == 11) {
					Logger.i(TAG, "launching PictureDrop :: pos " + position);
					goToActivity(mContext, PictureDropActivity.class, position);
				}
			}		
		});		
	}
	
	/**
	 * Method is used to start animations
	 */
	private void startAnimations() {
		startButtonAnim(ivBack);
		startBannerAnim(mContext, ivBanner);
	}
	
	/**
	 * Method is used to populate a model class with title and message
	 * @return arryList of titles and messages
	 */
	private ArrayList<SelectModel> populateSelectModel() {
		
		SelectModel model = null;
		for (int i = 0; i < 12; i++) {
			model = new SelectModel();
			model.setTitle(generateTitle(i));
			model.setMessage(generateMessage(i));
			SelectModel.addArrySelect(model);		
		}
		return SelectModel.getArrySelect();
	}
	
	/**
	 * Method is used to generate title
	 * @param level
	 * @return
	 */
	private String generateTitle(int level) {
		String mTitle = "";		
		if (level == 0 || level == 4 || level == 8) {
			mTitle = getResources().getString(R.string.txt_letter_tree);
		} else if (level == 1 || level == 5 || level == 9) {
			mTitle = getResources().getString(R.string.txt_spelling_tile);
		} else if (level == 2 || level == 6 || level == 10) {
			mTitle = getResources().getString(R.string.txt_word_maze);
		} else if (level == 3 || level == 7 || level == 11) {
			mTitle = getResources().getString(R.string.txt_picture_drop);
		}
		return mTitle;
	}
	
	/**
	 * Method is used to generate message
	 * @param level
	 * @return
	 */
	private String generateMessage(int level) {
		String mMessage = "";	
		if (level == 0 || level == 4 || level == 8) {
			mMessage = String.valueOf(level);
		} else if (level == 1 || level == 5 || level == 9) {
			mMessage = String.valueOf(level);
		} else if (level == 2 || level == 6 || level == 10) {
			mMessage = String.valueOf(level);
		} else if (level == 3 || level == 7 || level == 11) {
			mMessage = String.valueOf(level);
		}	
		return mMessage;
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
