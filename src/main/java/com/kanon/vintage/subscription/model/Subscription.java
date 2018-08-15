package com.kanon.vintage.subscription.model;

import java.util.Date;

public class Subscription {

    private String from;

    private String title;

    private String description;

    private String link;

    private Date publishDate;

    public Subscription(String from, String title, String description, String link) {
        this.from = from;
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

}
