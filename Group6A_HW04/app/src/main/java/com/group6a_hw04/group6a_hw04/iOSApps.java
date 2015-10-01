package com.group6a_hw04.group6a_hw04;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Parcelable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class iOSApps extends AppCompatActivity implements GetFeedsAsyncTask.IGetFeeds{

    static LinearLayout fMainLayout;//your layout where you will add the list of text

    String fselecteedMediaType,fmediaTypeUrl;

    String fDETAILED_MEDIA_ACTIVITY = "com.group6a_hw04.group6a_hw04.intent.action.APPDETAILS";
    String fMEDIA_TYPE = "Media_Type";
    String fMEDIA_FEEDS = "Media_Feeds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_osapps);
        setMediaIcon();

        fMainLayout = (LinearLayout) findViewById(R.id.linearLayoutApps);

        fselecteedMediaType = getIntent().getExtras().getString(MainActivity.fMEDIA_TYPE);
        fmediaTypeUrl = getIntent().getExtras().getString(MainActivity.fMEDIA_URL);

        this.setTitle(fselecteedMediaType);

        new GetFeedsAsyncTask(this,fselecteedMediaType).execute(fmediaTypeUrl);
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
            for(final Feed feed:feeds) {
                //Setting up master horizontal linear layout
                LinearLayout llinearLayoutApp = new LinearLayout(iOSApps.this);
                llinearLayoutApp.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams llinearLayoutParams = (new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                llinearLayoutParams.setMargins(10,10,10,10);
                llinearLayoutApp.setLayoutParams(llinearLayoutParams);

                //Setting up the image view
                ImageView limageViewAppIcon = new ImageView(iOSApps.this);
                limageViewAppIcon.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                Picasso.with(iOSApps.this).load(feed.getLargeImage()[0]).resize(150,150).into(limageViewAppIcon);

                //Setting up the text view and its layout params
                TextView ltextViewAppTitle = new TextView(iOSApps.this);
                LinearLayout.LayoutParams ltextViewLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                ltextViewLayoutParams.setMargins(35,0,0,0);

                ltextViewAppTitle.setLayoutParams(ltextViewLayoutParams);
                ltextViewAppTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                ltextViewAppTitle.setTypeface(Typeface.DEFAULT_BOLD);
                ltextViewAppTitle.setTextColor(Color.BLACK);
                ltextViewAppTitle.setClickable(true);
                ltextViewAppTitle.setText(feed.getTitle());
                ltextViewAppTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startDetailedActivity(fselecteedMediaType,feed);
                    }
                });

                //Adding text view and image view into the master layout
                llinearLayoutApp.addView(limageViewAppIcon);
                llinearLayoutApp.addView(ltextViewAppTitle);
                fMainLayout.addView(llinearLayoutApp);
            }
        }
        else{
            Log.d("Empty JSON","No data retrieved");
            finish();
        }

    }

    public void startDetailedActivity(String amediaType,Feed afeed){
        Intent ldetailedApp = new Intent(fDETAILED_MEDIA_ACTIVITY);
        ldetailedApp.putExtra(fMEDIA_TYPE,amediaType);
        ldetailedApp.putExtra(fMEDIA_FEEDS,afeed);
        startActivity(ldetailedApp);
    }
}
