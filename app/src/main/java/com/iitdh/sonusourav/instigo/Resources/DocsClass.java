package com.iitdh.sonusourav.instigo.Resources;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class DocsClass implements Serializable {

  @SerializedName("date")
  private String date;
  @SerializedName("_id")
  private String _id;
  @SerializedName("docTitle")
  private String docTitle;
  @SerializedName("desc")
  private String desc;
  @SerializedName("prof")
  private String prof;
  @SerializedName("by")
  private String by;
  @SerializedName("url")
  private String url;
  @SerializedName("file")
  private String file;
  @SerializedName("type")
    private String type;
  @SerializedName("path")
  private String path;
  @SerializedName("__v")
  private int __v;

  public DocsClass(String docTitle, String desc, String prof) {

    this.docTitle = docTitle;
    this.desc = desc;
    this.prof = prof;
  }

  public DocsClass(String date, String _id, String docTitle, String desc, String prof, String by,
      String url, String file, String type, String path, int __v) {

    this.date = date;
    this._id = _id;
    this.docTitle = docTitle;
    this.desc = desc;
    this.prof = prof;
    this.by = by;
    this.url = url;
    this.file = file;
        this.type=type;
    this.path = path;
    this.__v = __v;

    }

  public DocsClass(String date, String docTitle, String desc, String prof, String by, String url,
      String file, String type, String path) {

    this.date = date;
    this.docTitle = docTitle;
    this.desc = desc;
    this.prof = prof;
    this.by = by;
    this.url = url;
    this.file = file;
        this.type=type;
    this.path = path;

    }


    public DocsClass(){}

  public String getDate() {
    return date;
    }

  public String getBy() {
    return by;
    }

  public String getFile() {
    return file;
    }

  public String getDocTitle() {
    return docTitle;
    }

  public String getPath() {
    return path;
    }

    public String getType() {
        return type;
    }

  public String getUrl() {
    return url;
    }

  public void setDate(String date) {
    this.date = date;
    }

  public void setBy(String by) {
    this.by = by;
    }

  public void setFile(String file) {
    this.file = file;
    }

  public void setDocTitle(String docTitle) {
    this.docTitle = docTitle;
    }

  public void setPath(String path) {
    this.path = path;
    }

    public void setType(String type) {
        this.type = type;
    }

  public void setUrl(String url) {
    this.url = url;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getProf() {
    return prof;
  }

  public void setProf(String prof) {
    this.prof = prof;
  }

  public int get__v() {
    return __v;
  }

  public void set__v(int __v) {
    this.__v = __v;
    }
}
