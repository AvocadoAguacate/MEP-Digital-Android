package com.example.mep_digital.io;


import com.example.mep_digital.model.Assignments;
import com.example.mep_digital.model.CreateCourse;
import com.example.mep_digital.model.CreateSchedule;
import com.example.mep_digital.model.CreateStudent;
import com.example.mep_digital.model.AddStudentCourse;
import com.example.mep_digital.model.CreateTeacher;
import com.example.mep_digital.model.GetCourse;
import com.example.mep_digital.model.GetTeacher;
import com.example.mep_digital.model.ListCourses;
import com.example.mep_digital.model.ListStudents;
import com.example.mep_digital.model.ListTeachers;
import com.example.mep_digital.model.LoginPost;
import com.example.mep_digital.model.LoginReturn;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.New;
import com.example.mep_digital.model.News;
import com.example.mep_digital.model.PostChat;
import com.example.mep_digital.model.PutRating;
import com.example.mep_digital.model.StudentReturn;
import com.example.mep_digital.model.UpdateCourse;
import com.example.mep_digital.model.UpdateStudent;
import com.example.mep_digital.model.UpdateTeacher;
import com.example.mep_digital.model.UpdateTeacherCourse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "https://serene-sands-78874.herokuapp.com/api/";

    @GET("students")
    Call<ListStudents> getStudents();

    @GET("students/{id}")
    Call<StudentReturn> getStudent(@Path("id") String id);

    @POST("students")
    Call<Message> postStudent(@Body CreateStudent createStudent);

    @PUT("students/{id}")
    Call<Message> putStudent(@Path("id") String id, @Body UpdateStudent updateStudent);

    @DELETE("students/{id}")
    Call<Message> deleteStudent(@Path("id") String id);

    @GET("teachers")
    Call<ListTeachers> getTeachers();

    @GET("teachers/{id}")
    Call<GetTeacher> getTeacher(@Path("id") String id);

    @POST("teachers")
    Call<Message> postTeacher(@Body CreateTeacher createTeacher);

    @PUT("teachers/{id}")
    Call<Message> putTeacher(@Path("id") String id, @Body UpdateTeacher updateTeacher);

    @DELETE("teachers/{id}")
    Call<Message> deleteTeacher(@Path("id") String id);

    @GET("courses")
    Call<ListCourses> getCourses();

    @GET("courses/{id}")
    Call<GetCourse> getCourse(@Path("id") String id);

    @POST("courses")
    Call<Message> postCourse(@Body CreateCourse createCourse);

    @DELETE("courses/{id}")
    Call<Message> deleteCourse(@Path("id") String id);

    @PUT("courses/{id}")
    Call<Message> putCourse(@Path("id") String id, @Body UpdateCourse updateCourse);

    @POST("courses/{id}/schedule")
    Call<Message> postSchedule(@Path("id") String id, @Body CreateSchedule createSchedule);

    @DELETE("courses/{idCourse}/schedule/{idSchedule}")
    Call<Message> deleteSchedule(@Path("idCourse") String idCourse, @Path("idSchedule") String idSchedule);

    @PUT("courses/{id}/teacher")
    Call<Message> putTeacherCourse(@Path("id") String id, @Body UpdateTeacherCourse updateTeacherCourse);

    @HTTP(method = "DELETE", path = "courses/{id}/teacher", hasBody = true)
    Call<Message> deleteTeacherCourse(@Path("id") String id, @Body UpdateTeacherCourse updateTeacherCourse);

    @POST("courses/{id}/students")
    Call<Message> postStudentCourse(@Path("id") String id, @Body AddStudentCourse addStudentCourse);

    @DELETE("courses/{idCourse}/students/{idStudent}")
    Call<Message> deleteStudentCourse(@Path("idCourse") String idCourse, @Path("idStudent") String idStudent);

    @POST("auth/login")
    Call<LoginReturn> postLogin(@Body LoginPost loginPost);

    @GET("courses")
    Call<ListCourses> getTeacherCourses(@Query("teacherId") String teacherId);

    @GET("courses")
    Call<ListCourses> getStudentCourses(@Query("studentId") String teacherId);

    @POST("courses/{id}/chat")
    Call<Message> postChat(@Path("id") String id, @Body PostChat postChat);

    @POST("courses/{id}/assignments")
    Call<Message> postAssignments(@Path("id") String id, @Body Assignments assignments);

    @PUT("courses/{id}/assignments/{idAssignments}")
    Call<Message> putAssignments(@Path("id") String id, @Path("idAssignments") String idAssignments, @Body Assignments assignments);

    @DELETE("courses/{idCourse}/assignments/{idAssignments}")
    Call<Message> deleteAssignments(@Path("idCourse") String idCourse, @Path("idAssignments") String idAssignments);

    @POST("courses/{id}/news")
    Call<Message> postNews(@Path("id") String id, @Body New news);

    @PUT("courses/{idCourse}/news/{idNew}")
    Call<Message> putNews(@Path("idCourse") String id, @Path("idNew") String idNew, @Body New news);

    @DELETE("courses/{idCourse}/news/{idNew}")
    Call<Message> deleteNews(@Path("idCourse") String idCourse, @Path("idNew") String idNew);

    @PUT("teachers/{id}/ratings")
    Call<Message> putRating(@Path("id") String id, @Body PutRating putRating);


}
