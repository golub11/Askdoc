package com.example.pedijatarv2.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pedijatarv2.BaseApp;

import java.util.List;

public class AppViewModel extends ViewModel {

    private AppRepository mRepository;
    public LiveData<List<UserAccount>> getAllUsers;
    private final MutableLiveData<QuestionWithUsersAndDoctors> selectedItem = new MutableLiveData<>();

    public void selectItem(QuestionWithUsersAndDoctors item){
        selectedItem.setValue(item);
    }

    public LiveData<QuestionWithUsersAndDoctors> getSelectedItem(){ return selectedItem;}

    public double getAvgRating(long doctorId){ return mRepository.getAvgRating(doctorId); }

    public Doctor doc;

    public Doctor[] mDoctor;

    public LiveData<List<Doctor>> getAllDoctors(){
        return mRepository.getmAllDoctors();
    }
    public List<Question> getAllQuestions(){return mRepository.getAllQuestions();}

    public LiveData<List<QuestionWithUsersAndDoctors>> getUsersAndDoctorsWithQuestions(int id){ return mRepository.getUsersAndDoctorsWithQuestions(id);}

    public LiveData<List<QuestionsForDoctor>> getQuestionsForDoctor(long id){ return mRepository.getQuestionsForDoctor(id);}

    public void askQuestion(Question q){mRepository.askQuestion(q);}
    public AppViewModel() {
        super();

        mRepository= new AppRepository(BaseApp.getAppContext());
        Log.i("repo", "AppRepository: constuctore");
        mDoctor = mRepository.getDoctor();
       // doc = mDoctor[0];
    }

    LiveData<List<Doctor>> getmAllDoctors() {return mRepository.getmAllDoctors();}

    public void insert(Doctor doctor){mRepository.insert(doctor);}

    public void insertUser(UserAccount user){ mRepository.insertUser(user);}

    public LiveData<List<UserAccount>> getAllUsers() {
        return mRepository.getmAllUsers();
    }

    public void deleteAllUsers() {
        mRepository.deleteAllUsers();
    }

    public UserAccount checkUser(String userName, String pw){ return mRepository.checkUser(userName,pw);}

    public UserAccount getUser(int id){return mRepository.getUser(id);}

//    public void connectUserWithDoctor(int user, long doctor) { mRepository.connectUserWithDoctor(user,doctor); }

    public List<UserWithDoctors> getDoctorsByUser(int userId){return mRepository.getDoctorsByUser(userId);}

    public List<DoctorWithUsers> getUsersByDoctor(long doctorId){ return mRepository.getUsersByDoctor(doctorId);}

    public MutableLiveData<Doctor> doctorMLD = new MutableLiveData<>();
    private MutableLiveData<Question> questionMLD=  new MutableLiveData<>();

    public void selectDoctor(Doctor d) {
        doctorMLD.setValue(d);
    }
    public MutableLiveData<Doctor> getSelectedDoctor(){
        return doctorMLD;
    }
    public void rateDoctor(int rating, long doctorId){ mRepository.rateDoctor(rating,doctorId);}
    public void selectQuestion(Question q) {
        questionMLD.setValue(q);
    }
    public MutableLiveData<Question> getSelectedQuestion(){
        return questionMLD;
    }

    public void setAnswer(String a, String q){ mRepository.setAnswer(a,q);}

    public Doctor checkDoctor(String id, String name) {
        return mRepository.checkDoctor(id,name);
    }

    public Doctor[] getAnyDoctor() {
        return mRepository.getDoctor();
    }

    public Doctor getDoctorById(long loggedDoctorId) {
        return mRepository.getDoctorById(loggedDoctorId);
    }

    public int getNumOfVotes(long loggedDoctorId) {
        return mRepository.getNumOfVotes(loggedDoctorId);
    }

    /*public List<QuestionWithUsersAndDoctors> getQuestionsWithUsersAndDoctors() {
        return mRepository.getQuestionsWithUsersAndDoctors();
    }*/
}
