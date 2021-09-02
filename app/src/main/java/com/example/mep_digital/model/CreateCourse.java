package com.example.mep_digital.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CreateCourse {

    @SerializedName("id")
    @Expose
    private String id;
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
    public CreateCourse() {
    }

    /**
     *
     * @param grade
     * @param name
     * @param id
     */
    public CreateCourse(String id, String name, Integer grade) {
        super();
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
