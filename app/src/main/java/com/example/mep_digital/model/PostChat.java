package com.example.mep_digital.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PostChat {

    @SerializedName("userId")
    @Expose
    private String userId;
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
    public PostChat() {
    }

    /**
     *
     * @param userType
     * @param message
     * @param userId
     */
    public PostChat(String userId, String userType, String message) {
        super();
        this.userId = userId;
        this.userType = userType;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
