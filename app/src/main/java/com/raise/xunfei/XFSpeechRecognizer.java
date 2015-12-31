package com.raise.xunfei;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.raise.domain.UserwordBean;
import com.raise.utils.Trace;

import java.util.List;

/**
 * Created by zhoumingche on 2015/10/24.
 */
public class XFSpeechRecognizer implements RecognizerListener {

    private static XFSpeechRecognizer m_instance = new XFSpeechRecognizer();
    private static SpeechRecognizer mIat;
    private static Context mCtx;

    public static String GRAMMAR_PHOTO_ID;

    public XFSpeechRecognizer() {
    }

    public static XFSpeechRecognizer getInstance() {
        return m_instance;
    }

    public static void init(Context context) {
        mIat = SpeechRecognizer.createRecognizer(context, null);
        mCtx = context;
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "10000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "10000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "0");

        // 指定引擎类型
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
//        buildGrammar();
    }
    /**
     * 上传联系人/词表监听器。
     */
    private static LexiconListener mLexiconListener = new LexiconListener() {

        @Override
        public void onLexiconUpdated(String lexiconId, SpeechError error) {
            if (error != null) {
                Trace.d(error.getErrorDescription());
            } else {
                Trace.d("上传用户词表成功");
            }
        }
    };

    private static void buildGrammar() {
        mIat.buildGrammar("abnf", XFUtils.GRAMMAR_PHOTO_ORDER, new GrammarListener() {
            @Override
            public void onBuildFinish(String s, SpeechError speechError) {
                if (speechError == null) {
                    if (!TextUtils.isEmpty(s)) {
                        Trace.i("语法构建成功:id" + s);
                        GRAMMAR_PHOTO_ID = s;
                    }
                } else {
                    Trace.e("语法构建失败");
                }
            }
        });
    }

    @Override
    public void onVolumeChanged(int i, byte[] bytes) {

    }

    @Override
    public void onBeginOfSpeech() {
        Trace.d("开始识别语音");
    }

    @Override
    public void onEndOfSpeech() {
        Trace.d("结束识别语音");
    }

    @Override
    public void onResult(RecognizerResult recognizerResult, boolean b) {
        String resultString = recognizerResult.getResultString();
        Trace.d(resultString);
        Gson gson = new Gson();
        UserwordBean userwordBean = gson.fromJson(resultString, UserwordBean.class);
        StringBuilder sb = new StringBuilder();
        List<UserwordBean.Ws> wsList = userwordBean.getWs();
        for (UserwordBean.Ws ws : wsList) {
            List<UserwordBean.Ws.Cw> cwList = ws.getCw();
            for (UserwordBean.Ws.Cw cw : cwList) {
                sb.append(cw.getW());
            }
        }
        Trace.d(sb.toString());
    }

    @Override
    public void onError(SpeechError speechError) {
        Trace.d("speechError" + speechError.getErrorDescription());
    }

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {

    }

    public void start(XFRecognizerListener xfRecognizerListener) {
        mIat.startListening(xfRecognizerListener);
    }

    public void stop() {
        mIat.stopListening();
    }

    public void cancel() {
        mIat.cancel();
    }

    public void destroy() {
        mIat.destroy();
    }

    public static abstract class XFRecognizerListener implements RecognizerListener {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {

        }

        @Override
        public void onBeginOfSpeech() {
            Trace.d("命令词识别->开始");
        }

        @Override
        public void onEndOfSpeech() {
            Trace.d("命令词识别->结束");
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }

    }

}
