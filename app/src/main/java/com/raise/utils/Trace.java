package com.raise.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Created by y2222 on 2015-12-29.
 */
public class Trace {

    //典型的单例模式写法
    private static Logger instance;

    static {
        instance = Logger.getLogger(Trace.class.getSimpleName());
    }

    private Trace() {
    }

    /**
     * 配置日志
     * 日志文件目录等
     *
     * @param compile_level
     * @param filename
     */
    public static void init(String compile_level, String filename) {
        LogConfigurator configurator = new LogConfigurator();
        configurator.setFileName(filename);
        //设置编译级别，可以根据当前模式来设置
        if (compile_level.equals("debug"))
            //debug及以上级别的消息会被打印
            configurator.setRootLevel(Level.DEBUG);
        else
            //info及以上级别的消息会被打印
            configurator.setRootLevel(Level.INFO);

        configurator.setLevel("org.apache", Level.ERROR);
        //%p 表示日志语句的优先级
        //%c 日志信息所在的类名
        //%m%n 表示日志信息的内容
        configurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
        configurator.setMaxFileSize(1024 * 1024 * 1);
        configurator.setMaxBackupSize(3);
        configurator.setImmediateFlush(true);
        //很重要的一步，配置configure
        configurator.configure();
    }

    public static void d(String msg) {
        instance.debug(msg);
    }

    public static void d(String tag, String msg) {
        Logger.getLogger(tag).debug(msg);
    }

    public static void i(String msg) {
        instance.info(msg);
    }

    public static void i(String tag, String msg) {
        Logger.getLogger(tag).info(msg);
    }

    public static void e(String msg) {
        instance.error(msg);
    }

    public static void e(String tag, String msg) {
        Logger.getLogger(tag).error(msg);
    }


}
