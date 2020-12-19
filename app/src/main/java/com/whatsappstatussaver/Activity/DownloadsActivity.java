package com.whatsappstatussaver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.whatsappstatussaver.Adapter.GridAdapter;
import com.whatsappstatussaver.R;

import java.io.File;
import java.util.ArrayList;

public class DownloadsActivity extends AppCompatActivity
{
    GridView downloadsRecycler;
    ArrayList<File> list;
    GridAdapter gridAdapter;
    SwipeRefreshLayout pullToRefreshDownloads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloads);

        try {

            Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_downloads);
            setSupportActionBar(toolbar);
            toolbar.setSubtitle("Downloads");

            downloadsRecycler = findViewById(R.id.downloadsRecycler);
            pullToRefreshDownloads = findViewById(R.id.pullToRefreshDownloads);
            pullToRefreshDownloads.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadfiles();
                    pullToRefreshDownloads.setRefreshing(false);
                }
            });

            list = new ArrayList<>();
            list = getImageFiles(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WSDownloader/"));

            loadfiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadfiles()
    {
        try {
            if (list.isEmpty()) {
                Toast.makeText(this, "No Files", Toast.LENGTH_SHORT).show();
            } else {
                gridAdapter = new GridAdapter(this, list);
                downloadsRecycler.setAdapter(gridAdapter);
                downloadsRecycler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        startActivity(new Intent(DownloadsActivity.this, ShareActivity.class).putExtra("img", list.get(i).toString()));
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ArrayList<File> getImageFiles(File root) {
        ArrayList<File> list = new ArrayList();
        File[] files = root.listFiles();
        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()) {
                list.addAll(getImageFiles(files[i]));
            } else {
                if(files[i].getName().endsWith(".jpg") || files[i].getName().endsWith(".png") || files[i].getName().endsWith(".gif") || files[i].getName().endsWith(".mp4")) {
                    list.add(files[i]);
                }
            }
        }
        return  list;
    }

    private ActivityManager.MemoryInfo getMemoryInfo() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        assert activityManager != null;
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }
}