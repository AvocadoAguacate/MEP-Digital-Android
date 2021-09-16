package com.example.mep_digital.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mep_digital.R;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.Assignments;
import com.example.mep_digital.model.Course;
import com.example.mep_digital.model.GetCourse;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.New;
import com.example.mep_digital.model.News;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTeacherActivity extends AppCompatActivity {

    Course course;
    News news;
    boolean updateMode;
    String idCourse;
    Calendar calendar;
    String submitDate;

    EditText nameNewEditText;
    Button saveNewButton;
    Button deleteNewButton;
    Button cancelNewButton;
    EditText newTextEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_teacher);
        //
        nameNewEditText = findViewById(R.id.nameNewEditText);
        newTextEditText = findViewById(R.id.newTextEditText);
        saveNewButton = findViewById(R.id.saveNewButton);
        deleteNewButton = findViewById(R.id.deleteNewButton);
        cancelNewButton = findViewById(R.id.cancelNewButton);
        //
        loadData();
        //
        setButtons();
    }

    private void loadData(){
        Intent intent = getIntent();
        updateMode = intent.getBooleanExtra("updateMode",false);
        idCourse = intent.getStringExtra("idCourse");
        if(updateMode){
            news = (News) intent.getSerializableExtra("new");
            nameNewEditText.setText(news.getTitle());
            newTextEditText.setText(news.getMessage());
            submitDate = news.getDate();
        } else {
            deleteNewButton.setEnabled(false);
        }
    }
    private void setButtons(){
        cancelNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        deleteNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Message> call = RetrofitClient.getInstance().getMyApi().deleteNews(idCourse,news.get_id());
                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        try {
                            int statusCode = response.code();
                            Message message = response.body();
                            Toast.makeText(NewTeacherActivity.this,message.getMessage(),Toast.LENGTH_LONG).show();
                            goBackUpdated();
                        } catch (Exception e){
                            JsonParser parser = new JsonParser();
                            JsonElement mJson = null;
                            try {
                                mJson = parser.parse(response.errorBody().string());
                                Gson gson = new Gson();
                                Message errorResponse = gson.fromJson(mJson, Message.class);
                                Toast.makeText(NewTeacherActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {

                    }
                });
            }
        });

        saveNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = nameNewEditText.getText().toString();
                String description = newTextEditText.getText().toString();

                if(!title.isEmpty() && !description.isEmpty()){
                     Calendar calendar = Calendar.getInstance();
                     int month = calendar.get(Calendar.MONTH) + 1;
                    submitDate = calendar.get(Calendar.YEAR)+"-"+ month + "-" +
                            calendar.get(Calendar.DAY_OF_MONTH) + "T" +
                            calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                            calendar.get(Calendar.MINUTE)+":00.722Z";;
                    if(updateMode){
                        New putNews = new New(title,description,submitDate);
                        Call<Message> call = RetrofitClient.getInstance().getMyApi().putNews(idCourse,news.get_id(),putNews);
                        call.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                try {
                                    int statusCode = response.code();
                                    Message message = response.body();
                                    Toast.makeText(NewTeacherActivity.this,message.getMessage(),Toast.LENGTH_LONG).show();
                                    goBackUpdated();
                                } catch (Exception e){
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = null;
                                    try {
                                        mJson = parser.parse(response.errorBody().string());
                                        Gson gson = new Gson();
                                        Message errorResponse = gson.fromJson(mJson, Message.class);
                                        Toast.makeText(NewTeacherActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {

                            }
                        });
                    } else {//New homework
                        New postNews = new New(title,description,submitDate);
                        Call<Message> call = RetrofitClient.getInstance().getMyApi().postNews(idCourse,postNews);
                        call.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                try {
                                    int statusCode = response.code();
                                    Message message = response.body();
                                    Toast.makeText(NewTeacherActivity.this,message.getMessage(),Toast.LENGTH_LONG).show();
                                    goBackUpdated();
                                } catch (Exception e){
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = null;
                                    try {
                                        mJson = parser.parse(response.errorBody().string());
                                        Gson gson = new Gson();
                                        Message errorResponse = gson.fromJson(mJson, Message.class);
                                        Toast.makeText(NewTeacherActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {

                            }
                        });
                    }
                }

            }
        });
    }
    private void goBackUpdated(){
        Call<GetCourse> call = RetrofitClient.getInstance().getMyApi().getCourse(idCourse);
        call.enqueue(new Callback<GetCourse>() {
            @Override
            public void onResponse(Call<GetCourse> call, Response<GetCourse> response) {
                try {
                    int statusCode = response.code();
                    GetCourse getCourse = response.body();
                    Intent intent = new Intent(NewTeacherActivity.this,CourseTeacherActivity.class);
                    intent.putExtra("course",getCourse.getCourse());
                    intent.putExtra("needApiUpdate",false);
                    startActivity(intent);
                } catch (Exception e){
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        Message errorResponse = gson.fromJson(mJson, Message.class);
                        Toast.makeText(NewTeacherActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCourse> call, Throwable t) {

            }
        });
    }
}