package com.iitdh.sonusourav.instigo.Resources;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class CourseClass implements Serializable {

  @SerializedName("courseCode")
  private String courseCode;
  @SerializedName("courseName")
    private String courseName;
  @SerializedName("__v")
  private String __v;
  @SerializedName("_id")
  private String _id;
  @SerializedName("branch")
  private ArrayList<String> branch;
  @SerializedName("documents")
  private ArrayList<String> documents;

  public CourseClass(String courseCode, String courseName, String __v, String _id,
      ArrayList<String> branch, ArrayList<String> documents) {

    this.courseCode = courseCode;
    this.courseName=courseName;
    this.__v = __v;
    this._id = _id;
    this.branch = branch;
    this.documents = documents;
  }

  public CourseClass(String courseCode, String courseName, ArrayList<String> branch) {

    this.courseCode = courseCode;
        this.courseName=courseName;
        this.branch=branch;

    }

    public CourseClass(){}

  public String getCourseCode() {
    return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

  public String get__v() {
    return __v;
    }

  public String get_id() {
    return _id;
    }

  public ArrayList<String> getBranch() {
        return branch;
    }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

  public void set__v(String __v) {
    this.__v = __v;
    }

  public void set_id(String _id) {
    this._id = _id;
    }

  public void setBranch(ArrayList<String> branch) {
        this.branch = branch;
    }

  public ArrayList<String> getDocuments() {
    return documents;
  }

  public void setDocuments(ArrayList<String> documents) {
    this.documents = documents;
  }
}
