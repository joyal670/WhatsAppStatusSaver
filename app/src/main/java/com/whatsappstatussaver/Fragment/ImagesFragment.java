package com.whatsappstatussaver.Fragment;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.whatsappstatussaver.Activity.ViewImageActivity;
import com.whatsappstatussaver.Adapter.GridAdapter;
import com.whatsappstatussaver.R;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.ACTIVITY_SERVICE;

public class ImagesFragment extends Fragment {

    GridView gridView;
    ArrayList<File> list;
    GridAdapter gridAdapter;
    SwipeRefreshLayout pullToRefreshImageFrg;

    public ImagesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_images, container, false);

        try {

            gridView = (GridView) view.findViewById(R.id.gridViewHome);
            pullToRefreshImageFrg = view.findViewById(R.id.pullToRefreshImageFrg);
            pullToRefreshImageFrg.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    load();
                    pullToRefreshImageFrg.setRefreshing(false);
                }
            });
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
            } else {
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
                if (files[i].getName().endsWith(".jpg") || files[i].getName().endsWith(".png") || files[i].getName().endsWith(".gif")) {
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
