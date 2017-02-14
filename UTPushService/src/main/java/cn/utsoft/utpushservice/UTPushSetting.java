package cn.utsoft.utpushservice;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.notification.AdvancedCustomPushNotification;
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

    private final int TYPE_SILENT = 0;
    private final int TYPE_VIBRATE = 1;
    private final int TYPE_SOUND = 2;
    private final int TYPE_SOUND_VIBRATE = 3;
    private int notificationType = 3;
    private int msgType = 2;

    public static UTPushSetting getPushSetting() {
        return pushSetting;
    }

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
            throw new RuntimeException("acountStr不能为空");

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
        if (tags.length == 0) {
            throw new RuntimeException("add tags");
        }
        if (type != 1 || type != 2 || type != 3) {
            throw new RuntimeException("type类型错误");
        }
        if (type == 3 && alias == null) {
            throw new RuntimeException("当type=3时alias不能为null");
        }
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
        if (tags.length == 0) {
            throw new RuntimeException("add tags");
        }
        if (type != 1 || type != 2 || type != 3) {
            throw new RuntimeException("type类型错误");
        }
        if (type == 3 && alias == null) {
            throw new RuntimeException("当type=3时alias不能为null");
        }

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
        if (type != 1) {
            throw new RuntimeException("type类型错误");
        }
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
            throw new RuntimeException("aliasStr不能为空");
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
            throw new RuntimeException("aliasStr不能为空");

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
        if (!isTimeError(startHour, startMinute, endHour, endMinute)) {
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
        } else {
            throw new RuntimeException("请设置正确时间段");
        }
    }

    private boolean isTimeError(int startHour, int startMinute, int endHour, int endMinute) {
        if (startHour < 0 || startHour > 23 || startMinute < 0 || startMinute > 59 || endHour < 0 || endHour > 23 || endMinute < 0 || endMinute > 59) {
            return true;
        }
        return false;
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
    public void setNotificationLargeIcon(Bitmap bitmap) {
        if (bitmap == null) {
            throw new RuntimeException("bitmap不能为null");
        }
        PushServiceFactory.getCloudPushService().setNotificationLargeIcon(bitmap);
    }

    @Override
    public void setNotificationSmallIcon(int res) {
        PushServiceFactory.getCloudPushService().setNotificationSmallIcon(res);
    }

    @Override
    public void clearTips() {
        PushServiceFactory.getCloudPushService().clearNotifications();
    }


    /**
     * 设置消息提示方式
     *
     * @param type 0、无声 1、震动 2、铃声 3、铃声震动
     */

    @Override
    public void setMsgRemindType(Context context, int type) {
        if (type != 0 || type != 1 || type != 2 || type != 3) {
            throw new RuntimeException("type类型错误");
        }
        switch (type) {
            case TYPE_SILENT://免打扰
                BeepManager.getInstance(context).setMsgSound(context, false);
                BeepManager.getInstance(context).setVibrate(context, false);
                break;
            case TYPE_SOUND://铃音
                BeepManager.getInstance(context).setMsgSound(context, true);
                BeepManager.getInstance(context).setVibrate(context, false);
                break;
            case TYPE_VIBRATE://震动
                BeepManager.getInstance(context).setMsgSound(context, false);
                BeepManager.getInstance(context).setVibrate(context, true);
                break;
            case TYPE_SOUND_VIBRATE://铃声震动
                BeepManager.getInstance(context).setMsgSound(context, true);
                BeepManager.getInstance(context).setVibrate(context, true);
                break;
        }
        this.msgType = type;
    }

    @Override
    public int getMsgRemindType() {
        if (msgType == TYPE_SILENT)
            return TYPE_SILENT;
        else if (notificationType == TYPE_SOUND)
            return TYPE_SOUND;
        else if (notificationType == TYPE_VIBRATE)
            return TYPE_VIBRATE;
        else {
            return TYPE_SOUND_VIBRATE;
        }
    }

    @Override
    public int getNotificationRemindType() {
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


    /**
     * 设置通知提示方式
     *
     * @param remindType 0、无声 1、震动 2、铃声 3、铃声震动
     */
    @Override
    public boolean customNofication(int remindType, int statusDrawable) {
        if (remindType != 0 || remindType != 1 || remindType != 2 || remindType != 3) {
            throw new RuntimeException("type类型错误");
        }
        BasicCustomPushNotification notification = new BasicCustomPushNotification();
        notification.setRemindType(remindType);//设置提醒方式为声音
        if (statusDrawable > 0) {
            notification.setStatusBarDrawable(statusDrawable);//设置状态栏图标
        }
        boolean res = CustomNotificationBuilder.getInstance().setCustomNotification(001, notification);//注册该通知,并设置ID为1
        this.notificationType = remindType;
        return res;
    }

    /**
     * 设置通知提示方式及自定义通知布局
     *
     * @param remindType 0、无声 1、震动 2、铃声 3、铃声震动
     * @param layout 自定义布局id
     */
    @Override
    public boolean customNofication(int layout, int iconID, int titleID, int textID, int remindType) {
        if (layout < 0 || iconID < 0 || titleID < 0 || textID < 0) {
            throw new RuntimeException("请填入正确资源id");
        }

        if (remindType != 0 || remindType != 1 || remindType != 2 || remindType != 3) {
            throw new RuntimeException("type类型错误");
        }
        //创建高级自定义样式通知,设置布局文件以及对应的控件ID
        AdvancedCustomPushNotification notification = new AdvancedCustomPushNotification(layout, iconID, titleID, textID);
        notification.setServerOptionFirst(true);//设置服务端配置优先
        notification.setBuildWhenAppInForeground(false);//设置当推送到达时如果应用处于前台不创建通知
        notification.setRemindType(remindType);
        boolean res = CustomNotificationBuilder.getInstance().setCustomNotification(002, notification);//注册该通知,并设置ID为2
        PushServiceFactory.getCloudPushService().clearNotifications();
        return res;
    }


}
