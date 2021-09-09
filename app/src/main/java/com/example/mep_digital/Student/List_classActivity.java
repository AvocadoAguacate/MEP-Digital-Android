package com.example.mep_digital.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mep_digital.R;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import com.example.mep_digital.model.ListCourses;
import com.example.mep_digital.model.Course;

public class List_classActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{

    private ListView mListView;
    private List<String> mList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studen_list_class);

        mListView= findViewById(R.id.coursesListView);
        mListView.setOnItemClickListener(this);          // permite hacer click en los elementos
        addCourses();

        ////////////////////////////

        ///////////////////////////


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(this, "item clicked :" + i , Toast.LENGTH_SHORT).show(); // imprime la posicion del elemento con i como numero de posicion
        startActivity(new Intent(List_classActivity.this,Student_classActivity.class));

    }

    private void addCourses(){


        String texto ="Matematicas";
        //cuando se obtengan los datos de la base se agregan a la lista y al adapter
        mList.add(texto);
        mAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mList);
        mListView.setAdapter(mAdapter);

    }

}