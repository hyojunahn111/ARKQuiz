package com.example.arkquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Random;
import java.util.Scanner;

public class QuizpageEasy extends AppCompatActivity {

    private TextView TextView_quiz, TextView_dino_egg;
    private ImageView ImageView_quiz_image;
    private Button[] btn_selection;
    private Button btn_hint_by_dino_egg, btn_hint_by_ad;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizpage_easy);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/10331737121");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());//전면광고 로드

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView_quiz_easy);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
        if(numberOfQuiz>=10){
//            결과 페이지 로드
            Toast.makeText(this, "정답: "+correct_answer+"/10", Toast.LENGTH_SHORT).show();
            finish();
        }

        btn_selection=new Button[4];

        TextView_quiz=findViewById(R.id.TextView_quiz);
        TextView_dino_egg=findViewById(R.id.TextView_dino_egg);
        ImageView_quiz_image=findViewById(R.id.ImageView_quiz_image);
        btn_selection[0]=findViewById(R.id.button7);
        btn_selection[1]=findViewById(R.id.button8);
        btn_selection[2]=findViewById(R.id.button9);
        btn_selection[3]=findViewById(R.id.button10);
        btn_hint_by_ad=findViewById(R.id.btn_hint_by_ad);
        btn_hint_by_dino_egg=findViewById(R.id.btn_hint_by_dinoegg);


        mDBHelper=new DBHelper(this);
        db=mDBHelper.getReadableDatabase();

        current_dino_egg=sharedPreferences_dino_egg.getInt("dino_egg", 0);

//        mDBHelper.LoadQuiz();

        final Cursor cursor = mDBHelper.LoadSQLiteDBCursor_easy();
        try {
            Log.d("TAG", "cursor의 개수: "+cursor.getCount());
            cursor.moveToFirst();
            setQuiz(cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getBlob(8), cursor.getString(9));


//            Log.d("TAG", "cursor 값: "+cursor.getLong(0)+", "+cursor.getString(1)+","+ cursor.getString(2)+","+ cursor.getString(3)+","+ cursor.getString(4)+","+ cursor.getString(5)+","+ cursor.getString(6));
//            setQuiz(cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
//            while (!cursor.isAfterLast()) {
//                Log.d("TAG", "cursor 값: " + cursor.getLong(0) + ", " + cursor.getString(1) + "," + cursor.getString(2) + "," + cursor.getString(3) + "," + cursor.getString(4) + "," + cursor.getString(5) + "," + cursor.getString(6) + "," + cursor.getString(7));

//                for (int i = 0; i < 4; i++) {
//                    if (i == quiz_answer) {
//                        btn_selection[i].setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(getApplicationContext(), "정답입니다.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    } else {
//                        btn_selection[i].setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(getApplicationContext(), "오답입니다.", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//                cursor.moveToNext();
//            }
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
                    Toast.makeText(getApplicationContext(), "정답입니다.", Toast.LENGTH_SHORT).show();
                    isCorrect=true;
                }
                else Toast.makeText(getApplicationContext(), "오답입니다.", Toast.LENGTH_SHORT).show();
                makeDialog();
//                finish();
//                startActivity(new Intent(QuizpageEasy.this, QuizpageEasy.class));
            }
        });

        btn_selection[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quiz_answer==2) {
                    Toast.makeText(getApplicationContext(), "정답입니다.", Toast.LENGTH_SHORT).show();
                    isCorrect=true;
                }
                else Toast.makeText(getApplicationContext(), "오답입니다.", Toast.LENGTH_SHORT).show();
                makeDialog();
//                finish();
//                startActivity(new Intent(QuizpageEasy.this, QuizpageEasy.class));
            }
        });

        btn_selection[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quiz_answer==3) {
                    Toast.makeText(getApplicationContext(), "정답입니다.", Toast.LENGTH_SHORT).show();
                    isCorrect=true;
                }
                else Toast.makeText(getApplicationContext(), "오답입니다.", Toast.LENGTH_SHORT).show();
                makeDialog();
