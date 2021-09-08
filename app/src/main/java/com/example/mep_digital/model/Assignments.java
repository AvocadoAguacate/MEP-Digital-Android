package com.example.mep_digital.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Assignments {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("submitDate")
    @Expose
    private String submitDate;

    /**
     * No args constructor for use in serialization
     *
     */
    public Assignments() {
    }

    /**
     *
     * @param submitDate
     * @param description
     * @param title
     */
    public Assignments(String title, String description, String submitDate) {
        super();
        this.title = title;
        this.description = description;
        this.submitDate = submitDate;
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

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

}
