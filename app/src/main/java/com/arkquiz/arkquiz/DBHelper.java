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
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "렉스", "디폴로도쿠스", "파라사우롤로푸스", "파키", 3, R.drawable.ark_parasaur, "이 공룡은 적을 감지하는 능력이 있습니다.", NULL, 0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "그리핀", "파라사우롤로푸스", "안킬로사우르스", "케찰", 3, R.drawable.ark_ankylosaurus, "철을 잘 캐는 공룡입니다.", NULL, 0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "안킬로사우르스", "바리오닉스", "벨제부포", "검치호", 2, R.drawable.ark_baryonyx, "이 공료은 산소 게이지가 없습니다.", NULL, 0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "벌브독", "렉스", "울리라이노", "유니콘", 3, R.drawable.woollyrhino, "이 공룡은 게이지를 모을 수 있습니다.", NULL, 0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "티타노보아", "씨커", "다에오돈", "기가노토사우르스", 4, R.drawable.giganotosaurus, "이 공룡은 광폭 게이지가 있습니다.", NULL, 0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "쇠똥구리", "오비랍토르", "페고메틱스", "알로사우르스", 4, R.drawable.allosaurus, "이 공룡은 무리버프, 출혈데미지가 있습니다.", NULL,0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "콤피", "모렐로톱스", "칼리코테리움", "가차", 1, R.drawable.compy, "이 공룡은 작고 무리 버프가 있습니다.", NULL, 0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "디펜스 유닛", "카르노사우르스", "마그마사우르스", "켄트로사우르스", 2, R.drawable.carnotaurus, "이 공룡은 손이 짧고 머리 위에 작은 뿔 두개가 나와 있습니다.", NULL, 0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "스피노사우르스", "예티", "브론토사우르스", "유티라누스", 3, R.drawable.brontosaurus, "이 공룡은 거대하고 꼬리로 공격합니다.", NULL, 0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "딜로포사우르스", "가시 드래곤", "테러버드", "티라노사우르스", 1, R.drawable.dilophosaur, "이 공룡이 침을 뿌렸을 때 맞으면 시야를 가립니다.", NULL, 0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "아르젠타비스", "트리케라톱스", "펄로비아", "카프로스쿠스", 1, R.drawable.argentavis, "이 공룡은 무게를 반으로 줄여줍니다.", NULL, 0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "메갈로돈", "다이어울프", "가챠", "피오미아", 4, R.drawable.phiomoa, "이 공룡은 똥을 모을 때 많이 씁니다.", NULL, 0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "펄모노스콜피온", "모렐로톱스", "도에디쿠스", "이구애나돈", 1, R.drawable.pulmonoscorpius, "이 공룡에게 맞으면 마비수치가 차오릅니다.", NULL, 0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "벌브독", "디폴로도쿠스", "오닉스", "파키리노사우르스", 3, R.drawable.onyc, "이 공룡은 동굴에서 생활하며 일정 확률로 광견병을 일으킵니다.", NULL, 0, context);
            insertQuiz(db,1, "다음 공룡의 이름은 무엇일까요?", "스피노", "메가네우라", "바리오닉스", "크나다리아", 1, R.drawable.spino, "이 공룡은 물에 닿으면 물 버프를 받습니다.", NULL, 0, context);
            insertQuiz(db,1, "마나가르마의 공중 점프 할 수 있는 횟 수는?", "1번", "2번", "3번", "4번", 3, R.drawable.managarma, "점점점프...", NULL, 0, context);
            insertQuiz(db,1, "마나가르마의 대쉬 횟 수는?", "1번", "2번", "3번", "4번", 1, R.drawable.managarma, "마나가르마는 새가 아니랍니다.", NULL, 0, context);


