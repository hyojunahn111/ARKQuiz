package com.example.arkquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db, db_user_info;
    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper=new DBHelper(this);
        db=mDBHelper.getWritableDatabase();

        SharedPreferences sharedPreferences_dino_egg=getSharedPreferences("Dino_egg", MODE_PRIVATE);
        SharedPreferences sharedPreferences=getSharedPreferences("IsFirst", MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean("isFirst", false);
        if(!isFirst){ //최초 실행시 true 저장
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirst", true);
            editor.commit();

            SharedPreferences.Editor editor2=sharedPreferences_dino_egg.edit();
            editor2.putInt("dino_egg", 100);
            editor2.commit();

            mDBHelper.LoadQuiz(db, this);
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizpageEasy.class);
                startActivity(intent);
// 뉴비페이지

            }

        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuizpageNormal.class);
                startActivityForResult(intent, 101);
// 중수페이지

            }

        });

        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuizpageHard.class);
                startActivityForResult(intent, 101);
//고인물페이지

            }

        });

        Button button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuizpageTest.class);
                startActivityForResult(intent, 101);
//ark모의고사

            }

        });

//        Button button5 = (Button)findViewById(R.id.button5);
//        button5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), DinoEgg.class);
//                startActivityForResult(intent, 101);
//
////공룡알
//            }
//
//        });

        Button button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                startActivityForResult(intent, 101);

//상점
            }

        });
    }

//    public void LoadQuiz(){
//        Log.d("TAG", "loadQuiz 호출");
//        mDBHelper=new DBHelper(MainActivity.this);
//        db=mDBHelper.getWritableDatabase();
//        mDBHelper.onCreate(db);
//        db.beginTransaction();
//
//        try{
//            insertQuiz(db,"1", "다음 공룡의 이름은 무엇일까요?", "렉스", "디폴로도쿠스", "파라사우롤로푸스", "파키", "3");
//            insertQuiz(db,"1", "다음 공룡의 이름은 무엇일까요?", "알로사우루스", "기가노토사우르스", "아카티나", "마나가르마", "1" );
//            insertQuiz(db,"1", "다음 공룡의 이름은 무엇일까요?", "그리핀", "파라사우롤로푸스", "안킬로사우르스", "케찰", "3");
//            insertQuiz(db,"1", "다음 공룡의 이름은 무엇일까요?", "안킬로사우르스", "바리오닉스", "벨제부포", "검치호", "2");
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//            db.endTransaction();
//        }
//    }
//
//    public void insertQuiz(SQLiteDatabase mdb, String quiz_level, String quiz, String selection1, String selection2, String selection3, String selection4, String answer){
//        ContentValues contentValues=new ContentValues();
//        contentValues.put(mDBHelper.QUIZ_LEVEL, quiz_level);
//        contentValues.put(mDBHelper.QUIZ, quiz);
////        contentValues.put(mDBHelper.IMAGE, image);
//        contentValues.put(mDBHelper.SELECTION_1, selection1);
//        contentValues.put(mDBHelper.SELECTION_2, selection2);
//        contentValues.put(mDBHelper.SELECTION_3, selection3);
//        contentValues.put(mDBHelper.SELECTION_4, selection4);
//        contentValues.put(mDBHelper.ANSWER, answer);
//        mdb.insert(mDBHelper.TABLE_NAME, null, contentValues);
//        Log.d("TAG", "insertQuiz 호출 / "+selection1);
//    }

}