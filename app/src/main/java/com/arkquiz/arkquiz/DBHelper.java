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
            insertQuiz(db,1, "What is the name of this dinosaur?", "Rex", "Diplodocus", "Parasaur", "Pachy", 3, R.drawable.ark_parasaur, "This dinosaur has the ability to detect enemies.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Griffin", "Parasaur", "Ankylosaurus", "Quetzal", 3, R.drawable.ark_ankylosaurus, "This dinosaur gathers metal well.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Ankylosaurus", "Baryonyx", "Beelzebufo", "Direwolf", 2, R.drawable.ark_baryonyx, "This dinosaur does not have an oxygen gauge.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Bulbdog", "Rex", "Woolly Rhino", "Unicorn", 3, R.drawable.woollyrhino, "This dinosaur can accumulate attack gauges.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Titanoboa", "Seeker", "Daeodon", "Giganotosaurus", 4, R.drawable.giganotosaurus, "Its attacking range is very wide.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Dung Beetle", "Oviraptor", "Pegomastax", "Allosaurus", 4, R.drawable.allosaurus, "This dinosaur has flock buff and bleeding damage.", NULL,0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Compy", "Morellatops", "Chalicotherium", "Gacha", 1, R.drawable.compy, "This dinosaur is small and has flock buffs.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Defense Unit", "Carnotaurus", "Magmasaur", "Kentrosaurus", 2, R.drawable.carnotaurus, "This dinosaur has short arms and two small horns on its head.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Spino", "Yeti", "Brontosaurus", "Yutyrannus", 3, R.drawable.brontosaurus, "This dinosaur is huge and attacks with its tail.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Dilophosaur", "Thorny Dragon", "Terror Bird", "Stegosaurus", 1, R.drawable.dilophosaur, "When this dinosaur spits, the vision is obscured.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Argentavis", "Triceratops", "Purlovia", "Kaprosuchus", 1, R.drawable.argentavis, "This dinosaur reduces weight in half.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Megalodon", "Direwolf", "Gacha", "Phiomia", 4, R.drawable.phiomoa, "This dinosaur is used a lot when collecting poop.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Pulmonoscorpius", "Morellatops", "Doedicurus", "Iguanodon", 1, R.drawable.pulmonoscorpius, "If it hits you, your torpidity will increase.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Bulbdog", "Diplodocus", "Onyc", "Pachyrhinosaurus", 3, R.drawable.onyc, "This dinosaur lives in caves and has a chance of causing rabies.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Spino", "Meganeura", "Baryonyx", "Leech", 1, R.drawable.spino, "This dinosaur gets a water buff when it touches the water.", NULL, 0, context);
            insertQuiz(db,2, "How many times can Managarmr jump in the air?", "1 Time", "2 Time", "3 Time", "4 Time", 3, R.drawable.managarma, "jujujump...", NULL, 0, context);
            insertQuiz(db,2, "How many dashes can Managarmr use?", "1 Time", "2 Time", "3 Time", "4 Time", 1, R.drawable.managarma, "managarmr is not a bird", NULL, 0, context);


//          자원문제
            insertQuiz(db,1, "Which is not gathered by Castoroides?", "Wood", "Silica Pearls", "Rare Mushroom", "Thatch", 4, R.drawable.castoroides, "It is yellow", NULL, 0, context);
            insertQuiz(db,1, "Which is not gathered by killing Trilobites?", "Black Pearl", "Silica Pearls", "Oil", "Hide", 4, R.drawable.trilobite, "Remember that it is a'crustacean'.", NULL, 0, context);
            insertQuiz(db,1, "Which is not gathered by killing Ovis?", "Raw Mutton", "Raw meat", "Hide", "Pelt", 2, R.drawable.ovis, "'Ovis' is'sheep'.", NULL, 0, context);

            insertQuiz(db,1, "Which is not gathered by killing water bugs?", "Chitin", "Raw meat", "Cementing Paste", "Silk", 4, R.drawable.jugbug, "We can make clothes with this resource.", NULL, 0, context);

//          음식문제
            insertQuiz(db,1, "Which of these is not a berry?", "Amarberry", "Azulberry", "Tintoberry", "Pozoberry", 4, R.drawable.ark_logo, "Its name doesn't start with letter 'A'.", NULL, 0, context);

            //부패시간 문제
            insertQuiz(db,2, "How much time does it take for a berry to spoil?", "5 mins", "10 mins", "15 mins", "20 mins", 2, R.drawable.berries, "Korean students have this much break time in school.", NULL, 0, context);
            insertQuiz(db,2, "How much time does it take for a grain to spoil?", "5 mins", "10 mins", "15 mins", "20 mins", 1, R.drawable.citronal, "Divide a day in half and half and half and half and half and then divde it into 9. ", NULL, 0, context);
            insertQuiz(db,2, "WHow much time does it take for a raw meat to spoil?", "5 mins", "10 mins", "15 mins", "20 mins", 2, R.drawable.raw_meat, "Korean students have this much break time in school.", NULL, 0, context);
            insertQuiz(db,2, "How much time does it take for a stack of raw meat to spoil?", "5 hours 10 mins", "5 hours 40 mins", "6 hours 10 mins", "6 hours 40 mins", 4, R.drawable.raw_meat, "Multiply one spoiling time of a raw meat by 40!", NULL, 0, context);
            insertQuiz(db,2, "How much time does it take for a raw prime meat to spoil?", "2 mins", "2 mins 10 sec", "2 mins 20 sec", "2 mins 30 sec", 3, R.drawable.raw_prime_meat, "'140'", NULL, 0, context);
            insertQuiz(db,2, "How much time does it take for a spoiled meat to spoil?", "5 mins", "10 mins", "15 mins", "20 mins", 3, R.drawable.spoiled_meat, "Between spoiling time of a raw meat and a spoiling time of cooked meat.", NULL, 0, context);

            //한 칸 문제
            insertQuiz(db,1, "How many raw meats can you have in an item square?"/*한 칸에 들 수 있는 생고기의 양은?*/, "10", "20", "30", "40", 4, R.drawable.raw_meat, "If you get 10000 meat, 250 squares are filled.", NULL, 0, context);
            insertQuiz(db,1, "How many raw prime meats can you get in an item square?"/*한 칸에 들 수 있는 고품질 생고기의 양은?*/, "1", "2", "3", "4", 1, R.drawable.raw_prime_meat, "If you get 300 prime raw meat, you can fill 300 squares.", NULL, 0, context);
            insertQuiz(db,1, "How much cooked prime meats can be in an item square?", "10", "20", "30", "40", 3, R.drawable.cooked_prime_meat, "raw prime meat X 30", NULL, 0, context);
            insertQuiz(db,1, "How many bottles of Wyvern milk can you have in an item square?", "1", "2", "3", "4", 1, R.drawable.wyvern_milk, "Same as raw prime meat.", NULL, 0, context);

            //제작 문제
            insertQuiz(db,2, "What is not used when making gunpowder?", "Flint", "stone", "Thatch", "Charcoal", 3, R.drawable.gunpowder, "It is light yellow.", NULL, 0, context);
            insertQuiz(db,3, "How many elements do you need to make a primitive tek rifle?", "10", "20", "30", "40", 2, R.drawable.tek_rifle, "It takes as much as the elements that come out when you catch gamma Megapithecus.", NULL, 0, context);
            //데미지
            insertQuiz(db,3, "What is a damage of a shot of bow?", "50", "55", "60", "65", 2, R.drawable.bow, "If you increase your hp once and then shot by a bow, your hp becomes half.", NULL, 0, context);
            //자원문제
            insertQuiz(db,1, "What can you mostly get when you mining a stone with a pick?", "Flint", "stone", "Metal", "stone powder", 1, R.drawable.metal_pick, "It's best to pick yellow and triangular things.", NULL, 0, context);
            insertQuiz(db,1, "Which is not obtained by using a metal sickle?", "Fiber", "Raw prime fish meat", "Flint", "Thatch", 4, R.drawable.metal_sickle, "We can't attack trees with a metal sickle.", NULL, 0, context);
            insertQuiz(db,1, "Which is hardly got when using a hatchet?(Comparing to other examples.)", "Leech blood", "Hide", "AnglerGel", "stone", 4, R.drawable.metal_hatchet, "This resource is hard.", NULL, 0, context);
            insertQuiz(db,1, "Which is not an ingredient of a paint brush?", "Wood", "Hide", "Fiber", "Thatch", 3, R.drawable.paintbrush, "You can get this from grass.", NULL, 0, context);
            insertQuiz(db,1, "What is not an ingredient of parachute?", "Thatch", "Hide", "Wood", "Fiber", 3, R.drawable.parachute, "You can get this from trees.", NULL, 0, context);
            insertQuiz(db,1, "Where can we make torch?", "Personal inventory", "Making machine", "Smithy", "Mortar and Pestle", 1, R.drawable.torch, "User whose level is 1 can make torch.", NULL, 0, context);
            insertQuiz(db,1, "What level can you learn a small telescope?", "7", "10", "13", "15", 1, R.drawable.spyglass, "Newbies can easily learn it.", NULL, 0, context);

            //오벨리스크
            insertQuiz(db,3, "What is the coordinates of the Island Blue Obelisk?", "13.1, 55.3", "35.4, 64.3", "25.5, 25.6", "39.2, 68.7", 3, R.drawable.obelisk_1, "It's in the upper left.", NULL, 0, context);
            insertQuiz(db,3, "What is the coordinates of the Island Red Obelisk?", "79.8, 17.4", "75.2, 13.8", "49.1, 82.0", "24.9, 86.4", 1, R.drawable.obelisk_1, "It's in the bottom left corner.", NULL, 0, context);
            insertQuiz(db,3, "What is the coordinates of the Island Green Obelisk?", "14.8, 76.0", "59.0, 72.3", "19.4, 28.6", "16.8, 71.3", 2, R.drawable.obelisk_1, "It's in the middle right.", NULL, 0, context);



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
            insertQuiz(db, 2, "Which dinosaur is the best for getting metal?", "Ankylosaurus", "Megaloceros", "Dodo", "Mosasaurus", 1, R.drawable.ark_metal, "This dinosaur looks hard.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is the best for getting thatch?", "Pteranodon", "Ovis", "Allosaurus", "Megaloceros", 4, R.drawable.ark_thatch, "This may not be a dinosaur.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is the best to getting wood?", "Pachy", "Parasaur", "Mammoth", "Sabertooth", 3, R.drawable.ark_wood, "This is large in size.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is best for getting stone?", "Doedicurus", "Triceratops", "Compy", "Quetzal", 1, R.drawable.ark_stone, "This dinosaur looks hard.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is best for getting berry?", "Purlovia", "Brontosaurus", "Rex", "Rarasaur", 2, R.drawable.ark_berries, "This dinosaur has a really long neck.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is best for getting hide?", "Therizinosaur", "Trilobite", "Quetzal", "Gigantopithecus", 1, R.drawable.ark_hide, "This dinosaur has long nails.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is best for getting flint?", "Dierwolf", "Ankylosaurus", "Raptor", "Manta", 2, R.drawable.ark_flint, "This dinosaur looks hard.", NULL, 0, context);
            insertQuiz(db, 2, "What is the best tool for getting metal", "Metal Hatchet", "Metal pick", "Metal sickle", "Sword", 2, R.drawable.ark_metal, "Think of the real use of the tools", NULL, 0, context);
            insertQuiz(db, 2, "Which is the best tool for getting stone?", "stone Hatchet", "Metal Hatchet", "stone Pick", "Metal Pick", 2, R.drawable.ark_stone, "Hatchet are better at gathering stones than picks.", NULL, 0, context);
            insertQuiz(db, 2, "Which tool is best for getting flint?", "stone Hatchet", "Metal Hatchet", "stone Pick", "Metal Pick", 4, R.drawable.ark_flint, "A pick is more efficient at gathering flint than an Hatchet.", NULL, 0, context);
            insertQuiz(db, 2, "What is the best tool for getting wood?", "stone Hatchet", "Metal Hatchet", "stone Pick", "Metal Pick", 2, R.drawable.ark_wood, "It is more efficient to use hatchet rather than pick when getting wood.", NULL, 0, context);
            insertQuiz(db, 2, "Which of the following is the best tool for gathering thatch?", "stone Hatchet", "Metal Hatchet", "stone Pick", "Metal Pick", 4, R.drawable.ark_thatch, "It is more efficient to use pick rather than hatchet when getting thatch.", NULL, 0, context);
            insertQuiz(db, 2, "Which of the following is the best tool for getting fiber?", "Metal sickle", "Whip", "Metal Hatchet", "Metal Pick", 1, R.drawable.ark_fiber, "This tool is used to cut something.", NULL, 0, context);
            insertQuiz(db, 2, "What is the best tool for gathering Hide?", "Metal Hatchet", "Metal Pick", "Sword", "Metal sickle", 1, R.drawable.ark_hide, "This tool is a good tool for getting wood.", NULL, 0, context);
            insertQuiz(db, 2, "Which of the following is the best tool for getting raw meat?", "Metal Hatchet", "Metal Pick", "Sword", "Metal sickle", 2, R.drawable.raw_meat, "This tool is a good tool for getting metal.", NULL, 0, context);
            insertQuiz(db, 2, "Which of the following is not a dinosaur that reduces the weight of stones?", "Castoroides", "Equus", "Doedicurus", "Morellatops", 4, R.drawable.ark_stone, "This dinosaur is often seen in the desert.", NULL, 0, context);
            insertQuiz(db, 2, "Which of the following is not a dinosaur that reduces the weight of wood?", "Gacha", "Mammoth", "Equus", "Thorny Dragon", 3, R.drawable.ark_wood, "This dinosaur is often seen on large plains.", NULL, 0, context);
            insertQuiz(db, 2, "Which of the following is not a dinosaur that reduces the weight of metal?", "Magmasaur", "Wyvern", "Argentavis", "Ankylosaurus", 2, R.drawable.ark_metal, "This dinosaur can shoot breath.", NULL, 0, context);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadQuiz3(SQLiteDatabase db, Context context){
        try{
            //          유물 문제
            insertQuiz(db,3, "Where is the relic of Irish Clever in 'The Island'? \n (There may be individual errors)", "41.1,48.8", "80.0, 53.6", "50.0, 52.3", "71.4, 86.4", 1, R.drawable.artifact_of_the_clever, "It is near The Frozen Tooth, the mountain with full of resources.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Hunter in 'The Island'? \n (There may be individual errors)", "68.7, 56.5", "41.1, 53.6", "80.0, 53.6", "22.2, 88.9", 3, R.drawable.artifact_of_the_hunter, "It is near the south.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Cunning in 'The Island'? \n (There may be individual errors)", "35.2,48.8", "74.3, 36.6", "45.3, 91.9", "32.4, 14.2", 3, R.drawable.artifact_of_the_cunning, "Between the island of death and the Herbivorous island.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Strong in 'The Island'? \n (There may be individual errors)", "29.5,32.1", "87.2, 52.3", "29.3, 60.2", "10.3, 55.6", 1, R.drawable.artifact_of_the_strong, "The entrance is small, but inside is in a large cave.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Devourer in 'The Island'? \n (There may be individual errors)", "14.3, 78.5", "14.6, 85.6", "98.1, 33.1", "41.1, 65.6", 2, R.drawable.artifact_of_the_devourer, "It is called Death Island.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Massive in 'The Island'? \n (There may be individual errors)", "46.3, 85.3", "90.2, 8.1", "78.2, 14.8", "71.4, 86.4", 4, R.drawable.artifact_of_the_massive, "It is near Herbivorous island.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Pack in 'The Island'? \n (There may be individual errors)", "32.2, 88.1", "8.1, 82.1", "59.2, 61.3", "68.7, 56.5", 4, R.drawable.artifact_of_the_pack, "It's in the water and it's between the Red Obelisk and Snow Mountain.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Brutal in 'The Island'? \n (There may be individual errors)", "34.3, 11.2", "61.2, 32.4", "82.1, 35.7", "52.4, 10.3", 4, R.drawable.artifact_of_the_brute, "Between Redwood and SW2 Resurrection Zone.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Immune in 'The Island'? \n (There may be individual errors)", "95.4, 14.4", "96.2, 14.6", "62.7, 37.1", "13.8, 49.7", 3, R.drawable.artifact_of_the_immune, "It is called a Poison Cave.", NULL, 0, context);
            insertQuiz(db,3, "Where is the relic of Irish Skylord in 'The Island'? \n (There may be individual errors)", "91.6,81.7", "19.1, 19.0", "23.5,71.9", "15.2,38.9", 2, R.drawable.artifact_of_the_skylord, "It is near Penguin Island.", NULL, 0, context);
            //insertQuiz(db,1, "다음중 브루드마더의 유물이 아닌 것은?", "현명함의 유물", "거대함의 유물", "사냥꾼의 유물", "하늘군주의 유물", 4, R.drawable.ark_logo, R.drawable.skylord, NULL, 0, context);

            //          보스 공물 문제
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute of the boss.", "Argentavis", "Sarco", "Megalania", "Procoptodon", 4, R.drawable.ark_logo, "This dinosaur jumps high.", NULL, 0, context);
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute of the boss.", "Carnotaurus", "Kairuku", "Giganotosaurus", "Megalodon", 2, R.drawable.ark_logo, "This dinosaur gives an organic polymer.", NULL, 0, context);
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute of the boss.", "Basilosaurus", "Thylacoleo", "Allosaurus", "Megatherium", 4, R.drawable.ark_logo, "This dinosaur gets a buff when it kills insects.", NULL, 0, context);
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute of the boss.", "Brontosaurus", "Spino", "Raptor", "Diplodocus", 3, R.drawable.ark_logo, "This dinosaur has two-legged jumping and catching skills.", NULL, 0, context);
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute of the boss.", "Rex", "Titanoboa", "Woolly Rhino", "Ravager", 4, R.drawable.ark_logo, "This dinosaur is a quadruped and reduces weight.", NULL, 0, context);
//          알파공룡 문제
            insertQuiz(db,3, "Which of the following is not an alpha dinosaur?", "Alpha Leedsichthys", "Alpha Raptor", "Alpha Reaper king", "Alpha Castoroides", 4, R.drawable.ark_logo, "This dinosaur collects Cementing Paste, Wood, rare flowers, rare mushrooms, and silica pearls.", NULL, 0, context);
            insertQuiz(db,3, "Which of the following is not an alpha dinosaur?", "Alpha Megalodon", "Alpha Mosasaur", "Alpha Megalania", "Alpha Tusoteuthis", 3, R.drawable.ark_logo, "Mainly living in the ceiling in the cave and there are many in Ragnarok", NULL, 0, context);

            insertQuiz(db,2, "Which is not obtained by killing Managarmr?", "Chitin", "Keratin", "Hide", "Raw prime Meat", 1, R.drawable.managarma, "Managamar is not an insect.", NULL, 0, context);
            insertQuiz(db,2, "How many bottles of milk you can get when you stun Wyvern?", "4", "5", "6", "7", 2, R.drawable.wyvern_milk, "Less than a line.", NULL, 0, context);
//          보스 문제
            insertQuiz(db,2, "How many elements does Gamma Megapithecus catch?", "10", "20", "30", "40", 2, R.drawable.boss_megapithecus, "This question may be difficult. Maybe '2' people can solve it.", NULL, 0, context);
            //insertQuiz(db,1, "베타 메가피테쿠스를 잡으면 주는 원소의 양은?", "10", "20", "30", "40", 2, R.drawable.boss_megapithecus, "감마랑 똑같습니다.", NULL, 0, context);
            insertQuiz(db,2, "What is Gamma Rockwell's level limit?", "Lv50", "Lv60", "Lv70", "Lv80", 2, R.drawable.rockwell, "When you reach this level, you can learn Basilosaurus Saddle.", NULL, 0, context);
            insertQuiz(db,3, "What is Beta Rockwell's level limit?", "Lv75", "Lv75", "Lv85", "Lv95", 2, R.drawable.rockwell, "When you reach this level, you can learn the vault.", NULL, 0, context);
            insertQuiz(db,2, "What is Alpha Rockwell's level limit?", "Lv70", "Lv80", "Lv90", "Lv100", 4, R.drawable.rockwell, "When you reach this level, you can learn the Titanosaur platform saddle.", NULL, 0, context);
            insertQuiz(db,2, "What's not Manticore's boss tribute?", "Ice Talon", "Fire Talon", "Poison Talon", "Lightning Talon", 1, R.drawable.manticore, "There are no cold claws.", NULL, 0, context);
            insertQuiz(db,3, "What's not the tek engram you get when you catch Gamma Manticore?", "Tek Gauntlets", "Tek Leggings", "Tek Generator", "Tek Helmet", 4, R.drawable.tek_replicator, "First body...", NULL, 0, context);
//          키블 문제
            insertQuiz(db,2, "Which is not an ingredient of Basic kibble?", "Dilophosaur Egg ", "Amarberry", "Pteranodon Egg", "Thatch", 3, R.drawable.basic_kibble, "Use only the smallest eggs.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Simple kibble?", "Raptor Egg", "Cooked meat", "Rockarrot", "Mejoberry", 2, R.drawable.simple_kibble, "Simple kibble should be made of fish.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Regular kibble?", "Fiber", "Ichthyornis Egg", "Savoroot", "Prime Meat jerky", 4, R.drawable.regular_kibble, "It takes the longest to get.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Superior kibble?", "Prime Meat jerky", "Citronal", "Fiber", "Rare Flower", 4, R.drawable.superior_kibble, "You can find it at the Giant Beaver Dam or near Ragnarok Blue Obelisk.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Exceptional kibble?", "Focal chili", "Fria Curry", "Fiber", "Mejoberry", 2, R.drawable.exceptional_kibble, "It is brown.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of ExtraOrdinary kibble?", "Giant Bee Honey", "Wyvern Egg", "Lazarus chowder", "Energy Brew", 4, R.drawable.extraordinary_kibble, "It fills our stamina.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Brew?", "Tintoberry", "Mejoberry", "Narcotic", "Water", 2,R.drawable.medical_brew, "Herbivorous dinosaurs' favorite berry.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Enduro Stew?", "Water", "Mejoberry × 10", "Rockarrot × 5", "Stimulant × 1", 4, R.drawable.rockwell_recipes_enduro_stew, "+stimulant.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of a focal chili?", "Mejoberry × 10", "Citronal 5", "Cooked meat", "Prime Cooked meat", 4, R.drawable.rockwell_recipes_focal_chili, "There is no need to use prime..", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Lazarus chowder?", "Mejoberry × 10", "Longrass × 5", "Stimulant × 2", "Savoroot × 5", 3, R.drawable.lazarus_chowder, "Lazarus chowder doesn't need stimberry or spark powder, but needs nacoberry and spoiled meat.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Calien Soup?", "Citronal × 5", "Amarberry × 20", "Tintoberry × 10", "Stimulant × 2", 3, R.drawable.calien_soup, "10 is not enough..", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Fria Curry?", "Longrass × 5", "Rockarrot × 5", "Azulberry × 20", "Mejoberry × 10", 4, R.drawable.fria_curry, "10 is enough for the berry that dinosaurs love..", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of Shadow Steak Saute?", "Narcotic × 10", "Rare Mushroom × 2", "Savoroot × 1", "Rockarrot × 1", 1, R.drawable.ark_logo, "8  spoiled meat + 40 Nacoberries are enough.", NULL, 0, context);
            insertQuiz(db,2, "Roasting time of one cooked meat?", "5 mins", "10 mins", "15 mins", "20 mins", 4, R.drawable.cooked_meat, "It's twice the time of raw meat decay time..", NULL, 0, context);
            insertQuiz(db,2, "How much time does it spend for a meat jerky to spoil?", "12 hours", "24 hours", "36 hours", "48 hours", 4, R.drawable.cooked_meat_jerky, "Beef jerky has a longer shelf life than expected.", NULL, 0, context);
            insertQuiz(db,2, "How much time does it spend for a raw prime meat to spoil?", "40 mins", "42 mins", "44 mins", "46 mins", 4, R.drawable.cooked_prime_meat, "'2760'", NULL, 0, context);
            insertQuiz(db,2, "How much time does it spend for a prime meat jerky to spoil?", "12 hours", "24 hours", "36 hours", "48 hours", 4, R.drawable.prime_meat_jerky, "Same as meat jerky decay time..", NULL, 0, context);
            insertQuiz(db,2, "How many jerkies can be stored in an item square?", "10", "20", "30", "40", 2, R.drawable.cooked_meat_jerky, "Half of raw meat.", NULL, 0, context);
            insertQuiz(db,2, "Which can't be made in the Fabricator?", "Rocket Launcher", "Metal Cliff Platform", "Polymer", "Generator", 2, R.drawable.fabricator, "This is an engram that you can learn from Aberration.", NULL, 0, context);
            insertQuiz(db,2, "Which is not an ingredient of scuba mask?", "Hide", "Thatch", "Crystal", "Polymer", 4, R.drawable.scuba_mask, "A lot comes from penguins and mantis.", NULL, 0, context);
            insertQuiz(db,3, "How many c4 are used to break the metal door?", "1", "2", "3", "4", 2, R.drawable.c4_charge, "A single c4 damage to metal is 3544.", NULL, 0, context);
            insertQuiz(db,3, "How many grenades are used to break the stone foundation?", "9", "10", "11", "12", 1, R.drawable.grenade, "Grenade damage into stone is 1838.", NULL, 0, context);
            insertQuiz(db,3, "How many cannons are used to break the tek ceiling?", "250", "300", "350", "400", 1, R.drawable.cannon, "One cannon damage into the tek ceiling is 40.", NULL, 0, context);
//            insertQuiz(db,3, "What is the damage of a bullet in an automatic turret?", "70", "80", "90", "100", 1, R.drawable.auto_turret, "Although you increase your hp 5 times, you can die by an auto turret shot.", NULL, 0, context);
            insertQuiz(db,3, "How much damage does C4 take on wood?", "11320", "11813", "12591", "12677", 2, R.drawable.c4_charge, "There is no way to give a hint. The answer is 11813.", NULL, 0, context);
            insertQuiz(db,3, "How much torpidity rises when you shoot tranquilizer dart?", "220", "221", "222", "223", 2, R.drawable.tranquilizer_dart, "It is 33 shots based on paralysis number 7293..", NULL, 0, context);
            insertQuiz(db,3, "How much torpidity rises when you shoot shocking tranquilizer dart?", "440", "442", "444", "446", 2, R.drawable.shocking_tranquilizer_dart, "It's twice as much as tranquilizer dart.", NULL, 0, context);
            insertQuiz(db,3, "Which is the best bait for fishing?", "Giant Bee Honey", "Leech Blood", "sap", "Raw meat", 1, R.drawable.fishing_rod, "I like the sweetest thing.", NULL, 0, context);
            insertQuiz(db,3, "Which is not an ingredient of the tek claw?", "Element", "Black Pearl", "Polymer", "Hide", 4, R.drawable.tek_claws, "You can get this material by killing dinosaurs.", NULL, 0, context);
            insertQuiz(db,3, "Which is not an ingredient of a tek replicator?", "Metal Ingot 5000", "Element 110", "Black Pearl 150", "Crystal 600", 2, R.drawable.tek_replicator, "110 is not enough.", NULL, 0, context);
            insertQuiz(db,3, "What is not the TheIsland Ovis spawn area?", "20.0, 30.0", "20.0, 50,0", "85.0, 83.0", "75.0, 65.0", 3, R.drawable.ovis, null, R.drawable.theisland_ovis_spawn, 0, context);
            insertQuiz(db,3, "What is the limit on the number of turrets in a zone?", "50", "100", "150", "200", 2, R.drawable.heavy_auto_turret, "You will be happy when you get this much score in the exam.", NULL, 0, context);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadQuiz4(SQLiteDatabase db, Context context){

        insertQuiz(db,1, "Which berry do dinosaurs prefer most?", "Amarberry", "Tintoberry", "Mejoberry", "Azulberry", 3, R.drawable.berries, "It's purple.", NULL, 0, context);

        //<공룡 크기 문제>
        insertQuiz(db,1, "Which dinosaur fits through dino gate?", "Giganotosaurus", "Brontosaurus", "Argentavis", "Pteranodon", 4, R.drawable.stonedinosaurgateway, "It can fly and has a long beak.", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur fits through dino gate?", "Managarmr", "Paraceratherium", "Gasbags", "Diplodocus", 3, R.drawable.stonedinosaurgateway, "It can fill its body with gas.", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur fits through dino gate?", "Tusotheuthis", "Griffin", "Dunkleosteus", "Mosasaurus", 1, R.drawable.stonedinosaurgateway, "It has many legs.", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur fits through dino gate?", "Rex", "Spinosaurus", "Allosaurus", "Carnotaurus", 4, R.drawable.stonedinosaurgateway, "It has short arms and has two small horns.", NULL, 0, context);
        insertQuiz(db,1, "Which dinosaur fits through dino gate?", "Yutyrannus", "Baryonyx", "Paraceratherium", "Rock Elemental", 2, R.drawable.stonedinosaurgateway, "It can stun other creatures in water.", NULL, 0, context);

        //<테이밍 문제>
        insertQuiz(db,3, "(Assuming Taming speed is 1) How many minutes will be spent when taming LV150 Beelzebufo with Raw Mutton?", "10 mins", "11 mins", "12 mins", "13 mins", 3, R.drawable.beelzebufo, "It is a multiple of 4.", NULL, 0, context);
        insertQuiz(db,3, "(Assuming Taming speed is 1) How many elements will be needed when taming LV150 Ferox?", "10", "11", "12", "13", 1, R.drawable.ferox, "It is a multiple of 5.", NULL, 0, context);
        insertQuiz(db,3, "How many cannon balls are needed when taming a Titanosaur?", "205", "206", "207", "208", 2, R.drawable.titanosaur, "2x2x2x2x2x2x2x2-50", NULL, 0, context);
        insertQuiz(db,3, "(Assuming Taming speed is 1)How many tranq arrows are needed when taming LV150 Parasaur?(Using a bow)", "15", "16", "17", "18", 3, R.drawable.ark_parasaur, "It's a prime number.", NULL, 0, context);
        insertQuiz(db,3, "(Assuming Taming speed is 1)How many tranq arrows are needed when taming LV150 Raptor?(Using a bow)", "20", "21", "22", "23", 2, R.drawable.raptor, "It is a multiple of 7.", NULL, 0, context);
        insertQuiz(db,2, "(Assuming Taming speed is 1)How many Sabertooth Salmon(1.5X) are needed when taming LV150 Otter?", "1", "2", "3", "4", 2, R.drawable.otter, "LV75 otter needs one Sabertooth Salom(1.5X)", NULL, 0, context);
        insertQuiz(db,3, "(Assuming Taming speed is 1)How many spoiled meats are needed when taming LV150 Vulture?", "10", "11", "12", "13", 3, R.drawable.vulture, "It is a multiple of 4.", NULL, 0, context);
        insertQuiz(db,2, "(Assuming Taming speed is 1)How many rare flowers are needed when taming LV1 Giant Bee?", "1", "3", "5", "7", 2, R.drawable.giantbee, "How many times do you have meals?", NULL, 0, context);
        insertQuiz(db,3, "(Assuming Taming speed is 1)How many Ascerbic Mushrooms are needed when taming LV150 Glowtail?", "25", "30", "35", "40", 1, R.drawable.glowtail, "150/6", NULL, 0, context);
        insertQuiz(db,3, "Which dinosaur is best when getting organic polymer?", "Direbear", "Moschops", "Direwolf", "Dimetrodon", 1, R.drawable.organicpolymer, "The more it runs, more faster is becomes.", NULL, 0, context);
        insertQuiz(db,3, "Your Procoptodon gets 300 square boxes of berries. In order to move, how much weight stat should your Procoptodon be?", "at least 1000", "at least 2000", "at least 3000", "at least 4000", 4, R.drawable.procoptodon, "Choose the highest.", NULL, 0, context);
        insertQuiz(db,3, "Where can you make Enforcer?", "Fabricator", "Smithy", "Tek Replicator", "Chemistry Bench", 3, R.drawable.enforcer, "If you clear the boss stage, you can ues it.", NULL, 0, context);
        insertQuiz(db,3, "What can't we get when killing a Deathworm?", "Organic Polymer", "Deathwrom Horn", "AnglerGel", "Chitin", 4, R.drawable.deathworm, "Well..Deathworm may not be an insect~", NULL, 0, context);

        //<기절 떨어지는 속도 문제>
        insertQuiz(db,3, "(According to DodoDex) How fast does Snow Owl's torpidity decrease?", "Low", "Average", "Fast", "Very Fast", 1, R.drawable.snowowl, "Knoooooooooooooooooock Out", NULL, 0, context);
        insertQuiz(db,3, "(According to DodoDex) How fast does Gasbags' torpidity decrease?", "Low", "Average", "Fast", "Very Fast", 1, R.drawable.gasbags, "Knoooooooooooooooooock Out", NULL, 0, context);
        insertQuiz(db,2, "(According to DodoDex) How fast does Giganotosaurus' torpidity decrease?", "Low", "Average", "Fast", "Very Fast", 4, R.drawable.giganotosaurus, "As fast as a rocket.", NULL, 0, context);
        insertQuiz(db,3, "(According to DodoDex) How fast does Doedicurus' torpidity decrease?", "Low", "Average", "Fast", "Very Fast", 3, R.drawable.doedicurus, "As fast as a sport car.", NULL, 0, context);
        insertQuiz(db,3, "(According to DodoDex) How fast does Ankylosaurus' torpidity decrease?", "Low", "Average", "Fast", "Very Fast", 1, R.drawable.ankylosaurus, "Knoooooooooooooooooock Out", NULL, 0, context);
        insertQuiz(db,3, "(According to DodoDex) How fast does Spinosaurus' torpidity decrease?", "Low", "Average", "Fast", "Very Fast", 3, R.drawable.spino, "As fast as a sport car.", NULL, 0, context);
        insertQuiz(db,3, "(According to DodoDex) How fast does Raptor's torpidity decrease?", "Low", "Average", "Fast", "Very Fast", 1, R.drawable.raptor, "Knoooooooooooooooooock Out", NULL, 0, context);

        //<색깔 문제>
        insertQuiz(db,1, "What is the color of Mejoberry?", "White", "Red", "Green", "Purple", 4, R.drawable.berries, "The last color of rainbow.", NULL, 0, context);
        insertQuiz(db,1, "What is the color of Amarberry?", "Yellow", "Black", "Blue", "Orange", 1, R.drawable.berries, "The third color of rainbow.", NULL, 0, context);
        insertQuiz(db,1, "What is the color of Azulberry?", "Yellow", "Brown", "Blue", "Pink", 3, R.drawable.berries, "Similar color with sky.", NULL, 0, context);
        insertQuiz(db,1, "What is the color of Nacoberry?", "Red", "Black", "Gray", "White", 2, R.drawable.berries, "The color of night.", NULL, 0, context);
        insertQuiz(db,1, "What is the color of Stimberry?", "White", "Yellow", "Red", "Black", 1, R.drawable.berries, "The color of Snow.", NULL, 0, context);
        insertQuiz(db,1, "What is the color of Tintoberry?", "Yellow", "Red", "Pink", "Purple", 2, R.drawable.berries, "The color of blood.", NULL, 0, context);

        //<채집 효율>
        insertQuiz(db,2, "Which is best when gathering raw meat?", "Metal Pick", "Rex", "Mosasaurus", "Giganotosaurus", 4, R.drawable.raw_meat, "The most powerful dinosaur in ARK World.", NULL, 0, context);
        insertQuiz(db,3, "Which dinosaur is best when gathering black pearl?", "Phoenix", "Otter", "Gacha", "Angler", 3, R.drawable.black_pearl, "You can easily get black pearl at home using this dinosaur.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is best when gathering cementing paste?", "Achatina", "Beelzebufo", "Giant Beaver Dam", "Meganeura", 2, R.drawable.cementing_paste, "croak..croak...", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering chitin?", "Megatherium", "Metal Pick", "Sabertooth", "Mantis", 1, R.drawable.chitin, "It gets buff when killing insects,", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering crystal?", "Metal Pick", "Mantis", "Ankylosaurus", "Gacha", 1, R.drawable.crystal, "Human", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering fiber?", "Moschops", "Metal Sickle", "Gigantopithecus", "Direbear", 2, R.drawable.ark_fiber, "Human", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering flint?", "Metal Pick", "Ankylosaurus", "Rex", "Rock Elemental", 1, R.drawable.ark_flint, "Human", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering hide?", "Chainsaw", "Giganotosaurs", "Direwolf", "Thylacoleo", 1, R.drawable.ark_hide, "Dinosaur < Human", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering metal?", "Diplodocus", "Rex", "Phoenix", "Magmasaur", 4, R.drawable.ark_metal, "We can see this dinosaur in Genesis?", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering obsidian?", "Mantis", "Doedicurus", "Gacha", "Ankylosaurus", 2, R.drawable.obsidian, "It can hide inside its shell.", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering oil?", "Dunkleosteus", "Tusotheuthis", "Dung Beetle", "Gacha", 4, R.drawable.oil, "You can easily get oil at home using this dinosaur.", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering organic polymer?", "Wooden Club", "ChainSaw", "Gacha", "Pelagornis", 1, R.drawable.organicpolymer, "If you attack someone with this, someone's torpidity goes up.", NULL, 0, context);
        insertQuiz(db,3, "Which is best when gathering rare flower?", "Therizinosaurus", "Moschops", "Managarmr", "Megachelon", 4, R.drawable.rare_flower, "turtle...", NULL, 0, context);
        insertQuiz(db,3, "Which is best when gathering rare mushroom?", "Therizinosaurus", "Moschops", "Megachelon", "Iguanodon", 3, R.drawable.rare_mushroom, "turtle...", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering raw fish meat?", "Metal Hatchet", "Mosasaurus", "Spinosaurus", "Baryonyx", 1, R.drawable.raw_fish_meat, "Humans are better than dinosaurs.", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering raw prime fish meat?", "Mosasaurus", "Metal Sickle", "Megachelon", "Spinosaurus", 1, R.drawable.raw_prime_fish_meat, "It has a long neck.", NULL, 0, context);
        insertQuiz(db,3, "Which is best when gathering raw prime meat", "Riper King", "Rex", "Wyvern", "Crystal Wyvern", 1, R.drawable.raw_prime_meat, "It can breathe under the ground.", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering silica pearls?", "Gacha", "Otter", "Phoenix", "Angler", 4, R.drawable.silica_pearls, "It is a very ugly fish.", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering stone?", "Rock Elemental", "Mantis", "Doedicurus", "Magamasaurus", 3, R.drawable.ark_stone, "It has a chunk on its tail.", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering thatch?", "Brontosaurus", "Megaloceros", "Sabertooth", "Megatherium", 1, R.drawable.ark_thatch, "The largest among the choices.", NULL, 0, context);
        insertQuiz(db,2, "Which is best when gathering wood?", "Therizinosaurus", "Roll Rat", "Thorny Dragon", "Pachyrhinosaurus", 1, R.drawable.ark_wood, "It has long nails.", NULL, 0, context);
        insertQuiz(db,3, "Which is best when gathering sulfur?", "Mantis", "Phoenix", "Rock Elemental", "Ankylosaurus", 1, R.drawable.sulfur, "It can change weapons.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is best when gathering fiber?", "Therizinosaurus", "Gacha", "Pachyrhinosaurus", "Gigantopithecus", 2, R.drawable.ark_fiber, "Passive Taming", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur does not provide sauropod vertebra, which is the requirement of boss arenas?", "Diplodocus", "Brontosaurus", "Allosaurus", "Titanosaur", 3, R.drawable.sauropod_vertebra, "Dinosaurs with long tail and neck, and big body provides sauropod vertebra.", NULL, 0, context);
        insertQuiz(db,3, "Which dinosaur can gather sap?", "Thorny Dragon", "Therizinosaurus", "Archaeopteryx", "Dodo", 3, R.drawable.sap, "It can climb walls.", NULL, 0, context);
        insertQuiz(db,3, "Which dinosaur is best when gathering sand?", "Rock Elemental", "Gacha", "Phoenix", "Mantis", 4, R.drawable.sand, "It can change its weapon.", NULL, 0, context);
        insertQuiz(db,3, "Which dinosaur is best when gathering salt?", "Gacha", "Phoenix", "Mantis", "Rock Elemental", 3, R.drawable.salt, "It can change its weapon.", NULL, 0, context);

        //무게 감소
        insertQuiz(db,2, "Which dinosaur decreases the weight of metal ingot most?", "Argentavis", "Magmasaur", "Ravager", "Ankylosaurus", 2, R.drawable.metal_ingot, "It lay eggs.", NULL, 0, context);
        insertQuiz(db,3, "Which dinosaur decreases the weight of black pearl?", "Dunkleosteus", "Magmasaur", "Ravager", "Manta", 1, R.drawable.black_pearl, "It lives in water, and is slow.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur decreases the weight of cementing paste?", "Beelzebufo", "Achatina", "Equus", "Parasaur", 3, R.drawable.cementing_paste, "You can passive-tame it by Rockarrot.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur doesn't decrease the weight of crystal?", "Dunkleosteus", "Ravager", "Argentavis", "Ankylosaurus", 4, R.drawable.crystal, "It has horns on its back.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur doesn't decrease the weight of fiber?", "Ravager", "Argentavis", "Castoroides", "Thorny Dragon", 2, R.drawable.ark_fiber, "It flies.", NULL, 0, context);
        insertQuiz(db,3, "Which dinosaur doesn't decrease the weight of metal ingot?", "Magmasaur", "Ankylosaurus", "Gacha", "Mosasaurus", 4, R.drawable.metal_ingot, "It can carry platfrom saddle.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur doesn't decrease the weight of obsidian?", "Dunkleosteus", "Tusoteuthis", "Argentavis", "Ravager", 2, R.drawable.obsidian, "Squid...", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur lower the weight of oil?", "Argentavis", "Ankylosaurus", "Dunkleosteus", "Gacha", 3, R.drawable.oil, "It can gather metal in the water.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur lower the weight of organic polymer?", "Argentavis", "Ravager", "Equus", "Moschops", 1, R.drawable.organicpolymer, "It is flying dinosaur.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur lower the weight of stone the most?", "Basilisk", "Ravager", "Thorny Dragon", "Argentavis", 1, R.drawable.ark_stone, "It can hide inside the ground.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur doesn't lower the weight of thatch?", "Thorny Dragon", "Argentavis", "Ravager", "Castoroides", 2, R.drawable.ark_thatch, "It can carry a human and average-size dinosaurs with right-click.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur lower the weight of wood the most?", "Roll Rat", "Mammoth", "Thorny Dragon", "Gacha", 1, R.drawable.ark_wood, "It can move while spinning.", NULL, 0, context);

        //(10.04)
        //자원 이름
        insertQuiz(db,1, "What is the name of this resource?", "Absorbent Substrate", "Allosaurus Brain", "Ambergris", "Angler Gel", 1, R.drawable.absorbent_substrate, "Dropped by Tusoteuthis.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Allosaurus Brain", "Ambergris", "Angler Gel", "Ammonite Bile", 1, R.drawable.allosaurus_brain, "Dropped by Allosaurus.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Ammonite Bile", "Ambergris", "Angler Gel", "Argentavis Talon", 2, R.drawable.ambergris, "Magmasaur food.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Basilosaurus Blubber", "Ammonite Bile", "Angler Gel", "Argentavis Talon", 2, R.drawable.ammonite_bile, "Dropped by Ammonite.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Bio Toxin", "Basilosaurus Blubber", "Angler Gel", "Argentavis Talon", 3, R.drawable.angler_gel, "Dropped by Angler.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Basilosaurus Blubber", "Berries", "Argentavis Talon", "Black Pearl", 3, R.drawable.argentavis_talon, "Dropped by argentavis.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Cementing Paste", "Cactus Sap", "Black Pearl", "Bio Toxin", 4, R.drawable.bio_toxin, "Dropped by Cnidaria.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Charcoal", "Cementing Paste", "Cactus Sap", "Black Pearl", 4, R.drawable.black_pearl, "Dropped by Ammonite, Deathworm, Eurypterid, Trilobite, Tusoteuthis.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Cactus Sap", "Cementing Paste", "Charcoal", "Chitin", 1, R.drawable.cactus_sap, "It has water.", NULL, 0, context);
        insertQuiz(db,1, "What is the name of this resource?", "Chitin", "Charcoal", "Clay", "Corrupted Nodule", 2, R.drawable.charcoal, "Burnt wood.", NULL, 0, context);

        //키블 색
        insertQuiz(db,2, "What is the color of Basic Kibble?", "White", "Blue", "Yellow", "Sky blue", 1, R.drawable.basic_kibble, "The color of snow", NULL, 0, context);
        insertQuiz(db,2, "What is the color of Simple Kibble?", "Red", "Green", "Yellow", "White", 2, R.drawable.simple_kibble, "The color of grass.", NULL, 0, context);
        insertQuiz(db,2, "What is the color of Regular Kibble?", "Light Green", "Sky blue", "Yellow", "Blue", 4, R.drawable.regular_kibble, "The color of ocean.", NULL, 0, context);
        insertQuiz(db,2, "What is the color of Superior Kibble?", "Purple", "Black", "Yellow", "Navy", 1, R.drawable.superior_kibble, "The color of grape.", NULL, 0, context);
        insertQuiz(db,2, "What is the color of Exceptional Kibble?", "Pink", "Blue", "Yellow", "Sky blue", 3, R.drawable.exceptional_kibble, "The color of bananas.", NULL, 0, context);
        insertQuiz(db,2, "What is the color of Extraordinary Kibble?", "Light Green", "Sky blue", "Silver", "Gold", 2, R.drawable.extraordinary_kibble, "The color of clear sky.", NULL, 0, context);

        //공룡 타입(비행, 지상 등등)
        insertQuiz(db,1, "Which dinosaur can't fly?", "Argentavis", "Astrocetus", "Insect Swarm", "Angler", 4, R.drawable.ark_logo, null, R.drawable.angler, 0, context);
        insertQuiz(db,1, "Which dinosaur can't fly?", "Desert Titan", "Dimorphodon", "Gasbags", "Basilosaurus", 4, R.drawable.ark_logo, null, R.drawable.basilosaurus, 0, context);
        insertQuiz(db,1, "Which dinosaur can't fly?", "Lymantria", "Phoenix", "Ammonite", "Quetzal", 3, R.drawable.ark_logo, null, R.drawable.quetzal, 0, context);
        insertQuiz(db,1, "Which dinosaur can't fly?", "Megameura", "Onyc", "Hesperornis", "Vulture", 3, R.drawable.ark_logo, null, R.drawable.hesperornis, 0, context);
        insertQuiz(db,1, "Which dinosaur can't fly?", "Wyven", "Coelacanth", "Snow Owl", "Gasbags", 2, R.drawable.ark_logo, null, R.drawable.coelacanth, 0, context);
        insertQuiz(db,1, "Which dinosaur doesn't live in the water?", "Leedsichthys", "Alloasaurus", "Diplocauius", "Eurypterid", 2, R.drawable.ark_logo, null, R.drawable.allosaurus, 0, context);
        insertQuiz(db,1, "Which dinosaur doesn't live in the water?", "Basilisk", "Liopleurodon", "Mosasaurus", "Megalodon", 1, R.drawable.ark_logo, null, R.drawable.basilisk, 0, context);
        insertQuiz(db,1, "Which dinosaur doesn't live in the water?", "Beelzebufo", "Lamprey", "Otter", "Tusoteuthis", 1, R.drawable.ark_logo, null, R.drawable.beelzebufo, 0, context);
        insertQuiz(db,1, "Which dinosaur doesn't live in the water?", "Bulbdog", "Eurypterid", "Cnidaria", "Baryonyx", 1, R.drawable.ark_logo, null, R.drawable.bulbdog, 0, context);
        insertQuiz(db,1, "Which dinosaur doesn't live in the water?", "Electrophorus", "Arthropluera", "Dunkleosteus", "Ammonite", 2, R.drawable.ark_logo, null, R.drawable.arthropluera, 0, context);
        insertQuiz(db,2, "Which is not a boss?", "Attack Drone", "Broodmother Lysrix", "Yeti", "Corrupted Master Controller", 3, R.drawable.ark_logo, null, R.drawable.yeti, 0, context);
        insertQuiz(db,2, "Which is not a boss?", "Crystal Wyvern", "Deathworm", "Woolly Rhino", "Defense Unit", 3, R.drawable.ark_logo, null, R.drawable.woollyrhino, 0, context);
        insertQuiz(db,2, "Which is not a boss?", "Desert Titan", "Dragon", "Trilobite", "Forest Titan", 3, R.drawable.ark_logo, null, R.drawable.trilobite, 0, context);
        insertQuiz(db,2, "Which is not a boss?", "Ice Titan", "Thorny Dragon", "King Titan", "Manticore", 2, R.drawable.ark_logo, null, R.drawable.thornydragon, 0, context);
        insertQuiz(db,2, "Which is not a boss?", "Megapithecus", "Moeder", "Overseer", "Thylacoleo", 4, R.drawable.ark_logo, null, R.drawable.thylacoleo, 0, context);

        //레벨당 배울 수 있는 엔그램 문제
        insertQuiz(db,1, "Which is not the engram learned at level 1?", "Note", "stone Pick", "Torch", "stone Hatchet", 4, R.drawable.ark_logo, "We usually use it when cutting woods.", NULL, 0, context);
        insertQuiz(db,1, "Which is not an engram that can be learned at level 4?", "Thatch Foundation", "stone Foundation", "Thatch Ceiling", "Storage Box", 2, R.drawable.ark_logo, "We learn thatch items first, and then stone items.", NULL, 0, context);

        //스프 재료 문제
        insertQuiz(db,2, "Which is not the material of the Mindwipe Tonic?", "Narcotics", "Stimulant", "Rare Flower", "Raw Prime meat", 4, R.drawable.ark_logo, "Meats should be cooked.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of the Broth of Enlightenment?", "Woolly Rhino Horn", "Rockarrot", "Longrass", "Narcotics", 4, R.drawable.broth_of_enlightenment, "If someone eats it, his(her) torpidity goes up.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of the Calien Soup?", "Stimulant", "Citronal", "Rockarrot", "Tintoberry", 3, R.drawable.calien_soup, "Usually horses like this food.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of the Enduro Stew?", "Stimulant", "Savoroot", "Prime Cooked Meat", "Rockarrot", 3, R.drawable.rockwell_recipes_enduro_stew, "We don't have to use high quality food.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of the Focal Chili?", "Cooked Meat", "Stimulant", "Citronal", "Azulberry", 2, R.drawable.rockwell_recipes_focal_chili, "It is made of Sparkpowder and Stimberries.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of the Sweet Vegetable Cake?", "Giant Bee Honey", "Narcotics", "Sap", "Stimulant", 2, R.drawable.sweet_vegetable_cake, "If someone eats it, his(her) torpidity goes up.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of the Cactus Broth?", "Cactus Sap", "Narcotics", "Mejoberry", "Azulberry", 2, R.drawable.cactus_broth, "If someone eats it, his(her) torpidity goes up.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of the Bug Repellant?", "Stimulant", "Pelt", "Citronal", "Rockarrot", 1, R.drawable.bug_repellant, "It is made of Sparkpowder and Stimberries.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of the Lesser Antidote?", "Stimulant", "Rare Flower", "Rare Mushroom", "Deathworm Horn", 1, R.drawable.lesser_antidote, "It is made of Sparkpowder and Stimberries.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of the Beer Liquid?", "Wood", "Thatch", "Berries", "Water", 1, R.drawable.beer_liquid, "The hardest one among the examples.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of the Re-Fertilizer?", "Rare Mushroom", "Rare Flower", "Fertilizer", "Rock", 4, R.drawable.re_fertilizer, "It is very hard", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of the Mushroom Brew?", "Aggeravic Mushroom", "Aquatic Mushroom", "Sap", "rare Mushroom", 4, R.drawable.mushroom_brew, "Not rare~", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of the Water Jar?", "Cementing Paste", "Water", "Hide", "Crystal", 2, R.drawable.water_jar, "Imagine you are making a real bottle. What will not be used?", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of the Canteen?", "Polymer", "Metal Ingot", "Thatch", "Cementing Paste", 3, R.drawable.canteen, "The most common items among the examples.", NULL, 0, context);

        //염료 재료 문제
        insertQuiz(db,2, "Which is not the ingredient of black dye?", "Narcoberry", "Water", "Charcoal", "Wood", 4, R.drawable.black_dye ,"Not black.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of Slate dye?", "Narcoberry", "Stimberry", "Sparkpowder", "Charcoal", 4, R.drawable.slate_dye, "We don't need burnt woods.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of Silver dye?", "Narcoberry", "Water", "Metal", "Gunpowder", 3, R.drawable.silver_dye, "The hardest among examples.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of White dye?", "Stimberry", "Charcoal", "Thatch", "Water", 3, R.drawable.white_dye, "It is yellow.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of Red dye?", "Tintoberry", "Blood", "Charcoal", "Water", 2, R.drawable.red_dye, "Without it, humans will die.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of Orange dye?", "Amarberry", "Gunpowder", "Tintoberry", "Charcoal", 2, R.drawable.orange_dye, "It is made of sparkpowder and charcoal.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of Cantaloupe dye?", "Azulberry", "Tintoberry", "Stimberry", "Sparkpowder", 1, R.drawable.cantaloupe_dye, "It is blue.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of Yellow dye?", "Thatch", "Charcoal", "Amarberry", "Water", 1, R.drawable.yellow_dye, "Weak tree.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of Green dye?", "Amarberry", "Azulberry", "Charcoal", "Meat", 4, R.drawable.green_dye, "We can get this from animals.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of Cyan dye?", "Amarberry", "Azulberry", "Sparkpowder", "Stimberry", 4, R.drawable.cyan_dye, "It is white.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of Purple dye?", "Azulberry", "Tintoberry", "Amarberry", "Charcoal", 3, R.drawable.purple_dye, "It is blue.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of Brick dye?", "Tintoberry", "Narcoberry", "Gunpowder", "Sparkpowder", 3, R.drawable.brick_dye, "it is made of Sparkpowder and Charcoal.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of Tangerine dye?", "Amarberry", "Sparkpowder", "Tintoberry", "Gunpowder", 2, R.drawable.tangerine_dye, "It is made of stone and flint.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of Tan dye?", "Amarberry", "Sparkpowder", "Stimberry", "Gunpowder", 2, R.drawable.tan_dye, "It is made of stone and flint", NULL, 0, context);
        insertQuiz(db,2, "Which is not the ingredient of Parchment dye?", "Sparkpowder", "Stimberry", "Amarberry", "Gunpowder", 1, R.drawable.parchement_dye, "It is yellow.", NULL, 0, context);

        //하늘을 나는 공룡이 낚을 수 있는 것들이 아닌 것은?
        insertQuiz(db,2, "Which dinosaur is not carryable by Tapejara?", "Ferox", "Hyaenodon", "Shinehorn", "Ovis", 4, R.drawable.tapejara, "Its meat is good at taming other creatures.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is not carryable by Pteanodon?", "Blubdog", "Kairuku", "Compy", "Achatina", 4, R.drawable.pteranodon, "It creates cementing paste.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is not carryable by Tropeognathus?", "Trilobite", "Dodo", "Oviraptor", "Human", 3, R.drawable.tropeognathus, "It helps other creatures lay eggs.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is not carryable by Griffin?", "Otter", "Microraptor", "Shinehorn", "Glowtail", 3, R.drawable.griffin, "It shines.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is not carryable by Argentavis?", "Thorny Dragon", "Carnotaurus", "Sabertooth", "Daeodon", 2, R.drawable.argentavis, "Its attack has a bleeding effect at constant probability.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is not carryable by Crystal Wyvern?", "Megatherium", "Bloodstalker", "Gasbags", "Mantis", 2, R.drawable.crystalwyvern, "It looks like a spider.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is not carryable by Wyvern?", "Direbear", "Purlovia", "Sarco", "Yeti", 1,R.drawable.wyvern, "On Aberration, by harvesting the plants on the way down to the Rock Drake nests, it can gather organic polymers.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is not carryable by Quetzal?", "Bloodstalker", "Therizinosaurus", "Allosaurus", "Ravager", 4,R.drawable.quetzal, "Walking Argentavis.", NULL, 0, context);
        //땅,바다에 있는 공룡이 낚을 수 있는 것이 아닌것
        insertQuiz(db,2, "Which dinosaur is not carryable by Tusoteuthis?", "Ichthyosaurus", "Otter", "Sarco", "Yeti", 2,R.drawable.tusoteuthis, "You can hang it around your neck.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is not carryable by Karkinos?", "Ankylosaurus", "Otter", "Giant Bee", "Vulture", 4,R.drawable.karkinos, "It drops spoiled meat.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is not carryable by Megalosaurus?", "Piranha", "Daeodon", "Purlovia", "Argentavis", 3,R.drawable.megalosaurus, "It drops spoiled meat.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is not carryable by Kaprosuchus?", "Otter", "Ichthyosaurus", "Ferox", "Araneo", 1,R.drawable.kaprosuchus, "You can hang it around your neck.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is not carryable by Procoptodon?", "Sabertooth", "Daeodon", "Yeti", "Megaloceros", 4,R.drawable.procoptodon, "It has a fancy antler on its head.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is not carryable by human?", "Archaeopteryx", "Kairuku", "Oviraptor", "Achatina", 3,R.drawable.ark_logo, "It helps other creatures lay eggs.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is not carryable by Yeti?", "Dodo", "Kairuku", "Arrchaeoteryx", "Achatina", 4, R.drawable.yeti, "It creates cementing paste.", NULL, 0, context);

        //벽을 파괴 할 수 있는 공룡
        insertQuiz(db,2, "Which is not damaged by Astrocetus?", "Thatch", "Wood", "stone", "Metal", 4,R.drawable.astrocetus, "The hardest among the examples.", NULL, 0, context);
        insertQuiz(db,2, "Which is not damaged by Leedsichthys?", "Thatch", "Adobe", "stone", "Metal", 2,R.drawable.leedsichthys, "Leedsichthys seems to be able to break everything, but it can't break one thing that can be learn in Scorched Earth.", NULL, 0, context);
        insertQuiz(db,2, "Which is not damaged by Chalicotherium?", "Thatch", "Wood", "Adobe", "stone", 4,R.drawable.chalicotherium, "Chalicotheium can't break one thing that can be learn in Scorched Earth.", NULL, 0, context);
        insertQuiz(db,2, "Which is not damaged by Therizinosaurus?", "Wood", "Adobe", "stone", "Metal", 4,R.drawable.therizinosaurus, "The hardest one among examples.", NULL, 0, context);
        insertQuiz(db,1, "Which is not damaged by Arthropluera?", "stone", "Metal", "Tek", "None", 4,R.drawable.arthropluera, "Arthropluera's poision is very toxic.", NULL, 0, context);
        insertQuiz(db,2, "Which is not damaged by Titanosaur?", "stone", "None", "Metal", "Adobe", 2,R.drawable.titanosaur, "This dinosaur can break everything.", NULL, 0, context);
        //볼라에 맞는 공룡은?
        insertQuiz(db,2, "Which dinosaur is affected by bola?", "Equus", "Gallimimus", "Griffin", "Argentavis", 1,R.drawable.bola, "You can tame this creature with rockarrots.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by bola?", "Pteranodon", "Thlyacoleo", "Managarmr", "Mammoth", 1,R.drawable.bola, "When you press C key, you can use special skill.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by bola?", "Megalosaurus", "Raptor", "Velonasaur", "Woolly Rhino", 2,R.drawable.bola, "This dinosaur can carry a human.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by bola?", "Therizinosaurus", "Ravager", "Deinoychus", "Basilisk", 2,R.drawable.bola, "This dinosaur decreases items' weight.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by bola?", "Titanoboa", "Wyvern", "Baryonyx", "Magmasaur", 3,R.drawable.bola, "Its tail attack can stun creatures.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by bola?", "Gacha", "Rex", "Iguanodon", "Mammoth", 3,R.drawable.bola, "It can pick seeds out of the fruits.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by bola?", "Megatherium", "Thorny Dragon", "Morellatops", "Beelzebufo", 4,R.drawable.bola, "croak croak..", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by bola?", "Stegosaurus", "Spino", "Yutyrannus", "Glowtail", 4,R.drawable.bola, "Its body shines.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by bola?", "Lymantria", "Karkinos", "Chalicotherium", "Qeutzal", 1,R.drawable.bola, "It is an insect.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by bola?", "Enforcer", "Purlovia", "Paraceratherium", "Triceratops", 1,R.drawable.bola, "It is a machine.", NULL, 0, context);

        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Equus", "Sabertooth", "Direwolf", "Raptor", 1,R.drawable.plant_y_trap, "You can tame this creature with rockarrots.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Tapejara", "Pteranodon", "Pulmonoscorpius", "Megaloceros", 1,R.drawable.plant_y_trap, "We can use Tek saddle on this animal.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Phiomia", "Dodo", "Troodon", "Beelzebufo", 2,R.drawable.plant_y_trap, "The weakest creature in the ARK world.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Argentavis", "Vulture", "Pegomastax", "Pachy", 2,R.drawable.plant_y_trap, "It drops spoiled meat.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Thylacoleo", "Ovis", "Ravager", "Argentavis", 3,R.drawable.plant_y_trap, "This dinosaur lowers the weight of items.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Dimorphodon", "Lymantria", "Baryonyx", "Microraptor", 3,R.drawable.plant_y_trap, "Its tail attack can stun creatures.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Oviraptor", "Pelagornis", "Titanoboa", "Daeodon", 4,R.drawable.plant_y_trap, "It heals other creatures.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Dilophosaur", "Dung beetle", "Enforcer", "Bulbdog", 4,R.drawable.plant_y_trap, "It shines.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Ferox", "Castoroides", "Wyvern", "Crystal Wyvern", 1,R.drawable.plant_y_trap, "If we feed element to it, its body gets bigger.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Managarmr", "Allosaurus", "Gacha", "Magmasaur", 1,R.drawable.plant_y_trap, "It can jump three times and dash one time.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Mammoth", "Snow owl", "Megatherium", "Stegosaurus", 2,R.drawable.plant_y_trap, "It can detect heat.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Arthropluera", "Megalosaurus", "Purlovia", "Quetzal", 2,R.drawable.plant_y_trap, "It has two fancy horns.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Achatina", "Kairuku", "Velonasaur", "Compy", 3,R.drawable.plant_y_trap, "When flying creatures attacked by this dinosaur, flying creatures' stamina decreases.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Giganotosaurus", "Gasbags", "Wooly Rhino", "Phoenix", 3,R.drawable.plant_y_trap, "The more gauge it gets, the more powerful attack is possible.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Yeti", "Procoptodon", "Bloodstalker", "Deinoychus", 4,R.drawable.plant_y_trap, "It can attack other creatures while sticking to them.", NULL, 0, context);
        insertQuiz(db,2, "Which dinosaur is affected by Plant Species Y Trap?", "Piranha", "Mantis", "Roll Rat", "Gallimimus", 4,R.drawable.plant_y_trap, "Two humans can ride on its saddle.", NULL, 0, context);

    }

    public void loadQuiz5(SQLiteDatabase db, Context context){
        //4-1차 문제 추가
        insertQuiz(db,1, "(In Official Server) How much Health stat increase when you level your Health stat up?", "5%", "10%", "15%", "20%", 2 ,R.drawable.ark_logo, "The month of Halloween.", NULL, 0, context);
        insertQuiz(db,1, "(In Official Server) How much Stamina stat increase when you level your Stamina stat up?", "5%", "10%", "15%", "20%", 2 ,R.drawable.ark_logo, "The month of Halloween.", NULL, 0, context);
        insertQuiz(db,1, "(In Official Server) How much Oxygen stat increase when you level your Oxygen stat up?", "5%", "10%", "15%", "20%", 4 ,R.drawable.ark_logo, "divide 100 into 5", NULL, 0, context);
        insertQuiz(db,1, "(In Official Server) How much Food stat increase when you level your Food stat up?", "10%", "20%", "30%", "40%", 1 ,R.drawable.ark_logo, "Usually use this number when you count down.", NULL, 0, context);
        insertQuiz(db,1, "(In Official Server) How much Water stat increase when you level your Water stat up?", "10%", "15%", "20%", "25%", 1 ,R.drawable.ark_logo, "Usually use this number when you count down.", NULL, 0, context);
        insertQuiz(db,1, "(In Official Server) How much Weight stat increase when you level your Weight stat up?", "2%", "5%", "7%", "10%", 4 ,R.drawable.ark_logo, "How many fingers do you have?", NULL, 0, context);
        insertQuiz(db,1, "(In Official Server) How much Melee Damage stat increase when you level your Melee Damage stat up?", "5%", "10%", "15%", "20%", 1 ,R.drawable.ark_logo, "The number of toes in one foot.", NULL, 0, context);
        insertQuiz(db,1, "(In Official Server) How much Movement Speed stat increase when you level your Movement Speed stat up?", "0.5%", "1%", "1.5%", "2%", 3 ,R.drawable.ark_logo, "You can't make 100 with this number.", NULL, 0, context);
        insertQuiz(db,1, "(In Official Server) How much Crating Skill stat increase when you level your Crafting Skill stat up?", "5%", "10%", "15%", "20%", 2 ,R.drawable.ark_logo, "How many fingers do you have?", NULL, 0, context);
        insertQuiz(db,1, "(In Official Server) What is the maximum level?", "100", "105", "110", "115", 2 ,R.drawable.ark_logo, "100+5", NULL, 0, context);

        //4-2차 문제 추가
        insertQuiz(db,2, "Which is not an Extinction Engram?", "Small Taxidermy Base", "Taxidermy Tool", "Delivery Crate", "Glow stick", 4 ,R.drawable.ark_extinction, "It glows.", NULL, 0, context);
        insertQuiz(db,2, "Which is not an Extinction Engram?", "Medium Taxidermy Base", "Dino Leash", "Velonasaur saddle", "Fish Net", 4 ,R.drawable.ark_extinction, "By using it, you can catch the fish.", NULL, 0, context);
        insertQuiz(db,2, "Which is not an Extinction Engram?", "Large Taxidermy Base", "Gacha saddle", "Portable Rope Ladder", "Gasbag saddle", 3 ,R.drawable.ark_extinction, "You cam climb it.", NULL, 0, context);
        insertQuiz(db,2, "Which is not an Extinction Engram?", "Unstable Element", "Unstable Element Shard", "Shag Rug", "Snow Owl saddle", 3 ,R.drawable.ark_extinction, "You can get its ingredients by killing Ovis.", NULL, 0, context);
        insertQuiz(db,2, "Which is not an Extinction Engram?", "Managarmr saddle", "Small Wood Elevator Platform", "Tek Bridge", "Tek Gravity Grenade", 2 ,R.drawable.ark_extinction, "It is helpful when carrying some stuffs.", NULL, 0, context);
        insertQuiz(db,2, "Which is not an Extinction Engram?", "Unassembled Mek", "large Wood Elevator Platform", "M.D.S.M.", "rocket pod", 2 ,R.drawable.ark_extinction, "It is helpful when carrying some stuffs.", NULL, 0, context);
        insertQuiz(db,2, "Which is not an Extinction Engram?", "Zip-Line Anchor", "M.R.L.M.", "M.S.C.M.", "Rocket", 1 ,R.drawable.ark_extinction, "Ravager can move by using it.", NULL, 0, context);
        insertQuiz(db,2, "Which is not a Scorched Earth Engram?", "Climbing Pick", "Preserving Salt", "Clay", "tent", 1 ,R.drawable.ark_scorched_earth, "You can climb walls by using it.", NULL, 0, context);
        insertQuiz(db,2, "Which is not a Scorched Earth Engram?", "boomerang", "Morellatops saddle", "Oil Jar", "Stone Cliff Platform", 4 ,R.drawable.ark_scorched_earth, "If you have it, you can build things on cliffs.", NULL, 0, context);
        insertQuiz(db,2, "Which is not a Scorched Earth Engram?", "Water Well", "Clay Foundation", "Clay Wall", "Metal Cliff Platform", 4 ,R.drawable.ark_scorched_earth, "If you have it, you can build things on cliffs.", NULL, 0, context);
        insertQuiz(db,2, "Which is not a Scorched Earth Engram?", "Clay ladder", "wipe", "Gas Collector", "Propellant", 3 ,R.drawable.ark_scorched_earth, "It extracts gas.", NULL, 0, context);
        insertQuiz(db,2, "Which is not a Scorched Earth Engram?", "Desert Cloth Shirt", "mirror", "pliers", "Clay dinogate", 3 ,R.drawable.ark_scorched_earth, "Tool.", NULL, 0, context);
        insertQuiz(db,2, "Which is not a Scorched Earth Engram?", "Lymantria saddle", "Ravager saddle", "Thorny Dragon saddle", "Mantis saddle", 2 ,R.drawable.ark_scorched_earth, "It is a saddle of the dinosaur which reduces the weight.", NULL, 0, context);
        insertQuiz(db,2, "Which is not a Scorched Earth Engram?", "Wind Turbine", "Glider Suit Skin", "Chainsaw", "Oil Pump", 2 ,R.drawable.ark_scorched_earth, "If you have it, you can fly.", NULL, 0, context);
        insertQuiz(db,2, "Which is not a Scorched Earth Engram?", "Karkinos saddle", "Rock Golem saddle", "Flamethrower Ammo", "Flamethrower", 1 ,R.drawable.ark_scorched_earth, "Crocodile.", NULL, 0, context);
        insertQuiz(db,2, "Which is not a Scorched Earth Engram?", "Hazard Suit Gloves", "Cluster Grenade", "Rocket Homing Missile", "Water Well", 1 ,R.drawable.ark_scorched_earth, "With this item, you can protect your body from radioactivity.", NULL, 0, context);
        insertQuiz(db,2, "Which is a Abberation Engram?", "Phiomia saddle", "Blood Extraction Syringe", "simple bed", "Charge Battery", 4 ,R.drawable.ark_aberration, "With this item, you can use turret without Electrical Generator.", NULL, 0, context);
        insertQuiz(db,2, "Which is a Abberation Engram?", "stone arrow", "Paintbrush", "Standing torch", "Rock Drake saddle", 4 ,R.drawable.ark_aberration, "This dinosaur has a tek saddle.", NULL, 0, context);
        insertQuiz(db,2, "Which is a Abberation Engram?", "wood shield", "gunpowder", "Charge Lantern", "Flare Gun", 3 ,R.drawable.ark_aberration, "The surroundings light up.", NULL, 0, context);
        insertQuiz(db,2, "Which is a Abberation Engram?", "parachute", "Trophy Wall-Mount", "Basilisk saddle", "Painting Canvas", 3 ,R.drawable.ark_aberration, "Snake.", NULL, 0, context);
        insertQuiz(db,2, "Which is a Abberation Engram?", "Beer Barrel", "Hazard Suit Hat", "grenade", "Pteranodon saddle", 2 ,R.drawable.ark_aberration, "With this item, you can protect your body from radioactivity.", NULL, 0, context);
        insertQuiz(db,2, "Which is a Abberation Engram?", "Poison Grenade", "Hazard Suit Pants", "Shotgun", "Re-Fertilizer", 2 ,R.drawable.ark_aberration, "With this item, you can protect your body from radioactivity.", NULL, 0, context);
        insertQuiz(db,2, "Which is a Abberation Engram?", "Hazard Suit Shirt", "Beelzebufo saddle", "Greenhouse Wall", "Electrical Generator", 1 ,R.drawable.ark_aberration, "With this item, you can protect your body from radioactivity.", NULL, 0, context);
        insertQuiz(db,2, "Which is a Abberation Engram?", "Hazard Suit Boots", "Tapejara saddle", "GPS", "Megatherium saddle", 1 ,R.drawable.ark_aberration, "With this item, you can protect your body from radioactivity.", NULL, 0, context);

//        캔디는 어떤 캔디는 얘기하는지 모르겠어서 일단 주석처리함
        //4-3 문제 추가
//       insertQuiz(db,1, "원시 캔디가 주는 속도는?", "1%", "3%", "5%", "7%", 3 ,R.drawable.dino_candy_corn, "대표적으로 시계를 보면 생각납니다.", NULL, 0, context);
//        insertQuiz(db,1, "대가 캔디가 주는 속도는?", "5%", "10%", "15%", "20%", 2 ,R.drawable.dino_candy_corn, "학교 쉬는 시간 정도", NULL, 0, context);
//        insertQuiz(db,1, "우월 캔디가 주는 속도는?", "5%", "10%", "15%", "20%", 3 ,R.drawable.dino_candy_corn, "중2", NULL, 0, context);
//        insertQuiz(db,1, "발렌타이데이 캔디 모양은?", "알", "하트 초콜릿", "세모난 사탕", "양쪽이 묶인 캔디", 2 ,R.drawable.dino_candy_corn, "사랑", NULL, 0, context);

        insertQuiz(db,1, "What is the maximum level of users who don't clear the boss stage?", "100", "105", "110", "115", 2 ,R.drawable.ark_logo, "It is a multiple of 3.", NULL, 0, context);

        insertQuiz(db,3, "How much damage you get when you eat organic polymer?", "200", "300", "400", "500", 4 ,R.drawable.organicpolymer, "The biggest among the choices.", NULL, 0, context);

        insertQuiz(db,1, "(In Official Server) What is the maximum level of wild dinosaur?", "100", "150", "200", "250", 2 ,R.drawable.ark_logo, "human's maximum level+45", NULL, 0, context);


        insertQuiz(db,2, "Which is not the material of Thatch Foundation?", "wood", "thatch", "fiber", "stone", 4 ,R.drawable.thatch_foundation, "The hardest among the choices.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Wooden Sign?", "wood", "thatch", "fiber", "stone", 4 ,R.drawable.wooden_sign, "The hardest among the choices.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Spyglass?", "crystal", "Hide", "flint", "wood", 3 ,R.drawable.spyglass, "You can get this by sharpening stones.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Bola?", "Hide", "stone", "flint", "thatch", 3 ,R.drawable.bola, "It is yellow.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Wooden Foundation?", "wood", "flint", "fiber", "thatch", 2 ,R.drawable.wooden_foundation, "You can get this by sharpening stones.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Wooden Raft?", "wood", "thatch" , "Hide", "fiber", 2 ,R.drawable.wooden_raft, "You can get this by sharpening woods.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Delivery Crate?", "stone", "Gasbags bladder", "wood", "thatch", 1 ,R.drawable.delivery_crate, "You can get this item from Gasbags.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Lesser Antidote?", "Allosaurus Brain", "narcotic", "Woolly Rhino Horn", "Rare Mushroom", 1 ,R.drawable.lesser_antidote, "You can get this item from this dinosaurs which have pack buffs.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Refining Forge?", "stone", "Hide", "flint", "thatch", 4 ,R.drawable.refining_forge, "You can get this by sharpening woods.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Smithy?", "metal ingot", "Hide", "stone", "thatch", 4 ,R.drawable.smithy, "You can get this by sharpening woods.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Paining Canvas?", "wood", "fiber", "thatch", "Hide", 3 ,R.drawable.painting_canvas, "By hitting wood with pick, you can get this well.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of stone Foundation?", "stone", "wood", "fiber", "thatch", 3 ,R.drawable.stone_foundation, "You can easily get this by Metal Sickle.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Water Jar?", "Hide", "metal ingot", "cementing paste", "crystal", 2 ,R.drawable.water_jar, "You can get this by smelting metal.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Handcuffs?", "cementing paste", "crystal", "metal ingot", "흑요석", 2 ,R.drawable.handcuffs, "There is a wyvern with this name.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Metal Spike Wall?", "wood", "Hide", "metal ingot", "fiber", 1 ,R.drawable.metal_spike_wall, "You can get this by wood.", NULL, 0, context);
        insertQuiz(db,2, "Which is not the material of Wooden Tree Platform?", "wood", "thatch", "fiber", "metal ingot", 1 ,R.drawable.wooden_tree_platform, "You can get this by sharpening woods.", NULL, 0, context);

        //안장 그림 보여주고 맞추기
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
