package com.example.mep_digital.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mep_digital.LoginActivity;
import com.example.mep_digital.R;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.ListCourses;
import com.example.mep_digital.model.LoginReturn;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.UpdateTeacherCourse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Intent intent = getIntent();
        idTeacher = intent.getStringExtra("idTeacher");
        //
        listCourseListView = findViewById(R.id.listCourseListView);
        //
        getListViewStrings();
        //
        listCourseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListCourseTeacherActivity.this,CourseTeacherActivity.class);
                intent.putExtra("idTeacher",idTeacher);
                intent.putExtra("idCourse",listCourses.getCourses().get(position).getId());
                intent.putExtra("needApiUpdate",true);
                startActivity(intent);
            }
        });
    }

    private void updateListView(){
        listViewStrings = new ArrayList<>();
        for (int i = 0; i < listCourses.getCourses().size(); i++) {
            listViewStrings.add(listCourses.getCourses().get(i).getName() +
                    "\nId: " + listCourses.getCourses().get(i).getId());
        }
        listCourseListView.setAdapter(new ArrayAdapter<String>(ListCourseTeacherActivity.this,
                android.R.layout.simple_list_item_1, listViewStrings));
    }

    private void getListViewStrings(){
        Call<ListCourses> call = RetrofitClient.getInstance().getMyApi().getTeacherCourses(idTeacher);
        call.enqueue(new Callback<ListCourses>() {
            @Override
            public void onResponse(Call<ListCourses> call, Response<ListCourses> response) {
                try {
                    int statusCode = response.code();
                    listCourses = response.body();
                    updateListView();
                } catch (Exception e){
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        Message errorResponse = gson.fromJson(mJson, Message.class);
                        Toast.makeText(ListCourseTeacherActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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

}