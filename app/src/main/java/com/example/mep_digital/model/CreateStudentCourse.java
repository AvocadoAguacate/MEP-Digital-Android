package com.example.mep_digital.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CreateStudentCourse {

    @SerializedName("studentId")
    @Expose
    private String studentId;

    /**
     * No args constructor for use in serialization
     *
     */
    public CreateStudentCourse() {
    }

    /**
     *
     * @param studentId
     */
    public CreateStudentCourse(String studentId) {
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

