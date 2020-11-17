package com.arkquiz.arkquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnBackPressedListener{

    private SQLiteDatabase db, db_user_info;
    private DBHelper mDBHelper;
    private TextView TextView_main_dino_egg, TextView_username, TextView_rankingPoints;
    private int current_dino_egg, maxWidth;
    private AdView mAdView;
    private long backKeyPressedTime;
    private Button Button_play, Button_ranking, Button_shop, Button_myPage;
    private Dialog_first dialog;
    private SharedPreferences sharedPreferences_Id;
    private ImageView ImageView_flag;
    private FirebaseFirestore db_firestore;
    private final String TAG="MainActivity";
    private String currentUsername, myScore;
    private Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        this.getSupportActionBar().hide();

        mDBHelper=new DBHelper(this);
        db=mDBHelper.getWritableDatabase();
        TextView_main_dino_egg=findViewById(R.id.TextView_main_dino_egg);
        Button_play=findViewById(R.id.Button_main_play);
        Button_ranking=findViewById(R.id.Button_main_ranking);
        Button_shop=findViewById(R.id.Button_main_shop);
        Button_myPage=findViewById(R.id.Button_main_myPage);
        TextView_username=findViewById(R.id.TextView_main_nickname);
        ImageView_flag=findViewById(R.id.ImageView_main_flag);
        TextView_rankingPoints=findViewById(R.id.TextView_main_rankingPoint);

        mDBHelper.updateTruetoFalse(db);
        db_firestore = FirebaseFirestore.getInstance();
        final TypedArray typedArray=getResources().obtainTypedArray(R.array.flag);

        sharedPreferences_Id=getSharedPreferences("ID", MODE_PRIVATE);
        SharedPreferences sharedPreferences_setAccount=getSharedPreferences("setAccount", MODE_PRIVATE);
        boolean isAccount=sharedPreferences_setAccount.getBoolean("setAccount", false);
        if(!isAccount){
            final SharedPreferences.Editor editor=sharedPreferences_setAccount.edit();
            dialog=new Dialog_first(MainActivity.this, new CustomDialogClickListener() {
                @Override
                public void onPositiveClick(String ID) {
                    SharedPreferences.Editor editor_id=sharedPreferences_Id.edit();
                    editor_id.putString("ID", ID);
                    editor_id.commit();
                    editor.putBoolean("setAccount", true);
                    editor.commit();

                    Intent intent=new Intent(MainActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.copyFrom(dialog.getWindow().getAttributes());
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            Window window=dialog.getWindow();
            window.setAttributes(params);
        }
        else{
            DocumentReference docRef = db_firestore.collection("users").document(sharedPreferences_Id.getString("ID", ""));
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            currentUsername=document.get("nickname").toString();
                            myScore=document.get("score").toString()+" pts";
                            drawable=typedArray.getDrawable(Integer.parseInt(document.get("countryCode").toString()));
                            ImageView_flag.setImageDrawable(drawable);
                            TextView_username.setText(currentUsername);
                            TextView_rankingPoints.setText(myScore);
                        } else {
                        }
                    } else {
                    }
                }
            });
        }

        SharedPreferences sharedPreferences_dino_egg=getSharedPreferences("Dino_egg", MODE_PRIVATE);
        SharedPreferences sharedPreferences=getSharedPreferences("IsFirst", MODE_PRIVATE);

        boolean isFirst4 = sharedPreferences.getBoolean("isFirst4", false);
        if(!isFirst4){ //최초 실행시 true 저장 1.0.6 업데이트
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirst4", true);
            editor.commit();
//            mDBHelper.dbDeleteAll();
//            mDBHelper.loadQuiz(db, this);
//            mDBHelper.loadQuiz2(db, this);
//            mDBHelper.loadQuiz3(db, this);

            SharedPreferences.Editor editor2=sharedPreferences_dino_egg.edit();
            editor2.putInt("dino_egg", 500);
            editor2.commit();
        }

//        boolean isFirst6 = sharedPreferences.getBoolean("isFirst6", false);
//        if(!isFirst6){
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean("isFirst6", true);
//            editor.commit();
//            mDBHelper.loadQuiz4(db, this);
//        }

        boolean isFirst7=sharedPreferences.getBoolean("isFirst7", false);
        if(!isFirst7){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirst7", true);
            editor.commit();
            mDBHelper.dbDeleteAll();
            mDBHelper.loadQuiz(db, this);
            mDBHelper.loadQuiz2(db, this);
            mDBHelper.loadQuiz3(db, this);
            mDBHelper.loadQuiz4(db, this);
        }

        mDBHelper.updateTruetoFalse(db);

        current_dino_egg=sharedPreferences_dino_egg.getInt("dino_egg", 0);
        TextView_main_dino_egg.setText(String.valueOf(current_dino_egg));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        maxWidth=displayMetrics.widthPixels-240;
        TextView_username.setMaxWidth(maxWidth);

        Button.OnClickListener onClickListener= new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.Button_main_play:
                        Intent intent4=new Intent(MainActivity.this, PlayActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.Button_main_ranking:
                        Intent intent3=new Intent(MainActivity.this, RankingActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.Button_main_shop:
                        Intent intent=new Intent(MainActivity.this, Shop.class);
                        startActivity(intent);
                        break;
                    case R.id.Button_main_myPage:
                        Intent intent2=new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent2);
                        break;
                }
            }
        };
        Button_play.setOnClickListener(onClickListener);
        Button_shop.setOnClickListener(onClickListener);
        Button_myPage.setOnClickListener(onClickListener);
        Button_ranking.setOnClickListener(onClickListener);



    }

    //    두 번 누르면 앱 종료
    @Override
    public void onBackPressed() {
        Toast toast=Toast.makeText(MainActivity.this, "Press Back again to Exit", Toast.LENGTH_SHORT);
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            toast.show();
            return;
        }
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
//            finish();
            toast.cancel();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

}