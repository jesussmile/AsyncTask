package com.example.pannam.asynctask;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by pannam on 9/10/2016.
 */
public class ParseApplication {
    private String data;
    private ArrayList<Application> mApplications;


    public ArrayList<Application> getApplications() {
        return mApplications;
    }

    public ParseApplication(String data) {
        this.data = data;
        mApplications = new ArrayList<Application>();

    }

    public boolean process() {
        boolean operationStatus = true;

        Application currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(this.data));
            int eventType = xmlPullParser.getEventType();
            while (eventType != xmlPullParser.END_DOCUMENT) {
                String tagName = xmlPullParser.getName();
                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.END_DOCUMENT) {


                } else if (eventType == XmlPullParser.START_TAG) {
                    if (tagName.equalsIgnoreCase("entry")) {
                        inEntry = true;
                        currentRecord = new Application();

                    }

                } else if (eventType == XmlPullParser.TEXT) {
                    textValue = xmlPullParser.getText();

                } else if (eventType == XmlPullParser.END_TAG) {

                    if (inEntry) {
                        if (tagName.equalsIgnoreCase("entry")) {
                            mApplications.add(currentRecord);
                        }

                        if (tagName.equalsIgnoreCase("name")) {
                            currentRecord.setName(textValue);
                        } else if (tagName.equalsIgnoreCase("artist")) {
                            currentRecord.setArtist(textValue);
                        } else if (tagName.equalsIgnoreCase("release date")) {
                            currentRecord.setReleaseDate(textValue);
                        }

                        if (tagName.equalsIgnoreCase("entry")) {
                            inEntry = false;
                        }
                    }

                }
                eventType = xmlPullParser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            operationStatus = false;
        }


//        //log
//        for (Application app : mApplications) {
//            Log.d("LOG", "********");
//            Log.d("LOG", app.getName());
//            Log.d("LOG", app.getArtist());
//            Log.d("LOG", app.getReleaseDate());
//
//        }


        for(Application app: mApplications){
            Log.d("LOG", "******************");
            Log.d("LOG","Name: " +app.getName());
            Log.d("LOG","Artist: " +app.getArtist());
            Log.d("LOG","ReleaseDate: " +app.getReleaseDate());

        }


        return operationStatus;

    }
}