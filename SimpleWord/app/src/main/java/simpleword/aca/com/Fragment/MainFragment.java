package simpleword.aca.com.Fragment;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Pattern;

import simpleword.aca.com.Adapter.WordListAdapter;
import simpleword.aca.com.Db.DBHelper;
import simpleword.aca.com.MainActivity;
import simpleword.aca.com.Notification;
import simpleword.aca.com.QuestionGenerator;
import simpleword.aca.com.R;
import simpleword.aca.com.Receiver.ScreenService;
import simpleword.aca.com.RecyclerViewItemClickListener;
import simpleword.aca.com.Translator;
import simpleword.aca.com.Word;

public class MainFragment extends Fragment
{

    RecyclerView mRecyclerView;
    private DBHelper dbHelper;
    private EditText inputEditText;
    ArrayList<Word> wordArrayList;
    Translator translator;
    ImageView[] starImage;
    Drawable fillStarImage;
    Drawable emptyStarImage;
    int starCount = 1; //입력 전 선택한 별 카운트
    WordListAdapter wordListAdapter = null;
    WordListAdapter wordListFragmentListAdapter = null;
    private Notification mNotification;
    private boolean isToggle = true;
    Button screenToggleButton;

    Button inputButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View wordListView = inflater.inflate(R.layout.tab_main, container, false);
        translator = new Translator();
        starImage = new ImageView[5];
        fillStarImage = getResources().getDrawable(R.drawable.star_full, null);
        emptyStarImage = getResources().getDrawable(R.drawable.star_empty, null);

        starImage[0] = wordListView.findViewById(R.id.importance1);
        starImage[1] = wordListView.findViewById(R.id.importance2);
        starImage[2] = wordListView.findViewById(R.id.importance3);
        starImage[3] = wordListView.findViewById(R.id.importance4);
        starImage[4] = wordListView.findViewById(R.id.importance5);

        for(int i=0 ; i<5 ; i++)
        {
            starImage[i].setOnClickListener(starButtonListener);
        }

        inputEditText = (EditText) wordListView.findViewById(R.id.edt_main_word);
        inputButton = (Button) wordListView.findViewById(R.id.btn_inputText);
        inputButton.setOnClickListener(inputButtonListener);
        screenToggleButton = (Button) wordListView.findViewById(R.id.btn_lock_screen);
        screenToggleButton.setOnClickListener(screenToggle);

        mRecyclerView = wordListView.findViewById(R.id.rv_wordlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnItemTouchListener(rvListener);

        //dbHelper = new DBHelper(this.getActivity(), "Word", null, 1);
        dbHelper = DBHelper.getInstance(getActivity().getApplicationContext());

        //dbHelper.clearWord();
        //dbHelper.deleteWord("과자");
        wordArrayList = dbHelper.getAllWordsData();

        wordListAdapter = new WordListAdapter(wordArrayList, this.getContext());


        mRecyclerView.setAdapter(wordListAdapter);
        mNotification = new Notification(getActivity().getApplicationContext());
        mNotification.Go();



        return wordListView;
    }


    View.OnClickListener inputButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if (inputEditText.getText().toString().equals(""))
            { }
            else
            {
                addWord(null);
            }
        }
    };

    RecyclerViewItemClickListener rvListener = new RecyclerViewItemClickListener(getActivity(), new RecyclerViewItemClickListener.OnItemClickListener()
    {
        @Override
        public void onItemClick(View view, int position)
        {
            wordArrayList = dbHelper.getAllWordsData();
            wordListAdapter.removeItem(wordArrayList.get(position).getKoreanStr());
        }
    });

    View.OnClickListener starButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.importance1:
                    starImage[0].setImageDrawable(fillStarImage);
                    starImage[1].setImageDrawable(emptyStarImage);
                    starImage[2].setImageDrawable(emptyStarImage);
                    starImage[3].setImageDrawable(emptyStarImage);
                    starImage[4].setImageDrawable(emptyStarImage);
                    starCount = 1;
                    break;
                case R.id.importance2:
                    starImage[0].setImageDrawable(fillStarImage);
                    starImage[1].setImageDrawable(fillStarImage);
                    starImage[2].setImageDrawable(emptyStarImage);
                    starImage[3].setImageDrawable(emptyStarImage);
                    starImage[4].setImageDrawable(emptyStarImage);
                    starCount = 2;
                    break;
                case R.id.importance3:
                    starImage[0].setImageDrawable(fillStarImage);
                    starImage[1].setImageDrawable(fillStarImage);
                    starImage[2].setImageDrawable(fillStarImage);
                    starImage[3].setImageDrawable(emptyStarImage);
                    starImage[4].setImageDrawable(emptyStarImage);
                    starCount = 3;
                    break;
                case R.id.importance4:
                    starImage[0].setImageDrawable(fillStarImage);
                    starImage[1].setImageDrawable(fillStarImage);
                    starImage[2].setImageDrawable(fillStarImage);
                    starImage[3].setImageDrawable(fillStarImage);
                    starImage[4].setImageDrawable(emptyStarImage);
                    starCount = 4;
                    break;
                case R.id.importance5:
                    starImage[0].setImageDrawable(fillStarImage);
                    starImage[1].setImageDrawable(fillStarImage);
                    starImage[2].setImageDrawable(fillStarImage);
                    starImage[3].setImageDrawable(fillStarImage);
                    starImage[4].setImageDrawable(fillStarImage);
                    starCount = 5;
                    break;

            }
        }
    };

    public void addWord(String inputStr)
    {
        String str = null;
        if(inputStr == null)
        {
            str = inputEditText.getText().toString();

        }
        else
        {
            str = inputStr;
            starCount = 5;
        }

        Word word;

        if(isKorean(str))
        {
            String koToEngStr = translator.Translate(str, translator.KO_TO_EN);
            word = new Word(str, koToEngStr, starCount);
            wordListAdapter.addItem(word);
            inputEditText.setText("");
        }
        else
        {
            String engToKoStr = translator.Translate(str, translator.EN_TO_KO);
            word = new Word(engToKoStr, str, starCount);
            wordListAdapter.addItem(word);
            inputEditText.setText("");
        }
    }


    public void fragmentSelected()
    {
        wordListAdapter.resetAdapter();
    }

    public boolean isKorean(String str)
    {
        return Pattern.matches("^[ㄱ-ㅎ가-힣]*$", str);
    }

    public Notification getNotification()
    {
        return mNotification;
    }

    private View.OnClickListener screenToggle = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            if(isToggle)
            {
                DBHelper db = DBHelper.getInstance(getActivity());
                SQLiteDatabase sqdb = db.getReadableDatabase();
                long count = DatabaseUtils.queryNumEntries(sqdb,"WORDS");
                db.close();
                sqdb.close();

                if(count > 5)
                {
                    Intent lockScreenIntent = new Intent(getActivity(), ScreenService.class);
                    getActivity().startService(lockScreenIntent);
                    isToggle = false;
                }
                else
                {
                    Toast.makeText(getActivity(), "등록된 단어 수가 너무 적습니다!", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Intent lockScreenIntent = new Intent(getActivity(), ScreenService.class);
                getActivity().stopService(lockScreenIntent);
                isToggle = true;
            }

        }
    };

}

