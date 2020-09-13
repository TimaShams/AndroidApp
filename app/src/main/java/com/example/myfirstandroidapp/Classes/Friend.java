package com.example.myfirstandroidapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Friend implements Serializable {

    private int friendID = 0;
    private String fname = "";
    private String lname = "";
    private String gender  = "";
    private int age = 0;
    private String address  = "";

    public Friend(int friendID , String fname, String lname, String gender , int age , String address) {
        this.friendID = friendID;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.age = age;
        this.address = address;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }
}

