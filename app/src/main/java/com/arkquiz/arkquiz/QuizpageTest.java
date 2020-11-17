package com.arkquiz.arkquiz;

import androidx.annotation.NonNull;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class QuizpageTest extends AppCompatActivity{
    private TextView TextView_quiz, TextView_dino_egg, TextView_numberOfQuiz, TextView_point;
    private ImageView ImageView_quiz_image;
    private Button[] btn_selection;
    private Button btn_hint_by_dino_egg, btn_hint_by_ad, btn_home;

    private SQLiteDatabase db;
    private DBHelper mDBHelper;

    private Integer quiz_answer=0;
    private int numberOfQuiz=1;
    private int current_dino_egg, currentLevel, currentPoint, myPoint;
    private int correct_answer, myScore;
    private boolean isCorrect;
    private String current_hint;
    private String[] selectionInString;
    private Bitmap current_hint_image;
    private RewardedAd rewardedAd;
    private InterstitialAd mInterstitialAd;
    private FirebaseFirestore db_firestore;
    private SharedPreferences sharedPreferences_Id;
    private SharedPreferences.Editor editor_Id;
    private final String TAG="QuizPageTest";
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizpage_test);

//        this.getSupportActionBar().hide();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = findViewById(R.id.adView_quiz_test);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.admob_front_id));

//        loadAd();
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        selectionInString=new String[4];

        final SharedPreferences sharedPreferences_dino_egg=getSharedPreferences("Dino_egg", MODE_PRIVATE);

        Intent gIntent=getIntent();
        numberOfQuiz=gIntent.getIntExtra("numberOfQuiz", 1);
        correct_answer=gIntent.getIntExtra("correctAnswer", 0);
        myPoint=gIntent.getIntExtra("myPoint", 0);

        isCorrect=false;
        current_hint="";


        btn_selection=new Button[4];

        TextView_quiz=findViewById(R.id.TextView_test_quiz);
        TextView_dino_egg=findViewById(R.id.TextView_test_dino_egg);
        ImageView_quiz_image=findViewById(R.id.ImageView_test_quiz_image);
        btn_selection[0]=findViewById(R.id.btn_test_selection1);
        btn_selection[1]=findViewById(R.id.btn_test_selection2);
        btn_selection[2]=findViewById(R.id.btn_test_selection3);
        btn_selection[3]=findViewById(R.id.btn_test_selection4);
        btn_hint_by_ad=findViewById(R.id.btn_test_hint_by_ad);
        btn_hint_by_dino_egg=findViewById(R.id.btn_test_hint_by_dinoegg);
        btn_home=findViewById(R.id.btn_test_home);
        TextView_numberOfQuiz=findViewById(R.id.TextView_test_numberOfQuiz);
        TextView_point=findViewById(R.id.TextView_test_point);

        TextView_numberOfQuiz.setText(numberOfQuiz+"/10");

        mDBHelper=new DBHelper(this);
        db=mDBHelper.getReadableDatabase();
        db_firestore = FirebaseFirestore.getInstance();

        current_dino_egg=sharedPreferences_dino_egg.getInt("dino_egg", 0);
        TextView_dino_egg.setText(String.valueOf(current_dino_egg));
//        mDBHelper.LoadQuiz();

