package com.whatsappstatussaver.Fragment;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.whatsappstatussaver.Activity.ViewImageActivity;
import com.whatsappstatussaver.Adapter.GridAdapter;
import com.whatsappstatussaver.R;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.ACTIVITY_SERVICE;

public class VideoFragment extends Fragment {

    GridView gridView;
    ArrayList<File> list;
    GridAdapter gridAdapter;
    SwipeRefreshLayout pullToRefreshVidFrg;

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);

        gridView = (GridView) view.findViewById(R.id.gridViewAbt);
        pullToRefreshVidFrg = view.findViewById(R.id.pullToRefreshVidFrg);
        pullToRefreshVidFrg.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
                pullToRefreshVidFrg.setRefreshing(false);
            }
        });

        try {
            list = getImageFiles(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses"));
            load();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void load()
    {
        try {

            if (list.isEmpty()) {
                Toast.makeText(getContext(), "No Files", Toast.LENGTH_SHORT).show();
            } else
            {
                gridAdapter = new GridAdapter(getContext(), list);
                gridView.setAdapter(gridAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        startActivity(new Intent(getContext(), ViewImageActivity.class).putExtra("img", list.get(i).toString()));
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
            if (files[i].isDirectory()) {
                list.addAll(getImageFiles(files[i]));
            } else {
                if (files[i].getName().endsWith(".mp4")) {
                    list.add(files[i]);
                }
            }
        }
        return list;
    }

    private ActivityManager.MemoryInfo getMemoryInfo() {
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        assert activityManager != null;
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }
}