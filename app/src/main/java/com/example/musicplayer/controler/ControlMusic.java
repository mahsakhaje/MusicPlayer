package com.example.musicplayer.controler;

import android.content.ContentUris;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.musicplayer.model.Music;

import java.io.IOException;
import java.util.List;

public class ControlMusic {
    private MediaPlayer player;
    private List<Music> musicList;

    public ControlMusic() {
        player = new MediaPlayer();
    }

    public void play(Music music, Context context) throws IOException {

            if (player.isPlaying()) {
                player.stop();
                player.reset();
                player.release();
                player=new MediaPlayer();
            }



        player.setDataSource(context, music.getUri());
        player.prepare();
        player.start();


    }

    public void stop() {
        player.stop();
    }
}
