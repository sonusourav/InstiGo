package com.iitdh.sonusourav.instigo.Council;

import java.io.Serializable;

public class CouncilUserClass implements Serializable {
  private String _id, name, title, phoneno, email, imagePath, desc;

  CouncilUserClass() {
  }

  CouncilUserClass(String id, String name, String title, String phoneno, String email,
      String imagePath, String desc) {
    this._id = id;
    this.title = title;
        this.name=name;
    this.phoneno = phoneno;
        this.email=email;
    this.imagePath = imagePath;
    this.desc = desc;

    }

  CouncilUserClass(String id, String name, String title, String phoneno, String email) {
    this._id = id;
    this.title = title;
        this.name=name;
    this.phoneno = phoneno;
        this.email=email;

    }

  public String getId() {
    return _id;
  }

  public void setId(String id) {
    this._id = id;
  }

    public void setName(String name) {
        this.name = name;
    }

  public void setTitle(String title) {
    this.title = title;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  public void setPhoneno(String phoneno) {
    this.phoneno = phoneno;
    }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

  public String getTitle() {
    return title;
    }

    public String getEmail() {
        return email;
    }

  public String getPhoneno() {
    return phoneno;
  }

  public String getImagePath() {
    return imagePath;
    }

  public String getDesc() {
    return desc;
    }

  public void setDesc(String desc) {
    this.desc = desc;
  }

}
