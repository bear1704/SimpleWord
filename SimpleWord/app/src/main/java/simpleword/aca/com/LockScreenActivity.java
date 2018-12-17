package simpleword.aca.com;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LockScreenActivity extends Activity
{
    private TextView tvQuestion;
    private Button btnAnswer1;
    private Button btnAnswer2;
    private Button btnAnswer3;
    private Button btnAnswer4;

    QuestionGenerator questionGenerator;


    @Override
    protected  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


        tvQuestion = (TextView) findViewById(R.id.tv_question_english_str);
        btnAnswer1 = (Button) findViewById(R.id.btn_answer1);
        btnAnswer2 = (Button) findViewById(R.id.btn_answer2);
        btnAnswer3 = (Button) findViewById(R.id.btn_answer3);
        btnAnswer4 = (Button) findViewById(R.id.btn_answer4);
        questionGenerator = new QuestionGenerator(this);

        GenerateQuestion();

        btnAnswer1.setOnClickListener(btnOnClickListener);
        btnAnswer2.setOnClickListener(btnOnClickListener);
        btnAnswer3.setOnClickListener(btnOnClickListener);
        btnAnswer4.setOnClickListener(btnOnClickListener);


    }

    public void GenerateQuestion()
    {
        questionGenerator.Generate();
        tvQuestion.setText(questionGenerator.getQuestionEngStr());
        btnAnswer1.setText(questionGenerator.getSuggestion().get(0));
        btnAnswer2.setText(questionGenerator.getSuggestion().get(1));
        btnAnswer3.setText(questionGenerator.getSuggestion().get(2));
        btnAnswer4.setText(questionGenerator.getSuggestion().get(3));
    }

    View.OnClickListener btnOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Button b = (Button) view;

            if(questionGenerator.isCorrectAnswer(b.getText().toString()))
            {
                Toast.makeText(LockScreenActivity.this, "정답입니다!" , Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(LockScreenActivity.this, "틀렸습니다!" , Toast.LENGTH_SHORT).show();
                GenerateQuestion();
            }
        }
    };


}
