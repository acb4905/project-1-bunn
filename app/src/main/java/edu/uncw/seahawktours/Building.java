package edu.uncw.seahawktours;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.IndexType;
import io.objectbox.annotation.NameInDb;

@Entity
public class Building {

    @Index(type = IndexType.VALUE)
    private String name;
    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String imageResource;
    private String caption;
    private String description;
    private String url;
    private double lat;
    private double lon;
    private float radius;



    public void loadBuilding(Building building0, Building building1, Building building2, Building building3, Building building4,int id){
        if (id==0){
            this.setName(building0.getName());
            this.setImageResource(building0.getImageResource());
            this.setCaption(building0.getCaption());
            this.setDescription(building0.getDescription());
            this.setUrl(building0.getUrl());
        }

        if (id==1){
            this.setName(building1.getName());
            this.setImageResource(building1.getImageResource());
            this.setCaption(building1.getCaption());
            this.setDescription(building1.getDescription());
            this.setUrl(building1.getUrl());
        }

        if (id == 2){
            this.setName(building2.getName());
            this.setImageResource(building2.getImageResource());
            this.setCaption(building2.getCaption());
            this.setDescription(building2.getDescription());
            this.setUrl(building2.getUrl());
        }

        if (id == 3){
            this.setName(building3.getName());
            this.setImageResource(building3.getImageResource());
            this.setCaption(building3.getCaption());
            this.setDescription(building3.getDescription());
            this.setUrl(building3.getUrl());
        }

        if (id==4){
            this.setName(building4.getName());
            this.setImageResource(building4.getImageResource());
            this.setCaption(building4.getCaption());
            this.setDescription(building4.getDescription());
            this.setUrl(building4.getUrl());
        }
    }

    public Building(String name, String imageResourceId,String caption, String description, String url, double lat, double lon, float radius ){
        this.name=name;
        this.imageResource=imageResourceId;
        this.caption=caption;
        this.description=description;
        this.url=url;
        this.lat=lat;
        this.lon=lon;
        this.radius=radius;
    }

    public Building(){

    }




    public String getName(){return name;}

    public void setName(String name){this.name=name;}

    public String getImageResource(){return imageResource;}

    public void setImageResource(String id){this.imageResource=id;}

    public String getCaption(){return caption;}

    public void setCaption(String caption){this.caption=caption;}

    public String getDescription(){return description;}

    public void setDescription(String description){this.description=description;}

    public String getUrl(){return url;}

    public void setUrl(String url){this.url=url;}

    public double getLat(){ return this.lat;}

    public double getLon(){return this.lon;}

    public float getRadius(){return this.radius;}


}
