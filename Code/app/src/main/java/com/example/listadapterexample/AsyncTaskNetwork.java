package com.example.listadapterexample;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AsyncTaskNetwork extends AsyncTask<Void, Void, ArrayList<Record>>  {
    public Context context;

    public AsyncTaskNetwork(Context context){
        this.context = context;
    }

    @Override
    protected ArrayList<Record> doInBackground(Void... voids) {
        ArrayList<Record> list = new ArrayList<Record>();

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

        return null;
    }

    protected void onPostExecute(ArrayList<Record> list) {
        super.onPostExecute(list);
        ((MainActivity) context).onTaskCompleted(list);
    }
}
