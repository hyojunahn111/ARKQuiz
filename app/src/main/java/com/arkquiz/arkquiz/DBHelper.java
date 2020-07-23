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

    public void dbDelete(SQLiteDatabase db, Long dbId){
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE "+ID+"="+dbId);
    }

    public void LoadQuiz(SQLiteDatabase db, Context context){
        Log.d("TAG", "loadQuiz 호출");

//        db.beginTransaction();

        try{
//            db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES(null, '1', '다음 공룡의 이름은 무엇일까요?', '렉스', '디폴로도쿠스', '파라사우롤로푸스', '펄모노스콜피온', '3')");
//            db.execSQL("INSERT INTO "+TABLE_NAME+" VALUES(null, '1', '다음 공룡의 이름은 무엇일까요?', '아르젠타비스', '기가노토파우르스', '파키리노사우루스', '파키', '4')");
//          공룡 이름 문제
            insertQuiz(db,1, "What is the name of this dinosaur?", "T-Rex", "Diplodocus", "Parasaurolophus", "Pachy", 3, R.drawable.ark_parasaur, "This dinosaur has the ability to detect enemies.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Griffin", "Parasaurolophus", "Ankylosaurus", "Quetzal", 3, R.drawable.ark_ankylosaurus, "This dinosaur digs metal well.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Ankylosaurus", "Baryonyx", "Beelzebufo", "Direwolf", 2, R.drawable.ark_baryonyx, "This dinosaur does not have an oxygen gauge.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Bulbdog", "T-rex", "Woolly Rhino", "Unicorn", 3, R.drawable.woollyrhino, "This dinosaur can collect gauges.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Titanoboa", "Seeker", "Daeodon", "Giganotosaurus", 4, R.drawable.giganotosaurus, "This dinosaur has a wide gauge.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Dung Beetle", "Oviraptor", "Pegomastax", "Allosaurus", 4, R.drawable.allosaurus, "This dinosaur has flock buff and bleeding damage.", NULL,0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Compy", "Morellatops", "Chalicotherium", "Gacha", 1, R.drawable.compy, "This dinosaur is small and has flock buffs.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Defense Unit", "Carnotaurus", "Magmasaur", "Kantrosaurus", 2, R.drawable.carnotaurus, "This dinosaur has short hands and two small horns on its head.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Spino", "Yeti", "Brontosaurus", "Yutyrannus", 3, R.drawable.brontosaurus, "This dinosaur is huge and attacks with its tail.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Dilophosaur", "Thorny Dragon", "Terror Bird", "Stegosaurus", 1, R.drawable.dilophosaur, "When this dinosaur spits, the vision is obscured.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Argentavis", "Triceratops", "Purlovia", "Kaprosuchus", 1, R.drawable.argentavis, "This dinosaur cuts weight in half.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Megalodon", "Direwolf", "gacha", "Phiomia", 4, R.drawable.phiomoa, "This dinosaur is used a lot when collecting poop.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Pulmonoscorpius", "Morellatops", "Doedicurus", "Iguanodon", 1, R.drawable.pulmonoscorpius, "If you hit this dinosaur, the paralysis will increase.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Bulbdog", "Diplidicus", "Onyc", "Pachyrhinosaurus", 3, R.drawable.onyc, "This dinosaur lives in caves and has a chance of causing rabies.", NULL, 0, context);
            insertQuiz(db,1, "What is the name of this dinosaur?", "Spino", "Meganeura", "Baryonyx", "Leech", 1, R.drawable.spino, "This dinosaur receives a water buff when it touches the water.", NULL, 0, context);
            insertQuiz(db,1, "How many times can Managarmr jump in the air?", "1 Time", "2 Time", "3 Time", "4 Time", 3, R.drawable.managarma, "jujujump...", NULL, 0, context);
            insertQuiz(db,1, "How many dashes are in front of Managarmr?", "1 Time", "2 Time", "3 Time", "4 Time", 1, R.drawable.managarma, "managarmr is not a bird", NULL, 0, context);


//          자원문제
            insertQuiz(db,1, "What is not Cartoroides collects?", "Wood", "Silica Pearls", "Rare Mushroom", "Straw", 4, R.drawable.castoroides, "It is yellow", NULL, 0, context);
            insertQuiz(db,1, "What is not a resource from killing trilobites?", "Black Pearl", "Silica Pearls", "Oil", "Leather", 4, R.drawable.trilobite, "Remember that it is a'crustacean'.", NULL, 0, context);
            insertQuiz(db,1, "What is not a resource that comes from killing Ovis?", "Lamb", "Raw meat", "Leather", "Fur", 2, R.drawable.ovis, "'Sheep' is'sheep'.", NULL, 0, context);

            insertQuiz(db,1, "What is not a resource that comes from killing water bugs?", "Chitin", "Raw meat", "Cementing Paste", "silks", 4, R.drawable.jugbug, "can't make clothes.", NULL, 0, context);

//          음식문제
            insertQuiz(db,1, "Which of these is not a berry?", "Amarberry", "Azulberry", "Tintoberry", "Pozoberry", 4, R.drawable.ark_logo, "There is no such berry.", NULL, 0, context);

            //부패시간 문제
            insertQuiz(db,1, "What is the time of corruption for one berry?", "5 min", "10 min", "15 min", "20 min", 2, R.drawable.berries, "초 중 고등학생 쉬는시간정도..", NULL, 0, context);
            insertQuiz(db,1, "What is the time for one grain of decay?", "5 min", "10 min", "15 min", "20 min", 1, R.drawable.citronal, "하루 반의 반의 반의 반의 반에서 9로 나눈 정도..", NULL, 0, context);
            insertQuiz(db,1, "What is the decay time of one raw meat?", "5 min", "10 min", "15 min", "20 min", 2, R.drawable.raw_meat, "초 중 고등학생 쉬는시간정도..", NULL, 0, context);
            insertQuiz(db,1, "What is the decay time of 1 square of raw meat?", "5 hour 10 min", "5 hour 40 min", "6 hour 10 min", "6 hour 40 min", 4, R.drawable.raw_meat, "Multiply one corruption time by 40!", NULL, 0, context);
            insertQuiz(db,1, "What is the decay time for high quality raw meat?", "2 min", "2 min 10 sec", "2 min 20 sec", "2 min 30 sec", 3, R.drawable.raw_prime_meat, "'140'", NULL, 0, context);
            insertQuiz(db,1, "What is the time to rot one carrion?", "5 min", "10 min", "15 min", "20 min", 3, R.drawable.spoiled_meat, "Between the time of decay of 1 raw meat and 1 decay of grilled meat.", NULL, 0, context);

            //한 칸 문제
            insertQuiz(db,1, "How much raw meat can you have in one square?"/*한 칸에 들 수 있는 생고기의 양은?*/, "10", "20", "30", "40", 4, R.drawable.raw_meat, "If you get 10000 meat, 250 squares are made.", NULL, 0, context);
            insertQuiz(db,1, "How much high quality raw meat can you get in one box?"/*한 칸에 들 수 있는 고품질 생고기의 양은?*/, "1", "2", "3", "4", 1, R.drawable.raw_prime_meat, "If you dig 300, you can fill 300 squares.", NULL, 0, context);
            insertQuiz(db,1, "How much high-quality meat can be in one box?", "10", "20", "30", "40", 3, R.drawable.cooked_prime_meat, "High quality raw meat X 30", NULL, 0, context);
            insertQuiz(db,1, "What is the amount of Wyvern milk in one box?", "1", "2", "3", "4", 4, R.drawable.wyvern_milk, "Same as high quality raw meat.", NULL, 0, context);

            //제작 문제
            insertQuiz(db,1, "What is not an ingredient for making gunpowder?", "Flint", "Stone", "Thatch", "Charcoal", 3, R.drawable.gunpowder, "It is light yellow.", NULL, 0, context);
            insertQuiz(db,3, "How many elements do you need to make a raw tech rifle?", "10", "20", "30", "40", 2, R.drawable.tek_rifle, "It takes as much as the elements that come out when you catch gamma Megapithecus.", NULL, 0, context);
            //데미지
            insertQuiz(db,1, "What does a raw bow do?", "50", "55", "60", "65", 2, R.drawable.bow, "체력 스텟 하나를 찍고 화살 한 발을 맞았을 경우에 반 피가답니다", NULL, 0, context);
            //자원문제
            insertQuiz(db,1, "What will come out when I pick up a stone with a pickaxe?", "Flint", "Stone", "Metal", "Stone powder", 2, R.drawable.metal_pick, "It's best to pick yellow and triangular things.", NULL, 0, context);
            insertQuiz(db,1, "What is not a resource that can be obtained with an iron sickle?", "Fiber", "High quality raw fish meat", "Flint", "Thatch", 4, R.drawable.metal_sickle, "The tree cannot be touched.", NULL, 0, context);
                insertQuiz(db,1, "Which of the examples is less digging with an axe?", "Leech blood", "leather", "AnglerGel", "Stone", 4, R.drawable.metal_hatchet, "It's all well, but it's hard to pick Hard.", NULL, 0, context);
            insertQuiz(db,1, "What is not the material of a paint brush?", "Wood", "Leather", "Fiber", "Thatch", 3, R.drawable.paintbrush, "This comes out when you dig the grass.", NULL, 0, context);
            insertQuiz(db,1, "What is not a parachute material?", "Thatch", "Leather", "Wood", "Fiber", 4, R.drawable.parachute, "This comes out when you dig the grass.", NULL, 0, context);
            insertQuiz(db,1, "Where can make torchlight?", "Personal inventory", "Making machine", "Smithy", "Mortar and Pestle", 1, R.drawable.torch, "Can be made in 1 level.", NULL, 0, context);
            insertQuiz(db,1, "what level can you learn a small telescope?", "7", "10", "13", "15", 1, R.drawable.spyglass, "You can learn right away.", NULL, 0, context);

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
            insertQuiz(db, 2, "Which dinosaur is best for digging iron?", "Ankylosaurus", "Megaloceros", "dodo", "Mosasaurus", 1, R.drawable.ark_metal, "This dinosaur looks hard.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is the best to picking straw?", "Pteranodon", "Ovis", "Allosaurus", "Megaloceros", 4, R.drawable.ark_thatch, "This may not be a dinosaur.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is the best to digging wood?", "Pachy", "Parasaur", "Mammoth", "Sabertooth", 3, R.drawable.ark_wood, "This is large in size.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is best for digging Rock?", "Doedicurus", "Triceratops", "Compy", "Quetzal", 1, R.drawable.ark_stone, "This dinosaur looks hard.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is best for picking berry?", "Purlovia", "Brontosaurus", "T-rex", "parasaur", 2, R.drawable.ark_berries, "This dinosaur has a really long neck.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is best for digging Hide?", "Therizinosaur", "Trilobite", "Quetzal", "Gigantopithecus", 1, R.drawable.ark_hide, "This dinosaur has long nails.", NULL, 0, context);
            insertQuiz(db, 2, "Which dinosaur is best for digging flint?", "Dierwolf", "Ankylosaurus", "Raptor", "Manta", 2, R.drawable.ark_hide, "This dinosaur looks hard.", NULL, 0, context);
            insertQuiz(db, 2, "What is the best tool for digging iron?", "Metal Hatchet", "Metal pick", "Metal sickle", "Sword", 2, R.drawable.ark_metal, "Think of the real use of the tools", NULL, 0, context);
            insertQuiz(db, 2, "Which is the best tool for digging stones?", "Rock Hatchet", "Metal Hatchet", "Rock Pick", "Metal Pick", 2, R.drawable.ark_stone, "Hatchet are better at picking stones than pickaxes.", NULL, 0, context);
            insertQuiz(db, 2, "Which tool is best for digging flint?", "Rock Hatchet", "Metal Hatchet", "Rock Pick", "Metal Pick", 4, R.drawable.ark_stone, "A pickaxe is more efficient at picking flint than an Hatchet.", NULL, 0, context);
            insertQuiz(db, 2, "What is the best tool for digging wood?", "Rock Hatchet", "Metal Hatchet", "Rock Pick", "Metal Pick", 2, R.drawable.ark_wood, "곡괭이보다는 도끼가 목재 채집 효율이 더 좋습니다.", NULL, 0, context);
            insertQuiz(db, 2, "Which of the following is the best tool for picking straw?", "Rock Hatchet", "Metal Hatchet", "Rock Pick", "Metal Pick", 4, R.drawable.ark_thatch, "도끼보다는 곡괭이가 짚 채집 효율이 더 좋습니다.", NULL, 0, context);
            insertQuiz(db, 2, "Which of the following is the best tool for digging fiber?", "Metal sickle", "Whip", "Metal Hatchet", "Metal Pick", 1, R.drawable.ark_fiber, "This tool is used to cut something.", NULL, 0, context);
            insertQuiz(db, 2, "What is the best tool for mining leather?", "Metal Hatchet", "Metal Pick", "Sword", "Metal sickle", 1, R.drawable.ark_hide, "This tool is also a good tool for collecting wood.", NULL, 0, context);
            insertQuiz(db, 2, "Which of the following is the best tool for digging raw meat?", "Metal Hatchet", "Metal Pick", "Sword", "Metal sickle", 2, R.drawable.raw_meat, "This tool is also a good tool for picking iron.", NULL, 0, context);
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
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute from the boss.", "Argentavis", "Sarco", "Megalania", "Procoptodon", 4, R.drawable.ark_logo, "This dinosaur jumps the highest.", NULL, 0, context);
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute from the boss.", "Carnotaurus", "Kairuku", "Giganotosaurus", "Megalodon", 2, R.drawable.ark_logo, "This dinosaur gives an organic polymer.", NULL, 0, context);
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute from the boss.", "Basilosaurus", "Thylacoleo", "Allosaurus", "Megatherium", 4, R.drawable.ark_logo, "This dinosaur gets a buff when it catches insects.", NULL, 0, context);
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute from the boss.", "Brontosaurus", "Spino", "Raptor", "Diplodocus", 3, R.drawable.ark_logo, "This dinosaur has two-legged jumping and catching skills..", NULL, 0, context);
            insertQuiz(db,2, "Choose a dinosaur that doesn't give a tribute from the boss.", "T-rex", "Titanoboa", "Woolly Rhino", "Ravager", 4, R.drawable.ark_logo, "This dinosaur is a quadruped and reduces weight.", NULL, 0, context);
//          알파공룡 문제
            insertQuiz(db,3, "Which of the following is not an alpha dinosaur?", "Alpha Leedsichthys", "Alpha Raptor", "Alpha Reaper king", "Alpha Carnotaurus", 4, R.drawable.ark_logo, "This dinosaur collects Cementing Paste, Wood, rare flowers, rare mushrooms, and silica pearhyojuls.", NULL, 0, context);
            insertQuiz(db,3, "Which of the following is not an alpha dinosaur?", "Alpha Megalodon", "Alpha Mosasaur", "Alpha Megalania", "Alpha Tusoteuthis", 3, R.drawable.ark_logo, "There are no dinosaurs like this. Mainly living in the ceiling in the cave and there are many in Ragnarok", NULL, 0, context);

            insertQuiz(db,2, "What is not a resource that comes from killing Managarmr?", "Chitin", "Keratin", "Hide", "Raw prime Meat", 1, R.drawable.managarma, "Managamar is not an insect.", NULL, 0, context);
            insertQuiz(db,2, "What is the number of milk that comes out when you stun Wyvern?", "4", "5", "6", "7", 2, R.drawable.wyvern_milk, "Less than one line.", NULL, 0, context);
//          보스 문제
            insertQuiz(db,2, "How much element does gamma megapithecus catch?", "10", "20", "30", "40", 2, R.drawable.boss_megapithecus, "이 건 너무 어려울 수도 있겠네요. '둘' 이서 풀면 쉬울지도..", NULL, 0, context);
            //insertQuiz(db,1, "베타 메가피테쿠스를 잡으면 주는 원소의 양은?", "10", "20", "30", "40", 2, R.drawable.boss_megapithecus, "감마랑 똑같습니다.", NULL, 0, context);
            insertQuiz(db,2, "What is Gamma Rockwell's level limit?", "50Lv", "60Lv", "70Lv", "80Lv", 2, R.drawable.rockwell, "This is the level to learn Basilosaurus Saddle.", NULL, 0, context);
            insertQuiz(db,2, "What is beta Rockwell's level limit?", "65Lv", "75Lv", "85Lv", "95Lv", 2, R.drawable.rockwell, "It is a level to learn the safe.", NULL, 0, context);
            insertQuiz(db,3, "What is Alpha Rockwell's level limit?", "70Lv", "80Lv", "90Lv", "100Lv", 4, R.drawable.rockwell, "It is a level to learn the titanosaurus platform saddle.", NULL, 0, context);
            insertQuiz(db,2, "What's not Manticore's boss tribute?", "Ice Talon", "Fire Talon", "Poison Talon", "Lightning Talon", 1, R.drawable.manticore, "There are no cold claws.", NULL, 0, context);
            insertQuiz(db,2, "What's not the tech engram you get when you catch Gamma Manticore?", "Tek Gauntlets", "Tek Leggings", "Tek Ganerator", "Tek Helmet", 4, R.drawable.tek_replicator, "First body...", NULL, 0, context);
//          키블 문제
            insertQuiz(db,2, "What is not the Basic kibble material?", "Dilophosaur Egg ", "Amarberry", "Pteranodon Egg", "Thatch", 3, R.drawable.basic_kibble, "Use only the smallest eggs.", NULL, 0, context);
            insertQuiz(db,2, "What is not the Simple kibble material?", "Raptor Egg", "Cooked meat", "Carrot", "Mejoberry", 2, R.drawable.simple_kibble, "It should be made of fish.", NULL, 0, context);
            insertQuiz(db,2, "What is not the Regular kibble material?", "Dilophosaur Egg", "Ichthyornis Egg", "Potato", "Prime Meat jerky", 4, R.drawable.regular_kibble, "It takes the longest to get.", NULL, 0, context);
            insertQuiz(db,2, "What is not the Superior kibble material?", "Prime Meat jerky", "Citronal", "Thatch", "Rare Flower", 4, R.drawable.superior_kibble, "Often seen near Crystal, Beaver Dam and Ragnarok Bluebell.", NULL, 0, context);
            insertQuiz(db,2, "What is not the Exceptional kibble material?", "Focal chili", "Fria Curry", "Thatch", "Mejoberry", 2, R.drawable.exceptional_kibble, "It is brown.", NULL, 0, context);
            insertQuiz(db,2, "What is not the ExtraOrdinary kibble material?", "Giant Bee Honey", "Wyvern Egg", "Lazarus chowder", "Energy Brew", 4, R.drawable.extraordinary_kibble, "Hill here...?", NULL, 0, context);
            insertQuiz(db,2, "What is not Brew's ingredients?", "Tintoberry", "Mejoberry", "Narcotic", "Water", 2,R.drawable.medical_brew, "Herbivorous dinosaurs are my favorite.", NULL, 0, context);
            insertQuiz(db,2, "What is not an Enduro Stew ingredient?", "Water", "Mejoberry 10", "Carrot 5", "Stimulant 1", 4, R.drawable.rockwell_recipes_enduro_stew, "+stimulant.", NULL, 0, context);
            insertQuiz(db,2, "What is not a focal chili material?", "Mejoberry 10", "Citronal 5", "Cooked meat", "Prime Cooked meat", 4, R.drawable.rockwell_recipes_focal_chili, "There is no need to do high quality..", NULL, 0, context);
            insertQuiz(db,2, "What is not Nazarus chowder material?", "Mejoberry 10", "Corn 5", "Stimulant 2", "Potato 5", 3, R.drawable.lazarus_chowder, "It's not stimberry or spark powder, but nacoberry and rotten meat.", NULL, 0, context);
            insertQuiz(db,2, "What is not ingredients for Calien Soup?", "Citronal 5", "Amarberry 20", "Tintoberry 10", "Stimulant 2", 3, R.drawable.calien_soup, "10 is not enough..", NULL, 0, context);
            insertQuiz(db,2, "What is not cold curry ingredients?", "Corn 5", "Carrot 5", "Azulberry 20", "Mejoberry 20", 4, R.drawable.fria_curry, "10 is enough for the berry that dinosaurs love..", NULL, 0, context);
            insertQuiz(db,2, "What is not Shadow Steak Saute ingredients?", "Narcotic 10", "Rare Mushroom 2", "Potato 1", "Carrot 1", 1, R.drawable.ark_logo, "8  Rotten meat + 40 Nacoberries are enough.", NULL, 0, context);
            insertQuiz(db,2, "Roasting time of one grilled meat?", "5 min", "10 min", "15 min", "20 min", 4, R.drawable.cooked_meat, "It's twice the time of raw meat decay..", NULL, 0, context);
            insertQuiz(db,2, "What is the time of decay of one meat jerky?", "12 hour", "24 hour", "36 hour", "48 hour", 4, R.drawable.cooked_meat_jerky, "Beef jerky has a longer shelf life than expected.", NULL, 0, context);
            insertQuiz(db,2, "What is the decay time for one high quality meat?", "40 min", "42 min", "44 min", "46 min", 4, R.drawable.cooked_prime_meat, "'2760'", NULL, 0, context);
            insertQuiz(db,2, "High quality meat jerky One rot time?", "12 hour", "24 hour", "36 hour", "48 hour", 4, R.drawable.prime_meat_jerky, "Same as meat jerky decay time..", NULL, 0, context);
            insertQuiz(db,2, "How much jerky can be in one square?", "10", "20", "30", "40", 2, R.drawable.cooked_meat_jerky, "Half of raw meat.", NULL, 0, context);
            insertQuiz(db,2, "What can't be made in the making machine?", "Rocket Launcher", "Metal Cliff Platform", "Polymer", "Generator", 2, R.drawable.fabricator, "This is an engram that you can learn from Aberration.", NULL, 0, context);
            insertQuiz(db,2, "What is not a scuba hydroponic material?", "hide", "Thatch", "Crystal", "Polymer", 4, R.drawable.scuba_mask, "A lot comes from penguins and mantis.", NULL, 0, context);
            insertQuiz(db,3, "How many c4 to break the metal door?", "1", "2", "3", "4", 2, R.drawable.c4_charge, "A single c4 damage to iron is 3544.", NULL, 0, context);
            insertQuiz(db,2, "How many grenades are there to break the stone foundation?", "9", "10", "11", "12", 1, R.drawable.grenade, "Grenade damage into stone is 1838.", NULL, 0, context);
            insertQuiz(db,2, "How many cannons to break the tech ceiling?", "250", "300", "350", "400", 1, R.drawable.cannon, "One cannon damage that enters the tek ceiling is 40.", NULL, 0, context);
            insertQuiz(db,2, "What is the damage of one bullet in an automatic turret?", "70", "80", "90", "100", 1, R.drawable.auto_turret, "5렙 기준 모두 체력을 찍었을 때 완벽하게 2방입니다.", NULL, 0, context);
            insertQuiz(db,3, "How much damage does C4 take on wood?", "11320", "11813", "12591", "12677", 2, R.drawable.c4_charge, "There is no way to give a hint. The answer is 11813.", NULL, 0, context);
            insertQuiz(db,2, "What is the numbness of kicking on one foot?", "220", "221", "222", "223", 2, R.drawable.tranquilizer_dart, "It is 33 shots based on paralysis number 7293..", NULL, 0, context);
            insertQuiz(db,2, "What is the numbness of a shock paralysis?", "440", "442", "444", "446", 2, R.drawable.shocking_tranquilizer_dart, "It's twice as much as Paralysis dart.", NULL, 0, context);
            insertQuiz(db,2, "What is the best bait for fishing?", "Giant Bee Honey", "Leech Blood", "sap", "Raw meat", 2, R.drawable.fishing_rod, "I like the sweetest thing.", NULL, 0, context);
            insertQuiz(db,3, "What is not a resource to enter the tech claw?", "Element", "Black Pearl", "Polymer", "Hide", 4, R.drawable.tek_claws, "can't use a tough one.", NULL, 0, context);
            insertQuiz(db,3, "What aren't the resources involved in creating a tech maker?", "Metal Ingot 5000", "Element 110", "Black Pearl 150", "Crystal 600", 2, R.drawable.tek_replicator, "There are many squares.", NULL, 0, context);
                insertQuiz(db,3, "What is not the ThsIsland Obis Zen area?", "20.0, 30.0", "20.0, 50,0", "85.0, 83.0", "75.0, 65.0", 3, R.drawable.ovis, null, R.drawable.theisland_ovis_spawn, 0, context);
            insertQuiz(db,3, "What is the limit on the number of turrets in a zone?", "50", "100", "150", "200", 2, R.drawable.heavy_auto_turret, "이순신 장군이 그려져 있는 동전과 같습니다.", NULL, 0, context);
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
