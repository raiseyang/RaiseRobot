package com.raise.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by raise_yang on 2015/9/29.
 */
public class ConversationInfo {

    private String rc;//receive
    private String service;// 问答服务提供者：比如：baike openQA App
    private String operation;// ANSWER LAUNCH
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

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
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
}
