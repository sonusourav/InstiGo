package com.iitdh.sonusourav.instigo.Mess;

public class MealClass {


  private float ratings;
  private String time;
  private String item;
  private int raters;

  public MealClass(){};

  public MealClass(int ratings,String time,String item, int raters){
    this.ratings=ratings;
    this.time=time;
    this.item=item;
    this.raters=raters;
  };

  private int getRaters() {
    return raters;
  }

  public void setRaters(int raters) {
    this.raters = raters;
  }

  float getRatings() {
    return ratings;
  }

  public void setRatings(float ratings) {
    this.ratings = ratings;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getItem() {
    return item;
  }

  public void setItem(String items) {
    this.item = items;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MealClass object = (MealClass) o;

    if (time != null ? !time.equals(object.time) : object.time !=null)
      return false;
    if (item != null ? !item.equals(object.item) : object.item != null)
      return false;
    if (raters!=object.getRaters() )
      return false;
    return  (ratings==object.ratings);
  }
}
