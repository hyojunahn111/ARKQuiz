package com.example.arkquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class QuizpageNormal extends AppCompatActivity implements RewardedVideoAdListener{

    private TextView TextView_quiz, TextView_dino_egg, TextView_numberOfQuiz;
    private ImageView ImageView_quiz_image;
    private Button[] btn_selection;
    private Button btn_hint_by_dino_egg, btn_hint_by_ad, btn_home;

    private SQLiteDatabase db;
    private DBHelper mDBHelper;

    private Integer quiz_answer=0;
    private int numberOfQuiz=1;
    private int current_dino_egg;
    private int correct_answer;
    private boolean isCorrect;
    private String current_hint;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    private String[] selectionInString;
    private Bitmap current_hint_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizpage_normal);

        this.getSupportActionBar().hide();

        selectionInString=new String[4];

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/10331737121");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());//전면광고 로드

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView_quiz_normal);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener((RewardedVideoAdListener) this);
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());

        final SharedPreferences sharedPreferences_dino_egg=getSharedPreferences("Dino_egg", MODE_PRIVATE);

        Intent gIntent=getIntent();
        numberOfQuiz=gIntent.getIntExtra("numberOfQuiz", 1);
        correct_answer=gIntent.getIntExtra("correctAnswer", 0);

        isCorrect=false;
        current_hint="";

        if(numberOfQuiz%3==0) {
//        전면 광고 삽입
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
            mInterstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                    Log.d("Tag_Ad", "전면 광고 로드 실패 / 에러코드:"+i);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.d("Tag_Ad", "전면 광고 로드 완료");
                }
            });
        }


        btn_selection=new Button[4];

        TextView_quiz=findViewById(R.id.TextView_normal_quiz);
        TextView_dino_egg=findViewById(R.id.TextView_normal_dino_egg);
        ImageView_quiz_image=findViewById(R.id.ImageView_normal_quiz_image);
        btn_selection[0]=findViewById(R.id.btn_normal_selection1);
        btn_selection[1]=findViewById(R.id.btn_normal_selection2);
        btn_selection[2]=findViewById(R.id.btn_normal_selection3);
        btn_selection[3]=findViewById(R.id.btn_normal_selection4);
        btn_hint_by_ad=findViewById(R.id.btn_normal_hint_by_ad);
        btn_hint_by_dino_egg=findViewById(R.id.btn_normal_hint_by_dinoegg);
        btn_home=findViewById(R.id.btn_normal_home);
        TextView_numberOfQuiz=findViewById(R.id.TextView_normal_numberOfQuiz);

        TextView_numberOfQuiz.setText(numberOfQuiz+"/10");

        mDBHelper=new DBHelper(this);
        db=mDBHelper.getReadableDatabase();

        current_dino_egg=sharedPreferences_dino_egg.getInt("dino_egg", 0);
        TextView_dino_egg.setText(String.valueOf(current_dino_egg));
//        mDBHelper.LoadQuiz();

//        Random random = new Random();
//        random_number=random.nextInt(mDBHelper.getRowCount(db));
        final Cursor cursor = mDBHelper.LoadSQLiteDBCursor_normal();
        try {
            Log.d("TAG", "cursor의 개수: "+cursor.getCount());
            cursor.moveToFirst();
//            mDBHelper.isQuizShown[(int)cursor.getLong(0)]=true;
            SQLiteDatabase db2;
            db2=mDBHelper.getWritableDatabase();
            setQuiz(db, cursor.getLong(0), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getBlob(8), cursor.getString(9), cursor.getBlob(10));
            Log.d("TAG", "현재 isShown: "+cursor.getInt(11));
            mDBHelper.updateFalseToTrue(db2, (int)cursor.getLong(0));
            Log.d("TAG", "updateFalseToTrue 아후 isShown: "+cursor.getInt(11));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "Exception 발생");
        }
//        finally {
//        if (cursor != null) {
//            cursor.close();
//        }
//    }

        btn_selection[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quiz_answer==1) {
                    makeDialog_correct();
                    isCorrect=true;
                }
                else makeDialog_wrong();

//                finish();
//                startActivity(new Intent(QuizpageEasy.this, QuizpageEasy.class));
            }
        });

        btn_selection[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quiz_answer==2) {
                    makeDialog_correct();
                    isCorrect=true;
                }
                else makeDialog_wrong();
//                finish();
//                startActivity(new Intent(QuizpageEasy.this, QuizpageEasy.class));
            }
        });

        btn_selection[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quiz_answer==3) {
                    makeDialog_correct();
                    isCorrect=true;
                }
                else makeDialog_wrong();
//                finish();
//                startActivity(new Intent(QuizpageEasy.this, QuizpageEasy.class));
            }
        });

        btn_selection[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quiz_answer==4) {
                    makeDialog_correct();
                    isCorrect=true;
                }
                else makeDialog_wrong();
