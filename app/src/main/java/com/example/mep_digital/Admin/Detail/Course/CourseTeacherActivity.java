package com.example.mep_digital.Admin.Detail.Course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mep_digital.R;
import com.example.mep_digital.model.Teacher;

import java.util.ArrayList;

public class CourseTeacherActivity extends AppCompatActivity {

    TextView nameCourseTeacherTextView;
    TextView idCourseTeacherTextView;
    EditText courseTeacherIdEditText;
    Button searchCourseTeacherButton;
    Button addCourseTeacherButton;
    Button deleteCourseTeacherButton;
    Button cancelCourseTeacherButton;
    ListView dataCourseTeacherListView;
    Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_teacher);

        //
        nameCourseTeacherTextView = findViewById(R.id.nameCourseTeacherTextView);
        idCourseTeacherTextView = findViewById(R.id.idCourseTeacherTextView);
        courseTeacherIdEditText = findViewById(R.id.courseTeacherIdEditText);
        searchCourseTeacherButton = findViewById(R.id.searchCourseTeacherButton);
        addCourseTeacherButton = findViewById(R.id.addCourseTeacherButton);
        deleteCourseTeacherButton = findViewById(R.id.deleteCourseTeacherButton);
        cancelCourseTeacherButton = findViewById(R.id.cancelCourseTeacherButton);
        dataCourseTeacherListView = findViewById(R.id.dataCourseTeacherListView);
        //
        loadData();
    }

    private void loadData(){
        Intent intent = getIntent();
        nameCourseTeacherTextView.setText(intent.getStringExtra("nameCourse"));
        idCourseTeacherTextView.setText(intent.getStringExtra("idCourse"));
        if(!intent.getBooleanExtra("newTeacher",true)){
            teacher = (Teacher)intent.getSerializableExtra("teacher");
            courseTeacherIdEditText.setText(teacher.getId());
            updateTeacherDataListView();
        }
    }

    private void updateTeacherDataListView(){
        ArrayList<String> teacherArray = new ArrayList<>();
        float aproxRating = aproxRating();
        teacherArray.add(teacher.getName() + " " + teacher.getLastname() + "\n" +
                teacher.getEmail() + "\n" +
                "Calificación: "+ aproxRating + "\n");
        dataCourseTeacherListView.setAdapter(new ArrayAdapter<String>(CourseTeacherActivity.this,
                android.R.layout.simple_list_item_1, teacherArray));
    }


    private float aproxRating() {
        int sum = 0;
        if (teacher.getRatings().size() == 0){
            return (float) sum;
        }
        for(int i = 0; i < teacher.getRatings().size(); i++){
            sum += (float)teacher.getRatings().get(i).getRating();
        }
        return (float) (sum/teacher.getRatings().size());
    }


}