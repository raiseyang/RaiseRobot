package com.raise.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.raise.service.SocketRequestImpl;
import com.raise.service.inter.SocketCallBack;
import com.raise.service.inter.SocketRequestInterface;
import com.raise.utils.Trace;
import com.raise.xunfei.XFSpeechSynthesizer;
import com.raise.xunfei.XFSpeechUnderstander;
import com.raise.xunfei.XFUnderstanderListener;

public class MainActivity extends Activity {

    private final static int TOP_RESUME_SERVER = 0x1;
    private final static int TOP_PAUSE_SERVER = 0x2;
    private final static int SECOND_ORDER_ONE = 0x3;
    private final static int SECOND_ORDER_SECOND = 0x4;
    private final static int SECOND_ORDER_THREE = 0x5;
    private final static int SECOND_ORDER_FOUR = 0x6;

    private final static int MONITOR_STATUS_STOP = 0x1;
    private final static int MONITOR_STATUS_SECOND = 0x2;
    private final static int MONITOR_STATUS_ACTION = 0x3;

    private SparseArray<String> mSecondKeyWords = new SparseArray<>();
    private SparseArray<String> mTopKeyWords = new SparseArray<>();

    XFSpeechUnderstander mUnderstander;
    XFSpeechSynthesizer mSpeak;

    //记录当前菜单状态
    private int mStatus = MONITOR_STATUS_STOP;

    private SocketRequestInterface mSocket = SocketRequestImpl.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUnderstander = XFSpeechUnderstander.getInstance();
        mSpeak = XFSpeechSynthesizer.get_instance();

        mTopKeyWords.put(TOP_RESUME_SERVER, "继续服务");
        mTopKeyWords.put(TOP_PAUSE_SERVER, "暂停服务");

        mSecondKeyWords.put(SECOND_ORDER_ONE, "你是谁");
        mSecondKeyWords.put(SECOND_ORDER_SECOND, "去地点一");
        mSecondKeyWords.put(SECOND_ORDER_THREE, "播放一");
        mSecondKeyWords.put(SECOND_ORDER_FOUR, "拍照");


    }

    private void spark(String text) {
        mSpeak.start(text, null);
    }

    public void click_start_understand(View view) {
        mUnderstander.start(new XFUnderstanderListener() {
            @Override
            protected void on_success(String question, String answer) {
                Toast.makeText(MainActivity.this, question + " " + answer, Toast.LENGTH_SHORT).show();
                on_parse_voice(question);
            }

            @Override
            protected void on_failed(String question, String fail) {
                on_parse_voice(question);
                Toast.makeText(MainActivity.this, question, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void on_error(boolean isNoSpeech) {

                Toast.makeText(MainActivity.this, "on error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void on_parse_voice(String question) {
        //首先解析顶级菜单
        if (!on_parse_top(question)) {
            if (mStatus == MONITOR_STATUS_SECOND)
                //在服务状态，解析二级菜单
                on_parse_second(question);
        }
    }

    /**
     * 解析二级命令
     *
     * @param question
     */
    private boolean on_parse_second(String question) {
        for (int i = 0; i < mSecondKeyWords.size(); i++) {
            boolean has = question.contains(mSecondKeyWords.valueAt(i));
            if (has) {
                execute_action(mSecondKeyWords.keyAt(i));
                return true;
            }
        }
        return false;
    }

    /**
     * 解析顶级命令
     *
     * @param question
     */
    private boolean on_parse_top(String question) {
        for (int i = 0; i < mTopKeyWords.size(); i++) {
            boolean has = question.contains(mTopKeyWords.valueAt(i));
            if (has) {
                execute_action(mTopKeyWords.keyAt(i));
                return true;
            }
        }
        return false;
    }

    private void execute_action(int i) {
        switch (i) {
            case TOP_RESUME_SERVER:
                on_start_server();
                break;
            case TOP_PAUSE_SERVER:
                on_stop_server();
                break;
            case SECOND_ORDER_ONE:
                spark("我是小瑞");
                break;
            case SECOND_ORDER_SECOND:
                spark("我要去地点一了");
                mSocket.moveToTargetLocation(new SocketCallBack() {
                    @Override
                    public void on_success(String result) {
                        Trace.d("moveToTargetLocation on_success");
                    }

                    @Override
                    public void on_failed(String error) {
                        Trace.d("moveToTargetLocation on_failed");
                    }
                }, 1 + "");
                break;
            case SECOND_ORDER_THREE:
                spark("现在播放天气预报：2016年元旦假期（1月1日至3日），华北、黄淮等地将有雾霾天气；新疆北部和东北地区有降雪，南方地区有小到中雨；其余大部地区无明显冷空气活动和强降水过程，气温整体呈回升趋势。");
//                mSocket.moveToTargetLocation(new SocketCallBack() {
//                    @Override
//                    public void on_success(String result) {
//                        Trace.d("moveToTargetLocation on_success");
//                    }
//
//                    @Override
//                    public void on_failed(String error) {
//                        Trace.d("moveToTargetLocation on_failed");
//                    }
//                },1+"");
                break;
            case SECOND_ORDER_FOUR:
                startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
                break;
        }
    }

    private void on_stop_server() {
        mStatus = MONITOR_STATUS_STOP;
        spark("机器人暂停服务");
        mSocket.stopServer(new SocketCallBack() {
            @Override
            public void on_success(String result) {
                Trace.d("on_stop_server() on_success");
            }

            @Override
            public void on_failed(String error) {
                Trace.e("on_stop_server() on_failed");
            }
        });
    }

    private void on_start_server() {
        mStatus = MONITOR_STATUS_SECOND;
        spark("机器人继续服务");
        mSocket.startServer(new SocketCallBack() {
            @Override
            public void on_success(String result) {
                Trace.d("on_start_server() on_success");
            }

            @Override
            public void on_failed(String error) {
                Trace.e("on_start_server() on_failed");
            }
        });
    }

    public void click_code(View view) {
    }
}