//        Random random = new Random();
//        random_number=random.nextInt(mDBHelper.getRowCount(db));
        final Cursor cursor = mDBHelper.LoadSQLiteDBCursor_test();
        try {
            Log.d("TAG", "cursor의 개수: "+cursor.getCount());
            cursor.moveToFirst();
//            mDBHelper.isQuizShown[(int)cursor.getLong(0)]=true;
            SQLiteDatabase db2;
            db2=mDBHelper.getWritableDatabase();
            setQuiz(cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getInt(7), cursor.getBlob(8), cursor.getString(9), cursor.getBlob(10));
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
//                    TextView_dino_egg.setText(sharedPreferences_dino_egg.getInt("dino_egg", 0));
                    makeDialog_hint();
                }
                else Toast.makeText(QuizpageTest.this, "There is no dinosaur bone.", Toast.LENGTH_SHORT).show();
                editor.commit();
            }
        });

        btn_hint_by_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(QuizpageTest.this, "Failed to load ad.", Toast.LENGTH_SHORT).show();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(QuizpageTest.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                overridePendingTransition(0, 0); //애니메이션 제거
                AlertDialog.Builder builder=new AlertDialog.Builder(QuizpageTest.this);
                builder.setMessage("You will lose 10 Ranking Points when you exit during Ranking Mode!\nAre you sure to quit?");
                builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mDBHelper.updateTruetoFalse(db);
                        dialog.cancel();
                        Intent tempIntent=new Intent(QuizpageTest.this, MainActivity.class);
                        tempIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(tempIntent);

                        sharedPreferences_Id=getSharedPreferences("ID", MODE_PRIVATE);
                        editor_Id=sharedPreferences_Id.edit();
                        final DocumentReference documentRef = db_firestore.collection("users").document(sharedPreferences_Id.getString("ID", ""));
                        documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        myScore=Integer.parseInt(document.get("score").toString());
                                        if(myScore-10<0) myScore=0;
                                        else myScore-=10;
                                        documentRef
                                                .update("score", myScore)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "update success");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.d("MainActivity", e.toString());
                                                    }
                                                });
                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdOpened() {
                super.onAdOpened();
                makeDialog_hint();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    public void setQuiz(int level, String quiz, String selection1, String selection2, String selection3, String selection4,
                        int answer, byte[] image, String hint, byte[] hint_image){
        currentLevel=level;
        if(level==1) {
            TextView_point.setText("2 pts");
            currentPoint=2;
        }
        else if(level==2)   {
            TextView_point.setText("3 pts");
            currentPoint=3;
        }
        else {
            TextView_point.setText("5 pts");
            currentPoint=5;
        }
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
        quiz_answer=answer;
        ImageView_quiz_image.setImageBitmap(getBitmapImage(image));
        if(hint!=null)current_hint=hint;
        else current_hint="";
        if(hint_image!=null) current_hint_image=getBitmapImage(hint_image);
        Log.d("TAG", "setQuiz 호출 / 퀴즈 넘버: ");
    }

    public Bitmap getBitmapImage(byte[] b){
        Bitmap bitmap= BitmapFactory.decodeByteArray(b, 0, b.length);
        return bitmap;
    }

    public void makeDialog_correct(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String answerInString = selectionInString[quiz_answer - 1];

        LayoutInflater factory = LayoutInflater.from(QuizpageTest.this);
        final View dialog_view = factory.inflate(R.layout.activity_dialog_correct_test, null);

        TextView TextView_title=dialog_view.findViewById(R.id.TextView_dialog_correct_title);
        TextView TextView_text=dialog_view.findViewById(R.id.TextView_dialog_correct_text);
        Button Button_next=dialog_view.findViewById(R.id.Button_dialog_correct_next);
        TextView TextView_points=dialog_view.findViewById(R.id.TextView_dialog_correct_points);

        TextView_title.setText("Correct !");
        TextView_text.setText("The correct answer is " + answerInString);
        TextView_points.setText("+ "+currentPoint+" pts");

        builder.setView(dialog_view);
        AlertDialog alertDialog = builder.create();

        Button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOfQuiz >=10) {
//            결과 페이지 로드
                    makeDialog_finish();
//                            showAd();
                } else {
                    Intent intent = new Intent(QuizpageTest.this, QuizpageTest.class);
                    intent.putExtra("numberOfQuiz", numberOfQuiz + 1);
                    if (isCorrect) {
                        intent.putExtra("correctAnswer", correct_answer + 1);
                        intent.putExtra("myPoint", myPoint+currentPoint);
                    }

                    else {
                        intent.putExtra("correctAnswer", correct_answer);
                        intent.putExtra("myPoint", myPoint-currentPoint);
                    }
                    startActivity(intent);
                }
            }
        });

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void makeDialog_wrong(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String answerInString = selectionInString[quiz_answer - 1];

        LayoutInflater factory = LayoutInflater.from(QuizpageTest.this);
        final View dialog_view = factory.inflate(R.layout.activity_dialog_correct_test, null);

        TextView TextView_title=dialog_view.findViewById(R.id.TextView_dialog_correct_title);
        TextView TextView_text=dialog_view.findViewById(R.id.TextView_dialog_correct_text);
        Button Button_next=dialog_view.findViewById(R.id.Button_dialog_correct_next);
        TextView TextView_points=dialog_view.findViewById(R.id.TextView_dialog_correct_points);

        TextView_title.setText("Wrong !");
        TextView_text.setText("The correct answer is " + answerInString);
        TextView_points.setText("- "+currentPoint+" pts");

        builder.setView(dialog_view);
        AlertDialog alertDialog = builder.create();

        Button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOfQuiz >=10) {
//            결과 페이지 로드
                    makeDialog_finish();
//                            showAd();
                } else {
                    Intent intent = new Intent(QuizpageTest.this, QuizpageTest.class);
                    intent.putExtra("numberOfQuiz", numberOfQuiz + 1);
                    if (isCorrect) {
                        intent.putExtra("correctAnswer", correct_answer + 1);
                        intent.putExtra("myPoint", myPoint+currentPoint);
                    }
                    else {
                        intent.putExtra("correctAnswer", correct_answer);
                        intent.putExtra("myPoint", myPoint-currentPoint);
                    }
                    startActivity(intent);
                }
            }
        });

        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void makeDialog_finish(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(isCorrect) {
            correct_answer++;
            myPoint+=currentPoint;
        }
        else{
            myPoint-=currentPoint;
        }
        sharedPreferences_Id=getSharedPreferences("ID", MODE_PRIVATE);
        editor_Id=sharedPreferences_Id.edit();
        final DocumentReference documentRef = db_firestore.collection("users").document(sharedPreferences_Id.getString("ID", ""));
        documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        myScore=Integer.parseInt(document.get("score").toString());
                        Log.d(TAG, "myScore: "+myScore);

                        if(myScore+myPoint<0) myScore=0;
                        else myScore+=myPoint;
                        documentRef
                                .update("score", myScore)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, e.toString());
                                    }
                                });
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        LayoutInflater factory = LayoutInflater.from(QuizpageTest.this);
        final View dialog_view = factory.inflate(R.layout.activity_dialog_result, null);

        TextView TextView_result=dialog_view.findViewById(R.id.TextView_dialog_result);
        TextView TextView_point=dialog_view.findViewById(R.id.TextView_dialog_point);
        Button Button_ok=dialog_view.findViewById(R.id.Button_dialog_result_ok);
        TextView_result.setText(correct_answer+"/10");
        if(myPoint>=0)   TextView_point.setText("+"+myPoint+" pts");
        else TextView_point.setText(myPoint+" pts");
        Button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(QuizpageTest.this, MainActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                overridePendingTransition(0, 0);
            }
        });

        builder.setView(dialog_view);

