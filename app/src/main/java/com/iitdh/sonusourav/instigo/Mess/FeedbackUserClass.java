package com.iitdh.sonusourav.instigo.Mess;


public class FeedbackUserClass {
  private String username;
  private String url;
  private String title;
  private String day;
  private String part;
  private String date;
  private float ratings;
  private String _id;
  private int __v;
  private String desc;

  FeedbackUserClass(String feedbackDate, String feedbackDay, String feedbackPart, float ratings,
      String _id, String desc, String feedbackTitle, String feedbackUsername, String feedbackUri,
      int __v) {
    this.username = feedbackUsername;
    this.url = feedbackUri;
    this.title = feedbackTitle;
    this.day = feedbackDay;
    this.part = feedbackPart;
    this.date = feedbackDate;
    this.ratings = ratings;
    this._id = _id;
    this.desc = desc;
    this.__v = __v;
  }

  FeedbackUserClass(float ratings, String title, String part, String desc, String day) {
    this.title = title;
    this.day = day;
    this.part = part;
    this.ratings = ratings;
    this.desc = desc;
    }

    public FeedbackUserClass(){

    }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public String getDay() {
    return day;
    }

  public void setDay(String day) {
    this.day = day;
    }

  public String getUrl() {
    return url;
    }

  public void setUrl(String url) {
    this.url = url;
    }

  public String getDate() {
    return date;
    }

  public void setDate(String date) {
    this.date = date;
    }

  public String getPart() {
    return part;
    }

  public void setPart(String part) {
    this.part = part;
    }

  public float getRatings() {
    return ratings;
    }

  public void setRatings(float ratings) {
    this.ratings = ratings;
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

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
    }
}

