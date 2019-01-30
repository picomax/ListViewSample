package com.example.listadapterexample;

import org.json.JSONObject;

import java.util.ArrayList;

public class ToonModel {
    public String image;
    public String name;
    public String desc;

    ToonModel(String image, String name, String desc){
        this.image = image;
        this.name = name;
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}