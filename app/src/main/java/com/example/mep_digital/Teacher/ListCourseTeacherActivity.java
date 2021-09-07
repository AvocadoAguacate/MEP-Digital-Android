package com.example.mep_digital.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mep_digital.R;
import com.example.mep_digital.model.ListCourses;

import java.util.ArrayList;

public class ListCourseTeacherActivity extends AppCompatActivity {

    ListView listCourseListView;
    ArrayList<String> listViewStrings;
    String idTeacher;
    ListCourses listCourses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_course_teacher);
        //
        listCourseListView = findViewById(R.id.listCourseListView);
        //
        listCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListCourseTeacherActivity.this,CourseTeacherActivity.class);
                intent.putExtra("course", listCourses.getCourses().get(position));
                startActivity(intent);
            }
        });
        //
        getListViewStrings();
        //
        Intent intent = getIntent();
        idTeacher = intent.getStringExtra("idTeacher");

    }

    private void updateListView(){
        listCourseListView.setAdapter(new ArrayAdapter<String>(ListCourseTeacherActivity.this,
                android.R.layout.simple_list_item_1, listViewStrings));
    }

    private void getListViewStrings(){
        //call
        //for
        updateListView();
    }

}