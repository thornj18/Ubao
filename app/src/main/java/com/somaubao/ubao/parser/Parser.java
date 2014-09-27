package com.somaubao.ubao.parser;

import android.text.format.DateUtils;
import android.util.Log;

import com.somaubao.ubao.models.PostItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tonny on 9/5/2014.
 */
public class Parser {
    private String urlString;

    private String post_Title;
    private String post_Link;
    private String post_description;
    private String post_ImageURL;
    private String post_Time;
    SimpleDateFormat dateFormat;

    public Parser(String urlString) {
        this.urlString = urlString;
    }

    //The Method below initiates the internet connection
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }
// The parse method takes care of pulling data each tag in the Xml document and stores the results in an arraylist
    public List<PostItem> parse() {
        List<PostItem> postIitemList = null;
        try {

            int count = 0;
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            InputStream urlStream;
            urlStream = downloadUrl(urlString);
            parser.setInput(urlStream, null);
            int eventType = parser.getEventType();
            boolean done = false;
            PostItem postItem = new PostItem();
            postIitemList = new ArrayList<PostItem>();

             dateFormat = new SimpleDateFormat(
                          "EEE, DD MMM yyyy HH:mm");
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (tagName.equals("item")) {
                            postItem = new PostItem();
                        }
                        if (tagName.equalsIgnoreCase("title")) {
                            post_Title = readTitle(parser);
                        }
                        if (tagName.equalsIgnoreCase("pubDate")) {
                            post_Time = readDate(parser);
                        }
                        if (tagName.equalsIgnoreCase("link")) {
                            post_Link = readLink(parser);
                        }
                        if (tagName.equalsIgnoreCase("description")) {
                            post_description = readDescription(parser);
                        }
                        if (tagName.equalsIgnoreCase("media:thumbnail")) {
                            post_ImageURL = readImageURL(parser);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equals("channel")) {
                            done = true;
                        } else if (tagName.equals("item")) {

                            postItem = new PostItem(post_Title,post_ImageURL, post_Time,post_Link, post_description);
                            postIitemList.add(postItem);
                           //Log.d("The Image links are:", post_ImageURL);

                        }
                        break;
                }
                eventType = parser.next();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return postIitemList;
    }

    private String readImageURL(XmlPullParser parser)  throws IOException, XmlPullParserException  {
        String ImageURL = "";
        String tag = parser.getName();
        String xmlns = parser.getAttributeValue(null,"xmlns:media");
        if (tag.equalsIgnoreCase("media:thumbnail")){
            if (xmlns.equalsIgnoreCase("http://search.yahoo.com/mrss/")){
                ImageURL = parser.getAttributeValue(null,"url");
                parser.nextTag();
            }
        }
        Log.d("The Image links are:", post_ImageURL);
        return ImageURL;

    }

    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        String description = readText(parser);
        return description;
    }

    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        String link = readText(parser);
        return link;
    }

    private String readDate(XmlPullParser parser) throws IOException, XmlPullParserException, ParseException {
        String date =  readText(parser);
        Date postDate = dateFormat.parse(date);
        //date = dateFormat.format(date);
        long now = new Date().getTime();

        date = DateUtils.getRelativeTimeSpanString(postDate.getTime(),now, DateUtils.SECOND_IN_MILLIS).toString();

        return date;
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        String title = readText(parser);
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
            if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
               parser.nextTag();
        }
        //Hence this result is sent to the PostItemAdapter indorf
            return result;
    }
}