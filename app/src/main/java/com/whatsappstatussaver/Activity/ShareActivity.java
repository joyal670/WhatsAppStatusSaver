package com.whatsappstatussaver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.whatsappstatussaver.BuildConfig;
import com.whatsappstatussaver.R;

import java.io.File;

public class ShareActivity extends AppCompatActivity 
{
    String file;
    ImageView imageView;
    VideoView videoView;
    FloatingActionButton floatingAB;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        try {

            Intent intent = getIntent();
            file = intent.getStringExtra("img");

            imageView = (ImageView) findViewById(R.id.imageViewSaved);
            videoView = (VideoView) findViewById(R.id.videoViewSaved);
            floatingAB = (FloatingActionButton) findViewById(R.id.floatingABSaved);

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
                    shareFile();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shareFile()
    {
        try {

            Uri uri = FileProvider.getUriForFile(ShareActivity.this, BuildConfig.APPLICATION_ID + ".provider", new File(file));
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            share.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(share, "Share via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}