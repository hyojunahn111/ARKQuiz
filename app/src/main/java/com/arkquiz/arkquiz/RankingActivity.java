package com.arkquiz.arkquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RVItem> list=new ArrayList<>();
    private static final String TAG="RankingActivity";
    private FirebaseFirestore db;
    private CollectionReference users;
    private int currentRank=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        this.getSupportActionBar().hide();

        recyclerView=findViewById(R.id.RecyclerView_ranking);
        db = FirebaseFirestore.getInstance();

        users=db.collection("users");
        users.orderBy("score", Query.Direction.DESCENDING).limit(100)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                addItem(currentRank++, document.get("nickname").toString(), Integer.parseInt(document.get("countryCode").toString()), Integer.parseInt(document.get("score").toString()));
                            }
                            // use this setting to improve performance if you know that changes
                            // in content do not change the layout size of the RecyclerView
                            recyclerView.setHasFixedSize(true);

                            // use a linear layout manager
                            layoutManager = new LinearLayoutManager(RankingActivity.this);
                            recyclerView.setLayoutManager(layoutManager);

                            // specify an adapter (see also next example)
                            mAdapter = new MyAdapter(list, RankingActivity.this);
                            recyclerView.setAdapter(mAdapter);
                            Log.d(TAG, "아이템 개수: "+mAdapter.getItemCount());
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    public void addItem(int ranking, String nickname, int countryCode, int score){
        Log.d(TAG, "addItem 호출 / 랭킹: "+ ranking+" / 닉네임: "+nickname+" / 국가코드: "+countryCode);
        RVItem item=new RVItem();
        item.setRanking(ranking);
        item.setNickname(nickname);
        item.setCountryCode(countryCode);
        item.setScore(score);
        list.add(item);
    }

    @Override
    public void onBackPressed() {
        Intent tempIntent=new Intent(this, MainActivity.class);
        tempIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(tempIntent);
        overridePendingTransition(0, 0);

    }
}
