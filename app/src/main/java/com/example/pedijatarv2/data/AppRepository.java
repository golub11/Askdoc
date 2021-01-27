package com.example.pedijatarv2.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {


    private QuestionDAO mQuestionDao;
    private UserAccountDAO mUserDao;
    private DoctorDAO mDoctorDao;

    public List<Question> getAllQuestions(){
        return mQuestionDao.getAllQuestions();
    }

    public LiveData<List<Doctor>> getmAllDoctors() {
        return mDoctorDao.getAllDoctors();
    }

    public LiveData<List<QuestionWithUsersAndDoctors>> getUsersAndDoctorsWithQuestions(int id){return mUserDao.getTriple(id);}

    public LiveData<List<QuestionsForDoctor>> getQuestionsForDoctor(long id){ return mDoctorDao.getTriple(id);};
    public LiveData<List<UserAccount>> getmAllUsers() {
        return mUserDao.getUsers();
    }

    public void setAnswer(String a, String q){
        mQuestionDao.setAnswer(a, q);
    }

    AppRepository(Context application){

        AppDatabase db = AppDatabase.getDatabase(application);
        mDoctorDao = db.doctorDAO();
        mUserDao = db.usersDAO();
        mQuestionDao = db.questionDAO();

    }

   /* public void insertUserstoDoctor(DoctorWithUsers dwu){
        new insertUsersToDoctorAsyncTask(mDoctorDao).execute(dwu);
    }*/

    public void askQuestion(Question q){
        mQuestionDao.insert(q);
    }

    public void rateDoctor(int rating, long doctorId){
        mDoctorDao.increseVotesAndSum(doctorId,rating);
    }

    public double getAvgRating(long doctorId){
        try{
            double avg = mDoctorDao.getSum(doctorId)/mDoctorDao.getNumOfVotes(doctorId);
            return avg;
        } catch (Exception e) {

        }
        return 0.0;
    }

    public void insertUser(UserAccount user) {  mUserDao.insert(user);}
    public void deleteAllUsers() {
        mUserDao.deleteAllUsers();
    }
    public UserAccount checkUser(String userName, String pw){
        return mUserDao.checkUser(userName,pw);
    }

    public UserAccount getUser(int id) {
        return mUserDao.getUser(id);
    }

    /*public void connectUserWithDoctor(int user, long doctor) {
        mUserDao.connectUserWithDoctor(user,doctor);
    }*/


  /*  private static class insertUsersToDoctorAsyncTask extends AsyncTask<DoctorWithUsers,Void,Void>{
        protected DoctorDAO docDao;

        insertUsersToDoctorAsyncTask(DoctorDAO dd){
            this.docDao = dd;
        }

        @Override
        protected Void doInBackground(DoctorWithUsers... doctorWithUsers) {
            long indentifier = docDao.insertDoctor(doctorWithUsers[0].doctor);

            for(UserAccount user : doctorWithUsers[0].users){
                user.setDoctorId(indentifier);
            }

            docDao.insertUsers(doctorWithUsers[0].users);
            return null;
        }
    }
*/

    /// DOCTOR REPO
    public Doctor[] getDoctor(){
        return mDoctorDao.getAnyDoctor();
    }
    public void insert(Doctor doctor) {
        new insertAsyncTask(mDoctorDao).execute(doctor);
    }

    public List<UserWithDoctors> getDoctorsByUser(int userId) {
        return mUserDao.getDoctorsByUser(userId);
    }
    /*    public LiveData<List<DoctorWithUsers>> getUsersByDoctors(long doctorId){
        return mDoctorDao.getUsersByDoctor(doctorId);
    }*/

    public List<DoctorWithUsers> getUsersByDoctor(long docId){
        return mDoctorDao.getUsersByDoctor(docId);
    }
    public Doctor checkDoctor(String id, String name) {
        return mDoctorDao.checkDoctor(id,name);
    }

    public Doctor getDoctorById(long loggedDoctorId) {
        return mDoctorDao.getDoctorById(loggedDoctorId);
    }

    public int getNumOfVotes(long loggedDoctorId) {
        return mDoctorDao.getNumOfVotes(loggedDoctorId);
    }

    //public List<QuestionWithUsersAndDoctors> getQuestionsWithUsersAndDoctors() {
     //   return mQuestionDao.getQuestionsWithUsersAndDoctors();
    //}

    private static class insertAsyncTask extends AsyncTask<Doctor, Void, Void> {
        private DoctorDAO mAsyncTaskDao;

        insertAsyncTask(DoctorDAO mDoctorDao) {
            mAsyncTaskDao = mDoctorDao;
        }

        @Override
        protected Void doInBackground(final Doctor... doctors) {
            mAsyncTaskDao.insert(doctors[0]);
            return null;
        }
    }
    private class deleteAllDoctorsAsyncTask extends AsyncTask<Void,Void,Void> {
        private DoctorDAO mAsyncTaskDao;

        public deleteAllDoctorsAsyncTask(DoctorDAO mWordDao) {
            mAsyncTaskDao = mWordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
    private static class deleteDoctorAsyncTask extends AsyncTask<Doctor, Void, Void> {
        private DoctorDAO mAsyncTaskDao;

        deleteDoctorAsyncTask(DoctorDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Doctor... params) {
            mAsyncTaskDao.deleteDoctor(params[0]);
            return null;
        }
    }

    public void deleteAll(){ new deleteAllDoctorsAsyncTask(mDoctorDao).execute();}
    public void deleteDoctor(Doctor doctor){
        new deleteDoctorAsyncTask(mDoctorDao).execute(doctor);
    }
    //END

}
