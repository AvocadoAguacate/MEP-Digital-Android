package com.example.mep_digital.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.mep_digital.R;

public class StudentsListTeacherActivity extends AppCompatActivity {

    ListView listCourseListView;
    Button readyListCourseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list_teacher);
        //
        listCourseListView = findViewById(R.id.listCourseListView);
        readyListCourseButton = findViewById(R.id.readyListCourseButton);
    }
}