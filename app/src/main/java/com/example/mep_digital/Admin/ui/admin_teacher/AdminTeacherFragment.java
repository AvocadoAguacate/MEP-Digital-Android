package com.example.mep_digital.Admin.ui.admin_teacher;

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

import com.example.mep_digital.Admin.AdminActivity;
import com.example.mep_digital.Admin.Detail.Course.ClassDetailActivity;
import com.example.mep_digital.Admin.Detail.TeacherDetailActivity;
import com.example.mep_digital.databinding.FragmentAdminTeacherBinding;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.ListTeachers;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminTeacherFragment extends Fragment {

    private FragmentAdminTeacherBinding binding;
    private ListView listView;
    private ListTeachers listTeachers;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdminTeacherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView = binding.listViewAllTeachers;

        //Obteniendo los datos
        getTeachers();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(binding.getRoot().getContext(), TeacherDetailActivity.class);
                intent.putExtra("teacher",listTeachers.getTeachers().get(i));
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getTeachers(){
        Call<ListTeachers> call = RetrofitClient.getInstance().getMyApi().getTeachers();
        call.enqueue(new Callback<ListTeachers>() {
            @Override
            public void onResponse(Call<ListTeachers> call, Response<ListTeachers> response) {
                int statusCode = response.code();
                ListTeachers listResult = response.body();
                listTeachers = listResult;
                ArrayList<String> teachers = new ArrayList<String>();
                for (int i = 0; i < listResult.getTeachers().size(); i++) {
                    teachers.add(listResult.getTeachers().get(i).getName() + " " +
                            listResult.getTeachers().get(i).getLastname()+ "\nCédula: " +
                            listResult.getTeachers().get(i).getId());
                }
                listView.setAdapter(new ArrayAdapter<String>(binding.getRoot().getContext(),
                        android.R.layout.simple_list_item_1, teachers));
            }

            @Override
            public void onFailure(Call<ListTeachers> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }

}