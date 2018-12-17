package simpleword.aca.com.Fragment;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import simpleword.aca.com.Db.DBHelper;
import simpleword.aca.com.LockScreenActivity;
import simpleword.aca.com.QuestionGenerator;
import simpleword.aca.com.R;

public class WordTestFragment extends Fragment
{

    private TextView tvTestQtuestion;
    private Button btnTestAnswer1;
    private Button btnTestAnswer2;
    private Button btnTestAnswer3;
    private Button btnTestAnswer4;

    private TextView tvCurrentPage;
    private TextView tvMaxPage;
    private TextView tvCurrentPoint;

    private TextView tvResult;

    private LinearLayout llTest;

    private QuestionGenerator questionGenerator;

    private int currentQuestion = 1;
    private int maxQuestion = 20;
    private int currentPoint = 0;
    private long count;
    private View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View wordTestView = inflater.inflate(R.layout.tab_wordtest, container, false);

        v = wordTestView;

        DBHelper db = DBHelper.getInstance(getActivity());
        SQLiteDatabase sqdb = db.getReadableDatabase();
        count = DatabaseUtils.queryNumEntries(sqdb,"WORDS");
        db.close();
        sqdb.close();


        if(count > 5)
        {
            Init(wordTestView);
        }

        return wordTestView;
    }

    public void Init(View view)
    {
        View wordTestView = view;

        tvTestQtuestion = (TextView) wordTestView.findViewById(R.id.tv_test_question_english_str);
        btnTestAnswer1 = (Button) wordTestView.findViewById(R.id.btn_test_answer1);
        btnTestAnswer2 = (Button) wordTestView.findViewById(R.id.btn_test_answer2);
        btnTestAnswer3 = (Button) wordTestView.findViewById(R.id.btn_test_answer3);
        btnTestAnswer4 = (Button) wordTestView.findViewById(R.id.btn_test_answer4);

        tvCurrentPage = (TextView) wordTestView.findViewById(R.id.tv_test_current_question);
        tvMaxPage = (TextView) wordTestView.findViewById(R.id.tv_test_all_question);
        tvCurrentPoint = (TextView) wordTestView.findViewById(R.id.tv_test_correct);

        questionGenerator = new QuestionGenerator(getActivity());

        currentQuestion = 1;
        currentPoint = 0;


        tvCurrentPage.setText(String.valueOf(currentQuestion));
        tvMaxPage.setText(String.valueOf(maxQuestion));
        tvCurrentPoint.setText(String.valueOf(currentPoint)); //초기값

        btnTestAnswer1.setOnClickListener(listener);
        btnTestAnswer2.setOnClickListener(listener);
        btnTestAnswer3.setOnClickListener(listener);
        btnTestAnswer4.setOnClickListener(listener);

        llTest = (LinearLayout) wordTestView.findViewById(R.id.ll_test);
        tvResult = (TextView) wordTestView.findViewById(R.id.tv_test_result);



        llTest.setVisibility(View.VISIBLE);
        tvResult.setVisibility(View.GONE);
        GenerateQuestion();
    }

    public void GenerateQuestion()
    {
        questionGenerator.Generate();
        tvTestQtuestion.setText(questionGenerator.getQuestionEngStr());
        btnTestAnswer1.setText(questionGenerator.getSuggestion().get(0));
        btnTestAnswer2.setText(questionGenerator.getSuggestion().get(1));
        btnTestAnswer3.setText(questionGenerator.getSuggestion().get(2));
        btnTestAnswer4.setText(questionGenerator.getSuggestion().get(3));

    }

    View.OnClickListener listener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(currentQuestion < maxQuestion)
            {
                Button b = (Button) view;

                if (questionGenerator.isCorrectAnswer(b.getText().toString()))
                {
                    Toast.makeText(getActivity(), "정답입니다!", Toast.LENGTH_SHORT).show();
                    GenerateQuestion();
                    currentPoint++;
                    currentQuestion++;
                    tvCurrentPage.setText(String.valueOf(currentQuestion));
                    tvCurrentPoint.setText(String.valueOf(currentPoint)); //초기값
                } else
                {
                    Toast.makeText(getActivity(), "틀렸습니다....", Toast.LENGTH_SHORT).show();
                    GenerateQuestion();
                    currentQuestion++;
                    tvCurrentPage.setText(String.valueOf(currentQuestion));
                }
            }
            else
            {
                StringBuffer sb = new StringBuffer();
                sb.append("테스트 종료\n");
                sb.append("\n맞춘 문제 : ");
                sb.append(String.valueOf(currentPoint));
                sb.append("\n\n 점수 : ");
                sb.append(String.valueOf(currentPoint*5));

                tvResult.setVisibility(View.VISIBLE);
                llTest.setVisibility(View.GONE);


                tvResult.setText(sb);
            }
        }
    };
    public void fragmentSelected()
    {
        if(count> 5)
        {
            Init(v);
        }
    }

}
