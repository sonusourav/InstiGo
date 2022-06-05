package com.iitdh.sonusourav.instigo.Mess;


public class FeedbackUserClass {
    private String feedbackUsername;
    private String feedbackUri;
    private String feedbackTitle;
    private String feedbackDay;
    private String feedbackPart;
    private String feedbackDate;
    private String feedbackRatings;
    private String feedbackDesc;

    FeedbackUserClass(String feedbackUsername, String feedbackUri, String feedbackTitle, String feedbackDay,
                      String feedbackPart, String feedbackDate, String feedbackRatings, String feedbackDesc) {
        this.feedbackUsername = feedbackUsername;
        this.feedbackUri = feedbackUri;
        this.feedbackTitle = feedbackTitle;
        this.feedbackDay = feedbackDay;
        this.feedbackPart = feedbackPart;
        this.feedbackDate =feedbackDate;
        this.feedbackRatings =feedbackRatings;
        this.feedbackDesc=feedbackDesc;
    }

    public FeedbackUserClass(){

    }


    public String getFeedbackUsername() {
        return feedbackUsername;
    }

    public void setFeedbackUsername(String username) {
        this.feedbackUsername = username;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public String getFeedbackDay() {
        return feedbackDay;
    }

    public void setFeedbackDay(String feedbackDay) {
        this.feedbackDay = feedbackDay;
    }

    public String getFeedbackUri() {
        return feedbackUri;
    }

    public void setFeedbackUri(String feedbackUri) {
        this.feedbackUri = feedbackUri;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getFeedbackPart() {
        return feedbackPart;
    }

    public void setFeedbackPart(String feedbackPart) {
        this.feedbackPart = feedbackPart;
    }

    public String getFeedbackRatings() {
        return feedbackRatings;
    }

    public void setFeedbackRatings(String feedbackRatings) {
        this.feedbackRatings = feedbackRatings;
    }

    public String getFeedbackDesc() {
        return feedbackDesc;
    }

    public void setFeedbackDesc(String feedbackDesc) {
        this.feedbackDesc = feedbackDesc;
    }
}

