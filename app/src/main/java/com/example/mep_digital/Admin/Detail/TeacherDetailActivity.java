package com.example.mep_digital.Admin.Detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mep_digital.Admin.AdminActivity;
import com.example.mep_digital.R;
import com.example.mep_digital.Teacher.ListCourseTeacherActivity;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.CreateTeacher;
import com.example.mep_digital.model.ListCourses;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.Teacher;
import com.example.mep_digital.model.UpdateTeacher;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherDetailActivity extends AppCompatActivity {

    private EditText idTeacherEditText;
    private EditText nameTeacherEditText;
    private EditText lastName1TeacherEditText;
    private EditText lastName2TeacherEditText;
    private EditText emailTeacherEditText;
    private EditText passwordTeacher;
    private TextView teacherDetailTextView;
    private Button deleteTeacherButton;
    private TextView qualificationTeacherTextView;
    private TextView studentListCoursesTextView;
    private ListView courseTeacherListView;
    private ListCourses listCourses;

    private boolean updateMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);

        updateMode = false;

        idTeacherEditText = findViewById(R.id.idTeacherEditText);
        nameTeacherEditText = findViewById(R.id.nameTeacherEditText);
        lastName1TeacherEditText = findViewById(R.id.lastName1TeacherEditText);
        lastName2TeacherEditText = findViewById(R.id.lastName2TeacherEditText);
        emailTeacherEditText = findViewById(R.id.emailTeacherEditText);
        teacherDetailTextView = findViewById(R.id.teacherDetailTextView);
        deleteTeacherButton = findViewById(R.id.deleteTeacherButton);
        qualificationTeacherTextView = findViewById(R.id.qualificationTeacherTextView);
        studentListCoursesTextView = findViewById(R.id.studentListCoursesTextView);
        courseTeacherListView = findViewById(R.id.courseTeacherListView);
        passwordTeacher = findViewById(R.id.passwordTeacher);
        //Cargando profesor
        tryLoadData();

    }

    public void saveData(View view){
        if(checkData()){
            if(updateMode){
                String password = "";
                String passwordField = passwordTeacher.getText().toString();
                if(!passwordField.isEmpty()){
                    password = passwordField;
                }
                UpdateTeacher updateTeacher = new UpdateTeacher(emailTeacherEditText.getText().toString(),
                        password,nameTeacherEditText.getText().toString(),
                        lastName1TeacherEditText.getText().toString() + " " + lastName2TeacherEditText.getText().toString());
                Call<Message> call = RetrofitClient.getInstance().getMyApi().putTeacher(idTeacherEditText.getText().toString(),updateTeacher);
                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        try {
                            int statusCode = response.code();
                            Message message = response.body();
                            Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
                        } finally {
                            goBackWithIntent();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        // Log error here since request failed
                    }
                });

            } else {
                String password = passwordTeacher.getText().toString();
                if(password.isEmpty()){
                    password = idTeacherEditText.getText().toString();
                }
                CreateTeacher createTeacher = new CreateTeacher(idTeacherEditText.getText().toString(),
                        emailTeacherEditText.getText().toString(),password,
                        nameTeacherEditText.getText().toString(),lastName1TeacherEditText.getText().toString() +
                        " " + lastName2TeacherEditText.getText().toString(),1);
                Call<Message> call = RetrofitClient.getInstance().getMyApi().postTeacher(createTeacher);
                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        try {
                            int statusCode = response.code();
                            Message message = response.body();
                            Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
                        } finally {
                            goBackWithIntent();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        // Log error here since request failed
                    }
                });
            }
        }

    }

    private boolean checkData() {
        if(idTeacherEditText.getText().toString().isEmpty() ||
                nameTeacherEditText.getText().toString().isEmpty() ||
                lastName1TeacherEditText.getText().toString().isEmpty() ||
                lastName2TeacherEditText.getText().toString().isEmpty() ||
                emailTeacherEditText.getText().toString().isEmpty()
        ){
            return false;
        }
        return true;
    }

    public void deleteData(View view){
        String id = idTeacherEditText.getText().toString();
        if(!id.isEmpty()){
            Call<Message> call = RetrofitClient.getInstance().getMyApi().deleteTeacher(id);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    try {
                        int statusCode = response.code();
                        Message message = response.body();
                        Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Exception e){
                        JsonParser parser = new JsonParser();
                        JsonElement mJson = null;
                        try {
                            mJson = parser.parse(response.errorBody().string());
                            Gson gson = new Gson();
                            Message errorResponse = gson.fromJson(mJson, Message.class);
                            Toast.makeText(TeacherDetailActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } finally {
                        goBackWithIntent();
                    }
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {
                    // Log error here since request failed
                }
            });
        }
    }

    private void tryLoadData(){
        try {
            Intent intent = getIntent();
            Teacher teacher = (Teacher) intent.getSerializableExtra("teacher");
            idTeacherEditText.setText(teacher.getId());
            idTeacherEditText.setEnabled(false);
            nameTeacherEditText.setText(teacher.getName());
            lastName1TeacherEditText.setText(teacher.getLastname().split(" ")[0]);
            lastName2TeacherEditText.setText(teacher.getLastname().split(" ")[1]);
            emailTeacherEditText.setText(teacher.getEmail());
            getListViewStrings();
            updateMode = true;
        } catch (Exception e){
            teacherDetailTextView.setText("Nuevo profesor");
            deleteTeacherButton.setEnabled(false);
            qualificationTeacherTextView.setVisibility(View.INVISIBLE);
            studentListCoursesTextView.setVisibility(View.INVISIBLE);
            courseTeacherListView.setVisibility(View.INVISIBLE);
        }


    }

    private void getListViewStrings(){
        String idTeacher = idTeacherEditText.getText().toString();
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
                        Toast.makeText(TeacherDetailActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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

    private void updateListView(){
        ArrayList<String> listViewStrings = new ArrayList<>();
        for (int i = 0; i < listCourses.getCourses().size(); i++) {
            listViewStrings.add(listCourses.getCourses().get(i).getName() +
                    "\nId: " + listCourses.getCourses().get(i).getId());
        }
        courseTeacherListView.setAdapter(new ArrayAdapter<String>(TeacherDetailActivity.this,
                android.R.layout.simple_list_item_1, listViewStrings));
    }


    public void goBack(View view){
        finish();
    }

    private void goBackWithIntent(){
        Intent intent = new Intent(TeacherDetailActivity.this, AdminActivity.class);
        startActivity(intent);
    }

}