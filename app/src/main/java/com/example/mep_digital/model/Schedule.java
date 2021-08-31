package com.example.mep_digital.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Schedule implements Serializable {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("startHour")
    @Expose
    private Integer startHour;
    @SerializedName("startMinutes")
    @Expose
    private Integer startMinutes;
    @SerializedName("endHour")
    @Expose
    private Integer endHour;
    @SerializedName("endMinutes")
    @Expose
    private Integer endMinutes;

    /**
     * No args constructor for use in serialization
     *
     */
    public Schedule() {
    }

    /**
     *
     * @param endHour
     * @param startHour
     * @param startMinutes
     * @param _id
     * @param endMinutes
     * @param day
     */
    public Schedule(String _id, Integer day, Integer startHour, Integer startMinutes, Integer endHour, Integer endMinutes) {
        super();
        this._id = _id;
        this.day = day;
        this.startHour = startHour;
        this.startMinutes = startMinutes;
        this.endHour = endHour;
        this.endMinutes = endMinutes;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(Integer startMinutes) {
        this.startMinutes = startMinutes;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getEndMinutes() {
        return endMinutes;
    }

    public void setEndMinutes(Integer endMinutes) {
        this.endMinutes = endMinutes;
    }

}
