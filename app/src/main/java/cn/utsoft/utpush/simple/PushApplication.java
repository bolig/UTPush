package cn.utsoft.utpush.simple;

import android.app.Application;
import android.util.Log;

import cn.utsoft.utpushservice.ICommomCallback;
import cn.utsoft.utpushservice.manager.UTPushManager;

/**
 * Created by LiLi on 2017/2/9.
 * Func:
 * Desc:
 */

public class PushApplication extends Application {
    final String TAG = getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        UTPushManager.init(this, new ICommomCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.i(TAG, "errorCode"+errorCode+"errorMessage"+errorMessage);

            }
        });
    }
}
