package com.iitdh.sonusourav.instigo.Complaints;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class ComplainItemClass implements Serializable {

  @SerializedName("requestorUrl")
  private String complainPicUrl;
  @SerializedName("_id")
  private String _id;
  @SerializedName("__v")
  private int __v;
  @SerializedName("requestId")
  private String complainId;
  @SerializedName("requestorEmail")
  private String complainEmail;
  @SerializedName("requestorName")
  private String complainUsername;
  @SerializedName("requestName")
    private String complainTitle;
  @SerializedName("requestDesc")
  private String complainDesc;
  @SerializedName("houseNo")
  private int complainHouseNo;
  @SerializedName("hostelNo")
  private int complainHostel;
  @SerializedName("related")
  private String complainRelated;
  @SerializedName("dateCreated")
  private String complainDateCreated;
  @SerializedName("isPrivate")
  private boolean isPrivate;
  @SerializedName("isPriority")
  private boolean isPriority;
  @SerializedName("status")
  private int complainStatus;
  @SerializedName("comments")
  private ArrayList<CommentClass> comments;
  @SerializedName("statusDate")
  private ArrayList<String> statusDate;
  @SerializedName("trackStatus")
  private ArrayList<String> trackStatus;
    public  ComplainItemClass(){

    }

  ComplainItemClass(String _id, int __v, String complainId, String complainPicUrl,
      String complainEmail, String complainUsername, String complainTitle,
      String complainDesc, int complainStatus, int complainHouseNo, int complainHostel,
      String complainRelated, String complainDateCreated,
      boolean isPrivate, boolean isPriority, ArrayList<CommentClass> comments,
      ArrayList<String> trackStatus, ArrayList<String> statusDate) {

    this._id = _id;
    this.__v = __v;
    this.complainId = complainId;
    this.complainPicUrl = complainPicUrl;
    this.complainEmail = complainEmail;
    this.complainUsername = complainUsername;
    this.complainTitle = complainTitle;
    this.complainDesc = complainDesc;
    this.complainStatus = complainStatus;
    this.complainHouseNo = complainHouseNo;
    this.complainHostel = complainHostel;
    this.complainRelated = complainRelated;
    this.isPriority = isPriority;
    this.isPrivate = isPrivate;
    this.comments = comments;
    this.complainDateCreated = complainDateCreated;
    this.trackStatus = trackStatus;
    this.statusDate = statusDate;
  }

  ComplainItemClass(String complainUsername, String complainTitle, String complainDesc,
      int complainHostel, int complainHouseNo, String complainRelated,
      boolean isPriority, boolean complainIsPrivate) {
    this.complainUsername = complainUsername;
    this.complainTitle = complainTitle;
    this.complainDesc = complainDesc;
        this.complainHostel=complainHostel;
        this.complainHouseNo=complainHouseNo;
    this.complainRelated = complainRelated;
    this.isPriority = isPriority;
    this.isPrivate = complainIsPrivate;
    }

  ComplainItemClass(String complainPicUrl, String complainUsername, String complainTitle,
      String complainDesc, int complainStatus, int complainHostel,
      int complainHouseNo, String complainRelated, boolean isPriority, boolean complainIsPrivate,
      ArrayList<String> trackStatus, ArrayList<String> statusDate) {
    this.complainPicUrl = complainPicUrl;
        this.complainUsername=complainUsername;
    this.complainTitle = complainTitle;
    this.complainDesc = complainDesc;
    this.complainStatus = complainStatus;
        this.complainHostel=complainHostel;
        this.complainHouseNo=complainHouseNo;
    this.complainRelated = complainRelated;
    this.isPriority = isPriority;
    this.isPrivate = complainIsPrivate;
    this.trackStatus = trackStatus;
    this.statusDate = statusDate;
    }

  public String getComplainId() {
    return complainId;
    }

  public void setComplainId(String complainId) {
    this.complainId = complainId;
  }

  String getComplainEmail() {
        return complainEmail;
    }

  String getComplainUsername() {
        return complainUsername;
    }

  String getComplainRelated() {
    return complainRelated;
    }

  String getComplainTitle() {
        return complainTitle;
    }

  public int getComplainHostel() {
        return complainHostel;
    }

  public int getComplainHouseNo() {
        return complainHouseNo;
    }

  public void setComplainHostel(int complainHostel) {
        this.complainHostel = complainHostel;
    }

  public void setComplainHouseNo(int complainHouseNo) {
        this.complainHouseNo = complainHouseNo;
    }

  String getComplainDesc() {
    return complainDesc;
    }

  boolean isPrivate() {
    return isPrivate;
    }

  public void setPrivate(boolean aPrivate) {
    this.isPrivate = aPrivate;
    }

    public void setComplainEmail(String complainEmail) {
        this.complainEmail = complainEmail;
    }

    public void setComplainUsername(String complainUsername) {
        this.complainUsername = complainUsername;
    }

  public void setComplainRelated(String complainRelated) {
    this.complainRelated = complainRelated;
    }

    public void setComplainTitle(String complainTitle) {
        this.complainTitle = complainTitle;
    }

  public void setComplainDesc(String complainDesc) {
    this.complainDesc = complainDesc;
  }

  String getComplainDateCreated() {
    return complainDateCreated;
  }

  public void setComplainDateCreated(String complainDateCreated) {
    this.complainDateCreated = complainDateCreated;
  }

  public boolean isPriority() {
    return isPriority;
    }

  public void setPriority(boolean priority) {
    this.isPriority = priority;
    }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
    }

  public int get__v() {
    return __v;
  }

  public void set__v(int __v) {
    this.__v = __v;
  }

  public ArrayList<CommentClass> getComments() {
    return comments;
  }

  public void setComments(ArrayList<CommentClass> comments) {
    this.comments.addAll(comments);
  }

  int getComplainStatus() {
    return complainStatus;
  }

  public void setComplainStatus(int complainStatus) {
    this.complainStatus = complainStatus;
  }

  public String getComplainPicUrl() {
    return complainPicUrl;
  }

  public void setComplainPicUrl(String complainPicUrl) {
    this.complainPicUrl = complainPicUrl;
  }

  public ArrayList<String> getStatusDate() {
    return statusDate;
  }

  public void setStatusDate(ArrayList<String> statusDate) {
    this.statusDate = statusDate;
  }

  public ArrayList<String> getTrackStatus() {
    return trackStatus;
  }

  public void setTrackStatus(ArrayList<String> trackStatus) {
    this.trackStatus = trackStatus;
  }
}
