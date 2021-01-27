package com.example.pedijatarv2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedijatarv2.R;
import com.example.pedijatarv2.data.Doctor;
import com.example.pedijatarv2.ui.RecyclerViewClickListener;

import java.util.List;


public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.DoctorViewHolder> {

    public static int selectedDoctorId = -1;

    private final LayoutInflater mInflater;
    private List<Doctor> mDoctors;

    public DoctorListAdapter(Context context){
        mInflater=LayoutInflater.from(context);

    }

    public void setDoctors(List<Doctor> doctors){
        mDoctors = doctors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DoctorListAdapter.DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new DoctorViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull DoctorListAdapter.DoctorViewHolder holder, int position) {
        if (mDoctors!=null){
            Doctor current =mDoctors.get(position);
            holder.doctorItemView.setText(current.getFirstName());

        }
        else{
            holder.doctorItemView.setText("No Avaliable Doctor");
        }
    }

    @Override
    public int getItemCount() {
        if(mDoctors==null){
            return 0;
        }
        else{
            return mDoctors.size();
        }
    }

    public static boolean selected = false;

    public static int getSelectedDoctorId() {
        return selectedDoctorId;
    }
    public Doctor getDoctorAtPosition(int position){ return mDoctors.get(position);}

    class DoctorViewHolder extends RecyclerView.ViewHolder{
        //View
        private final TextView doctorItemView;

        @SuppressLint("ResourceAsColor")
        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(v -> {

                if(!selected) {
                    doctorItemView.setBackgroundColor(Color.GREEN);
                    selectedDoctorId = getLayoutPosition();
                    selected = true;

                }
                else if (selectedDoctorId == getLayoutPosition() && selected){
                    doctorItemView.setBackgroundColor(R.color.colorPrimaryLight);
                    selectedDoctorId = -1;
                    selected = false;
                }
            });
        }

        private void selectDoctor(int position) {
                Doctor sDoctor = getDoctorAtPosition(position);

        }


    }
}
