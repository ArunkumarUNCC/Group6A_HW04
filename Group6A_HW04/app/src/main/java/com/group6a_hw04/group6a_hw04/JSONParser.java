package com.group6a_hw04.group6a_hw04;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Arunkumar's on 9/30/2015.
 */
public class JSONParser {
   static String fselectedMeadiaType;

    static final String fLABEL = "label";
    static final String fATTRIBUTES = "attributes";
    static final String fHREF = "href";

    public JSONParser(String aselectedMediaType) {
        this.fselectedMeadiaType = aselectedMediaType;
    }

    public static class ParseAppFeeds{
        static ArrayList<Feed> parseFeeds(String astringBuilder) throws JSONException {
            ArrayList<Feed> lfeedsArrayList = new ArrayList<Feed>();

            JSONObject root = new JSONObject(astringBuilder);
            JSONObject rootObject = root.getJSONObject("feed");
            JSONArray jsonEntryArray = rootObject.getJSONArray("entry");

            for (int i=0;i<jsonEntryArray.length();i++){
                JSONObject lcommonObject;
                JSONArray llinkArray;
                Feed lfeed = new Feed();


                //Parsing Title of the app
                lcommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("im:name");
                lfeed.setTitle(lcommonObject.getString(fLABEL));

                //Parsing Name of the app
                lcommonObject = jsonEntryArray.getJSONObject(i);
                llinkArray = lcommonObject.getJSONArray("im:image");
                lfeed.setLargeImage(new String[]{llinkArray.getJSONObject(0).getJSONObject(fATTRIBUTES).getString(fLABEL),
                        llinkArray.getJSONObject(2).getJSONObject(fATTRIBUTES).getString(fLABEL)});

                //Parsing Artist of the app
                lcommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("im:artist");
                lfeed.setArtist(lcommonObject.getString(fLABEL));

                //Parsing Price of the app
                lcommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("im:price");
                lfeed.setPrice(lcommonObject.getString(fLABEL));

                //Parsing Category of the app
                lcommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("category").getJSONObject(fATTRIBUTES);
                lfeed.setPrice(lcommonObject.getString(fLABEL));

                //Parsing release Date of the app
                lcommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("im:releaseDate").getJSONObject(fATTRIBUTES);
                lfeed.setArtist(lcommonObject.getString(fLABEL));

                //Parsing Summary of the app
                if((fselectedMeadiaType.equals("BOOKS")) || (fselectedMeadiaType.equals("MAC_APPS")) ||
                        (fselectedMeadiaType.equals("TV_SHOWS")) || (fselectedMeadiaType.equals("MOVIES")) || (fselectedMeadiaType.equals("PODCASTS"))) {
                    lcommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("summary");
                    lfeed.setArtist(lcommonObject.getString(fLABEL));
                }

                //Parsing Preview Link of the app
                if((fselectedMeadiaType.equals("AUDIO_BOOKS")) || (fselectedMeadiaType.equals("MOVIES")) ||
                        (fselectedMeadiaType.equals("TV_SHOWS")) || (fselectedMeadiaType.equals("MUSIC_VIDEOS"))) {
                    lcommonObject = jsonEntryArray.getJSONObject(i);
                    llinkArray = lcommonObject.getJSONArray("im:link");

                    lfeed.setLinkToPreview(llinkArray.getJSONObject(1).getJSONObject(fATTRIBUTES).getString(fHREF));

                    //Parsing Duration of the app
                    lfeed.setDuration(llinkArray.getJSONObject(1).getJSONObject(fATTRIBUTES).getString(fLABEL));
                }
                else{
                    lcommonObject = jsonEntryArray.getJSONObject(i).getJSONObject("link").getJSONObject(fATTRIBUTES);
                    lfeed.setLinkToPreview(lcommonObject.getString(fLABEL));
                }


                lfeedsArrayList.add(lfeed);
            }
            return lfeedsArrayList;
        }
    }
}
