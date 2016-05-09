package me.taishi.TechCrunchReader.utils;

import android.util.Log;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import me.taishi.TechCrunchReader.models.RSSFeed;

public class NewsFeedParser {
    private InputStream urlStream;
    private XmlPullParserFactory factory;
    private XmlPullParser parser;

    private List<RSSFeed> rssFeedList;
    private RSSFeed rssFeed;

    private String urlString;
    private String tagName;
    private String title;
    private String link;
    private String description;
    private String category;
    private String pubDate;
    private String guid;
    private String feedburner;

    private String thumbnailUrl;



    public static final String ITEM = "item";
    public static final String CHANNEL = "channel";
    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY = "category";
    public static final String PUBLISHEDDATE = "pubDate";
    public static final String GUID = "guid";
    public static final String FEEDBURNERORIGLINK = "feedburner:origLink";

//    public static final String THUMBNAIL = "description";
    public static final String THUMBNAILURL ="media:thumbnail";


    public NewsFeedParser(String urlString) {
        this.urlString = urlString;
    }

    public static InputStream downloadStream(String urlString) throws IOException {
        URL url = new URL(urlString);
       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }




    public List<RSSFeed> parseXmlData() {
        try {
            int count = 0;
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            urlStream = downloadStream(urlString);
            parser.setInput(urlStream, null);
            int eventType = parser.getEventType();
            boolean done = false;
            rssFeed = new RSSFeed();
            rssFeedList = new ArrayList<RSSFeed>();
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (tagName.equals(ITEM)) {
                            rssFeed = new RSSFeed();
                        }
                        if (tagName.equals(TITLE)) {
                            title = parser.nextText().toString();
                            Log.i("title",title);
                        }
                        if (tagName.equals(LINK)) {
                            link = parser.nextText().toString();
                        }
                        if (tagName.equals(DESCRIPTION)) {
                            description = parser.nextText().toString();
                        }
                        if (tagName.equals(CATEGORY)) {
                            category = parser.nextText().toString();
                        }
                        if (tagName.equals(PUBLISHEDDATE)) {
                            pubDate = parser.nextText().toString();

                            //日にちを短縮する処理
                            pubDate = pubDate.substring(0,17);
                        }
                        if (tagName.equals(GUID)) {
                            guid = parser.nextText().toString();
                        }

                        //加えたサムネイルの処理
                        if (tagName.equals(THUMBNAILURL)) {

                            // 部分一致です
                            thumbnailUrl = parser.getAttributeValue(null,"url");

                            if (thumbnailUrl.indexOf("crop") != -1) {
                                System.out.println( "部分一致です" );
                            } else {
                                System.out.println( "部分一致ではありません" );
                                //置換
                                thumbnailUrl = thumbnailUrl.replace("http","https");
                                //切り出した文字列を表示
//                            thumbnailUrl = thumbnailUrl.substring(0,thumbnailUrl.indexOf("?"));

                                Log.i("thumbnailUrl",thumbnailUrl);
                            }


                        }

                        if (tagName.equals(FEEDBURNERORIGLINK)) {
                            feedburner = parser.nextText().toString();
                            Log.i("burner",feedburner);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equals(CHANNEL)) {
                            done = true;
                        } else if (tagName.equals(ITEM)) {

                            rssFeed = new RSSFeed(title, link, description, category, pubDate,
                                    guid, feedburner, thumbnailUrl);

                           // Log.i("RSSFeedItem", title + " " + link + " " + description + " " + category + " " + pubDate + guid + " " + feedburner);

                            rssFeedList.add(rssFeed);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("RSSCount", " : "+Integer.toString(rssFeedList.size()).toString());
        return rssFeedList;

    }
}
