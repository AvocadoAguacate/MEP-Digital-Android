package com.example.mep_digital.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddStudentCourse {

    @SerializedName("studentId")
    @Expose
    private String studentId;

    /**
     * No args constructor for use in serialization
     *
     */
    public AddStudentCourse() {
    }

    /**
     *
     * @param studentId
     */
    public AddStudentCourse(String studentId) {
        super();
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}

