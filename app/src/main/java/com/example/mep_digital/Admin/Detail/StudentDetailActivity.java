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
import com.example.mep_digital.Admin.Detail.Course.ClassDetailActivity;
import com.example.mep_digital.R;
import com.example.mep_digital.Student.List_classActivity;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.Course;
import com.example.mep_digital.model.CreateStudent;
import com.example.mep_digital.model.ListCourses;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.Student;
import com.example.mep_digital.model.UpdateStudent;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentDetailActivity extends AppCompatActivity {


    private EditText idStudentEditText;
    private EditText nameStudentEditText;
    private EditText lastName1EditText;
    private EditText lastName2EditText;
    private EditText emailStudentEditText;
    private EditText passwordStudent;
    private Spinner studentSelectGradeSpinner;
    private TextView studentDetailTextView;
    private TextView studentListCoursesTextView;
    private ListView coursesStudentListView;
    private boolean updateMode;
    private Button deleteButton;
    private List<Course> listCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        //bit update
        updateMode = false;
        //finders
        idStudentEditText = findViewById(R.id.idStudentEditText);
        nameStudentEditText = findViewById(R.id.nameStudentEditText);
        lastName1EditText = findViewById(R.id.lastName1TeacherEditText);
        lastName2EditText = findViewById(R.id.lastName2TeacherEditText);
        emailStudentEditText = findViewById(R.id.emailStudentEditText);
        studentSelectGradeSpinner = findViewById(R.id.studentSelectGradeSpinner);
        deleteButton = findViewById(R.id.deleteStudentButton);
        studentDetailTextView = findViewById(R.id.studentDetailTextView);
        studentListCoursesTextView = findViewById(R.id.studentListCoursesTextView);
        coursesStudentListView = findViewById(R.id.coursesStudentListView);
        passwordStudent = findViewById(R.id.passwordStudent);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteData(v);
            }
        });
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grade_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        studentSelectGradeSpinner.setAdapter(adapter);


        //Cargando estudiante
        tryLoadData();
    }

    public void goBack(View view){
        finish();
    }

    public void saveData(View view){
        if(checkData()){
            if(updateMode){
                String password = "";
                String passwordField = passwordStudent.getText().toString();
                if(!passwordField.isEmpty()){
                    password = passwordField;
                }
                UpdateStudent updateStudent = new UpdateStudent(emailStudentEditText.getText().toString(),
                        password,nameStudentEditText.getText().toString(),
                        lastName1EditText.getText().toString() + " " + lastName2EditText.getText().toString(),
                        studentSelectGradeSpinner.getSelectedItemPosition() + 1);
                Call<Message> call = RetrofitClient.getInstance().getMyApi().putStudent(idStudentEditText.getText().toString(),updateStudent);
                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        try {
                            int statusCode = response.code();
                            Message message = response.body();
                            Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (Exception e ){
                            JsonParser parser = new JsonParser();
                            JsonElement mJson = null;
                            try {
                                mJson = parser.parse(response.errorBody().string());
                                Gson gson = new Gson();
                                Message errorResponse = gson.fromJson(mJson, Message.class);
                                Toast.makeText(StudentDetailActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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

            } else { //New student
                String password = passwordStudent.getText().toString();
                if(password.isEmpty()){
                    password = idStudentEditText.getText().toString();
                }
                CreateStudent createStudent = new CreateStudent(idStudentEditText.getText().toString(),
                        emailStudentEditText.getText().toString(),password,
                        nameStudentEditText.getText().toString(),lastName1EditText.getText().toString() +
                        " " + lastName2EditText.getText().toString(),
                        studentSelectGradeSpinner.getSelectedItemPosition() + 1);
                Call<Message> call = RetrofitClient.getInstance().getMyApi().postStudent(createStudent);
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
                                Toast.makeText(StudentDetailActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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

    }

    private boolean checkData() {
        if(idStudentEditText.getText().toString().isEmpty() ||
                nameStudentEditText.getText().toString().isEmpty() ||
                lastName1EditText.getText().toString().isEmpty() ||
                lastName2EditText.getText().toString().isEmpty() ||
                emailStudentEditText.getText().toString().isEmpty()
        ){
            return false;
        }

        return true;
    }

    public void deleteData(View view){
        String id = idStudentEditText.getText().toString();
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
                        JsonParser parser = new JsonParser();
                        JsonElement mJson = null;
                        try {
                            mJson = parser.parse(response.errorBody().string());
                            Gson gson = new Gson();
                            Message errorResponse = gson.fromJson(mJson, Message.class);
                            Toast.makeText(StudentDetailActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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
            Student student = (Student)intent.getSerializableExtra("student");
            idStudentEditText.setText(student.getId());
            idStudentEditText.setEnabled(false);
            nameStudentEditText.setText(student.getName());
            if(student.getLastname().contains(" ")){
                lastName1EditText.setText(student.getLastname().split(" ")[0]);
                lastName2EditText.setText(student.getLastname().split(" ")[1]);
            } else {
                lastName1EditText.setText(student.getLastname());
                lastName2EditText.setText(student.getLastname());
            }
            emailStudentEditText.setText(student.getEmail());
            studentSelectGradeSpinner.setSelection(student.getGrade() - 1);
            updateMode = true;
            getCourses();
        } catch (Exception e){
            studentDetailTextView.setText("Nuevo estudiante");
            deleteButton.setEnabled(false);
            coursesStudentListView.setVisibility(View.INVISIBLE);
            studentListCoursesTextView.setVisibility(View.INVISIBLE);
        }


    }
    private void getCourses() {
        String studentId = idStudentEditText.getText().toString();
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
                        Toast.makeText(StudentDetailActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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
        String text = "";
        ArrayList<String> mList = new ArrayList<>();
        for(int i=0; i<listCourses.size();i++){
            text =listCourses.get(i).getName() + "\n" + listCourses.get(i).getId();
            mList.add(text);
            ArrayAdapter mAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mList);
            coursesStudentListView.setAdapter(mAdapter);
        }
    }

    private void goBackWithIntent(){
        Intent intent = new Intent(StudentDetailActivity.this, AdminActivity.class);
        startActivity(intent);
    }

}