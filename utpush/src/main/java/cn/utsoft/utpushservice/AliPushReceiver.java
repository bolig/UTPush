package cn.utsoft.utpushservice;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

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

    }
}
