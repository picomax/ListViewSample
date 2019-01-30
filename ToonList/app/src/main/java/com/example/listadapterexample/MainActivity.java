package com.example.listadapterexample;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private NetworkingTask networkTask;
    private ListView mListView;
    private ToonListAdapter mAdapter;
    private ArrayList<ToonModel> toonList;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        networkTask.cancel(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        toonList = new ArrayList<ToonModel>();

        // Xml에서 추가한 ListView 연결
        mListView = (ListView) findViewById(R.id.main_list);

        // 커스텀 어댑터 생성
        mAdapter = new ToonListAdapter();

        // ListView에 어댑터 연결
        mListView.setAdapter(mAdapter);

        requestData();
    }

    private void requestData(){
        if(checkNetworkStatus() == false) {
            Toast.makeText(this, "Network is not available.", Toast.LENGTH_SHORT).show();
            return;
        }

        String urlString = "http://nick.hooni.net/api/toon.php";

        networkTask = new NetworkingTask(MainActivity.this);
        //networkTask.execute(urlString);
        networkTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urlString);
    }

    public void onTaskCompleted(ToonListModel toonListModel) {
        Toast.makeText(this, "ToonList will be shown.", Toast.LENGTH_SHORT).show();

        this.toonList = toonListModel.getToonList();
        mAdapter.setToonList(toonList);
        mAdapter.notifyDataSetChanged();
    }

    public boolean checkNetworkStatus() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
            } else {
                // connected, but other.
            }

            return true;
        }

        return false;
    }
}
