package cn.utsoft.utpushservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.notification.BasicCustomPushNotification;
import com.alibaba.sdk.android.push.notification.CustomNotificationBuilder;

import cn.utsoft.utpushservice.manager.BeepManager;

/**
 * Created by LiLi on 2017/2/8.
 * Func:推送设置类
 * Desc:
 */

public class UTPushSetting implements IPushSetting {
    public static final String SETTINGS_ACT = "settings-act";

    private static UTPushSetting pushSetting = new UTPushSetting();
    BasicCustomPushNotification notification = new BasicCustomPushNotification();

    private final int TYPE_SILENT = 0;
    private final int TYPE_SOUND = 1;
    private final int TYPE_VIBRATE = 2;
    private final int TYPE_SOUND_VIBRATE = 3;
    private int notificationType;

    public static UTPushSetting getPushSetting() {
        return pushSetting;
    }

//    @Override
//    public void register(Context var1, ICommomCallback var2) {
//
//    }
//
//    @Override
//    public void register(Context var1, String var2, String var3, ICommomCallback var4) {
//
//    }

    @Override
    public void onAppStart() {

    }

    @Override
    public void bindAccount(final String acountStr, final ICommomCallback callback) {
        if (!TextUtils.isEmpty(acountStr)) {
            PushServiceFactory.getCloudPushService().bindAccount(acountStr, new CommonCallback() {
                @Override
                public void onSuccess(String response) {
                    // 本地存储绑定的用户名
                    Log.i(SETTINGS_ACT, "@用户绑定账号 ：" + acountStr + " 成功");
                    callback.onSuccess(response);
                }

                @Override
                public void onFailed(String errorCode, String errorMessage) {
                    Log.i(SETTINGS_ACT, "@用户绑定账号 ：" + acountStr + " 失败，原因 ： " + errorMessage);
                    callback.onFailed(errorCode, errorMessage);
                }
            });
        } else {
        }
    }

