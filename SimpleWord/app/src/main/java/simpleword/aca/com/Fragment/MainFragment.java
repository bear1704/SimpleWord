package simpleword.aca.com.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;

import simpleword.aca.com.Adapter.WordListAdapter;
import simpleword.aca.com.Db.DBHelper;
import simpleword.aca.com.MainActivity;
import simpleword.aca.com.R;
import simpleword.aca.com.Word;

public class MainFragment extends Fragment
{

    RecyclerView mRecyclerView;
    private DBHelper dbHelper;
    ArrayList<Word> wordArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View wordListView = inflater.inflate(R.layout.tab_main, container, false);

        mRecyclerView = wordListView.findViewById(R.id.rv_wordlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        dbHelper = new DBHelper(this.getActivity(), "Word", null, 1);

        dbHelper.addWord(new Word("한국", "korea", 2));
        dbHelper.addWord(new Word("중국", "china", 1));


        wordArrayList = dbHelper.getAllWordsData();

        WordListAdapter wordListAdapter = new WordListAdapter(wordArrayList, this.getContext(), dbHelper);

        mRecyclerView.setAdapter(wordListAdapter);


        dbHelper.addWord(new Word("한국", "korea", 2));
        dbHelper.addWord(new Word("중국", "china", 1));


        return wordListView;
    }



}

