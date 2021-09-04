package com.example.mep_digital.Admin.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mep_digital.Admin.Detail.StudentDetailActivity;
import com.example.mep_digital.databinding.FragmentAdminStudentBinding;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.ListStudents;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentFragment extends Fragment {


    private FragmentAdminStudentBinding binding;
    private ListView listView;
    private ListStudents listStudents;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdminStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView = binding.listViewAllStudents;
        //Obteniendo los datos
        getStudents();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(binding.getRoot().getContext(), StudentDetailActivity.class);
                intent.putExtra("student",listStudents.getStudents().get(i));
                startActivity(intent);
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


    private void getStudents(){
        Call<ListStudents> call = RetrofitClient.getInstance().getMyApi().getStudents();
        call.enqueue(new Callback<ListStudents>() {
            @Override
            public void onResponse(Call<ListStudents> call, Response<ListStudents> response) {
                int statusCode = response.code();
                ListStudents listResult = response.body();
                listStudents = listResult;
                ArrayList<String> students = new ArrayList<String>();
                for (int i = 0; i < listResult.getStudents().size(); i++) {
                    students.add(listResult.getStudents().get(i).getName() + " "+
                            listResult.getStudents().get(i).getLastname() + "\nCédula: " +
                            listResult.getStudents().get(i).getId());
                }
                listView.setAdapter(new ArrayAdapter<String>(binding.getRoot().getContext(),
                        android.R.layout.simple_list_item_1, students));
            }

            @Override
            public void onFailure(Call<ListStudents> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }
}