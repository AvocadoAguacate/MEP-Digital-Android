package com.example.mep_digital.Admin.Detail.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mep_digital.R;

public class CourseTeacherActivity extends AppCompatActivity {

    TextView nameCourseTeacherTextView;
    TextView idCourseTeacherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_teacher);

        //
        nameCourseTeacherTextView = findViewById(R.id.nameCourseTeacherTextView);
        idCourseTeacherTextView = findViewById(R.id.idCourseTeacherTextView);
        Intent intent = getIntent();
        nameCourseTeacherTextView.setText(intent.getStringExtra("nameCourse"));
        idCourseTeacherTextView.setText(intent.getStringExtra("idCourse"));
    }
}