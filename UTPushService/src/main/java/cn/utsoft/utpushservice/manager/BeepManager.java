/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.utsoft.utpushservice.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.Closeable;
import java.io.IOException;

import cn.utsoft.utpushservice.R;

/**
 * Create By 李波 on 2017/1/10.
 * Function: 系统提示音和震动
 * Desc:     摘取自ZXing
 */
public final class BeepManager implements MediaPlayer.OnErrorListener, Closeable {

    private static final String TAG = BeepManager.class.getSimpleName();
    private static BeepManager instance;

    private static final float BEEP_VOLUME = 0.10f;
    private static final long VIBRATE_DURATION = 200L;

    public static final String KEY_PLAY_BEEP = "preferences_play_beep";
    public static final String KEY_VIBRATE = "preferences_vibrate";

    private final Context mContext;

    private MediaPlayer mediaPlayer;

    private boolean playBeep;
    private boolean vibrate;

    private BeepManager(Context context) {
        this.mContext = context.getApplicationContext();
        this.mediaPlayer = null;
    }

    public static BeepManager getInstance(Context context) {
        BeepManager manager = instance;
        if (manager == null) {
            synchronized (BeepManager.class) {
                manager = instance;
                if (manager == null) {
                    manager = new BeepManager(context);
                }
                instance = manager;
            }
        }
        return instance;
    }

    /**
     * 设置是否可以发声
     *
     * @param context
     * @param sound
     */
    public static void setMsgSound(Context context, boolean sound) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(KEY_PLAY_BEEP, sound);
        edit.commit();
    }

    /**
     * 设置是否可以震动
     *
     * @param context
     * @param vibrate
     */
    public static void setVibrate(Context context, boolean vibrate) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(KEY_VIBRATE, vibrate);
        edit.commit();
    }

    /**
     * 刷新数据
     *
     * @param activity
     * @param res
     */
    private synchronized void updatePrefs(Activity activity, int res) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        playBeep = shouldBeep(prefs, mContext);
        vibrate = prefs.getBoolean(KEY_VIBRATE, false);
        if (playBeep) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it too loud,
            // so we now play on the music stream.
            if (mediaPlayer == null) {
                if (activity != null) {
                    activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
                }
                mediaPlayer = buildMediaPlayer(mContext, res);
            } else {
                if (res != -1) {
                    AssetFileDescriptor file = mContext.getResources().openRawResourceFd(res);
                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(file.getFileDescriptor(),
                                file.getStartOffset(),
                                file.getLength());
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 执行操作
     */
    public void playBeepSoundAndVibrate() {
        this.playBeepSoundAndVibrate(null, -1);
    }
            
    /**
     * 执行操作
     */
    public void playBeepSoundAndVibrate(int soundRes) {
        this.playBeepSoundAndVibrate(null, soundRes);
    }

    /**
     * 执行操作
     */
    public synchronized void playBeepSoundAndVibrate(@Nullable Activity activity, int soundRes) {

        updatePrefs(activity, soundRes);

        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * 检查当前的 铃音模式，或者成为 情景模式。
     * 说明：getRingerMode() ——返回当前的铃声模式。如RINGER_MODE_NORMAL（普通）、RINGER_MODE_SILENT（静音）、RINGER_MODE_VIBRATE（震动）
     * 如果当前是铃音模式，则继续准备下面的 蜂鸣提示音操作，如果是静音或者震动模式。就不要继续了。因为用户选择了无声的模式，我们就也不要出声了。
     *
     * @param prefs
     * @param context
     * @return
     */
    private static boolean shouldBeep(SharedPreferences prefs, Context context) {
        boolean shouldPlayBeep = prefs.getBoolean(KEY_PLAY_BEEP, true);
        if (shouldPlayBeep) {
            // See if sound settings overrides this
            AudioManager audioService = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
                shouldPlayBeep = false;
            }
        }
        return shouldPlayBeep;
    }

    /**
     * 构建MediaPlayer播放器, 用于播放声音
     *
     * @param activity
     * @param res
     * @return
     */
    private MediaPlayer buildMediaPlayer(Context activity, int res) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor file = null;
            if (res != -1) {
                file = activity.getResources().openRawResourceFd(res);
            } else {
                file = activity.getResources().openRawResourceFd(R.raw.avchat_ring);
            }
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            } finally {
                file.close();
            }
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(false);
            mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            mediaPlayer.release();
            return null;
        }
    }

    @Override
    public synchronized boolean onError(MediaPlayer mp, int what, int extra) {
        if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
//            activity.finish();
        } else {
            close();
            updatePrefs(null, R.raw.msg);
        }
        return true;
    }

    @Override
    public synchronized void close() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
