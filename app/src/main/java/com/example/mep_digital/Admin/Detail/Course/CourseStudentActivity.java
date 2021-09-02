package com.example.mep_digital.Admin.Detail.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mep_digital.R;

public class CourseStudentActivity extends AppCompatActivity {

    TextView nameCourseStudentTextView;
    TextView idCourseStudentTextView;
    EditText courseStudentIdEditText;
    Button searchCourseStudentButton;
    Button addCourseStudentButton;
    Button deleteCourseStudentButton;
    Button cancelCourseStudentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_student);
        //
        nameCourseStudentTextView = findViewById(R.id.nameCourseStudentTextView);
        idCourseStudentTextView = findViewById(R.id.idCourseStudentTextView);
        courseStudentIdEditText = findViewById(R.id.courseStudentIdEditText);
        searchCourseStudentButton = findViewById(R.id.searchCourseStudentButton);
        addCourseStudentButton = findViewById(R.id.addCourseStudentButton);
        deleteCourseStudentButton = findViewById(R.id.deleteCourseStudentButton);
        cancelCourseStudentButton = findViewById(R.id.cancelCourseStudentButton);
        //
        loadData();


    }

    private void loadData(){
        Intent intent = getIntent();
        nameCourseStudentTextView.setText(intent.getStringExtra("nameCourse"));
        idCourseStudentTextView.setText(intent.getStringExtra("idCourse"));
    }
}