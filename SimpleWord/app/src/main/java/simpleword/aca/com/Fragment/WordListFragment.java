package simpleword.aca.com.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import simpleword.aca.com.Adapter.WordListAdapter;
import simpleword.aca.com.Db.DBHelper;
import simpleword.aca.com.R;
import simpleword.aca.com.RecyclerViewItemClickListener;
import simpleword.aca.com.Word;

public class WordListFragment extends Fragment
{

    private DBHelper dbHelper;
    ArrayList<Word> wordArrayList;
    WordListAdapter wordListAdapter = null;
    RecyclerView mRecyclerView;
    Spinner listSpinner;
    String[] spinnerItem;
    ArrayAdapter<String> spinnerAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View wordListView = inflater.inflate(R.layout.tab_wordlist, container, false);

        mRecyclerView = wordListView.findViewById(R.id.rv_list_wordlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dbHelper = DBHelper.getInstance(getActivity().getApplicationContext());

        wordArrayList = dbHelper.getAllWordsData();

        wordListAdapter = new WordListAdapter(wordArrayList, this.getContext());

        mRecyclerView.setAdapter(wordListAdapter);
        mRecyclerView.addOnItemTouchListener(rvListener);

        spinnerItem = new String[]{"알파벳 순으로 정렬", "중요도 순으로 정렬"};
        spinnerAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                spinnerItem);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        listSpinner = (Spinner) wordListView.findViewById(R.id.list_spinner);
        listSpinner.setOnItemSelectedListener(spinnerListener);
        listSpinner.setAdapter(spinnerAdapter);
        return wordListView;
    }

    public void deleteWord(String str)
    {

    }


    AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
        {
            if(position == 0)
            {
                dbHelper.setMode(0);
                wordListAdapter.resetAdapter();

                Log.v("error_", "zero");
            }
            else if(position == 1)
            {
                dbHelper.setMode(1);
                wordListAdapter.resetAdapter();
                Log.v("error_", "one");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {

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


    public void fragmentSelected() //프래그먼트가 보이는 상태일 때, db 초기화
    {
        super.onResume();
        wordListAdapter.resetAdapter();
    }

}
