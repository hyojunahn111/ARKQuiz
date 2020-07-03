package com.example.arkquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Shop extends AppCompatActivity implements RewardedVideoAdListener{

    private TextView TextView_shop_dino_egg;
    private int current_dino_egg;
    private AdView mAdView;
    private Button btn_ad;
    IInAppBillingService mService;
    String dino_egg_100, dino_egg_500, dino_egg_1000;
    private RewardedVideoAd mRewardedVideoAd;


/*
    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dino_egg);

        TextView_shop_dino_egg = findViewById(R.id.TextView_shop_dino_egg);
        btn_ad=findViewById(R.id.button11);

        final SharedPreferences sharedPreferences_dino_egg = getSharedPreferences("Dino_egg", MODE_PRIVATE);
        current_dino_egg = sharedPreferences_dino_egg.getInt("dino_egg", 0);
        TextView_shop_dino_egg.setText(String.valueOf(current_dino_egg));

//        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView_shop);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());

        btn_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            }
        });
/*
        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE); //mService를 사용하여 구글 플레이와 통신 가능

//        skuList(아이템 리스트)에 아이템 값 삽임
        ArrayList<String> skuList = new ArrayList<String> ();
        skuList.add("공룡알 100개");
        skuList.add("공룡알 500개");
        skuList.add("공룡알 1000개");
        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

//        구글 플레이에서 인앱정보 검색
        try {
            Bundle skuDetails = mService.getSkuDetails(3,
                    getPackageName(), "inapp", querySkus);
            //        구글 플레이의 응답 결과
            int response = skuDetails.getInt("RESPONSE_CODE");
            if (response == 0) {
                ArrayList<String> responseList
                        = skuDetails.getStringArrayList("DETAILS_LIST");

                for (String thisResponse : responseList) {
                    JSONObject object = new JSONObject(thisResponse);
                    String sku = object.getString("productId");
                    String price = object.getString("price");
                    if (sku.equals("공룡알 100개"))  dino_egg_100= price;
                    else if (sku.equals("공룡알 500개")) dino_egg_500 = price;
                    else if (sku.equals("공룡알 1000개")) dino_egg_1000=price;
// 아이템 구매
                    Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),
                            sku, "inapp", "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
                    PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

                    startIntentSenderForResult(pendingIntent.getIntentSender(),
                            1001, new Intent(), Integer.valueOf(0), Integer.valueOf(0),
                            Integer.valueOf(0));
                }
            }
        } catch (RemoteException | JSONException | IntentSender.SendIntentException e) {
            e.printStackTrace();
        }

        try {
            Bundle ownedItems = mService.getPurchases(3, getPackageName(), "inapp", null);
            int response = ownedItems.getInt("RESPONSE_CODE");
            if (response == 0) {
                ArrayList<String> ownedSkus =
                        ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String>  purchaseDataList =
                        ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String>  signatureList =
                        ownedItems.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                String continuationToken =
                        ownedItems.getString("INAPP_CONTINUATION_TOKEN");

                for (int i = 0; i < purchaseDataList.size(); ++i) {
                    String purchaseData = purchaseDataList.get(i);
                    String signature = signatureList.get(i);
                    String sku = ownedSkus.get(i);

                    // do something with this purchase information
                    // e.g. display the updated list of products owned by user
                }

                // if continuationToken != null, call getPurchases again
                // and pass in the token to retrieve more items

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Log.d("tag_ad", "보상형 광고 로드");
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
        Toast.makeText(this, "공룡알 20개가 지급되었습니다!", Toast.LENGTH_SHORT);
        SharedPreferences sharedPreferences_dino_egg = getSharedPreferences("Dino_egg", MODE_PRIVATE);
        current_dino_egg = sharedPreferences_dino_egg.getInt("dino_egg", 0);
        SharedPreferences.Editor editor=sharedPreferences_dino_egg.edit();
        editor.putInt("dino_egg", current_dino_egg+20);
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
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
                    Toast.makeText(this, "You have bought the " + sku + ". Excellent choice, adventurer", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(this, "Failed to parse purchase data.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }*/

//        int response = mService.consumePurchase(3, getPackageName(), token);
/*
//    액티비티의 onDestroy 메서드를 재정의하여 mServiceConn이라는 인앱 결제에 대한 서비스 연결에서 바인딩 해제
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
    }*/
}