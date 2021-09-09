package com.example.mep_digital.Admin.Detail.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mep_digital.R;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.GetTeacher;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.Teacher;
import com.example.mep_digital.model.UpdateTeacherCourse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseTeacherActivity extends AppCompatActivity {

    TextView nameCourseTeacherTextView;
    TextView idCourseTeacherTextView;
    EditText courseTeacherIdEditText;
    Button searchCourseTeacherButton;
    Button addCourseTeacherButton;
    Button deleteCourseTeacherButton;
    Button cancelCourseTeacherButton;
    ListView dataCourseTeacherListView;
    Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_teacher);

        //
        nameCourseTeacherTextView = findViewById(R.id.nameCourseTeacherTextView);
        idCourseTeacherTextView = findViewById(R.id.idCourseTeacherTextView);
        courseTeacherIdEditText = findViewById(R.id.courseTeacherIdEditText);
        searchCourseTeacherButton = findViewById(R.id.searchCourseTeacherButton);
        addCourseTeacherButton = findViewById(R.id.addCourseTeacherButton);
        deleteCourseTeacherButton = findViewById(R.id.deleteCourseTeacherButton);
        cancelCourseTeacherButton = findViewById(R.id.cancelCourseTeacherButton);
        dataCourseTeacherListView = findViewById(R.id.dataCourseTeacherListView);
        //Botones
        cancelCourseTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        deleteCourseTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teacherId = courseTeacherIdEditText.getText().toString();
                if(!teacherId.isEmpty()){
                    String idCourse = idCourseTeacherTextView.getText().toString();
                    UpdateTeacherCourse updateTeacherCourse = new UpdateTeacherCourse(teacherId);
                    Call<Message> call = RetrofitClient.getInstance().getMyApi().deleteTeacherCourse(idCourse,updateTeacherCourse);
                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            try {
                                int statusCode = response.code();
                                Message message = response.body();
                                Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                                dataCourseTeacherListView.setAdapter(null);
                            } catch (Exception e){
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = null;
                                try {
                                    mJson = parser.parse(response.errorBody().string());
                                    Gson gson = new Gson();
                                    Message errorResponse = gson.fromJson(mJson, Message.class);
                                    Toast.makeText(CourseTeacherActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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
        });
        searchCourseTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teacherId = courseTeacherIdEditText.getText().toString();
                if(!teacherId.isEmpty()){
                    Call<GetTeacher> call = RetrofitClient.getInstance().getMyApi().getTeacher(teacherId);
                    call.enqueue(new Callback<GetTeacher>() {
                        @Override
                        public void onResponse(Call<GetTeacher> call, Response<GetTeacher> response) {
                            try {
                                int statusCode = response.code();
                                GetTeacher getTeacher = response.body();
                                teacher = getTeacher.getTeacher();
                                updateTeacherDataListView();
                            } catch (Exception e){
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = null;
                                try {
                                    mJson = parser.parse(response.errorBody().string());
                                    Gson gson = new Gson();
                                    Message errorResponse = gson.fromJson(mJson, Message.class);
                                    Toast.makeText(CourseTeacherActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<GetTeacher> call, Throwable t) {

                        }
                    });
                }
            }
        });
        addCourseTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teacherId = courseTeacherIdEditText.getText().toString();
                if(!teacherId.isEmpty()){
                    String idCourse = idCourseTeacherTextView.getText().toString();
                    UpdateTeacherCourse updateTeacherCourse = new UpdateTeacherCourse(teacherId);
                    Call<Message> call = RetrofitClient.getInstance().getMyApi().putTeacherCourse(idCourse,updateTeacherCourse);
                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            try {
                                int statusCode = response.code();
                                Message message = response.body();
                                Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                                dataCourseTeacherListView.setAdapter(null);
                            } catch (Exception e){
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = null;
                                try {
                                    mJson = parser.parse(response.errorBody().string());
                                    Gson gson = new Gson();
                                    Message errorResponse = gson.fromJson(mJson, Message.class);
                                    Toast.makeText(CourseTeacherActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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
        });
        //
        loadData();
    }

    private void loadData(){
        Intent intent = getIntent();
        nameCourseTeacherTextView.setText(intent.getStringExtra("nameCourse"));
        idCourseTeacherTextView.setText(intent.getStringExtra("idCourse"));
        if(!intent.getBooleanExtra("newTeacher",true)){
            teacher = (Teacher)intent.getSerializableExtra("teacher");
            courseTeacherIdEditText.setText(teacher.getId());
            updateTeacherDataListView();
        }
    }

    private void updateTeacherDataListView(){
        ArrayList<String> teacherArray = new ArrayList<>();
        float aproxRating = aproxRating();
        teacherArray.add(teacher.getName() + " " + teacher.getLastname() + "\n" +
                teacher.getEmail() + "\n" +
                "Calificación: "+ aproxRating + "\n");
        dataCourseTeacherListView.setAdapter(new ArrayAdapter<String>(CourseTeacherActivity.this,
                android.R.layout.simple_list_item_1, teacherArray));
    }


    private float aproxRating() {
        int sum = 0;
        if (teacher.getRatings().size() == 0){
            return (float) sum;
        }
        for(int i = 0; i < teacher.getRatings().size(); i++){
            sum += (float)teacher.getRatings().get(i).getRating();
        }
        return (float) (sum/teacher.getRatings().size());
    }


}