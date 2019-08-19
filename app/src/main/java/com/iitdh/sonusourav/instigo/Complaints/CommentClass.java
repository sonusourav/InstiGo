package com.iitdh.sonusourav.instigo.Complaints;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class CommentClass implements Serializable {

  @SerializedName("comment")
  private String comment;
  @SerializedName("_id")
  private String _id;
  @SerializedName("__v")
  private String __v;
  @SerializedName("by")
  private String commentBy;
  @SerializedName("date")
  private String commentDate;
  @SerializedName("email")
  private String commentEmail;
  @SerializedName("url")
  private String picUrl;

  CommentClass(String _id, String  __v,String comment, String commentBy, String commentDate, String commentEmail,String picUrl){
    this._id=_id;
    this.__v=__v;
    this.comment=comment;
    this.commentBy=commentBy;
    this.commentDate=commentDate;
    this.commentEmail=commentEmail;
    this.picUrl=picUrl;
  }

  CommentClass(String comment, String commentBy, String commentDate, String commentEmail,String picUrl){
    this.comment=comment;
    this.commentBy=commentBy;
    this.commentDate=commentDate;
    this.commentEmail=commentEmail;
    this.picUrl=picUrl;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String get__v() {
    return __v;
  }

  public void set__v(String __v) {
    this.__v = __v;
  }

  public String getCommentBy() {
    return commentBy;
  }

  public void setCommentBy(String commentBy) {
    this.commentBy = commentBy;
  }

  public String getCommentDate() {
    return commentDate;
  }

  public void setCommentDate(String commentDate) {
    this.commentDate = commentDate;
  }

  public String getCommentEmail() {
    return commentEmail;
  }

  public void setCommentEmail(String commentEmail) {
    this.commentEmail = commentEmail;
  }

  public String getPicUrl() {
    return picUrl;
  }

  public void setPicUrl(String picUrl) {
    this.picUrl = picUrl;
  }
}
