package edu.uncw.seahawktours;

import android.content.Context;

import java.util.ArrayList;

public class Building {
    private String name;
    private int imageResourceId;
    private String caption;
    private String description;
    private String url;


    public void loadBuilding(Building building0, Building building1, Building building2, Building building3, Building building4,int id){
        if (id==0){
            this.setName(building0.getName());
            this.setImageResource(building0.getImageResourceId());
            this.setCaption(building0.getCaption());
            this.setDescription(building0.getDescription());
            this.setUrl(building0.getUrl());
        }

        if (id==1){
            this.setName(building1.getName());
            this.setImageResource(building1.getImageResourceId());
            this.setCaption(building1.getCaption());
            this.setDescription(building1.getDescription());
            this.setUrl(building1.getUrl());
        }

        if (id == 2){
            this.setName(building2.getName());
            this.setImageResource(building2.getImageResourceId());
            this.setCaption(building2.getCaption());
            this.setDescription(building2.getDescription());
            this.setUrl(building2.getUrl());
        }

        if (id == 3){
            this.setName(building3.getName());
            this.setImageResource(building3.getImageResourceId());
            this.setCaption(building3.getCaption());
            this.setDescription(building3.getDescription());
            this.setUrl(building3.getUrl());
        }

        if (id==4){
            this.setName(building4.getName());
            this.setImageResource(building4.getImageResourceId());
            this.setCaption(building4.getCaption());
            this.setDescription(building4.getDescription());
            this.setUrl(building4.getUrl());
        }
    }

    protected Building(String name, int imageResourceId,String caption, String description, String url ){
        this.name=name;
        this.imageResourceId=imageResourceId;
        this.caption=caption;
        this.description=description;
        this.url=url;
    }

    public String getName(){return name;}

    public void setName(String name){this.name=name;}

    public int getImageResourceId(){return imageResourceId;}

    public void setImageResource(int id){this.imageResourceId=id;}

    public String getCaption(){return caption;}

    public void setCaption(String caption){this.caption=caption;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description=description;}

    public String getUrl(){return url;}

    public void setUrl(String url){this.url=url;}


}
