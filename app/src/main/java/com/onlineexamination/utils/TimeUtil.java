package com.onlineexamination.utils;

import java.util.Date;

/**
 * Created by 庞品 on 2017/2/11.
 */

public class TimeUtil {
    public static String getDate() {
        String info = "";
        Date date = new Date();
        int hour = date.getHours();
        if (hour < 6) {
            info = "凌晨好";
        } else if (hour < 9) {
            info = "早上好";
        } else if (hour < 12) {
            info = "上午好";
        } else if (hour < 14) {
            info = "中午好";
        } else if (hour < 17) {
            info = "下午好";
        } else if (hour < 19) {
            info = "傍晚好";
        } else if (hour < 23) {
            info = "晚上好";
        } else {
            info = "午夜好";
        }
        return info;
    }
}
