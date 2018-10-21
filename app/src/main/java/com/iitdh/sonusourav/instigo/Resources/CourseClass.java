package com.iitdh.sonusourav.instigo.Resources;

public class CourseClass {

    private String courseNo;
    private String courseName;
    private String dateCreated;
    private String userCreated;
    private String branch;


    public CourseClass(String courseNo,String courseName){

        this.courseNo=courseNo;
        this.courseName=courseName;

    }

    public CourseClass(String courseNo,String courseName,String dateCreated,String userCreated,String branch){

        this.courseNo=courseNo;
        this.courseName=courseName;
        this.dateCreated=dateCreated;
        this.userCreated=userCreated;
        this.branch=branch;

    }

    public CourseClass(){}




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
}
