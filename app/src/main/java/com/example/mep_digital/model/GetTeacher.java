package com.example.mep_digital.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTeacher {

    @SerializedName("teacher")
    @Expose
    private Teacher teacher;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetTeacher() {
    }

    /**
     *
     * @param teacher
     */
    public GetTeacher(Teacher teacher) {
        super();
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

}
