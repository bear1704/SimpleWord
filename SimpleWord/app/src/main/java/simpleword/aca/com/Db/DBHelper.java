package simpleword.aca.com.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import simpleword.aca.com.Word;

public class DBHelper extends SQLiteOpenHelper
{

    private Context context;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE WORDS ( ");
        sb.append(" KOREAN TEXT PRIMARY KEY, ");
        sb.append(" ENGLISH TEXT, ");
        sb.append(" INTEGER STAR ) ");

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
