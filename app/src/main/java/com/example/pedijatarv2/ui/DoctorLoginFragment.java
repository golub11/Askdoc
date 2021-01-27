package com.example.pedijatarv2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pedijatarv2.R;
import com.example.pedijatarv2.data.AppViewModel;
import com.example.pedijatarv2.data.Doctor;
import com.example.pedijatarv2.data.UserAccount;

public class DoctorLoginFragment extends Fragment {

    private AppViewModel viewModel;
    private Button mButton;

    public static String LOGGED_DOCTOR_ID = "doctor_id";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doctor_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButton = view.findViewById(R.id.btn_doctor_login);
        mButton.setOnClickListener(l -> {
            tryDoctorLogin(view);
        });
    }

    public void tryDoctorLogin(View view){
        viewModel = new ViewModelProvider(this).get(AppViewModel.class);

        EditText i = view.findViewById(R.id.doctor_id);
        String id = i.getText().toString();

        EditText n =  view.findViewById(R.id.doctor_name);
        String name = n.getText().toString();

        Doctor tmp_doctor = viewModel.checkDoctor(id,name);

        if(tmp_doctor!=null){
            Toast.makeText(getContext(),"Doctor - logged",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(requireActivity(),DoctorsActivity.class);
            intent.putExtra(LOGGED_DOCTOR_ID,tmp_doctor.doctor_id);
            startActivity(intent);
        }
        else{
            Toast.makeText(getContext(),"Incorrect Username or Password ",Toast.LENGTH_LONG).show();
        }


    }
}
