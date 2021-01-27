package com.example.pedijatarv2.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.lang.ref.WeakReference;
import java.util.List;

@Database(entities = {Doctor.class, UserAccount.class,Question.class,UserDoctorCrossRef.class}, version = 22, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract QuestionDAO questionDAO();
    public abstract DoctorDAO doctorDAO();
    public abstract UserAccountDAO usersDAO();


    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (AppDatabase.class){
                if(INSTANCE ==  null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,"pedijatar_db")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static AppDatabase.Callback sRoomDatabaseCallback = new AppDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void>{
        private DoctorDAO mDoctor;
        private QuestionDAO mQuestion;
        private UserAccountDAO mUser;

        String [] qs = {"Prvo pitanje","Drugo","Trece","Kada raste prvi zub detetu?", "Musko dete se naglo ugojilo, sta da radim?","Trece pitanje?"};
        String [] fName = {"Nikola", "Ivana","Aleksandra"};
        String [] lName = {"Golubovic","Jovanovic","Culibrk"};


        String [] fNameUser = {"Dalibor","Srdjan","Nevena"};
        String [] uNameUser = {"admin","root","admin123"};
        String [] passwords = {"admin","root","admin123"};

        public PopulateDbAsync(AppDatabase db){
            mDoctor = db.doctorDAO();
            mQuestion = db.questionDAO();
            mUser = db.usersDAO();
        }


        @SuppressLint("LongLogTag")
        @Override
        protected Void doInBackground(Void... voids) {

            if(mDoctor.getAnyDoctor().length < 1){
                for (int i = 0; i<fName.length;i++){
                    Doctor d = new Doctor(fName[i],lName[i]);
                    mDoctor.insert(d);
                    Log.i("doctor-added", fName[i]);
                }
            }

            if(mUser.getAnyUser().length < 1){
                for (int i = 0; i<fNameUser.length;i++){
                    UserAccount ua = new UserAccount(fNameUser[i],lName[i],uNameUser[i],passwords[i]);
                    mUser.insert(ua);
                    Log.i("user-added with username: ", uNameUser[i]);

                }
            }
            int [] userIds = {1,1,2,1,2,3};
            int [] docIds = {1,2,3,1,2,1};
           if(mQuestion.getAnyQuestion().length < 1){
                for (int i = 0; i<qs.length;i++){
                    Question q = new Question(qs[i]);

                    UserDoctorCrossRef u = new UserDoctorCrossRef(userIds[i],docIds[i]);
                    mUser.insertPairs(u);

                    q.setUserId(userIds[i]);
                    q.setDoctorId(docIds[i]);

                    mQuestion.insert(q);
                    Log.i("question-added", qs[i]);



                }
            }

            return null;
        }
    }

}
