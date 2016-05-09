package me.taishi.TechCrunchReader.adapters;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import me.taishi.TechCrunchReader.R;
import me.taishi.TechCrunchReader.models.RSSFeed;
import me.taishi.TechCrunchReader.utils.ConnectionDetector;
import me.taishi.TechCrunchReader.utils.Constants;
import me.taishi.TechCrunchReader.utils.NewsFeedParser;

import java.util.ArrayList;
import java.util.List;

public class TabsPagerAdapter extends PagerAdapter {
    String tabs[] = {"稼げるまとめ速報", "株式速報", "円速", "マネーニュース2ch", "FX2ちゃんねる","お金ちゃんねる"};

    Activity activity;


    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private NewsFeedParser mNewsFeeder;
    private List<RSSFeed> mRssFeedList = new ArrayList<RSSFeed>();

    public TabsPagerAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Inflate a new layout from our resources
        View view = null;


        view = activity.getLayoutInflater().inflate(R.layout.pager_item, container, false);

        // Add the newly created View to the ViewPager


        // Retrieve a RecyclerView from the inflated View, and update it's data

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));

        performAsyncTask(position, mRecyclerView);


        ((ViewPager) container).addView(view);
        // Return the View
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        ((ViewPager) container).removeView((View) view);
    }


    public void performAsyncTask(int pos, RecyclerView mrecyclerView) {


        boolean isNetworkAvailable = ConnectionDetector.isConnectingToInternet(activity.getApplicationContext());

        if (isNetworkAvailable) {


            new DoRssFeedTask(mrecyclerView, progressBar).execute(Constants.TOPSTORIES[pos]);


        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_LONG).show();
        }


    }


    public class DoRssFeedTask extends AsyncTask<String, Void, List<RSSFeed>> {
        ProgressBar prog;
        String jsonStr = null;
        NewsFeedParser mNewsFeeder;

        RecyclerView recyclerView;
        ProgressBar pbar;


        public DoRssFeedTask(RecyclerView recyclerView1, ProgressBar progBar) {
            super();
            this.recyclerView = recyclerView1;
            this.pbar = progBar;
        }


        @Override
        protected void onPreExecute() {

            pbar.setVisibility(View.VISIBLE);
            Log.i("Fragment ASyncTask", "onPreExecute()");
        }

        @Override
        protected List<RSSFeed> doInBackground(String... params) {

            for (String urlVal : params) {
                mNewsFeeder = new NewsFeedParser(urlVal);
            }
            mRssFeedList = mNewsFeeder.parseXmlData();

            Log.i("Fragment ASyncTask", "doInBackground()");

            return mRssFeedList;
        }

        @Override
        protected void onPostExecute(List<RSSFeed> result) {
            pbar.setVisibility(View.GONE);


            settingAdapter(recyclerView);


            Log.i("Fragment ASyncTask", "onPostExecute()");
        }

    }


    public void settingAdapter(RecyclerView rv) {
        NewsDataAdapter mAdapter = new NewsDataAdapter(activity, mRssFeedList);
        int count = mRssFeedList.size();
        if (count != 0 && mAdapter != null) {
            rv.setAdapter(mAdapter);
        }
    }


}