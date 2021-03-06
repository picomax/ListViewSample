package com.example.listadapterexample;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NetworkingTask extends AsyncTask<String, Integer, String>  {
    public AsyncResponse delegate = null;//Call back interface

    public NetworkingTask(AsyncResponse asyncResponse) {
        delegate = asyncResponse;//Assigning call back interfacethrough constructor
    }

    @Override
    protected String doInBackground(String... strings) {
        /*
        ArrayList<ToonModel> toonList = new ArrayList<ToonModel>();
        try {
            URL url = new URL("http://nick.hooni.net/list.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            JSONObject rootJson = new JSONObject(builder.toString());
            JSONObject headerJson = rootJson.getJSONObject("header");
            JSONObject bodyJson = rootJson.getJSONObject("body");
            JSONArray listJson = bodyJson.getJSONArray("list");

            for(int i=0; i<listJson.length(); i++) {
                JSONObject tmpJson = listJson.getJSONObject(i);
                String image = tmpJson.get("image").toString();
                String name = tmpJson.get("name").toString();
                String desc = tmpJson.get("desc").toString();
                Record tmpRecord = new Record(image, name, desc);
                list.add(tmpRecord);
            }

            urlConnection.disconnect();

            return list;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
*/
        String urlString = strings[0];
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("X-AUTH-KEY", "haha1234");
            //urlConnection.setDoOutput(true);
            //urlConnection.setDoInput(true);
            //urlConnection.setChunkedStreamingMode(0);
            urlConnection.setUseCaches(false);
            urlConnection.connect();

            JSONObject jsonParam = new JSONObject();
            //jsonParam.put("category", "1");
            jsonParam.put("page", "1");

            OutputStream os = urlConnection.getOutputStream();
            os.write(jsonParam.toString().getBytes());
            os.close();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (isCancelled()) {
                    break;
                }
                builder.append(line);
            }
            urlConnection.disconnect();

            if (isCancelled()) {
                return null;
            }

            JSONObject rootJson = new JSONObject(builder.toString());
            JSONObject headerJson = rootJson.getJSONObject("header");

            JSONObject bodyJson = rootJson.getJSONObject("body");
            JSONArray listJson = bodyJson.getJSONArray("list");

            ToonListModel.getInstance().parseJson(listJson);

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Failed";
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.taskOnCompleted(result);
    }
}
