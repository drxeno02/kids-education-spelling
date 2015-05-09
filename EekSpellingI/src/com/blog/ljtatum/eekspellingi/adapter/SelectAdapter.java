package com.blog.ljtatum.eekspellingi.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.model.SelectModel;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.Utils;

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
			
			holder.rlLeft = (RelativeLayout) view.findViewById(R.id.rl_left);
			holder.rlRight = (RelativeLayout) view.findViewById(R.id.rl_right);
			holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);	
			holder.tvLeftBubble = (TextView) view.findViewById(R.id.tv_left_bubble);
			holder.tvRightBubble = (TextView) view.findViewById(R.id.tv_right_bubble);
			holder.ivLock = (ImageView) view.findViewById(R.id.iv_lock);
		
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.tvTitle.setText(mArrySelect.get(position).getTitle());
		
		// set visibility gone on views
		if (position % 2 == 0) {
			holder.rlLeft.setVisibility(View.GONE);
			if (holder.rlRight.getVisibility() == View.GONE) {
				holder.rlRight.setVisibility(View.VISIBLE);
			}
			holder.tvRightBubble.setText(mArrySelect.get(position).getMessage());
		} else {
			holder.rlRight.setVisibility(View.GONE);
			if (holder.rlLeft.getVisibility() == View.GONE) {
				holder.rlLeft.setVisibility(View.VISIBLE);
			}
			holder.tvLeftBubble.setText(mArrySelect.get(position).getMessage());
		}
		
		// set visibility on level locks
		if (position > 3) {
			String strPrefNameUnlock = Constants.LV_UNLOCKED.concat("_" + position);
			Logger.i(TAG, "pos: " + position + " // strPrefNameUnlock: " + strPrefNameUnlock);
			if (!sharedPref.getBooleanPref(strPrefNameUnlock, false)) {
				holder.ivLock.setVisibility(View.VISIBLE);
			}
		} else {
			holder.ivLock.setVisibility(View.GONE);
		}

		return view;
	}
	
	private static class ViewHolder {
		private ImageView ivLock;
		private TextView tvLeftBubble, tvRightBubble, tvTitle;
		private RelativeLayout rlLeft, rlRight;
	}
	

}
