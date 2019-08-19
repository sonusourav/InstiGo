package com.iitdh.sonusourav.instigo.Mess;

public class MenuClass {

  private MealClass breakfast,lunch,snacks,dinner;
  private String _id;
  private int day,__v;


  public MenuClass(){};

  public MenuClass(MealClass breakfast,MealClass lunch,MealClass snacks,MealClass dinner,String _id,int day,int __v){

    this.breakfast=breakfast;
    this.lunch=lunch;
    this.snacks=snacks;
    this.dinner=dinner;
    this._id=_id;
    this.day=day;
    this.__v=__v;

  }


  public MealClass getBreakfast() {
    return breakfast;
  }

  public void setBreakfast(MealClass breakfast) {
    this.breakfast = breakfast;
  }

  public MealClass getLunch() {
    return lunch;
  }

  public void setLunch(MealClass lunch) {
    this.lunch = lunch;
  }

  public MealClass getSnacks() {
    return snacks;
  }

  public void setSnacks(MealClass snacks) {
    this.snacks = snacks;
  }

  public MealClass getDinner() {
    return dinner;
  }

  public void setDinner(MealClass dinner) {
    this.dinner = dinner;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public int get__v() {
    return __v;
  }

  public void set__v(int __v) {
    this.__v = __v;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }




}
