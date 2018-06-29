package com.example.android2.kampuskannekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ERPActivity extends AppCompatActivity {

    private WebView webView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erp);
        webView2=findViewById(R.id.webView2);

        webView2.getSettings().setJavaScriptEnabled(true);
        webView2.getSettings().setDomStorageEnabled(true);
        webView2.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView2.loadUrl("https://kampuskonnekt.com/erp.php");

        webView2.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
    }
}
