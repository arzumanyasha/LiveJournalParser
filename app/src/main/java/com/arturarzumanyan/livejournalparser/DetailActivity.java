package com.arturarzumanyan.livejournalparser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //кнопка назад
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //забирается линк который надо загрузить и дальше сама загрузка в WebView
        String url = getIntent().getStringExtra("link");
        WebView wv = (WebView) findViewById(R.id.webViewDetail);
        wv.loadUrl(url);
    }
}
