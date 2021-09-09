package com.example.mep_digital.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mep_digital.R;

import java.util.ArrayList;
import java.util.List;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_class);

        NewsListView= findViewById(R.id.newsListView);   // define el Listview
        addNews();

        HomeworkListView= findViewById(R.id.homeworkListView);   // define el Listview
        addHomeworks();

        TeachersListView= findViewById(R.id.teacherListView);   // define el Listview
        TeachersListView.setOnItemClickListener(this);
        addTeachers();
    }

    private void addNews(){
        String text ="Examen Final";
        //cuando se obtengan los datos de la base se agregan a la lista y al adapter
        NewsList.add(text);
        NewsAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,NewsList);
        NewsListView.setAdapter(NewsAdapter);
    }

    private void addHomeworks(){
        String text ="Tr01";

        HomeworkList.add(text);
        HomeworkAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,HomeworkList);
        HomeworkListView.setAdapter(HomeworkAdapter);
    }

    private void addTeachers(){
        String text ="Juan Fuentes";
        TeachersList.add(text);
        TeachersAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,TeachersList);
        TeachersListView.setAdapter(TeachersAdapter);
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