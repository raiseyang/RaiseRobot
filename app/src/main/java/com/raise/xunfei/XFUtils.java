package com.raise.xunfei;

/**
 * Created by raise_yang on 2015/9/29.
 */
public class XFUtils {

    private XFUtils() {
    }

    /**
     * y22222ly@163.com下的appid
     */
    public static final String APP_ID = "5609fc34";

    //青年女声
    public static final String COMPOSITE_VOICE_XIAOYAN = "xiaoyan";
    //青年男声
    public static final String COMPOSITE_VOICE_XIAOYU = "xiaoyu";
    //青年女声 中英文（粤语）
    public static final String COMPOSITE_VOICE_XIAOMEI = "xiaomei";
    //青年女声 汉语（四川话）
    public static final String COMPOSITE_VOICE_XIAORONG = "xiaorong";
    //青年女生 汉语（东北话）
    public static final String COMPOSITE_VOICE_XIAOQIAN = "xiaoqian";
    //童年男声
    public static final String COMPOSITE_VOICE_XIAOXIN = "xiaoxin";
    //童年女声
    public static final String COMPOSITE_VOICE_NANNAN = "nannan";
    //老年男声
    public static final String COMPOSITE_VOICE_VILS = "vils";

    public static final String GRAMMAR_PHOTO_ORDER = "#ABNF 1.0 UTF-8;" +
            "languagezh-CN; " +
            "mode voice; " +
            "root $main; " +
            "$main = 拍[个]照 ; ";
//            +
//            "$place1 = 北京 | 武汉 | 南京 | 天津 | 天京 | 东京; " +
//            "$place2 = 上海 | 合肥; ";


}
