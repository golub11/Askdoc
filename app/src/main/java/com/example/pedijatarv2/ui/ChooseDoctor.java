package com.example.pedijatarv2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pedijatarv2.R;
import com.example.pedijatarv2.adapter.DoctorListAdapter;
import com.example.pedijatarv2.data.AppViewModel;
import com.example.pedijatarv2.data.Doctor;
import com.example.pedijatarv2.data.UserAccount;

public class ChooseDoctor extends AppCompatActivity{
    private int choosenDoctorId=-1;

    protected UserAccount loggedUser;
    private AppViewModel mViewModel;
    public DoctorListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_doctor);
        loggedUser = (UserAccount) getIntent().getExtras().getSerializable(DisplayAppointmentsActivity.LOGGED_USER);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        adapter = new DoctorListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModel = new ViewModelProvider(this).get(AppViewModel.class);

        mViewModel.getAllDoctors().observe(this, doctors -> {
            // Update the cached copy of the words in the adapter.
            adapter.setDoctors(doctors);
        });



    }

    static long selectedDoctor;
    public void chooseDoctor(View view) {
        choosenDoctorId = adapter.getSelectedDoctorId();
        Doctor doctor = adapter.getDoctorAtPosition(choosenDoctorId);

        Toast.makeText(this, "Uspesno ste izabrali doktora " + doctor.getFirstName() + " " +
                doctor.getLastName(),Toast.LENGTH_SHORT).show();
        setResult(2);
        selectedDoctor = doctor.doctor_id;

        Log.i("Postavljen request","2");
        finish();
    }
}