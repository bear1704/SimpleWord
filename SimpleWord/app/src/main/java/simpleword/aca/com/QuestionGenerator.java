package simpleword.aca.com;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import simpleword.aca.com.Db.DBHelper;

public class QuestionGenerator
{
    public static final int PICK_ENGLISH = 11;
    public static final int PICK_KOREAN = 22;
    public static final int PICK_BOTH = 33;
    public final int SUGGESTION_COUNT = 4;

    Context mContext;
    DBHelper dbHelper;
    SQLiteDatabase db;

    ArrayList<String> suggestion; //4지선다 문항 4개(한글)
    private String rightAnswerKorStr; //문제에 제시된 영어 단어의 답(한글)
    private String questionEngStr; //문제에 제시할 영어 단어
    private String selectedAnswer; //내가 선택한 답
    private String suggestionCandidateStr;




    public QuestionGenerator(Context context)
    {
        mContext = context;
        suggestion = new ArrayList<>();
         dbHelper = DBHelper.getInstance(mContext);
         db = dbHelper.getReadableDatabase();

    }

    public void Generate()
    {

        PickWordFromDB(PICK_BOTH); //문제, 정답 할당

        while(suggestion.size() < SUGGESTION_COUNT)
        {
            suggestionCandidateStr = PickWordFromDB(PICK_KOREAN); //하나 뽑아오기

            if(!suggestionCandidateStr.equals(rightAnswerKorStr))
            {
                suggestion.add(suggestionCandidateStr); //제시문제와 답안후보의 값이 다르다면, 답안후보 등록
            }

        }

        Random rnd = new Random();
        int num = rnd.nextInt(3);//0~3까지

        suggestion.add(num, rightAnswerKorStr); //4개중 하나에 옳은 정답 삽입

    }

    public boolean isCorrectAnswer(String selectedStr)
    {
        if(selectedStr.equals(rightAnswerKorStr))
            return true;
        else
            return false;
    }

    public String getRightAnswerKorStr()
    {
        return rightAnswerKorStr;
    }
    public String getQuestionEngStr()
    {
        if(questionEngStr == null)
        {
            Log.d("error_", "널인데? 왜널?");
        }

        return questionEngStr;
    }
    public ArrayList<String> getSuggestion()
    {
        return suggestion;
    }


    public String PickWordFromDB(int mode)
    {
        String str = null;

        if(mode == PICK_KOREAN)
        {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT KOREAN FROM WORDS ORDER BY RANDOM() LIMIT 1");
            Cursor cursor = db.rawQuery(sb.toString(), null);
            while(cursor.moveToNext())
            {
                str = cursor.getString(0);
            }
            return str;
        }
        else if(mode == PICK_ENGLISH)
        {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT ENGLISH FROM WORDS ORDER BY RANDOM() LIMIT 1");
            Cursor cursor = db.rawQuery(sb.toString(), null);

            while(cursor.moveToNext())
            {
                str = cursor.getString(0);
            }
            return str;

        }
        else if(mode == PICK_BOTH)
        {
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT KOREAN, ENGLISH FROM WORDS ORDER BY RANDOM() LIMIT 1 ");
            Cursor cursor = db.rawQuery(sb.toString(), null);

            while(cursor.moveToNext())
            {
                rightAnswerKorStr = cursor.getString(0);
                questionEngStr = cursor.getString(1);
            }
            return null;
        }

        Log.v("error_", "PickWord에러");
        return null;
    }


}
