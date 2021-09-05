package com.example.mep_digital.Admin.Detail.Course;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mep_digital.R;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.Course;
import com.example.mep_digital.model.CreateCourse;
import com.example.mep_digital.model.CreateSchedule;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.Schedule;
import com.example.mep_digital.model.UpdateCourse;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassDetailActivity extends AppCompatActivity  {

    Button selecDaysButton;
    Button selectStartTimeButton;
    Button adminClassTeacherButton;
    Button adminClassStudentsButton;
    Button selectFinishTimeButton;
    Button saveClassButton;
    Button deleteClassButton;
    Button cancelClassButton;
    EditText idCourseAdminEditText;
    EditText nameCourseAdminEditText;
    Spinner courseSelectGradeSpinner;
    Course course;
    boolean newCourse;

    boolean[] checkedDays;
    String[] daysWeek;
    ArrayList<String> selectedDays = new ArrayList<>();

    int startHour,startMinute,finishHour,finishMinute = -1;
    private List<Schedule> listSchedule;


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
        courseSelectGradeSpinner = findViewById(R.id.courseSelectGradeSpinner);
        saveClassButton = findViewById(R.id.saveClassButton);
        deleteClassButton = findViewById(R.id.deleteClassButton);
        cancelClassButton = findViewById(R.id.cancelClassButton);
        //
        daysWeek = getResources().getStringArray(R.array.week_days);
        checkedDays = new boolean[daysWeek.length];
        //Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.grade_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSelectGradeSpinner.setAdapter(adapter);
        courseSelectGradeSpinner.setSelection(0);
        //Botones
        cancelClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        deleteClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idCourse = idCourseAdminEditText.getText().toString();
                if(!idCourse.isEmpty()){
                    Call<Message> call = RetrofitClient.getInstance().getMyApi().deleteCourse(idCourse);
                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            try {
                                int statusCode = response.code();
                                Message message = response.body();
                                Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                                finish();
                            } catch (Exception e){
                                Toast.makeText(getApplicationContext(),"Error al eliminar curso", Toast.LENGTH_LONG).show();
                                cancelClassButton.setText("Error:"+ response.errorBody().toString());
                            }
                        }
                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {

                        }
                    });
                }
            }
        });

        saveClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idCourse = idCourseAdminEditText.getText().toString();
                String nameCourse = nameCourseAdminEditText.getText().toString();
                if(!idCourse.isEmpty() && !nameCourse.isEmpty()){
                    int grade = courseSelectGradeSpinner.getSelectedItemPosition();
                    if(newCourse){
                        CreateCourse createCourse = new CreateCourse(idCourse,nameCourse,grade);
                        Call<Message> call = RetrofitClient.getInstance().getMyApi().postCourse(createCourse);
                        call.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                try {
                                    int statusCode = response.code();
                                    Message message = response.body();
                                    Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                                    saveSchedule();
                                } catch (Exception e){
                                    cancelClassButton.setText("Error:"+ response.errorBody().toString());
                                }
                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        UpdateCourse updateCourse = new UpdateCourse(nameCourse,grade);
                        Call<Message> call = RetrofitClient.getInstance().getMyApi().putCourse(idCourse,updateCourse);
                        call.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                try {
                                    int statusCode = response.code();
                                    Message message = response.body();
                                    Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                                    saveSchedule();
                                } catch (Exception e){
                                    Toast.makeText(getApplicationContext(),"Error al actualizar curso", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {

                            }
                        });
                    }
                }
            }
        });

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
                            if(!selectedDays.contains(daysWeek[which])){
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
                        updateSelectDaysButtonTitle();
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

        //Timepickers
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
                                updateStartTimeButton();
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
                                updateFinishTimeButton();
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
                    if(course.getTeacher() == null){
                        intent.putExtra("newTeacher",true);
                    } else {
                        intent.putExtra("teacher",course.getTeacher());
                        intent.putExtra("newTeacher",false);
                    }
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
                    if(course.getStudents() == null){
                        intent.putExtra("emptyList",true);
                    } else {
                        intent.putExtra("listStudents", (Serializable) course.getStudents());
                        intent.putExtra("emptyList",false);
                    }
                    startActivity(intent);
                }
            }
        });

        //
        loadData();

    }

    private void saveSchedule() {
        if(selectedDays.size() > 0){
            if(startHour > 0 && finishHour > 0){
                if(((startHour*100)+startMinute) < ((finishHour*100)+finishMinute)){
                    //deleteSchedule(); ToDo
                    for(int i = 0; i < selectedDays.size(); i++){
                        int day = getIntDay(selectedDays.get(i));
                        CreateSchedule createSchedule = new CreateSchedule(day,startHour,startMinute,finishHour,finishMinute);
                        String idCourse = idCourseAdminEditText.getText().toString();
                        Call<Message> call = RetrofitClient.getInstance().getMyApi().postSchedule(idCourse,createSchedule);
                        call.enqueue(new Callback<Message>() {
                            @Override
                            public void onResponse(Call<Message> call, Response<Message> response) {
                                try {
                                    int statusCode = response.code();
                                    Message message = response.body();
                                    Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();

                                } catch (Exception e){
                                    Toast.makeText(getApplicationContext(),"Error al guardar horario", Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Message> call, Throwable t) {

                            }
                        });
                    }
                } else {
                    Toast.makeText(ClassDetailActivity.this,"Debería revisar las horas del horario",Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ClassDetailActivity.this,"Debería de seleccionar las horas del horario",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(ClassDetailActivity.this,"Debería agregar un horario, notamos que no ha seleccionado los días",Toast.LENGTH_LONG).show();
        }
    }

    private void deleteSchedule() {
        if(listSchedule.size()>0){
            String idCourse =idCourseAdminEditText.getText().toString();
            for (int i = 0; i < listSchedule.size(); i++) {
                Call<Message> call = RetrofitClient.getInstance().getMyApi().deleteSchedule(idCourse,listSchedule.get(i).get_id());
                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        try {
                            int statusCode = response.code();
                            Message message = response.body();
                            System.out.println(message.getMessage());

                        } catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Error al guardar horario,eliminando", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {

                    }
                });
            }

        }
    }

    private int getIntDay(String day) {
        for(int i = 0; i < daysWeek.length ; i++){
            if(daysWeek[i].contains(day)){
                return i;
            }
        }
        return 0;
    }

    private void loadData(){
        Intent intent = getIntent();
        newCourse = intent.getBooleanExtra("newCourse",true);
        if(!newCourse){
            course = (Course)intent.getSerializableExtra("course");
            nameCourseAdminEditText.setText(course.getName());
            idCourseAdminEditText.setText(course.getId());
            courseSelectGradeSpinner.setSelection(course.getGrade());
            listSchedule = course.getSchedule();
            getScheduleCourseToPrint();
        }
    }

    private void getScheduleCourseToPrint(){
        for(int i = 0; i < course.getSchedule().size(); i++){
            if(!selectedDays.contains(daysWeek[course.getSchedule().get(i).getDay()])){
                selectedDays.add(daysWeek[course.getSchedule().get(i).getDay()]);
            }
            if(i == 0){
                finishHour = course.getSchedule().get(i).getEndHour();
                finishMinute = course.getSchedule().get(i).getEndMinutes();
                startHour = course.getSchedule().get(i).getStartHour();
                startMinute = course.getSchedule().get(i).getStartMinutes();
            }
        }
        updateSelectDaysButtonTitle();
        updateFinishTimeButton();
        updateStartTimeButton();
    }

    private void updateSelectDaysButtonTitle(){
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

    private void updateFinishTimeButton(){
        String title = "Hora de inicio: " + finishHour + ":" + finishMinute;
        selectFinishTimeButton.setText(title);
    }

    private void updateStartTimeButton(){
        String title = "Hora de inicio: " + startHour + ":" + startMinute;
        selectStartTimeButton.setText(title);
    }
}