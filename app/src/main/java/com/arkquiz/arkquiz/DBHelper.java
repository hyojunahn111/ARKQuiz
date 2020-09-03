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
//            db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES(null, '1', '다음 공룡의 이름은 무엇일까요?', '아르젠타비스', '기가노토파우르스', '파키리노사우루스', '파키', '4')");
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
            insertQuiz(db,1, "How much time does it take for a rotten meat to decay?", "5 mins", "10 mins", "15 mins", "20 mins", 3, R.drawable.spoiled_meat, "Between decay time of a raw meat and a decay time of grilled meat.", NULL, 0, context);

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
//            insertQuiz(db,1, "다음 공룡의 소리는 누구일까요?", "안킬로사우르스", "펄로비아", "바위골렘", "샤이니혼", 2, R.drawable.ankylosaurus, "이 공룡의 이름은 '도'로 시작합니다", context);

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
            insertQuiz(db,2, "Which is not an ingredient of Nazarus chowder?", "Mejoberry × 10", "Corn × 5", "Stimulant × 2", "Potato × 5", 3, R.drawable.lazarus_chowder, "It's not stimberry or spark powder, but nacoberry and rotten meat.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Calien Soup?", "Citronal × 5", "Amarberry × 20", "Tintoberry × 10", "Stimulant × 2", 3, R.drawable.calien_soup, "10 is not enough..", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of cold curry?", "Corn × 5", "Carrot × 5", "Azulberry × 20", "Mejoberry × 20", 4, R.drawable.fria_curry, "10 is enough for the berry that dinosaurs love..", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Shadow Steak Saute?", "Narcotic × 10", "Rare Mushroom × 2", "Potato × 1", "Carrot × 1", 1, R.drawable.ark_logo, "8  Rotten meat + 40 Nacoberries are enough.", NULL, 0, context);
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
            insertQuiz(db,1, "공룡들이 제일 좋아하는 베리는 무엇인가요?", "아마르베리", "틴토베리", "메조베리", "아줄베리", 3, R.drawable.ark_logo, "보라색입니다.", NULL, 0, context);

            //<공룡 크기 문제>
            insertQuiz(db,1, "디노게이트로 가둘 수 없는 공룡은 무엇인가요?", "기가노토사우르스", "브론토", "아르젠", "프테라", 4, R.drawable.ark_logo, "하늘을 날아다니고 부리가 깁니다.", NULL, 0, context);
            insertQuiz(db,1, "디노게이트로 가둘 수 없는 공룡은 무엇인가요?", "마나가르마", "파라케르", "가스백", "디플로도쿠스", 3, R.drawable.ark_logo, "몸에 가스를 채울 수 있습니다", NULL, 0, context);
            insertQuiz(db,1, "디노게이트로 가둘 수 없는 공룡은 무엇인가요?", "투소", "그리핀", "둔클레오", "모사사우르스", 1, R.drawable.ark_logo, "다리가 많습니다", NULL, 0, context);
            insertQuiz(db,1, "디노게이트로 가둘 수 없는 공룡은 무엇인가요?", "렉스", "스피노", "알로사우르스", "카르노타우르스", 4, R.drawable.ark_logo, "손이 짧고 머리 앞에 뿔이 두 개 솟아있습니다", NULL, 0, context);
            insertQuiz(db,1, "디노게이트로 가둘 수 없는 공룡은 무엇인가요?", "유티라누스", "바리오닉스", "파라케", "바위정령", 2, R.drawable.ark_logo, "물에서 스턴을 넣을 수 있습니다", NULL, 0, context);

            //<테이밍 문제>
            insertQuiz(db,3, "(배율 1배 기준) 양고기로 150LV 벨제부포를 테이밍 했을 때 걸리는 시간은 몇 분인가요?", "10분", "11분", "12분", "13분", 3, R.drawable.ark_logo, "4의 배수 입니다.", NULL, 0, context);
            insertQuiz(db,2, "(배율 1배 기준) 150레벨 페록스를 테이밍 하기 위한 원소의 개 수는 몇 개가 필요한가요?", "10개", "11개", "12개", "13개", 1, R.drawable.ark_logo, "5의 배수 입니다.", NULL, 0, context);
            insertQuiz(db,3, "티타노사우르스를 테이밍 하기 위한 대포알의 개 수는 몇 개 인가요?", "205", "206", "207", "208", 2, R.drawable.ark_logo, "2x2x2x2x2x2x2x2-50", NULL, 0, context);
            insertQuiz(db,1, "(배율 1배 기준)150레벨 파라사우롤로푸스를 테이밍 하기 위한 마비화살의 개 수는?(활로 쐈을 경우)", "15발", "16발", "17발", "18발", 3, R.drawable.ark_logo, "소수입니다.", NULL, 0, context);
            insertQuiz(db,1, "(배율 1배 기준)150레벨 랩터를 테이밍 하기 위한 마비 화살의 개 수는?(활로 쐈을 경우)", "20발", "21발", "22발", "23발", 2, R.drawable.ark_logo, "7의 배수", NULL, 0, context);
            insertQuiz(db,1, "(배율 1배 기준)150레벨 수달을 테이밍하기 위한 검치연어(1.5X) 개 수는?", "1개", "2개", "3개", "4개", 2, R.drawable.ark_logo, "75레벨은 1개", NULL, 0, context);
            insertQuiz(db,1, "(배율 1배 기준)150레벨 독수리를 테이밍하기 위한 썩은고기의 개 수는?", "10개", "11개", "12개", "13개", 3, R.drawable.ark_logo, "4의 배수", NULL, 0, context);
            insertQuiz(db,1, "(배율 1배 기준)1레벨 거대벌을 테이밍하기 위한 희귀 꽃의 개 수는?", "1개", "3개", "5개", "7개", 2, R.drawable.ark_logo, "손으로 희귀꽃을 주웠을 때 나오는 희귀꽃에서 하나 빼면 됩니다.", NULL, 0, context);
            insertQuiz(db,1, "(배율 1배 기준)150레벨 글로우테일을 테이밍하기 위한 아사믹 버섯의 개 수는?", "25", "30", "35", "40", 1, R.drawable.ark_logo, "150/6", NULL, 0, context);
            insertQuiz(db,2, "에버레이션에서 유기폴리머를 제일 잘 캐는 공룡은?", "다이어베어", "모스콥스", "다이어울프", "디메트로돈", 1, R.drawable.ark_logo, "이 공룡은 꿀도 잘 캡니다.", NULL, 0, context);
            insertQuiz(db,2, "프로콥토톤으로 열매 300칸을 캤을 때 프로콥토톤이 최소한 움직일 수 있게 하려면 무게가 몇이 되어야 하나요?", "1000이상", "2000이상", "3000이상", "4000이상", 4, R.drawable.ark_logo, "제일 높은거", NULL, 0, context);
            insertQuiz(db,2, "엔포서를 만들 수 있는 장소는?", "제작기", "대장간", "테크 제작기", "화학 실험대", 3, R.drawable.ark_logo, "보스를 깨야 배울 수 있는 엔그램", NULL, 0, context);
            insertQuiz(db,2, "데스웜을 잡았을 때 나오는게 아닌 것은?", "유기 폴리머", "데스윔 뿔", "앵글러 젤", "키틴", 4, R.drawable.ark_logo, "데스웜은 곤충이 아닌가 봐요", NULL, 0, context);

            //<기절 떨어지는 속도 문제>
            insertQuiz(db,1, "(도도덱스 기준) 눈 올빼미 기절이 떨어지는 속도는 어느 정도 인가요?", "낮음", "보통", "빠름", "매우 빠름", 1, R.drawable.ark_logo, "기이이이저어어얼", NULL, 0, context);
            insertQuiz(db,1, "(도도덱스 기준) 가스백 기절이 풀리는 속도는 어느 정도인가요?", "낮음", "보통", "빠름", "매우 빠름", 1, R.drawable.ark_logo, "기이이이저어어얼", NULL, 0, context);
            insertQuiz(db,2, "(도도덱스 기준) 기가노토 기절이 풀리는 속도는 어느 정도인가요?", "낮음", "보통", "빠름", "매우 빠름", 4, R.drawable.ark_logo, "스포츠카 속도 만큼", NULL, 0, context);
            insertQuiz(db,2, "(도도덱스 기준) 도에디 기절이 풀리는 속도는 어느 정도인가요?", "낮음", "보통", "빠름", "매우 빠름", 3, R.drawable.ark_logo, "4G", NULL, 0, context);
            insertQuiz(db,2, "(도도덱스 기준) 안킬로 기절이 풀리는 속도는 어느 정도인가요?", "낮음", "보통", "빠름", "매우 빠름", 1, R.drawable.ark_logo, "기이이이저어어얼", NULL, 0, context);
            insertQuiz(db,2, "(도도덱스 기준) 스피노 기절이 풀리는 속도는 어느 정도인가요?", "낮음", "보통", "빠름", "매우 빠름", 3, R.drawable.ark_logo, "4G", NULL, 0, context);
            insertQuiz(db,2, "(도도덱스 기준) 랩터 기절이 풀리는 속도는 어느 정도인가요?", "낮음", "보통", "빠름", "매우 빠름", 1, R.drawable.ark_logo, "기이이이저어어얼", NULL, 0, context);

            //<색깔 문제>
            insertQuiz(db,1, "메조베리의 색깔은 무슨 색인가요?", "흰색", "빨간색", "초록색", "보라색", 4, R.drawable.ark_logo, "무지개 마지막 색깔", NULL, 0, context);
            insertQuiz(db,1, "아마르베리의 색깔은 무슨 색인가요?", "노란색", "검정색", "파랑색", "주황색", 1, R.drawable.ark_logo, "무지개 3번째 색깔", NULL, 0, context);
            insertQuiz(db,1, "아줄베리의 색깔은 무슨 색인가요?", "노란색", "갈색", "파란색", "분홍색", 3, R.drawable.ark_logo, "하늘색깔", NULL, 0, context);
            insertQuiz(db,1, "나코베리의 색깔은 무슨 색인가요?", "빨간색", "검정색", "회색", "흰색", 2, R.drawable.ark_logo, "니 미래ㅋㅋㅋㅋㅋㅋㅈㅅ 밤이 되었을 때 하늘색", NULL, 0, context);
            insertQuiz(db,1, "스팀베리의 색깔은 무슨 색인가요?", "흰색", "노란색", "빨간색", "검정색", 1, R.drawable.ark_logo, "눈 색깔", NULL, 0, context);
            insertQuiz(db,1, "틴토베리의 색깔은 무슨 색인가요?", "노란색", "빨간색", "분홍색", "보라색", 2, R.drawable.ark_logo, "피 색깔", NULL, 0, context);

            //<채집 효율>
            insertQuiz(db,2, "생고기를 제일 잘 캐는 것은?", "철 곡괭이", "렉스", "모사사우르스", "기가노토사우르스", 4, R.drawable.ark_logo, "아크에서 제일 강한 공룡", NULL, 0, context);
            insertQuiz(db,2, "흑진주를 제일 잘 얻는 공룡은?", "불새", "수달", "가챠", "앵글러피쉬", 3, R.drawable.ark_logo, "개인 생산이 제일 잘 얻을 수 있죠", NULL, 0, context);
            insertQuiz(db,2, "시멘트 폴을 제일 잘 얻을 수 있는 것은?", "아카티나", "벨저부포", "비버댐", "메가네우라", 2, R.drawable.ark_logo, "개굴..개굴..", NULL, 0, context);
            insertQuiz(db,2, "키틴을 제일 잘 얻을 수 있는 것은?", "메가테리움", "철 곡괭이", "검치호", "사마귀", 1, R.drawable.ark_logo, "곤충을 얻으면 버프를 얻는 공룡", NULL, 0, context);
            insertQuiz(db,2, "크리스탈을 제일 잘 얻을 수 있는 것은?", "철 곡괭이", "사마귀", "안킬로사우르스", "가챠", 1, R.drawable.ark_logo, "사람", NULL, 0, context);
            insertQuiz(db,1, "섬유를 제일 잘 얻을 수 있는 것은?", "모스콥스", "철제 낫", "기간토피테쿠스", "다이어베어", 2, R.drawable.ark_logo, "사람", NULL, 0, context);
            insertQuiz(db,1, "부싯돌을 제일 잘 얻을 수 있는 것은?", "철 곡괭이", "안킬로사우르스", "사마귀", "바위정령", 1, R.drawable.ark_logo, "사람", NULL, 0, context);
            insertQuiz(db,2, "가죽을 제일 잘 얻을 수 있는 것은?", "전기톱", "기가노토사우르스", "다이어울프", "틸라콜레오", 1, R.drawable.ark_logo, "공룡 < 사람", NULL, 0, context);
            insertQuiz(db,2, "철을 제일 많이 얻을 수 있는 것은?", "다풀로카울르스", "사마귀", "불새", "마그마사우르스", 4, R.drawable.ark_logo, "", NULL, 0, context);
            insertQuiz(db,1, "흑요석을 제일 잘 얻을 수 있는 것은?", "사마귀", "도에디쿠스", "가챠", "안킬로사우르스", 2, R.drawable.ark_logo, "껍질안에 숨을 수 있습니다.", NULL, 0, context);
            insertQuiz(db,2, "오일을 제일 잘 얻을 수 있는 것은?", "둔클레오스테스", "투소테우스", "쇠똥구리", "가챠", 4, R.drawable.ark_logo, "개인 생산이 제일 빠르죠", NULL, 0, context);
            insertQuiz(db,1, "유기폴리머를 제일 잘 얻을 수 있는 것은?", "목재 둔기", "전기톱", "가챠", "펠라고르니스", 1, R.drawable.ark_logo, "이걸 사용하면 마비 수치가 올라갑니다.", NULL, 0, context);
            insertQuiz(db,2, "희귀꽃을 제일 잘 얻을 수 있는 것은?", "테리지노사우르스", "모스콥스", "마나가르마", "메가셀론", 4, R.drawable.ark_logo, "거부기..", NULL, 0, context);
            insertQuiz(db,2, "희귀버섯을 제일 잘 얻을 수 있는 것은?", "테리지노사우르스", "모스콥스", "메가셀론", "이구아노돈", 3, R.drawable.ark_logo, "거어부우기", NULL, 0, context);
            insertQuiz(db,2, "날 생선살을 제일 잘 얻을 수 있는 것은?", "철제 도끼", "모사사우르스", "스피노", "바리오닉스", 1, R.drawable.ark_logo, "공룡보단 사람이죠", NULL, 0, context);
            insertQuiz(db,2, "고품질 날생선살을 제일 잘 캐는 것은?", "모사사우르스", "철제 낫", "메가셀론", "스피노", 1, R.drawable.ark_logo, "목이 깁니다", NULL, 0, context);
            insertQuiz(db,3, "고품질 생고기를 제일 잘 캐는 것은?", "리퍼킹", "렉스", "와이번", "크리스탈 와이번", 1, R.drawable.ark_logo, "땅에 숨을 수 있습니다.", NULL, 0, context);
            insertQuiz(db,1, "실리카 진주를 제일 잘 캐는 것은?", "가챠", "수달", "불새", "앵글러피쉬", 4, R.drawable.ark_logo, "초롱아귀", NULL, 0, context);
            insertQuiz(db,1, "돌을 제일 잘 캐는 것은?", "바위정령", "사마귀", "도에디쿠스", "마그마사우르스", 3, R.drawable.ark_logo, "꼬리에 둥글둥글한 것이 있습니다.", NULL, 0, context);
            insertQuiz(db,1, "짚을 제일 잘 캐는 것은?", "브론토사우르스", "메갈로캐로스", "안킬로사우르스", "메가테리움", 1, R.drawable.ark_logo, "보기 중에 덩치가 제일 큰 공룡입니다.", NULL, 0, context);
            insertQuiz(db,1, "목재를 제일 잘 캐는 것은?", "테리지노사우르스", "롤렛", "가시 드래곤", "파키리노사우르스", 1, R.drawable.ark_logo, "손톱이 긴 공룡입니다.", NULL, 0, context);
            insertQuiz(db,2, "유황을 제일 잘 캐는 공룡은?", "사마귀", "불새", "바위정령", "안킬로사우르스", 1, R.drawable.ark_logo, "무기를 바꿔 낄 수 있는 공룡입니다.", NULL, 0, context);
            insertQuiz(db,2, "섬유를 제일 잘 얻는 공룡은?", "테리지노사우르스", "가챠", "파키리노사우르스", "기간토피테쿠스", 2, R.drawable.ark_logo, "(--> 이건 잘 모르겠다 개인생산인지 아닌지 일단 힌트 이걸로 해두셈 나중에 내가 직접해보고 바꾸겠음) 자연테이밍", NULL, 0, context);
            insertQuiz(db,1, "보스 공물은 용각류 척추가 나오지 않는 공룡은?", "디플로도쿠스", "브론토사우르스", "알로사우르스", "티타노사우르스", 3, R.drawable.ark_logo, "꼬리가 길고 덩치가 큰 공룡이 아닌 것은 찾으면 되요", NULL, 0, context);
            insertQuiz(db,3, "나무 수액을 캐는 공룡은?", "가시드래곤", "테리지노사우르스", "시조새", "도도", 3, R.drawable.ark_logo, "벽을 탈 수 있는 공룡입니다", NULL, 0, context);
            insertQuiz(db,2, "모래를 제일 잘 캐는 공룡은?", "바위정령", "가챠", "불새", "사마귀", 4, R.drawable.ark_logo, "무기를 바꿔 낄 수 있느 공룡입니다.", NULL, 0, context);
            insertQuiz(db,2, "소금을 제일 잘 캐는 공룡은?", "가챠", "불새", "사마귀", "바위정령", 3, R.drawable.ark_logo, "무기를 바꿔 낄 수 있는 공룡입니다", NULL, 0, context);

            //무게 감소
            insertQuiz(db,1, "제련 된 철 무게를 제일 많이 줄여주는 공룡은?", "아르젠", "마그마사우르스", "레비져", "안킬로사우르스", 2, R.drawable.ark_logo, "알에서 부화시키는 공룡입니다.", NULL, 0, context);
            insertQuiz(db,3, "흑진주의 무게를 줄여주는 공룡은?", "둔클레오스테스", "마그마사우르스", "레비져", "만타", 1, R.drawable.ark_logo, "물에 살고 속도가 느립니다.", NULL, 0, context);
            insertQuiz(db,2, "시멘트폴의 무게를 줄여주는 공룡은?", "벨저부포", "아키타나", "에쿠스", "파라사우르스", 3, R.drawable.ark_logo, "당근으로 친화 테이밍 가능한 공룡입니다", NULL, 0, context);
            insertQuiz(db,1, "크리스탈의 무게를 줄여주는 공룡이 아닌 것은?", "둔클레오스테스", "레비져", "아르젠타비스", "안킬로사우르스", 4, R.drawable.ark_logo, "등에 뾰족뾰족한 가시나 솟아나 있습니다.", NULL, 0, context);
            insertQuiz(db,2, "섬유의 무게를 줄여주는 공룡이 아닌 것은?", "레비져", "아르젠타비스", "카스트로데이스", "가시 드래곤", 2, R.drawable.ark_logo, "하늘을 날아다닙니다", NULL, 0, context);
            insertQuiz(db,3, "제련되지 않은 철의 무게를 줄여주는 공룡이 아닌 것은?", "마그마사우르스", "안킬로사우르스", "가챠", "모사사우르스", 4, R.drawable.ark_logo, "등에 플랫폼 안장을 지을 수 있는 공룡입니다.", NULL, 0, context);
            insertQuiz(db,1, "흑요석의 무게를 줄여주지 않는 공룡은?", "둔클레오스테스", "투소테우티스", "아르젠타비스", "레바져", 2, R.drawable.ark_logo, "오징오징...", NULL, 0, context);
            insertQuiz(db,2, "오일의 무게를 줄여주는 공룡은?", "아르젠", "안킬로사우르스", "둔클레오스테스", "가챠", 3, R.drawable.ark_logo, "바다에서 철을 캘 수 있는 공룡입니다.\n", NULL, 0, context);
            insertQuiz(db,1, "유기폴리머의 무게를 줄여주는 공룡은?", "아르젠", "레비져", "에쿠스", "모스콥스", 3, R.drawable.ark_logo, "하늘을 날아다니는 공룡입니다", NULL, 0, context);
            insertQuiz(db,2, "돌 무게를 줄여주는 공룡이 아닌 것은?", "바실리스크", "도에디쿠스", "가시 드래곤", "아르젠타비스", 2, R.drawable.ark_logo, "하늘을 날아다니는 공룡입니다.", NULL, 0, context);
            insertQuiz(db,1, "짚의 무게를 줄여주는 공룡이 아닌 것은?", "가시 드래곤", "아르젠타비스", "레비져", "카스트로이데스", 3, R.drawable.ark_logo, "우클릭으로 사람과 보통 공룡을 찝을 수 있습니다.\n", NULL, 0, context);
            insertQuiz(db,1, "목제의 무게를 제일 많이 줄여주는 공룡은?", "룰랫", "맘모스", "가스 드래곤", "가챠", 3, R.drawable.ark_logo, "이 공룡의 안장에는 내구도가 있습니다.", NULL, 0, context);






        }catch (Exception e){
            e.printStackTrace();
        }
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
