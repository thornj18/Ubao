package com.somaubao.ubao.models;

/**
 * Created by Tonny on 6/20/2014.
 */
public class PostItem {
    public String post_Title;
    public String post_ImageURL;
    public String post_Time;
    public String post_Link;
    public String post_description;

    public PostItem() {
    }

    public PostItem(String post_Title, String post_ImageURL, String post_Time, String post_Link, String post_description) {
        this.post_ImageURL = post_ImageURL;
        this.post_Title = post_Title;
        this.post_Time = post_Time;
        this.post_Link = post_Link;
        this.post_description = post_description;

    }

}