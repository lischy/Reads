package com.example.reads;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.net.URL;
import java.util.ArrayList;

public class ReadListActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_read_list);

        try{
            URL readUrl = WebSocketConnector.buildUrl("cooking");
//           new  fetchData().execute(readUrl);
            getSupportLoaderManager().initLoader(0,null,loaderCallbacks);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private LoaderManager.LoaderCallbacks<ArrayList<MyJsonParser>> loaderCallbacks= new LoaderManager.LoaderCallbacks<ArrayList<MyJsonParser>>(){

        @NonNull
        @Override
        public Loader<ArrayList<MyJsonParser>> onCreateLoader(int id, @Nullable Bundle args) {
            return new BooksLoader(getApplicationContext());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<ArrayList<MyJsonParser>> loader, ArrayList<MyJsonParser> data) {

            TextView tv = findViewById(R.id.readTv);
            String resultString = "";
            for(MyJsonParser book :data){
                resultString = resultString + book.title + "\n" + book.publishedDate + "\n" +book.authors[0]+"\n\n";
            }
            tv.setText(resultString);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<ArrayList<MyJsonParser>> loader) {

        }
    };

//    public class fetchData extends AsyncTask<URL ,Void , String>{
//
//        @Override
//        protected String doInBackground(URL... urls) {
//            URL readUrl = urls[0];
//            String jsonResults = null;
//            try {
//                 jsonResults = WebSocketConnector.getJson(readUrl);
//            }catch (Exception e){
//                e.printStackTrace();
//                Log.e("ERROR",e.getMessage());
//            }
//            return  jsonResults;
//            }
//
//        @Override
//        protected void onPostExecute(String result) {
//            TextView tv = findViewById(R.id.readTv);
//
//            ArrayList<MyJsonParser> books = WebSocketConnector.getBooksFromJson(result);
//            String resultString = "";
//            for(MyJsonParser book :books){
//                resultString = resultString + book.title + "\n" + book.publishedDate + "\n\n";
//            }
//            tv.setText(resultString);
//        }
//    }
}
