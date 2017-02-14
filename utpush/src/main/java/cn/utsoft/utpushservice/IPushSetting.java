package cn.utsoft.utpushservice;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by LiLi on 2017/2/8.
 * Func:推送功能管理接口
 * Desc:
 */

public interface IPushSetting {

    void onAppStart();

    void bindAccount(String var1, ICommomCallback var2);

    void unbindAccount(ICommomCallback var1);

    void bindTag(int var1, String[] var2, String var3, ICommomCallback var4);

    void unbindTag(int var1, String[] var2, String var3, ICommomCallback var4);

    void listTags(int var1, ICommomCallback var2);

    void addAlias(String var1, ICommomCallback var2);

    void removeAlias(String var1, ICommomCallback var2);

    void listAliases(ICommomCallback var1);

    String getDeviceId();

    String getUTDeviceId();

    void setLogLevel(int var1);

    void setDoNotDisturb(int var1, int var2, int var3, int var4, ICommomCallback var5);

    void closeDoNotDisturbMode();

    void setNotificationSoundFilePath(String var1);

    void setNotificationLargeIcon(Bitmap var1);

    void setNotificationSmallIcon(int var1);

    void clearTips();

    void setMsgRemindType(Context context, int type);

    int getMsgRemindType();

    int getNotificationRemindType();

    boolean customNofication(int remindType,int drawableRes);

    boolean customNofication(int layout,int iconID,int titleID,int textID,int remindType);



}