    @Override
    public void unbindAccount(final ICommomCallback callback) {
        PushServiceFactory.getCloudPushService().unbindAccount(
                new CommonCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(SETTINGS_ACT, "@用户解绑账户 ：" + "成功");
                        // 删除本地存储的用户名
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onFailed(String errorCode, String errorMessage) {
                        Log.i(SETTINGS_ACT, "@用户解绑账户 ：" + " 失败，原因 ：" + errorMessage);
                        callback.onFailed(errorCode, errorMessage);
                    }
                }
        );

    }

    /**
     * @param type     1、绑定标签到设备 2、绑定标签到账号 3、绑定标签到别名
     * @param tags     标签集合
     * @param alias    别名，仅当var1等于3时有效，可以为null
     * @param callback
     */
    @Override
    public void bindTag(int type, String[] tags, String alias, final ICommomCallback callback) {
        PushServiceFactory.getCloudPushService().bindTag(type, tags, alias, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i(SETTINGS_ACT, "绑定标签到设备成功.");
                callback.onSuccess(response);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.e(SETTINGS_ACT, "绑定标签到设备失败，errorCode: " + errorCode + ", errorMessage：" + errorMessage);
                callback.onFailed(errorCode, errorMessage);
            }
        });
    }

    /**
     * @param type     1、绑定标签到设备 2、绑定标签到账号 3、绑定标签到别名
     * @param tags     标签集合
     * @param alias    别名，仅当var1等于3时有效，可以为null
     * @param callback
     */

    @Override
    public void unbindTag(int type, String[] tags, String alias, final ICommomCallback callback) {
        PushServiceFactory.getCloudPushService().unbindTag(type, tags, alias, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i(SETTINGS_ACT, "解绑账号标签成功.");
                callback.onSuccess(response);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.e(SETTINGS_ACT, "解绑账号标签失败，errorCode: " + errorCode + ", errorMessage：" + errorMessage);
                callback.onFailed(errorCode, errorMessage);
            }
        });
    }

    /**
     * 查询目标绑定的标签
     *
     * @param type     目标类型，1: 本设备
     * @param callback
     */
    @Override
    public void listTags(int type, final ICommomCallback callback) {
        PushServiceFactory.getCloudPushService().listTags(type, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i(SETTINGS_ACT, "解绑别名标签成功，标签：" + response);
                callback.onSuccess(response);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                callback.onFailed(errorCode, errorMessage);
                Log.e(SETTINGS_ACT, "查询设备标签失败，errorCode: " + errorCode + ", errorMessage：" + errorMessage);
            }
        });
    }

    @Override
    public void addAlias(String aliasStr, final ICommomCallback callback) {
        if (!TextUtils.isEmpty(aliasStr)) {
            PushServiceFactory.getCloudPushService().addAlias(aliasStr, new CommonCallback() {
                @Override
                public void onSuccess(String response) {
                    Log.i(SETTINGS_ACT, "添加别名成功.");
                    callback.onSuccess(response);
                }

                @Override
                public void onFailed(String errorCode, String errorMessage) {
                    Log.e(SETTINGS_ACT, "添加别名失败，errorCode: " + errorCode + ", errorMessage：" + errorMessage);
                    callback.onFailed(errorCode, errorMessage);
                }
            });
        } else {
        }
    }

    @Override
    public void removeAlias(String aliasStr, final ICommomCallback callback) {
        if (!TextUtils.isEmpty(aliasStr)) {
            PushServiceFactory.getCloudPushService().removeAlias(aliasStr, new CommonCallback() {
                @Override
                public void onSuccess(String response) {
                    Log.i(SETTINGS_ACT, "删除别名成功.");
                    callback.onSuccess(response);
                }

                @Override
                public void onFailed(String errorCode, String errorMessage) {
                    Log.e(SETTINGS_ACT, "删除别名失败，errorCode: " + errorCode + ", errorMessage：" + errorMessage);
                    callback.onFailed(errorCode, errorMessage);
                }
            });
        } else {
        }

    }

    @Override
    public void listAliases(final ICommomCallback callback) {
        PushServiceFactory.getCloudPushService().listAliases(new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i(SETTINGS_ACT, "listAliases" + response);
                callback.onSuccess(response);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.e(SETTINGS_ACT, "listAliases，errorCode: " + errorCode + ", errorMessage：" + errorMessage);
                callback.onFailed(errorCode, errorMessage);
            }
        });
    }

    @Override
    public String getDeviceId() {
        if (PushServiceFactory.getCloudPushService() != null)
            return PushServiceFactory.getCloudPushService().getDeviceId();
        return null;
    }

    @Override
    public String getUTDeviceId() {
        if (PushServiceFactory.getCloudPushService() != null)
            return PushServiceFactory.getCloudPushService().getUTDeviceId();
        return null;
    }

    @Override
    public void setLogLevel(int var1) {
        PushServiceFactory.getCloudPushService().setLogLevel(var1);
    }

    /**
     * @param startHour   免打扰的起始时间（小时），24小时制，取值范围：0-23
     * @param startMinute 免打扰起始时间（分钟），取值范围：0-59
     * @param endHour     免打扰的结束时间（小时），24小时制，取值范围：0-23
     * @param endMinute   免打扰结束时间（分钟），取值范围：0-59
     * @param callback
     */
    @Override
    public void setDoNotDisturb(int startHour, int startMinute, int endHour, int endMinute, final ICommomCallback callback) {
        PushServiceFactory.getCloudPushService().setDoNotDisturb(startHour, startMinute, endHour, endMinute, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                callback.onSuccess(s);
            }

            @Override
            public void onFailed(String s, String s1) {
                callback.onFailed(s, s1);
            }
        });
    }

    /**
     * 关闭免打扰
     */
    @Override
    public void closeDoNotDisturbMode() {
        PushServiceFactory.getCloudPushService().closeDoNotDisturbMode();
    }

    /**
     * 设置声音文件，若不调用则获取默认文件
     *
     * @param path 声音文件路径
     */
    @Override
    public void setNotificationSoundFilePath(String path) {
        PushServiceFactory.getCloudPushService().setNotificationSoundFilePath(path);
    }

    @Override
    public void setNotificationLargeIcon(Bitmap var1) {
        PushServiceFactory.getCloudPushService().setNotificationLargeIcon(var1);
    }

    @Override
    public void setNotificationSmallIcon(int var1) {
        PushServiceFactory.getCloudPushService().setNotificationSmallIcon(var1);
    }

    @Override
    public void clearTips() {
        PushServiceFactory.getCloudPushService().clearNotifications();

    }

    /**
     * 设置提示方式
     *
     * @param type 0、无声 1、震动 2、铃声 3、铃声震动
     */
    @Override
    public void setRemindType(int type) {
        notification.setRemindType(BasicCustomPushNotification.REMIND_TYPE_SOUND);
        this.notificationType = type;
//        notification.setStatusBarDrawable(R.drawable.logo_yuanjiao_120);//设置状态栏图标
        boolean res = CustomNotificationBuilder.getInstance().setCustomNotification(1, notification);//注册该通知,并设置ID为1
    }

    @Override
    public void setRemindType(Context context, int type) {
        switch (type) {
            case TYPE_SILENT:
                BeepManager.getInstance(context).setMsgSound(context, false);
                BeepManager.getInstance(context).setVibrate(context, false);
                break;
            case TYPE_SOUND:
                BeepManager.getInstance(context).setMsgSound(context, true);
                BeepManager.getInstance(context).setVibrate(context, false);
                break;
            case TYPE_VIBRATE:
                BeepManager.getInstance(context).setMsgSound(context, false);
                BeepManager.getInstance(context).setVibrate(context, true);
                break;
            case TYPE_SOUND_VIBRATE:
                BeepManager.getInstance(context).setMsgSound(context, true);
                BeepManager.getInstance(context).setVibrate(context, true);
                break;
        }
        this.notificationType = type;
    }

    @Override
    public int getRemindType() {
        if (notificationType == TYPE_SILENT)
            return TYPE_SILENT;
        else if (notificationType == TYPE_SOUND)
            return TYPE_SOUND;
        else if (notificationType == TYPE_VIBRATE)
            return TYPE_VIBRATE;
        else {
            return TYPE_SOUND_VIBRATE;
        }
    }


}
