package me.taishi_y.englishsensei.Adapter;

/**
 * Created by yamasakitaishi on 2016/04/23.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import me.taishi_y.englishsensei.Fragment.FragmentTabLearn;
import me.taishi_y.englishsensei.Fragment.FragmentTabTeach;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentTabLearn tab1 = new FragmentTabLearn();
                return tab1;
            case 1:
                FragmentTabTeach tab2 = new FragmentTabTeach();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