//        String quiz_result="Result: "+correct_answer+"/10";
//        String point_result="Point: "+myPoint;
//        builder.setMessage(quiz_result+"\n"+point_result)
//                .setPositiveButton("Back to main page", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        finish();
//                        Intent intent2=new Intent(QuizpageTest.this, MainActivity.class);
//                        intent2.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent2);
//                        mDBHelper.updateTruetoFalse(db);
//
//                        showAd();
//                    }
//                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void makeDialog_hint(){
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizpageTest.this);
        LayoutInflater factory = LayoutInflater.from(QuizpageTest.this);
        final View dialog_view = factory.inflate(R.layout.dialog_hint, null);

        ImageView ImageView_dialog_hint = dialog_view.findViewById(R.id.ImageView_dialog_hint);
        TextView TextView_hint=dialog_view.findViewById(R.id.TextView_dialog_hint);
        Button Button_ok=dialog_view.findViewById(R.id.Button_dialog_hint_OK);

        ImageView_dialog_hint.setImageBitmap(current_hint_image);
        TextView_hint.setText(current_hint);

        builder.setView(dialog_view);
        final AlertDialog alertDialog = builder.create();
        Button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("You will lose 10 Ranking Points when you exit during Ranking Mode!\nAre you sure to quit?");
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mDBHelper.updateTruetoFalse(db);
                dialog.cancel();
                Intent tempIntent=new Intent(QuizpageTest.this, MainActivity.class);
                tempIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(tempIntent);

                sharedPreferences_Id=getSharedPreferences("ID", MODE_PRIVATE);
                editor_Id=sharedPreferences_Id.edit();
                final DocumentReference documentRef = db_firestore.collection("users").document(sharedPreferences_Id.getString("ID", ""));
                documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                myScore=Integer.parseInt(document.get("score").toString());
                                if(myScore-10<0) myScore=0;
                                else myScore-=10;
                                documentRef
                                        .update("score", myScore)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "update success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("MainActivity", e.toString());
                                            }
                                        });
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private void loadAd(){
        this.rewardedAd=new RewardedAd(this, getString(R.string.admob_reward_id));
        RewardedAdLoadCallback adLoadCallback =new RewardedAdLoadCallback(){
            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
            }

            @Override
            public void onRewardedAdFailedToLoad(int i) {
                super.onRewardedAdFailedToLoad(i);
            }
        };
        this.rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

    }

    private void showAd(){
        if(this.rewardedAd.isLoaded()){
            RewardedAdCallback adCallback=new RewardedAdCallback() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                }

                @Override
                public void onRewardedAdOpened() {
                    super.onRewardedAdOpened();
                }

                @Override
                public void onRewardedAdClosed() {
                    super.onRewardedAdClosed();
                }

                @Override
                public void onRewardedAdFailedToShow(int i) {
                    super.onRewardedAdFailedToShow(i);
                    Toast.makeText(QuizpageTest.this, "Failed to load ad.", Toast.LENGTH_SHORT).show();
                    Log.d("보상형 광고", "onRewardedAdFailedToShow+에러 코드:"+i);
//                    Toast.makeText(Shop.this, "Failed to load ad.", Toast.LENGTH_SHORT).show();
                }
            };
            this.rewardedAd.show(this, adCallback);
        }
        else{
        }
    }
}