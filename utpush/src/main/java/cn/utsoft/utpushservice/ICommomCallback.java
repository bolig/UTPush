package cn.utsoft.utpushservice;

/**
 * Created by LiLi on 2017/2/9.
 * Func:
 * Desc:
 */

public interface ICommomCallback {
    void onSuccess(String response);

    void onFailed(String errorCode, String errorMessage);

}
