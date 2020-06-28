package com.example.arkquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.res.ResourcesCompat;

import java.io.ByteArrayOutputStream;

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

    private static final String createQuery="CREATE TABLE IF NOT EXISTS "+ TABLE_NAME
            +"("
            +ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +QUIZ_LEVEL+" TEXT " + "NOT NULL, "
            +QUIZ+" TEXT " + "NOT NULL, "
            +SELECTION_1+" TEXT, "
            +SELECTION_2+" TEXT, "
            +SELECTION_3+" TEXT, "
            +SELECTION_4+" TEXT, "
            +ANSWER+" TEXT NOT NULL, "
            +IMAGE+" BLOB)";

    public static final int DATABASE_VERSION=1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createQuery);
        Log.d("TAG", "createQuery="+createQuery);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public Cursor LoadSQLiteDBCursor_easy(){

        Log.d("TAG", "LoadSQLiteDBCursor_easy 호출");

        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();

        String selectQuery="SELECT "+ID+", "+QUIZ_LEVEL+", "+QUIZ+", "+SELECTION_1+", "+SELECTION_2+", "+SELECTION_3+", "+SELECTION_4+", "+ANSWER+", "+IMAGE+ " FROM "+TABLE_NAME+
                " WHERE "+QUIZ_LEVEL+"=1 ORDER BY RANDOM() LIMIT 1";
        Cursor cursor=null;

        try{
            cursor=db.rawQuery(selectQuery, null);
//            cursor.moveToFirst();
            Log.d("TAG", "selectQuery="+selectQuery);
//            Log.d("TAG", "cursor.getLong(0)="+cursor.getLong(0));
//            Log.d("TAG", "cursor 값: "+cursor.getLong(0)+", "+cursor.getString(1)+", "+cursor.getString(2)+", "+cursor.getString(3)+","+ cursor.getString(4)+","+ cursor.getString(5)+","+ cursor.getString(6)+","+ cursor.getString(7));
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
        return cursor;
    }

    public Cursor LoadSQLiteDBCursor_normal(){
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();

        String selectQuery="SELECT "+ID+", "+QUIZ_LEVEL+", "+QUIZ+", "+SELECTION_1+", "+SELECTION_2+", "+SELECTION_3+", "+SELECTION_4+", "+ANSWER+" FROM "+TABLE_NAME+
                " WHERE "+QUIZ_LEVEL+"=2 ORDER BY RANDOM() LIMIT 10";
        Cursor cursor=null;

        try{
            cursor=db.rawQuery(selectQuery, null);
            db.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
        return cursor;
    }

    public Cursor LoadSQLiteDBCursor_hard(){
        SQLiteDatabase db=this.getReadableDatabase();
        db.beginTransaction();

        String selectQuery="SELECT "+ID+", "+QUIZ_LEVEL+", "+QUIZ+", "+SELECTION_1+", "+SELECTION_2+", "+SELECTION_3+", "+SELECTION_4+", "+ANSWER+" FROM "+TABLE_NAME+
                " WHERE "+QUIZ_LEVEL+"=3 ORDER BY RANDOM() LIMIT 10";
        Cursor cursor=null;

        try{
            cursor=db.rawQuery(selectQuery, null);
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

            insertQuiz(db,"1", "다음 공룡의 이름은 무엇일까요?", "렉스", "디폴로도쿠스", "파라사우롤로푸스", "파키", "3", R.drawable.ark_parasaur, context);
            insertQuiz(db,"1", "다음 공룡의 이름은 무엇일까요?", "알로사우루스", "기가노토사우르스", "아카티나", "마나가르마", "1", R.drawable.ark_allosaurus, context );
            insertQuiz(db,"1", "다음 공룡의 이름은 무엇일까요?", "그리핀", "파라사우롤로푸스", "안킬로사우르스", "케찰", "3", R.drawable.ark_ankylosaurus, context);
            insertQuiz(db,"1", "다음 공룡의 이름은 무엇일까요?", "안킬로사우르스", "바리오닉스", "벨제부포", "검치호", "2", R.drawable.ark_baryonyx, context);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
//            db.endTransaction();
        }
    }

    public void insertQuiz(SQLiteDatabase mdb, String quiz_level, String quiz, String selection1, String selection2, String selection3, String selection4, String answer, int image, Context context){
        ContentValues contentValues=new ContentValues();
        contentValues.put(QUIZ_LEVEL, quiz_level);
        contentValues.put(QUIZ, quiz);
        contentValues.put(SELECTION_1, selection1);
        contentValues.put(SELECTION_2, selection2);
        contentValues.put(SELECTION_3, selection3);
        contentValues.put(SELECTION_4, selection4);
        contentValues.put(ANSWER, answer);
        Drawable imageIntToDrawable= ResourcesCompat.getDrawable(context.getResources(), image, null);
        contentValues.put(IMAGE, getByteArrayFromDrawable(imageIntToDrawable));
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

//

}
