package edu.uncw.seahawktours;

import android.content.Context;

import java.util.ArrayList;

public class Building {
    private String name;
    private int imageResourceId;
    private String caption;
    private String description;
    private String url;


    public ArrayList <Building> loadBuildings(Building building0, Building building1, Building building2, Building building3, Building building4){
        ArrayList<Building> buildings = new ArrayList<Building>();
        buildings.add(building0);
        buildings.add(building1);
        buildings.add(building2);
        buildings.add(building3);
        buildings.add(building4);
        return buildings;
    }

    private Building(String name, int imageResourceId,String caption, String description, String url ){
        this.name=name;
        this.imageResourceId=imageResourceId;
        this.caption=caption;
        this.description=description;
        this.url=url;
    }

    public String getName(){return name;}

    public int getImageResourceId(){return imageResourceId;}

    public String getCaption(){return caption;}

    public String getDescription(){return description;}

    public String getUrl(){return url;}


}
