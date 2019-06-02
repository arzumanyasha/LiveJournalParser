package com.arturarzumanyan.livejournalparser;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public Elements titles;
    public Elements descriptions;
    public Elements imageUrls;
    public Elements links;
    public ArrayList<String> titleList = new ArrayList<>();
    public ArrayList<String> descriptionList = new ArrayList<>();
    public ArrayList<String> urlList = new ArrayList<>();
    public ArrayList<String> linkList = new ArrayList<>();
    public ArrayList<Post> posts = new ArrayList<>();
    private ListAdapter adapter;
    private ListView lv;
    private WebView wv;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.list_view);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                String url = posts.get(position).getLink();
                intent.putExtra("link", url);

                startActivity(intent);
            }
        });

        wv = (WebView) findViewById(R.id.webView);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new JavaScriptInterface(), "HTMLOUT");
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                /* This call inject JavaScript into the page which just finished loading. */
                wv.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }
        });
        wv.loadUrl("https://www.livejournal.com/media/20letzhzh/");

        adapter = new ListAdapter(this, posts);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item1) {
            wv.loadUrl("https://www.livejournal.com/media/20letzhzh/");
        } else if (id == R.id.item2) {
            wv.loadUrl("https://www.livejournal.com/category/politika-i-obschestvo/");
        } else if (id == R.id.item3) {
            wv.loadUrl("https://www.livejournal.com/category/vokrug-sveta/");
        } else if (id == R.id.item4) {
            wv.loadUrl("https://www.livejournal.com/category/kino/");
        } else if (id == R.id.item5) {
            wv.loadUrl("https://www.livejournal.com/category/nauka-i-tehnika/");
        } else if (id == R.id.item6) {
            wv.loadUrl("https://www.livejournal.com/category/razvlecheniya/");
        } else if (id == R.id.item7) {
            wv.loadUrl("https://www.livejournal.com/category/semya-i-deti/");
        } else if (id == R.id.item8) {
            wv.loadUrl("https://www.livejournal.com/category/eda/");
        } else if (id == R.id.item9) {
            wv.loadUrl("https://www.livejournal.com/category/krasota-i-zdorove/");
        } else if (id == R.id.item10) {
            wv.loadUrl("https://www.livejournal.com/category/stil-zhizni/");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class ContentLoaderAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lv.setAdapter(adapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            Document doc;
            try {
                doc = Jsoup.parse(strings[0]);
                titles = doc.select("h3.post-card__title.ng-binding");
                descriptions = doc.select("p.post-card__lead.post-card__lead--vertical.ng-binding");
                imageUrls = doc.select("div.post-card__image.post-card__image--vertical.ng-scope > img");
                links = doc.select("a.post-card__link");


                posts.clear();

                for (int i = 0; i < titles.size(); i++) {
                    posts.add(new Post(titles.get(i).text(),
                            descriptions.get(i).text(),
                            imageUrls.get(i).attr("src"),
                            links.get(i).attr("href")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class JavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            Log.v("MAINMAIN", html);
            new ContentLoaderAsyncTask().execute(html);
        }
    }
}
