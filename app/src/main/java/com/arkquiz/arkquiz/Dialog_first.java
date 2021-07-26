package com.arkquiz.arkquiz;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Dialog_first extends Dialog {

    private Context context;
    private CustomDialogClickListener customDialogClickListener;
    private Button Button_save, Button_check;
    private EditText EditText_nickname;
    private Spinner spinner;
    private String myCountry, myId, myUsername;
    private FirebaseFirestore db;
    private int countryCode;
    private final String TAG="Dialog_first";
    private boolean check=false;

    public Dialog_first(Context context){
        super(context);
    }

    public Dialog_first(@NonNull Context context, CustomDialogClickListener customDialogClickListener) {
        super(context);
        this.context=context;
        this.customDialogClickListener=customDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_first);

        Button_save=findViewById(R.id.Button_save);
        Button_check=findViewById(R.id.Button_check);
        EditText_nickname=findViewById(R.id.EditText_dialog_nickname);
        spinner=findViewById(R.id.Spinner_dialog_country);

        db = FirebaseFirestore.getInstance();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                myCountry=adapterView.getItemAtPosition(i).toString();
                countryCode=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EditText_nickname.getText().toString().equals(myUsername)) check=false;
                if(!check) Toast.makeText(context, "Press Check Button before creating an account.", Toast.LENGTH_SHORT).show();
                else{
                    if(EditText_nickname.getText().toString().length()<=0) Toast.makeText(getContext(), "Please enter a valid username.", Toast.LENGTH_SHORT).show();
                    else    {
                        Log.d("DialogFirst", "닉네임: "+EditText_nickname.getText().toString()+"\n국가:"+myCountry);
                        // Create a new user with a first and last name
                        Map<String, Object> user = new HashMap<>();
                        user.put("nickname", EditText_nickname.getText().toString());
                        user.put("country", myCountry);
                        user.put("score", 0);
                        user.put("countryCode", countryCode);

// Add a new document with a generated ID
                        db.collection("users")
                                .add(user)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                        myId=documentReference.getId();
                                        customDialogClickListener.onPositiveClick(myId);
                                        Log.d(TAG, "ID: "+myId);
                                        Toast.makeText(getContext(), "Account has successfully created.", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Failed to create an account.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        dismiss();
                    }
                }
            }
        });

        Button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String temp_string=EditText_nickname.getText().toString();
                if(temp_string.length()<=0) Toast.makeText(context, "Please enter a valid username.", Toast.LENGTH_SHORT).show();
                else{
                    check=true;
                    db.collection("users")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                            if(temp_string.equals(document.get("nickname").toString())){
                                                check=false;
                                                Toast.makeText(context, "This username already exists.\nPlease choose a different username.", Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                        }
                                        if(check) {
                                            Toast.makeText(context, "You can use this username.", Toast.LENGTH_SHORT).show();
                                            myUsername=temp_string;
                                        }

                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
            }
        });
    }
}
