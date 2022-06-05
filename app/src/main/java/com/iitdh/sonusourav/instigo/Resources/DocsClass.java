package com.iitdh.sonusourav.instigo.Resources;

public class DocsClass {

    private String courseNo;
    private String courseName;
    private String dateCreated;
    private String userCreated;
    private String branch;
    private String topic;
    private String subTopic;
    private String type;
    private String imageUrl;



    public DocsClass(String topic, String subTopic, String type){

        this.topic=topic;
        this.subTopic=subTopic;
        this.type=type;

    }

    public DocsClass(String courseNo, String courseName, String dateCreated, String userCreated, String branch, String topic, String subTopic, String type){

        this.courseNo=courseNo;
        this.courseName=courseName;
        this.dateCreated=dateCreated;
        this.userCreated=userCreated;
        this.branch=branch;
        this.topic=topic;
        this.subTopic=subTopic;
        this.type=type;

    }

    public DocsClass(String courseNo, String courseName, String dateCreated, String userCreated, String branch, String topic, String subTopic, String type,String imageUrl){

        this.courseNo=courseNo;
        this.courseName=courseName;
        this.dateCreated=dateCreated;
        this.userCreated=userCreated;
        this.branch=branch;
        this.topic=topic;
        this.subTopic=subTopic;
        this.type=type;
        this.imageUrl=imageUrl;

    }

    public DocsClass(){}




    public String getCourseNo() {
        return courseNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getUserCreated() {
        return userCreated;
    }

    public String getBranch() {
        return branch;
    }

    public String getTopic() {
        return topic;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public String getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
