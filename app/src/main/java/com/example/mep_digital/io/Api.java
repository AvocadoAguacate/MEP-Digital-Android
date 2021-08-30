package com.example.mep_digital.io;


import com.example.mep_digital.model.ListStudents;
import com.example.mep_digital.model.LoginPost;
import com.example.mep_digital.model.LoginReturn;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    String BASE_URL = "https://serene-sands-78874.herokuapp.com/api/";
    @GET("students")
    Call<ListStudents> getStudents();

    @POST("auth/login")
    Call<LoginReturn> postLogin(@Body LoginPost loginPost);
}
