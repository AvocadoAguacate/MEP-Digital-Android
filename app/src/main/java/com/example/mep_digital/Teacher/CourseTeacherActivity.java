package com.example.mep_digital.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mep_digital.R;
import com.example.mep_digital.model.Course;

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

    }

    private void getDataCourse(){
        Intent intent = getIntent();
        course = (Course)intent.getSerializableExtra("course");
        nameCourseTextView.setText(course.getName());
    }
}