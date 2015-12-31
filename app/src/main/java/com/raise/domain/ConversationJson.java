package com.raise.domain;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by raise_yang on 2015/9/29.
 */
public class ConversationJson {

    private ArrayList<ConversationInfo> moreResults;

    private int rc;//receive
    private String service;// 问答服务提供者：比如：baike openQA
    private String operation;//
    @SerializedName("text")
    private String question;// question
    private Answer answer;// answer

    private Error error;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public ArrayList<ConversationInfo> getMoreResults() {
        return moreResults;
    }

    public void setMoreResults(ArrayList<ConversationInfo> moreResults) {
        this.moreResults = moreResults;
    }
}
