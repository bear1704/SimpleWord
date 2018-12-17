package simpleword.aca.com;

import android.app.Notification;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import simpleword.aca.com.Adapter.ViewPagerAdapter;
import simpleword.aca.com.Db.DBHelper;
import simpleword.aca.com.Fragment.MainFragment;
import simpleword.aca.com.Fragment.WordListFragment;
import simpleword.aca.com.Fragment.WordTestFragment;
import simpleword.aca.com.Receiver.ScreenService;

import static simpleword.aca.com.Notification.KEY_TEXT_REPLY;
import static simpleword.aca.com.Notification.channelId;

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
    DBHelper dbHelper;


    private simpleword.aca.com.Notification mNotification;

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
        dbHelper = DBHelper.getInstance(getApplicationContext());
        IntentFilter filter = new IntentFilter();
        filter.addAction(KEY_TEXT_REPLY);
        registerReceiver(mBroadcastReceiver, filter);

        isStart = true;



    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            reply(intent);
        }
    };


   private void reply(Intent intent)
   {
       Bundle bundle = RemoteInput.getResultsFromIntent(intent);
       if(bundle != null)
       {
           //Toast.makeText(MainActivity.this, "리플라이 성공!", Toast.LENGTH_SHORT).show();
            String messageText = bundle.getString(KEY_TEXT_REPLY);

            if(messageText.equals(""))
            {

            }
            else
            {
                mainFragment.addWord(messageText);

                NotificationCompat.Builder repliedNotification = new NotificationCompat.Builder(this, simpleword.aca.com.Notification.channelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentText("Replied");

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                notificationManagerCompat.notify(Integer.parseInt(simpleword.aca.com.Notification.channelId), repliedNotification.build());
                notificationManagerCompat.cancel(Integer.parseInt(channelId));
                mainFragment.getNotification().Go();
            }


       }
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

