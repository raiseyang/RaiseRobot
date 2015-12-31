package com.raise.service;

import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;

import com.raise.application.App;
import com.raise.service.inter.SocketCallBack;
import com.raise.utils.Trace;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Created by raise_yang on 2015/10/14.
 */
public class SocketBase {

    public static final String cloud_ip = "192.168.200.88";
    public static final int cloud_port = 8000;

    protected SocketCallBack m_callBack;
    protected Socket m_socket;

    protected String m_service_ip;
    protected int m_service_port;

    private BufferedReader m_reader;
    private OutputStream m_write;


    public synchronized void connection(final SocketCallBack callBack) {
        if (App.is_debug) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callBack.on_success("");
                }
            }, 1000);
        } else {
            if (m_socket != null && is_alive()) {
                Trace.d("socket已连接，请不要重复打开socket");
                callBack.on_success("");
                return;
            }
            new AsyncTask<Void, Void, Integer>() {

                Integer success = Integer.valueOf(0);

                @Override
                protected Integer doInBackground(Void... params) {
                    try {
                        Trace.i("正在连接 cloud 服务器.");
                        m_socket = new Socket(m_service_ip, m_service_port);
                        m_socket.setKeepAlive(true);
                        m_reader = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
                        m_write = m_socket.getOutputStream();
                        if (m_socket != null) {
                            Trace.i("连接 cloud 服务器成功.");
                        } else {
                            Trace.i("连接服务器失败.");
                            return Integer.valueOf(-1);
                        }
                        return success;
                    } catch (Exception e) {
                        Trace.i("socket 连接服务器异常:" + e.getMessage());
                        return Integer.valueOf(-1);
                    }
                }

                @Override
                protected void onPostExecute(Integer result) {
                    if (result.equals(success)) {
                        callBack.on_success("");
                    } else {
                        callBack.on_failed("");
                    }
                }
            }.execute();
        }
    }


    protected void fetch_to_service(SocketCallBack callBack, String params) {
        try {
            Trace.i(params);
            this.fetch_to_service(callBack, params.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            callBack.on_failed("发送参数异常:不支持的编码格式");
            e.printStackTrace();
        }
    }

    protected void fetch_to_service(SocketCallBack callBack, byte[] params) {
//        Log.d("socket", "向服务器发送的数据：" + new String(params));
        m_callBack = callBack;
        new SocketTask().executeOnExecutor(Executors.newCachedThreadPool(), params);
    }

    public boolean is_alive() {
        if (App.is_debug) {
            return true;
        } else {
            if (m_socket != null && m_socket.isConnected()) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 释放socket和流
     */
    public void release() {
        try {
            m_write.close();
            m_reader.close();
            m_socket.close();
        } catch (Exception e) {
            //Ignore
        } finally {
            m_socket = null;
        }
    }


    class SocketTask extends AsyncTask<byte[], Void, String> {

        private String m_error_msg;

        @Override
        protected String doInBackground(byte[]... params) {
            if (m_socket == null) {
                m_error_msg = "socket 未初始化.";
                return null;
            }
            if (m_socket.isConnected()) {
                if (m_socket.isClosed()) {
                    m_error_msg = "socket 已关闭.";
                    return null;
                }
//                BufferedReader reader = null;
//                OutputStream write = null;
                try {
//                    m_reader = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
//                    m_write = m_socket.getOutputStream();
                    //发送数据
                    m_write.write(params[0]);
                    m_write.flush();
                    Trace.i("socket_task 发送：" + new String(params[0]));
                    //接收数据
                    char[] char_array = new char[1024];
                    //这里会挂起，直到读到数据
                    m_reader.read(char_array);
                    Trace.i("socket_task 接收：" + String.valueOf(char_array));
                    if (TextUtils.isEmpty(String.valueOf(char_array).trim())) {
                        Trace.e("socket已关闭");
                        m_socket.close();
                        m_socket = null;
                        m_error_msg = "socket已关闭";
                        return null;
//                        ViLogger.e("接收到了服务器返回来的空字符" + "重新挂起请求");
//                        m_reader.read(char_array);
//                        ViLogger.i("socket_task 接收：" + String.valueOf(char_array));
                    }
//                    if (TextUtils.isEmpty(String.valueOf(char_array).trim())) {
//                        ViLogger.i("接收到了服务器返回来的空字符" + "重新挂起请求");
//                        m_reader.read(char_array);
//                        ViLogger.i("socket_task 接收：" + String.valueOf(char_array));
//                    }
                    return String.valueOf(char_array);
                } catch (Exception e) {
                    e.printStackTrace();
                    m_error_msg = "socket_task 与服务器传输异常";
                    return null;
                }
//                finally {
//                    try {
//                        reader.close();
//                        write.close();
//                    } catch (Exception e) {
//                    }
//                }
            } else {
                m_error_msg = "socket 未连接.";
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                m_callBack.on_success(s);
            } else {
                Trace.e("socket error:" + m_error_msg);
                m_callBack.on_failed(m_error_msg);
            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}
