package com.blog.ljtatum.eekspellingi.helper;

import java.util.Random;

import android.content.Context;

import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;

public class Messages {
	
	private String[] metaDesc;
	
	/**
	 * Method is used for retrieving description text for SelectActivity blurps
	 * @param context
	 * @return
	 */
	public String[] getDesc(Context context) {
		
		int level = 0;
		Random r = new Random();
		SharedPref sharedPref = new SharedPref(context, Constants.PREF_FILE_NAME);	
		
		for (int i = 0; i < 12; i++) {
			String strCount = Constants.LV_COUNT.concat("_" + i);
			int lvCount = sharedPref.getIntPref(strCount, 0);
			
			if (lvCount <= 10) {
				
			} else if (lvCount > 10 && lvCount <= 20) {
				
			} else {
				
			}
			
		}
		
		
		return metaDesc;
	}

}