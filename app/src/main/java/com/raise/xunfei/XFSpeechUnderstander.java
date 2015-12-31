package com.raise.xunfei;

import android.content.Context;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUnderstander;
import com.raise.utils.Trace;

/**
 * Created by raise_yang on 2015/9/29.
 * SpeechUnderstanderListener 的装饰类 Decorator
 * 这个类是一个单例：、
 * 1，必须在application中调用init()来初始化
 * 2，通过getInstance()得到其实例
 */
public class XFSpeechUnderstander {

    private static final String TAG = "XFSpeechUnderstander";
    private static Context m_ctx;
    /**
     * 被包装对象
     */
    private static SpeechUnderstander m_understander;
    /**
     * 单例对象
     */
    private static XFSpeechUnderstander m_instance;

    private XFSpeechUnderstander() {
    }

    public static XFSpeechUnderstander getInstance() {
        return m_instance;
    }

    public static void init(Context context) {
        m_instance = new XFSpeechUnderstander();
        m_ctx = context;
        m_understander = SpeechUnderstander.createUnderstander(context, initListener);

        m_understander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        m_understander.setParameter(SpeechConstant.VAD_BOS, "10000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        m_understander.setParameter(SpeechConstant.VAD_EOS, "2000");

        // 设置标点符号，默认：1（有标点）
        m_understander.setParameter(SpeechConstant.ASR_PTT, "0");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//        m_understander.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
//        m_understander.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/sud.wav");

    }


    private static InitListener initListener = new InitListener() {
        @Override
        public void onInit(int i) {
            if (i != ErrorCode.SUCCESS) {
                Trace.e("初始化 speech understander 失败,ErrorCode:" + i);
            } else {
                Trace.d("初始化 speech understander 成功");
            }
        }
    };

    public void start(XFUnderstanderListener listener) {
        if (!m_understander.isUnderstanding())
            m_understander.startUnderstanding(listener);
    }

    public void stop() {
        if (m_understander.isUnderstanding())
            m_understander.stopUnderstanding();
    }

    public void cancel() {
        m_understander.cancel();
    }

    public void destroy() {
        m_understander.destroy();
    }

    public boolean is_starting() {
        return m_understander.isUnderstanding();
    }
}
