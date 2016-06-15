package kindnewspaper.a20110548.ac.kr.kindnewspaper;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public static final String NEWSTAG = "NewsTag";

    protected String mResult = null;
    private String title = "";
    private String content = "";
    private String author = "";

    private XmlPullParserFactory xmlFactoryObject;
    protected ArrayList<NewsInfo> mArray = new ArrayList<NewsInfo>();
    protected ListView mList;
    protected NewsAdapter mAdapter;
    protected RequestQueue mQueue = null;
    protected ImageLoader mImageLoader = null;
    SearchView searchView = null;
    PlanetFilter planetFilter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAdapter = new NewsAdapter(this, R.layout.news_list);

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB
        Network network = new BasicNetwork(new HurlStack());
        mQueue = new RequestQueue(cache, network);
        mQueue.start();

        mImageLoader = new ImageLoader(mQueue, new LruBitmapCache(LruBitmapCache.getCacheSize(this)));
        //mList.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, mArray.get(position).getTitle(), Toast.LENGTH_SHORT).show();
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

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                mAdapter.getFilter().filter(s);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
       //         mAdapter.getFilter().filter(s);
//Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(NEWSTAG);
        }
    }

    public class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            requestNews();
            View rootView = inflater.inflate(R.layout.content_main, container, false);
            mList = (ListView) rootView.findViewById(R.id.listview);
            mList.setAdapter(mAdapter);
            mList.setOnItemClickListener(MainActivity.this);
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(PlaceholderFragment.ARG_SECTION_NUMBER, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "전체";
                case 1:
                    return "몰라";
                case 2:
                    return "zz";
            }
            return null;
        }
    }

    //---------------------------------------------------------------------
    public class NewsInfo {
        String title;
        String content;
        String author;

        public NewsInfo(String title, String content, String author) {
            this.title = title;
            this.content = content;
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getAuthor() {
            return author;
        }
    }

    //---------------------------------------------------------------------
    static class NewsViewHolder {
        TextView txTitle;
        TextView txContent;
        TextView txAuthor;
        WebView wvContent;
    }

    //---------------------------------------------------------------------
    public class NewsAdapter extends ArrayAdapter<NewsInfo>  {
        private LayoutInflater inflater = null;

        public NewsAdapter(Context context, int resource) {
            super(context, resource);
            inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return mArray.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NewsViewHolder nvh = null;

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.news_list, parent, false);

                nvh = new NewsViewHolder();
                nvh.txTitle = (TextView) convertView.findViewById(R.id.title);
                nvh.txContent = (TextView) convertView.findViewById(R.id.content);
                nvh.txAuthor = (TextView) convertView.findViewById(R.id.author);
                nvh.wvContent = (WebView) convertView.findViewById(R.id.wvContent);

                convertView.setTag(nvh);
            } else {
                nvh = (NewsViewHolder) convertView.getTag();
            }

            NewsInfo info = mArray.get(position);
            if (info != null) {
                nvh.txTitle.setText("제목 : " + info.getTitle());
                nvh.txContent.setText(info.getContent());
                nvh.txAuthor.setText(info.getAuthor());
                nvh.wvContent.loadData(info.getContent(), "text/html; charset=UTF-8", null);

            }
            return convertView;
        }

        public Filter getFilter() {
            if (planetFilter == null)
                planetFilter = new PlanetFilter();

            return planetFilter;
        }

    }
    //---------------------------------------------------------------------

    //--------------------------------------------------------------
    protected void requestNews() {
        String url = "http://www.chosun.com/site/data/rss/rss.xml";
        StringRequest stringRequest = new UTF8StringRequest(Request.Method.GET, url, new
                Response.Listener<String>() {
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        mResult = response;
                        parseXMLAndStoreIt();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "서버 에러", Toast.LENGTH_LONG).show();
            }
        });

        mQueue.add(stringRequest);
    }

    public class UTF8StringRequest extends StringRequest {
        public UTF8StringRequest(int method, String url, Response.Listener<String> listener,
                                 Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            String utf8String = null;
            try {
                utf8String = new String(response.data, "UTF-8");
                return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            }
        }
    }

    //--------------------------------------------------------------
    public void parseXMLAndStoreIt() {
            int event;
        String text = null;
        boolean item = false;

        try {
            String rssNews = mResult;
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myParser = xmlFactoryObject.newPullParser();
            myParser.setInput(new StringReader(rssNews));

            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (name.equals("item"))
                            item = true;
                        if (name.equals("title")) {
                            title = text;
                        } else if (name.equals("description")) {
                            content = text;
                        } else if (name.equals("author")) {
                            author = text;
                        }
                        break;
                }
                if (title != "" && content != "" && author != "" && item) {
                    mArray.add(new NewsInfo(title, content, author));
                    title = "";
                    content = "";
                    author = "";
                    item = false;
                }
                event = myParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------
    private class PlanetFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = mArray;
                results.count = mArray.size();
            }
            else {

                ArrayList<NewsInfo> nNewsList = new ArrayList<NewsInfo>();
                for (NewsInfo info : mArray) {
                    String string = info.getTitle();
                    String string2 = info.getAuthor();
                    String string3 = info.getContent();
                    if (info.getTitle().toUpperCase().matches(".*" + constraint.toString().toUpperCase() + ".*")){

                        nNewsList.add(info);
                    }

                    else if (info.getAuthor().toUpperCase().matches(".*" + constraint.toString().toUpperCase() + ".*")){

                        nNewsList.add(info);}
                    else if (info.getContent().toUpperCase().matches(".*" + constraint.toString().toUpperCase() + ".*")){

                        nNewsList.add(info);}




                }


                results.values = nNewsList;
                results.count = nNewsList.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0){
                mAdapter.notifyDataSetInvalidated();
            mArray.clear();}
            else {
                mArray = (ArrayList<NewsInfo>) results.values;
                mAdapter.notifyDataSetChanged();

            }

        }

    }


}