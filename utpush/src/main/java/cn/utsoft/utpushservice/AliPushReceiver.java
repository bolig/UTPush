package cn.utsoft.utpushservice;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

import java.util.Map;

import cn.utsoft.utpushservice.manager.BeepManager;

/**
 * Created by 李波 on 2017/2/7.
 * Function:
 * Desc:
 */

public class AliPushReceiver extends MessageReceiver {



    /**
     * 当推送类型为消息时调用
     *
     * @param context
     * @param cPushMessage CPushMessage类型，可以获取消息Id、消息标题和内容。
     */
    @Override
    protected void onMessage(Context context, CPushMessage cPushMessage) {
        super.onMessage(context, cPushMessage);

        String content = cPushMessage.getContent();
        Log.d(TAG, "消息:" + content + "; extraMap:");
        BeepManager.getInstance(context).playBeepSoundAndVibrate();
    }
    /**
     * 通知回调
     * @param context
     * @param title
     * @param summary
     * @param map
     */
    @Override
    protected void onNotification(Context context, String title, String summary, Map<String, String> map) {
        super.onNotification(context, title, summary, map);
        Log.d(TAG, "title" + title + "\nsummary" + summary + "\nmap" + map.toString());
    }
    /**
     * 通知打开回调
     * @param context
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    protected void onNotificationOpened(Context context, String s, String s1, String s2) {
        super.onNotificationOpened(context, s, s1, s2);
    }

    /**
     * 通知删除回调
     * @param context
     * @param s
     */
    @Override
    protected void onNotificationRemoved(Context context, String s) {
        super.onNotificationRemoved(context, s);
    }

    /**
     * 通知到达回调
     * @param context
     * @param s
     * @param s1
     * @param map
     * @param i
     * @param s2
     * @param s3
     */
    @Override
    protected void onNotificationReceivedInApp(Context context, String s, String s1, Map<String, String> map, int i, String s2, String s3) {
        super.onNotificationReceivedInApp(context, s, s1, map, i, s2, s3);
    }

}
