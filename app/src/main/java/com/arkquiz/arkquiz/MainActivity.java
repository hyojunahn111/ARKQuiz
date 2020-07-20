package com.arkquiz.arkquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener{

    private SQLiteDatabase db, db_user_info;
    private DBHelper mDBHelper;
    private TextView TextView_main_dino_egg;
    private int current_dino_egg;
    private AdView mAdView;
    private long backKeyPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().hide();

        mDBHelper=new DBHelper(this);
        db=mDBHelper.getWritableDatabase();
        TextView_main_dino_egg=findViewById(R.id.TextView_main_dino_egg);

        mDBHelper.updateTruetoFalse(db);

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

        boolean isFirst2 = sharedPreferences.getBoolean("isFirst2", false);
        if(!isFirst2){ //최초 실행시 true 저장 1.0.5 업데이트
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirst2", true);
            editor.commit();

            mDBHelper.loadQuiz2(db, this);
        }

        boolean isFirst3 = sharedPreferences.getBoolean("isFirst3", false);
        if(!isFirst3){ //최초 실행시 true 저장 1.0.6 업데이트
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirst3", true);
            editor.commit();

            mDBHelper.loadQuiz3(db, this);
        }

        mDBHelper.updateTruetoFalse(db);

        current_dino_egg=sharedPreferences_dino_egg.getInt("dino_egg", 0);
        TextView_main_dino_egg.setText(String.valueOf(current_dino_egg));

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
                startActivity(intent);
//                startActivityForResult(intent, 101);
// 중수페이지

            }

        });

        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuizpageHard.class);
                startActivity(intent);
//                startActivityForResult(intent, 101);
//고인물페이지

            }

        });

        Button button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuizpageTest.class);
                startActivity(intent);
//                startActivityForResult(intent, 101);
//ark모의고사

            }

        });
/*
        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DinoEgg.class);
                startActivity(intent);
//                startActivityForResult(intent, 101);

//공룡알
            }

        });*/

        Button button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Shop.class);
                startActivity(intent);
//                startActivityForResult(intent, 101);

//상점
            }

        });
    }

//    두 번 누르면 앱 종료
    @Override
    public void onBackPressed() {
        Toast toast=Toast.makeText(MainActivity.this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT);
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast.show();
            return;
        }
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            finish();
            toast.cancel();
        }
    }

}