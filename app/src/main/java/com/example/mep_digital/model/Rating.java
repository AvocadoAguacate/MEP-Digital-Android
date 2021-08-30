package com.example.mep_digital.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("idx")
    @Expose
    private String idx;
    @SerializedName("student")
    @Expose
    private Student student;
    @SerializedName("rating")
    @Expose
    private Integer rating;

    /**
     * No args constructor for use in serialization
     *
     */
    public Rating() {
    }

    /**
     *
     * @param student
     * @param rating
     * @param idx
     */
    public Rating(String idx, Student student, Integer rating) {
        super();
        this.idx = idx;
        this.student = student;
        this.rating = rating;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}
