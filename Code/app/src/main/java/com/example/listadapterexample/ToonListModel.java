package com.example.listadapterexample;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ToonListModel {
    private ArrayList<ToonModel> toonList = new ArrayList<ToonModel>();

    ToonListModel(JSONArray jsonArray){
        try{
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject tmpJson = jsonArray.getJSONObject(i);
                String image = tmpJson.get("image").toString();
                String name = tmpJson.get("name").toString();
                String desc = tmpJson.get("desc").toString();
                ToonModel tmpToonModel = new ToonModel(image, name, desc);
                toonList.add(tmpToonModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<ToonModel> getToonList() {
        return toonList;
    }

    public void setmList(ArrayList<ToonModel> toonList) {
        this.toonList = toonList;
    }
}
