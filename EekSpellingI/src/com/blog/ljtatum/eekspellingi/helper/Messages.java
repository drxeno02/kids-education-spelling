package com.blog.ljtatum.eekspellingi.helper;

import java.util.Random;

import android.content.Context;

import com.blog.ljtatum.eekspellingi.constants.Constants;
import com.blog.ljtatum.eekspellingi.sharedpref.SharedPref;

public class Messages {
	private static String metaStr;

	/**
	 * Method is used for retrieving description text for SelectActivity blurps
	 *
	 * @param context
	 * @return
	 */
	public static String getDesc(Context context, int level) {
		SharedPref sharedPref = new SharedPref(context, Constants.PREF_FILE_NAME);
		String metaDesc = "";
		String strPrefNameCount = Constants.LV_COUNT.concat("_" + level);
		String strPrefNameUnlock = Constants.LV_UNLOCKED.concat("_" + level);
		int lvCount = sharedPref.getIntPref(strPrefNameCount, 0);
		boolean isUnlocked = sharedPref.getBooleanPref(strPrefNameUnlock, false);

		if (!isUnlocked) {
			metaDesc = "Level is currently locked";
		} else {
			if (lvCount <= 10) {
				if (level == 0 || level == 4 || level == 8) {
					metaDesc = "Learn new words with Letter Tree!";
				} else if (level == 1 || level == 5 || level == 9) {
					metaDesc = "Identify words with Spelling Tile!";
				} else if (level == 2 || level == 6 || level == 10) {
					metaDesc = "Lets do a maze!";
				} else if (level == 3 || level == 7 || level == 11) {
					metaDesc = "Lets learn with pictures!";
				}
			} else if (lvCount > 10 && lvCount <= 20) {
				if (level == 0 || level == 4 || level == 8) {
					metaDesc = "You're getting pretty good at this!";
				} else if (level == 1 || level == 5 || level == 9) {
					metaDesc = "Nice job, you have the hang of it!";
				} else if (level == 2 || level == 6 || level == 10) {
					metaDesc = "The more difficult levels have larger mazes!";
				} else if (level == 3 || level == 7 || level == 11) {
					metaDesc = "What other words would you like to learn?";
				}
			} else {
				if (level == 0 || level == 4 || level == 8) {
					metaDesc = "Great job!";
				} else if (level == 1 || level == 5 || level == 9) {
					metaDesc = "Learn any new words lately?";
				} else if (level == 2 || level == 6 || level == 10) {
					metaDesc = "Excellent job!";
				} else if (level == 3 || level == 7 || level == 11) {
					metaDesc = "Awesome work!";
				}
			}
		}
		return metaDesc;
	}

	/**
	 * Method is used to receive positive or negative messaging
	 *
	 * @param msgBool if true will return positive message otherwise negative
	 * @return
	 */
	public static String msgPath(boolean msgBool, boolean lowVerbosity) {
		Random r = new Random();
		int metaNum = r.nextInt(19) + 1;
		return getStr(metaNum, msgBool, lowVerbosity);
	}

	/**
	 * Method returns a randomized positive or negative message
	 *
	 * @param strId message to return
	 * @param msgBool if true will return positive message otherwise negative
	 * @param lowVerbosity if low verbosity is true, will return from a short list of positive or
	 *        negative messages
	 * @return
	 */
	private static String getStr(int strId, boolean msgBool, boolean lowVerbosity) {
		// @false: positive msgPath && @true: negative msgPath

		if (lowVerbosity) {
			if (msgBool) {
				if (strId >= 1 && strId < 10) {
					metaStr = "Correct!";
				} else if (strId >= 10 && strId < 15) {
					metaStr = "Very good!";
				} else {
					metaStr = "Yes!";
				}
			} else {
				if (strId >= 1 && strId < 10) {
					metaStr = "Incorrect";
				} else if (strId >= 10 && strId < 15) {
					metaStr = "No";
				} else {
					metaStr = "That's not it";
				}
			}
		} else {
			if (msgBool) { // positive msgPath
				if (strId == 1) {
					metaStr = "You did it";
				} else if (strId == 2) {
					metaStr = "You are on fire";
				} else if (strId == 3) {
					metaStr = "Great";
				} else if (strId == 4) {
					metaStr = "Great job";
				} else if (strId == 5) {
					metaStr = "Awesome";
				} else if (strId == 6) {
					metaStr = "Awesome job";
				} else if (strId == 7) {
					metaStr = "Way to go";
				} else if (strId == 8) {
					metaStr = "Yes";
				} else if (strId == 9) {
					metaStr = "You're good";
				} else if (strId == 10) {
					metaStr = "Nice";
				} else if (strId == 11) {
					metaStr = "Excellent";
				} else if (strId == 12) {
					metaStr = "That's the way";
				} else if (strId == 13) {
					metaStr = "Perfect";
				} else if (strId == 14) {
					metaStr = "Great, keep going";
				} else if (strId == 15) {
					metaStr = "You're really good";
				} else if (strId == 16) {
					metaStr = "Good thinking";
				} else if (strId == 17) {
					metaStr = "Keep it up";
				} else if (strId == 18) {
					metaStr = "Very smart of you";
				} else if (strId == 19) {
					metaStr = "You are very keen";
				}
			} else { // negative msgPath
				if (strId == 1) {
					metaStr = "Keep practicing";
				} else if (strId == 2) {
					metaStr = "Nope";
				} else if (strId == 3) {
					metaStr = "That's not it";
				} else if (strId == 4) {
					metaStr = "O no. Try again";
				} else if (strId == 5) {
					metaStr = "Try again";
				} else if (strId == 6) {
					metaStr = "Not quite";
				} else if (strId == 7) {
					metaStr = "You'll get it";
				} else if (strId == 8) {
					metaStr = "Keep trying";
				} else if (strId == 9) {
					metaStr = "No, not it";
				} else if (strId == 10) {
					metaStr = "Don't give up";
				} else if (strId == 11) {
					metaStr = "You can do it";
				} else if (strId == 12) {
					metaStr = "Very close";
				} else if (strId == 13) {
					metaStr = "No";
				} else if (strId == 14) {
					metaStr = "So close";
				} else if (strId == 15) {
					metaStr = "Nope, not it";
				} else if (strId == 16) {
					metaStr = "Guess again";
				} else if (strId == 17) {
					metaStr = "Slow down and focus";
				} else if (strId == 18) {
					metaStr = "Don't give up";
				} else if (strId == 19) {
					metaStr = "That's not correct";
				}
			}
		}

		return metaStr;
	}

}