package com.iitdh.sonusourav.instigo.Complaints;

import android.os.Parcel;
import android.os.Parcelable;

public class ComplainItemClass implements Parcelable{


    private String complainEmail;
    private String complainUsername;
    private String complainHouseNo;
    private String complainHostel;
    private String complainType;
    private String complainTitle;
    private String complainDetails;
    private long complainTime;
    private int status;
    private boolean complainIsPrivate;
    private String complainReceivers;


    public  ComplainItemClass(){

    }

    ComplainItemClass(String complainEmail,String complainUsername,String complainHouseNo,String complainHostel,String complainType,long complainTime
            ,int status,String complainTitle,String complainDetails,boolean complainIsPrivate,String complainReceivers){

        this.complainEmail=complainEmail;
        this.complainUsername=complainUsername;
        this.complainType=complainType;
        this.complainHostel=complainHostel;
        this.complainHouseNo=complainHouseNo;
        this.complainTime=complainTime;
        this.complainTitle=complainTitle;
        this.complainDetails=complainDetails;
        this.status=status;
        this.complainIsPrivate=complainIsPrivate;
        this.complainReceivers=complainReceivers;
    }
    ComplainItemClass(String complainEmail,String complainUsername,String complainHouseNo,String complainHostel,String complainType
            ,int status,String complainTitle,String complainDetails,boolean complainIsPrivate,String complainReceivers){

        this.complainEmail=complainEmail;
        this.complainUsername=complainUsername;
        this.complainType=complainType;
        this.complainHostel=complainHostel;
        this.complainHouseNo=complainHouseNo;
        this.complainTitle=complainTitle;
        this.complainDetails=complainDetails;
        this.status=status;
        this.complainIsPrivate=complainIsPrivate;
        this.complainReceivers=complainReceivers;
    }


    private ComplainItemClass(Parcel in) {
        complainEmail = in.readString();
        complainUsername = in.readString();
        complainHouseNo = in.readString();
        complainHostel = in.readString();
        complainType = in.readString();
        complainTitle = in.readString();
        complainDetails = in.readString();
        complainTime = in.readLong();
        status = in.readInt();
        complainIsPrivate = in.readByte() != 0;
        complainReceivers = in.readString();
    }

    public static final Creator<ComplainItemClass> CREATOR = new Creator<ComplainItemClass>() {
        @Override
        public ComplainItemClass createFromParcel(Parcel in) {
            return new ComplainItemClass(in);
        }

        @Override
        public ComplainItemClass[] newArray(int size) {
            return new ComplainItemClass[size];
        }
    };

    public String getComplainEmail() {
        return complainEmail;
    }

    public String getComplainUsername() {
        return complainUsername;
    }

    public String getComplainType() {
        return complainType;
    }


    public String getComplainTitle() {
        return complainTitle;
    }

    public long getComplainTime() {
        return complainTime;
    }

    public int getStatus() {
        return status;
    }

    public String getComplainHostel() {
        return complainHostel;
    }

    public String getComplainHouseNo() {
        return complainHouseNo;
    }

    public void setComplainHostel(String complainHostel) {
        this.complainHostel = complainHostel;
    }

    public void setComplainHouseNo(String complainHouseNo) {
        this.complainHouseNo = complainHouseNo;
    }

    public String getComplainDetails() {
        return complainDetails;
    }

    public String getComplainReceivers() {
        return complainReceivers;
    }


    public boolean isComplainIsPrivate() {
        return complainIsPrivate;
    }

    public void setComplainIsPrivate(boolean complainIsPrivate) {
        this.complainIsPrivate = complainIsPrivate;
    }

    public void setComplainReceivers(String complainReceivers) {
        this.complainReceivers = complainReceivers;
    }

    public void setComplainEmail(String complainEmail) {
        this.complainEmail = complainEmail;
    }

    public void setComplainUsername(String complainUsername) {
        this.complainUsername = complainUsername;
    }

    public void setComplainType(String complainType) {
        this.complainType = complainType;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public void setComplainTitle(String complainTitle) {
        this.complainTitle = complainTitle;
    }

    public void setComplainTime(long complainTime) {
        this.complainTime = complainTime;
    }

    public void setComplainDetails(String complainDetails) {
        this.complainDetails = complainDetails;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(complainEmail);
        parcel.writeString(complainUsername);
        parcel.writeString(complainHouseNo);
        parcel.writeString(complainHostel);
        parcel.writeString(complainType);
        parcel.writeString(complainTitle);
        parcel.writeString(complainDetails);
        parcel.writeLong(complainTime);
        parcel.writeInt(status);
        parcel.writeByte((byte) (complainIsPrivate ? 1 : 0));
        parcel.writeString(complainReceivers);
    }
}
