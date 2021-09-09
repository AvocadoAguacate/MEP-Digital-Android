package com.example.mep_digital.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CreateSchedule {

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
    public CreateSchedule() {
    }

    /**
     *
     * @param endHour
     * @param startHour
     * @param startMinutes
     * @param endMinutes
     * @param day
     */
    public CreateSchedule(Integer day, Integer startHour, Integer startMinutes, Integer endHour, Integer endMinutes) {
        super();
        this.day = day;
        this.startHour = startHour;
        this.startMinutes = startMinutes;
        this.endHour = endHour;
        this.endMinutes = endMinutes;
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

