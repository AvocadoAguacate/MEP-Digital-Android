package com.example.mep_digital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mep_digital.Admin.AdminActivity;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.LoginPost;
import com.example.mep_digital.model.LoginReturn;
import com.example.mep_digital.model.LoginType;

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
        //Aqui decido que tipo de usuario es
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
        } else if(spinner.getSelectedItem().toString().contains("Est")){     //"Tea"
            userType = LoginType.teacher.toString();
        }
        LoginPost loginPost = new LoginPost(editTextUser.getText().toString(),
                editTextPassword.getText().toString(),userType);
        Call<LoginReturn> call = RetrofitClient.getInstance().getMyApi().postLogin(loginPost);
        call.enqueue(new Callback<LoginReturn>() {
            @Override
            public void onResponse(Call<LoginReturn> call, Response<LoginReturn> response) {
                int statusCode = response.code();
                LoginReturn loginReturn = response.body();
                try {
                    System.out.println(loginReturn.toString());
                    Toast.makeText(getApplicationContext(), loginReturn.getEmail(),Toast.LENGTH_LONG).show();
                    router();

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Inicio fallido",Toast.LENGTH_LONG).show();
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
        } else if (spinner.getSelectedItem().toString().contains("Est")) {
            //goToStudentActivity();
        } else if (spinner.getSelectedItem().toString().contains("Pro")) {
            //goToTeacherActivity();
        }

    }
}