//                finish();
//                startActivity(new Intent(QuizpageEasy.this, QuizpageEasy.class));
            }
        });

        btn_hint_by_dino_egg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=sharedPreferences_dino_egg.edit();
                if(sharedPreferences_dino_egg.getInt("dino_egg", 0)>=20){
                    editor.putInt("dino_egg", current_dino_egg-20);
                    makeDialog_hint();
                }
                else Toast.makeText(QuizpageNormal.this, "공룡 알이 부족합니다.", Toast.LENGTH_SHORT).show();
                editor.commit();
            }
        });

        btn_hint_by_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                    makeDialog_hint();
                }
                else{
                    Toast.makeText(QuizpageNormal.this, "광고 로드에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizpageNormal.super.onBackPressed();
            }
        });
    }

    public void setQuiz(SQLiteDatabase db, long id, String quiz, String selection1, String selection2, String selection3, String selection4,
                        String answer, byte[] image, String hint, byte[] hint_image){
        TextView_quiz.setText(quiz);
//        ImageView_quiz_image.setImageResource(image);
        btn_selection[0].setText(selection1);
        btn_selection[1].setText(selection2);
        btn_selection[2].setText(selection3);
        btn_selection[3].setText(selection4);
        selectionInString[0]=selection1;
        selectionInString[1]=selection2;
        selectionInString[2]=selection3;
        selectionInString[3]=selection4;
        quiz_answer=Integer.parseInt(answer);
        ImageView_quiz_image.setImageBitmap(getBitmapImage(image));
        if(hint!=null)current_hint=hint;
        else current_hint="";
        if(hint_image!=null) current_hint_image=getBitmapImage(hint_image);
        Log.d("TAG", "setQuiz 호출 / 퀴즈 넘버: "+numberOfQuiz);
    }

    public Bitmap getBitmapImage(byte[] b){
        Bitmap bitmap= BitmapFactory.decodeByteArray(b, 0, b.length);
        return bitmap;
    }

    public void makeDialog_correct(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String answerInString=selectionInString[quiz_answer-1];
        builder.setTitle("정답입니다!").setMessage("정답은 "+answerInString+"입니다.")
//        .setNeutralButton("", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//            }
//        })
                .setPositiveButton("다음 문제로 넘어가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(numberOfQuiz>=10){
//            결과 페이지 로드
                            makeDialog_finish();
                        }
                        else{
                            Intent intent=new Intent(QuizpageNormal.this, QuizpageNormal.class);
                            intent.putExtra("numberOfQuiz", numberOfQuiz+1);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            if(isCorrect) intent.putExtra("correctAnswer", correct_answer+1);
                            else intent.putExtra("correctAnswer", correct_answer);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void makeDialog_wrong(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String answerInString=selectionInString[quiz_answer-1];
        builder.setTitle("오답입니다!").setMessage("정답은 "+answerInString+"입니다.")
//                .setNeutralButton("", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                })
                .setPositiveButton("다음 문제로 넘어가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(numberOfQuiz>=10){
//            결과 페이지 로드
                            makeDialog_finish();
                        }
                        else{
                            Intent intent=new Intent(QuizpageNormal.this, QuizpageNormal.class);
                            intent.putExtra("numberOfQuiz", numberOfQuiz+1);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            if(isCorrect) intent.putExtra("correctAnswer", correct_answer+1);
                            else intent.putExtra("correctAnswer", correct_answer);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void makeDialog_finish(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String quiz_result="결과: "+correct_answer+"/10";
        builder.setMessage(quiz_result)
                .setPositiveButton("메인 페이지로 돌아가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        Intent intent2=new Intent(QuizpageNormal.this, MainActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        mDBHelper.updateTruetoFalse(db);
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void makeDialog_hint(){
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizpageNormal.this);
        LayoutInflater factory = LayoutInflater.from(QuizpageNormal.this);
        final View dialog_view = factory.inflate(R.layout.dialog_hint, null);

        ImageView ImageView_dialog_hint=dialog_view.findViewById(R.id.ImageView_dialog_hint);
        ImageView_dialog_hint.setImageBitmap(current_hint_image);

        builder.setTitle("힌트").setMessage(current_hint).setView(dialog_view)
                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("메인 화면으로 나가시겠습니까?");
        builder.setPositiveButton("나가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent tempIntent=new Intent(QuizpageNormal.this, MainActivity.class);
                tempIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(tempIntent);            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Log.d("tag_ad", "보상형 광고 로드 성공!");
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizpageNormal.this);
        builder.setTitle("힌트").setMessage(current_hint)
                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Log.d("tag_ad", "보상형 광고 로드 실패 / 에러 코드: "+i);
    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}