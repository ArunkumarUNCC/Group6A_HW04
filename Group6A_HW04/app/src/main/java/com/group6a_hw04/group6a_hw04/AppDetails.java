package com.group6a_hw04.group6a_hw04;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AppDetails extends AppCompatActivity {

    Feed fAppFeed;
    String fMediaType;
    View fTextLayout;

    ImageView fThumbnail;
    TextView fAppTitle, fReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);
        setMediaIcon();

        fAppFeed = (Feed) getIntent().getSerializableExtra(iOSApps.fMEDIA_FEEDS);
        fMediaType = getIntent().getStringExtra(iOSApps.fMEDIA_TYPE);

        fTextLayout = findViewById(R.id.linearLayoutTextFields);
        fThumbnail = (ImageView) findViewById(R.id.imageViewThumbnail);
        fAppTitle = (TextView) findViewById(R.id.textViewAppTitle);
        fReleaseDate = (TextView) findViewById(R.id.textViewAppReleaseDate);

        createScreen(fAppFeed, fMediaType);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_details, menu);
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

    public void createScreen(Feed aAppFeed, String aMediaType){
        fAppTitle.setText(aAppFeed.getTitle());

        if(aMediaType.equals("IOS_APPS") || aMediaType.equals("MOVIES") || aMediaType.equals("PODCASTS"))
            fReleaseDate.setText("");
        else
            fReleaseDate.setText(aAppFeed.getReleaseDate());

        Picasso.with(AppDetails.this).load(aAppFeed.getLargeImage()[1]).into(fThumbnail);
        
        createArtist(aAppFeed,  aMediaType);

    }

    private void createArtist(Feed aAppFeed, String aMediaType) {
//        createTextView
    }

    public void setMediaIcon(){
        ActionBar lActionBar = getSupportActionBar();
        lActionBar.setDisplayShowHomeEnabled(true);
        lActionBar.setIcon(R.mipmap.media_icon);
    }
}
