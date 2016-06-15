package kindnewspaper.a20110548.ac.kr.kindnewspaper;

/**
 * Created by subin on 2016-06-05.
 */
import android.content.Intent;
import android.net.Network;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

public class NewsViewActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SURVEY = 1001;

    private RequestQueue mQueue = null;
    private ImageLoader mImageLoader = null;

    private String title = "";
    private String content = "";
    private String author = "";
    private String link = "";
int answerScore = 0;
    WebView newsView;
    TextView textView;
Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_main);

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB
        com.android.volley.Network network = new BasicNetwork(new HurlStack());
        mQueue = new RequestQueue(cache, network);
        mQueue.start();

        intent = new Intent(this.getIntent());

        title = intent.getStringExtra("title");
        link = intent.getStringExtra("link");
        content = intent.getStringExtra("content");
        author = intent.getStringExtra("author");

        newsView = (WebView)findViewById(R.id.newsView);
        newsView.setWebViewClient(new WebViewClient());
        newsView.getSettings().setJavaScriptEnabled(true);
        newsView.loadUrl(link);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sIntent = new Intent(NewsViewActivity.this, SurveyActivity.class);
                startActivityForResult(sIntent,REQUEST_CODE_SURVEY);

            }
        });






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_SURVEY){
        }
        if(resultCode == RESULT_OK ){
            String[] answer = {"","","","",""};
            answer[0] = data.getExtras().getString("Answer1");
            answer[1] = data.getExtras().getString("Answer2");
            answer[2] = data.getExtras().getString("Answer3");
            answer[3] = data.getExtras().getString("Answer4");
            answer[4] = data.getExtras().getString("Answer5");
            answerScore = getAnswerScore(answer);

        }
    }

    //점수계산하는함수
    protected int getAnswerScore(String[] answer){
        int tmp = 0;
        for(int i=0;i<5;i++){
            tmp += Integer.valueOf(answer[i]);
        }
        return  tmp;

    }

}