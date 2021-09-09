package com.example.mep_digital.Admin.Detail.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mep_digital.R;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.AddStudentCourse;
import com.example.mep_digital.model.GetCourse;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.Student;
import com.example.mep_digital.model.StudentReturn;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseStudentActivity extends AppCompatActivity {

    TextView nameCourseStudentTextView;
    TextView idCourseStudentTextView;
    EditText courseStudentIdEditText;
    Button searchCourseStudentButton;
    Button addCourseStudentButton;
    Button deleteCourseStudentButton;
    Button cancelCourseStudentButton;
    ListView listCourseStudentListView;
    ListView dataCourseStudentListView;

    List<Student> listStudent;


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
        listCourseStudentListView = findViewById(R.id.listCourseStudentListView);
        dataCourseStudentListView = findViewById(R.id.dataCourseStudentListView);
        //Set buttons
        searchCourseStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentId = courseStudentIdEditText.getText().toString();
                if(!studentId.isEmpty()){
                    Call<StudentReturn> call = RetrofitClient.getInstance().getMyApi().getStudent(studentId);
                    call.enqueue(new Callback<StudentReturn>() {
                        @Override
                        public void onResponse(Call<StudentReturn> call, Response<StudentReturn> response) {
                            try {
                                int statusCode = response.code();
                                StudentReturn studentReturn = response.body();
                                updateDataStudentListView(studentReturn.getStudent());
                            } catch (Exception e){
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = null;
                                try {
                                    mJson = parser.parse(response.errorBody().string());
                                    Gson gson = new Gson();
                                    Message errorResponse = gson.fromJson(mJson, Message.class);
                                    Toast.makeText(CourseStudentActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<StudentReturn> call, Throwable t) {
                            // Log error here since request failed
                        }
                    });
                }
            }
        });
        addCourseStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStudent = courseStudentIdEditText.getText().toString();
                if(!idStudent.isEmpty()){
                    AddStudentCourse addStudentCourse = new AddStudentCourse(idStudent);
                    String idCourse = idCourseStudentTextView.getText().toString();
                    Call<Message> call = RetrofitClient.getInstance().getMyApi().postStudentCourse(idCourse, addStudentCourse);
                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            try {
                                int statusCode = response.code();
                                Message message = response.body();
                                Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                                getListStudentList();
                            } catch (Exception e){
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = null;
                                try {
                                    mJson = parser.parse(response.errorBody().string());
                                    Gson gson = new Gson();
                                    Message errorResponse = gson.fromJson(mJson, Message.class);
                                    Toast.makeText(CourseStudentActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {

                        }
                    });
                }

            }
        });
        deleteCourseStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStudent = courseStudentIdEditText.getText().toString();
                if(!idStudent.isEmpty()){
                    String idCourse = idCourseStudentTextView.getText().toString();
                    Call<Message> call = RetrofitClient.getInstance().getMyApi().deleteStudentCourse(idCourse, idStudent);
                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            try {
                                int statusCode = response.code();
                                Message message = response.body();
                                Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                                getListStudentList();
                            } catch (Exception e){
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = null;
                                try {
                                    mJson = parser.parse(response.errorBody().string());
                                    Gson gson = new Gson();
                                    Message errorResponse = gson.fromJson(mJson, Message.class);
                                    Toast.makeText(CourseStudentActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {

                        }
                    });
                }

            }
        });

        cancelCourseStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //
        loadData();

    }

    private void loadData(){
        Intent intent = getIntent();
        nameCourseStudentTextView.setText(intent.getStringExtra("nameCourse"));
        idCourseStudentTextView.setText(intent.getStringExtra("idCourse"));
        if(!intent.getBooleanExtra("emptyList",true)){
            listStudent = (List<Student>) intent.getSerializableExtra("listStudents");
            updateListStudentListView();
        }
    }

    private void updateListStudentListView(){
        ArrayList<String> listStudentView = new ArrayList<>();
        for(int i = 0; i < listStudent.size(); i++){
            listStudentView.add(
                    listStudent.get(i).getName() + " " + listStudent.get(i).getLastname() +
                            "\nCédula: " + listStudent.get(i).getId()
            );
        }
        listCourseStudentListView.setAdapter(new ArrayAdapter<String>(CourseStudentActivity.this,
                android.R.layout.simple_list_item_1, listStudentView));
    }

    private void updateDataStudentListView(Student student){
        ArrayList<String> arraylistStudentData = new ArrayList<>();
        arraylistStudentData.add(
                student.getName() + " " + student.getLastname() + "\n" +
                        student.getEmail() + "\n" +
                        "Grado: " + student.getGrade()
        );
        dataCourseStudentListView.setAdapter(new ArrayAdapter<String>(CourseStudentActivity.this,
                android.R.layout.simple_list_item_1, arraylistStudentData));
    }

    private void getListStudentList(){
        Call<GetCourse> call = RetrofitClient.getInstance().getMyApi()
                .getCourse(idCourseStudentTextView.getText().toString());
        call.enqueue(new Callback<GetCourse>() {
            @Override
            public void onResponse(Call<GetCourse> call, Response<GetCourse> response) {
                try {
                    int statusCode = response.code();
                    GetCourse getCourse = response.body();
                    listStudent = getCourse.getCourse().getStudents();
                    updateListStudentListView();
                } catch (Exception e){
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        Message errorResponse = gson.fromJson(mJson, Message.class);
                        Toast.makeText(CourseStudentActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCourse> call, Throwable t) {

            }
        });
    }
}