//                finish();
//                startActivity(new Intent(QuizpageEasy.this, QuizpageEasy.class));
            }
        });

        btn_selection[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quiz_answer==4) {
                    Toast.makeText(getApplicationContext(), "정답입니다.", Toast.LENGTH_SHORT).show();
                    isCorrect=true;
                }
                else Toast.makeText(getApplicationContext(), "오답입니다.", Toast.LENGTH_SHORT).show();
                makeDialog();
//                finish();
//                startActivity(new Intent(QuizpageEasy.this, QuizpageEasy.class));
            }
        });

        btn_hint_by_dino_egg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=sharedPreferences_dino_egg.edit();
                editor.putInt("dino_egg", current_dino_egg-20);
                editor.commit();

                AlertDialog.Builder builder = new AlertDialog.Builder(QuizpageEasy.this);
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
        });

        btn_hint_by_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void setQuiz(String quiz, String selection1, String selection2, String selection3, String selection4, String answer, byte[] image, String hint){
        TextView_quiz.setText(quiz);
//        ImageView_quiz_image.setImageResource(image);
        btn_selection[0].setText(selection1);
        btn_selection[1].setText(selection2);
        btn_selection[2].setText(selection3);
        btn_selection[3].setText(selection4);
        quiz_answer=Integer.parseInt(answer);
        ImageView_quiz_image.setImageBitmap(getBitmapImage(image));
        current_hint=hint;
        Log.d("TAG", "setQuiz 호출 / 퀴즈 넘버: "+numberOfQuiz);
    }

    public Bitmap getBitmapImage(byte[] b){
        Bitmap bitmap= BitmapFactory.decodeByteArray(b, 0, b.length);
        return bitmap;
    }

    public void makeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("정답").setMessage("정답은 "+quiz_answer+"번입니다.")
        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        })
                .setPositiveButton("다음 문제로 넘어가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        Intent intent=new Intent(QuizpageEasy.this, QuizpageEasy.class);
                        intent.putExtra("numberOfQuiz", numberOfQuiz+1);
                        if(isCorrect) intent.putExtra("correctAnswer", correct_answer+1);
                        else intent.putExtra("correctAnswer", correct_answer);
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    public void LoadQuiz(){
//        Log.d("TAG", "loadQuiz 호출");
//        mDBHelper=new DBHelper(QuizpageEasy.this);
//        db=mDBHelper.getWritableDatabase();
//        mDBHelper.onCreate(db);
//        db.beginTransaction();
//
//        try{
//            db.execSQL("INSERT INTO "+mDBHelper.TABLE_NAME+" VALUES(null, '1', '다음 공룡의 이름은 무엇일까요?', '렉스', '디폴로도쿠스', '파라사우롤로푸스', '파키', '3')");
//            db.execSQL("INSERT INTO "+mDBHelper.TABLE_NAME+" VALUES(null, '1', '다음 공룡의 이름은 무엇일까요?', '아르젠타비스', '기가노토파우르스', '파키리노사우루스', '파키', '4')");
//
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
//        mdb.insert(DBHelper.TABLE_NAME, null, contentValues);
//        Log.d("TAG", "insertQuiz 호출 / "+selection1);
//    }
}

/*
        String[] Q = new String[5];
        Q[1] = "다음 공룡의 이름은?";
        Q[2] = "다음 소리를 내는 공룡의 이름은?";
        Q[3] = "오벨리스크의 위치는?";
        Q[4] = "다음 공룡이 나오는 맵은?"; //라그나로크, 더 아일랜드, 더 센터, 스코치드어스, 에버레이션, 익스팅션, 발게로, 제네시스, 크리스탈 아일랜드
        Q[5] = "다음 동굴의 이름은?"; // 강함의 동굴, 하늘군주의 동굴, 사냥꾼의 동굴, 거대함의 동굴, 공백의 동굴, 교활함의 동굴, 그림자의 동굴, 깊이의 동굴, 면역의 동굴, 무리의 동굴, 바위의 동굴, 짐승의 동굴, 현명함의 동굴

        String[] D1 = new String[5];
        D1[1] = "파라사우롤로푸르스";
        D1[2] = "프테라노돈";
        D1[3] = "하이랜드 NE";
        D1[4] = "익스팅션";
        D1[5] = "교활함의 동굴";

        String[] D2 = new String[5];
        D2[1] = "파라케라테리움";
        D2[2] = "아르젠타비스";
        D2[3] = "하이랜드 E";
        D2[4] = "에버레이션";
        D2[5] = "강함의 동굴";

        String[] D3 = new String[5];
        D3[1] = "마나가르마";
        D3[2] = "안칼로사우르스";
        D3[3] = "정글 1";
        D3[4] = "더 센터";
        D3[5] = "공백의 동굴";

        String[] D4 = new String[5];
        D4[1] = "그리핀";
        D4[2] = "케찰";
        D4[3] = "정글 2";
        D4[4] = "라그나로크";
        D4[5] = "하늘군주의 동굴";

        int[] ans = new int[3];
        ans[0] = 3;
        ans[1] = 2;
        ans[2] = 1;

        Random rd = new Random();

        int ran = rd.nextInt(Q.length);
        int answer = 0;
        int DE = 0; //DinoEgg

        //1

        System.out.println(Q[ran]);
        System.out.println("1" + D1[ran]);
        System.out.println("2" + D2[ran]);
        System.out.println("3" + D3[ran]);
        System.out.println("4" + D4[ran]);
        answer = ans[ran];

        Scanner scan = new Scanner(System.in);
        System.out.println("정답을 선택하세요");
        String input = scan.nextLine();
        int inputN = Integer.parseInt(input);

        if(inputN == answer) {
            System.out.println("정답입니다.");
            DE = DE + 100;
        }
        else {
            System.out.println("오답입니다.");
        }
        ran = rd.nextInt(ans.length);

        // 2
        System.out.println(Q[ran]);
        System.out.println("1" + D1[ran]);
        System.out.println("2" + D2[ran]);
        System.out.println("3" + D3[ran]);
        System.out.println("4" + D4[ran]);
        answer = ans[ran];

        scan = new Scanner(System.in);
        System.out.println("정답을 선택하세요");
        input = scan.nextLine();
        inputN = Integer.parseInt(input);

        if(inputN == answer) {
            System.out.println("정답입니다.");
            DE = DE + 100;
        }
        else {
            System.out.println("오답입니다.");
        }
        ran = rd.nextInt(ans.length);

        //3
        System.out.println(Q[ran]);
        System.out.println("1" + D1[ran]);
        System.out.println("2" + D2[ran]);
        System.out.println("3" + D3[ran]);
        System.out.println("4" + D4[ran]);
        answer = ans[ran];

        scan = new Scanner(System.in);
        System.out.println("정답을 선택하세요");
        input = scan.nextLine();
        inputN = Integer.parseInt(input);

        if(inputN == answer) {
            System.out.println("정답입니다.");
            DE = DE + 100;
        }
        else {
            System.out.println("오답입니다.");
        }
        ran = rd.nextInt(ans.length);

        //4
        System.out.println(Q[ran]);
        System.out.println("1" + D1[ran]);
        System.out.println("2" + D2[ran]);
        System.out.println("3" + D3[ran]);
        System.out.println("4" + D4[ran]);
        answer = ans[ran];

        scan = new Scanner(System.in);
        System.out.println("정답을 선택하세요");
        input = scan.nextLine();
        inputN = Integer.parseInt(input);

        if(inputN == answer) {
            System.out.println("정답입니다.");
            DE = DE + 100;
        }
        else {
            System.out.println("오답입니다.");
        }
        ran = rd.nextInt(ans.length);

        //5
        System.out.println(Q[ran]);
        System.out.println("1" + D1[ran]);
        System.out.println("2" + D2[ran]);
        System.out.println("3" + D3[ran]);
        System.out.println("4" + D4[ran]);
        answer = ans[ran];

        scan = new Scanner(System.in);
        System.out.println("정답을 선택하세요");
        input = scan.nextLine();
        inputN = Integer.parseInt(input);

        if(inputN == answer) {
            System.out.println("정답입니다.");
            DE = DE + 100;
        }
        else {
            System.out.println("오답입니다.");
        }
        ran = rd.nextInt(ans.length);

        System.out.println("얻은 공룡알 : " + DE);
*/