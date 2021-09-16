package com.example.mep_digital.Teacher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mep_digital.Admin.Detail.Course.ClassDetailActivity;
import com.example.mep_digital.R;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.Assignment;
import com.example.mep_digital.model.Assignments;
import com.example.mep_digital.model.GetCourse;
import com.example.mep_digital.model.Message;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.time.Month;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssigmentTeacherActivity extends AppCompatActivity {

    EditText nameHomeworkEditText;
    Button selectTimeHomeworkButton;
    Button saveHomeworkButton;
    Button deleteHomeworkButton;
    Button cancelHomeworkButton;
    EditText homeworkTeacherEditText;
    Assignment assignment;
    boolean updateMode;
    String idCourse;
    Calendar calendar;
    int year,month,dayOfMonth,targetHour,targetMinute;
    String submitDate;

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigment_teacher);

        //
        nameHomeworkEditText = findViewById(R.id.nameHomeworkEditText);
        selectTimeHomeworkButton = findViewById(R.id.selectTimeHomeworkButton);
        homeworkTeacherEditText = findViewById(R.id.homeworkTeacherEditText);
        saveHomeworkButton = findViewById(R.id.saveHomeworkButton);
        deleteHomeworkButton = findViewById(R.id.deleteHomeworkButton);
        cancelHomeworkButton = findViewById(R.id.cancelHomeworkButton);
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
            assignment = (Assignment) intent.getSerializableExtra("homework");
            nameHomeworkEditText.setText(assignment.getTitle());
            selectTimeHomeworkButton.setText(assignment.getSubmitDate());
            homeworkTeacherEditText.setText(assignment.getDescription());
            submitDate = assignment.getSubmitDate();
        } else {
            deleteHomeworkButton.setEnabled(false);
        }

    }
    private void setButtons(){
        cancelHomeworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        deleteHomeworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Message> call = RetrofitClient.getInstance().getMyApi().deleteAssignments(idCourse,assignment.get_id());
                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        try {
                            int statusCode = response.code();
                            Message message = response.body();
                            Toast.makeText(AssigmentTeacherActivity.this,message.getMessage(),Toast.LENGTH_LONG).show();
                            goBackUpdated();
                        } catch (Exception e){
                            JsonParser parser = new JsonParser();
                            JsonElement mJson = null;
                            try {
                                mJson = parser.parse(response.errorBody().string());
                                Gson gson = new Gson();
                                Message errorResponse = gson.fromJson(mJson, Message.class);
                                Toast.makeText(AssigmentTeacherActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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
        selectTimeHomeworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDate = "";
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AssigmentTeacherActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                int monthU = month + 1;
                                submitDate = year+"-"+monthU +"-"+dayOfMonth+"T";
                                TimePickerDialog timePickerDialog = new TimePickerDialog(
                                        AssigmentTeacherActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                targetHour = hourOfDay;
                                                targetMinute = minute;
                                                submitDate += targetHour+":"+targetMinute+":00.722Z";
                                                Toast.makeText(AssigmentTeacherActivity.this,submitDate,Toast.LENGTH_LONG).show();
                                                selectTimeHomeworkButton.setText("Entrega: "
                                                        +dayOfMonth + "/"+ (month + 1)+ "/"+year +
                                                        " a las "+ targetHour + ":"+ targetMinute);
                                            }
                                        },12,0,true);
                                timePickerDialog.updateTime(targetHour,targetMinute);
                                timePickerDialog.show();
                            }
                        },year,month,dayOfMonth);
                datePickerDialog.show();
            }
        });
        saveHomeworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = nameHomeworkEditText.getText().toString();
                String description = homeworkTeacherEditText.getText().toString();
                if(!title.isEmpty() && !description.isEmpty() && submitDate.contains(".722Z")){
                    if(updateMode){
                        Assignments assignments = new Assignments(title,description,submitDate);
                        Call<Message> call = RetrofitClient.getInstance().getMyApi().putAssignments(idCourse,assignment.get_id(),assignments);
                        call.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                try {
                                    int statusCode = response.code();
                                    Message message = response.body();
                                    Toast.makeText(AssigmentTeacherActivity.this,message.getMessage(),Toast.LENGTH_LONG).show();
                                    goBackUpdated();
                                } catch (Exception e){
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = null;
                                    try {
                                        mJson = parser.parse(response.errorBody().string());
                                        Gson gson = new Gson();
                                        Message errorResponse = gson.fromJson(mJson, Message.class);
                                        Toast.makeText(AssigmentTeacherActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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
                        Assignments assignments = new Assignments(title,description,submitDate);
                        Call<Message> call = RetrofitClient.getInstance().getMyApi().postAssignments(idCourse,assignments);
                        call.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                try {
                                    int statusCode = response.code();
                                    Message message = response.body();
                                    Toast.makeText(AssigmentTeacherActivity.this,message.getMessage(),Toast.LENGTH_LONG).show();
                                    goBackUpdated();
                                } catch (Exception e){
                                    JsonParser parser = new JsonParser();
                                    JsonElement mJson = null;
                                    try {
                                        mJson = parser.parse(response.errorBody().string());
                                        Gson gson = new Gson();
                                        Message errorResponse = gson.fromJson(mJson, Message.class);
                                        Toast.makeText(AssigmentTeacherActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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
                    Intent intent = new Intent(AssigmentTeacherActivity.this,CourseTeacherActivity.class);
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
                        Toast.makeText(AssigmentTeacherActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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