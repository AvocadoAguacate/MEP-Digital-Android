package com.example.mep_digital.Student;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mep_digital.R;
import com.example.mep_digital.Teacher.ChatTeacherActivity;
import com.example.mep_digital.Teacher.CourseTeacherActivity;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.Course;
import com.example.mep_digital.model.GetCourse;
import com.example.mep_digital.model.Message;
import com.example.mep_digital.model.PostChat;
import com.example.mep_digital.model.User;
import com.example.mep_digital.model.UserChat;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Student_ChatActivity extends AppCompatActivity {


    ListView chatStudentListView;
    EditText textStudentEditText;
    Button sendStudentButton;
    Button backChatStudentButton;

    Course course;
    String studentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_chat);
        //
        chatStudentListView = findViewById(R.id.chatStudentListView);
        textStudentEditText = findViewById(R.id.textStudentEditText);
        sendStudentButton = findViewById(R.id.sendStudentButton);
        backChatStudentButton = findViewById(R.id.backChatStudentButton);
        //
        getData();
        setButtons();
    }



    private void setButtons() {
        sendStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textStudentEditText.getText().toString();
                if(!text.isEmpty()){
                    PostChat postChat = new PostChat(studentId, UserChat.Student.toString(), text);
                    Call<Message> call = RetrofitClient.getInstance().getMyApi().postChat(
                            course.getId(),postChat);
                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            try {
                                int statusCode = response.code();
                                Message message = response.body();
                                Toast.makeText(getApplicationContext(),message.getMessage(), Toast.LENGTH_LONG).show();
                                updateChatFromApi();
                            } catch (Exception e){
                                JsonParser parser = new JsonParser();
                                JsonElement mJson = null;
                                try {
                                    mJson = parser.parse(response.errorBody().string());
                                    Gson gson = new Gson();
                                    Message errorResponse = gson.fromJson(mJson, Message.class);
                                    Toast.makeText(Student_ChatActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
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
        });
        backChatStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<GetCourse> call = RetrofitClient.getInstance().getMyApi().getCourse(course.getId());
                call.enqueue(new Callback<GetCourse>() {
                    @Override
                    public void onResponse(Call<GetCourse> call, Response<GetCourse> response) {
                        try {
                            int statusCode = response.code();
                            GetCourse getCourse = response.body();
                            Intent intent = new Intent(Student_ChatActivity.this, Student_classActivity.class);
                            intent.putExtra("course",getCourse.getCourse());
                            intent.putExtra("needApiUpdate",false);
                            startActivity(intent);
                        } catch (Exception e){
                            JsonParser parser = new JsonParser();
                            JsonElement mJson = null;
                            try {
                                mJson = parser.parse(response.errorBody().string());
                                Gson gson = new Gson();
                                Message errorResponse = gson.fromJson(mJson, Message.class);
                                Toast.makeText(Student_ChatActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } finally {
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GetCourse> call, Throwable t) {

                    }
                });
            }
        });
        chatStudentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateChatFromApi();
            }
        });
    }

    private void updateChatFromApi(){
        Call<GetCourse> call = RetrofitClient.getInstance().getMyApi().getCourse(course.getId());
        call.enqueue(new Callback<GetCourse>() {
            @Override
            public void onResponse(Call<GetCourse> call, Response<GetCourse> response) {
                try {
                    int statusCode = response.code();
                    GetCourse getCourse = response.body();
                    course = getCourse.getCourse();
                    updateChat();
                } catch (Exception e){
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = null;
                    try {
                        mJson = parser.parse(response.errorBody().string());
                        Gson gson = new Gson();
                        Message errorResponse = gson.fromJson(mJson, Message.class);
                        Toast.makeText(Student_ChatActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCourse> call, Throwable t) {

            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        course = (Course) intent.getSerializableExtra("course");
        studentId = intent.getStringExtra("studentId");
        updateChat();
    }

    private void updateChat(){
        ArrayList<String> chatStrings = new ArrayList<>();
        for (int i = 0; i < course.getChat().size(); i++) {
            chatStrings.add(getName(UserChat.valueOf(course.getChat().get(i).getUserType()),
                    course.getChat().get(i).getUser()) + "\n"
                    + course.getChat().get(i).getMessage());
        }
        chatStudentListView.setAdapter(new ArrayAdapter<String>(Student_ChatActivity.this,
                android.R.layout.simple_list_item_1, chatStrings));
    }

    private String getName(UserChat type, User user){
        String result = "";
        if(type == UserChat.Teacher){
            result += "Profesor: ";
        } else {
            result += "Estudiante: ";
        }
        result += user.getName() + " " + user.getLastName();
        return result;
    }
}

