package com.tempus.timewalk.timewalk.Models;

/**
 * An immutable class to represented the tour and hold their respective information
 * Created by Isaac on 23/8/17.
 */

public class Information {

    /**
     * Variables
     */
    String venue;
    String detail;
    String description;
    int images;

    /**
     * Constructor to create a data model to hold details for one tour
     * @param venue name of the tour
     * @param detail specification of the tour
     * @param description description of the tour
     * @int images Images associated with the venue
     */
    public Information(String venue, String detail, String description, int images){
        this.venue = venue;
        this.detail = detail;
        this.description = description;
        this.images = images;
    }

    /**
     * Return string value of venue name
     * @return venue name in string value
     */
    public  String getVenue(){
        return  venue;
    }

    /**
     * Return string value of venue details
     * @return venue details in string value
     */
    public String getDetail(){
        return detail;
    }

    /**
     * Return string value of venue description
     * @return venue description in string value
     */
    public String getDescription(){
        return description;
    }

    /**
     * Return int value representation of all images
     * @return images in int value
     */
    public int getImages(){
        return images;
    }
}
