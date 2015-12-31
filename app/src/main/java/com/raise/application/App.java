package com.raise.application;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.raise.domain.DeviceInfo;
import com.raise.ui.R;
import com.raise.utils.SdcardUtils;
import com.raise.utils.Trace;
import com.raise.xunfei.XFSpeechRecognizer;
import com.raise.xunfei.XFSpeechSynthesizer;
import com.raise.xunfei.XFSpeechUnderstander;
import com.raise.xunfei.XFUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by raise_yang on 2015/9/29.
 */
public class App extends Application {

    public static String APP_NAME;
    public static String COMPILE_LEVEL;
    public static boolean is_debug;

    @Override
    public void onCreate() {
        super.onCreate();
        APP_NAME = getString(R.string.app_name);
        COMPILE_LEVEL = getString(R.string.compile_level);
        is_debug = "debug".equals(COMPILE_LEVEL) ? true : false;
        if (COMPILE_LEVEL.equals("debug")) {
            App.toast(this, "编译版本:" + getString(R.string.compile_level));
        }
        //初始化log4j
        init_log4j();

        //初始化 讯飞voice --在主线程中使用，若想在子线程中也能使用，请调用下面注释的代码
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=" + XFUtils.APP_ID);
//        SpeechUtility.createUtility(this, SpeechConstant.APPID
//                + XFUtils.APP_ID
////                +"=55fbd0b7"
//                + SpeechConstant.FORCE_LOGIN + "=true");

        //初始化讯飞understander
        XFSpeechUnderstander.init(this);
        XFSpeechRecognizer.init(this);
        XFSpeechSynthesizer.init(this);

        //初始化设备参数
        DeviceInfo.init(this);

    }

    private void init_log4j() {
        String sd_path = SdcardUtils.getSdCardPath();
        if (sd_path != null) {
            String logFilePath;
            logFilePath = sd_path
                    + File.separator + APP_NAME
                    + File.separator + "log";
            File file = new File(logFilePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Trace.init(getString(R.string.compile_level), logFilePath);
        }
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, int res_id) {
        Toast.makeText(context, context.getString(res_id), Toast.LENGTH_SHORT).show();
    }

}
