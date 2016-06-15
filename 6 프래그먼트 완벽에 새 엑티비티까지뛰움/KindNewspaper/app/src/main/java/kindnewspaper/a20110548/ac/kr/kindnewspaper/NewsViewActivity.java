package kindnewspaper.a20110548.ac.kr.kindnewspaper;

/**
 * Created by subin on 2016-06-05.
 */
import android.content.Intent;
import android.net.Network;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

public class NewsViewActivity extends AppCompatActivity{
    private RequestQueue mQueue = null;
    private ImageLoader mImageLoader = null;

    private String title = "";
    private String content = "";
    private String author = "";
    private String link = "";

    WebView newsView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_view);

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB
        com.android.volley.Network network = new BasicNetwork(new HurlStack());
        mQueue = new RequestQueue(cache, network);
        mQueue.start();

        Intent intent = new Intent(this.getIntent());

        title = intent.getStringExtra("title");
        link = intent.getStringExtra("link");
        content = intent.getStringExtra("content");
        author = intent.getStringExtra("author");

        newsView = (WebView)findViewById(R.id.newsView);
        newsView.setWebViewClient(new WebViewClient());
        newsView.getSettings().setJavaScriptEnabled(true);
        newsView.loadUrl(link);
    }
}