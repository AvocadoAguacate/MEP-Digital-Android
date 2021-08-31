package com.example.mep_digital.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Chat implements Serializable {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     *
     */
    public Chat() {
    }

    /**
     *
     * @param userType
     * @param _id
     * @param message
     * @param user
     */
    public Chat(String _id, User user, String userType, String message) {
        super();
        this._id = _id;
        this.user = user;
        this.userType = userType;
        this.message = message;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
