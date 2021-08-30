package com.example.mep_digital.io;


import com.example.mep_digital.model.CreateStudent;
import com.example.mep_digital.model.ListStudents;
import com.example.mep_digital.model.ListTeachers;
import com.example.mep_digital.model.LoginPost;
import com.example.mep_digital.model.LoginReturn;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.UpdateStudent;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    String BASE_URL = "https://serene-sands-78874.herokuapp.com/api/";
    @GET("students")
    Call<ListStudents> getStudents();

    @POST("students")
    Call<Message> postStudent(@Body CreateStudent createStudent);

    @PUT("students/{id}")
    Call<Message> putStudent(@Path("id") String id, @Body UpdateStudent updateStudent);

    @DELETE("students/{id}")
    Call<Message> deleteStudent(@Path("id") String id);

    @POST("auth/login")
    Call<LoginReturn> postLogin(@Body LoginPost loginPost);



}
