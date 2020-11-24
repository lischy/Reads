package com.example.reads;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BooksLoader extends AsyncTaskLoader<ArrayList<MyJsonParser>> {
    private ArrayList<MyJsonParser> cachedData = null;

    public BooksLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (cachedData == null){
            forceLoad();
        }else {
            super.deliverResult(cachedData);
        }
    }
    @Nullable
    @Override
    public ArrayList<MyJsonParser> loadInBackground() {
        URL readUrl = WebSocketConnector.buildUrl("android");
        String jsonResults = null;
        try {
            jsonResults = WebSocketConnector.getJson(readUrl);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("ERROR",e.getMessage());
        }
        ArrayList<MyJsonParser> books = WebSocketConnector.getBooksFromJson(jsonResults);
        cachedData = books;
        Log.d("BOOKS",cachedData.toString()+ books);
//        deliverResult(cachedData);
        return  cachedData;
    }

    @Override
    public void deliverResult(@Nullable ArrayList<MyJsonParser> data) {
        cachedData = data;
        if (isStarted()){
            super.deliverResult(data);
        }
    }

}
