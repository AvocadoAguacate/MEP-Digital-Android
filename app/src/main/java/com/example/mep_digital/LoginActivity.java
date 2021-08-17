package com.example.mep_digital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class LoginActivity extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
    public void router(View view){
        //Aqui decido que tipo de usuario es
        if(spinner.getSelectedItem().toString().contains("Adm")) {
            goToAdminActivity();
        }
    }

    /**
     * Crea el intent para cambiar al activity para Admin
     */
    private void goToAdminActivity(){
        Intent intent = new Intent(this,AdminActivity.class);
        startActivity(intent);
    }
}