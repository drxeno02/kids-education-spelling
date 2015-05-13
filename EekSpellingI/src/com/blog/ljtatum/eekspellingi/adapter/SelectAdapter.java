package com.blog.ljtatum.eekspellingi.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.model.SelectModel;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.Utils;
import com.blog.ljtatum.eekspellingi.view.RoundedImageView;

public class SelectAdapter extends BaseAdapter {
	private static final String TAG = SelectAdapter.class.getSimpleName();
	
	private Context mContext;
	private ArrayList<SelectModel> mArrySelect;
	private SharedPref sharedPref;
	
	public SelectAdapter(Context context, ArrayList<SelectModel> arrySelect) {
		this.mContext = context;
		this.mArrySelect = arrySelect;
		sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrySelect.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mArrySelect.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View view = convertView;
		ViewHolder holder;
		if (Utils.checkIfNull(view)) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_select, parent, false);		
			holder = new ViewHolder();

			holder.tvLock = (TextView) view.findViewById(R.id.tv_lock);
			holder.rlBkg = (RoundedImageView) view.findViewById(R.id.rl_bkg);	
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		// set visibility on level locks
		if (position > 3) {
			String strPrefNameUnlock = Constants.LV_UNLOCKED.concat("_" + position);
			Logger.i(TAG, "pos: " + position + " // strPrefNameUnlock: " + strPrefNameUnlock);
			if (!sharedPref.getBooleanPref(strPrefNameUnlock, false)) {
				holder.tvLock.setVisibility(View.VISIBLE);
			}
		} else {
			holder.tvLock.setVisibility(View.GONE);
		}
		
		// set background of views
		if (position == 0 || position == 4 || position == 8) {
			holder.rlBkg.setBackground(mContext.getResources().getDrawable(R.drawable.select_letter_tree_block));
		} else if (position == 1 || position == 5 || position == 9) {
			holder.rlBkg.setBackground(mContext.getResources().getDrawable(R.drawable.select_spelling_tile_block));
		} else if (position == 2 || position == 6 || position == 10) {
			holder.rlBkg.setBackground(mContext.getResources().getDrawable(R.drawable.select_word_maze_block));
		} else if (position == 3 || position == 7 || position == 11) {
			holder.rlBkg.setBackground(mContext.getResources().getDrawable(R.drawable.select_picture_block));
		}
		return view;
	}
	
	
	
	private static class ViewHolder {
		private TextView tvLock;
		private RoundedImageView rlBkg;
	}
	

}
