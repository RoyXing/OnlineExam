package com.onlineexamination.config;

/**
 * Created by 庞品 on 2017/2/15.
 */

public class Config {
    //主URL
    private static String URL = "http://115.159.100.155:8080/courseteach";

    //注册接口
    public static String RIGISTER = URL + "/user/rigister";

    //登录接口
    public static String LOGIN = URL + "/user/login";

    //话题列表
    public static String TOPIC_LIST = URL + "/topic/list";

    //我的发帖
    public static String MY_WRITE=URL+"/topic/list";

    //与我相关
    public static String MY_COMMINT=URL+"/topic/join";

    //话题详情+ID
    public static String TOPIC = URL + "/topic/";

    //话题评论
    public static String ADD_COMMENT_TOPIC = URL + "/comment/add";

    //增加话题
    public static String ADD_TOPIC = URL + "/topic/add";

    //个人信息修改
    public static String UP_PERSON_MESSAGE = URL + "/user/update";

    //图片上传
    public static String UP_FILE = URL + "/upload ";
}
