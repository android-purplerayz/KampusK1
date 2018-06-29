package com.example.android2.kampuskannekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BlogActivity extends AppCompatActivity {

    private WebView webView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        webView1=findViewById(R.id.webView1);

        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.getSettings().setDomStorageEnabled(true);
        webView1.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView1.loadUrl("https://kampuskonnekt.com/blog/");

        webView1.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
    }
}
