package com.onlineexamination.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineexamination.MyApplication;
import com.onlineexamination.R;

/**
 * @author:jeney
 * @date:2015/5/12
 * @todo:显示Toast的工具类
 */
public class ToastUtils {
    public static void show(Context context, String content) {
        if (null != context) {
            Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void show(Context context, int content) {
        if (null != context) {
            Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void toast(String str) {
        Toast.makeText(MyApplication.getInstance(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 自定义形式的toast
     * @param str
     */
    public static void toastTip(String str){
        View view= LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.toast_view,null);
        TextView textView=(TextView) view.findViewById(R.id.text_tip);
        textView.setText(str);
        Toast toastTest=new Toast(MyApplication.getInstance());
        toastTest.setView(view);
        toastTest.setGravity(Gravity.CENTER,0,0);
        toastTest.setDuration(Toast.LENGTH_SHORT);
        toastTest.show();
    }
}

