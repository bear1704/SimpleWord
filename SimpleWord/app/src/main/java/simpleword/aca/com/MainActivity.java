package simpleword.aca.com;

import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import simpleword.aca.com.Adapter.ViewPagerAdapter;
import simpleword.aca.com.Db.DBHelper;
import simpleword.aca.com.Fragment.MainFragment;
import simpleword.aca.com.Fragment.WordListFragment;
import simpleword.aca.com.Fragment.WordTestFragment;

public class MainActivity extends AppCompatActivity
{
    private static final String clientId = "eWBogsUUe9GhsNs7747O";
    private static final String clientSecret = "vLR36Xfcxr";
    Translator tl;
    private String translatedText;
    private ViewPager mViewPager;
    private ViewPagerAdapter mPageAdapter;
    private TabLayout mTabLayout;

    MainFragment mainFragment;
    WordListFragment wordListFragment;
    WordTestFragment wordTestFragment;

    boolean isStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tv_transResult = findViewById(R.id.text1);
        tl = new Translator();

        mainFragment = new MainFragment();
        wordListFragment = new WordListFragment();
        wordTestFragment = new WordTestFragment();

        mViewPager = findViewById(R.id.vp_main_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_main_layout);
        mTabLayout.addOnTabSelectedListener(tabSelectedListener);


        setupViewPager();
        mTabLayout.setupWithViewPager(mViewPager);

        isStart = true;

    }

    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener()
    {
        @Override
        public void onTabSelected(TabLayout.Tab tab)
        {
                int position = tab.getPosition();

                if(isStart)
                {
                    switch (position)
                    {
                        case 0:
                            mainFragment.fragmentSelected();
                            break;
                        case 1:
                            wordListFragment.fragmentSelected();
                            break;
                        case 2:
                            break;

                    }
                }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab)
        {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab)
        {

        }
    };

    private void setupViewPager()
    {
        mPageAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPageAdapter.addFragment(mainFragment, "메인");
        mPageAdapter.addFragment(wordListFragment, "목록");
        mPageAdapter.addFragment(wordTestFragment, "테스트");

        mViewPager.setAdapter(mPageAdapter);


    }


}

