package com.example.mep_digital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.CreateTeacher;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.Teacher;
import com.example.mep_digital.model.UpdateTeacher;

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

        //Cargando estudiante
        tryLoadData();

    }

    public void saveData(View view){
        if(checkData()){
            if(updateMode){
                UpdateTeacher updateTeacher = new UpdateTeacher(emailTeacherEditText.getText().toString(),
                        idTeacherEditText.getText().toString(),nameTeacherEditText.getText().toString(),
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
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        // Log error here since request failed
                    }
                });

            } else {
                CreateTeacher createTeacher = new CreateTeacher(idTeacherEditText.getText().toString(),
                        emailTeacherEditText.getText().toString(),idTeacherEditText.getText().toString(),
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
                            finish();
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
            Call<Message> call = RetrofitClient.getInstance().getMyApi().deleteStudent(id);
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
                        finish();
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
            nameTeacherEditText.setText(teacher.getName());
            lastName1TeacherEditText.setText(teacher.getLastname().split(" ")[0]);
            lastName2TeacherEditText.setText(teacher.getLastname().split(" ")[1]);
            emailTeacherEditText.setText(teacher.getEmail());
            updateMode = true;
        } catch (Exception e){
            //New teacher
            Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
        }


    }


    public void goBack(View view){
        finish();
    }

}