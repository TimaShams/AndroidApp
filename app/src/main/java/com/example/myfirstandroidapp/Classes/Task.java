package com.example.myfirstandroidapp.Classes;

public class Task {

    private int id = 0;
    private String name  = "";
    private String location  = "";
    private boolean status = false;

    public Task() {
    }

    public Task(int id, String name, String location , Boolean status ) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.status = status;
    }


    public int bringID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
