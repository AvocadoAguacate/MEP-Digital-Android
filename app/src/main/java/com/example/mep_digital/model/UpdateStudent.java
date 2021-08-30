package com.example.mep_digital.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UpdateStudent {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("grade")
    @Expose
    private Integer grade;

    /**
     * No args constructor for use in serialization
     *
     */
    public UpdateStudent() {
    }

    /**
     *
     * @param password
     * @param grade
     * @param name
     * @param email
     * @param lastname
     */
    public UpdateStudent(String email, String password, String name, String lastname, Integer grade) {
        super();
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.grade = grade;
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

}