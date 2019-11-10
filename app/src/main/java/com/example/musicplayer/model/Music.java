package com.example.musicplayer.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Music implements Serializable {
    private Long id;
    private String title;
    private String artist;
    private String album;
    private Uri uri;
    private String picPath;
    int duration;
 //   private Bitmap bitmap;

    public Uri getUri() {
        return uri;
    }

    public Bitmap getBitmap() {
       return BitmapFactory.decodeFile(getPicPath());


    }


    public String getPicPath() {
        return picPath;
    }

    public String getAlbum() {
        return album;
    }

    public int getDuration() {
        return duration;
    }

    public Music(Long id, String title, String artist, String album, Uri uri, String picPath, int duration) {
        this.picPath = picPath;
        this.uri = uri;
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;

        this.duration=duration;
     //   this.bitmap = bitmap;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }


}
