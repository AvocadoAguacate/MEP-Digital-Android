package com.example.mep_digital.Student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.mep_digital.R;

import java.util.ArrayList;
import java.util.List;

public class Student_teacherActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner stars;
    private  ArrayAdapter<String> SpinnerAdapter;

    private ImageButton SendChatButton;
    private EditText ChatText;


    private ListView ChatListView;
    private List<String> ChatList = new ArrayList<>();
    private ArrayAdapter<String> ChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_teacher);

        stars=(Spinner)findViewById(R.id.qualificationSpinner);
        String [] calification_array= {"1 estrella","2 estrellas", "3 estrellas", "4 estrellas"," 5 estrellas"};
        SpinnerAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, calification_array);
        stars.setAdapter(SpinnerAdapter);

        SendChatButton= findViewById(R.id.sendMessageButton);
        SendChatButton.setOnClickListener(this);
        ChatText=findViewById(R.id.sendMessageTextView);
        ChatListView= findViewById(R.id.chatListView);   // define el Listview

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sendMessageButton:String text = ChatText.getText().toString();
            ChatList.add(text);
            ChatText.getText().clear();
            ChatAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,ChatList);
            ChatListView.setAdapter(ChatAdapter);
        }
    }


    private void ChangeView(View view){
        startActivity(new Intent(Student_teacherActivity.this,Student_classActivity.class));
        Intent objI = new Intent(Student_teacherActivity.this,Student_classActivity.class);
        startActivity(objI);
    }


}