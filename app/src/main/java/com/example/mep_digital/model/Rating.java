package com.example.mep_digital.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("student")
    @Expose
    private Student student ;
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
     * @param _id
     */
    public Rating(String _id, Student student, Integer rating) {
        super();
        this._id = _id;
        this.student = student;
        this.rating = rating;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
