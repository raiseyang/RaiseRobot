package com.raise.service;

import android.os.Handler;

import com.raise.application.App;
import com.raise.service.inter.SocketCallBack;
import com.raise.service.inter.SocketRequestInterface;

/**
 * Created by raise_yang on 2015/10/14.
 */
public class SocketRequestImpl extends SocketBase implements SocketRequestInterface {

    private static SocketRequestImpl instance;

    static {
        instance = new SocketRequestImpl();
    }

    private SocketRequestImpl() {
        m_service_ip = SocketBase.cloud_ip;
        m_service_port = SocketBase.cloud_port;
    }

    public static SocketRequestImpl getInstance() {
        return instance;
    }

    @Override
    public void getSumNode(final SocketCallBack callBack) {
        if (App.is_debug) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //需要修改
                    callBack.on_success("response:totalpos:::end");
                }
            }, 5000);
        } else {
            fetch_to_service(callBack, "request:totalpos:::end");
        }
    }

    @Override
    public void moveToTargetLocation(final SocketCallBack callBack, String location) {
        if (App.is_debug) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callBack.on_success("response:movepos:success:1:3:end");
                }
            }, 5000);
        } else {
            fetch_to_service(callBack, String.format("request:movepos:%s::end", location));
        }
    }

    @Override
    public void getCurrentLocation(final SocketCallBack callBack) {
        if (App.is_debug) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    callBack.on_success("response:getpos:success:1:3:end");
                }
            }, 1000);
        } else {
            fetch_to_service(callBack, "request:getpos:::end");
        }
    }

    @Override
    public void setCurrentLocation(final SocketCallBack callBack, String location) {
        if (App.is_debug) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //需要修改
                    callBack.on_success("response:getpos:success:1:3:end");
                }
            }, 1000);
        } else {
            fetch_to_service(callBack, String.format("request:setpos:%s::end", location));
        }
    }

    @Override
    public void stopServer(final SocketCallBack callBack) {
        if (App.is_debug) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //需要修改
                    callBack.on_success("response:topmenu:ok:end");
                }
            }, 1000);
        } else {
            fetch_to_service(callBack, String.format("request:topmenu:stop:end"));
        }
    }

    @Override
    public void startServer(final SocketCallBack callBack) {
        if (App.is_debug) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //需要修改
                    callBack.on_success("response:topmenu:ok:end");
                }
            }, 1000);
        } else {
            fetch_to_service(callBack, String.format("request:topmenu:start:end"));
        }
    }


}
