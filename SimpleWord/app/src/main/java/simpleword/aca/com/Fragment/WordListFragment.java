package simpleword.aca.com.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import simpleword.aca.com.Adapter.WordListAdapter;
import simpleword.aca.com.Db.DBHelper;
import simpleword.aca.com.R;
import simpleword.aca.com.Word;

public class WordListFragment extends Fragment
{

    private DBHelper dbHelper;
    ArrayList<Word> wordArrayList;
    WordListAdapter wordListAdapter = null;
    RecyclerView mRecyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View wordListView = inflater.inflate(R.layout.tab_wordlist, container, false);

        mRecyclerView = wordListView.findViewById(R.id.rv_list_wordlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dbHelper = DBHelper.getInstance(getActivity().getApplicationContext());

        wordArrayList = dbHelper.getAllWordsData();

        wordListAdapter = new WordListAdapter(wordArrayList, this.getContext());

        mRecyclerView.setAdapter(wordListAdapter);

        return wordListView;
    }


    public void fragmentSelected()
    {
        super.onResume();
        wordArrayList = dbHelper.getAllWordsData();
        wordListAdapter = new WordListAdapter(wordArrayList, this.getContext());
        mRecyclerView.setAdapter(wordListAdapter);
        Log.v("error_", "두번째 selected");
    }

}
