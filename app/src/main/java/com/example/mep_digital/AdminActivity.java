package com.example.mep_digital;

import static androidx.navigation.Navigation.findNavController;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mep_digital.model.Student;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mep_digital.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = findNavController(this, R.id.nav_host_fragment_activity_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public void goTeacherDetail(View view){
        Intent intent = new Intent(binding.getRoot().getContext(), TeacherDetailActivity.class);
        startActivity(intent);
    }

    public void goStudentDetail(View view){
        Intent intent = new Intent(binding.getRoot().getContext(), StudentDetailActivity.class);
        startActivity(intent);
    }

}