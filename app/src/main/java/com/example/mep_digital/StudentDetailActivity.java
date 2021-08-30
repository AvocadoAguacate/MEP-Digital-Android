package com.example.mep_digital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.CreateStudent;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.Student;
import com.example.mep_digital.model.UpdateStudent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentDetailActivity extends AppCompatActivity {


    private EditText idStudentEditText;
    private EditText nameStudentEditText;
    private EditText lastName1EditText;
    private EditText lastName2EditText;
    private EditText emailStudentEditText;
    private Spinner studentSelectGradeSpinner;
    private boolean updateMode;

    private Button deleteButton;

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
                UpdateStudent updateStudent = new UpdateStudent(emailStudentEditText.getText().toString(),
                        idStudentEditText.getText().toString(),nameStudentEditText.getText().toString(),
                        lastName1EditText.getText().toString() + " " + lastName2EditText.getText().toString(),
                        studentSelectGradeSpinner.getSelectedItemPosition());
                Call<Message> call = RetrofitClient.getInstance().getMyApi().putStudent(idStudentEditText.getText().toString(),updateStudent);
                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        int statusCode = response.code();
                        Message message = response.body();
                        Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        // Log error here since request failed
                    }
                });

            } else {
                CreateStudent createStudent = new CreateStudent(idStudentEditText.getText().toString(),
                        emailStudentEditText.getText().toString(),idStudentEditText.getText().toString(),
                        nameStudentEditText.getText().toString(),lastName1EditText.getText().toString() +
                        " " + lastName2EditText.getText().toString(),studentSelectGradeSpinner.getSelectedItemPosition());
                Call<Message> call = RetrofitClient.getInstance().getMyApi().postStudent(createStudent);
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
                    int statusCode = response.code();
                    Message message = response.body();
                    Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                    finish();
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
            nameStudentEditText.setText(student.getName());
            lastName1EditText.setText(student.getLastname().split(" ")[0]);
            lastName2EditText.setText(student.getLastname().split(" ")[1]);
            emailStudentEditText.setText(student.getEmail());
            studentSelectGradeSpinner.setSelection(student.getGrade());
            updateMode = true;
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_LONG).show();
        }


    }
}