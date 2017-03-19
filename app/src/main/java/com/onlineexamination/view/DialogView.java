package com.onlineexamination.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlineexamination.R;


public class DialogView extends Dialog {

    public static final int DEFAULT_STYLE = R.style.DialogTheme;

    Context context;
    private TextView textView;
    private ImageView xuanzhuan;

    public DialogView(Context context) {
        this(context, DEFAULT_STYLE);
    }

    public DialogView(Context context, int style) {
        super(context, style);
        this.context = context;
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        xuanzhuan = (ImageView) findViewById(R.id.xuanzhuan);
        textView = (TextView) findViewById(R.id.text_message);
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.xuanzhuan_anim);
        LinearInterpolator lir=new LinearInterpolator();
        anim.setInterpolator(lir);
        xuanzhuan.startAnimation(anim);
    }

    /**
     * 调改方法前 请先调show方法
     *
     * @param message
     */
    public void setMessage(String message) {
        textView.setText(message);
    }

    public void setMessage(int resId) {
        textView.setText(resId);
    }

    public void close() {
        if (this != null && this.isShowing()) {
            dismiss();
        }
    }
}
