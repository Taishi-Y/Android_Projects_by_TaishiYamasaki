package me.taishi.TechCrunchReader.models;

import java.io.Serializable;

public class RSSFeed implements Serializable {

    private String title;
    private String link;
    private String description;
    private String category;
    private String pubDate;
    private String guid;
    private String feedburnerOrigLink;
    private String thumbnailUrl;

    public RSSFeed() {
    }

    public RSSFeed(String title, String link, String description, String category, String pubDate,
                   String guid, String feedburnerOrigLink, String thumbnailUrl) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.category = category;
        this.pubDate = pubDate;
        this.guid = guid;
        this.feedburnerOrigLink = feedburnerOrigLink;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getGuid() {
        return guid;
    }

    public String getFeedburnerOrigLink() {
        return feedburnerOrigLink;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setFeedburnerOrigLink(String feedburnerOrigLink) {
        this.feedburnerOrigLink = feedburnerOrigLink;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}
