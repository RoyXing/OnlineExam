package com.onlineexamination.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.onlineexamination.R;
import com.onlineexamination.bean.UserBean;
import com.onlineexamination.config.Config;
import com.onlineexamination.utils.JsonToBean;
import com.onlineexamination.utils.SharedPreferencesDB;
import com.onlineexamination.utils.ToastUtils;
import com.onlineexamination.view.TitleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;


public class LoginActivity extends Activity implements  View.OnClickListener {

    EditText editEmail;
    EditText editPassword;
    TitleView mToolbar;
    Button btnLogin;
    TextView textRegi,textSelect;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_login);

        initView();
        initEvent();
    }

    private void initView() {

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btn_login);
        mToolbar = (TitleView) findViewById(R.id.toolbar);
        editEmail.setSelection(editEmail.getText().length());
        editPassword.setSelection(editPassword.getText().length());
        textRegi=(TextView) findViewById(R.id.tv_zhuce);
        textSelect=(TextView) findViewById(R.id.tv_wangji);
    }

    private void initEvent() {
        mToolbar.setCustomTitle("登录");
        mToolbar.isShowLeftImage(false);
        btnLogin.setOnClickListener(this);
        textRegi.setOnClickListener(this);
        textSelect.setOnClickListener(this);
    }


    public void onComplete() {
        SharedPreferencesDB.getInstance().setBoolean("isLogin", true);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(editEmail.getText().toString())) {
                    ToastUtils.show(LoginActivity.this, "请输入你的账号");
                } else if (TextUtils.isEmpty(editPassword.getText().toString())) {
                    ToastUtils.show(LoginActivity.this, "请输入你的密码");
                } else {
                    login(editEmail.getText().toString(),editPassword.getText().toString());
                }
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.tv_zhuce:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_wangji:
                ToastUtils.toast("请联系系统管理员！");
                break;
        }
    }
    public void login(final String userName, String password) {
        OkHttpUtils
                .post()
                .url(Config.LOGIN)
                .addParams("name", userName)
                .addParams("password", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.toast("服务器错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optInt("code") == 10000) {
                                UserBean userBean = JsonToBean.getBean(jsonObject.optString("response").toString(), UserBean.class);
                                SharedPreferencesDB.getInstance().setString("userid", userBean.getId());
                                SharedPreferencesDB.getInstance().setString("account_id", userBean.getAccount_id());
                                SharedPreferencesDB.getInstance().setString("username", userBean.getName());
                                SharedPreferencesDB.getInstance().setString("userimgae", Config.URL+"/"+userBean.getImgUrl());
                                SharedPreferencesDB.getInstance().setString("usersex", userBean.getSex());
                                SharedPreferencesDB.getInstance().setString("userbirth", userBean.getBirthday());
                                SharedPreferencesDB.getInstance().setString("useraccout", userBean.getStudentId());
                                SharedPreferencesDB.getInstance().setString("usermajor", userBean.getMajoy());
                                SharedPreferencesDB.getInstance().setString("usercollege", userBean.getCollege());
                                SharedPreferencesDB.getInstance().setString("phone", userBean.getPhone());
                                SharedPreferencesDB.getInstance().setString("email",userBean.getEmail());

                                onComplete();
                            } else {
                                ToastUtils.toast("验证失败！");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.toast("账号或密码出错");
                        }
                    }
                });
    }
}
