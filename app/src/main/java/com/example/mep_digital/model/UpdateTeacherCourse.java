package com.example.mep_digital.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateTeacherCourse {

    @SerializedName("teacherId")
    @Expose
    private String teacherId;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateTeacherCourse() {
    }

    /**
     *
     * @param teacherId
     */
    public UpdateTeacherCourse(String teacherId) {
        super();
        this.teacherId = teacherId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

}

