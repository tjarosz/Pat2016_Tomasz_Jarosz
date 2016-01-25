package com.example.tomek.tomaszjarosz;


public class Item {
    private final String title;
    private final String description;
    private final String url;

    public Item(String title, String desc, String url) {
        this.title = title;
        this.description = desc;
        this.url = url;
    }

    public String getTitle(){
        return this.title;
    }
    public String getDesc(){
        return this.description;
    }
    public String getUrl(){
        return this.url;
    }
}
