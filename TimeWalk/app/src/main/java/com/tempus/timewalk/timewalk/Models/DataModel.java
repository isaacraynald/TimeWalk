package com.tempus.timewalk.timewalk.Models;

/**
 * Created by Isaac on 23/8/17.
 */

public class DataModel {
    String venue;
    String detail;
    String description;
    int images;

    public DataModel(String venue, String detail, String description, int images){
        this.venue = venue;
        this.detail = detail;
        this.description = description;
        this.images = images;
    }

    public  String getVenue(){
        return  venue;
    }
    public String getDetail(){
        return detail;
    }
    public String getDescription(){
        return description;
    }
    public int getImages(){
        return images;
    }
}
