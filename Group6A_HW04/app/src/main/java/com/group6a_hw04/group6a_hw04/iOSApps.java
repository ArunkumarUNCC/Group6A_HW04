package com.group6a_hw04.group6a_hw04;

import android.content.Intent;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class iOSApps extends AppCompatActivity implements GetFeedsAsyncTask.IGetFeeds{

    static LinearLayout fMainLayout;//your layout where you will add the list of text

    String fSelectedMediaType, fMediaTypeUrl;

    final static String fDETAILED_MEDIA_ACTIVITY = "com.group6a_hw04.group6a_hw04.intent.action.APPDETAILS";
    final static String fMEDIA_TYPE = "Media_Type";
    final static String fMEDIA_FEEDS = "Media_Feeds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_osapps);
        setMediaIcon();

        fMainLayout = (LinearLayout) findViewById(R.id.linearLayoutApps);

        fSelectedMediaType = getIntent().getExtras().getString(MainActivity.fMEDIA_TYPE);
        fMediaTypeUrl = getIntent().getExtras().getString(MainActivity.fMEDIA_URL);

        this.setTitle(fSelectedMediaType);

        new GetFeedsAsyncTask(this, fSelectedMediaType).execute(fMediaTypeUrl);
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
    public void displayFeeds(ArrayList<Feed> aFeeds) {
        if (aFeeds !=null) {
            for(final Feed feed: aFeeds) {
                //Setting up master horizontal linear layout
                LinearLayout lLinearLayoutApp = new LinearLayout(iOSApps.this);
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
                lTextViewAppTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startDetailedActivity(fSelectedMediaType, feed);
                    }
                });

                //Adding text view and image view into the master layout
                lLinearLayoutApp.addView(lImageViewAppIcon);
                lLinearLayoutApp.addView(lTextViewAppTitle);
                fMainLayout.addView(lLinearLayoutApp);
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
}
