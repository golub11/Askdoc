package com.example.pedijatarv2.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.pedijatarv2.R;
import com.example.pedijatarv2.data.AppViewModel;
import com.example.pedijatarv2.data.UserAccount;

import org.w3c.dom.Text;

public class NextAppointmentsFragment extends Fragment {

    private AppViewModel sharedViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_next_appointments,container,false);
    }
    private TextView tv;
    private TextView tt;
    private TextView avgR;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avgR = view.findViewById(R.id.rating);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        sharedViewModel.getSelectedDoctor().observe(getViewLifecycleOwner(),item -> {
            // update UI
            tv = view.findViewById(R.id.firstName);
            tv.setText("Asked dr. "+item.getFirstName());
            tt = view.findViewById(R.id.lastName);
            tt.setText(item.getLastName());

            double n = sharedViewModel.getAvgRating(item.doctor_id);
            if (n!=0.0){
                String s = "Doctor's rating: " + n;
                avgR.setText(s);
            }
            else{
                avgR.setText("");
            }
                });

        sharedViewModel.getSelectedQuestion().observe(getViewLifecycleOwner(),question -> {
            tv = view.findViewById(R.id.answered_question);
            if(question.answer!=null) {
                tv.setText("Answer:" + question.answer);
            }
            else{
                tv.setText("");
            }
        });


    //UserAccount user = sharedViewModel.getUser(0);

        //sharedViewModel.getUser(5);
    }
}
