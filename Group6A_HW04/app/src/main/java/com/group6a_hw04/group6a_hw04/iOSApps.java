package com.group6a_hw04.group6a_hw04;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class iOSApps extends AppCompatActivity implements GetFeedsAsyncTask.IGetFeeds{

    static LinearLayout fMainLayout;//your layout where you will add the list of text

    String fSelectedMediaType, fMediaTypeUrl;

    final static String fDETAILED_MEDIA_ACTIVITY = "com.group6a_hw04.group6a_hw04.intent.action.APPDETAILS";
    final static String fMEDIA_TYPE = "Media_Type";
    final static String fMEDIA_FEEDS = "Media_Feeds";
    final String fMEDIA_PREFERENCE = "Media Preference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_osapps);
        setMediaIcon();

        fMainLayout = (LinearLayout) findViewById(R.id.linearLayoutApps);

        fSelectedMediaType = getIntent().getExtras().getString(MainActivity.fMEDIA_TYPE);
        fMediaTypeUrl = getIntent().getExtras().getString(MainActivity.fMEDIA_URL);
        boolean lcheckPreference = getIntent().getExtras().getBoolean(MainActivity.fCHECK_PREFERENCE);

        this.setTitle(fSelectedMediaType);

        if(lcheckPreference){
            SharedPreferences lshareMedia = getSharedPreferences(fSelectedMediaType, MODE_PRIVATE);
            String lcheckFeeds = lshareMedia.getString(fMEDIA_FEEDS,null);
            Gson lgson1 = new Gson();
            Type type = new TypeToken<List<Feed>>(){}.getType();
            ArrayList<Feed> lfeeds = lgson1.fromJson(lcheckFeeds, type);
            displayFeeds(lfeeds);
        }
        else {
            new GetFeedsAsyncTask(this, fSelectedMediaType).execute(fMediaTypeUrl);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_i_osapps, menu);
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

    public void setMediaIcon(){
        ActionBar lActionBar = getSupportActionBar();
        lActionBar.setDisplayShowHomeEnabled(true);
        lActionBar.setIcon(R.mipmap.media_icon);
    }

    @Override
    public void checkPreferences(ArrayList<Feed> aFeeds){

        final SharedPreferences lshareMedia = getSharedPreferences(fSelectedMediaType, MODE_PRIVATE);

        String lcheckMedia = lshareMedia.getString(fMEDIA_TYPE,null);
        Gson lgson = new Gson();
        Type ltype = new TypeToken<List<Feed>>() {}.getType();
        String lstringObjects = lgson.toJson(aFeeds, ltype);

        if (lcheckMedia==null || !lcheckMedia.equals(fSelectedMediaType)){

            lshareMedia.edit().putString(fMEDIA_TYPE,fSelectedMediaType).apply();

            Timer ltimer = new Timer();
            ltimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    lshareMedia.edit().clear().apply();
                }
            },2*60*1000);
        }


        lshareMedia.edit().putString(fMEDIA_FEEDS,lstringObjects).apply();

        displayFeeds(aFeeds);

    }

    public void displayFeeds(ArrayList<Feed> aFeeds) {
        int i=0;
        if (aFeeds !=null) {

            for(final Feed feed: aFeeds) {
                //Setting up master horizontal linear layout
                final LinearLayout lLinearLayoutApp = new LinearLayout(iOSApps.this);
                lLinearLayoutApp.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams lLinearLayoutParams = (new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                lLinearLayoutParams.setMargins(10, 10, 10, 10);
                lLinearLayoutApp.setLayoutParams(lLinearLayoutParams);

                //Setting up the image view
                ImageView lImageViewAppIcon = new ImageView(iOSApps.this);
                lImageViewAppIcon.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                Picasso.with(iOSApps.this).load(feed.getLargeImage()[0]).resize(150,150).into(lImageViewAppIcon);

                //Setting up the text view and its layout params
                TextView lTextViewAppTitle = new TextView(iOSApps.this);
                LinearLayout.LayoutParams lTextViewLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                lTextViewLayoutParams.setMargins(35, 0, 0, 0);

                lTextViewAppTitle.setLayoutParams(lTextViewLayoutParams);
                lTextViewAppTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                lTextViewAppTitle.setTypeface(Typeface.DEFAULT_BOLD);
                lTextViewAppTitle.setTextColor(Color.BLACK);
                lTextViewAppTitle.setClickable(true);
                lTextViewAppTitle.setText(feed.getTitle());

                //Code for onClick
                lTextViewAppTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startDetailedActivity(fSelectedMediaType, feed);
                    }
                });

                //Adding text view and image view into the master layout
                lLinearLayoutApp.addView(lImageViewAppIcon);
                lLinearLayoutApp.addView(lTextViewAppTitle);
                lLinearLayoutApp.setId(i);
                fMainLayout.addView(lLinearLayoutApp);

                final int ltemp = i;
                //Code for onLongClick
                lTextViewAppTitle.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        findViewById(ltemp).setVisibility(View.GONE);
                        deleteSharedPreference(ltemp,feed.getTitle(),fSelectedMediaType);
                        return true;
                    }
                });

                i++;
            }
        }
        else{
            Log.d("Empty JSON","No data retrieved");
            finish();
        }

    }

    public void startDetailedActivity(String aMediaType, Feed aFeed){
        Intent lDetailedApp = new Intent(fDETAILED_MEDIA_ACTIVITY);
        lDetailedApp.putExtra(fMEDIA_TYPE, aMediaType);
        lDetailedApp.putExtra(fMEDIA_FEEDS, aFeed);
        startActivity(lDetailedApp);
    }

    private void deleteSharedPreference(int aindex,String amediaTitle,String aSelectedMediaType){
        final SharedPreferences lshareMedia = getSharedPreferences(aSelectedMediaType, MODE_PRIVATE);
        String lcheckFeeds = lshareMedia.getString(fMEDIA_FEEDS,null);

        Gson lgson2 = new Gson();
        Type ltype = new TypeToken<List<Feed>>(){}.getType();

        if(lcheckFeeds!=null) {
            ArrayList<Feed> lfeeds = lgson2.fromJson(lcheckFeeds, ltype);


            //lfeeds.remove(aindex);
            lfeeds = delteFromList(aindex,lfeeds,amediaTitle);

            lcheckFeeds = lgson2.toJson(lfeeds, ltype);
            lshareMedia.edit().putString(fMEDIA_FEEDS,lcheckFeeds).apply();
        }
    }

    private ArrayList<Feed> delteFromList(int aindex,ArrayList<Feed> feed,String amediaTitle){
        if(aindex>=feed.size() || !feed.get(aindex).getTitle().equals(amediaTitle)){
            return delteFromList(--aindex,feed,amediaTitle);
        }
        else{
            feed.remove(aindex);
            return feed;
        }
    }
}
