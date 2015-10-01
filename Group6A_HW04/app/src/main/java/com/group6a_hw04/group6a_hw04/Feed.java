package com.group6a_hw04.group6a_hw04;

import android.os.Parcelable;

import java.io.Serializable;

public class Feed implements Serializable{
    String title, artist, duration, category, releaseDate,
            summary, price, linkToPreview;
    String[] largeImage = new String[2];

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String[] largeImage) {
        this.largeImage = largeImage;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLinkToPreview() {
        return linkToPreview;
    }

    public void setLinkToPreview(String linkToPreview) {
        this.linkToPreview = linkToPreview;
    }
}
