package com.example.mep_digital.Admin.Detail.Course;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mep_digital.R;

import java.util.ArrayList;

public class ClassDetailActivity extends AppCompatActivity  {

    Button selecDaysButton;
    Button selectStartTimeButton;
    Button adminClassTeacherButton;
    Button adminClassStudentsButton;
    Button selectFinishTimeButton;
    EditText idCourseAdminEditText;
    EditText nameCourseAdminEditText;

    boolean[] checkedDays;
    String[] daysWeek;
    ArrayList<String> selectedDays = new ArrayList<>();

    int startHour,startMinute,finishHour,finishMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);
        //
        selecDaysButton = findViewById(R.id.selecDaysButton);
        selectStartTimeButton = findViewById(R.id.selectStartTimeButton);
        selectFinishTimeButton = findViewById(R.id.selectFinishTimeButton);
        adminClassTeacherButton = findViewById(R.id.adminClassTeacherButton);
        adminClassStudentsButton = findViewById(R.id.adminClassStudentButton);
        idCourseAdminEditText = findViewById(R.id.idCourseAdminEditText);
        nameCourseAdminEditText = findViewById(R.id.nameCourseAdminEditText);
        //
        daysWeek = getResources().getStringArray(R.array.week_days);
        checkedDays = new boolean[daysWeek.length];
        //Menu de selección de días
        selecDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ClassDetailActivity.this);
                mBuilder.setTitle(R.string.select_days_alert_dialog);
                mBuilder.setMultiChoiceItems(daysWeek, checkedDays, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            if(!selectedDays.contains(which)){
                                selectedDays.add(daysWeek[which]);
                            }
                        } else if(selectedDays.contains(daysWeek[which])){
                            selectedDays.remove(daysWeek[which]);
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.accept_alert_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i = 0; i < selectedDays.size(); i++){
                            item += selectedDays.get(i);
                            if (i != selectedDays.size()-1){
                                item += " , ";
                            }
                        }
                        if(selectedDays.size()>0){
                            selecDaysButton.setText(item);
                        } else {
                            selecDaysButton.setText(R.string.select_days);
                        }

                    }
                });
                mBuilder.setNegativeButton(R.string.cancel_alert_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = mBuilder.create();
                alertDialog.show();
            }
        });

        //Timepicker
        selectStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ClassDetailActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                startHour = hourOfDay;
                                startMinute = minute;
                                selectStartTimeButton.setText("Hora de inicio: " + startHour + ":"
                                + startMinute);
                            }
                        },12,0,true);
                timePickerDialog.updateTime(startHour,startMinute);
                timePickerDialog.show();
            }
        });
        selectFinishTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ClassDetailActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                finishHour = hourOfDay;
                                finishMinute = minute;
                                selectFinishTimeButton.setText("Hora de inicio: " + finishHour + ":"
                                        + finishMinute);
                            }
                        },12,0,true);
                timePickerDialog.updateTime(finishHour,finishMinute);
                timePickerDialog.show();
            }
        });

        adminClassTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idCourse = idCourseAdminEditText.getText().toString();
                String nameCourse = nameCourseAdminEditText.getText().toString();
                if(!idCourse.isEmpty() && !nameCourse.isEmpty()) {
                    Intent intent = new Intent(ClassDetailActivity.this,CourseTeacherActivity.class);
                    intent.putExtra("idCourse",idCourse);
                    intent.putExtra("nameCourse",nameCourse);
                    startActivity(intent);
                }
            }
        });

        adminClassStudentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idCourse = idCourseAdminEditText.getText().toString();
                String nameCourse = nameCourseAdminEditText.getText().toString();
                if(!idCourse.isEmpty() && !nameCourse.isEmpty()) {
                    Intent intent = new Intent(ClassDetailActivity.this,CourseStudentActivity.class);
                    intent.putExtra("idCourse",idCourse);
                    intent.putExtra("nameCourse",nameCourse);
                    startActivity(intent);
                }
            }
        });

    }
}