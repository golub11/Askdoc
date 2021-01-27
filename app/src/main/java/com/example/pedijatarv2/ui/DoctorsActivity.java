package com.example.pedijatarv2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pedijatarv2.R;
import com.example.pedijatarv2.adapter.AppointsmentListAdapter;
import com.example.pedijatarv2.adapter.QuestionsForDoctorListAdapter;
import com.example.pedijatarv2.data.AppViewModel;
import com.example.pedijatarv2.data.Doctor;
import com.example.pedijatarv2.data.Question;
import com.example.pedijatarv2.data.QuestionsForDoctor;
import com.example.pedijatarv2.databinding.ActivityDoctorsBinding;

import org.w3c.dom.Text;

import java.util.List;

import static com.example.pedijatarv2.ui.DoctorLoginFragment.LOGGED_DOCTOR_ID;

public class DoctorsActivity extends AppCompatActivity {
    private AppViewModel viewModel;
    private long loggedDoctorId;
    public QuestionsForDoctorListAdapter adapter;
    protected ActivityDoctorsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null){
            loggedDoctorId = b.getLong(LOGGED_DOCTOR_ID);
        }

        binding = DataBindingUtil.setContentView(this,R.layout.activity_doctors);

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_doctor);

        LocalBroadcastManager.getInstance(this).registerReceiver(mQuestionReceiver,new IntentFilter("question-for-answer"));

        adapter = new QuestionsForDoctorListAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        viewModel.getQuestionsForDoctor(loggedDoctorId).observe(this,data -> {
            adapter.setData(data.get(0));
        });

        viewModel.getSelectedQuestion().observe(this,question -> {
            binding.setQuestion(question);
        });

        TextView nVotes  = findViewById(R.id.num_of_votes);
        TextView rating  = findViewById(R.id.avg_rating);
        rating.setText(Double.toString(viewModel.getAvgRating(loggedDoctorId)));
        nVotes.setText("("+Integer.toString(viewModel.getNumOfVotes(loggedDoctorId))+")");
        //add rating of logged doctor to id/doctor_textview and number of votes
    }
    protected Question q;
    public BroadcastReceiver mQuestionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            q = (Question) intent.getSerializableExtra("question");
            viewModel.selectQuestion(q);
        }
    };

    public void answerOnQuestion(View view) {
        String answer = ((EditText) findViewById(R.id.answer)).getText().toString();
        String q = ((TextView) findViewById(R.id.question_for_answering)).getText().toString();
        viewModel.setAnswer(answer, q);
    }

   /* @Override
    protected void onStart() {
        super.onStart();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        AnswerOnQuestionFragment fragment = new AnswerOnQuestionFragment();

        fragmentTransaction.add(R.id.next_appointments_doctor,fragment);
        fragmentTransaction.commit();
    }*/
}