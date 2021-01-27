package com.example.pedijatarv2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pedijatarv2.R;
import com.example.pedijatarv2.data.AppViewModel;
import com.example.pedijatarv2.data.Doctor;
import com.example.pedijatarv2.data.Question;

public class AskAndRateDoctor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private AppViewModel viewmodel;
    private int loggedUser;
    private long sDoctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_rate_doctor);

        loggedUser = getIntent().getIntExtra("logged-user",-1);

        Spinner spinner = (Spinner) findViewById(R.id.ratings_spinner);
        Button ask = (Button)findViewById(R.id.askButton);
        Button rate = (Button)findViewById(R.id.rateButton);
        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this,R.array.ratings,android.R.layout.simple_spinner_item);

        viewmodel = new ViewModelProvider(this).get(AppViewModel.class);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(aa);

        ask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String question = ((EditText) findViewById(R.id.question)).getText().toString();
                Question q = new Question();
                q.question = question;

                if(loggedUser == -1 || sDoctor<1){
                    Toast.makeText(AskAndRateDoctor.this, "Molimo vas izaberite doktora pre postavljanja pitanja", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{

                    q.setUserId(loggedUser);

                    q.setDoctorId(sDoctor);

                    viewmodel.askQuestion(q);
                    Toast.makeText(AskAndRateDoctor.this, "Uspešno ste postavili pitanje izabranom doktoru ", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });


        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sDoctor<1) {
                    Toast.makeText(AskAndRateDoctor.this, "Molimo vas izaberite doktora pre ocenjivanja", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    String rating = spinner.getSelectedItem().toString();
                    int r = Integer.parseInt(rating);

                    viewmodel.rateDoctor(r, sDoctor);
                    Toast.makeText(AskAndRateDoctor.this, "Uspešno ste ocenili izabranog doktora sa ocenom " + rating, Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}