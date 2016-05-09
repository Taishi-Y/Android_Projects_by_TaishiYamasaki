

package me.taishi.TechCrunchReader.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.taishi.TechCrunchReader.R;
import me.taishi.TechCrunchReader.adapters.TabsPagerAdapter;
import me.taishi.TechCrunchReader.views.SlidingTabLayout;


public class SlidingFragment extends Fragment {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sample, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	 setUpPager(view);
         setUpTabColor();
    }
    void setUpPager(View view){
    	 mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
         mViewPager.setAdapter(new TabsPagerAdapter(getActivity()));
         mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
         mSlidingTabLayout.setViewPager(mViewPager); 
    }
    void setUpTabColor(){
    	 mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
 			@Override
 			public int getIndicatorColor(int position) {
 				// TODO Auto-generated method stub
 				return Color.YELLOW;
            }
 			@Override
 			public int getDividerColor(int position) {
 				// TODO Auto-generated method stub
 				return Color.WHITE;
 			}
         });
    }
}
