package com.example.musicplayer.controler;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.example.musicplayer.model.Music;

import java.io.IOException;
import java.util.List;

public class ControlMusic {
    public static  ControlMusic instance ;
    private MediaPlayer mediaPlayer;
    private List<Music> musicList;
    Music mMusic;
    private  int currentPosition;

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public static ControlMusic getInstance() {
        if (instance == null)
            instance= new ControlMusic();
        return instance;
    }

    private ControlMusic() {
        mediaPlayer=new MediaPlayer();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void play(Music music, Context context) throws IOException {
        mMusic = music;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(context.getApplicationContext(), mMusic.getUri());
        mediaPlayer.prepare();
        mediaPlayer.start();
        if(mediaPlayer.getDuration()>=mMusic.getDuration()){

        }



    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void reset() {
mediaPlayer.reset();
    }
    public void start(){
        mediaPlayer.start();
    }
}