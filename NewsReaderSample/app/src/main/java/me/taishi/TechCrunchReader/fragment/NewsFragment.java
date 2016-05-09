package me.taishi.TechCrunchReader.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.taishi.TechCrunchReader.R;

import me.taishi.TechCrunchReader.adapters.NewsDataAdapter;
import me.taishi.TechCrunchReader.models.RSSFeed;
import me.taishi.TechCrunchReader.utils.ConnectionDetector;
import me.taishi.TechCrunchReader.utils.Constants;
import me.taishi.TechCrunchReader.utils.NewsFeedParser;


import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NewsFeedParser mNewsFeeder;
    private List<RSSFeed>  mRssFeedList = new ArrayList<RSSFeed>();

    private SwipeRefreshLayout mSwipeRefreshLayout;



//    public static NewsFragment newInstance(CharSequence title,int position) {
//        Bundle bundle = new Bundle();
//        bundle.putCharSequence("title", title);
//        bundle.putInt("position", position);
//        NewsFragment fragment = new NewsFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, null);

        Log.i("Fragment", "OncreateView()");
        return mSwipeRefreshLayout;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 5000);
    }

    @Override
    public void onStart() {
        super.onStart();


        Log.i("Fragment", "onStart()");

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        performAsyncTask();

    }

    public void performAsyncTask() {
        if (mRssFeedList.size() > 0) {
            settingAdapter();
        } else {
            boolean isNetworkAvailable = ConnectionDetector.isConnectingToInternet(getActivity().getApplicationContext());
            if (isNetworkAvailable) {
                Bundle args = getArguments();
                if (args != null)
                   {
                       new DoRssFeedTask().execute(Constants.TOPSTORIES[args.getInt("position")]);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Oops!!! News Fragment", Toast.LENGTH_LONG).show();
                    }
                }
             else {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class DoRssFeedTask extends AsyncTask<String, Void, List<RSSFeed>> {
        ProgressDialog prog;
        String jsonStr = null;

        @Override
        protected void onPreExecute() {
           /* prog = new ProgressDialog(getActivity());
            prog.setMessage("Loading...");
            prog.setCancelable(false);
            prog.show();*/
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
           /* prog.dismiss();*/
            settingAdapter();
            Log.i("Fragment ASyncTask", "onPostExecute()");
        }
    }

    public void settingAdapter() {
        mAdapter = new NewsDataAdapter(getActivity(), mRssFeedList);
        int count = mRssFeedList.size();
        if (count != 0 && mAdapter != null) {
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}

