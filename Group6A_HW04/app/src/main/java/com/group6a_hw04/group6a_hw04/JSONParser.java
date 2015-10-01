package com.group6a_hw04.group6a_hw04;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {


    static final String fLABEL = "label";
    static final String fATTRIBUTES = "attributes";
    static final String fHREF = "href";

    public static class ParseAppFeeds{
            static ArrayList<Feed> parseFeeds(String aStringBuilder, String aSelectedMediaType) throws JSONException {
            ArrayList<Feed> lFeedsArrayList = new ArrayList<Feed>();

            JSONObject root = new JSONObject(aStringBuilder);
            JSONObject rootObject = root.getJSONObject("feed");
            JSONArray jsonEntryArray = rootObject.getJSONArray("entry");

            for (int i=0;i<jsonEntryArray.length();i++){
                JSONObject lCommonObject;
                JSONArray lLinkArray;
                Feed lFeed = new Feed();

                //Parsing Title of the app
                lCommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("im:name");
                lFeed.setTitle(lCommonObject.getString(fLABEL));

                //Parsing Image of the app
                lCommonObject = jsonEntryArray.getJSONObject(i);
                lLinkArray = lCommonObject.getJSONArray("im:image");
                lFeed.setLargeImage(new String[]{lLinkArray.getJSONObject(0).getString(fLABEL),
                        lLinkArray.getJSONObject(2).getString(fLABEL)});

                //Parsing Artist of the app
                lCommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("im:artist");
                lFeed.setArtist(lCommonObject.getString(fLABEL));

                //Parsing Price of the app
                lCommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("im:price");
                lFeed.setPrice(lCommonObject.getString(fLABEL));

                //Parsing Category of the app
                lCommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("category").getJSONObject(fATTRIBUTES);
                lFeed.setPrice(lCommonObject.getString(fLABEL));

                //Parsing release Date of the app
                lCommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("im:releaseDate").getJSONObject(fATTRIBUTES);
                lFeed.setArtist(lCommonObject.getString(fLABEL));

                //Parsing Summary of the app
                if((aSelectedMediaType.equals("BOOKS")) || (aSelectedMediaType.equals("MAC_APPS")) ||
                        (aSelectedMediaType.equals("TV_SHOWS")) || (aSelectedMediaType.equals("MOVIES"))
                        || (aSelectedMediaType.equals("PODCASTS"))) {
                    lCommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("summary");
                    lFeed.setArtist(lCommonObject.getString(fLABEL));
                }

                //Parsing Preview Link of the app
                if((aSelectedMediaType.equals("AUDIO_BOOKS")) || (aSelectedMediaType.equals("MOVIES")) ||
                        (aSelectedMediaType.equals("TV_SHOWS")) || (aSelectedMediaType.equals("MUSIC_VIDEO"))) {
                    lCommonObject = jsonEntryArray.getJSONObject(i);
                    lLinkArray = lCommonObject.getJSONArray("link");

                    lFeed.setLinkToPreview(lLinkArray.getJSONObject(1).getJSONObject(fATTRIBUTES).getString(fHREF));

                    //Parsing Duration of the app
                    lFeed.setDuration(lLinkArray.getJSONObject(1).getJSONObject("im:duration").getString(fLABEL));
                }
                else{
                    lCommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("link").getJSONObject(fATTRIBUTES);
                    lFeed.setLinkToPreview(lCommonObject.getString(fHREF));
                }

                lFeedsArrayList.add(lFeed);
            }
            return lFeedsArrayList;
        }
    }
}
