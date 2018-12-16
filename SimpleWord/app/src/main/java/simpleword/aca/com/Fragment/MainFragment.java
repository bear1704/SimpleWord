package simpleword.aca.com.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import simpleword.aca.com.Adapter.WordListAdapter;
import simpleword.aca.com.R;
import simpleword.aca.com.Word;

public class MainFragment extends Fragment
{

    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View wordListView = inflater.inflate(R.layout.tab_main, container, false);

        mRecyclerView = wordListView.findViewById(R.id.rv_wordlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Word> wordArrayList = new ArrayList<>();
        wordArrayList.add(new Word("안녕","hi",4));

        WordListAdapter wordListAdapter = new WordListAdapter(wordArrayList, this.getContext());

        mRecyclerView.setAdapter(wordListAdapter);
        return wordListView;
    }

}

