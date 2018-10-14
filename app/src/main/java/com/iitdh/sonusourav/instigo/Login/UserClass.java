package com.iitdh.sonusourav.instigo.Login;



public class UserClass {
    public String name;
    public String userName;
    public String email;
    public String gender;
    public String phone;
    public String pass;

    public UserClass(String userEmail,String username, String userPass) {

        email = userEmail;
        userName=username;
        pass = userPass;
        name = "";
        phone = "";
        gender = "";

    }
    public UserClass(String userEmail) {

        email = userEmail;
        userName="";
        pass = "fa31b7bcb9e0d9ad4ab7e94e0230f2af7";
        name = "";
        phone = "";
        gender = "";

    }


    public UserClass( String userEmail,String username,String userPassword, String uname, String userGender, String userPhone) {
        email = userEmail;
        userName=username;
        pass = userPassword;
        name = uname;
        phone = userPhone;
        gender = userGender;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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


}
