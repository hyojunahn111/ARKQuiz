package com.arkquiz.arkquiz;

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
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Shop extends AppCompatActivity implements PurchasesUpdatedListener {

    private TextView TextView_shop_dino_egg, TextView_dinobone200, TextView_dinobone1000, TextView_dinobone2000;
    private int current_dino_egg;
    private AdView mAdView;
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
        TextView_dinobone200=findViewById(R.id.TextView_dinobone200);
        TextView_dinobone1000=findViewById(R.id.TextView_dinobone1000);
        TextView_dinobone2000=findViewById(R.id.TextView_dinobone2000);
        btn_ad=findViewById(R.id.button11);
        btn_home=findViewById(R.id.button20);
        btn_dino_bone_200=findViewById(R.id.Button_dinobone200);
        btn_dino_bone_1000=findViewById(R.id.Button_dinobone1000);
        btn_dino_bone_2000=findViewById(R.id.Button_dinobone2000);

        this.getSupportActionBar().hide();

        final SharedPreferences sharedPreferences_dino_egg = getSharedPreferences("Dino_egg", MODE_PRIVATE);
        current_dino_egg = sharedPreferences_dino_egg.getInt("dino_egg", 0);
        TextView_shop_dino_egg.setText(String.valueOf(current_dino_egg));

        setUpBillingClient();
/*
        bp=new BillingProcessor(this, licenseKey, null, new BillingProcessor.IBillingHandler() {
            //     특정 ID를 가진 아이템의 구매 성공 시 호출
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {

            }
//    앱 내에서 구매를 했는지 안했는지 체크
            @Override
            public void onPurchaseHistoryRestored() {

            }
//    구매시 발생하는 에러
// 구매자가 구매과정에서 그냥 취소해도 발생되는데, 이때의 errorCode는  Constants.BILLING_RESPONSE_RESULT_USER_CANCELED 라고한다.
            @Override
            public void onBillingError(int errorCode, Throwable error) {

            }
//            BillingProcessor가 초기화되고, 구매 준비가 되면 호출
//   구매할 아이템들을 리스트로 구성해서 보여주는 코드를 구현하면 됨
            @Override
            public void onBillingInitialized() {

            }
        });*/

//        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
/*
        btn_dino_bone_200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Shop.this, "상품을 준비중입니다.", Toast.LENGTH_SHORT).show();
//                bp.purchase(this, "dinobone200");
//                BillingFlowParams billingFlowParams=BillingFlowParams.newBuilder()
//                        .setSkuDetails(skuDetailsList.get(0)).build();
//                billingClient.launchBillingFlow(Shop.this, billingFlowParams);
                doBillingFlow(skuDetails200);
            }
        });

        btn_dino_bone_1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Shop.this, "상품을 준비중입니다.", Toast.LENGTH_SHORT).show();
//                bp.purchase(this, "dinobone1000");
//                BillingFlowParams billingFlowParams=BillingFlowParams.newBuilder()
//                        .setSkuDetails(skuDetailsList.get(1)).build();
//                billingClient.launchBillingFlow(Shop.this, billingFlowParams);
                doBillingFlow(skuDetails1000);
            }
        });

        btn_dino_bone_2000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Shop.this, "상품을 준비중입니다.", Toast.LENGTH_SHORT).show();
//                bp.purchase(this, "dinobone2000");
//                BillingFlowParams billingFlowParams=BillingFlowParams.newBuilder()
//                        .setSkuDetails(skuDetailsList.get(2)).build();
//                billingClient.launchBillingFlow(Shop.this, billingFlowParams);
//                SharedPreferences sharedPreferences_dino_egg = getSharedPreferences("Dino_egg", MODE_PRIVATE);
                doBillingFlow(skuDetails2000);
            }
        });
*/
        btn_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shop.super.onBackPressed();
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        if (bp != null) {
//            bp.release();
//        }
//        super.onDestroy();
//    }

    private void doBillingFlow(SkuDetails skuDetails) {
        Toast.makeText(Shop.this, "doBillingFlow 호출"+skuDetails, Toast.LENGTH_SHORT).show();

        BillingFlowParams flowParams;

        // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
        flowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails).build();
        billingClient.launchBillingFlow(Shop.this, flowParams); // 구매화면 띄우기

        /*if(responseCode == BillingClient.BillingResponse.ITEM_ALREADY_OWNED) {
            Purchase.PurchasesResult purchasesResult = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
            onPurchasesUpdated(BillingClient.BillingResponse.OK, purchasesResult.getPurchasesList());
        }*/
    }
/*
    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {
        Toast.makeText(Shop.this, "onPurchaseUpdated 호출", Toast.LENGTH_SHORT);
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }
*/
    private void handlePurchase(Purchase purchase) {
        Toast.makeText(Shop.this, "handlePurchase 호출"+purchase.getSku(), Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences_dino_egg = getSharedPreferences("Dino_egg", MODE_PRIVATE);
        current_dino_egg = sharedPreferences_dino_egg.getInt("dino_egg", 0);
        SharedPreferences.Editor editor=sharedPreferences_dino_egg.edit();
        if(purchase.getSku().equals("dinobone100")){
            editor.putInt("dino_egg", current_dino_egg+200);
            Toast.makeText(Shop.this, "공룡뼈 200개가 추가되었습니다!", Toast.LENGTH_SHORT);
        }
        else if(purchase.getSku().equals("dinobone500")){
            editor.putInt("dino_egg", current_dino_egg+1000);
            Toast.makeText(Shop.this, "공룡뼈 1000개가 추가되었습니다!", Toast.LENGTH_SHORT);
        }
        else if(purchase.getSku().equals("dinobone1000")){
            editor.putInt("dino_egg", current_dino_egg+1000);
            Toast.makeText(Shop.this, "공룡뼈 2000개가 추가되었습니다!", Toast.LENGTH_SHORT);
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
                    Toast.makeText(Shop.this, "결제 서비스 연결에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    loadProduct();
                }
                else{
                    Toast.makeText(Shop.this, "결제에 서비스 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("결제 오류", ""+billingResult);
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(Shop.this, "결제 서바스와 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadProduct(){
        if(billingClient.isReady()){
            Toast.makeText(Shop.this, "결제 클라이언트가 준비되었습니다.", Toast.LENGTH_SHORT).show();
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
                         Toast.makeText(Shop.this, "상품 목록을 로드해오는 것을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                     }
                }
            });
        }
        else{
            Toast.makeText(Shop.this, "결제 클라이언트가 준비되지 않았습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        billingClient.endConnection();
        super.onDestroy();
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> list) {
        Toast.makeText(Shop.this, "onPurchaseUpdated 호출", Toast.LENGTH_SHORT).show();
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && list != null) {
            for (Purchase purchase : list) {
                handlePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
            Toast.makeText(Shop.this, "결제를 취소하셨습니다.", Toast.LENGTH_SHORT);
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
    ////        특정 ID를 가진 아이템의 구매 성공 시 호출
//    @Override
//    public void onProductPurchased(String productId, TransactionDetails details) {
//
//    }
//
////    앱네에서 구매를 했는지 안했는지 체크크
//   @Override
//    public void onPurchaseHistoryRestored() {
//
//    }
//
////    구매시 발생하는 에러
//// 구매자가 구매과정에서 그냥 취소해도 발생되는데, 이때의 errorCode는  Constants.BILLING_RESPONSE_RESULT_USER_CANCELED 라고한다.
//// 에러 처리를 할때 errorCode가 Constants.BILLING_RESPONSE_RESULT_USER_CANCELED 가 아닌경우에만 에러 메세지를 띄울 수 있도록 하자.
//    @Override
//    public void onBillingError(int errorCode, Throwable error) {
//
//    }
//
////    BillingProcessor가 초기화되고, 구매 준비가 되면 호출
////    구매할 아이템들을 리스트로 구성해서 보여주는 코드를 구현하면 됨
//    @Override
//    public void onBillingInitialized() {
//        products=(ArrayList<SkuDetails>) bp.getPurchaseListingDetails(new );
//    }
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