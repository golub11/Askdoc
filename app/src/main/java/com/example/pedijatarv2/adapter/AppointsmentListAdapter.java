package com.example.pedijatarv2.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedijatarv2.R;
import com.example.pedijatarv2.data.AppViewModel;
import com.example.pedijatarv2.data.Doctor;
import com.example.pedijatarv2.data.Question;
import com.example.pedijatarv2.data.QuestionWithUsersAndDoctors;
import com.example.pedijatarv2.data.UserWithDoctors;
import com.example.pedijatarv2.ui.DisplayAppointmentsActivity;

import java.util.List;

public class AppointsmentListAdapter extends RecyclerView.Adapter<AppointsmentListAdapter.AppointsmentViewHolder> {
    private final LayoutInflater mInflater;
    protected List<Question> mQuestions;
    protected UserWithDoctors mUserWithDoctors;
    protected Context mContext;
    protected Intent intent;


    public AppointsmentListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setQuestions(List<Question> questions){
        mQuestions = questions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AppointsmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_appointments_item,parent,false);

        return new AppointsmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointsmentViewHolder holder, int position) {
        if(mQuestions!=null){
            Question q = mQuestions.get(position);
            holder.questionItemView.setText(q.question);
            if (q.answer!=null){
                holder.questionItemView.setBackgroundResource(R.color.colorPrimary);
            }
        }
        if(mQuestions == null || mQuestions.size()==0){
                holder.questionItemView.setText("Nemate aktivna pitanja za doktore.");

        }

    }

    @Override
    public int getItemCount() {
        if(mQuestions==null) {
            return 0;
        }
        else{
            return mQuestions.size();
        }
    }


    public void setUserWithDoctors(UserWithDoctors users) {
        mUserWithDoctors = users;
    }

    protected QuestionWithUsersAndDoctors data;

    public void setData(QuestionWithUsersAndDoctors data) {
        this.data= data;
        setQuestions(data.questions);
        setUserWithDoctors(data.users);

    }


    public class AppointsmentViewHolder extends RecyclerView.ViewHolder {

        private final TextView questionItemView;
        private Doctor tmpDoctor;
        public AppointsmentViewHolder(View view) {
            super(view);
            questionItemView = view.findViewById(R.id.question_textView);
            questionItemView.setOnClickListener(v-> {
                Log.i("clicked adapter at: ",Integer.toString(getAdapterPosition()));
                Log.i("and clicked at object",mQuestions.get(getAdapterPosition()).question);
                intent = new Intent("clicked-question");

                intent.putExtra("question", mQuestions.get(getAdapterPosition()));
                int k = getAdapterPosition();
                long l = mQuestions.get(k).getDoctorId();

                for (int i=0; i<mUserWithDoctors.doctors.size();i++){
                    if(mUserWithDoctors.doctors.get(i).doctor_id == l){
                        tmpDoctor = mUserWithDoctors.doctors.get(i);
                    }
                }
                intent.putExtra("userWithDoctor",tmpDoctor);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            });
        }
    }
}
