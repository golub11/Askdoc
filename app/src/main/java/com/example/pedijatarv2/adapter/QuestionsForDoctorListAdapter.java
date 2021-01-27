package com.example.pedijatarv2.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedijatarv2.R;
import com.example.pedijatarv2.data.Doctor;
import com.example.pedijatarv2.data.DoctorWithUsers;
import com.example.pedijatarv2.data.Question;
import com.example.pedijatarv2.data.QuestionsForDoctor;
import com.example.pedijatarv2.data.UserAccount;

import java.util.List;

public class QuestionsForDoctorListAdapter extends RecyclerView.Adapter<QuestionsForDoctorListAdapter.QuestionsViewHolder> {
    private final LayoutInflater mInflater;
    protected List<Question> mQuestions;
    protected DoctorWithUsers mDoctorWithUsers;
    protected Context mContext;
    protected Intent intent;
    protected Doctor loggedDoctor;
    protected List<UserAccount> askedQuestionsBy;

    public QuestionsForDoctorListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }
    public void setQuestions(List<Question> questions){
        for (int i = 0;i<questions.size();i++){
            // izbaci sva odgovorena pitanja za prikaz
            if(questions.get(i).answer != null){
                questions.remove(questions.get(i));
            }
        }
        mQuestions = questions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_appointments_item,parent,false);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position) {
        if(mQuestions!=null){
            Question q = mQuestions.get(position);
            if(q.answer == null) {
                // ukoliko nije odgovoreno na pitanje, prikazi
                holder.questionItemView.setText(q.question);
            }
            else{
                holder.questionItemView.setText("Answered: " +q.question);

            }

        }
        else{
            holder.questionItemView.setText("Nemate novih pitanja");
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
    protected QuestionsForDoctor data;
    public void setData(QuestionsForDoctor qfd){
        data = qfd;
        setQuestions(data.questions);
        setDoctorWithUsers(data.doctors);
    }

    private void setDoctorWithUsers(DoctorWithUsers doctors) {
        mDoctorWithUsers = doctors;
        loggedDoctor = mDoctorWithUsers.doctor;
        askedQuestionsBy = mDoctorWithUsers.users;
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder{
        private final TextView questionItemView;

        public QuestionsViewHolder(View view) {
            super(view);
            questionItemView = view.findViewById(R.id.question_textView);
            questionItemView.setOnClickListener(v-> {
                Log.i("clicked adapter at: ",Integer.toString(getAdapterPosition()));

                intent = new Intent("question-for-answer");

                intent.putExtra("question", mQuestions.get(getAdapterPosition()));
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            });
        }
    }
}
