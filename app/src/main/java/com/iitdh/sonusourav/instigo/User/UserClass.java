package com.iitdh.sonusourav.instigo.User;



public class UserClass {
    private String name;
    private String email;
    private String pass;
    private String gender;
    private String phone;
    private String branch;
    private String year;
    private String dob;
    private String hostel;
    private String profilePic;
    private String coverPic;

    public UserClass(){}

    public UserClass(String userEmail,String uname, String userPass) {

        email = userEmail;
        pass = userPass;
        name = uname;
        phone = "";
        gender = "";
        branch="";
        year="";
        dob="";
        hostel="";
        profilePic="";
        coverPic="";

    }
    public UserClass(String userEmail, String userName) {

        email = userEmail;
        pass = "fa31b7bcb9e0d9ad4ab7e94e0230f2af7";
        name = userName;
        phone = "";
        gender = "";
        branch="";
        year="";
        dob="";
        hostel="";
        profilePic="";
        coverPic="";

    }

    public UserClass( String uname,String userBranch,String userYear, String userPhone,String userDob,String userGender,String userHostel) {
        name = uname;
        phone = userPhone;
        gender = userGender;
        branch=userBranch;
        year=userYear;
        dob=userDob;
        hostel=userHostel;

    }

    public UserClass( String userEmail,String uname,String userBranch,String userYear, String userPhone,String userDob,  String userGender,String userHostel,String userPic,String userCoverPic) {
        email = userEmail;
        name = uname;
        phone = userPhone;
        gender = userGender;
        branch=userBranch;
        year=userYear;
        dob=userDob;
        hostel=userHostel;
        profilePic=userPic;
        coverPic=userCoverPic;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }
}
