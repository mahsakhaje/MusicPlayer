package com.example.musicplayer.model;

import android.graphics.Bitmap;

import java.util.List;

public class Album {
    private long mAlbumId;
    private List<Music> mMusicLis;
    private Bitmap mAlbumPic;

    public long getmAlbumId() {
        return mAlbumId;
    }

    public void setmAlbumId(long mAlbumId) {
        this.mAlbumId = mAlbumId;
    }

    public List<Music> getmMusicLis() {
        return mMusicLis;
    }

    public void setmMusicLis(List<Music> mMusicLis) {
        this.mMusicLis = mMusicLis;
    }

    public Bitmap getmAlbumPic() {
        return mAlbumPic;
    }

    public void setmAlbumPic(Bitmap mAlbumPic) {
        this.mAlbumPic = mAlbumPic;
    }
}
