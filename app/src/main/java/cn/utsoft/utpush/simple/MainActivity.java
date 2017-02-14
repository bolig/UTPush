package cn.utsoft.utpush.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import cn.utsoft.utpushservice.ICommomCallback;
import cn.utsoft.utpushservice.manager.UTPushManager;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String info = getClass().getSimpleName();

    String acountStr;
    String tagStr;
    String aliasStr;

    Button bindAccount;
    Button unbindAccount;
    Button addAlias;
    Button removeAlias;
    Button bindTagToDev;
    Button unbindTagFromDev;
    Button bindTagToAccount;
    Button unbindTagFromAccount;
    Button bindTagToAlias;
    Button unbindTagFromAlias;
    Button listTags;
    Button listAliases;
    Button silent;
    Button vibrate;
    Button sound;
    Button soundVibrate;
    Button settingMusic;
    Button deviceID;
    Button diyNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindAccount = (Button) this.findViewById(R.id.bindAccount);
        unbindAccount = (Button) this.findViewById(R.id.unbindAccount);
        addAlias = (Button) this.findViewById(R.id.addAlias);
        removeAlias = (Button) this.findViewById(R.id.removeAlias);
        bindTagToDev = (Button) this.findViewById(R.id.bindTagToDev);
        unbindTagFromDev = (Button) this.findViewById(R.id.unbindTagFromDev);
        bindTagToAccount = (Button) this.findViewById(R.id.bindTagToAccount);
        unbindTagFromAccount = (Button) this.findViewById(R.id.unbindTagFromAccount);
        bindTagToAlias = (Button) this.findViewById(R.id.bindTagToAlias);
        unbindTagFromAlias = (Button) this.findViewById(R.id.unbindTagFromAlias);
        listTags = (Button) this.findViewById(R.id.listTags);
        listAliases = (Button) this.findViewById(R.id.listAliases);
        silent = (Button) this.findViewById(R.id.btn_silent);
        settingMusic = (Button) this.findViewById(R.id.setting_music);
        deviceID = (Button) this.findViewById(R.id.btn_deviceid);
        diyNotification = (Button) this.findViewById(R.id.btn_diyNotification);

        diyNotification.setOnClickListener(this);
        deviceID.setOnClickListener(this);
        settingMusic.setOnClickListener(this);
        bindAccount.setOnClickListener(this);
        unbindAccount.setOnClickListener(this);
        addAlias.setOnClickListener(this);
        removeAlias.setOnClickListener(this);
        bindTagToDev.setOnClickListener(this);
        unbindTagFromDev.setOnClickListener(this);
        bindTagToAccount.setOnClickListener(this);
        unbindTagFromAccount.setOnClickListener(this);
        bindTagToAlias.setOnClickListener(this);
        unbindTagFromAlias.setOnClickListener(this);
        listTags.setOnClickListener(this);
        listAliases.setOnClickListener(this);
        silent.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_music:
                Intent intent = new Intent(this, SecendSettingActivity.class);
                startActivity(intent);
                break;
            // 绑定账号
            case R.id.bindAccount:
                acountStr = ((EditText) this.findViewById(R.id.userAccount)).getText().toString().trim();
                if (!acountStr.equals("") && null != acountStr) {
                    UTPushManager.getPushSetting().bindAccount(acountStr, new ICommomCallback() {
                        @Override
                        public void onSuccess(String response) {
                            Log.i(info, "bindSuccess");
                        }

                        @Override
                        public void onFailed(String errorCode, String errorMessage) {
                            Log.i(info, "bindFailed" + errorMessage);

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "请输入账号，谢谢",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            // 解绑账号
            case R.id.unbindAccount:
                UTPushManager.getPushSetting().unbindAccount(new ICommomCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(info, "unBindSuccess");
                    }

                    @Override
                    public void onFailed(String errorCode, String errorMessage) {
                        Log.i(info, "unBindFailed" + errorMessage);

                    }
                });
                break;
            // 绑定标签到设备
            case R.id.bindTagToDev:
                UTPushManager.getPushSetting().bindTag(1, new String[]{"标签1", "标签2"}, null, new ICommomCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(info, "bindTagSuccess");

                    }

                    @Override
                    public void onFailed(String errorCode, String errorMessage) {
                        Log.i(info, "bindTagFailed" + errorMessage);

                    }
                });
                break;
            // 解绑设备标签
            case R.id.unbindTagFromDev:
                UTPushManager.getPushSetting().unbindTag(1, new String[]{"标签1", "标签2"}, null, new ICommomCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(info, "unBindTagSuccess");

                    }

                    @Override
                    public void onFailed(String errorCode, String errorMessage) {
                        Log.i(info, "unBindTagFailed" + errorMessage);

                    }
                });
                break;
            // 绑定标签到账号
            case R.id.bindTagToAccount:
                UTPushManager.getPushSetting().bindTag(2, new String[]{"标签1", "标签2"}, null, new ICommomCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(info, "bindTagSuccess");

                    }

                    @Override
                    public void onFailed(String errorCode, String errorMessage) {
                        Log.i(info, "bindTagFailed" + errorMessage);

                    }
                });
                break;
            // 解绑账号标签
            case R.id.unbindTagFromAccount:
                UTPushManager.getPushSetting().unbindTag(2, new String[]{"标签1", "标签2"}, null, new ICommomCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(info, "unBindTagSuccess");

                    }

                    @Override
                    public void onFailed(String errorCode, String errorMessage) {
                        Log.i(info, "unBindTagFailed" + errorMessage);

                    }
                });
                break;
            // 绑定标签到别名
            case R.id.bindTagToAlias:
                UTPushManager.getPushSetting().bindTag(3, new String[]{"标签1", "标签2"}, "alias", new ICommomCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(info, "bindTagSuccess");

                    }

                    @Override
                    public void onFailed(String errorCode, String errorMessage) {
                        Log.i(info, "bindTagFailed" + errorMessage);

                    }
                });
                break;
            // 解绑别名标签
            case R.id.unbindTagFromAlias:
                UTPushManager.getPushSetting().unbindTag(3, new String[]{"标签1", "标签2"}, "alias", new ICommomCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(info, "unbindTagSuccess");

                    }

                    @Override
                    public void onFailed(String errorCode, String errorMessage) {
                        Log.i(info, "unbindTagFailed" + errorMessage);

                    }
                });
                break;
            // 查询设备标签
            case R.id.listTags:
                UTPushManager.getPushSetting().listTags(1, new ICommomCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(info, "listTagSuccess");

                    }

                    @Override
                    public void onFailed(String errorCode, String errorMessage) {
                        Log.i(info, "listTagFailed" + errorMessage);

                    }
                });
                break;
            // 查询设备别名
            case R.id.listAliases:
                UTPushManager.getPushSetting().listAliases(new ICommomCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(info, "listAliases" + response);

                    }

                    @Override
                    public void onFailed(String errorCode, String errorMessage) {
                        Log.i(info, "listAliases" + errorMessage);
                    }
                });
                break;
            // 添加设备别名
            case R.id.addAlias:
                aliasStr = ((EditText) this.findViewById(R.id.inputAlias)).getText().toString();
                if (aliasStr != null && aliasStr.length() > 0) {
                    UTPushManager.getPushSetting().addAlias(aliasStr, new ICommomCallback() {
                        @Override
                        public void onSuccess(String response) {
                            Log.i(info, "addAliasSuccess");

                        }

                        @Override
                        public void onFailed(String errorCode, String errorMessage) {
                            Log.i(info, "addAliasFailed" + errorMessage);

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "请输入别名，谢谢", Toast.LENGTH_SHORT).show();
                }
                break;
            // 删除设备别名
            case R.id.removeAlias:
//                alias 别名（alias = null or alias.length = 0时，删除设备全部别名）
                UTPushManager.getPushSetting().removeAlias(aliasStr, new ICommomCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(info, "delAliasSuccess");
                    }

                    @Override
                    public void onFailed(String errorCode, String errorMessage) {
                        Log.i(info, "delAliasFailed" + errorMessage);
                    }
                });
                break;

            case R.id.btn_silent:
                UTPushManager.getPushSetting().setRemindType(this, 0);
                Log.i(info, "remaindType" + UTPushManager.getPushSetting().getRemindType());
                break;


            case R.id.btn_deviceid:
                String deviceId = UTPushManager.getPushSetting().getDeviceId();
                Toast.makeText(this, ""+deviceId, Toast.LENGTH_SHORT).show();
                break;
            case  R.id.btn_diyNotification:
              /*  boolean b = UTPushManager.getPushSetting().customNofication(BasicCustomPushNotification.REMIND_TYPE_SOUND, R.mipmap.logo_yuanjiao_120);*/
                boolean b = UTPushManager.getPushSetting().customNofication(R.layout.layout_diycontent, R.id.imageView, R.id.textView, R.id.textView2);
                Toast.makeText(this, "ddddddddddd===="+ b, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
