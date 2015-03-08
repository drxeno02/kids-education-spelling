package com.blog.ljtatum.eekspellingi.activity;


import java.util.List;
import java.util.Timer;

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
import com.blog.ljtatum.eekspellingi.model.SelectModel;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.MusicUtils;
import com.blog.ljtatum.eekspellingi.util.ShareAppUtil;
import com.blog.ljtatum.eekspellingi.util.Utils;

public class SelectActivity extends BaseActivity {
	private static final String TAG = SelectActivity.class.getSimpleName();
		
	private Context mContext;
	
	private ShareAppUtil shareApp;
	private SharedPref sharedPref;
	private SelectAdapter selectAdapter;
	private ImageView ivBack, ivBanner;
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_activity);	
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

		List<SelectModel> arrySelect = populateSelectModel();
		
		// set up adapter
		selectAdapter = new SelectAdapter(mContext, arrySelect);
		lv.setAdapter(selectAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}		
		});		
	}
	
	private List<SelectModel> populateSelectModel() {
		
		SelectModel model = null;
		for (int i = 0; i < 12; i++) {
			model = new SelectModel();
			model.setTitle(generateTitle(i));
			model.setMessage(generateMessage(i));
			SelectModel.addArrySelect(model);		
		}
		return SelectModel.getArrySelect();
	}
	
	private String generateTitle(int level) {
		String mTitle = "";		
		if (level == 0 || level == 4 || level == 8) {
			mTitle = "Letter Tree";
		} else if (level == 1 || level == 5 || level == 9) {
			mTitle = "Spelling Tiles";
		} else if (level == 2 || level == 6 || level == 10) {
			mTitle = "Word Maze";
		} else if (level == 3 || level == 7 || level == 12) {
			mTitle = "Picture Drop";
		}
		return mTitle;
	}
	
	private String generateMessage(int level) {
		String mMessage = "";	
		if (level == 0 || level == 4 || level == 8) {
			mMessage = String.valueOf(level);
		} else if (level == 1 || level == 5 || level == 9) {
			mMessage = String.valueOf(level);
		} else if (level == 2 || level == 6 || level == 10) {
			mMessage = String.valueOf(level);
		} else if (level == 3 || level == 7 || level == 12) {
			mMessage = String.valueOf(level);
		}	
		return mMessage;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.share:
			shareApp.shareIntent(SelectActivity.this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		startBannerAnim(mContext, ivBanner);
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
