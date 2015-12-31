package com.raise.xunfei;

import android.content.Context;
import android.os.Bundle;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.raise.utils.Trace;

/**
 * 装饰类Decorator,包含一个被装饰对象SpeechSynthesizer
 */
public class XFSpeechSynthesizer {

    private static final XFSynthesizerListener DEFAULT_LISTENER = new XFSynthesizerListener() {

        @Override
        public void on_completed(SpeechError speechError) {

        }
    };

    private SpeechSynthesizer m_synthesizer;

    private static XFSpeechSynthesizer m_instance;

    /**
     * 播放监听回调,默认实现空的回调方法
     */
    private XFSynthesizerListener m_listener = DEFAULT_LISTENER;

    //调用语音合成引擎，默认在线
//    private String m_engine_type = SpeechConstant.TYPE_LOCAL;
    private String m_engine_type = SpeechConstant.TYPE_CLOUD;

    public static void init(Context context) {
        m_instance = new XFSpeechSynthesizer(context);
    }

    public static XFSpeechSynthesizer get_instance() {
        return m_instance;
    }

    private XFSpeechSynthesizer(Context context) {
        m_synthesizer = SpeechSynthesizer.createSynthesizer(context, new InitListener() {
            @Override
            public void onInit(int i) {
                if (i != ErrorCode.SUCCESS) {
                    Trace.e("初始化 speech Synthesizer 失败,ErrorCode:" + i);
                } else {
                    Trace.d("初始化 speech Synthesizer 成功");
                }
            }
        });
        if (m_engine_type.equals(SpeechConstant.TYPE_CLOUD)) {
            m_synthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
            m_synthesizer.setParameter(SpeechConstant.VOICE_NAME, XFUtils.COMPOSITE_VOICE_XIAOYAN);//设置发音人
        } else {
            m_synthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL); //设置云端
            m_synthesizer.setParameter(SpeechConstant.VOICE_NAME, "");//设置发音人
        }
        m_synthesizer.setParameter(SpeechConstant.SPEED, "50");//设置语速
        m_synthesizer.setParameter(SpeechConstant.VOLUME, "100");//设置音量，范围0~100

//        如果不需要保存合成音频，注释该行代码
//        m_synthesizer.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
    }

    public boolean isSpeaking() {
        return m_synthesizer.isSpeaking();
    }

    public void start(String input, XFSynthesizerListener listener) {
        Trace.d("XF语音播放：" + input);
        this.m_listener = listener == null ? DEFAULT_LISTENER : listener;
        m_synthesizer.startSpeaking(input, m_listener);
    }

    public void stop() {
        m_synthesizer.stopSpeaking();
    }

    public void destroy() {
        m_synthesizer.destroy();
    }

    /**
     * SynthesizerListener回调接口的空实现
     * 方便重写方法
     */
    public static abstract class XFSynthesizerListener implements SynthesizerListener {
        @Override
        public void onSpeakBegin() {
            Trace.d("开始播放");
        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {
            Trace.d("结束播放");
            on_completed(speechError);
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }

        public abstract void on_completed(SpeechError speechError);
    }
}
