package cn.utsoft.utpushservice.manager;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

import cn.utsoft.utpushservice.ICommomCallback;
import cn.utsoft.utpushservice.UTPushSetting;

/**
 * Created by LiLi on 2017/2/9.
 * Func:初始化云推送、获取推送设置实体类
 * Desc:
 */

public class UTPushManager {

    /**
     * 获取推送设置实体类
     * @return
     */
    public static UTPushSetting getPushSetting() {
        return UTPushSetting.getPushSetting();
    }

    /**
     * 初始化云推送
     * @param applicationContext
     * @param callback
     */
    public static void init(Context applicationContext, final ICommomCallback callback) {
        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i("UTPushUtil==", "init cloudchannel success");
                String deviceId = pushService.getDeviceId();
                Log.i("deviceid", "deviceid==" + deviceId);
                callback.onSuccess(response);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.i("UTPushUtil==", "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
                callback.onFailed(errorCode, errorMessage);
            }
        });
    }

    /**
     * 初始化云推送,动态设置appKey、appSecret
     * @param applicationContext
     * @param appKey
     * @param appSecret
     * @param callback
     */
    public static void init(Context applicationContext, String appKey, String appSecret, final ICommomCallback callback) {
        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, appKey, appSecret, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i("UTPushUtil==", "init cloudchannel success");
                String deviceId = pushService.getDeviceId();
                Log.i("deviceid", "deviceid==" + deviceId);
                callback.onSuccess(response);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.i("UTPushUtil==", "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
                callback.onFailed(errorCode, errorMessage);
            }
        });
    }
}
