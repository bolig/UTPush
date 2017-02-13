package cn.utsoft.utpush.simple;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

import cn.utsoft.utpushservice.manager.UTPushManager;

/**
 * Create by houyong on 2017/2/10
 * Function：
 * Desc：
 */
public class SecendSettingActivity extends AppCompatActivity implements View.OnClickListener
{
    final String SETTING_NOTICE = "setting_notification";

    final String DEFAULT_RES_PATH_FREFIX = "android.resource://";
    final String DEFAULT_RES_SOUND_TYPE = "raw";
    final String DEFAULT_RES_ICON_TYPE = "mipmap";

    final String DEFAULT_NOTICE_SOUND = "alicloud_notification_sound";
    final String ASSIGN_NOTIFCE_SOUND = "alicloud_notification_sound_assign";
    final String DEFAULT_NOTICE_LARGE_ICON = "alicloud_notification_largeicon";
    final String ASSIGN_NOTIFCE_LARGE_ICON = "alicloud_notification_largeicon_assign";

    final String DEFAULT_NOTICE_SMALL_ICON = "alicloud_notification_smallicon";
    final String ASSIGN_NOTICE_SMALL_ICON = "bat";
    String PackageName;

    Button btn_defaultMusic;
    Button diyMusic;
    Button defaultIcon;
    Button diyIcon;
    Button defaultStatuIcon;
    Button diyStatuIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_secend);
        PackageName = getPackageName();
        btn_defaultMusic = (Button) this.findViewById(R.id.default_sound);
        diyMusic = (Button) this.findViewById(R.id.assign_sound);
        defaultIcon = (Button) this.findViewById(R.id.default_largeicon);
        diyIcon = (Button) this.findViewById(R.id.assign_largeicon);
        defaultStatuIcon = (Button) this.findViewById(R.id.default_smallicon);
        diyStatuIcon = (Button) this.findViewById(R.id.assign_smallicon);

        btn_defaultMusic.setOnClickListener(this);
        diyMusic.setOnClickListener(this);
        defaultIcon.setOnClickListener(this);
        diyIcon.setOnClickListener(this);
        defaultStatuIcon.setOnClickListener(this);
        diyStatuIcon.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.default_sound:
                int defaultSoundId = getResources().getIdentifier(DEFAULT_NOTICE_SOUND, DEFAULT_RES_SOUND_TYPE, PackageName);
                if (defaultSoundId != 0) {
                    String defaultSoundPath = DEFAULT_RES_PATH_FREFIX + getPackageName() + "/" + defaultSoundId;
//                    PushServiceFactory.getCloudPushService().setNotificationSoundFilePath(defaultSoundPath);
                    UTPushManager.getPushSetting().setNotificationSoundFilePath(defaultSoundPath);
                    Log.i(SETTING_NOTICE, "Set notification sound res id to R." + DEFAULT_RES_SOUND_TYPE + "." + DEFAULT_NOTICE_SOUND);
                } else {
                    Log.e(SETTING_NOTICE, "Set notification sound path error, R."
                            + DEFAULT_RES_SOUND_TYPE + "." + DEFAULT_NOTICE_SOUND + " not found.");
                }
                break;
            case R.id.assign_sound:
                /**
                 * 此处指定资源Id为R.raw.alicloud_notification_sound_assgin的声音文件
                 */
                int assignSoundId = getResources().getIdentifier(ASSIGN_NOTIFCE_SOUND, DEFAULT_RES_SOUND_TYPE, PackageName);
                if (assignSoundId != 0) {
                    String defaultSoundPath = DEFAULT_RES_PATH_FREFIX + getPackageName() + "/" + assignSoundId;
//                    PushServiceFactory.getCloudPushService().setNotificationSoundFilePath(defaultSoundPath);
                        UTPushManager.getPushSetting().setNotificationSoundFilePath(defaultSoundPath);
                    Log.i(SETTING_NOTICE, "Set notification sound res id to R." + DEFAULT_RES_SOUND_TYPE + "." + ASSIGN_NOTIFCE_SOUND);
                } else {
                    Log.e(SETTING_NOTICE, "Set notification sound path error, R."
                            + DEFAULT_RES_SOUND_TYPE + "." + ASSIGN_NOTIFCE_SOUND + " not found.");
                }
                break;
            case R.id.default_largeicon:
                /**
                 * 如果用户未调用过setNotificationLargeIcon()接口，默认取R.raw.alicloud_notification_largeicon图标资源
                 * 则无需使用以下方式进行设置
                 */
                int defaultLargeIconId = getResources().getIdentifier(DEFAULT_NOTICE_LARGE_ICON, DEFAULT_RES_ICON_TYPE, PackageName);
                if (defaultLargeIconId != 0) {
                    Drawable drawable = getApplicationContext().getResources().getDrawable(defaultLargeIconId);
                    if (drawable != null) {
                        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
//                        PushServiceFactory.getCloudPushService().setNotificationLargeIcon(bitmap);
                        UTPushManager.getPushSetting().setNotificationLargeIcon(bitmap);
                        Log.i(SETTING_NOTICE, "Set notification largeIcon res id to R." + DEFAULT_RES_ICON_TYPE + "." + DEFAULT_NOTICE_LARGE_ICON);
                    }
                } else {
                    Log.e(SETTING_NOTICE, "Set largeIcon bitmap error, R."
                            + DEFAULT_RES_ICON_TYPE + "." + DEFAULT_NOTICE_LARGE_ICON + " not found.");
                }
                break;
            case R.id.assign_largeicon:
                /**
                 * 此处指定资源Id为R.raw.alicloud_notification_largeicon_assgin的图标资源
                 */
                int assignLargeIconId = getResources().getIdentifier(ASSIGN_NOTIFCE_LARGE_ICON, DEFAULT_RES_ICON_TYPE, PackageName);
                if (assignLargeIconId != 0) {
                    Drawable drawable = getApplicationContext().getResources().getDrawable(assignLargeIconId);
                    if (drawable != null) {
                        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
//                        PushServiceFactory.getCloudPushService().setNotificationLargeIcon(bitmap);
                        UTPushManager.getPushSetting().setNotificationLargeIcon(bitmap);
                        Log.i(SETTING_NOTICE, "Set notification largeIcon res id to R." + DEFAULT_RES_ICON_TYPE + "." + ASSIGN_NOTIFCE_LARGE_ICON);
                    }
                } else {
                    Log.e(SETTING_NOTICE, "Set largeIcon bitmap error, R."
                            + DEFAULT_RES_ICON_TYPE + "." + ASSIGN_NOTIFCE_LARGE_ICON + " not found.");
                }
                break;
            case R.id.default_smallicon:
                /**
                 * 如果用户未调用过setNotificationSmallIcon()接口，默认取R.raw.alicloud_notification_smallicon图标资源
                 * 则无需使用以下方式进行设置
                 */
                int defaultSmallIconId = getResources().getIdentifier(DEFAULT_NOTICE_LARGE_ICON, DEFAULT_RES_ICON_TYPE, PackageName);
                if (defaultSmallIconId != 0) {
//                    PushServiceFactory.getCloudPushService().setNotificationSmallIcon(defaultSmallIconId);
                   UTPushManager.getPushSetting().setNotificationSmallIcon(defaultSmallIconId);
                    Log.i(SETTING_NOTICE, "Set notification smallIcon res id to R." + DEFAULT_RES_ICON_TYPE + "." + DEFAULT_NOTICE_SMALL_ICON);
                } else {
                    Log.e(SETTING_NOTICE, "Set notification smallIcon error, R." +
                            DEFAULT_RES_ICON_TYPE + "." + DEFAULT_NOTICE_SMALL_ICON + " not found.");
                }
                break;
            case R.id.assign_smallicon:
                /**
                 * 此处指定资源Id为R.raw.alicloud_notification_smallicon_assgin的图标资源
                 */
                int assignSmallIconId = getResources().getIdentifier(ASSIGN_NOTICE_SMALL_ICON, DEFAULT_RES_ICON_TYPE, PackageName);
                if (assignSmallIconId != 0) {
//                    PushServiceFactory.getCloudPushService().setNotificationSmallIcon(assignSmallIconId);
                    UTPushManager.getPushSetting().setNotificationSmallIcon(assignSmallIconId);
                    Log.i(SETTING_NOTICE, "Set notification smallIcon res id to R." + DEFAULT_RES_ICON_TYPE + "." + ASSIGN_NOTICE_SMALL_ICON);
                } else {
                    Log.e(SETTING_NOTICE, "Set notification smallIcon error, R." +
                            DEFAULT_RES_ICON_TYPE + "." + ASSIGN_NOTICE_SMALL_ICON + " not found.");
                }
                break;

        }
    }
}
