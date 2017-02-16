package com.onlineexamination.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.onlineexamination.R;
import com.onlineexamination.config.Config;
import com.onlineexamination.utils.ToastUtils;
import com.onlineexamination.view.TitleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;


/**
 * Created by roy on 2017/1/15.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText register_nickname;
    private EditText register_account;
    private EditText register_password;
    private Button btnRegister;
    private TitleView mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_layout);
        initView();
        initEvent();
    }

    private void initView() {
        register_nickname = (EditText) findViewById(R.id.register_nickname);
        register_account = (EditText) findViewById(R.id.register_account);
        register_password = (EditText) findViewById(R.id.register_password);
        btnRegister = (Button) findViewById(R.id.btn_login);
        mToolbar = (TitleView) findViewById(R.id.toolbar);
        btnRegister.setOnClickListener(this);
    }

    private void initEvent() {
        mToolbar.setCustomTitle("注册");
        mToolbar.isShowLeftImage(true);
        mToolbar.setLeftImageOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(register_nickname.getText().toString())) {
                    ToastUtils.toast("请设置你的昵称");
                } else if (TextUtils.isEmpty(register_account.getText().toString())) {
                    ToastUtils.toast("请输入你设置的账号");
                } else if (TextUtils.isEmpty(register_password.getText().toString())) {
                    ToastUtils.toast("请输入你设置的密码");
                } else {
                    register(register_nickname.getText().toString(), register_account.getText().toString(), register_password.getText().toString());
                }
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }


    /**
     * phone,password,userName
     *
     * @param nickName
     * @param userName
     * @param password
     */
    public void register(String nickName, String userName, String password) {

        OkHttpUtils.post()
                .url(Config.RIGISTER)
                .addParams("userName", nickName)
                .addParams("phone", userName)
                .addParams("password", password)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.toast("服务器错误！");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("roy", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("code") == 10000 && jsonObject.optString("info").equals("success")) {
                        ToastUtils.toast("注册成功，请登陆");
                        finish();
                    } else {
                        ToastUtils.toast("注册失败！");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
