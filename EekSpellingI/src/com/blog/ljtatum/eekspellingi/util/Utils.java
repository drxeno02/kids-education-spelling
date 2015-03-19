package com.blog.ljtatum.eekspellingi.util;

import java.util.Random;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.anim.ShimmerTextView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


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
	 * Method can be used to check null value for any object
	 * 
	 * @param objectToCheck
	 * @return boolean
	 */
	public static <T> boolean checkIfNull(T objectToCheck) {
		return objectToCheck == null ? true : false;
	}

}
