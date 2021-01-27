package com.example.pedijatarv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pedijatarv2.data.AppViewModel;
import com.example.pedijatarv2.data.DoctorWithUsers;
import com.example.pedijatarv2.data.QuestionsForDoctor;
import com.example.pedijatarv2.data.UserAccount;
import com.example.pedijatarv2.databinding.ActivityMainBinding;
import com.example.pedijatarv2.ui.DisplayAppointmentsActivity;
import com.example.pedijatarv2.ui.DoctorLoginFragment;
import com.example.pedijatarv2.ui.RegisterFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RegisterFragment.AddUserDialogListener {
    private AppViewModel viewModel;
    private ActivityMainBinding binding;

    public static String LOGGED_USER_ID = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(AppViewModel.class);


        List<DoctorWithUsers> dwu = viewModel.getUsersByDoctor(1);

        Log.i("alal",Integer.toString(dwu.size()));

    }

    public void showAddUserDialog(View view) {
        Toast.makeText(getApplicationContext(),"toast",Toast.LENGTH_LONG).show();
        DialogFragment dialog = new RegisterFragment();
        dialog.show(getSupportFragmentManager(), getString(R.string.dialog_add_user));

    }
// ############# NE BRISI ############
/*
    @Override
    public void onClick(View view) {
        String strEmail = binding.txtEmailAddress.getText().toString().trim();
        String strPassword = binding.txtPassword.getText().toString().trim();

        UserAccount data = new UserAccount();

        if (TextUtils.isEmpty(strEmail)) {
            binding.txtEmailAddress.setError("Please Enter Your E-mail Address");
        }
        else if (TextUtils.isEmpty(strPassword)) {
            binding.txtPassword.setError("Please Enter Your Password");
        }
        else {
            data.setEmail(strEmail);
            data.setPassword(strPassword);
            viewModel.insertUser(data);
            Toast.makeText(this,"user added, pw: "+strPassword + ", email: "+ strEmail,Toast.LENGTH_SHORT).show();
        }

    }
*/

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Dialog d = dialog.getDialog();
        String username = ((EditText)d.findViewById(R.id.edit_text_dialog_username)).getText().toString();
        String password = ((EditText)d.findViewById(R.id.edit_text_dialog_password)).getText().toString();

        UserAccount ua = new UserAccount(username,password);
        viewModel.insertUser(ua);

        Toast.makeText(getApplicationContext(),"User added",Toast.LENGTH_LONG);
        //adapter.notifyDataSetChanged();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void tryLogin(View view) {
        String user = ((EditText) findViewById(R.id.txtEmailAddress)).getText().toString();
        String pw = ((EditText) findViewById(R.id.txtPassword)).getText().toString();

        UserAccount tmp_user = viewModel.checkUser(user,pw);

        if(tmp_user!=null){
            Toast.makeText(getApplicationContext(),"logged",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DisplayAppointmentsActivity.class);
            intent.putExtra(LOGGED_USER_ID,tmp_user.user_id);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"Incorrect Username or Password ",Toast.LENGTH_LONG).show();
        }

    }

    private static boolean startedDL = false;
    public void showDoctorLogin(View view) {
        if(!startedDL) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();

            DoctorLoginFragment fragment = new DoctorLoginFragment();

            fragmentTransaction.add(R.id.doctor_login_container, fragment);
            fragmentTransaction.commit();
            startedDL = true;
        }
    }
}