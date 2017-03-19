package com.onlineexamination.config;

/**
 * Created by 庞品 on 2017/2/15.
 */

public class Config {
    //主URL
    public static String URL = "http://115.159.100.155:8080/OnlineTest";

    //注册接口
    public static String RIGISTER = URL + "/user/rigister";

    //登录接口
    public static String LOGIN = URL + "/user/login";

    //修改密码
    public static String RW_PASSWORD=URL+"/user/modify";

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
    public static String UP_FILE = URL + "/user/upload";

    //获取试卷种类
    public static String EXAM_TYPE=URL+"/paperType/getPaperType";

    //根据试卷分类id获取对应试题列表
    public static String EXAM_LIST=URL+"/paper/getPaperListByPaperType";

    //数据模糊搜索
    public static String EXAM_SEARCH=URL+"/paper/searchPaperByKeyword";

    //根据试卷id获取试卷内容
    public static String EXAM_CONTENT=URL+"/question/getQuestionListByPaper_id";

    //根据学生id获取已经考过的试卷
    public static String TAKE_EXAM_LIST=URL+"/alreadyTest/getAlreadyTestListBySid";

    //考试成绩集合
    public static String ACHIEVEMEN=URL+"/score/getScoreListBySid";

    //考试成绩提交
    public static String SCORE=URL+"/user/score";
}
