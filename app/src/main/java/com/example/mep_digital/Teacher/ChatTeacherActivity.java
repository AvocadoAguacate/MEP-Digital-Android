package com.example.mep_digital.Teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mep_digital.R;
import com.example.mep_digital.model.Course;

import java.util.ArrayList;

public class ChatTeacherActivity extends AppCompatActivity {

    ListView chatTeacherListView;
    Button sendTeacherButton;
    EditText textTeacherEditText;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_teacher);
        //
        textTeacherEditText = findViewById(R.id.textTeacherEditText);
        sendTeacherButton = findViewById(R.id.sendTeacherButton);
        chatTeacherListView = findViewById(R.id.chatTeacherListView);
        //
        loadChat();
    }

    private void loadChat() {
        Intent intent = getIntent();
        course = (Course) intent.getSerializableExtra("course");
        updateChat();
    }

    private void updateChat(){
        ArrayList<String> chatStrings = new ArrayList<>();
        for (int i = 0; i < course.getChat().size(); i++) {
            chatStrings.add("De:" + course.getChat().get(i).getUserType()+
                    "\n" + course.getChat().get(i).getMessage());
        }
        chatTeacherListView.setAdapter(new ArrayAdapter<String>(ChatTeacherActivity.this,
                android.R.layout.simple_list_item_1, chatStrings));
    }
}