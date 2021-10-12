package com.example.eventmanagementapplication.Models;

public class FeedbackModel {
    String feedbackID,userEmail,feedback,qualityOfWork,valueOfMoney,grandDecor,professionalism,uniqueIdea,classyAndElegent,Accomodating,onTimeService;

    public FeedbackModel(String feedbackID,String userEmail,String feedback, String qualityOfWork, String valueOfMoney, String grandDecor, String professionalism, String uniqueIdea, String classyAndElegent, String accomodating, String onTimeService) {
        this.feedbackID = feedbackID;
        this.userEmail = userEmail;
        this.feedback = feedback;
        this.qualityOfWork = qualityOfWork;
        this.valueOfMoney = valueOfMoney;
        this.grandDecor = grandDecor;
        this.professionalism = professionalism;
        this.uniqueIdea = uniqueIdea;
        this.classyAndElegent = classyAndElegent;
        Accomodating = accomodating;
        this.onTimeService = onTimeService;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getQualityOfWork() {
        return qualityOfWork;
    }

    public void setQualityOfWork(String qualityOfWork) {
        this.qualityOfWork = qualityOfWork;
    }

    public String getValueOfMoney() {
        return valueOfMoney;
    }

    public void setValueOfMoney(String valueOfMoney) {
        this.valueOfMoney = valueOfMoney;
    }

    public String getGrandDecor() {
        return grandDecor;
    }

    public void setGrandDecor(String grandDecor) {
        this.grandDecor = grandDecor;
    }

    public String getProfessionalism() {
        return professionalism;
    }

    public void setProfessionalism(String professionalism) {
        this.professionalism = professionalism;
    }

    public String getUniqueIdea() {
        return uniqueIdea;
    }

    public void setUniqueIdea(String uniqueIdea) {
        this.uniqueIdea = uniqueIdea;
    }

    public String getClassyAndElegent() {
        return classyAndElegent;
    }

    public void setClassyAndElegent(String classyAndElegent) {
        this.classyAndElegent = classyAndElegent;
    }

    public String getAccomodating() {
        return Accomodating;
    }

    public void setAccomodating(String accomodating) {
        Accomodating = accomodating;
    }

    public String getOnTimeService() {
        return onTimeService;
    }

    public void setOnTimeService(String onTimeService) {
        this.onTimeService = onTimeService;
    }
}

