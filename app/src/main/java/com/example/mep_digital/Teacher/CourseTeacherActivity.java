package com.example.mep_digital.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mep_digital.R;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.Assignment;
import com.example.mep_digital.model.Course;
import com.example.mep_digital.model.GetCourse;
import com.example.mep_digital.model.ListCourses;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.News;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseTeacherActivity extends AppCompatActivity {

    Course course;
    TextView nameCourseTextView;
    ListView newsCourseTeacherListView;
    ListView homeworksCourseTeacherListView;
    Button newNewButton;
    Button newHomeworkButton;
    Button chatCourseButton;
    Button studentsListButton;
    Button backButton;
    List<News> listNews;
    List<Assignment> listAssigments;
    String idTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_teacher2);
        //
        nameCourseTextView = findViewById(R.id.nameCourseTextView);
        newsCourseTeacherListView = findViewById(R.id.newsCourseTeacherListView);
        homeworksCourseTeacherListView = findViewById(R.id.homeworksCourseTeacherListView);
        newNewButton = findViewById(R.id.newNewButton);
        newHomeworkButton = findViewById(R.id.newHomeworkButton);
        chatCourseButton = findViewById(R.id.chatCourseButton);
        studentsListButton = findViewById(R.id.studentsListButton);
        backButton = findViewById(R.id.backButton);
        //
        getDataCourse();
        //
        setButtons();
        //
        setListViewOnClick();
    }


    private void setListViewOnClick() {
        newsCourseTeacherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CourseTeacherActivity.this,NewTeacherActivity.class);
                intent.putExtra("updateMode",true);
                intent.putExtra("idCourse", course.getId());
                intent.putExtra("new",course.getNews().get(position));
                startActivity(intent);
            }
        });
        homeworksCourseTeacherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CourseTeacherActivity.this,AssigmentTeacherActivity.class);
                intent.putExtra("updateMode",true);
                intent.putExtra("idCourse", course.getId());
                intent.putExtra("homework",course.getAssignments().get(position));
                startActivity(intent);
            }
        });
    }

    private void setButtons() {
        newNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseTeacherActivity.this,NewTeacherActivity.class);
                intent.putExtra("updateMode",false);
                intent.putExtra("idCourse", course.getId());
                startActivity(intent);
            }
        });
        newHomeworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseTeacherActivity.this,AssigmentTeacherActivity.class);
                intent.putExtra("updateMode",false);
                intent.putExtra("idCourse", course.getId());
                startActivity(intent);
            }
        });
        chatCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseTeacherActivity.this,ChatTeacherActivity.class);
                intent.putExtra("course",course);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseTeacherActivity.this,ListCourseTeacherActivity.class);
                intent.putExtra("idTeacher",course.getTeacher().getId());
                startActivity(intent);
            }
        });
        studentsListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseTeacherActivity.this,StudentsListTeacherActivity.class);
                intent.putExtra("course",course);
                startActivity(intent);
            }
        });
    }

    private void getDataCourse(){
        Intent intent = getIntent();
        boolean needApiUpdate = intent.getBooleanExtra("needApiUpdate",true);
        String idCourse = intent.getStringExtra("idCourse");
        if(needApiUpdate){
            Call<GetCourse> call = RetrofitClient.getInstance().getMyApi().getCourse(idCourse);
            call.enqueue(new Callback<GetCourse>() {
                @Override
                public void onResponse(Call<GetCourse> call, Response<GetCourse> response) {
                    try {
                        int statusCode = response.code();
                        GetCourse getCourse = response.body();
                        course = getCourse.getCourse();
                        loadDataCourse();
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
                        } finally {
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetCourse> call, Throwable t) {

                }
            });
        } else {
            course = (Course)intent.getSerializableExtra("course");
            idTeacher = intent.getStringExtra("idTeacher");
            loadDataCourse();
        }

    }
    private void loadDataCourse(){
        nameCourseTextView.setText(course.getName());
        listNews = course.getNews();
        updateNewsLisView();
        listAssigments = course.getAssignments();
        updateAssigmentsLisView();
    }

    private void updateAssigmentsLisView() {
        ArrayList<String> listStringsAssgimnents = new ArrayList<>();
        for (int i = 0; i < listAssigments.size(); i++) {
            listStringsAssgimnents.add(listAssigments.get(i).getTitle() +
                    "\n" + listAssigments.get(i).getDescription() +
                    "\nFecha de entrega: " + listAssigments.get(i).getSubmitDate());
        }
        homeworksCourseTeacherListView.setAdapter(new ArrayAdapter<String>(CourseTeacherActivity.this,
                android.R.layout.simple_list_item_1, listStringsAssgimnents));
    }

    private void updateNewsLisView() {
        ArrayList<String> listStringsNews = new ArrayList<>();
        for (int i = 0; i < listNews.size(); i++) {
            listStringsNews.add(listNews.get(i).getTitle() +
                    "\n" + listNews.get(i).getMessage()+
                    "\nFecha de publicación: " + listNews.get(i).getDate());
        }
        newsCourseTeacherListView.setAdapter(new ArrayAdapter<String>(CourseTeacherActivity.this,
                android.R.layout.simple_list_item_1, listStringsNews));
    }
}