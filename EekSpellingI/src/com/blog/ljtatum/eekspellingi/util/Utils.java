package com.blog.ljtatum.eekspellingi.util;

import java.lang.reflect.Array;
import java.util.Random;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import com.blog.ljtatum.eekspellingi.anim.ShimmerTextView;

import com.blog.ljtatum.eekspellingi.R;


@SuppressWarnings("unused")
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
	 * Method is used to remove duplicate characters
	 * @param origStr
	 * @return
	 */
	public static String removeDuplicates(final String origStr) {
	    final StringBuilder result = new StringBuilder();
	    for (int i = 0; i < origStr.length(); i++) {
	        String currentChar = origStr.substring(i, i + 1);
	        if (result.indexOf(currentChar) < 0) {
	        	 result.append(currentChar);
	        }	           
	    }
	    return String.valueOf(result);
	}
	
	/**
	 * Method is used to clear TextViews
	 * @param params
	 */
	public static void clearText(TextView...params) {
		for (TextView tv : params) {
			tv.setText("");
		}
	}
	
	/**
	 * Method is used to set color of TextViews
	 * @param colorId
	 * @param params
	 */
	public static void setTextViewColor(int colorId, TextView...params) {
		for (TextView tv : params) {
			tv.setTextColor(colorId);
		}
	}
	
	/**
	 * Method is used to set visibility of Views
	 * @param isVisible sets view visible otherwise gone
	 * @param params
	 */
	public static void setViewVisibility(boolean isVisible, View...params) {	
		if (isVisible) {
			for (View v : params) {
				v.setVisibility(View.VISIBLE);
			}
		} else {
			for (View v : params) {
				v.setVisibility(View.GONE);
			}
		}		
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
	 * Method is used to concatenate two arrays using generics and reflection
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T> T[] concatenate (T[] a, T[] b) {
	    int aLen = a.length;
	    int bLen = b.length;
	 
	    @SuppressWarnings("unchecked") 
	    T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen+bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);
	 
	    return c;
	} 
	
	/**
	 * Method is used to concatenate Three arrays using generics and reflection
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static <T> T[] concatenate (T[] a, T[] b, T[] c) {
	    int aLen = a.length;
	    int bLen = b.length;
	    int cLen = c.length;
	 
	    @SuppressWarnings("unchecked") 
	    T[] d = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen+bLen+cLen);
	    System.arraycopy(a, 0, d, 0, aLen);
	    System.arraycopy(b, 0, d, aLen, bLen);
	    System.arraycopy(c, 0, d, aLen+bLen, cLen);
	 
	    return d;
	} 
	
	/**
	 * Method is used to concatenate Three arrays using generics and reflection
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @return
	 */
	public static <T> T[] concatenate (T[] a, T[] b, T[] c, T[] d) {
	    int aLen = a.length;
	    int bLen = b.length;
	    int cLen = c.length;
	    int dLen = d.length;
	 
	    @SuppressWarnings("unchecked") 
	    T[] e = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen+bLen+cLen+dLen);
	    System.arraycopy(a, 0, e, 0, aLen);
	    System.arraycopy(b, 0, e, aLen, bLen);
	    System.arraycopy(c, 0, e, aLen+bLen, cLen);
	    System.arraycopy(c, 0, e, aLen+bLen+cLen, dLen);
	 
	    return e;
	} 	
}
