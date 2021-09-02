package com.example.mep_digital.Admin.Detail.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mep_digital.R;

public class CourseTeacherActivity extends AppCompatActivity {

    TextView nameCourseTeacherTextView;
    TextView idCourseTeacherTextView;
    EditText courseTeacherIdEditText;
    Button searchCourseTeacherButton;
    Button addCourseTeacherButton;
    Button deleteCourseTeacherButton;
    Button cancelCourseTeacherButton;

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
        //
        loadData();
    }

    private void loadData(){
        Intent intent = getIntent();
        nameCourseTeacherTextView.setText(intent.getStringExtra("nameCourse"));
        idCourseTeacherTextView.setText(intent.getStringExtra("idCourse"));
    }
}