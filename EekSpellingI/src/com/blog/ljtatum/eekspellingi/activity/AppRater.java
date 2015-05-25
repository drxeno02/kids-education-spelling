package com.blog.ljtatum.eekspellingi.activity;
import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.constants.Constants;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppRater {

	private final static int DAYS_UNTIL_PROMPT = 5;
	private final static int LAUNCHES_UNTIL_PROMPT = 10;

	public static void rateThisApp(Context mContext) {
		SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
		if (prefs.getBoolean("dontshowagain", false)) {
			return;
		}

		SharedPreferences.Editor editor = prefs.edit();

		// Increment launch counter
		long launchCount = prefs.getLong("launch_count", 0) + 1;
		editor.putLong("launch_count", launchCount);

		// Get date of first launch
		Long dateFirstLaunch = prefs.getLong("date_firstlaunch", 0);
		if (dateFirstLaunch == 0) {
			dateFirstLaunch = System.currentTimeMillis();
			editor.putLong("date_firstlaunch", dateFirstLaunch);
		}

		// Wait at least n days before opening
		if (launchCount >= LAUNCHES_UNTIL_PROMPT) {
			if (System.currentTimeMillis() >= dateFirstLaunch
					+ (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
				showRateDialog(mContext, editor);
			}
		}
		editor.commit();
	} // end app_launched()

	private static void showRateDialog(final Context mContext,
			final SharedPreferences.Editor editor) {
		final Dialog dialog = new Dialog(mContext);
		dialog.setTitle("Rate " + mContext.getResources().getString(R.string.app_name));

		LinearLayout ll = new LinearLayout(mContext);
		ll.setOrientation(LinearLayout.VERTICAL);

		TextView tv = new TextView(mContext);
		tv.setText("If you enjoy using "
				+ mContext.getResources().getString(R.string.app_name)
				+ ", "
				+ "please take a moment to rate it. Thanks for your support!\n\n");
		Color.parseColor("#FFFFFF");
		tv.setBackgroundColor(Color.parseColor("#FFFFFF"));
		tv.setWidth(300);
		tv.setPadding(6, 0, 6, 12);
		ll.addView(tv);

		Button b1 = new Button(mContext);
		b1.setText("Rate " + mContext.getResources().getString(R.string.app_name));
		b1.setBackgroundResource(R.drawable.custom_button_a);
		b1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri
						.parse("market://details?id=" + Constants.PKG_NAME_SPELLING)));
				dialog.dismiss();
			}
		});
		ll.addView(b1);

		Button b2 = new Button(mContext);
		b2.setText("Remind me later");
		b2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		ll.addView(b2);

		Button b3 = new Button(mContext);
		b3.setText("No, thanks");
		b3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (editor != null) {
					editor.putBoolean("dontshowagain", true);
					editor.commit();
				}
				dialog.dismiss();
			}
		});
		ll.addView(b3);

		dialog.setContentView(ll);
		dialog.show();
	}
}
