package cn.utsoft.utpushservice;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

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
//        BeepManager.setMsgSound(context,true);
        String packageName = context.getPackageName();
//        Toast.makeText(context, ""+packageName, Toast.LENGTH_SHORT).show();

//        BeepManager.setVibrate(context,true);
        BeepManager.getInstance(context).playBeepSoundAndVibrate();
        Log.i(TAG, "onMessage: "+context.getPackageName());
    }

}
