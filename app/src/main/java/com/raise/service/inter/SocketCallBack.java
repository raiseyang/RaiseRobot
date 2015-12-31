package com.raise.service.inter;

/**
 * Created by raise_yang on 2015/10/13.
 */
public interface SocketCallBack {
    /**
     * 请求成功，该方法被调用
     * @param result 原生的后台返回结果
     */
    void on_success(String result);

    /**
     * 请求失败，该方法被调用
     * @param error 具体的失败信息
     */
    void on_failed(String error);
}
