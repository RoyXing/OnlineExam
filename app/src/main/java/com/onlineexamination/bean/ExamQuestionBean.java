package com.onlineexamination.bean;

import java.util.List;

/**
 * Created by 庞品 on 2017/3/14.
 */

public class ExamQuestionBean {
    private String id;
    private String paper_id;
    private String title;
    private String answer;
    private String selectAnswer;
    private String grade;
    private String createTime;
    private List<QuestionSelectBean> optionList;

    public String getSelectAnswer() {
        return selectAnswer;
    }

    public void setSelectAnswer(String selectAnswer) {
        this.selectAnswer = selectAnswer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(String paper_id) {
        this.paper_id = paper_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public List<QuestionSelectBean> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<QuestionSelectBean> optionList) {
        this.optionList = optionList;
    }

    @Override
    public String toString() {
        return "ExamQuestionBean{" +
                "selectAnswer='" + selectAnswer + '\'' +
                '}';
    }
}
