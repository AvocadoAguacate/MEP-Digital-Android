package com.example.mep_digital.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentReturn {

    @SerializedName("student")
    @Expose
    private Student student;

    /**
     * No args constructor for use in serialization
     *
     */
    public StudentReturn() {
    }

    /**
     *
     * @param student
     */
    public StudentReturn(Student student) {
        super();
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}