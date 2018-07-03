package net.lxj.alipay;

import android.app.Application;
import android.os.Environment;

import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;

public class MyApplication extends Application {




    @Override
    public void onCreate() {
        super.onCreate();
        //初始化 SDK 基本配置
//        TIMSdkConfig config = new TIMSdkConfig(SDK_APPID)
//                .enableCrashReport(false)
//                .enableLogPrint(true)
//                .setLogLevel(TIMLogLevel.DEBUG)
//                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");
//
////初始化 SDK
//        TIMManager.getInstance().init(getApplicationContext(), config);
    }
}
