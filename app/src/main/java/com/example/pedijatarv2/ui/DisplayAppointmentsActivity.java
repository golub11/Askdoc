package com.example.pedijatarv2.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.pedijatarv2.R;
import com.example.pedijatarv2.adapter.AppointsmentListAdapter;
import com.example.pedijatarv2.data.AppViewModel;
import com.example.pedijatarv2.data.Doctor;
import com.example.pedijatarv2.data.Question;
import com.example.pedijatarv2.data.QuestionWithUsersAndDoctors;
import com.example.pedijatarv2.data.UserAccount;
import com.example.pedijatarv2.data.UserWithDoctors;
import com.example.pedijatarv2.databinding.ActivityDisplayAppointmentsBinding;

import java.util.List;

import static com.example.pedijatarv2.MainActivity.LOGGED_USER_ID;

public class DisplayAppointmentsActivity extends AppCompatActivity {

    protected AppViewModel viewModel ;
    protected ActivityDisplayAppointmentsBinding binding;
    protected static UserAccount user;
    protected static List<UserWithDoctors> doctorsByUser;
    private int loggedUserId;
    public AppointsmentListAdapter adapter;


    public static String LOGGED_USER="user_log";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_appointments);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("clicked-question"));


        //pri pokretanju aktivitija
        if(b!=null) {
            loggedUserId = b.getInt(LOGGED_USER_ID);
        }

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_display_appointments);

        user = viewModel.getUser(loggedUserId);

        doctorsByUser = viewModel.getDoctorsByUser(1);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_next);
        adapter = new AppointsmentListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        viewModel.getUsersAndDoctorsWithQuestions(loggedUserId).observe(this, data ->
                adapter.setData(data.get(0))
        );

        binding.setUser(user);

        binding.setLifecycleOwner(this);

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            d = (Doctor) intent.getSerializableExtra("userWithDoctor");
            q = (Question) intent.getSerializableExtra("question");
            String s = intent.getStringExtra("lala");
            viewModel.selectQuestion(q);
            viewModel.selectDoctor(d);
            Log.i("beast-mode",d.getFirstName());
            Log.i("beast-mode",q.question);
        }
    };



    protected Doctor d;
    protected Question q;
    @Override
    protected void onStart() {
        super.onStart();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        NextAppointmentsFragment fragment = new NextAppointmentsFragment();

        fragmentTransaction.add(R.id.next_appointments,fragment);
        fragmentTransaction.commit();
    }

    public void chooseDoctor(View view) {

        Intent intent = new Intent();

        intent.setClass(getApplicationContext(),ChooseDoctor.class);
        intent.putExtra(LOGGED_USER,user);
        startActivityForResult(intent,2);
        Log.i("Poslat request","2");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if request code is same as the passed
        if(resultCode == 2){
            {
                TextView text = findViewById(R.id.textView);
                text.setText("Izabrali ste doktora");


            }
        }
    }

    public void contactForm(View view) {
        Intent intent = new Intent();
        intent.putExtra("logged-user",loggedUserId);

        intent.setClass(getApplicationContext(),AskAndRateDoctor.class);
        startActivity(intent);
    }
}