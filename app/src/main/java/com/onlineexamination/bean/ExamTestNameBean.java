package com.onlineexamination.bean;

/**
 * Created by 庞品 on 2017/3/4.
 */

public class ExamTestNameBean {
    public ExamTestNameBean(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }

    private String id;
    private String paperType_id;
    private String teacher_id;
    private String name;
    private String state;
    private String grade;
    private String createTime;
    private String questionList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaperType_id() {
        return paperType_id;
    }

    public void setPaperType_id(String paperType_id) {
        this.paperType_id = paperType_id;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getQuestionList() {
        return questionList;
    }

    public void setQuestionList(String questionList) {
        this.questionList = questionList;
    }
}
