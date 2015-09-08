package com.example.antonmolganov.boloidtestapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anton on 29.05.2015.
 */
public class Task {

    private String text = null;
    private String durationLimitText = null;
    private boolean translation = false;
    private Double lon = -1.0;
    private Double lat = -1.0;
    private String bingMapImage = null;
    private long date = -1;
    private String title = null;
    private long price = -1;
    private String reflink = null;
    private String longText = null;
    private long ID = -1;
    private ArrayList<Price> prices = null;



    private long zoomLevel = -1;
    private String locationText = null;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDurationLimitText() {
        return durationLimitText;
    }

    public void setDurationLimitText(String durationLimitText) {
        this.durationLimitText = durationLimitText;
    }

    public boolean isTranslation() {
        return translation;
    }

    public void setTranslation(boolean translation) {
        this.translation = translation;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getBingMapImage() {
        return bingMapImage;
    }

    public void setBingMapImage(String bingMapImage) {
        this.bingMapImage = bingMapImage;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getReflink() {
        return reflink;
    }

    public void setReflink(String reflink) {
        this.reflink = reflink;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public ArrayList<Price> getPrices() {
        return prices;
    }

    public void addPrice(String description, long price) {
        if (this.prices == null) this.prices = new ArrayList<Price>();
        prices.add(new Price(description, price));
    }

    public long getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(long zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public class Price {
        private String description = null;
        private long price = -1;

        public Price(String description, long price){
            this.description = description;
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public long getPrice() {
            return price;
        }

    }





}
