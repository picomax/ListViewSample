package com.example.listadapterexample;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    */
    private AsyncTaskNetwork networkTask;
    private ListView mListView;
    private MainListAdapter mAdapter;
    private ArrayList<Record> list = new ArrayList<Record>();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        networkTask.cancel(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);


        // Xml에서 추가한 ListView 연결
        mListView = (ListView) findViewById(R.id.main_list);

        // 커스텀 어댑터 생성
        mAdapter = new MainListAdapter();

        // ListView에 어댑터 연결
        mListView.setAdapter(mAdapter);

        requestData();
    }

    private void requestData(){
        if(checkNetworkStatus() == false) {
            return;
        }
        networkTask = new AsyncTaskNetwork(MainActivity.this);
        //networkTask.execute();
        networkTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

    public void onTaskCompleted(ArrayList<Record> list) {
        this.list = list;
        mAdapter.setMainList(list);
        mAdapter.notifyDataSetChanged();
    }
}
