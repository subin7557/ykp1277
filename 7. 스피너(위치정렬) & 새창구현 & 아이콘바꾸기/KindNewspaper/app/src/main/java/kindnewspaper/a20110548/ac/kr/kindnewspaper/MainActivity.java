package kindnewspaper.a20110548.ac.kr.kindnewspaper;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public static final String NEWSTAG = "NewsTag";


    protected RequestQueue mQueue = null;
    //-------------위치----------------------------------------
    private String myAddress;
    public static final String ADDRESSTAG = "AddressTag";

    protected JSONObject mAddressResult = null;
    double latitude;
    double longtitude;
    double altitude;

    private LocationManager locationManager;
    private String locationProvider;
    private LocationListener mLocationListener;

    // Criteria 클래스로 최선의 Provider를 선택하도록 조건 설정
    public static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(true);
        criteria.setSpeedRequired(true);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        return criteria;
    }
    //-------------------------------위치--------------------------------

    public String getMyAddress() {
        return myAddress;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
// Setup spinner


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB
        Network network = new BasicNetwork(new HurlStack());
        mQueue = new RequestQueue(cache, network);
        mQueue.start();


//------------------위치---------
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            Toast.makeText(this, "GPS is OFF, Please GPS ON", Toast.LENGTH_SHORT).show();

        locationProvider = locationManager.getBestProvider(getCriteria(), true);

        // LocationListener 등록
        mLocationListener = new myLocationListener();

        // 마지막 위치 가져와서 출력
        getLastLocation();

        try {
            locationManager.requestLocationUpdates(locationProvider, 5000, 0, mLocationListener);
            Log.e("requestLocationUpdate Try", "Permission Allowed");
        } catch (SecurityException e) {
            Log.e("requestLocationUpdate Catch", "Permission Denied");
        }
        //---------------위치-----------
    }

    //-----------------------------위치
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            locationManager.removeUpdates(mLocationListener);
            Log.e("onDestroy", "");
        } catch (SecurityException e) {

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            locationManager.removeUpdates(mLocationListener);
            Log.e("onPause", "");
        } catch (SecurityException e) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            locationManager.requestLocationUpdates(locationProvider, 5000, 0, mLocationListener);
            Log.e("onResume", "");
        } catch (SecurityException e) {

        }
    }

    // 마지막 위치를 받아와 출력하는 메소드
    public void getLastLocation() {
        Location myLocation = getLastKnownLocation();

        latitude = myLocation.getLatitude();
        longtitude = myLocation.getLongitude();
        altitude = myLocation.getAltitude();

       // Toast.makeText(MainActivity.this,String.valueOf(latitude) + " " + String.valueOf(longtitude) + " " + String.valueOf(altitude), Toast.LENGTH_SHORT).show();

        requestAddress(latitude, longtitude);

        Log.e("getData", "lat = " + latitude + " lng = " + longtitude + " alt = " + altitude);

    }

    // 마지막 위치를 확인하여 그 위치를 반환하는 메소드, 최초실행시 locationManager.getLastKnownLocation(provider) 사용하면 null 반환으로 인한 회피법
    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        Location l = null;
        for (String provider : providers) {
            try {
                l = locationManager.getLastKnownLocation(provider);
                Log.e("getLastKnownLocation", "bestProvider : " + locationProvider);
            } catch (SecurityException e) {
                Log.e("getLastKnownLocation Catch", "Permission Denied");
            }
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    // LocationListener 외부 클래스로 작성
    public class myLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            Toast.makeText(MainActivity.this, "Location Changed", Toast.LENGTH_SHORT).show();

            latitude = location.getLatitude();
            longtitude = location.getLongitude();
            altitude = location.getAltitude();
           // Toast.makeText(MainActivity.this, String.valueOf(latitude) + " " + String.valueOf(longtitude) + " " + String.valueOf(altitude), Toast.LENGTH_SHORT).show();
            //lat.setText("Lat : " + String.valueOf(latitude));
            //lng.setText("Lng : " + String.valueOf(longtitude));
            //alt.setText("Alt : " + String.valueOf(altitude));

            //requestAddress(latitude, longtitude);

            Log.e("onLocationChanged", "bestProvider : " + locationProvider);
            Log.e("getData", "lat = " + latitude + " lng = " + longtitude + " alt = " + altitude);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Toast.makeText(MainActivity.this, provider + " Available", Toast.LENGTH_SHORT).show();
                    Log.e("LocationProvider : ", provider + " Available");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Toast.makeText(MainActivity.this, provider + " Out of Service", Toast.LENGTH_SHORT).show();
                    Log.e("LocationProvider : ", provider + " Out of Service");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(MainActivity.this, provider + " Service Stop", Toast.LENGTH_SHORT).show();
                    Log.e("LocationProvider : ", provider + " Service Stop");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(MainActivity.this, provider + " Provider Enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(MainActivity.this, provider + " Provider Disenabled", Toast.LENGTH_SHORT).show();
        }
    }

    //------------------위치--------------

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



    //-------------------------위치------------------------------------------
    protected void requestAddress(double lat, double longti) {
        String latt = Double.toString(lat);
        String lngg = Double.toString(longti);
        String myApiKey = "AIzaSyDkumaBj_Gty5NmCg23I9jsGu3q_B7l5U0";
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latt + "," + lngg + "&language=ko&key=" + myApiKey;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mAddressResult = response;
                        settingMyAddress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "서버 에러", Toast.LENGTH_LONG).show();
                    }
                }
        );
        jsObjRequest.setTag(ADDRESSTAG);
        mQueue.add(jsObjRequest);
    }

    public void settingMyAddress() {
        try {
            JSONArray jsonMainNode = mAddressResult.getJSONArray("results");
            JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);

            myAddress = jsonChildNode.getString("formatted_address");

        } catch (Exception e) {
        }
        Toast.makeText(MainActivity.this, myAddress, Toast.LENGTH_SHORT).show();

        TextView tv = (TextView)findViewById(R.id.location);
        tv.setText(myAddress);
    }
    //-------------------------------위치-----------------------------


}