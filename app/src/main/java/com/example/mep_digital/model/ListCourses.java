package com.example.mep_digital.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ListCourses implements Serializable {

    @SerializedName("courses")
    @Expose
    private List<Course> courses = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ListCourses() {
    }

    /**
     *
     * @param courses
     */
    public ListCourses(List<Course> courses) {
        super();
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}