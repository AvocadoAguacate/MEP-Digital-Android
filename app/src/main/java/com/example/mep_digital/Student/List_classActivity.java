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
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.Message;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.mep_digital.model.ListCourses;
import com.example.mep_digital.model.Course;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class List_classActivity extends AppCompatActivity{

    private ListView mListView;
    private List<String> mList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private String studentId;
    private List<Course> listCourses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studen_list_class);
        ////////////////////////////
        mListView= findViewById(R.id.coursesListView);
        ////////////////////////////
        getCoures();
        ////////////////////////////
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(List_classActivity.this,Student_classActivity.class);
                intent.putExtra("courseId",listCourses.get(position).getId());
                startActivity(intent);
            }
        });          // permite hacer click en los elementos
        ////////////////////////////

        ///////////////////////////


    }

    private void getCoures() {
        Intent intent = getIntent();
        studentId = intent.getStringExtra("studentId");
        Call<ListCourses> call = RetrofitClient.getInstance().getMyApi().getStudentCourses(studentId);
        call.enqueue(new Callback<ListCourses>() {
            @Override
            public void onResponse(Call<ListCourses> call, Response<ListCourses> response) {
                try {
                    int statusCode = response.code();
                    ListCourses getCourses = response.body();
                    listCourses = getCourses.getCourses();
                    addCourses();
                } catch (Exception e){
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        Message errorResponse = gson.fromJson(mJson, Message.class);
                        Toast.makeText(List_classActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ListCourses> call, Throwable t) {

            }
        });

    }

    private void addCourses(){
//        Cargue los cursos al listview desde listCourses
//        listCourses.get(0).getName();
//        listCourses.get(0).getId();
        String texto ="Matematicas";
        //cuando se obtengan los datos de la base se agregan a la lista y al adapter
        mList.add(texto);
        mAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mList);
        mListView.setAdapter(mAdapter);

    }

}