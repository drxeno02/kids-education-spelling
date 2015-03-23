package com.blog.ljtatum.eekspellingi.util;

import java.util.Random;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.blog.ljtatum.eekspellingi.R;


public class Utils {

	private static int bannerId;
	
	/**
	 * Method is used for retrieving in-house ad banner resource id
	 * @param context
	 * @return resource id
	 */
	public static Drawable getBanner(Context context) {
		int rand = 0;
		Random r = new Random();
		rand = r.nextInt(9) + 1;
		if (rand <= 2) {
			bannerId = 1;
			return context.getResources().getDrawable(R.drawable.ph_banner_a);
		} else if (rand > 2 && rand < 5) {
			bannerId = 2;
			return context.getResources().getDrawable(R.drawable.ph_banner_eek);
		}
		bannerId = 3;
		return context.getResources().getDrawable(R.drawable.ph_banner_b);
		
	}
	
	/**
	 * Method is used to retrieve banner id
	 * @return
	 */
	public static int getBannerId() {
		return bannerId;
	}
		
	/**
	 * Method is used to check null value for any object
	 * 
	 * @param objectToCheck
	 * @return boolean
	 */
	public static <T> boolean checkIfNull(T objectToCheck) {
		return objectToCheck == null ? true : false;
	}

	/**
	 * Method is used to remove duplicate characters
	 * @param origStr
	 * @return
	 */
	public static String removeDuplicates(String origStr) {		
	    String result = "";
	    for (int i = 0; i < origStr.length(); i++) {
	        if(!result.contains(String.valueOf(origStr.charAt(i)))) {
	            result += String.valueOf(origStr.charAt(i));
	        } 
	    } 
	    return result;
	} 
}
