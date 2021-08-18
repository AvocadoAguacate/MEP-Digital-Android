package com.example.mep_digital.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mep_digital.R;
import com.example.mep_digital.StudentDetailActivity;
import com.example.mep_digital.databinding.FragmentAdminStudentBinding;

import java.util.ArrayList;
import java.util.List;

public class StudentFragment extends Fragment {


    private FragmentAdminStudentBinding binding;
    private ListView listView;
    private List<String> teachers;
    private String selectedTeacher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdminStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView = binding.listViewAllStudents;
        //ejemplo temporal mientras se conecta al api
        teachers = new ArrayList<String>();
        teachers.add("Eduardo Picado Gonzalo\nCédula:30510435");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(binding.getRoot().getContext(),android.R.layout.simple_list_item_1, teachers);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(binding.getRoot().getContext(), "Pulsado en "+teachers.get(i), Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    public void goStudentDetail(View view){
        Intent intent = new Intent(binding.getRoot().getContext(), StudentDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}