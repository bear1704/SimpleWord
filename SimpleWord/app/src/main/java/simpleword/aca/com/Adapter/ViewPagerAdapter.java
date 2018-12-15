package simpleword.aca.com.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter
{

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> fragmentTitleList = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) { super(fm); }

    @Override
    public Fragment getItem(int position) { return fragmentList.get(position); }
    @Override
    public String getPageTitle(int position) { return fragmentTitleList.get(position);}
    @Override
    public int getCount() {return fragmentList.size();}

    public void addFragment(Fragment frag, String str)
    {
        fragmentList.add(frag);
        fragmentTitleList.add(str);
    }

}
