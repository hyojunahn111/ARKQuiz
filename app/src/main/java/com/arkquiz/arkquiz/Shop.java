package com.arkquiz.arkquiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// import com.android.vending.billing.IInAppBillingService;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
//import com.android.vending.billing.IInAppBillingService;
//import com.anjlab.android.iab.v3.BillingProcessor;
//import com.anjlab.android.iab.v3.SkuDetails;
//import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shop extends AppCompatActivity implements PurchasesUpdatedListener {

    private TextView TextView_shop_dino_egg, TextView_dinobone200, TextView_dinobone1000, TextView_dinobone2000;
    private int current_dino_egg;
    private Button btn_ad, btn_home, btn_dino_bone_200, btn_dino_bone_2000, btn_dino_bone_1000;
//    IInAppBillingService mService;
    private String dino_egg_100, dino_egg_500, dino_egg_1000;
    SkuDetails skuDetails200, skuDetails1000, skuDetails2000, skuDetails_purchase;

//    private PurchaseHeartsAdapter skusAdapter;
//    private BillingProcessor bp;
    public static ArrayList<SkuDetails> skuDetailsList;
    private String licenseKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjD+PhZ101elYRYcN9zfdZKLFy8DQYlLcqGnDEYqdhru8/ntjUE1oXC+nan9HbijEjfmYdTiR6MtwK0uo56P5zN7ii4UMWebvcySBgnIs2WlJ1DPFN1odbrj+xKtgK2090D0mLJzTgcuEKUSj+8/+mabf+2tTeSzsgao+G80xgtdAl4Ixq2dotfv+O52cNzftm83m8BjVNyaYrTKMemmBDn5M1W2zulfwWp1ftmLHTvUXM5ku+PfnVX7KSFRqRL/y6CC4yhjBttQvMhn8bXJDHC36aR4Zh68JcnDuzWr8enhhq3XNKbf9MjSp1VCQft058pv8T/iZL8gISf/d6gKYnQIDAQAB";
//    private MaterialDialog purchaseDialog;
    private BillingClient billingClient;
    private String price_dinobone200, price_dinobone1000, price_dinobone2000;
    private RewardedAd rewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dino_egg);

        TextView_shop_dino_egg = findViewById(R.id.TextView_shop_dino_egg);
        TextView_dinobone200=findViewById(R.id.TextView_dinobone200);
        TextView_dinobone1000=findViewById(R.id.TextView_dinobone1000);
        TextView_dinobone2000=findViewById(R.id.TextView_dinobone2000);
        btn_ad=findViewById(R.id.button11);
        btn_home=findViewById(R.id.button20);
        btn_dino_bone_200=findViewById(R.id.Button_dinobone200);
        btn_dino_bone_1000=findViewById(R.id.Button_dinobone1000);
        btn_dino_bone_2000=findViewById(R.id.Button_dinobone2000);

        this.getSupportActionBar().hide();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        loadAd();

        final SharedPreferences sharedPreferences_dino_egg = getSharedPreferences("Dino_egg", MODE_PRIVATE);
        current_dino_egg = sharedPreferences_dino_egg.getInt("dino_egg", 0);
        TextView_shop_dino_egg.setText(String.valueOf(current_dino_egg));

        setUpBillingClient();

        btn_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAd();
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shop.super.onBackPressed();
            }
        });

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
                    SharedPreferences sharedPreferences_dino_egg = getSharedPreferences("Dino_egg", MODE_PRIVATE);
                    current_dino_egg = sharedPreferences_dino_egg.getInt("dino_egg", 0);
                    SharedPreferences.Editor editor=sharedPreferences_dino_egg.edit();
                    editor.putInt("dino_egg", current_dino_egg+100);
                    editor.commit();
                    Toast.makeText(Shop.this, "100 Dinobones are rewarded!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onRewardedAdOpened() {
                    super.onRewardedAdOpened();
                }

                @Override
                public void onRewardedAdClosed() {
                    super.onRewardedAdClosed();
                    loadAd();
                }

                @Override
                public void onRewardedAdFailedToShow(int i) {
                    super.onRewardedAdFailedToShow(i);
//                    Log.d("보상형 광고", "onRewardedAdFailedToShow+에러 코드:"+i);
                    Toast.makeText(Shop.this, "Failed to load ad.", Toast.LENGTH_SHORT).show();
                }
            };
            this.rewardedAd.show(this, adCallback);
        }
        else{
        }
    }

    private void doBillingFlow(SkuDetails skuDetails) {
//        Toast.makeText(Shop.this, "doBillingFlow 호출"+skuDetails, Toast.LENGTH_SHORT).show();

        BillingFlowParams flowParams;

        // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
        flowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails).build();
        billingClient.launchBillingFlow(Shop.this, flowParams); // 구매화면 띄우기

    }

    private void handlePurchase(Purchase purchase) {
//        Toast.makeText(Shop.this, "handlePurchase 호출"+purchase.getSku(), Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences_dino_egg = getSharedPreferences("Dino_egg", MODE_PRIVATE);
        current_dino_egg = sharedPreferences_dino_egg.getInt("dino_egg", 0);
        SharedPreferences.Editor editor=sharedPreferences_dino_egg.edit();
        if(purchase.getSku().equals("dinobone100")){
            editor.putInt("dino_egg", current_dino_egg+200);
            Toast.makeText(Shop.this, "200 dinobones are rewarded!", Toast.LENGTH_SHORT);
        }
        else if(purchase.getSku().equals("dinobone500")){
            editor.putInt("dino_egg", current_dino_egg+1000);
            Toast.makeText(Shop.this, "1000 dinobones are rewarded!", Toast.LENGTH_SHORT);
        }
        else if(purchase.getSku().equals("dinobone1000")){
            editor.putInt("dino_egg", current_dino_egg+1000);
            Toast.makeText(Shop.this, "2000 dinobones are rewarded!", Toast.LENGTH_SHORT);
        }
        editor.commit();
        TextView_shop_dino_egg.setText(sharedPreferences_dino_egg.getInt("dino_egg", 0));
        ConsumeParams consumeParams = ConsumeParams
                .newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .setDeveloperPayload(purchase.getDeveloperPayload())
                .build();
        billingClient.consumeAsync(consumeParams, consumeResponseListener);
    }

    ConsumeResponseListener consumeResponseListener=new ConsumeResponseListener() {
        @Override
        public void onConsumeResponse(BillingResult billingResult, String s) {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                // Handle the success of the consume operation.
                // For example, increase the number of coins inside the user's basket.

            }
        }
    };

