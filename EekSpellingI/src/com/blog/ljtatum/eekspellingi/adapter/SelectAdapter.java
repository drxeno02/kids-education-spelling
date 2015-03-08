package com.blog.ljtatum.eekspellingi.adapter;

import java.util.List;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.model.SelectModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelectAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<SelectModel> mArrySelect;
	
	public SelectAdapter(Context context, List<SelectModel> arrySelect) {
		this.mContext = context;
		this.mArrySelect = arrySelect;
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
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.item_select, null);		
			holder = new ViewHolder();
			
			holder.rlLeft = (RelativeLayout) view.findViewById(R.id.rl_left);
			holder.rlRight = (RelativeLayout) view.findViewById(R.id.rl_right);
			holder.tvTitle = (TextView) view.findViewById(R.id.tv_title);	
			holder.tvLeftBubble = (TextView) view.findViewById(R.id.tv_left_bubble);
			holder.tvRightBubble = (TextView) view.findViewById(R.id.tv_right_bubble);
			holder.tvTitle.setText(mArrySelect.get(position).getTitle());
			
			// set visibility gone on views
			if (position % 2 == 0) {
				holder.rlLeft.setVisibility(View.GONE);
				holder.tvRightBubble.setText(mArrySelect.get(position).getMessage());
			} else {
				holder.rlRight.setVisibility(View.GONE);			
				holder.tvLeftBubble.setText(mArrySelect.get(position).getMessage());
			}
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		return view;
	}
	
	static class ViewHolder {
		private TextView tvLeftBubble, tvRightBubble, tvTitle;
		private RelativeLayout rlLeft, rlRight;
	}
	

}
