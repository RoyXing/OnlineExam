package com.onlineexamination.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.onlineexamination.R;


public class DialogNoData extends Dialog {

    public static final int DEFAULT_STYLE = R.style.DialogTheme;

    Context context;
    public TextView textView, message;


    public DialogNoData(Context context) {
        this(context, DEFAULT_STYLE);
    }

    public DialogNoData(Context context, int style) {
        super(context, style);
        this.context = context;
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_no_data);
        textView = (TextView) findViewById(R.id.text_click);
        message = (TextView) findViewById(R.id.message);

    }

    /**
     * 调改方法前 请先调show方法
     */
    public void setMessage(String message) {
        this.message.setText(message);
    }

    public void close() {
        if (this != null && this.isShowing()) {
            dismiss();
        }
    }
}
