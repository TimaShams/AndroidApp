package com.example.myfirstandroidapp.Classes;

public class Utility {

    private static Utility instance;

    private boolean [] list;

    public boolean [] getList() {
        return list;
    }

    public void setList(boolean[] list) {
        this.list = list;
    }

    private Utility(){}

    public static Utility getInstance(){
        if(instance == null){
            instance = new Utility();
        }
        return instance;
    }
}