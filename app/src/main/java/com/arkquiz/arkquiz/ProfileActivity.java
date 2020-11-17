package com.arkquiz.arkquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileActivity extends AppCompatActivity {

    private EditText EditText_username;
    private Spinner Spinner_country;
    private TextView TextView_score, TextView_ranking;
    private FirebaseFirestore db;
    private SharedPreferences sharedPreferences_Id;
    private final String TAG="ProfileActivity";
    private int myScore, myCountryCode, myRank=0;
    private ImageView ImageView_country;
    private Drawable drawable;
    private Button Button_save, Button_check;
    private boolean check;
    private String myUsername, myCountry;
    private CollectionReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        this.getSupportActionBar().hide();

        EditText_username=findViewById(R.id.EditText_profile_username);
        Spinner_country=findViewById(R.id.Spinner_profile_country);
        TextView_score=findViewById(R.id.TextView_profile_score);
        TextView_ranking=findViewById(R.id.TextView_profile_ranking);
        ImageView_country=findViewById(R.id.ImageView_profile_flag);
        Button_save=findViewById(R.id.Button_profile_save);
        Button_check=findViewById(R.id.Button_profile_check);

        db = FirebaseFirestore.getInstance();
        sharedPreferences_Id=getSharedPreferences("ID", MODE_PRIVATE);

        final TypedArray typedArray=getResources().obtainTypedArray(R.array.flag);

        Spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView)adapterView.getChildAt(0)).setTextSize(20);
                drawable=typedArray.getDrawable(i);
                ImageView_country.setImageDrawable(drawable);
                myCountry=adapterView.getItemAtPosition(i).toString();
                myCountryCode=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        DocumentReference docRef = db.collection("users").document(sharedPreferences_Id.getString("ID", ""));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        EditText_username.setText(document.get("nickname").toString());
                        TextView_score.setText(document.get("score").toString());
                        myScore=Integer.parseInt(document.get("score").toString());
                        drawable=typedArray.getDrawable(Integer.parseInt(document.get("countryCode").toString()));
                        ImageView_country.setImageDrawable(drawable);
                        Spinner_country.setSelection(Integer.parseInt(document.get("countryCode").toString()));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        users=db.collection("users");
        users.orderBy("score", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                myRank++;
                                if(Integer.parseInt(document.get("score").toString())==myScore) break;
                            }
                            TextView_ranking.setText(String.valueOf(myRank));
                            Log.d(TAG, "랭킹 점수 측정");
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String temp_string=EditText_username.getText().toString();
                if(temp_string.length()<=0) Toast.makeText(ProfileActivity.this, "Please enter a valid username", Toast.LENGTH_SHORT).show();
                else {
                    check = true;
                    db.collection("users")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            if (temp_string.equals(document.get("nickname").toString())) {
                                                check = false;
                                                Toast.makeText(ProfileActivity.this, "This username already exists.\nPlease choose a different username.", Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                        }
                                        if(check){
                                            myUsername=temp_string;
                                            Toast.makeText(ProfileActivity.this, "You can use this username.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
            }
        });

        Button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditText_username.getText().toString().equals(myUsername)) {
                    check=false;
                    Toast.makeText(ProfileActivity.this, "Please press Check Button before editing your profile.", Toast.LENGTH_SHORT).show();
                }
                if (check) {
                    DocumentReference documentRef = db.collection("users").document(sharedPreferences_Id.getString("ID", ""));
                    documentRef
                            .update("nickname", EditText_username.getText().toString(), "country", myCountry, "countryCode", myCountryCode)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ProfileActivity.this, "Succeed to change profile.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, e.toString());
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent tempIntent=new Intent(this, MainActivity.class);
        tempIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(tempIntent);
        overridePendingTransition(0, 0);

    }
}