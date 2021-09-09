package com.example.mep_digital.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UpdateCourse {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("grade")
    @Expose
    private Integer grade;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateCourse() {
    }

    /**
     *
     * @param grade
     * @param name
     */
    public UpdateCourse(String name, Integer grade) {
        super();
        this.name = name;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

}
