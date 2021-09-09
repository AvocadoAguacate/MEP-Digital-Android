package com.example.mep_digital.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PutRating {

    @SerializedName("studentId")
    @Expose
    private String studentId;
    @SerializedName("rating")
    @Expose
    private Integer rating;

    /**
     * No args constructor for use in serialization
     *
     */
    public PutRating() {
    }

    /**
     *
     * @param studentId
     * @param rating
     */
    public PutRating(String studentId, Integer rating) {
        super();
        this.studentId = studentId;
        this.rating = rating;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}
