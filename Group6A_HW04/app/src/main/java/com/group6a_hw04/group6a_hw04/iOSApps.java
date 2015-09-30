package com.group6a_hw04.group6a_hw04;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class iOSApps extends AppCompatActivity implements GetFeedsAsyncTask.IGetFeeds{

    View fMainLayout;//your layout where you will add the list of text

    String fselecteedMediaType,fmediaTypeUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_osapps);
        setMediaIcon();

        fMainLayout = findViewById(R.id.linearLayoutApps);

        fselecteedMediaType = getIntent().getExtras().getString(MainActivity.fMEDIA_TYPE);
        fmediaTypeUrl = getIntent().getExtras().getString(MainActivity.fMEDIA_URL);

        this.setTitle(fselecteedMediaType);

        new GetFeedsAsyncTask(this,fselecteedMediaType).execute(fmediaTypeUrl);
        /*
        * Add following code when adding Text views
        * <TextView
                style="@style/layoutSpacing"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textAppearance="?android:attr/textAppearanceLarge"
                android:text="Large Text"
                android:id="@+id/textView4" />
        *
        * */
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
    public void displayFeeds(ArrayList<Feed> feeds) {
        if (feeds!=null) {
            for(Feed title:feeds) {
                Log.d("Parsed Title", title.getTitle());
            }
        }
    }
}
