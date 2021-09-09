package com.example.mep_digital.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.mep_digital.R;
import com.example.mep_digital.model.Course;

import java.util.ArrayList;

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
        //
        getData();
        //
        readyListCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        Course course = (Course)intent.getSerializableExtra("course");
        ArrayList<String> students = new ArrayList<>();
        for (int i = 0; i < course.getStudents().size(); i++) {
            students.add(course.getStudents().get(i).getName() + " "+
                    course.getStudents().get(i).getLastname() + "\nCédula: " +
                    course.getStudents().get(i).getId());
        }
        listCourseListView.setAdapter(new ArrayAdapter<String>(StudentsListTeacherActivity.this,
                android.R.layout.simple_list_item_1, students));
    }
}