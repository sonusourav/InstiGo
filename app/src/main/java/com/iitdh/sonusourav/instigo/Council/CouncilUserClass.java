package com.iitdh.sonusourav.instigo.Council;

public class CouncilUserClass {
    private String name;
    private String desgn;
    private String phoneNo;
    private String email;
    private int picId;

    CouncilUserClass(String name,String desgn,String phoneNo,String email,int picId){
        this.desgn=desgn;
        this.name=name;
        this.phoneNo=phoneNo;
        this.email=email;
        this.picId=picId;

    }

    CouncilUserClass(String name,String desgn,String phoneNo,String email){
        this.desgn=desgn;
        this.name=name;
        this.phoneNo=phoneNo;
        this.email=email;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesgn(String desgn) {
        this.desgn = desgn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getName() {
        return name;
    }

    public String getDesgn() {
        return desgn;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public int getPicId() {
        return picId;
    }
}
