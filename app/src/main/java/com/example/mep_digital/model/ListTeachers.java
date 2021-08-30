package com.example.mep_digital.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListTeachers {

    @SerializedName("teachers")
    @Expose
    private List<Teacher> teachers = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public ListTeachers() {
    }

    /**
     *
     * @param teachers
     */
    public ListTeachers(List<Teacher> teachers) {
        super();
        this.teachers = teachers;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

}
