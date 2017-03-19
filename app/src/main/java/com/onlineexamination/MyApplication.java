package com.onlineexamination;

import android.app.Application;

import com.onlineexamination.bean.ExamQuestionBean;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by 庞品 on 2017/2/8.
 */

public class MyApplication extends Application {
    private static MyApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    /**
     * 网络请求封装，okhttputils  张鸿洋的
     * 使用方法请看以下网页介绍
     * http://blog.csdn.net/lmj623565791/article/details/49734867/
     */
    private void init() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

}
