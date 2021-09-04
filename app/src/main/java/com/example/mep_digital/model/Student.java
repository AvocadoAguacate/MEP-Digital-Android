package com.example.mep_digital.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class    Student implements Serializable {

    @SerializedName("_id")
    private String _id;
    @SerializedName("id")
    private String id;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("grade")
    private Integer grade;
    @SerializedName("__v")
    private Integer v;

    public Student(String _id, String id, String email,
                   String password, String name,
                   String lastname, Integer grade,
                   Integer v) {
        this._id = _id;
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.grade = grade;
        this.v = v;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "Student{" +
                "_id='" + _id + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", grade=" + grade +
                ", v=" + v +
                '}';
    }
}
