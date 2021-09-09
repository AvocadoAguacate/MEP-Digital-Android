package com.example.mep_digital.Admin.ui.home;

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

import com.example.mep_digital.Admin.Detail.Course.ClassDetailActivity;
import com.example.mep_digital.databinding.FragmentAdminClassBinding;
import com.example.mep_digital.io.RetrofitClient;
import com.example.mep_digital.model.ListCourses;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminClassFragment extends Fragment {

    private FragmentAdminClassBinding binding;
    private ListView listView;
    private ListCourses listCourses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAdminClassBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = binding.listViewAllClasses;

        //Obteniendo los datos
        getClasses();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(binding.getRoot().getContext(), ClassDetailActivity.class);
                intent.putExtra("course", listCourses.getCourses().get(i));
                intent.putExtra("newCourse",false);//bit de modo, true = quiere hacer un nuevo curso y no hay que cargar la información de un curso
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

    private void getClasses(){
        Call<ListCourses> call = RetrofitClient.getInstance().getMyApi().getCourses();
        call.enqueue(new Callback<ListCourses>() {
            @Override
            public void onResponse(Call<ListCourses> call, Response<ListCourses> response) {
                int statusCode = response.code();
                ListCourses listResult = response.body();
                listCourses = listResult;
                ArrayList<String> courses = new ArrayList<String>();
                for (int i = 0; i < listResult.getCourses().size(); i++) {
                    courses.add(listResult.getCourses().get(i).getName() + "\nId: " +
                            listResult.getCourses().get(i).getId());
                }
                listView.setAdapter(new ArrayAdapter<String>(binding.getRoot().getContext(),
                        android.R.layout.simple_list_item_1, courses));
            }

            @Override
            public void onFailure(Call<ListCourses> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }
}