package com.onlineexamination.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by roy on 2017/1/6.
 * 时间转换
 */

public class TimeUtils {
    /**
     * lang时间转换
     */
    public static String longToString(String time){
        //服务器返回时间
        long currentTime = Long.parseLong(time);
        //构造方法内可以自定义显示格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(currentTime*1000);
        //指定格式的时间串
        String times = dateFormat.format(date);
        return times;
    }
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

    /**
     * 计时使用
     */
    public static String countTime(long count){
        String time="";
        if (count<=59){
            time=count+" 秒";
        }else {
            long second=count%60;
            long min=count/60;
            if (min<=59){
                time=min+" 分 "+second+" 秒";
            }else {
                long hour=min/60;
                time=hour+" 时 "+min+" 分 "+second+" 秒";
            }
        }
        return time;
    }

}
