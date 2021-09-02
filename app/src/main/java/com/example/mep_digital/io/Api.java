package com.example.mep_digital.io;


import com.example.mep_digital.model.CreateCourse;
import com.example.mep_digital.model.CreateSchedule;
import com.example.mep_digital.model.CreateStudent;
import com.example.mep_digital.model.CreateStudentCourse;
import com.example.mep_digital.model.CreateTeacher;
import com.example.mep_digital.model.ListCourses;
import com.example.mep_digital.model.ListStudents;
import com.example.mep_digital.model.ListTeachers;
import com.example.mep_digital.model.LoginPost;
import com.example.mep_digital.model.LoginReturn;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.UpdateCourse;
import com.example.mep_digital.model.UpdateStudent;
import com.example.mep_digital.model.UpdateTeacher;
import com.example.mep_digital.model.UpdateTeacherCourse;

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

    @GET("teachers")
    Call<ListTeachers> getTeachers();

    @POST("teachers")
    Call<Message> postTeacher(@Body CreateTeacher createTeacher);

    @PUT("teachers/{id}")
    Call<Message> putTeacher(@Path("id") String id, @Body UpdateTeacher updateTeacher);

    @DELETE("teachers/{id}")
    Call<Message> deleteTeacher(@Path("id") String id);

    @GET("courses")
    Call<ListCourses> getCourses();

    @POST("courses")
    Call<Message> postCourse(@Body CreateCourse createCourse);

    @DELETE("courses/{id}")
    Call<Message> deleteCourse(@Path("id") String id);

    @PUT("courses/{id}")
    Call<Message> putCourse(@Path("id") String id, UpdateCourse updateCourse);

    @POST("courses/{id}/schedule")
    Call<Message> postSchedule(@Path("id") String id, CreateSchedule createSchedule);

    @DELETE("courses/{idCourse}/schedule/{idSchedule}")
    Call<Message> deleteSchedule(@Path("idCourse") String idCourse, @Path("idSchedule") String idSchedule);

    @PUT("courses/{id}/teacher")
    Call<Message> putTeacherCourse(@Path("id") String id, UpdateTeacherCourse updateTeacherCourse);

    @DELETE("courses/{id}/teacher")
    Call<Message> deleteTeacherCourse(@Path("id") String id, UpdateTeacherCourse updateTeacherCourse);

    @POST("courses/{id}/students")
    Call<Message> postStudentCourse(@Path("id") String id, CreateStudentCourse createStudentCourse);

    @DELETE("courses/{idCourse}/students/{idStudent}")
    Call<Message> deleteStudentCourse(@Path("idCourse") String idCourse, @Path("idStudent") String idStudent);

    @POST("auth/login")
    Call<LoginReturn> postLogin(@Body LoginPost loginPost);



}
