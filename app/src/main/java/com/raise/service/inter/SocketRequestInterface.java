package com.raise.service.inter;

/**
 * Created by raise_yang on 2015/10/13.
 */
public interface SocketRequestInterface {

    // 连接云台
    void connection(SocketCallBack callBack);

    // 得到总的结点位置个数
    void getSumNode(SocketCallBack callBack);

    // 移动到目标位置
    void moveToTargetLocation(SocketCallBack callBack, String location);

    // 获得当前的位置
    void getCurrentLocation(SocketCallBack callBack);

    // 设置当前为A点位置
    void setCurrentLocation(SocketCallBack callBack, String location);

    void stopServer(SocketCallBack callBack);

    void startServer(SocketCallBack callBack);
}
