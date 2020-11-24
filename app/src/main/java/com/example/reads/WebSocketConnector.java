package com.example.reads;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class WebSocketConnector {

    public static final String QUERY_PARAMETER_KEY = "q";

    private WebSocketConnector(){};
    public static final String BASE_API_URL = "https://www.googleapis.com/books/v1/volumes";
    public static URL buildUrl (String title){
        URL url = null ;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon().appendQueryParameter(QUERY_PARAMETER_KEY,title)
                .build();
        try{
            url = new URL(uri.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getJson (URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try{
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");

            boolean hasData = scanner.hasNext();
            if (hasData){
                return scanner.next();
            }
            else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            connection.disconnect();
        }

    }

    public static ArrayList<MyJsonParser> getBooksFromJson (String json){
        ArrayList<MyJsonParser> books = new ArrayList<MyJsonParser>();
        final String ID ="id";
        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHED_DATE = "publishedDate";
        final String ITEMS = "items";
        final String VOLUMEINFO = "volumeInfo";
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray arrayBooks = jsonObject.getJSONArray(ITEMS);
            int numberOfBooks = arrayBooks.length();
            for (int i = 0; i<numberOfBooks ; i ++){
                JSONObject bookJSON = arrayBooks.getJSONObject(i);
                JSONObject volumeInfoJSON = bookJSON.getJSONObject(VOLUMEINFO);
                int authorNum = volumeInfoJSON.getJSONArray(AUTHORS).length();
                String[] authors = new String[authorNum];
                for (int j =0 ; j<authorNum;j++){
                    authors[j] = volumeInfoJSON.getJSONArray(AUTHORS).get(j).toString();
                }
                MyJsonParser jsonParser = new MyJsonParser(
                        bookJSON.getString(ID),
                        volumeInfoJSON.getString(TITLE),
                        (volumeInfoJSON.isNull(SUBTITLE) ? "" : volumeInfoJSON.getString(SUBTITLE)),
                        authors,
                        volumeInfoJSON.getString(PUBLISHER),
                        volumeInfoJSON.getString(PUBLISHED_DATE));
                books.add(jsonParser);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return books;
    }
}
