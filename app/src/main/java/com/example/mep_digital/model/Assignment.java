package com.example.mep_digital.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Assignment implements Serializable {

    @SerializedName("_id")
    @Expose
    private String _id;
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
    public Assignment() {
    }

    /**
     *
     * @param submitDate
     * @param description
     * @param _id
     * @param title
     */
    public Assignment(String _id, String title, String description, String submitDate) {
        super();
        this._id = _id;
        this.title = title;
        this.description = description;
        this.submitDate = submitDate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
