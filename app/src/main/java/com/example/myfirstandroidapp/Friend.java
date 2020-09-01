package com.example.myfirstandroidapp;

public class Friend {

    private int id = 0;
    private String fname  = "";
    private String lname  = "";
    private int yob = 5;
    private String gender  = "";

    public Friend(int id, String fname, String lname , int yob , String gender) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.yob = yob;
        this.gender = gender;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getYob() {
        return yob;
    }

    public void setYob(int yob) {
        this.yob = yob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}

