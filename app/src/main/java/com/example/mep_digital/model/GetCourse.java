package com.example.mep_digital.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetCourse {

    @SerializedName("course")
    @Expose
    private Course course;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetCourse() {
    }

    /**
     *
     * @param course
     */
    public GetCourse(Course course) {
        super();
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "GetCourse{" +
                "course=" + course.toString() +
                '}';
    }
}
