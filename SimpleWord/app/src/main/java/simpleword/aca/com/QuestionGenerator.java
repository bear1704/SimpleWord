package simpleword.aca.com;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import simpleword.aca.com.Db.DBHelper;

public class QuestionGenerator
{
    public static final int PICK_ENGLISH = 11;
    public static final int PICK_KOREAN = 22;
    public static final int PICK_BOTH = 33;


    Context mContext;
    DBHelper dbHelper = DBHelper.getInstance(mContext);
    SQLiteDatabase db = dbHelper.getReadableDatabase();

    ArrayList<String> database; //db에서 가져올
    ArrayList<String> suggestion = new ArrayList<>(); //4지선다 문항 4개
    private String rightAnswerKorStr; //문제에 제시된 영어 단어의 답(한글)
    private String questionEngStr; //문제에 제시할 영어 단어
    private String selectedAnswer; //내가 선택한 답


    public QuestionGenerator(Context context)
    {
        mContext = context;
    }



    public String PickWordFromDB(int mode)
    {
        Word word;

        if(mode == PICK_KOREAN)
        {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT KOREAN FROM WORDS ORDER BY RANDOM() LIMIT 1");
            Cursor cursor = db.rawQuery(sb.toString(), null);
            return cursor.getString(0);
        }
        else if(mode == PICK_ENGLISH)
        {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT ENGLISH FROM WORDS ORDER BY RANDOM() LIMIT 1");
            Cursor cursor = db.rawQuery(sb.toString(), null);
            return cursor.getString(0);
        }
        else if(mode == PICK_BOTH)
        {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT KOREAN, ENGLISH FROM WORDS ORDER BY RANDOM() LIMIT 1");
            Cursor cursor = db.rawQuery(sb.toString(), null);
            return cursor.getString(0);
        }

        Log.v("error_", "PickWord에러");
        return null;
    }


}
