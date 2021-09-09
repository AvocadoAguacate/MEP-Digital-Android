package com.example.mep_digital.Student;

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
import com.example.mep_digital.Teacher.CourseTeacherActivity;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.Assignment;
import com.example.mep_digital.model.Course;
import com.example.mep_digital.model.GetCourse;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.News;
import com.example.mep_digital.model.Teacher;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Student_classActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView NewsListView;
    private List<String> NewsList = new ArrayList<>();
    private ArrayAdapter<String> NewsAdapter;

    private ListView HomeworkListView;
    private List<String> HomeworkList = new ArrayList<>();
    private ArrayAdapter<String> HomeworkAdapter;

    private ListView TeachersListView;
    private List<String> TeachersList = new ArrayList<>();
    private ArrayAdapter<String> TeachersAdapter;

    private Button chatStudentButton;
    private String studentId;
    private String courseId;
    private Course course;

    private String text;

    private TextView nameTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_class);

        getData();

        NewsListView= findViewById(R.id.newsListView);   // define el Listview
        HomeworkListView= findViewById(R.id.homeworkListView);   // define el Listview
        TeachersListView= findViewById(R.id.teacherListView);   // define el Listview
        TeachersListView.setOnItemClickListener(this);
        chatStudentButton= findViewById(R.id.chatStudentButton);
        nameTextView= findViewById(R.id.classNameTextView);
        ///////////////////////////////////
        chatStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Student_classActivity.this,Student_ChatActivity.class);
                intent.putExtra("course",course);
                intent.putExtra("studentId",studentId);
                startActivity(intent);
            }
        });
        TeachersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Student_classActivity.this,Student_teacherActivity.class);
                intent.putExtra("teacher",course.getTeacher());
                intent.putExtra("studentId",studentId);
                startActivity(intent);
            }
        });

    }

    private void updateAll() {
        //Ya la info debe estar en course
        addCourseName();
        addNews();
        addHomeworks();
        addTeachers();
    }


    private void getData() {
        Intent intent = getIntent();
        studentId = intent.getStringExtra("studentId");
        boolean needApiUpdate = intent.getBooleanExtra("needApiUpdate",true);
        if(needApiUpdate){
            courseId = intent.getStringExtra("courseId");
            Call<GetCourse> call = RetrofitClient.getInstance().getMyApi().getCourse(courseId);
            call.enqueue(new Callback<GetCourse>() {
                @Override
                public void onResponse(Call<GetCourse> call, Response<GetCourse> response) {
                    try {
                        int statusCode = response.code();
                        GetCourse getCourse = response.body();
                        course = getCourse.getCourse();
                        updateAll();
                    } catch (Exception e){
                        JsonParser parser = new JsonParser();
                        JsonElement mJson = null;
                        try {
                            mJson = parser.parse(response.errorBody().string());
                            Gson gson = new Gson();
                            Message errorResponse = gson.fromJson(mJson, Message.class);
                            Toast.makeText(Student_classActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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
            updateAll();
        }

    }

    private void addNews(){
        List<News>ListNews;
        ListNews=course.getNews();
        for(int i=0;i<ListNews.size();i++){
            text=ListNews.get(i).getTitle();
            NewsList.add(text);
            NewsAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,NewsList);
            NewsListView.setAdapter(NewsAdapter);
        }
    }

    private void addHomeworks(){
        List<Assignment>ListAssignment;
        ListAssignment=course.getAssignments();
        for(int i=0;i<ListAssignment.size();i++){
            text=ListAssignment.get(i).getTitle();
            HomeworkList.add(text);
            HomeworkAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,HomeworkList);
            HomeworkListView.setAdapter(HomeworkAdapter);
        }
    }

    private void addTeachers(){
        Teacher teacher;
        teacher=course.getTeacher();
        text=teacher.getName();
        TeachersList.add(text);
        TeachersAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,TeachersList);
        TeachersListView.setAdapter(TeachersAdapter);
    }

    private void addCourseName(){
        text=course.getName();
        nameTextView.setText(text);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, "item clicked :" + i , Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Student_classActivity.this,Student_teacherActivity.class));

    }
    // cambio de vista para el boton
    private void ChangeView(View view){ finish();
         startActivity(new Intent(Student_classActivity.this,List_classActivity.class));
         Intent objI = new Intent(Student_classActivity.this,List_classActivity.class);
         startActivity(objI);
    }
}