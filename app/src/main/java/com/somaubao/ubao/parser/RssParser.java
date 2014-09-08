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
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Tonny on 7/23/2014.
 */
public class RssParser  {
    private RSSXMLTag currentTag;
    private String urlString;
    public String imagelink;


    private enum RSSXMLTag {
        TITLE, DATE, LINK, CONTENT, GUID, IGNORETAG, IMAGEURL;
    }


    public RssParser(String urlString) {
        this.urlString = urlString;
    }


    public ArrayList<PostItem> parse() {
        // TODO Auto-generated method stub
        InputStream is = null;
        ArrayList<PostItem> postDataList = new ArrayList<PostItem>();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10 * 1000);
            connection.setConnectTimeout(10 * 1000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int response = connection.getResponseCode();
            Log.d("debug", "The response is: " + response);
            is = connection.getInputStream();

            // parse xml after getting the data
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(is, null);

            int eventType = xpp.getEventType();
            PostItem pdData = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "EEE, DD MMM yyyy HH:mm");

            long now = new Date().getTime();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("item")) {
                        pdData = new PostItem();
                        currentTag = RSSXMLTag.IGNORETAG;
                    }else if (xpp.getName().equalsIgnoreCase("media:thumbnail")){
                        imagelink = xpp.getAttributeValue(null, "url");
                        currentTag = RSSXMLTag.IMAGEURL;
                    } else if (xpp.getName().equals("link")) {
                        currentTag = RSSXMLTag.LINK;
                    } else if (xpp.getName().equals("pubDate")) {
                        currentTag = RSSXMLTag.DATE;
                    } else if (xpp.getName().equals("title")) {
                        currentTag = RSSXMLTag.TITLE;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equals("item")) {
                        // format the data here, otherwise format data in
                        // Adapter
                        Date postDate = dateFormat.parse(pdData.post_Time);
                        pdData.post_Time = DateUtils.getRelativeTimeSpanString(postDate.getTime(), now, DateUtils.SECOND_IN_MILLIS).toString();
                        postDataList.add(pdData);
                    } else {
                        currentTag = RSSXMLTag.IGNORETAG;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    String content = xpp.getText();
                    content = content.trim();
                    // Log.d("debug", content);
                    if (pdData != null) {
                        switch (currentTag) {
                            case TITLE:
                                if (content.length() != 0) {
                                    if (pdData.post_Title != null) {
                                        pdData.post_Title += content;
                                    } else {
                                        pdData.post_Title = content;
                                    }
                                }
                                break;
                            case LINK:
                                if (content.length() != 0) {
                                    if (pdData.post_Link != null) {
                                        pdData.post_Link += content;
                                    } else {
                                        pdData.post_Link = content;
                                    }
                                }
                                break;
                            case DATE:
                                if (content.length() != 0) {
                                    if (pdData.post_Time != null) {
                                        pdData.post_Time += content;
                                    } else {
                                        pdData.post_Time = content;
                                    }
                                }
                                break;
                            case IMAGEURL:
                                if (content.length() != 0) {
                                    if (pdData.post_ImageURL != null) {
                                        pdData.post_ImageURL += content;
                                    } else {
                                        pdData.post_ImageURL = content;
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }

                eventType = xpp.next();
            }
            Log.v("tst", String.valueOf((postDataList.size())));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return postDataList;
    }

}