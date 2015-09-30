package com.group6a_hw04.group6a_hw04;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Arunkumar's on 9/30/2015.
 */
public class GetFeedsAsyncTask extends AsyncTask<String,Void,ArrayList<Feed>> {
    IGetFeeds factivity;
    ProgressDialog ffeedLoadProgress;

    String fPROGRESSMESSAGE = "Loading Apps...";

    public GetFeedsAsyncTask(IGetFeeds activity) {
        this.factivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        ffeedLoadProgress = new ProgressDialog((Context) factivity);
        ffeedLoadProgress.setMessage(fPROGRESSMESSAGE);
        ffeedLoadProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        ffeedLoadProgress.show();
    }

    @Override
    protected ArrayList<Feed> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.connect();

            int status = connection.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Feed> feeds) {
        super.onPostExecute(feeds);

        ffeedLoadProgress.dismiss();
        factivity.displayFeeds(feeds);
    }

    public static interface IGetFeeds{
        public void displayFeeds(ArrayList<Feed> feeds);
    }
}
