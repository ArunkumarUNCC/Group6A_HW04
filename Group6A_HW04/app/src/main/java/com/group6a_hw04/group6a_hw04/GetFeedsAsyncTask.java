package com.group6a_hw04.group6a_hw04;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class GetFeedsAsyncTask extends AsyncTask<String,Void,ArrayList<Feed>> {
    IGetFeeds fActivity;
    ProgressDialog fFeedLoadProgress;
    String fSelectedMediaType;

    String fPROGRESSMESSAGE = "Loading Apps...";

    public GetFeedsAsyncTask(IGetFeeds activity,String aselectedMediaType) {
        this.fActivity = activity;
        this.fSelectedMediaType = aselectedMediaType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        fFeedLoadProgress = new ProgressDialog((Context) fActivity);
        fFeedLoadProgress.setMessage(fPROGRESSMESSAGE);
        fFeedLoadProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        fFeedLoadProgress.show();
    }

    @Override
    protected ArrayList<Feed> doInBackground(String... params) {
        try {
            URL lUrl = new URL(params[0]);
            HttpURLConnection lConnection = (HttpURLConnection) lUrl.openConnection();
            lConnection.setRequestMethod("GET");

            lConnection.connect();

            int status = lConnection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader lBufferedReader = new BufferedReader(new InputStreamReader(lConnection.getInputStream()));
                StringBuilder lStringBuilder = new StringBuilder();

                String line = lBufferedReader.readLine();

                while (line!=null){
                    lStringBuilder.append(line);
                    line = lBufferedReader.readLine();
                }

                return JSONParser.ParseAppFeeds.parseFeeds(String.valueOf(lStringBuilder), fSelectedMediaType);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Feed> aFeeds) {
        super.onPostExecute(aFeeds);
        fFeedLoadProgress.dismiss();
        fActivity.displayFeeds(aFeeds);
    }

    public static interface IGetFeeds{
        public void displayFeeds(ArrayList<Feed> feeds);
    }
}
