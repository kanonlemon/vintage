package com.kanon.vintage.subscription.model;

import java.io.Serializable;
import java.util.Date;

public class Subscription implements Serializable {

    private static final long serialVersionUID = -2261179506280556731L;

    private String from;

    private String title;

    private String description;

    private String link;

    private Date publishDate;

    public Subscription(String from, String title, String description, String link, Date publishDate) {
        this.from = from;
        this.title = title;
        this.description = description;
        this.link = link;
        this.publishDate = publishDate;
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

    /**
     * 基础比较方法为from, link 完全相等
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        Subscription others = (Subscription) obj;
        return this.from.equals(others.from)
                && this.link.equals(others.link);
    }

    @Override
    public int hashCode() {
        return (this.from + this.link).hashCode();
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "from='" + from + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }
}
