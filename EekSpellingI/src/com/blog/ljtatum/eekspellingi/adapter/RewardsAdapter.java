package com.blog.ljtatum.eekspellingi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.logger.Logger;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;
import com.blog.ljtatum.eekspellingi.util.Utils;

public class RewardsAdapter extends BaseAdapter {
	private final static String TAG = RewardsAdapter.class.getSimpleName();
	
	private Context mContext;
	private int mDimens;
	private int[] mArryImg;
	private SharedPref sharedPref;

	public RewardsAdapter(Context context, int[] arryImg, int dimens) {
		// TODO Auto-generated constructor stub
		mContext = context;
		mArryImg = arryImg;
		mDimens = dimens;
		sharedPref = new SharedPref(mContext, Constants.PREF_FILE_NAME);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArryImg.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		
		if (Utils.checkIfNull(convertView)) {
			LayoutInflater inflater = (LayoutInflater) mContext
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_reward, parent, false);
			convertView.setLayoutParams(new GridView.LayoutParams(mDimens, mDimens));
			holder = new ViewHolder();
			
			holder.iv = (ImageView) convertView.findViewById(R.id.iv);
			holder.tv = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv.setImageResource(mArryImg[position]);
		
		// display reward quantity
		int rewardCount = 0;
		String strPrefName = Constants.REWARDS.concat("_" + position);
		rewardCount = sharedPref.getIntPref(strPrefName, 0);
		Logger.i(TAG, "prefName: " + strPrefName + " // count: " + rewardCount);
		holder.tv.setText(String.valueOf(rewardCount));
		
		return convertView;
	}
	
	private static class ViewHolder {
		private ImageView iv;
		private TextView tv;
	}
	

}
