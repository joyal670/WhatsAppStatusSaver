package com.whatsappstatussaver.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.whatsappstatussaver.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class ViewImageActivity extends AppCompatActivity {

    ImageView imageView;
    VideoView videoView;
    FloatingActionButton floatingAB;
    public final String DIR_SAVE = "/WSDownloader/";
    String file;

    String storagePermission[];
    private static final int STORAGE_REQUEST_CODE = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        try {

            Intent intent = getIntent();
            file = intent.getStringExtra("img");

            imageView = (ImageView) findViewById(R.id.imageView);
            videoView = (VideoView) findViewById(R.id.videoView);
            floatingAB = (FloatingActionButton) findViewById(R.id.floatingAB);

            storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

            try {

                if (file.endsWith(".jpg") || file.endsWith(".png") || file.endsWith(".gif")) {
                    imageView.setVisibility(View.VISIBLE);
                    videoView.setVisibility(View.INVISIBLE);
                    Glide.with(getApplicationContext())
                            .load(file)
                            .into(imageView);
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                    videoView.setVisibility(View.VISIBLE);
                    videoView.setVideoURI(Uri.parse(file));

                    final MediaController mediaController = new MediaController(this);
                    mediaController.requestFocus();

                    mediaController.setAnchorView(videoView);


                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.setLooping(true);
                            mediaController.show(0);
                        }
                    });

                    videoView.setMediaController(mediaController);
                    videoView.start();
                }

                floatingAB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!checkPermission()) {
                            requestPermission();
                        } else {
                            SaveData();
                        }

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (!storageAccepted) {
                        Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show();
                    } else {
                        SaveData();
                    }
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST_CODE);
    }


    private boolean checkPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void SaveData() {
        try {
            final File currentFile = new File(file);
            File destfile = new File(Environment.getExternalStorageDirectory().toString() + DIR_SAVE + currentFile.getName());
            Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
            try {
                copyFile(currentFile, destfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyFile(File sourceFile, File destfile) throws IOException {
        try {
            if (!destfile.getParentFile().exists()) {
                destfile.getParentFile().mkdir();
            }

            if (!destfile.exists())
                destfile.createNewFile();

            FileChannel source = null;
            FileChannel destination = null;

            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destfile).getChannel();

            destination.transferFrom(source, 0, source.size());

            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}