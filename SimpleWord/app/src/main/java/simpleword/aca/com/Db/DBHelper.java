package simpleword.aca.com.Db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

import simpleword.aca.com.Word;

public class DBHelper extends SQLiteOpenHelper
{

    private Context context;
    public static DBHelper helper = null;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        this.context = context;
    }

    public static DBHelper getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) //싱글톤
    {
        if(helper == null)
        {
            helper = new DBHelper(context, name, factory, version);
        }

        return helper;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE WORDS ( ");
        sb.append(" KOREAN TEXT, ");
        sb.append(" ENGLISH TEXT, ");
        sb.append(" STAR INTEGER ) ");

        sqLiteDatabase.execSQL(sb.toString());

        Toast.makeText(context, "DB Table 생성완료", Toast.LENGTH_LONG).show();
    }

    public void addWord(Word word)
    {

        SQLiteDatabase db = getWritableDatabase();

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO WORDS ( ");
        sb.append(" KOREAN, ENGLISH, STAR ) ");
        sb.append(" VALUES( ?, ?, ?) ");

        db.execSQL(sb.toString(), new Object[]
                {
                    word.getKoreanStr(),
                        word.getEnglishStr(),
                        word.getStar()
                });
        Toast.makeText(context, "Insert 완료", Toast.LENGTH_LONG).show();

    }

    public void clearWord()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(" DELETE FROM WORDS");
    }

    public void deleteWord(String str)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM WORDS WHERE KOREAN=" + str);

    }

    public ArrayList<Word> getAllWordsData()
    {
        ArrayList<Word> words = new ArrayList<>();
        Word word = null;

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT KOREAN, ENGLISH, STAR FROM WORDS ");

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);

        while(cursor.moveToNext())
        {
            word = new Word(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
            words.add(word);

        }

        return words;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        Toast.makeText(context, "버전 변경이 감지되었습니다", Toast.LENGTH_LONG).show();
    }


    public void testDB()
    {
        SQLiteDatabase db = getReadableDatabase();
    }

}