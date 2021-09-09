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

import com.example.mep_digital.Admin.AdminActivity;
import com.example.mep_digital.Student.List_classActivity;
import com.example.mep_digital.Admin.Detail.StudentDetailActivity;
import com.example.mep_digital.Teacher.ListCourseTeacherActivity;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.LoginPost;
import com.example.mep_digital.model.LoginReturn;
import com.example.mep_digital.model.LoginType;
import com.example.mep_digital.model.Message;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class LoginActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText editTextUser;
    private EditText editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //TextField
        editTextUser=(EditText)findViewById(R.id.editTextUser);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        //Spniner login type
        spinner=(Spinner)findViewById(R.id.spinnerLogin);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.login_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    /**
     * Escoge la ruta adecuada dependiendo el tipo de usuario
     * @param view
     */
    public void login(View view){


        if(checkLogin()){
            postLogin();
        } else {
            Toast.makeText(getApplicationContext(), "Faltan datos por ingresar",Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Crea el intent para cambiar al activity para Admin
     */
    private void goToAdminActivity(){
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }



    private void goToStudentActivity(){
        Intent intent = new Intent(this, List_classActivity.class);
        startActivity(intent);

    }

    private void goToTeacherActivity(String idTeacher){
        Intent intent = new Intent(this, ListCourseTeacherActivity.class);
        intent.putExtra("idTeacher",idTeacher);
        startActivity(intent);
    }

    public void goToAdminActivityFast(View view){
        goToTeacherActivity("12");
    }

    private boolean checkLogin(){
        if(editTextUser.getText().toString().isEmpty() ||
        editTextPassword.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }

    private void postLogin(){
        String userType = LoginType.admin.toString();
        if (spinner.getSelectedItem().toString().contains("Adm")){
            userType = LoginType.admin.toString();
        } else if(spinner.getSelectedItem().toString().contains("Est")){
            userType = LoginType.student.toString();
        } else if(spinner.getSelectedItem().toString().contains("Pro")){
            userType = LoginType.teacher.toString();
        }
        LoginPost loginPost = new LoginPost(editTextUser.getText().toString(),
                editTextPassword.getText().toString(),userType);
        //button.setText(loginPost.toString());
        Call<LoginReturn> call = RetrofitClient.getInstance().getMyApi().postLogin(loginPost);
        call.enqueue(new Callback<LoginReturn>() {
            @Override
            public void onResponse(Call<LoginReturn> call, Response<LoginReturn> response) {
                int statusCode = response.code();
                LoginReturn loginReturn = response.body();
                try {
                    Toast.makeText(getApplicationContext(), loginReturn.getEmail(),Toast.LENGTH_LONG).show();
                    router();

                }catch (Exception e){
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        Message errorResponse = gson.fromJson(mJson, Message.class);
                        Toast.makeText(LoginActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginReturn> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Inicio fallido",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void router(){
        if(spinner.getSelectedItem().toString().contains("Adm")){
            goToAdminActivity();
        } else {
            String userId = editTextUser.getText().toString();
            if (spinner.getSelectedItem().toString().contains("Est")) {
            goToStudentActivity();
            } else if (spinner.getSelectedItem().toString().contains("Pro")) {
                goToTeacherActivity(userId);
            }
        }

    }
}