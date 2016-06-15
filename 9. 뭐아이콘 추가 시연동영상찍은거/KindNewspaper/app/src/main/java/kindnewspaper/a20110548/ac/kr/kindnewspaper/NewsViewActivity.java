package kindnewspaper.a20110548.ac.kr.kindnewspaper;

/**
 * Created by subin on 2016-06-05.
 */
import android.content.Intent;
import android.net.Network;
import android.os.Bundle;
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

public class NewsViewActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RequestQueue mQueue = null;
    private ImageLoader mImageLoader = null;

    private String title = "";
    private String content = "";
    private String author = "";
    private String link = "";

    WebView newsView;
    TextView textView;

    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
    RadioGroup radioGroup3;
    RadioGroup radioGroup4;
    RadioGroup radioGroup5;

    int Answer1 = 0;
    int Answer2 = 0;
    int Answer3 = 0;
    int Answer4 = 0;
    int Answer5 = 0;

    Button btn;

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

        radioGroup1 = (RadioGroup)findViewById(R.id.answer1);
        radioGroup2 = (RadioGroup)findViewById(R.id.answer2);
        radioGroup3 = (RadioGroup)findViewById(R.id.answer3);
        radioGroup4 = (RadioGroup)findViewById(R.id.answer4);
        radioGroup5 = (RadioGroup)findViewById(R.id.answer5);

        radioGroup1.setOnCheckedChangeListener(NewsViewActivity.this);
        radioGroup2.setOnCheckedChangeListener(NewsViewActivity.this);
        radioGroup3.setOnCheckedChangeListener(NewsViewActivity.this);
        radioGroup4.setOnCheckedChangeListener(NewsViewActivity.this);
        radioGroup5.setOnCheckedChangeListener(NewsViewActivity.this);

        btn = (Button)findViewById(R.id.InsertBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewsViewActivity.this,Integer.toString(Answer1) + " " + Integer.toString(Answer2)+ " " + Integer.toString(Answer3)+ " " + Integer.toString(Answer4)+ " " + Integer.toString(Answer5),Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radio1_1 :
                Answer1 = 1;
                break;
            case R.id.radio1_2 :
                Answer1 = 2;
                break;
            case R.id.radio1_3 :
                Answer1 = 3;
                break;
            case R.id.radio1_4 :
                Answer1 = 4;
                break;
            case R.id.radio1_5 :
                Answer1 = 5;
                break;
            //1번질문 끝

            case R.id.radio2_1 :
                Answer2 = 1;
                break;
            case R.id.radio2_2 :
                Answer2 = 2;
                break;
            case R.id.radio2_3 :
                Answer2 = 3;
                break;
            case R.id.radio2_4 :
                Answer2 = 4;
                break;
            case R.id.radio2_5 :
                Answer2 = 5;
                break;
            //2번질문 끝

            case R.id.radio3_1 :
                Answer3 = 1;
                break;
            case R.id.radio3_2 :
                Answer3 = 2;
                break;
            case R.id.radio3_3 :
                Answer3 = 3;
                break;
            case R.id.radio3_4 :
                Answer3 = 4;
                break;
            case R.id.radio3_5 :
                Answer3 = 5;
                break;
            //3번질문 끝

            case R.id.radio4_1 :
                Answer4 = 1;
                break;
            case R.id.radio4_2 :
                Answer4 = 2;
                break;
            case R.id.radio4_3 :
                Answer4 = 3;
                break;
            case R.id.radio4_4 :
                Answer4 = 4;
                break;
            case R.id.radio4_5 :
                Answer4 = 5;
                break;
            //4번질문 끝

            case R.id.radio5_1 :
                Answer5 = 1;
                break;
            case R.id.radio5_2 :
                Answer5 = 2;
                break;
            case R.id.radio5_3 :
                Answer5 = 3;
                break;
            case R.id.radio5_4 :
                Answer5 = 4;
                break;
            case R.id.radio5_5 :
                Answer5 = 5;
                break;
            //5번질문끝
        }
    }
}