/*
Michael Vitulli - Arunkumar
 */

package com.group6a_hw04.group6a_hw04;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    final static String fMEDIA_LIST_ACTIVITY = "com.group6a_hw04.group6a_hw04.intent.action.IOSAPPS";
    final static String fMEDIA_TYPE = "Media_Type";
    final static String fMEDIA_URL = "Media URL";
    final String fMEDIA_PREFERENCE = "Media Preference";
    final static String fCHECK_PREFERENCE = "Check Preference";

    View fMainLayout;
    static SharedPreferences fshareMedia;
    static SharedPreferences.Editor fmediaEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMediaIcon();

        fMainLayout = findViewById(R.id.linearLayoutMain);

        fshareMedia = getSharedPreferences(fMEDIA_PREFERENCE, MODE_PRIVATE);
        fmediaEditor = fshareMedia.edit();
        fmediaEditor.clear();
        fmediaEditor.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void audioBookOnClick(View aView){
        createIntent("AUDIO_BOOKS", "https://itunes.apple.com/us/rss/topaudiobooks/limit=25/json",checkSharedPreference("AUDIO_BOOKS"));
    }

    public void booksOnClick(View aView){
        createIntent("BOOKS", "https://itunes.apple.com/us/rss/topfreeebooks/limit=25/json",checkSharedPreference("BOOKS"));
    }

    public void iOSAppsOnClick(View aView){
        createIntent("IOS_APPS", "https://itunes.apple.com/us/rss/newapplications/limit=25/json",checkSharedPreference("IOS_APPS"));
    }

    public void iTunesUOnClick(View aView){
        createIntent("ITUNES_U", "https://itunes.apple.com/us/rss/topitunesucollections/limit=25/json",checkSharedPreference("ITUNES_U"));
    }

    public void macAppsOnClick(View aView){
        createIntent("MAC_APPS", "https://itunes.apple.com/us/rss/topfreemacapps/limit=25/json",checkSharedPreference("MAC_APPS"));
    }

    public void moviesOnClick(View aView){
        createIntent("MOVIES", "https://itunes.apple.com/us/rss/topmovies/limit=25/json",checkSharedPreference("MOVIES"));
    }

    public void musicVideoOnClick(View aView){
        createIntent("MUSIC_VIDEO", "https://itunes.apple.com/us/rss/topmusicvideos/limit=25/json",checkSharedPreference("MUSIC_VIDEO"));
    }

    public void podcastsOnClick(View aView){
        createIntent("PODCASTS", "https://itunes.apple.com/us/rss/toppodcasts/limit=25/json",checkSharedPreference("PODCASTS"));
    }

    public void tvShowsOnClick(View aView){
        createIntent("TV_SHOWS", "https://itunes.apple.com/us/rss/toptvepisodes/limit=25/json",checkSharedPreference("TV_SHOWS"));
    }

    public void createIntent(String aType, String aURL,boolean aSharedPreference){
        Intent lIntent = new Intent(fMEDIA_LIST_ACTIVITY);
        lIntent.putExtra(fMEDIA_TYPE, aType);
        lIntent.putExtra(fMEDIA_URL, aURL);
        lIntent.putExtra(fCHECK_PREFERENCE,aSharedPreference);
        startActivity(lIntent);

    }

    public boolean checkSharedPreference(String amediaType){

        boolean lcheck = fshareMedia.contains(fMEDIA_TYPE);
        if(lcheck) {
            String lcheckMedia = fshareMedia.getString(fMEDIA_TYPE,null);
            if(lcheckMedia!=null && lcheckMedia.equals(amediaType)){
                return true;
            }
            else return false;
        } else {
            putSharedPreference(amediaType);
            return false;
        }
    }

    public void putSharedPreference(String amediaType){
        fshareMedia.edit().putString(fMEDIA_TYPE,amediaType).apply();

        Timer ltimer = new Timer();
        ltimer.schedule(new TimerTask() {
            @Override
            public void run() {
                fshareMedia.edit().clear().apply();
            }
        },2*60*1000);
    }
}
