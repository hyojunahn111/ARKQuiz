package com.arkquiz.arkquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import java.io.ByteArrayOutputStream;

import static java.sql.Types.NULL;

public class DBHelper extends SQLiteOpenHelper {

    public static final String ID="id";
    public static final String DATABASE_NAME="ARKQuiz.db";
    public static final String TABLE_NAME="Table_ARK_Quiz";
    public static final String QUIZ_LEVEL="quiz_level";
    public static final String QUIZ="quiz";
    public static final String IMAGE="image";
    public static final String SELECTION_1="selection_1";
    public static final String SELECTION_2="selection_2";
    public static final String SELECTION_3="selection_3";
    public static final String SELECTION_4="selection_4";
    public static final String ANSWER="answer";
    public static final String HINT="hint";
    public static final String HINT_IMAGE="hint_image";
    public static final String IS_SHOWN="is_shown";
//    public boolean[] isQuizShown;

    private static final String createQuery="CREATE TABLE IF NOT EXISTS "+ TABLE_NAME
            +"("
            +ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +QUIZ_LEVEL+" INTEGER " + "NOT NULL, "
            +QUIZ+" TEXT " + "NOT NULL, "
            +SELECTION_1+" TEXT, "
            +SELECTION_2+" TEXT, "
            +SELECTION_3+" TEXT, "
            +SELECTION_4+" TEXT, "
            +ANSWER+" INTEGER NOT NULL, "
            +IMAGE+" BLOB, "
            +HINT+" TEXT, "
            +HINT_IMAGE+" BLOB, "
            +IS_SHOWN+" INTEGER)";

    public static final int DATABASE_VERSION=1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createQuery);
