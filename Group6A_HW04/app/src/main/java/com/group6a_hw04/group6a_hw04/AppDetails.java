package com.group6a_hw04.group6a_hw04;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class AppDetails extends AppCompatActivity {

    Feed fAppFeed;
    String fMediaType;
    LinearLayout fTextLayout;

    ImageView fThumbnail;
    TextView fAppTitle, fReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_details);
        setMediaIcon();

        fAppFeed = (Feed) getIntent().getSerializableExtra(iOSApps.fMEDIA_FEEDS);
        fMediaType = getIntent().getStringExtra(iOSApps.fMEDIA_TYPE);

        fTextLayout = (LinearLayout) findViewById(R.id.linearLayoutTextFields);
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
            fReleaseDate.setText("Release Date: " + aAppFeed.getReleaseDate());

        Picasso.with(AppDetails.this).load(aAppFeed.getLargeImage()[1]).into(fThumbnail);
        
        createArtist(aAppFeed);
        if(!aMediaType.equals("AUDIO_BOOKS") || !aMediaType.equals("IOS_APPS") || !aMediaType.equals("MOVIES")){
            createSummary(aAppFeed);
        }
        if(!aMediaType.equals("AUDIO_BOOKS")){
            createPrice(aAppFeed);
        }
        if(aMediaType.equals("AUDIO_BOOKS")){
            createDuration(aAppFeed);
        }
        if(!aMediaType.equals("PODCASTS")){
            createCategory(aAppFeed);
        }

        createLink(aAppFeed.getLinkToPreview());

    }

    private void createLink(final String aLinkToPreview) {
        TextView lGenericTextView = new TextView(AppDetails.this);
        lGenericTextView.setText(aLinkToPreview);
        lGenericTextView.setTextColor(Color.BLUE);
        lGenericTextView.setClickable(true);

        lGenericTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lIntent = new Intent(AppDetails.this, PreviewActivity.class);
                lIntent.putExtra("URL", aLinkToPreview);
                startActivity(lIntent);
            }
        });

        fTextLayout.addView(lGenericTextView);

    }

    private void createSummary(Feed aAppFeed) {
        createTextView("Summary: ", aAppFeed.getSummary());
    }

    private void createDuration(Feed aAppFeed) {
        createTextView("Duration: ", aAppFeed.getDuration());
    }

    private void createPrice(Feed aAppFeed) {
        createTextView("Price: $", aAppFeed.getPrice());
    }

    private void createCategory(Feed aAppFeed) {
        createTextView("Category: ", aAppFeed.getCategory());
    }

    private void createArtist(Feed aAppFeed) {
        createTextView("By: ", aAppFeed.getArtist());
    }

    private void createTextView(String aPrefix, String aContent) {
        TextView lGenericTextView = new TextView(AppDetails.this);
        lGenericTextView.setTextAppearance(this, android.R.style.TextAppearance_Large);
//        lGenericTextView.setTextAppearance(this, R.style.layoutSpacing);
        lGenericTextView.setText(aPrefix + aContent);
        fTextLayout.addView(lGenericTextView);
    }

    public void setMediaIcon(){
        ActionBar lActionBar = getSupportActionBar();
        lActionBar.setDisplayShowHomeEnabled(true);
        lActionBar.setIcon(R.mipmap.media_icon);
    }
}
