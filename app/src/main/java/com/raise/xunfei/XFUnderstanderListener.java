package com.raise.xunfei;

import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.raise.domain.ConversationInfo;
import com.raise.domain.ConversationJson;
import com.raise.utils.Trace;

import java.util.ArrayList;

/**
 * Created by raise_yang on 2015/10/29.
 */
public abstract class XFUnderstanderListener implements SpeechUnderstanderListener {
    /**
     * @param question 用户说的话
     * @param answer   机器人说的话
     */
    protected abstract void on_success(String question, String answer);

    /**
     * @param question 用户说的话
     * @param fail     机器人说的话
     */
    protected abstract void on_failed(String question, String fail);

    /**
     * @param isNoSpeech true is 你好像没有说话哦, false is network error or other
     */
    protected abstract void on_error(boolean isNoSpeech);

    @Override
    public void onResult(UnderstanderResult understanderResult) {
        if (understanderResult != null &&
                !TextUtils.isEmpty(understanderResult.getResultString())) {
            String result = understanderResult.getResultString();
            Trace.i("语义理解返回结果raw：" + result);
            //解析结果
            Gson gson = new Gson();
            ConversationJson conversation = null;
            try {
                conversation = gson.fromJson(result, ConversationJson.class);
            } catch (JsonSyntaxException e) {
                Trace.e("Gson解析异常:" + e.getMessage());
                on_failed("", "Gson parse failed,e=" + e.getMessage());
                return;
            }
            //判断应答码
            //0：操作成功；4：没有对应的场景；2&3：无效请求，服务器出错
            if (conversation.getRc() != 0) {
                //没有设定场景
                if (conversation.getRc() == 4) {
                    on_failed(conversation.getQuestion(), "");
                } else {
                    if (conversation.getError() != null) {
                        on_failed(conversation.getQuestion(), conversation.getError().getMessage());
                    } else {
                        on_failed(conversation.getQuestion(), "no define error, rc=" + conversation.getRc());
                    }
                }
            } else {
                //正常返回,并且操作方式operation为：ANSWER
                if ("ANSWER".equals(conversation.getOperation())) {
                    on_success(conversation.getQuestion(), conversation.getAnswer().getText());
                } else {
                    //如果不是ANSWER返回操作，就在moreResult中找到ANSWER操作
                    ArrayList<ConversationInfo> moreResults = conversation.getMoreResults();
                    if (moreResults == null || moreResults.size() == 0) {
                        on_failed(conversation.getQuestion(), "I don't answer this question.");
                    } else {
                        for (ConversationInfo info : moreResults) {
                            if ("ANSWER".equals(info.getOperation())) {
                                on_success(info.getQuestion(), info.getAnswer().getText());
                                return;
                            }
                        }
                        on_failed(conversation.getQuestion(), "I don't answer this question.");
                    }

                }
            }
        } else {
            on_failed("", "语义理解返回结果为null");
        }
    }

    @Override
    public void onVolumeChanged(int i, byte[] bytes) {

    }

    @Override
    public void onBeginOfSpeech() {
        Trace.d("开始语义理解");
    }

    @Override
    public void onEndOfSpeech() {
        Trace.d("结束语义理解");
    }

    @Override
    public void onError(SpeechError speechError) {
        if (speechError != null) {
            Trace.e("语义理解error:" + speechError.getErrorDescription() + "  code:" + speechError.getErrorCode());
            if (speechError.getErrorCode() == 10118 || "您好像没有说话哦".equals(speechError.getErrorDescription())) {
                on_error(true);
            } else {
                on_error(false);
            }
        } else {
            on_error(false);
        }
    }


    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {

    }
}