//        isQuizShown=new boolean[getRowCount(db)];
//        for(int i=0;i<getRowCount(db);i++){
//            isQuizShown[i]=false;
//        }
        Log.d("TAG", "createQuery="+createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,     int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void updateFalseToTrue(SQLiteDatabase db, int id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(IS_SHOWN, 1);
        db.update(TABLE_NAME, contentValues, ID+"="+id, null);
//        db.execSQL("UPDATE "+TABLE_NAME+" SET "+IS_SHOWN+"=1 WHERE "+ID+"="+id);
//        Log.d("tag", "updateFalseToTrue: "+"UPDATE "+TABLE_NAME+" SET "+IS_SHOWN+"=1 WHERE "+ID+"="+id);
        Log.d("TAG", "updateFalseToTrue 호출");
    }

    public void updateTruetoFalse(SQLiteDatabase db){
        db.execSQL("UPDATE "+TABLE_NAME+" SET "+IS_SHOWN+"=0 WHERE "+IS_SHOWN+"=1");
        Log.d("TAG", "updateTrueToFalse 호출");
    }

    public Cursor LoadSQLiteDBCursor(int quiz_level){

        Log.d("TAG", "LoadSQLiteDBCursor_easy 호출");

        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();
        String selectQuery="SELECT "+ID+", "+QUIZ_LEVEL+", "+QUIZ+", "+SELECTION_1+", "+SELECTION_2+", "+SELECTION_3+", "+SELECTION_4
                +", "+ANSWER+", "+IMAGE+ ", "+HINT+", "+HINT_IMAGE+", "+IS_SHOWN+" FROM "+TABLE_NAME+
                " WHERE "+QUIZ_LEVEL+"="+quiz_level+" AND "+IS_SHOWN+ "=0 ORDER BY RANDOM() LIMIT 1";
//        String selectQuery="";
//        Random random = new Random();
        Cursor cursor=null;

        try{
            cursor=db.rawQuery(selectQuery, null);
            Log.d("TAG", "selectQuery="+selectQuery);
            db.setTransactionSuccessful();
        }catch(Exception e){
            Log.d("TAG", "DBHelper에서 Exception 발생");
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
        return cursor;
    }
/*
    public Cursor LoadSQLiteDBCursor_normal(){
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();
        String selectQuery="SELECT "+ID+", "+QUIZ_LEVEL+", "+QUIZ+", "+SELECTION_1+", "+SELECTION_2+", "+SELECTION_3+", "+SELECTION_4
                +", "+ANSWER+", "+IMAGE+ ", "+HINT+", "+HINT_IMAGE+", "+IS_SHOWN+" FROM "+TABLE_NAME+
                " WHERE "+QUIZ_LEVEL+"=2 AND "+IS_SHOWN+ "=0 ORDER BY RANDOM() LIMIT 1";
        Cursor cursor=null;

        try{
            cursor=db.rawQuery(selectQuery, null);
            Log.d("TAG", "selectQuery="+selectQuery);
            db.setTransactionSuccessful();
        }catch(Exception e){
            Log.d("tag", "LoadSQLiteDBCursor_normal() Exception 발생");
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
        return cursor;
    }

    public Cursor LoadSQLiteDBCursor_hard(){
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();
        String selectQuery="SELECT "+ID+", "+QUIZ_LEVEL+", "+QUIZ+", "+SELECTION_1+", "+SELECTION_2+", "+SELECTION_3+", "+SELECTION_4
                +", "+ANSWER+", "+IMAGE+ ", "+HINT+", "+HINT_IMAGE+", "+IS_SHOWN+" FROM "+TABLE_NAME+
                " WHERE "+QUIZ_LEVEL+"=3 AND "+IS_SHOWN+ "=0 ORDER BY RANDOM() LIMIT 1";
//        String selectQuery="SELECT "+ID+", "+QUIZ_LEVEL+", "+QUIZ+", "+SELECTION_1+", "+SELECTION_2+", "+SELECTION_3+", "+SELECTION_4
//                +", "+ANSWER+", "+IMAGE+ ", "+HINT+", "+HINT_IMAGE +" FROM "+TABLE_NAME+
//                " WHERE "+QUIZ_LEVEL+"=3 ORDER BY RANDOM() LIMIT 1";
        Cursor cursor=null;

        try{
            cursor=db.rawQuery(selectQuery, null);
            Log.d("TAG", "selectQuery="+selectQuery);
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
        return cursor;
    }*/

    public Cursor LoadSQLiteDBCursor_test(){
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();
        String selectQuery="SELECT "+ID+", "+QUIZ_LEVEL+", "+QUIZ+", "+SELECTION_1+", "+SELECTION_2+", "+SELECTION_3+", "+SELECTION_4
                +", "+ANSWER+", "+IMAGE+ ", "+HINT+", "+HINT_IMAGE+", "+IS_SHOWN+" FROM "+TABLE_NAME+
                " WHERE "+IS_SHOWN+ "=0 ORDER BY RANDOM() LIMIT 1";
        Cursor cursor=null;

        try{
            cursor=db.rawQuery(selectQuery, null);
            Log.d("TAG", "selectQuery="+selectQuery);
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
        return cursor;
    }

    public void dbDeleteAll(){
        SQLiteDatabase db = getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(TABLE_NAME, null, null);
    }

    public void dbDelete(SQLiteDatabase db, Long dbId){
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE "+ID+"="+dbId);
    }

    public void loadQuiz(SQLiteDatabase db, Context context){
        Log.d("TAG", "loadQuiz 호출");

//        db.beginTransaction();

        try{
//            db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES(null, '1', '다음 공룡의 이름은 무엇일까요?', '렉스', '디폴로도쿠스', '파라사우롤로푸스', '펄모노스콜피온', '3')");
//            db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES(null, '1', '다음 공룡의 이름은 무엇일까요?', 'Argentavis타비스', '기가노토파우르스', '파키리노사우루스', '파키', '4')");
//          공룡 이름 문제
            insertQuiz(db,1, "What is the name of this dinosaur?", "T-Rex", "Diplodocus", "Parasaurolophus", "Pachy", 3, R.drawable.ark_parasaur, "This dinosaur has the ability to detect enemies.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Griffin", "Parasaurolophus", "Ankylosaurus", "Quetzal", 3, R.drawable.ark_ankylosaurus, "This dinosaur digs metal well.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Ankylosaurus", "Baryonyx", "Beelzebufo", "Direwolf", 2, R.drawable.ark_baryonyx, "This dinosaur does not have an oxygen gauge.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Bulbdog", "T-Rex", "Woolly Rhino", "Unicorn", 3, R.drawable.woollyrhino, "This dinosaur can accumulate attack gauges.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Titanoboa", "Seeker", "Daeodon", "Giganotosaurus", 4, R.drawable.giganotosaurus, "Its attacking range is very wide.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Dung Beetle", "Oviraptor", "Pegomastax", "Allosaurus", 4, R.drawable.allosaurus, "This dinosaur has flock buff and bleeding damage.", NULL,0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Compy", "Morellatops", "Chalicotherium", "Gacha", 1, R.drawable.compy, "This dinosaur is small and has flock buffs.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Defense Unit", "Carnotaurus", "Magmasaur", "Kentrosaurus", 2, R.drawable.carnotaurus, "This dinosaur has short arms and two small horns on its head.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Spino", "Yeti", "Brontosaurus", "Yutyrannus", 3, R.drawable.brontosaurus, "This dinosaur is huge and attacks with its tail.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Dilophosaur", "Thorny Dragon", "Terror Bird", "Stegosaurus", 1, R.drawable.dilophosaur, "When this dinosaur spits, the vision is obscured.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Argentavis", "Triceratops", "Purlovia", "Kaprosuchus", 1, R.drawable.argentavis, "This dinosaur reduces weight in half.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Megalodon", "Direwolf", "gacha", "Phiomia", 4, R.drawable.phiomoa, "This dinosaur is used a lot when collecting poop.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Pulmonoscorpius", "Morellatops", "Doedicurus", "Iguanodon", 1, R.drawable.pulmonoscorpius, "If it hits you, your paralysis figure will increase.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Bulbdog", "Diplidicus", "Onyc", "Pachyrhinosaurus", 3, R.drawable.onyc, "This dinosaur lives in caves and has a chance of causing rabies.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Spino", "Meganeura", "Baryonyx", "Leech", 1, R.drawable.spino, "This dinosaur gets a water buff when it touches the water.", NULL, 0, context);
            insertQuiz(db,1, "How many times can Managarmr jump in the air?", "1 Time", "2 Time", "3 Time", "4 Time", 3, R.drawable.managarma, "jujujump...", NULL, 0, context);
            insertQuiz(db,1, "How many dashes can Managarmr use?", "1 Time", "2 Time", "3 Time", "4 Time", 1, R.drawable.managarma, "managarmr is not a bird", NULL, 0, context);


//          자원문제
            insertQuiz(db,1, "Which is not collected by Cartoroides?", "Wood", "Silica Pearls", "Rare Mushroom", "Straw", 4, R.drawable.castoroides, "It is yellow", NULL, 0, context);
            insertQuiz(db,1, "Which is not collected by killing trilobites?", "Black Pearl", "Silica Pearls", "Oil", "Leather", 4, R.drawable.trilobite, "Remember that it is a'crustacean'.", NULL, 0, context);
            insertQuiz(db,1, "Which is not collected by killing Ovis?", "Lamb", "Raw meat", "Leather", "Fur", 2, R.drawable.ovis, "'Ovis' is'sheep'.", NULL, 0, context);

            insertQuiz(db,1, "Which is not collected by killing water bugs?", "Chitin", "Raw meat", "Cementing Paste", "silks", 4, R.drawable.jugbug, "We can't make clothes with this resource.", NULL, 0, context);

//          음식문제
            insertQuiz(db,1, "Which of these is not a berry?", "Amarberry", "Azulberry", "Tintoberry", "Pozoberry", 4, R.drawable.ark_logo, "There is no such berry.", NULL, 0, context);

            //부패시간 문제
            insertQuiz(db,1, "How much time does it take for a berry to rot?", "5 mins", "10 mins", "15 mins", "20 mins", 2, R.drawable.berries, "Korean students have this much breaktime in school.", NULL, 0, context);
            insertQuiz(db,1, "How much time does it take for a grain to decay?", "5 mins", "10 mins", "15 mins", "20 mins", 1, R.drawable.citronal, "Divide a day in half and half and half and half and half and then divde it into 9. ", NULL, 0, context);
            insertQuiz(db,1, "WHow much time does it take for a raw meat to decay?", "5 mins", "10 mins", "15 mins", "20 mins", 2, R.drawable.raw_meat, "Korean students have this much breaktime in school.", NULL, 0, context);
            insertQuiz(db,1, "How much time does it take for a item box of meat to decay?", "5 hours 10 mins", "5 hours 40 mins", "6 hours 10 mins", "6 hours 40 mins", 4, R.drawable.raw_meat, "Multiply one corruption time by 40!", NULL, 0, context);
            insertQuiz(db,1, "How much time does it take for a raw prime meat to decay?", "2 mins", "2 mins 10 sec", "2 mins 20 sec", "2 mins 30 sec", 3, R.drawable.raw_prime_meat, "'140'", NULL, 0, context);
            insertQuiz(db,1, "How much time does it take for a spoiled meat to decay?", "5 mins", "10 mins", "15 mins", "20 mins", 3, R.drawable.spoiled_meat, "Between decay time of a raw meat and a decay time of grilled meat.", NULL, 0, context);

            //한 칸 문제
            insertQuiz(db,1, "How many raw meats can you have in an item square?"/*한 칸에 들 수 있는 생고기의 양은?*/, "10", "20", "30", "40", 4, R.drawable.raw_meat, "If you get 10000 meat, 250 squares are filled.", NULL, 0, context);
            insertQuiz(db,1, "How many raw prime meats can you get in an item square?"/*한 칸에 들 수 있는 고품질 생고기의 양은?*/, "1", "2", "3", "4", 1, R.drawable.raw_prime_meat, "If you get 300 high quality raw meat, you can fill 300 squares.", NULL, 0, context);
            insertQuiz(db,1, "How much cooked prime meats can be in an item square?", "10", "20", "30", "40", 3, R.drawable.cooked_prime_meat, "raw prime meat X 30", NULL, 0, context);
            insertQuiz(db,1, "How many bottles of wyvern milk can you have in an item square?", "1", "2", "3", "4", 4, R.drawable.wyvern_milk, "Same as raw prime meat.", NULL, 0, context);

            //제작 문제
            insertQuiz(db,1, "What is not used when making gunpowder?", "Flint", "Stone", "Thatch", "Charcoal", 3, R.drawable.gunpowder, "It is light yellow.", NULL, 0, context);
            insertQuiz(db,3, "How many elements do you need to make a raw tek rifle?", "10", "20", "30", "40", 2, R.drawable.tek_rifle, "It takes as much as the elements that come out when you catch gamma Megapithecus.", NULL, 0, context);
            //데미지
            insertQuiz(db,1, "What is a damage of a shot of bow?", "50", "55", "60", "65", 2, R.drawable.bow, "If you increase your hp once and then shot by a bow, your hp becomes half.", NULL, 0, context);
            //자원문제
            insertQuiz(db,1, "What can you mostly get when you dig a stone with a pick?", "Flint", "Stone", "Metal", "Stone powder", 2, R.drawable.metal_pick, "It's best to pick yellow and triangular things.", NULL, 0, context);
            insertQuiz(db,1, "Which is not obtained by using a metal sickle?", "Fiber", "Raw prime fish meat", "Flint", "Thatch", 4, R.drawable.metal_sickle, "We can't attack trees with a metal sickle.", NULL, 0, context);
            insertQuiz(db,1, "Which is hardly got when using a hatchet?", "Leech blood", "Leather", "AnglerGel", "Stone", 4, R.drawable.metal_hatchet, "This resource is hard.", NULL, 0, context);
            insertQuiz(db,1, "Which is not an ingredient of a paint brush?", "Wood", "Leather", "Fiber", "Thatch", 3, R.drawable.paintbrush, "This comes out when you dig the grass.", NULL, 0, context);
            insertQuiz(db,1, "What is not an ingredient of parachute?", "Thatch", "Leather", "Wood", "Fiber", 4, R.drawable.parachute, "This can be got in grass.", NULL, 0, context);
            insertQuiz(db,1, "Where can we make torchlight?", "Personal inventory", "Making machine", "Smithy", "Mortar and Pestle", 1, R.drawable.torch, "User whose level is 1 can make torch.", NULL, 0, context);
            insertQuiz(db,1, "What level can you learn a small telescope?", "7", "10", "13", "15", 1, R.drawable.spyglass, "Newbies can easily learn it.", NULL, 0, context);

            //오벨리스크
            insertQuiz(db,1, "What is the coordinates of the Island Blue Obelisk?", "13.1, 55.3", "35.4, 64.3", "25.5, 25.6", "39.2, 68.7", 3, R.drawable.obelisk, "It's in the upper left.", NULL, 0, context);
            insertQuiz(db,1, "What is the coordinates of the Island Red Obelisk?", "79.8, 17.4", "75.2, 13.8", "49.1, 82.0", "24.9, 86.4", 1, R.drawable.obelisk, "It's in the bottom left corner.", NULL, 0, context);
            insertQuiz(db,1, "What is the coordinates of the Island Green Obelisk?", "14.8, 76.0", "59.0, 72.3", "19.4, 28.6", "16.8, 71.3", 2, R.drawable.obelisk, "It's in the middle right.", NULL, 0, context);



//          그림 힌트








//            insertQuiz(db,1, "다음 중 브루드마더의 유물이 아닌 것은?", "현명함의 유물", "거대함의 유물", "사냥꾼의 유물", "하늘군주의 유물", 2, R.drawable.ark_logo, R.drawable.skylord, context);  --> 힌트를 사진으로 주기
//            insertQuiz(db,1, "다음 공룡의 소리는 누구일까요?", "Ankylosaurus", "펄로비아", "바위골렘", "샤이니혼", 2, R.drawable.ankylosaurus, "이 공룡의 이름은 '도'로 시작합니다", context);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
//            db.endTransaction();
        }
    }

    public void loadQuiz2(SQLiteDatabase db, Context context){
        try{
            insertQuiz(db, 2, "Which dinosaur is the best for getting metal?", "Ankylosaurus", "Megaloceros", "dodo", "Mosasaurus", 1, R.drawable.ark_metal, "This dinosaur looks hard.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is the best for getting straw?", "Pteranodon", "Ovis", "Allosaurus", "Megaloceros", 4, R.drawable.ark_thatch, "This may not be a dinosaur.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is the best to getting wood?", "Pachy", "Parasaur", "Mammoth", "Sabertooth", 3, R.drawable.ark_wood, "This is large in size.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is best for getting stone?", "Doedicurus", "Triceratops", "Compy", "Quetzal", 1, R.drawable.ark_stone, "This dinosaur looks hard.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is best for getting berry?", "Purlovia", "Brontosaurus", "T-rex", "parasaur", 2, R.drawable.ark_berries, "This dinosaur has a really long neck.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is best for getting hide?", "Therizinosaur", "Trilobite", "Quetzal", "Gigantopithecus", 1, R.drawable.ark_hide, "This dinosaur has long nails.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is best for getting flint?", "Dierwolf", "Ankylosaurus", "Raptor", "Manta", 2, R.drawable.ark_hide, "This dinosaur looks hard.", NULL, 0, context);
            insertQuiz(db, 2, "What is the best tool for getting metal", "Metal Hatchet", "Metal pick", "Metal sickle", "Sword", 2, R.drawable.ark_metal, "Think of the real use of the tools", NULL, 0, context);
            insertQuiz(db, 2, "Which is the best tool for getting stones?", "Stone Hatchet", "Metal Hatchet", "Stone Pick", "Metal Pick", 2, R.drawable.ark_stone, "Hatchet are better at picking stones than pickaxes.", NULL, 0, context);
            insertQuiz(db, 2, "Which tool is best for getting flint?", "Stone Hatchet", "Metal Hatchet", "Stone Pick", "Metal Pick", 4, R.drawable.ark_stone, "A pickaxe is more efficient at picking flint than an Hatchet.", NULL, 0, context);
            insertQuiz(db, 2, "What is the best tool for getting wood?", "Stone Hatchet", "Metal Hatchet", "Stone Pick", "Metal Pick", 2, R.drawable.ark_wood, "It is more efficient to use hatchet rather than pickaxe when getting wood.", NULL, 0, context);
            insertQuiz(db, 2, "Which of the following is the best tool for picking straw?", "Stone Hatchet", "Metal Hatchet", "Stone Pick", "Metal Pick", 4, R.drawable.ark_thatch, "It is more efficient to use pickaxe rather than hatchet when getting thatch.", NULL, 0, context);
            insertQuiz(db, 2, "Which of the following is the best tool for getting fiber?", "Metal sickle", "Whip", "Metal Hatchet", "Metal Pick", 1, R.drawable.ark_fiber, "This tool is used to cut something.", NULL, 0, context);
            insertQuiz(db, 2, "What is the best tool for minsing leather?", "Metal Hatchet", "Metal Pick", "Sword", "Metal sickle", 1, R.drawable.ark_hide, "This tool is a good tool for getting wood.", NULL, 0, context);
            insertQuiz(db, 2, "Which of the following is the best tool for getting raw meat?", "Metal Hatchet", "Metal Pick", "Sword", "Metal sickle", 2, R.drawable.raw_meat, "This tool is a good tool for getting metal.", NULL, 0, context);
            insertQuiz(db, 3, "Which of the following is not a dinosaur that reduces the weight of stones?", "Castoroides", "Equus", "Doedicurus", "Morellatops", 4, R.drawable.ark_stone, "This dinosaur is often seen in the desert.", NULL, 0, context);
            insertQuiz(db, 3, "Which of the following is not a dinosaur that reduces the weight of wood?", "Gacha", "Mammoth", "Equus", "Thorny Dragon", 3, R.drawable.ark_wood, "This dinosaur is often seen on large plains.", NULL, 0, context);
            insertQuiz(db, 3, "Which of the following is not a dinosaur that reduces the weight of iron?", "Magmasaur", "Wyvern", "Argentavis", "Ankylosaurus", 2, R.drawable.ark_metal, "This dinosaur can shoot breath.", NULL, 0, context);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadQuiz3(SQLiteDatabase db, Context context){
        try{
            //          유물 문제
            insertQuiz(db,3, "Where is the relic of Irish Clever? \n (There may be individual errors)", "41.1,48.8", "80.0, 53.6", "50.0, 52.3", "71.4, 86.4", 1, R.drawable.artifact_of_the_clever, "It is near a Resource mountain.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Hunter? \n (There may be individual errors)", "68.7, 56.5", "41.1, 53.6", "80.0, 53.6", "22.2, 88.9", 3, R.drawable.artifact_of_the_hunter, "It is in the south.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Cunning? \n (There may be individual errors)", "35.2,48.8", "74.3, 36.6", "45.3, 91.9", "32.4, 14.2", 3, R.drawable.artifact_of_the_cunning, "Between the island of death and the herbivorous island.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Strong? \n (There may be individual errors)", "29.5,32.1", "87.2, 52.3", "29.3, 60.2", "10.3, 55.6", 1, R.drawable.artifact_of_the_strong, "The entrance is small, but inside is in a large cave.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Devourer? \n (There may be individual errors)", "14.3, 78.5", "14.6, 85.6", "98.1, 33.1", "41.1, 65.6", 2, R.drawable.artifact_of_the_devourer, "It is called Carno Caves.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Massive? \n (There may be individual errors)", "46.3, 85.3", "90.2, 8.1", "78.2, 14.8", "71.4, 86.4", 4, R.drawable.artifact_of_the_massive, "It is near herbivorous island.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Pack? \n (There may be individual errors)", "32.2, 88.1", "8.1, 82.1", "59.2, 61.3", "68.7, 56.5", 4, R.drawable.artifact_of_the_pack, "It's in the water and it's between the Red Obel and Snow Mountain.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Brutal? \n (There may be individual errors)", "34.3, 11.2", "61.2, 32.4", "82.1, 35.7", "52.4, 10.3", 4, R.drawable.artifact_of_the_brute, "Between Redwood and SW2 Resurrection Zone.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Immune? \n (There may be individual errors)", "95.4, 14.4", "96.2, 14.6", "62.7, 37.1", "13.8, 49.7", 3, R.drawable.artifact_of_the_immune, "It is called a poison cave.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Skylord? \n (There may be individual errors)", "91.6,81.7", "19.1, 19.0", "23.5,71.9", "15.2,38.9", 2, R.drawable.artifact_of_the_skylord, "It is near Penguin Island.", NULL, 0, context);
            //insertQuiz(db,1, "다음중 브루드마더의 유물이 아닌 것은?", "현명함의 유물", "거대함의 유물", "사냥꾼의 유물", "하늘군주의 유물", 4, R.drawable.ark_logo, R.drawable.skylord, NULL, 0, context);

            //          보스 공물 문제
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute of the boss.", "Argentavis", "Sarco", "Megalania", "Procoptodon", 4, R.drawable.ark_logo, "This dinosaur jumps high.", NULL, 0, context);
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute of the boss.", "Carnotaurus", "Kairuku", "Giganotosaurus", "Megalodon", 2, R.drawable.ark_logo, "This dinosaur gives an organic polymer.", NULL, 0, context);
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute of the boss.", "Basilosaurus", "Thylacoleo", "Allosaurus", "Megatherium", 4, R.drawable.ark_logo, "This dinosaur gets a buff when it kills insects.", NULL, 0, context);
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute of the boss.", "Brontosaurus", "Spino", "Raptor", "Diplodocus", 3, R.drawable.ark_logo, "This dinosaur has two-legged jumping and catching skills..", NULL, 0, context);
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute of the boss.", "T-rex", "Titanoboa", "Woolly Rhino", "Ravager", 4, R.drawable.ark_logo, "This dinosaur is a quadruped and reduces weight.", NULL, 0, context);
//          알파공룡 문제
            insertQuiz(db,3, "Which of the following is not an alpha dinosaur?", "Alpha Leedsichthys", "Alpha Raptor", "Alpha Reaper king", "Alpha Carnotaurus", 4, R.drawable.ark_logo, "This dinosaur collects Cementing Paste, Wood, rare flowers, rare mushrooms, and silica pearhyojuls.", NULL, 0, context);
            insertQuiz(db,3, "Which of the following is not an alpha dinosaur?", "Alpha Megalodon", "Alpha Mosasaur", "Alpha Megalania", "Alpha Tusoteuthis", 3, R.drawable.ark_logo, "There are no dinosaurs like this. Mainly living in the ceiling in the cave and there are many in Ragnarok", NULL, 0, context);

            insertQuiz(db,2, "Which is not obtained by killing Managarmr?", "Chitin", "Keratin", "Hide", "Raw prime Meat", 1, R.drawable.managarma, "Managamar is not an insect.", NULL, 0, context);
            insertQuiz(db,2, "How many bottles of milk you can get when you stun Wyvern?", "4", "5", "6", "7", 2, R.drawable.wyvern_milk, "Less than a line.", NULL, 0, context);
//          보스 문제
            insertQuiz(db,2, "How many elements does gamma megapithecus catch?", "10", "20", "30", "40", 2, R.drawable.boss_megapithecus, "This question may be difficult. Maybe '2' people can solve it.", NULL, 0, context);
            //insertQuiz(db,1, "베타 메가피테쿠스를 잡으면 주는 원소의 양은?", "10", "20", "30", "40", 2, R.drawable.boss_megapithecus, "감마랑 똑같습니다.", NULL, 0, context);
            insertQuiz(db,2, "What is Gamma Rockwell's level limit?", "Lv50", "Lv60", "Lv70", "Lv80", 2, R.drawable.rockwell, "When you reach this level, you can learn Basilosaurus Saddle.", NULL, 0, context);
            insertQuiz(db,2, "What is beta Rockwell's level limit?", "Lv75", "Lv75", "Lv85", "Lv95", 2, R.drawable.rockwell, "When you reach this level, you can learn the vault.", NULL, 0, context);
            insertQuiz(db,3, "What is Alpha Rockwell's level limit?", "Lv70", "Lv80", "Lv90", "Lv100", 4, R.drawable.rockwell, "When you reach this level, you can learn the titanosaurus platform saddle.", NULL, 0, context);
            insertQuiz(db,2, "What's not Manticore's boss tribute?", "Ice Talon", "Fire Talon", "Poison Talon", "Lightning Talon", 1, R.drawable.manticore, "There are no cold claws.", NULL, 0, context);
            insertQuiz(db,2, "What's not the tek engram you get when you catch Gamma Manticore?", "Tek Gauntlets", "Tek Leggings", "Tek Ganerator", "Tek Helmet", 4, R.drawable.tek_replicator, "First body...", NULL, 0, context);
//          키블 문제
            insertQuiz(db,2, "Which is not an ingredient of Basic kibble?", "Dilophosaur Egg ", "Amarberry", "Pteranodon Egg", "Thatch", 3, R.drawable.basic_kibble, "Use only the smallest eggs.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Simple kibble?", "Raptor Egg", "Cooked meat", "Carrot", "Mejoberry", 2, R.drawable.simple_kibble, "It should be made of fish.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Regular kibble?", "Dilophosaur Egg", "Ichthyornis Egg", "Potato", "Prime Meat jerky", 4, R.drawable.regular_kibble, "It takes the longest to get.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Superior kibble?", "Prime Meat jerky", "Citronal", "Thatch", "Rare Flower", 4, R.drawable.superior_kibble, "Often seen near Crystal, Beaver Dam and Ragnarok Bluebell.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Exceptional kibble?", "Focal chili", "Fria Curry", "Thatch", "Mejoberry", 2, R.drawable.exceptional_kibble, "It is brown.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of ExtraOrdinary kibble?", "Giant Bee Honey", "Wyvern Egg", "Lazarus chowder", "Energy Brew", 4, R.drawable.extraordinary_kibble, "Hill here...?", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Brew?", "Tintoberry", "Mejoberry", "Narcotic", "Water", 2,R.drawable.medical_brew, "Herbivorous dinosaurs are my favorite.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Enduro Stew?", "Water", "Mejoberry × 10", "Carrot × 5", "Stimulant × 1", 4, R.drawable.rockwell_recipes_enduro_stew, "+stimulant.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of a focal chili?", "Mejoberry × 10", "Citronal 5", "Cooked meat", "Prime Cooked meat", 4, R.drawable.rockwell_recipes_focal_chili, "There is no need to use prime..", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Nazarus chowder?", "Mejoberry × 10", "Corn × 5", "Stimulant × 2", "Potato × 5", 3, R.drawable.lazarus_chowder, "It's not stimberry or spark powder, but nacoberry and spoiled meat.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Calien Soup?", "Citronal × 5", "Amarberry × 20", "Tintoberry × 10", "Stimulant × 2", 3, R.drawable.calien_soup, "10 is not enough..", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of cold curry?", "Corn × 5", "Carrot × 5", "Azulberry × 20", "Mejoberry × 20", 4, R.drawable.fria_curry, "10 is enough for the berry that dinosaurs love..", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Shadow Steak Saute?", "Narcotic × 10", "Rare Mushroom × 2", "Potato × 1", "Carrot × 1", 1, R.drawable.ark_logo, "8  spoiled meat + 40 Nacoberries are enough.", NULL, 0, context);
            insertQuiz(db,2, "Roasting time of one grilled meat?", "5 mins", "10 mins", "15 mins", "20 mins", 4, R.drawable.cooked_meat, "It's twice the time of raw meat decayed time..", NULL, 0, context);
            insertQuiz(db,2, "How much time does it spend for a meat jerky to decay?", "12 hours", "24 hours", "36 hours", "48 hours", 4, R.drawable.cooked_meat_jerky, "Beef jerky has a longer shelf life than expected.", NULL, 0, context);
            insertQuiz(db,2, "How much time does it spend for a raw prime meat to decay??", "40 mins", "42 mins", "44 mins", "46 mins", 4, R.drawable.cooked_prime_meat, "'2760'", NULL, 0, context);
            insertQuiz(db,2, "How much time does it spend for a prime meat jerky to decay?", "12 hours", "24 hours", "36 hours", "48 hours", 4, R.drawable.prime_meat_jerky, "Same as meat jerky decayed time..", NULL, 0, context);
            insertQuiz(db,2, "How many jerkies can be stored in an item square?", "10", "20", "30", "40", 2, R.drawable.cooked_meat_jerky, "Half of raw meat.", NULL, 0, context);
            insertQuiz(db,2, "Which can't be made in the making machine?", "Rocket Launcher", "Metal Cliff Platform", "Polymer", "Generator", 2, R.drawable.fabricator, "This is an engram that you can learn from Aberration.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of scuba hydroponic?", "hide", "Thatch", "Crystal", "Polymer", 4, R.drawable.scuba_mask, "A lot comes from penguins and mantis.", NULL, 0, context);
            insertQuiz(db,3, "How many c4 are used to break the metal door?", "1", "2", "3", "4", 2, R.drawable.c4_charge, "A single c4 damage to metal is 3544.", NULL, 0, context);
            insertQuiz(db,2, "How many grenades are uesed to break the stone foundation?", "9", "10", "11", "12", 1, R.drawable.grenade, "Grenade damage into stone is 1838.", NULL, 0, context);
            insertQuiz(db,2, "How many cannons are used to break the tek ceiling?", "250", "300", "350", "400", 1, R.drawable.cannon, "One cannon damage that enters the tek ceiling is 40.", NULL, 0, context);
            insertQuiz(db,2, "What is the damage of a bullet in an automatic turret?", "70", "80", "90", "100", 1, R.drawable.auto_turret, "Although you increase your hp 5 times, you can die by an auto turret shot.", NULL, 0, context);
            insertQuiz(db,3, "How much damage does C4 take on wood?", "11320", "11813", "12591", "12677", 2, R.drawable.c4_charge, "There is no way to give a hint. The answer is 11813.", NULL, 0, context);
            insertQuiz(db,2, "How much torpor rate rises when you shoot tranquilizer dart?", "220", "221", "222", "223", 2, R.drawable.tranquilizer_dart, "It is 33 shots based on paralysis number 7293..", NULL, 0, context);
            insertQuiz(db,2, "How much torpor rate rises when you shoot shocking tranquilizer dart?", "440", "442", "444", "446", 2, R.drawable.shocking_tranquilizer_dart, "It's twice as much as Paralysis dart.", NULL, 0, context);
            insertQuiz(db,2, "Which is the best bait for fishing?", "Giant Bee Honey", "Leech Blood", "sap", "Raw meat", 2, R.drawable.fishing_rod, "I like the sweetest thing.", NULL, 0, context);
            insertQuiz(db,3, "Which is not an ingredient of the tek claw?", "Element", "Black Pearl", "Polymer", "Hide", 4, R.drawable.tek_claws, "can't use a tough one.", NULL, 0, context);
            insertQuiz(db,3, "Which is not an ingredient of a tek maker?", "Metal Ingot 5000", "Element 110", "Black Pearl 150", "Crystal 600", 2, R.drawable.tek_replicator, "There are many squares.", NULL, 0, context);
            insertQuiz(db,3, "What is not the ThsIsland OVis spawn area?", "20.0, 30.0", "20.0, 50,0", "85.0, 83.0", "75.0, 65.0", 3, R.drawable.ovis, null, R.drawable.theisland_ovis_spawn, 0, context);
            insertQuiz(db,3, "What is the limit on the number of turrets in a zone?", "50", "100", "150", "200", 2, R.drawable.heavy_auto_turret, "You will be happy when you get this much score in the exam.", NULL, 0, context);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadQuiz4(SQLiteDatabase db, Context context){

        insertQuiz(db,1, "Which berry do dinosaurs prefer most?", "Amarberry", "Tintoberry", "Mejoberry", "Azulberry", 3, R.drawable.berries, "It's purple.", NULL, 0, context);

        //<공룡 크기 문제>
        insertQuiz(db,1, "Which dinosaur can't be shut in by dino gate?", "Giganotosaurus", "Brontosaurus", "Argentavis", "Pteranodon", 4, R.drawable.stonedinosaurgateway, "It can fly and has a long beak.", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur can't be shut in by dino gate?", "Managarma", "Paraceratherium", "Gasbags", "Diplodocus", 3, R.drawable.stonedinosaurgateway, "It can fill its body with gas.", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur can't be shut in by dino gate?", "Tusotheuthis", "Griffin", "Dunkleosteus", "Mosasaurus", 1, R.drawable.stonedinosaurgateway, "It has many legs.", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur can't be shut in by dino gate?", "Rex", "Spinosaurus", "Allosaurus", "Carnotaurus", 4, R.drawable.stonedinosaurgateway, "It has short arms and has two small horns.", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur can't be shut in by dino gate?", "Yutyrannus", "Baryonyx", "Paraceratherium", "Rock Elemental", 2, R.drawable.stonedinosaurgateway, "It can stun other creatures in water.", NULL, 0, context);

        //<테이밍 문제>
        insertQuiz(db,3, "(Assuming Taming speed is 1) How many minutes will be spent when taming LV150 Beelzebufo with lamb?", "10 mins", "11 mins", "12 mins", "13 mins", 3, R.drawable.beelzebufo, "It is a multiple of 4.", NULL, 0, context);
        insertQuiz(db,2, "(Assuming Taming speed is 1) How many elements will be needed when taming LV150 Ferox?", "10", "11", "12", "13", 1, R.drawable.ferox, "It is a multiple of 5.", NULL, 0, context);
        insertQuiz(db,3, "How many cannon balls are needed when taming a Titanosaur?", "205", "206", "207", "208", 2, R.drawable.titanosaur, "2x2x2x2x2x2x2x2-50", NULL, 0, context);
        insertQuiz(db,1, "(Assuming Taming speed is 1)How many tranq arrows are needed when taming LV150 Parasaur?(Using a bow)", "15", "16", "17", "18", 3, R.drawable.ark_parasaur, "It's a prime number.", NULL, 0, context);
        insertQuiz(db,1, "(Assuming Taming speed is 1)How many tranq arrows are needed when taming LV150 Raptor?(Using a bow)", "20", "21", "22", "23", 2, R.drawable.raptor, "It is a multiple of 7.", NULL, 0, context);
        insertQuiz(db,1, "(Assuming Taming speed is 1)How many Sabertooth Salmon(1.5X) are needed when taming LV150 Otter?", "1", "2", "3", "4", 2, R.drawable.otter, "LV75 otter needs one Sabertooth Salom(1.5X)", NULL, 0, context);
        insertQuiz(db,1, "(Assuming Taming speed is 1)How many spoiled meats are needed when taming LV150 Vulture?", "10", "11", "12", "13", 3, R.drawable.vulture, "It is a multiple of 4.", NULL, 0, context);
        insertQuiz(db,1, "(Assuming Taming speed is 1)How many rare flowers are needed when taming LV1 Giant Bee?", "1", "3", "5", "7", 2, R.drawable.giantbee, "How many Rare Flowers you can get at once? Minus one from it.", NULL, 0, context);
        insertQuiz(db,1, "(Assuming Taming speed is 1)How many Ascerbic Mushrooms are needed when taming LV150 Glowtail?", "25", "30", "35", "40", 1, R.drawable.glowtail, "150/6", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is best when getting organic polymer?", "Direbear", "Moschops", "Direwolf", "Dimetrodon", 1, R.drawable.organicpolymer, "This dinosaur is also good at getting flowers.", NULL, 0, context);
        insertQuiz(db,2, "Your Procoptodon gets 300 square boxes of berries. In order to move, how much weight stat should your Procoptodon be?", "at least 1000", "at least 2000", "at least 3000", "at least 4000", 4, R.drawable.procoptodon, "Choose the highest.", NULL, 0, context);
        insertQuiz(db,2, "Where can you make Enforcer?", "Fabricator", "Smithy", "Tek Replicator", "Chemistry Bench", 3, R.drawable.enforcer, "If you clear the boss stage, you can ues it.", NULL, 0, context);
        insertQuiz(db,2, "What can't we get when killing a Deathworm?", "Organic Polymer", "Deathwrom Horn", "AnglerGel", "Chitin", 4, R.drawable.deathworm, "Well..Deathworm may not be an insect~", NULL, 0, context);

        //<기절 떨어지는 속도 문제>
        insertQuiz(db,1, "(According to DodoDex) How fast does Snow Owl's torpor rate decrease?", "Low", "Average", "Fast", "Very Fast", 1, R.drawable.snowowl, "Knoooooooooooooooooock Out", NULL, 0, context);
        insertQuiz(db,1, "(According to DodoDex) How fast does Gasbags' torpor rate decrease?", "Low", "Average", "Fast", "Very Fast", 1, R.drawable.gasbags, "Knoooooooooooooooooock Out", NULL, 0, context);
        insertQuiz(db,2, "(According to DodoDex) How fast does Giganotosaurus' torpor rate decrease?", "Low", "Average", "Fast", "Very Fast", 4, R.drawable.giganotosaurus, "As fast as a rocket.", NULL, 0, context);
        insertQuiz(db,2, "(According to DodoDex) How fast does Doedicurus' torpor rate decrease?", "Low", "Average", "Fast", "Very Fast", 3, R.drawable.doedicurus, "As fast as a sport car.", NULL, 0, context);
        insertQuiz(db,2, "(According to DodoDex) How fast does Ankylosaurus' torpor rate decrease?", "Low", "Average", "Fast", "Very Fast", 1, R.drawable.ankylosaurus, "Knoooooooooooooooooock Out", NULL, 0, context);
        insertQuiz(db,2, "(According to DodoDex) How fast does Spinosaurus' torpor rate decrease?", "Low", "Average", "Fast", "Very Fast", 3, R.drawable.spino, "As fast as a sport car.", NULL, 0, context);
        insertQuiz(db,2, "(According to DodoDex) How fast does Raptor's torpor rate decrease?", "Low", "Average", "Fast", "Very Fast", 1, R.drawable.raptor, "Knoooooooooooooooooock Out", NULL, 0, context);

        //<색깔 문제>
        insertQuiz(db,1, "What is the color of Mejoberry?", "White", "Red", "Green", "Purple", 4, R.drawable.berries, "The last color of rainbow.", NULL, 0, context);
        insertQuiz(db,1, "What is the color of Amarberry?", "Yellow", "Black", "Blue", "Orange", 1, R.drawable.berries, "The third color of rainbow.", NULL, 0, context);
        insertQuiz(db,1, "What is the color of Azulberry?", "Yellow", "Brown", "Blue", "Pink", 3, R.drawable.berries, "Similar color with sky.", NULL, 0, context);
        insertQuiz(db,1, "What is the color of Nacoberry?", "Red", "Black", "Gray", "White", 2, R.drawable.berries, "The color of night.", NULL, 0, context);
        insertQuiz(db,1, "What is the color of Stimberry?", "White", "Yellow", "Red", "Black", 1, R.drawable.berries, "The color of Snow.", NULL, 0, context);
        insertQuiz(db,1, "What is the color of Tintoberry?", "Yellow", "Red", "Pink", "Purple", 2, R.drawable.berries, "The color of blood.", NULL, 0, context);

        //<채집 효율>
        insertQuiz(db,2, "Which is best when gathering raw meat?", "Metal Pick", "Rex", "Mosasaurus", "Giganotosaurus", 4, R.drawable.raw_meat, "The most powerful dinosaur in ARK World.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosuar is best when gathering black pearl?", "Pheonix", "Otter", "Gacha", "Angler", 3, R.drawable.black_pearl, "You can easily get black pearl at home using this dinosaur.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosuar is best when gathering cementing paste?", "Achatina", "Beelzebufo", "Giant Beaver Dam", "Meganeura", 2, R.drawable.cementing_paste, "croak..croak...", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering chitin?", "Megatherium", "Metal Pick", "Sabertooth", "Mantis", 1, R.drawable.chitin, "It gets buff when killing insects,", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering crystal?", "Metal Pick", "Mantis", "Ankylosaurus", "Gacha", 1, R.drawable.crystal, "Human", NULL, 0, context);
        insertQuiz(db,1, "Which is best when gathering fiber?", "Moschops", "Metal Sickle", "Gigantopithecus", "Direbear", 2, R.drawable.ark_fiber, "Human", NULL, 0, context);
        insertQuiz(db,1, "Which is best when gathering flint?", "Metal Pick", "Ankylosaurus", "Mantis", "Rock Elemental", 1, R.drawable.ark_flint, "Human", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering hide?", "Chainsaw", "Giganotosaurs", "Direwolf", "Thylacoleo", 1, R.drawable.ark_hide, "Dinosaur < Human", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering metal?", "Diplodocus", "Mantis", "Pheonix", "Magmasaur", 4, R.drawable.ark_metal, "We can see this dinosaur in Genesis?", NULL, 0, context);
        insertQuiz(db,1, "Which is best when gathering obsidian?", "Mantis", "Doedicurus", "Gacha", "Ankylosaurus", 2, R.drawable.obsidian, "It can hide inside its shell.", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering oil?", "Dunkleosteus", "Tusotheuthis", "Dong Beetle", "Gaca", 4, R.drawable.oil, "You can easily get oil at home using this dinosaur.", NULL, 0, context);
        insertQuiz(db,1, "Which is best when gathering organic polymer?", "Wooden Club", "ChainSaw", "Gacha", "Pelagornis", 1, R.drawable.organicpolymer, "If you attack someone with this, someone's torpor rate goes up.", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering rare flower?", "Therizinosaurus", "Moschops", "Managarma", "Megachelon", 4, R.drawable.rare_flower, "turtle...", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering rare mushroom?", "Therizinosaurus", "Moschops", "Megachelon", "Iguanodon", 3, R.drawable.rare_mushroom, "turtle...", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering raw fish meat?", "Metal Hatchet", "Mosasaurus", "Spinosaurus", "Baryonyx", 1, R.drawable.raw_fish_meat, "Humans are better than dinosaurs.", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering raw prime fish meat?", "Mosasaurus", "Metal Sickle", "Megachelon", "Spinosaurus", 1, R.drawable.raw_prime_fish_meat, "It has a long neck.", NULL, 0, context);
        insertQuiz(db,3, "Which is best when gathering raw prime meat", "Riper King", "Rex", "Wyvern", "Crystal Wyvern", 1, R.drawable.raw_prime_meat, "It can breathe under the ground.", NULL, 0, context);
        insertQuiz(db,1, "Which is best when gathering pearls?", "Gacha", "Otter", "Pheonix", "Angler", 4, R.drawable.silica_pearls, "It is a very ugly fish.", NULL, 0, context);
        insertQuiz(db,1, "Which is best when gathering stone?", "Rock Elemental", "Mantis", "Doedicurus", "Magamasaurus", 3, R.drawable.ark_stone, "It has a chunk on its tail.", NULL, 0, context);
        insertQuiz(db,1, "Which is best when gathering thatch?", "Brontosaurus", "MMegaloceros", "Sabertooth", "Megatherium", 1, R.drawable.ark_thatch, "The largest among the choices.", NULL, 0, context);
        insertQuiz(db,1, "Which is best when gathering wood?", "Therizinosaurus", "Roll Rat", "Thorny Dragon", "Pachyrhinosaurus", 1, R.drawable.ark_wood, "It has long nails.", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering sulfur?", "Mantis", "Pheonix", "Rock Elemental", "Ankylosaurus", 1, R.drawable.sulfur, "It can change weapons.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is best when gathering fiber?", "Therizinosaurus", "Gacha", "Pachyrhinosaurus", "Gigantopithecus", 2, R.drawable.ark_fiber, "Passive Taming", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur does not provide sauropod vertebra, which is the requirement of boss arenas?", "Diplodocus", "Brontosaurus", "Allosaurus", "Titanosaur", 3, R.drawable.sauropod_vertebra, "Dinosaurs with long tail and neck, and big body provides sauropod vertebra.", NULL, 0, context);
        insertQuiz(db,3, "Which dinosaur can gather sap?", "Thorny Dragon", "Therizinosaurus", "Archaeopteryx", "Dodo", 3, R.drawable.sap, "It can climb walls.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is best when gathering sand?", "Rock Elemental", "Gacha", "Pheonix", "Mantis", 4, R.drawable.sand, "It can change its weapon.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is best when gathering salt?", "Gacha", "Pheonix", "Mantis", "Rock Elemental", 3, R.drawable.salt, "It can change its weapon.", NULL, 0, context);

        //무게 감소
        insertQuiz(db,1, "Which dinosaur decreases the weight of metal ingot most?", "Argentavis", "Magmasaur", "Ravager", "Ankylosaurus", 2, R.drawable.metal_ingot, "It lay eggs.", NULL, 0, context);
        insertQuiz(db,3, "Which dinosaur decreases the weight of black pearl?", "Dunkleosteus", "Magmasaur", "Ravager", "Manta", 1, R.drawable.black_pearl, "It lives in water, and is slow.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur decreases the weight of cementing paste?", "Beelzebufo", "Achatina", "Equus", "Parasaur", 3, R.drawable.cementing_paste, "You can passive-tame it by carrot.", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur doesn't decrease the weight of crystal?", "Dunkleosteus", "Ravager", "Argentavis", "Ankylosaurus", 4, R.drawable.crystal, "It has horns on its back.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur doesn't decrease the weight of fiber?", "Ravager", "Argentavis", "Castoroides", "Thorny Dragon", 2, R.drawable.ark_fiber, "It flys.", NULL, 0, context);
        insertQuiz(db,3, "Which dinosaur doesn't decrease the weight of metal ingot?", "Magmasaur", "Ankylosaurus", "Gacha", "Mosasaurus", 4, R.drawable.metal_ingot, "It can carry platfrom saddle.", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur doesn't decrease the weight of obsidian?", "Dunkleosteus", "Tusoteuthis", "Argentavis", "Ravager", 2, R.drawable.obsidian, "Squid...", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur lower the weight of oil?", "Argentavis", "Ankylosaurus", "Dunkleosteus", "Gacha", 3, R.drawable.oil, "It can gather metal in the water.", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur lower the weight of organic polymer?", "Argentavis", "Ravager", "Equus", "Moschops", 3, R.drawable.organicpolymer, "It is flying dinosaur.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur lower the weight of stone?", "Basilisk", "Doedicurus", "Thorny Dragon", "Argentavis", 2, R.drawable.ark_stone, "It is flying dinosaur.", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur lower the weight of thatch?", "Thorny Dragon", "Argentavis", "Ravager", "Castoroides", 3, R.drawable.ark_thatch, "It can carry a human and average-size dinosaurs with right-click.", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur lower the weight of wood?", "Roll Rat", "Mammoth", "Thorny Dragon", "Gacha", 3, R.drawable.ark_wood, "Its saddle has durability.", NULL, 0, context);

        //(10.04)
        //자원 이름
        insertQuiz(db,1, "What is the name of this resource?", "Absorbent Substrate", "Allosaurus Brain", "Ambergris", "Angler Gel", 1, R.drawable.absorbent_substrate, "Dropped by Tusoteuthis.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Allosaurus Brain", "Ambergris", "Angler Gel", "Ammonite Bite", 1, R.drawable.allosaurus_brain, "Dropped by Allosaurus.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Ammonite Bile", "Ambergris", "Angler Gel", "Argentavis Talon", 2, R.drawable.ambergris, "Magmasaur food.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Basilosaurus Blubber", "Ammonite Bile", "Angler Gel", "Argentavis Talon", 2, R.drawable.ammonite_bile, "Dropped by Ammonite.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Bio Toxin", "Basilosaurus Blubber", "Angler Gel", "Argentavis Talon", 3, R.drawable.angler_gel, "Dropped by Angler.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Basilosaurus Blubber", "Berries", "Argentavis Talon", "Black Pearl", 3, R.drawable.argentavis_talon, "Dropped by argentavis.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Cementing Paste", "Cactus Sap", "Black Pearl", "Bio Toxin", 4, R.drawable.bio_toxin, "Dropped by Cnidaria.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Charcoal", "Cementing Paste", "Cactus Sap", "Black Pearl", 4, R.drawable.black_pearl, "Dropped by Ammonite, Deathworm, Eurypterid, Trilobite, Tusoteuthis.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Cactus Sap", "Cementing Paste", "Charcoal", "Chitin", 1, R.drawable.cactus_sap, "have water.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Chitin", "Charcoal", "Clay", "Corrupted Nodule", 2, R.drawable.charcoal, "Burnt wood.", NULL, 0, context);

        //키블 색
        insertQuiz(db,2, "베이직 키블의 색깔은?", "흰색", "파랑색", "노랑색", "하늘색", 1, R.drawable.basic_kibble, "눈 색깔이랑 같습니다.", NULL, 0, context);
        insertQuiz(db,2, "심플 키블의 색깔은?", "빨강색", "초록색", "노랑색", "하양색", 2, R.drawable.simple_kibble, "여름 풀 색깔이랑 같습니다.", NULL, 0, context);
        insertQuiz(db,2, "레귤러 키블의 색깔은?", "연두색", "하늘색", "노랑색", "파랑색", 4, R.drawable.regular_kibble, "낮 맑은 하늘 색과 같습니다.", NULL, 0, context);
        insertQuiz(db,2, "슈페리어 키블의 색깔은?", "보라색", "검정색", "노랑색", "남색", 1, R.drawable.superior_kibble, "자두 색깔이랑 같습니다.", NULL, 0, context);
        insertQuiz(db,2, "익셉셔널 키블의 색깔은?", "핑크색", "파랑색", "노랑색", "하늘색", 3, R.drawable.exceptional_kibble, "벌 색깔이랑 같습니다.", NULL, 0, context);
        insertQuiz(db,2, "특별한 키블의 색깔은?", "연두색", "하늘색", "은색", "벽돌색", 2, R.drawable.extraordinary_kibble, "하늘 색.", NULL, 0, context);

        //공룡 타입(비행, 지상 등등)
        insertQuiz(db,2, "하늘을 나는 공룡이 아닌 것은?", "Argentavis", "Astrocetus", "Insect Swarm", "Angler", 4, R.drawable.ark_logo, null, R.drawable.angler, 0, context);
        insertQuiz(db,2, "하늘을 나는 공룡이 아닌 것은?", "Desert Titan", "Dimorphodon", "Gasbags", "Basilosaurus", 4, R.drawable.ark_logo, null, R.drawable.basilosaurus, 0, context);
        insertQuiz(db,2, "하늘을 나는 공룡이 아닌 것은?", "Lymantria", "Phoenix", "Ammonite", "Quetzal", 3, R.drawable.ark_logo, null, R.drawable.quetzal, 0, context);
        insertQuiz(db,2, "하늘을 나는 공룡이 아닌 것은?", "Megameura", "Onyc", "Hesperornis", "Vulture", 3, R.drawable.ark_logo, null, R.drawable.hesperornis, 0, context);
        insertQuiz(db,2, "하늘을 나는 공룡이 아닌 것은?", "Wyven", "Coelacanth", "Snow Owl", "Gasbags", 2, R.drawable.ark_logo, null, R.drawable.coelacanth, 0, context);
        insertQuiz(db,2, "바다에 생존하는 공룡이 아닌 것은??", "Leedsichthys", "Alloasaurus", "Diplocauius", "Eurypterid", 2, R.drawable.ark_logo, null, R.drawable.allosaurus, 0, context);
        insertQuiz(db,2, "바다에 생존하는 공룡이 아닌 것은??", "Basilisk", "Liopleurodon", "Mosasaurus", "Megalodon", 1, R.drawable.ark_logo, null, R.drawable.basilisk, 0, context);
        insertQuiz(db,2, "바다에 생존하는 공룡이 아닌 것은??", "Beelzebufo", "Lamprey", "Otter", "Tusoteuthis", 1, R.drawable.ark_logo, null, R.drawable.beelzebufo, 0, context);
        insertQuiz(db,2, "바다에 생존하는 공룡이 아닌 것은??", "Bulbdog", "Eurypterid", "Cnidaria", "Baryonyx", 1, R.drawable.ark_logo, null, R.drawable.bulbdog, 0, context);
        insertQuiz(db,2, "바다에 생존하는 공룡이 아닌 것은??", "Electrophorus", "Arthropluera", "Dunkleosteus", "Ammonite", 2, R.drawable.ark_logo, null, R.drawable.arthropluera, 0, context);
        insertQuiz(db,2, "보스가 아닌 것은?", "Attack Drone", "Broodmother Lysrix", "Yeti", "Corrupted Master Controller", 3, R.drawable.ark_logo, null, R.drawable.yeti, 0, context);
        insertQuiz(db,2, "보스가 아닌 것은?", "Crystal Wyvern", "Deathworm", "Woolly Rhino", "Defense Unit", 3, R.drawable.ark_logo, null, R.drawable.woollyrhino, 0, context);
        insertQuiz(db,2, "보스가 아닌 것은?", "Desert Titan", "Dragon", "Trilobite", "Forest Titan", 3, R.drawable.ark_logo, null, R.drawable.trilobite, 0, context);
        insertQuiz(db,2, "보스가 아닌 것은?", "Ice Titan", "Thorny Dragon", "King Titan", "Manticore", 2, R.drawable.ark_logo, null, R.drawable.thornydragon, 0, context);
        insertQuiz(db,2, "보스가 아닌 것은?", "Megapithecus", "Moeder", "Overseer", "Thylacoleo", 4, R.drawable.ark_logo, null, R.drawable.thylacoleo, 0, context);

        //레벨당 배울 수 있는 엔그램 문제
        insertQuiz(db,1, "1렙에 배워져 있는 앤그램이 아닌 것은?", "쪽지", "돌 곡괭이", "횃불", "돌 도끼", 4, R.drawable.ark_logo, "이것은 나무를 잘 베죠", NULL, 0, context);
        insertQuiz(db,1, "4렙에 배울 수 있는 엔그램이 아닌 것은?", "짚 토대", "돌 토대", "짚 천장", "보관함", 2, R.drawable.ark_logo, "엔그램 배우는 순서는 짚 -> 돌이죠.", NULL, 0, context);

        //스프 재료 문제

        //염료 재료 문제

        //하늘을 나는 공룡이 낚을 수 있는 것들이 아닌 것은?

        //벽을 파괴 할 수 있는 공룡
    }

    public void insertQuiz(SQLiteDatabase mdb, int quiz_level, String quiz, String selection1, String selection2, String selection3, String selection4, int answer, int image, String hint, int hint_image, int is_shown, Context context){
        ContentValues contentValues=new ContentValues();
        contentValues.put(QUIZ_LEVEL, quiz_level);
        contentValues.put(QUIZ, quiz);
        contentValues.put(SELECTION_1, selection1);
        contentValues.put(SELECTION_2, selection2);
        contentValues.put(SELECTION_3, selection3);
        contentValues.put(SELECTION_4, selection4);
        contentValues.put(ANSWER, answer);
        if(hint!=null) contentValues.put(HINT, hint);
        Drawable imageIntToDrawable= ResourcesCompat.getDrawable(context.getResources(), image, null);
        contentValues.put(IMAGE, getByteArrayFromDrawable(imageIntToDrawable));
        if(hint_image!=NULL){
            Drawable imageIntToDrawable_hint=ResourcesCompat.getDrawable(context.getResources(), hint_image, null);
            contentValues.put(HINT_IMAGE, getByteArrayFromDrawable(imageIntToDrawable_hint));
        }
        contentValues.put(IS_SHOWN, is_shown);
        long newID=mdb.insert(DBHelper.TABLE_NAME, null, contentValues);
        Log.d("TAG", "insertQuiz 호출 / "+selection1);
        Log.d("TAG", "newID: "+newID);
    }

//    bitmap을 byte array로 변환
    public byte[] getByteArrayFromDrawable(Drawable drawable){
        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); //CompressFormat: PNG, JPEG 등 어떤 저장자를 쓸지 결정
        byte[] data=stream.toByteArray();

        return data;
    }

    public int getRowCount(SQLiteDatabase db){
        int cnt=0;
        Cursor cursor=db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        cnt=cursor.getCount();
        return cnt;
    }
//

}
