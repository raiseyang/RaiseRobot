package com.raise.domain;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by raise on 2015-12-29.
 */
public class DeviceInfo {

    private static DeviceInfo instance = new DeviceInfo();

    private static DisplayMetrics metrics;

    private DeviceInfo() {
    }

    public DeviceInfo getInstance() {
        return instance;
    }

    public static void init(Context context) {
        metrics = context.getResources().getDisplayMetrics();
    }

    public static int getHeightPx() {
        return metrics.heightPixels;
    }

    public static int getWidthPx() {
        return metrics.widthPixels;
    }

    public static int getXDpi() {
        return (int) metrics.xdpi;
    }

    public static int getYDpi() {
        return (int) metrics.ydpi;
    }

    public static int getDensityDpi() {
        return metrics.densityDpi;
    }

    /**
     * 相对于160:1的密度值
     *
     * @return
     */
    public static int getDensity() {
        return (int) metrics.density;
    }

    public static String to_string() {
        return String.format("宽度px:%s,高度px:%s,宽度dpi:%s,高度dpi:%s,密度:%s,斜边dpi:%s",
                getWidthPx(), getHeightPx(), getXDpi(), getYDpi(), getDensity(), getDensityDpi());
    }
}
