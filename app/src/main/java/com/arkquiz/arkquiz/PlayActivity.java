package com.arkquiz.arkquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity {

    private ImageButton ImageButton_beginner, ImageButton_intermediate, ImageButton_advanced, ImageButton_rankingMode
            , ImageButton_home, ImageButton_help;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        this.getSupportActionBar().hide();

        ImageButton_beginner=findViewById(R.id.ImageButton_play_beginner);
        ImageButton_intermediate=findViewById(R.id.ImageButton_play_intermediate);
        ImageButton_advanced=findViewById(R.id.ImageButton_play_advanced);
        ImageButton_rankingMode=findViewById(R.id.ImageButton_play_rankingMode);
        ImageButton_help=findViewById(R.id.ImageButton_help);
        ImageButton_home=findViewById(R.id.ImageButton_play_home);

        ImageButton.OnClickListener onClickListener=new ImageButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.ImageButton_play_beginner:
                        Intent intent1=new Intent(PlayActivity.this, QuizpageEasy.class);
                        startActivity(intent1);
                        break;
                    case R.id.ImageButton_play_intermediate:
                        Intent intent2=new Intent(PlayActivity.this, QuizpageNormal.class);
                        startActivity(intent2);
                        break;
                    case R.id.ImageButton_play_advanced:
                        Intent intent3=new Intent(PlayActivity.this, QuizpageHard.class);
                        startActivity(intent3);
                        break;
                    case R.id.ImageButton_play_rankingMode:
                        Intent intent4=new Intent(PlayActivity.this, QuizpageTest.class);
                        startActivity(intent4);
                        break;
                    case R.id.ImageButton_help:
                        hintDialog();
                        break;
                    case R.id.ImageButton_play_home:
                        PlayActivity.super.onBackPressed();
                        break;
                }
            }
        };
        ImageButton_beginner.setOnClickListener(onClickListener);
        ImageButton_intermediate.setOnClickListener(onClickListener);
        ImageButton_advanced.setOnClickListener(onClickListener);
        ImageButton_rankingMode.setOnClickListener(onClickListener);
        ImageButton_home.setOnClickListener(onClickListener);
        ImageButton_help.setOnClickListener(onClickListener);
    }

    private void hintDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater factory = LayoutInflater.from(this);
//        final View dialog_view = factory.inflate(R.layout.activity_dialog_result, null);
        builder.setTitle("Help")
                .setMessage("In Ranking Mode, you can get Ranking Points or lose Ranking Points.\n" +
                        "With Ranking Points, you can compete your ARK knowledge with users all over the world!\n")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent tempIntent=new Intent(this, MainActivity.class);
        tempIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(tempIntent);
        overridePendingTransition(0, 0);

    }
}
