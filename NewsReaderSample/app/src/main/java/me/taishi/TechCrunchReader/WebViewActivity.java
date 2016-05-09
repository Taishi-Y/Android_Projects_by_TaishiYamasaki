package me.taishi.TechCrunchReader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class WebViewActivity extends ActionBarActivity {


    private WebView webView1;
    private Toolbar toolbar;
    private String title;
    private String url;
    TextView tv;
    String joinedArticle;

    Document document;
    Elements getTitle,getArticle,getBoxTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(me.taishi.TechCrunchReader.R.layout.activity_webview);
        //  this.getWindow().requestFeature(Window.FEATURE_PROGRESS);

        toolbar = (Toolbar) findViewById(me.taishi.TechCrunchReader.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        title = getIntent().getExtras().getString("title");
        url = getIntent().getExtras().getString("url");


        tv = (TextView) findViewById(R.id.textView);


//        //広告
//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


        getSupportActionBar().setTitle(title);
        taskExe();



        if (savedInstanceState != null) {
            ((WebView) findViewById(me.taishi.TechCrunchReader.R.id.webView1)).restoreState(savedInstanceState);
        } else {

            webView1 = (WebView) findViewById(me.taishi.TechCrunchReader.R.id.webView1);


            webView1.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            webView1.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView1.getSettings().setJavaScriptEnabled(true);
            webView1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);


            final Activity activity = this;


            webView1.setWebViewClient(new WebViewClient()

            {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view,
                                                        String url) {
                    // TODO Auto-generated method stub
                    view.loadUrl(url);
                    return true;
                }
            });

            webView1.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    // Activities and WebViews measure progress with different scales.
                    // The progress meter will automatically disappear when we reach 100%
                    activity.setProgress(progress * 1000);
                }
            });

            webView1.setWebViewClient(new WebViewClient() {
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                }
            });



            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Code for WebView goes here

//                    webView1.loadData(getArticle.toString(),getArticle.toString(),null);
                }
            });



        }


    }



//    @Override
//    protected void onSaveInstanceState(Bundle outState ){
//        ((WebView) findViewById(me.taishi.TechCrunchReader.R.id.webView1)).saveState(outState);
//    }



    private void taskExe(){
//        final String param0 = etitText.getText().toString();

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>(){


            @Override
            protected Void doInBackground(Void... params) {

                try {
                    document = Jsoup.connect(url).get();
                    getTitle = document.getElementsByTag("title");
                    getBoxTitle = document.select("div.phase1_box");
                    getArticle = document.select("div.phase1_texts");
//                    getArticle = document.className("phase1_texts");


                    joinedArticle = getBoxTitle.toString() + getArticle.toString();


//                    Element masthead = doc.select("div.masthead").first();
                    // div with class=masthead
                    // こちらでもtitleを取得できる
                    // String title = document.title();
//                    phase1_texts
                    Log.i("title",getTitle.toString());
                    Log.i("naiyou",getArticle.toString());
//                    tv.setText(title.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result){
//                tv.setText(getArticle.toString());
                webView1.loadData(joinedArticle,"text/html; charset=utf-8", "utf-8");
            }
        };
        task.execute();
//        tv.setText(getArticle.toString());
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView1.canGoBack()) {
            webView1.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

}
