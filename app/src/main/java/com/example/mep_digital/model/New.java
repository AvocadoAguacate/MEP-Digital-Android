package com.example.mep_digital.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class New {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date")
    @Expose
    private String date;

    /**
     * No args constructor for use in serialization
     *
     */
    public New() {
    }

    /**
     *
     * @param date
     * @param title
     * @param message
     */
    public New(String title, String message, String date) {
        super();
        this.title = title;
        this.message = message;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