//          자원문제
            insertQuiz(db,1, "카르토로이데스가 모으는 자원이 아닌 것은?", "목재", "실리카 진주", "희귀 버섯", "짚", 4, R.drawable.castoroides, "노랑색입니다.", NULL, 0, context);
            insertQuiz(db,1, "삼엽충을 죽이면 나오는 자원이 아닌 것은?", "흑진주", "실리카 진주", "오일", "가죽", 4, R.drawable.trilobite, "'갑각류'라는 점을 기억하세요.", NULL, 0, context);
            insertQuiz(db,1, "오비스을 죽이면 나오는 자원이 아닌 것은?", "양고기", "생고기", "가죽", "털가죽", 2, R.drawable.ovis, "'양'은 '양'이죠.", NULL, 0, context);

            insertQuiz(db,1, "물병벌래을 죽이면 나오는 자원이 아닌 것은?", "키틴", "생고기", "시멘트폴", "비단", 4, R.drawable.jugbug, "옷을 못 만듭니다.", NULL, 0, context);

//          음식문제
            insertQuiz(db,1, "다음 중 베리가 아닌 것은?", "아마르베리", "아줄베리", "틴토베리", "포조베리", 4, R.drawable.ark_logo, "이런 열매는 없습니다.", NULL, 0, context);

            //부패시간 문제
            insertQuiz(db,1, "베리 1개의 부패 시간은?", "5분", "10분", "15분", "20분", 2, R.drawable.berries, "초 중 고등학생 쉬는시간정도..", NULL, 0, context);
            insertQuiz(db,1, "곡물 1개의 부패 시간은?", "5분", "10분", "15분", "20분", 1, R.drawable.citronal, "하루 반의 반의 반의 반의 반에서 9로 나눈 정도..", NULL, 0, context);
            insertQuiz(db,1, "생고기 1개의 부패 시간은?", "5분", "10분", "15분", "20분", 2, R.drawable.raw_meat, "초 중 고등학생 쉬는시간정도..", NULL, 0, context);
            insertQuiz(db,1, "생고기 1칸의 부패 시간은?", "5시간 10분", "5시간 40분", "6시간 10분", "6시간 40분", 4, R.drawable.raw_meat, "한 개의 부패 시간에 40을 곱하면 되죠!", NULL, 0, context);
            insertQuiz(db,1, "고품질 생고기 1개의 부패 시간은?", "2분", "2분10초", "2분 20초", "2분 30초", 3, R.drawable.raw_prime_meat, "'140'", NULL, 0, context);
            insertQuiz(db,1, "썩은 고기 1개의 부패 시간은?", "5분", "10분", "15분", "20분", 3, R.drawable.spoiled_meat, "생고기 1개 부패 시간과 구운고기 1개 부패 시간의 사이입니다.", NULL, 0, context);

            //한 칸 문제
            insertQuiz(db,1, "한 칸에 들 수 있는 생고기의 양은?", "10개", "20개", "30개", "40개", 4, R.drawable.raw_meat, "고기를 10000개를 캤다면 250칸이 만들어집니다.", NULL, 0, context);
            insertQuiz(db,1, "한 칸에 들 수 있는 고품질 생고기의 양은?", "1개", "2개", "3개", "4개", 1, R.drawable.raw_prime_meat, "300개를 캐면 300칸을 채울 수 있습니다.", NULL, 0, context);
            insertQuiz(db,1, "한 칸에 들 수 있는 고품질 고기의 양은?", "10개", "20개", "30개", "40개", 3, R.drawable.cooked_prime_meat, "한 칸에 고품질 생고기의 30배를 들 수 있습니다.", NULL, 0, context);
            insertQuiz(db,1, "한 칸에 들 수 있는 와이번 우유의 양은?", "1개", "2개", "3개", "4개", 4, R.drawable.wyvern_milk, "고품질 생고기와 같습니다.", NULL, 0, context);

            //제작 문제
            insertQuiz(db,1, "화약을 만들기 위한 재료가 아닌 것은?", "부싯돌", "돌", "짚", "숯", 3, R.drawable.gunpowder, "옅은 노란색입니다.", NULL, 0, context);
            insertQuiz(db,3, "원시 테크 소총을 만들기 위한 원소의 개 수는?", "10개", "20개", "30개", "40개", 2, R.drawable.tek_rifle, "감메 메가피테쿠스를 잡으면 나오는 원소만큼듭니다.", NULL, 0, context);
            //데미지
            insertQuiz(db,1, "원시 활 한 발의 데미지는?", "50", "55", "60", "65", 2, R.drawable.bow, "체력 스텟 하나를 찍고 화살 한 발을 맞았을 경우에 반 피가답니다", NULL, 0, context);
            //자원문제
            insertQuiz(db,1, "곡괭이로 돌을 캐면 잘 나오는 것은?", "부싯돌", "돌", "철", "돌 가루", 2, R.drawable.metal_pick, "노랗고 세모난걸 제일 잘 캐죠.", NULL, 0, context);
            insertQuiz(db,1, "철제 낫으로 얻을 수 있는 자원이 아닌 것은?", "섬유", "고품질 날 생선살", "섬유", "짚", 4, R.drawable.metal_sickle, "나무는 못 건들죠.", NULL, 0, context);
            insertQuiz(db,1, "보기중에서 도끼로 덜 캐지는 것은?", "거머리 혈액", "가죽", "앵글러젤", "돌", 4, R.drawable.metal_hatchet, "다 잘 캐지만 단단한 걸 그나마 못 캐죠.", NULL, 0, context);
            insertQuiz(db,1, "페인트 붓의 재료가 아닌 것은?", "목재", "가죽", "섬유", "짚", 3, R.drawable.paintbrush, "이것은 풀을 캐면 나옵니다", NULL, 0, context);
            insertQuiz(db,1, "낙하산의 재료가 아닌 것은?", "짚", "가죽", "목재", "섬유", 4, R.drawable.parachute, "이것은 풀을 캐면 나옵니다.", NULL, 0, context);
            insertQuiz(db,1, "횟불을 만들 수 있는 곳은?", "개인 인벤토리", "제작기", "대장간", "절구와 공이", 1, R.drawable.torch, "1렙에 만들 수 있습니다.", NULL, 0, context);
            insertQuiz(db,1, "작은 망원경을 배울 수 있는 레벨은?", "7", "10", "13", "15", 1, R.drawable.spyglass, "금방 배울 수 있습니다.", NULL, 0, context);

            //오벨리스크
            insertQuiz(db,1, "아일랜드 블루 오벨리스크의 좌표는?", "13.1, 55.3", "35.4, 64.3", "25.5, 25.6", "39.2, 68.7", 3, R.drawable.obelisk, "왼쪽 위에 있습니다.", NULL, 0, context);
            insertQuiz(db,1, "아일랜드 레드 오벨리스크의 좌표는?", "79.8, 17.4", "75.2, 13.8", "49.1, 82.0", "24.9, 86.4", 1, R.drawable.obelisk, "왼쪽 아래에 있습니다.", NULL, 0, context);
            insertQuiz(db,1, "아일랜드 그린 오벨리스크의 좌표는?", "14.8, 76.0", "59.0, 72.3", "19.4, 28.6", "16.8, 71.3", 2, R.drawable.obelisk, "오른쪽 중간에 있습니다.", NULL, 0, context);



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
            insertQuiz(db, 2, "다음 중 철을 캐기 가장 좋은 공룡은?", "안킬로사우르스", "메갈로케로스", "도도", "모사사우르스", 1, R.drawable.ark_metal, "이 공룡은 딱딱하게 생겼네요.", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 짚을 캐기 가장 좋은 공룡은?", "프테라노돈", "오비스", "알로사우루스", "메갈로케로스", 4, R.drawable.ark_thatch, "이것은 공룡이 아닐 수도 있겠네요", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 목재를 캐기 가장 좋은 공룡은?", "파키", "파라사우롤로푸스", "매머드", "검치호", 3, R.drawable.ark_wood, "이것은 크기가 큽니다", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 돌을 캐기 가장 좋은 공룡은?", "도에디쿠루스", "트리케라톱스", "콤피", "케찰", 1, R.drawable.ark_stone, "이 공룡은 딱딱하게 생겼네요", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 열매를 캐기 가장 좋은 공룡은?", "펄로비아", "브론토사우르스", "렉스", "파라사우롤로푸스", 2, R.drawable.ark_berries, "이 공룡은 목이 정말 깁니다.", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 가죽을 캐기 가장 좋은 공룡은?", "다이어울프", "삼엽충", "케찰", "기간토피테쿠스", 1, R.drawable.ark_hide, "이것은 요즘에도 볼 수 있죠", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 부싯돌을 캐기 가장 좋은 공룡은?", "다이어울프", "안킬로사우르스", "랩터", "만타", 2, R.drawable.ark_hide, "이 공룡은 단단하게 생겼네요.", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 철을 캐기 가장 좋은 도구는?", "철 도끼", "철 곡괭이", "낫", "검", 2, R.drawable.ark_metal, "도구들의 실제 용도를 떠올려보세요", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 돌을 캐기 가장 좋은 도구는?", "돌 도끼", "철 도끼", "돌 곡괭이", "철 곡괭이", 2, R.drawable.ark_stone, "곡괭이보다는 도끼가 돌 채집 효율이 더 좋습니다.", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 부싯돌을 캐기 가장 좋은 도구는?", "돌 도끼", "철 도끼", "돌 곡괭이", "철 곡괭이", 4, R.drawable.ark_stone, "도끼보다는 곡괭이가 부싯돌 채집 효율이 더 좋습니다.", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 목재를 캐기 가장 좋은 도구는?", "돌 도끼", "철 도끼", "돌 곡괭이", "철 곡괭이", 2, R.drawable.ark_wood, "곡괭이보다는 도끼가 목재 채집 효율이 더 좋습니다.", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 짚을 캐기 가장 좋은 도구는?", "돌 도끼", "철 도끼", "돌 곡괭이", "철 곡괭이", 4, R.drawable.ark_thatch, "도끼보다는 곡괭이가 짚 채집 효율이 더 좋습니다.", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 섬유를 캐기 가장 좋은 도구는?", "낫", "채찍", "철 도끼", "철 곡괭이", 1, R.drawable.ark_fiber, "이 도구는 어떤 것을 베는 데 사용됩니다.", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 가죽을 캐기 가장 좋은 도구는?", "철 도끼", "철 곡괭이", "검", "낫", 1, R.drawable.ark_hide, "이 도구는 나무 채집 효울이 좋은 도구이기도 하죠.", NULL, 0, context);
            insertQuiz(db, 2, "다음 중 생고기를 캐기 가장 좋은 도구는?", "철 도끼", "철 곡괭이", "검", "낫", 2, R.drawable.raw_meat, "이 도구는 철 채집 효율이 좋은 도구이기도 하죠.", NULL, 0, context);
            insertQuiz(db, 3, "다음 중 돌의 무게를 감소시켜주는 공룡이 아닌 것은?", "카스토로이드", "에쿠스", "도에디쿠르스", "모렐라톱스", 4, R.drawable.ark_stone, "이 공룡은 사막에서 자주 보입니다.", NULL, 0, context);
            insertQuiz(db, 3, "다음 중 목재의 무게를 감소시켜주는 공룡이 아닌 것은?", "가차", "매머드", "에쿠스", "가시 드래곤", 3, R.drawable.ark_wood, "이 공룡은 넓은 평야에서 자주 보이죠.", NULL, 0, context);
            insertQuiz(db, 3, "다음 중 철의 무게를 감소시켜주는 공룡이 아닌 것은?", "마그마사우르스", "와이번", "아르젠타비스", "안킬로사우르스", 2, R.drawable.ark_metal, "이 공룡은 브레스를 쏠 수 있습니다.", NULL, 0, context);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadQuiz3(SQLiteDatabase db, Context context){
        try{
            //          유물 문제
            insertQuiz(db,3, "아일랜드 현명함의 유물 위치는 어디일까요? \n (개개인의 오차가 있을 수 있습니다)", "41.1,48.8", "80.0, 53.6", "50.0, 52.3", "71.4, 86.4", 1, R.drawable.artifact_of_the_clever, "자원산 근처 입니다.", NULL, 0, context);
            insertQuiz(db,3, "아일랜드 사냥꾼의 유물 위치는 어디일까요? \n (개개인의 오차가 있을 수 있습니다)", "68.7, 56.5", "41.1, 53.6", "80.0, 53.6", "22.2, 88.9", 3, R.drawable.artifact_of_the_hunter, "남쪽에 있습니다.", NULL, 0, context);
            insertQuiz(db,3, "아일랜드 교활함의 유물 위치는 어디일까요? \n (개개인의 오차가 있을 수 있습니다)", "35.2,48.8", "74.3, 36.6", "45.3, 91.9", "32.4, 14.2", 3, R.drawable.artifact_of_the_cunning, "죽음의 섬과 초식섬 사이 입니다.", NULL, 0, context);
            insertQuiz(db,3, "아일랜드 강함의 유물 위치는 어디일까요? \n (개개인의 오차가 있을 수 있습니다)", "29.5,32.1", "87.2, 52.3", "29.3, 60.2", "10.3, 55.6", 1, R.drawable.artifact_of_the_strong, "입구는 작지만 내부는 큰 동굴에 있습니다.", NULL, 0, context);
            insertQuiz(db,3, "아일랜드 탐닉의 유물 위치는 어디일까요? \n (개개인의 오차가 있을 수 있습니다)", "14.3, 78.5", "14.6, 85.6", "98.1, 33.1", "41.1, 65.6", 2, R.drawable.artifact_of_the_devourer, "카르노 동굴로 불리고 있습니다.", NULL, 0, context);
            insertQuiz(db,3, "아일랜드 거대함의 유물 위치는 어디일까요? \n (개개인의 오차가 있을 수 있습니다)", "46.3, 85.3", "90.2, 8.1", "78.2, 14.8", "71.4, 86.4", 4, R.drawable.artifact_of_the_massive, "초식섬 근처입니다.", NULL, 0, context);
            insertQuiz(db,3, "아일랜드 무리의 유물 위치는 어디일까요? \n (개개인의 오차가 있을 수 있습니다)", "32.2, 88.1", "8.1, 82.1", "59.2, 61.3", "68.7, 56.5", 4, R.drawable.artifact_of_the_pack, "물 안에 있고 레드오벨과 설산의 사이입니다.", NULL, 0, context);
            insertQuiz(db,3, "아일랜드 짐승의 유물 위치는 어디일까요? \n (개개인의 오차가 있을 수 있습니다)", "34.3, 11.2", "61.2, 32.4", "82.1, 35.7", "52.4, 10.3", 4, R.drawable.artifact_of_the_brute, "레드우드와 SW2 부활지역 사이에 있습니다.", NULL, 0, context);
            insertQuiz(db,3, "아일랜드 먼역의 유물 위치는 어디일까요? \n (개개인의 오차가 있을 수 있습니다)", "95.4, 14.4", "96.2, 14.6", "62.7, 37.1", "13.8, 49.7", 3, R.drawable.artifact_of_the_immune, "독 동굴로 불리고 있습니다.", NULL, 0, context);
            insertQuiz(db,3, "아일랜드 하늘군주의 유물 위치는 어디일까요? \n (개개인의 오차가 있을 수 있습니다)", "91.6,81.7", "19.1, 19.0", "23.5,71.9", "15.2,38.9", 2, R.drawable.artifact_of_the_skylord, "펭귄섬 근처에 있습니다.", NULL, 0, context);
            //insertQuiz(db,1, "다음중 브루드마더의 유물이 아닌 것은?", "현명함의 유물", "거대함의 유물", "사냥꾼의 유물", "하늘군주의 유물", 4, R.drawable.ark_logo, R.drawable.skylord, NULL, 0, context);

            //          보스 공물 문제
            insertQuiz(db,2, "다음 중 보스의 공물을 주지 않는 공룡을 고르시오.", "아르젠타비스", "사르코쿠스", "메갈라니아", "프로콥토돈", 4, R.drawable.ark_logo, "이 공룡은 점프를 제일 높게 뜁니다.", NULL, 0, context);
            insertQuiz(db,2, "다음 중 보스의 공물을 주지 않는 공룡을 고르시오.", "카르노테우스", "카이루쿠", "기가노토사우르스", "메갈로돈", 2, R.drawable.ark_logo, "이 공룡은 유기 폴리머를 줍니다.", NULL, 0, context);
            insertQuiz(db,2, "다음 중 보스의 공물을 주지 않는 공룡을 고르시오.", "바실로사우르스", "틸라콜레오", "알로사우르스", "메가테리움", 4, R.drawable.ark_logo, "이 공룡은 곤충류를 잡으면 버프를 받습니다.", NULL, 0, context);
            insertQuiz(db,2, "다음 중 보스의 공물을 주지 않는 공룡을 고르시오.", "브론토사우르스", "스피노사우르스", "랩터", "디폴로도쿠스", 3, R.drawable.ark_logo, "이 공룡은 두발로 뛰어 다니고 잡기 기술이 있습니다.", NULL, 0, context);
            insertQuiz(db,2, "다음 중 보스의 공물을 주지 않는 공룡을 고르시오.", "티라노사우루스", "티티노보아", "울리 라이노", "레비저", 4, R.drawable.ark_logo, "이 공룡은 4족보행이며 무게를 줄여줍니다.", NULL, 0, context);
//          알파공룡 문제
            insertQuiz(db,3, "다음 중 알파 공룡이 아닌 것은?", "알파 리드시크", "알파 랩터", "리퍼퀸", "알파 카스토로이데스", 4, R.drawable.ark_logo, "이 공룡은 시멘트폴, 나무, 희귀꽃, 희귀 버섯, 실리카 진주를 모읍니다", NULL, 0, context);
            insertQuiz(db,3, "다음 중 알파 공룡이 아닌 것은?", "알파 메갈로돈", "알파 모사사우르스", "알파 메갈라니아", "알파 투소테우스", 3, R.drawable.ark_logo, "이런 공룡은 없죠. 주로 동굴에서 천장에서 생활하며 라그나로크 맵에서는 화산섬에 아일랜드에서는 희귀한 공룡입니다", NULL, 0, context);

            insertQuiz(db,2, "마나가르마을 죽이면 나오는 자원이 아닌 것은?", "키틴", "케라틴", "가죽", "고품질 생고기", 1, R.drawable.managarma, "마나가르마는 곤충이 아니랍니다.", NULL, 0, context);
            insertQuiz(db,2, "와이번을 기절시키면 나오는 우유의 갯 수는?", "4", "5", "6", "7", 2, R.drawable.wyvern_milk, "한 줄보다 하나 적죠.", NULL, 0, context);
//          보스 문제
            insertQuiz(db,2, "감마 메가피테쿠스를 잡으면 주는 원소의 양은?", "10", "20", "30", "40", 2, R.drawable.boss_megapithecus, "이 건 너무 어려울 수도 있겠네요. '둘' 이서 풀면 쉬울지도..", NULL, 0, context);
            //insertQuiz(db,1, "베타 메가피테쿠스를 잡으면 주는 원소의 양은?", "10", "20", "30", "40", 2, R.drawable.boss_megapithecus, "감마랑 똑같습니다.", NULL, 0, context);
            insertQuiz(db,2, "감마 로크웰의 레벨 제한은?", "50Lv", "60Lv", "70Lv", "80Lv", 2, R.drawable.rockwell, "바실로사우르스 안장을 배우는 레벨입니다.", NULL, 0, context);
            insertQuiz(db,2, "베타 로크웰의 레벨 제한은?", "65Lv", "75Lv", "85Lv", "95Lv", 2, R.drawable.rockwell, "곰고를 배울 수 있는 레벨입니다.", NULL, 0, context);
            insertQuiz(db,3, "알파 로크웰의 레벨 제한은?", "70Lv", "80Lv", "90Lv", "100Lv", 4, R.drawable.rockwell, "티타노사우르스 플렛폼 안장을 배울 수 있는 레벨입니다.", NULL, 0, context);
            insertQuiz(db,2, "만티코어의 보스 공물이 아닌 것은?", "아이스 와이번의 발톱", "화염 와이번의 발톱", "독 와이번의 발톱", "번개 와이번의 발톱", 1, R.drawable.manticore, "차가운 발톱은 없어요.", NULL, 0, context);
            insertQuiz(db,2, "감마 만티코어를 깼을 때 뚫리는 테크 엔그램이 아닌 것은?", "테크 팔 방어구", "테크 다리 방어구", "테크 발전기", "테크 머리 보호구", 4, R.drawable.tek_replicator, "일단 몸 쪽 먼저...", NULL, 0, context);
//          키블 문제
            insertQuiz(db,2, "기본 기블의 재료가 아닌 것은?", "딜로포사우르스 알", "아마르베리", "프테라노돈 알", "섬유", 3, R.drawable.basic_kibble, "제일 작은 알만 씁니다.", NULL, 0, context);
            insertQuiz(db,2, "간단한 키블의 재료가 아닌 것은?", "랩터 알", "구운 고기", "당근", "메조베리", 2, R.drawable.simple_kibble, "물고기로 만들어야죠.", NULL, 0, context);
            insertQuiz(db,2, "평범한 키블의 재료가 아닌 것은?", "디플로도쿠스 알", "이크티오르니스 알", "감자", "고품질 육포", 4, R.drawable.regular_kibble, "이 중 얻는데 제일 오래걸립니다.", NULL, 0, context);
            insertQuiz(db,2, "우수한 키블의 재료가 아닌 것은?", "고품질 육포", "시트론", "섬유", "희귀꽃", 4, R.drawable.superior_kibble, "크리스탈, 비버댐, 라그나로크 블루오벨 근처에서 자주 보이죠.", NULL, 0, context);
            insertQuiz(db,2, "특별한 키블의 재료가 아닌 것은?", "포칼 칠리", "차가운 카레", "섬유", "메조베리", 2, R.drawable.exceptional_kibble, "갈색이예요.", NULL, 0, context);
            insertQuiz(db,2, "최상급 키블의 재료가 아닌 것은?", "벌꿀", "와이번 알", "나자로 차우더", "에너지 브루", 4, R.drawable.extraordinary_kibble, "여기서 힐을...?", NULL, 0, context);
            insertQuiz(db,2, "브루의 재료가 아닌 것은?", "틴토베리", "메조베리", "마취약", "물", 2,R.drawable.medical_brew, "초식 공룡이 제일 좋아하는 것입니다.", NULL, 0, context);
            insertQuiz(db,2, "엔드류 스튜 재료가 아닌 것은?", "물", "메조베리 10개", "당근 5개", "흥분제 1개", 4, R.drawable.rockwell_recipes_enduro_stew, "+흥분제.", NULL, 0, context);
            insertQuiz(db,2, "포카 칠리 재료가 아닌 것은?", "메조베리 10개", "시트론 5개", "요리된 고기", "요리된 고품질 고기", 4, R.drawable.rockwell_recipes_focal_chili, "고품질까지야...", NULL, 0, context);
            insertQuiz(db,2, "나자로 파우더 재료가 아닌 것은?", "메조베리 10개", "옥수수 5개", "흥분제 2개", "감자 5개", 3, R.drawable.lazarus_chowder, "스팀베리, 스파크파우더가 아니라 나코베리, 썩은고기죠.", NULL, 0, context);
            insertQuiz(db,2, "칼리 앤 스프 재료가 아닌 것은?", "시트론 5개", "아마르베리 20개", "틴토베리 10개", "흥분제 2개", 3, R.drawable.calien_soup, "10개면 부족하죠.", NULL, 0, context);
            insertQuiz(db,2, "차가운 카레 재료가 아닌 것은?", "옥수수 5개", "당근 5개", "아줄베리 20개", "메조베리 20개", 4, R.drawable.fria_curry, "공룡이 젤 좋아하는 베리는 10개면 충분하죠.", NULL, 0, context);
            insertQuiz(db,2, "그림자 스테이크 재료가 아닌 것은?", "마취약 10개", "버섯 2개", "감자1개", "당근1개", 1, R.drawable.ark_logo, "썩은고기 8+ 나코베리 40개면 충분합니다.", NULL, 0, context);
            insertQuiz(db,2, "구운 고기 1개의 부패 시간은?", "5분", "10분", "15분", "20분", 4, R.drawable.cooked_meat, "생고기 부패 시간의 두배입니다.", NULL, 0, context);
            insertQuiz(db,2, "고기 육포 1개의 부패 시간은?", "12시간", "24시간", "36시간", "48시간", 4, R.drawable.cooked_meat_jerky, "육포는 생각보다 유통기한이 길답니다.", NULL, 0, context);
            insertQuiz(db,2, "고품질 고기 1개의 부패 시간은?", "40분", "42분", "44분", "46분", 4, R.drawable.cooked_prime_meat, "'2760'", NULL, 0, context);
            insertQuiz(db,2, "고품질 고기 육포 1개의 부패 시간은?", "12시간", "24시간", "36시간", "48시간", 4, R.drawable.prime_meat_jerky, "고기 육포 부패 시간이랑 똑같습니다.", NULL, 0, context);
            insertQuiz(db,2, "한 칸에 들 수 있는 육포의 양은?", "10개", "20개", "30개", "40개", 2, R.drawable.cooked_meat_jerky, "생고기의 절 반 정도 들 수 있습니다.", NULL, 0, context);
            insertQuiz(db,2, "제작기에서 지을 수 없는 것은?", "로켓 런처", "철제 절벽 토대", "폴리머", "발전기", 2, R.drawable.fabricator, "에버레이션에서 배울 수 있는 엔그램입니다.", NULL, 0, context);
            insertQuiz(db,2, "스쿠버 수경 재료가 아닌 것은?", "가죽", "섬유", "크리스탈", "폴리머", 4, R.drawable.scuba_mask, "펭귄이나 사마귀한테서 많이 나옵니다.", NULL, 0, context);
            insertQuiz(db,3, "철재 문을 부시기 위한 c4의 개 수는?", "1개", "2개", "3개", "4개", 2, R.drawable.c4_charge, "철제에 들어가는 c4 한 발의 데미지는 3544입니다.", NULL, 0, context);
            insertQuiz(db,2, "석재 토대를 부시기 위한 수류탄의 개 수는?", "9개", "10개", "11개", "12개", 1, R.drawable.grenade, "석재에 들어가는 수류탄의 데미지는 1838입니다.", NULL, 0, context);
            insertQuiz(db,2, "테크 천장을 부시기 위한 대포의 개 수는?", "250개", "300개", "350개", "400개", 1, R.drawable.cannon, "테크 천장에 들어가는 대포 한 알 데미지는 40입니다.", NULL, 0, context);
            insertQuiz(db,2, "자동포탑에 들어간 총알 한 발의 데미지는?", "70", "80", "90", "100", 1, R.drawable.auto_turret, "5렙 기준 모두 체력을 찍었을 때 완벽하게 2방입니다.", NULL, 0, context);
            insertQuiz(db,3, "목재한테 들어가는 C4 한 발 데미지는?", "11320", "11813", "12591", "12677", 2, R.drawable.c4_charge, "힌트를 드릴 방법이 없네요. 답은 11813입니다.", NULL, 0, context);
            insertQuiz(db,2, "마비다트 한 발에 차는 마비수치는?", "220", "221", "222", "223", 2, R.drawable.tranquilizer_dart, "마비수치 7293기준 33발입니다.", NULL, 0, context);
            insertQuiz(db,2, "충격 마비다트 한 발에 차는 마비 수치는?", "440", "442", "444", "446", 2, R.drawable.shocking_tranquilizer_dart, "마비다트의 두 배입니다.", NULL, 0, context);
            insertQuiz(db,2, "낚시할 때 제일 좋은 미끼는?", "거대 벌꿀", "거머리 혈액", "수액", "생고기", 2, R.drawable.fishing_rod, "제일 단 걸 좋아하죠.", NULL, 0, context);
            insertQuiz(db,3, "테크 클로에 들어가는 자원이 아닌 것은?", "원소", "흑진주", "폴리머", "가죽", 4, R.drawable.tek_claws, "질긴건 안들어가요 .", NULL, 0, context);
            insertQuiz(db,3, "테크 제작기에 들어가는 자원이 아닌 것은?", "철 주괴 5000개", "원소 110개", "흑진주 150개", "크리스탈 600개", 2, R.drawable.tek_replicator, "정사각형인게 살짝 많네요.", NULL, 0, context);
            insertQuiz(db,3, "ThsIsland 오비스 젠 지역이 아닌 것은?", "20.0, 30.0", "20.0, 50,0", "85.0, 83.0", "75.0, 65.0", 3, R.drawable.ovis, null, R.drawable.theisland_ovis_spawn, 0, context);
            insertQuiz(db,3, "한 구역의 포탑 개 수 제한은?", "50개", "100개", "150개", "200개", 2, R.drawable.heavy_auto_turret, "이순신 장군이 그려져 있는 동전과 같습니다.", NULL, 0, context);
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
