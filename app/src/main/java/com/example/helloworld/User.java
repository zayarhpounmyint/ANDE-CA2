package com.example.helloworld;


public class User {
    private String username , useremail ;
    private int userphone ;

    public String getUsername() {
        return username;
    }

    public String getUseremail() {
        return useremail;
    }

    public int getUserphone() {
        return userphone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserphone(int userphone) {
        this.userphone = userphone;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
}
}