//     구글 플레이에 연결
    public void setUpBillingClient(){
        billingClient=BillingClient.newBuilder(this).setListener(this).enablePendingPurchases().build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if(billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK){
//                    Toast.makeText(Shop.this, "결제 서비스 연결에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    loadProduct();
                }
                else{
                    Toast.makeText(Shop.this, "Failed to connect with billing service.", Toast.LENGTH_SHORT).show();
                    Log.d("결제 오류", ""+billingResult);
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(Shop.this, "Failed to connect with billing service.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadProduct(){
        if(billingClient.isReady()){
//            Toast.makeText(Shop.this, "결제 클라이언트가 준비되었습니다.", Toast.LENGTH_SHORT).show();
            SkuDetailsParams params= SkuDetailsParams.newBuilder()
                    .setSkusList(Arrays.asList("dinobone100", "dinobone500", "dinobone1000"))
                    .setType(BillingClient.SkuType.INAPP)
                    .build();

            billingClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {
                @Override
                public void onSkuDetailsResponse(final BillingResult billingResult, List<com.android.billingclient.api.SkuDetails> skuDetailsList) {
                     if(billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK){
                         if(skuDetailsList.size()!=0){
                             for (SkuDetails skuDetails : skuDetailsList) {
                                 String sku = skuDetails.getSku();
                                 String price = skuDetails.getPrice();
                                 if ("dinobone100".equals(sku)) {
                                     price_dinobone200 = price;
                                     skuDetails200=skuDetails;
                                     TextView_dinobone200.setText(price_dinobone200);
                                 } else if ("dinobone500".equals(sku)) {
                                     price_dinobone1000 = price;
                                     skuDetails1000=skuDetails;
                                     TextView_dinobone1000.setText(price_dinobone1000);
                                 } else if("dinobone1000".equals(sku)){
                                     price_dinobone2000=price;
                                     skuDetails2000=skuDetails;
                                     TextView_dinobone2000.setText(price_dinobone2000);
                                 }
                                 btn_dino_bone_200.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         doBillingFlow(skuDetails200);
//                                         skuDetails_purchase=skuDetails200;
                                     }
                                 });

                                 btn_dino_bone_1000.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         doBillingFlow(skuDetails1000);
//                                         skuDetails_purchase=skuDetails1000;
                                     }
                                 });

                                 btn_dino_bone_2000.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         doBillingFlow(skuDetails2000);
//                                         skuDetails_purchase=skuDetails2000;
                                     }
                                 });
                             }
                         }
                     }
                     else{
                         Toast.makeText(Shop.this, "Failed to load item list.", Toast.LENGTH_SHORT).show();
                     }
                }
            });
        }
        else{
            Toast.makeText(Shop.this, "Billing Client is not prepared.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        billingClient.endConnection();
        super.onDestroy();
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> list) {
//        Toast.makeText(Shop.this, "onPurchaseUpdated 호출", Toast.LENGTH_SHORT).show();
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && list != null) {
            for (Purchase purchase : list) {
                handlePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
            Toast.makeText(Shop.this, "Billing canceled.", Toast.LENGTH_SHORT);
        } else {
            // Handle any other error codes.
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Shop.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}