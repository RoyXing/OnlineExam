package com.onlineexamination.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.onlineexamination.R;
import com.onlineexamination.utils.SharedPreferencesDB;

/**
 * Created by 庞品 on 2017/2/15.
 */

public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);//休眠2秒
                    if (SharedPreferencesDB.getInstance().getBoolean("isLogin", false)) {
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    finish();
                }catch (Exception e){}

            }
        }.start();
    }
}
