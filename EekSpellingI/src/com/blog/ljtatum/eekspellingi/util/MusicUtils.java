package com.blog.ljtatum.eekspellingi.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.blog.ljtatum.eekspellingi.R;
import com.blog.ljtatum.eekspellingi.logger.Logger;

public class MusicUtils {

	private static String TAG = MusicUtils.class.getSimpleName();

	private static Context mContext;
	public static MediaPlayer gameBgm;
	private static int mMusicId;
	private static int mBgmKeyStore = -1;
	private static int currentMusic = -1;
	private static int previousMusic = -1;

	/**
	 * Method is used to initialize variables and start bgms
	 * @param context
	 * @param bgmKeyStore
	 */
	public static void start(Context context, int bgmKeyStore) {
		mContext = context;
		mBgmKeyStore = bgmKeyStore;
		mMusicId = convertMusicId(mBgmKeyStore);
		Logger.v(TAG, "musicId: " + mMusicId);
		start(mContext, mBgmKeyStore, true);
	}

	/**
	 * Method is used to track multiple bgms
	 * @param bgmKeyStore
	 * @return
	 */
	private static int convertMusicId(int bgmKeyStore) {
		if (bgmKeyStore == 1) {
			return R.raw.bgm_main;
		} else if (bgmKeyStore == 2) {
			return R.raw.bgm_rewards;
		} else if (bgmKeyStore == 3) {
			return R.raw.bgm_level_easy;
		} else if (bgmKeyStore == 4) {
			return R.raw.bgm_level_medium;
		} else if (bgmKeyStore == 5) {	
			return R.raw.bgm_level_hard;
		}
		return 0;
	}

	/**
	 * Method is used to start bgms
	 * @param context
	 * @param music
	 * @param active
	 */
	private static void start(Context context, int music, boolean active) {

		if (active == true && currentMusic > -1) {
			// music is active and being handled
			return;
		}

		if (music == -1) {
			// music is suspended (onPause); prepared for new Activity
			music = previousMusic;
		}

		if (music == currentMusic) {
			// music is active and being handled
			return;
		}

		if (currentMusic != -1) {
			previousMusic = currentMusic;
			// playing some other music, pause it and change
			pause();
		}

		currentMusic = music;

		if (gameBgm != null) {
			if (!gameBgm.isPlaying()) {
				Logger.v(TAG, "Current music resumed(" + currentMusic + ")");
				gameBgm.start();
			}
		} else {
			Logger.v(TAG, "Initial start of music(" + currentMusic + ")");
			if (mBgmKeyStore == 1) {
 				gameBgm = MediaPlayer.create(context, R.raw.bgm_main);
 			} else if (mBgmKeyStore == 2) {
 				gameBgm = MediaPlayer.create(context, R.raw.bgm_rewards);
 			} else if (mBgmKeyStore == 3) {	
 				gameBgm = MediaPlayer.create(context, R.raw.bgm_level_easy);
 			} else if (mBgmKeyStore == 4) {
 				gameBgm = MediaPlayer.create(context, R.raw.bgm_level_medium);
 			} else if (mBgmKeyStore == 5) {	
 				gameBgm = MediaPlayer.create(context, R.raw.bgm_level_hard);
 			}
			
			try {
				gameBgm.setLooping(true);
				gameBgm.setVolume(.4f, .4f);
				// gameBgm.seekTo(0);
				gameBgm.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Method is used to pause bgms
	 */
	public static void pause() {
		if (gameBgm != null) {
			try {
				if (gameBgm.isPlaying()) {
					gameBgm.pause();
				}
			} catch (IllegalStateException ise) {
				ise.printStackTrace();
				stop();
			}
			
		}
		
		// previousMusic should always be something valid
    	if (currentMusic != -1) {	
    		previousMusic = currentMusic;
			currentMusic = -1;	
			Logger.v(TAG, "Paused music is prepared to play again (if necessary)");
    	}
	}

	/**
	 * Method is used to stop bgms
	 */
	public static void stop() {
		Logger.v(TAG, "Stopping media player");
		if (gameBgm != null) {
			try {
				if (gameBgm.isPlaying()) {
					gameBgm.stop();
					gameBgm = null;
				}
			} catch (IllegalStateException ise) {
				ise.printStackTrace();
				gameBgm = null;
			}		
		}

		mBgmKeyStore = -1;
		currentMusic = -1;
		previousMusic = -1;
	}
	
	/**
	 * Method is used to release bgms
	 */
	public static void release() {
		Logger.v(TAG, "Releasing media player");
		try {
			if (gameBgm != null) {
				if (gameBgm.isPlaying()) {
					gameBgm.stop();
				}
				gameBgm.release();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (currentMusic != -1) {
			previousMusic = currentMusic;
		}
		currentMusic = -1;
		Logger.v(TAG, "Media player has been destroyed");
	}
	
	/**
	 * Method is used for playing sound bytes
	 * @param context
	 * @param sound
	 */
	public static void playSound(int sound) {
		MediaPlayer mp = MediaPlayer.create(mContext, sound);
		mp.setVolume((float) .5, (float) .5);
		mp.start();
		mp.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}
}
