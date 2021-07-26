package com.arkquiz.arkquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class Splash extends Activity {

    private SQLiteDatabase db;
    private DBHelper mDBHelper;
    private static final String TAG="SPLASH";
    private SharedPreferences sharedPreferences_dino_egg;
    private  SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 3000);


    }

    private class splashhandler implements Runnable{
        public void run(){
            loadQuizzes();
            startActivity(new Intent(getApplication(), MainActivity.class));
            Splash.this.finish();
        }
    }

    @Override
    public void onBackPressed() {
    }

    private void loadQuizzes(){
        sharedPreferences_dino_egg=getSharedPreferences("Dino_egg", MODE_PRIVATE);
        sharedPreferences=getSharedPreferences("IsFirst", MODE_PRIVATE);

        mDBHelper=new DBHelper(this);
        db=mDBHelper.getWritableDatabase();

        boolean isFirst4 = sharedPreferences.getBoolean("isFirst4", false);
        if(!isFirst4){ //최초 실행시 true 저장 1.0.6 업데이트
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirst4", true);
            editor.apply();
//            mDBHelper.dbDeleteAll();
//            mDBHelper.loadQuiz(db, this);
//            mDBHelper.loadQuiz2(db, this);
//            mDBHelper.loadQuiz3(db, this);

            SharedPreferences.Editor editor2=sharedPreferences_dino_egg.edit();
            editor2.putInt("dino_egg", 500);
            editor2.apply();
        }

        boolean isFirst7=sharedPreferences.getBoolean("isFirst7", false);
        if(!isFirst7){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirst7", true);
            editor.apply();
            mDBHelper.dbDeleteAll();
            mDBHelper.loadQuiz(db, this);
            mDBHelper.loadQuiz2(db, this);
            mDBHelper.loadQuiz3(db, this);
            mDBHelper.loadQuiz4(db, this);

        }

        boolean isFirst8=sharedPreferences.getBoolean("isFirst8", false);
        if(!isFirst8){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirst8", true);
            editor.apply();
            mDBHelper.loadQuiz5(db, this);
        }

        boolean isFirst9=sharedPreferences.getBoolean("isFirst9", false);
        if(!isFirst9){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("isFirst9", true);
            editor.apply();
            mDBHelper.loadQuiz6(db, this);
        }
    }

}
