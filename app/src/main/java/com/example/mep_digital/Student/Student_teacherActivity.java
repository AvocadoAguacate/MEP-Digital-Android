package com.example.mep_digital.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mep_digital.R;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.PutRating;
import com.example.mep_digital.model.Teacher;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Student_teacherActivity extends AppCompatActivity {

    private Spinner stars;
    private  ArrayAdapter<String> SpinnerAdapter;

    private ImageButton SendChatButton;
    private EditText ChatText;


    private ListView ChatListView;
    private List<String> ChatList = new ArrayList<>();
    private ArrayAdapter<String> ChatAdapter;

    private Button backStudentButton;

    private Teacher teacher;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_teacher);

        getData();

        backStudentButton = findViewById(R.id.backStudentButton);
        backStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        stars=(Spinner)findViewById(R.id.qualificationSpinner);
        String [] calification_array= {"Seleccione una calificación","1 estrella","2 estrellas", "3 estrellas", "4 estrellas"," 5 estrellas"};
        SpinnerAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, calification_array);
        stars.setAdapter(SpinnerAdapter);
        stars.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    PutRating putRating = new PutRating(studentId,position);
                    Call<Message> call = RetrofitClient.getInstance().getMyApi().putRating(teacher.getId(),putRating);
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
                                    Toast.makeText(Student_teacherActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getData() {
        Intent intent = getIntent();
        teacher = (Teacher)intent.getSerializableExtra("teacher");
        studentId = intent.getStringExtra("studentId");
        //Saque toda la info de teacher
    }


    private void ChangeView(View view){
        startActivity(new Intent(Student_teacherActivity.this,Student_classActivity.class));
        Intent objI = new Intent(Student_teacherActivity.this,Student_classActivity.class);
        startActivity(objI);
    }


}