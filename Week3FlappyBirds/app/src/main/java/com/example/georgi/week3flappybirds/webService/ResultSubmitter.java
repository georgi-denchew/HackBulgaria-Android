package com.example.georgi.week3flappybirds.webService;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Georgi on 15.11.2014 г..
 */
public class ResultSubmitter extends AsyncTask<UserData, Void, Void> {

    public static interface ResultHandler {
        public void showProgressBar();
        public void hideProgressBar();
        public void displayNetworkError();
        public void displayNoInternetConnection();
        public boolean isNetworkAvailable();
    }


    private ResultHandler mResultHandler;

    public ResultSubmitter(ResultHandler resultHandler){
        this.mResultHandler = resultHandler;
    }

    @Override
    protected void onPreExecute() {
        mResultHandler.showProgressBar();
    }

    @Override
    protected Void doInBackground(UserData... params) {
        UserData userData = params[0];

        boolean isNetworkAvailable = mResultHandler.isNetworkAvailable();

        if (!isNetworkAvailable){
            mResultHandler.displayNoInternetConnection();
            return null;
        }

        URL url = null;
        HttpResponse response;
        try {
            url = new URL("http://95.111.103.224:8080/Flappy/scores");

            HttpClient client = new DefaultHttpClient();
            URI uri = url.toURI();
            HttpPut put = new HttpPut(uri);

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("name", userData.getUsername()));
            pairs.add(new BasicNameValuePair("mail", userData.getEmail()));
            pairs.add(new BasicNameValuePair("whereFrom", userData.getUniversity()));
            pairs.add(new BasicNameValuePair("score", userData.getScore().toString()));
            put.setEntity(new UrlEncodedFormEntity(pairs));

            response = client.execute(put);
            int a = 0;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            mResultHandler.displayNetworkError();
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mResultHandler.hideProgressBar();
        mResultHandler = null;
    }
}
