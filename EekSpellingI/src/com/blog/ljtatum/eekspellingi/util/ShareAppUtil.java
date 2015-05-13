package com.blog.ljtatum.eekspellingi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Parcelable;
import android.text.TextUtils;

import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.logger.Logger;

public class ShareAppUtil {
	private static final String TAG = ShareAppUtil.class.getSimpleName();
	
	private Context mContext;
	
	/**
	 * Launches share intent, e.g. facebook, twitter, email
	 * 
	 * @return
	 */
	public Intent shareIntent(final Context context) {
		mContext = context;
		List<Intent> targetedShareIntents = new ArrayList<Intent>();
		Intent shareIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
		shareIntent.setType("text/plain");

		PackageManager pm = mContext.getPackageManager();
		List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
		for (final ResolveInfo app : activityList) {
			String packageName = app.activityInfo.packageName;
			Intent targetedShareIntent = new Intent(
					android.content.Intent.ACTION_SEND_MULTIPLE);
			targetedShareIntent.setType("text/plain");
			targetedShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Eek! Spelling I");

			if (TextUtils.equals(packageName, "com.google.android.gm")) {
				targetedShareIntent.putExtra(
						android.content.Intent.EXTRA_EMAIL,
						new String[] { "ljtatum@hotmail.com", "pathofjordan@gmail.com" });
				targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						Constants.SHARE_MESSAGE_EMAIL);
			} else if (TextUtils.equals(packageName, "com.android.email")) {
				targetedShareIntent.putExtra(
						android.content.Intent.EXTRA_EMAIL,
						new String[] { "ljtatum@hotmail.com", "pathofjordan@gmail.com" });
				targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						Constants.SHARE_MESSAGE_EMAIL);
			} else if (TextUtils.equals(packageName, "com.android.mms")) {
				targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						Constants.SHARE_MESSAGE);
			}

			targetedShareIntent.setPackage(packageName);
			targetedShareIntents.add(targetedShareIntent);
		}

		// facebook intent
		Intent facebookIntent = getShareIntent("facebook", "Eek! Spellling I",
				Constants.SHARE_MESSAGE);
		if (facebookIntent != null) {
			Logger.i(TAG, "fb: " + facebookIntent);
			targetedShareIntents.add(facebookIntent);
		}

		// twitter intent
		Intent twitterIntent = getShareIntent("twitter", "Eek! Spelling I",
				Constants.SHARE_MESSAGE);
		if (twitterIntent != null) {
			Logger.i(TAG, "twit: " + twitterIntent);
			targetedShareIntents.add(twitterIntent);
		}
			
		// check for empty share intent
		if (!targetedShareIntents.isEmpty()) {
			Intent chooserIntent = Intent.createChooser(
					targetedShareIntents.remove(0), "Share via");
			chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
					targetedShareIntents.toArray(new Parcelable[] {}));
			mContext.startActivity(chooserIntent);
		}
	
		return shareIntent;
	}

	/**
	 * Returns lists of sharable intents, e.g. facebook, twitter, email
	 * 
	 * @return
	 */
	private Intent getShareIntent(String type, String subject, String text) {
		boolean found = false;
		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.setType("text/plain");

		// gets the list of intents that can be loaded.
		List<ResolveInfo> resInfo = mContext.getPackageManager()
				.queryIntentActivities(share, 0);
		Logger.printOnConsole("resinfo: " + resInfo);
		if (!resInfo.isEmpty()) {
			for (ResolveInfo info : resInfo) {
				if (info.activityInfo.packageName.toLowerCase(Locale.US).contains(type)
						|| info.activityInfo.name.toLowerCase(Locale.US).contains(type)) {
					share.putExtra(Intent.EXTRA_SUBJECT, subject);
					share.putExtra(Intent.EXTRA_TEXT, text);
					share.setPackage(info.activityInfo.packageName);
					found = true;
					break;
				}
			}
			if (!found)
				return null;
			return share;
		}
		return null;
	}
	
}
