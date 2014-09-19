package com.somaubao.ubao;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * Created by Tonny on 7/27/2014.
 */
public class Postview extends Activity {
    TextView txtTitle;
    ActionBar actionBar;

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postview);
        actionBar = getActionBar();
        webView = (WebView) findViewById(R.id.postView);
        Bundle bundle = this.getIntent().getExtras();
        String postLink = bundle.getString("link");
        String postTitle = bundle.getString("title");

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(postLink);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSansSerifFontFamily("san-serif");
        webView.setWebViewClient(new WebViewClient());

        actionBar.setDisplayHomeAsUpEnabled(true);

        if(postTitle != null){
            actionBar.setTitle(postTitle);

        }else{
            actionBar.hide();
        }


    }


}

