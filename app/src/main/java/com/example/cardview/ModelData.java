package com.example.cardview;

/**
 * Created by THUNDER on 12/1/2016.
 */

public class ModelData {

    String title;
    String city;
    String description;
    String Venue;

    public String getVenue() {
        return Venue;
    }

    public void setVenue(String venue) {
        Venue = venue;
    }



    public String getImgurl() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    String img_url;
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


//    String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



  /*  public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
*/




}
