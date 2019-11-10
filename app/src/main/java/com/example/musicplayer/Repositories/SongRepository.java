package com.example.musicplayer.Repositories;

import com.example.musicplayer.model.Album;
import com.example.musicplayer.model.Music;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.musicplayer.model.Music;

public class SongRepository {
    private static SongRepository instance;
    List<Music> musicList;
    List<String> albums;
    Music mCurrentMusic;
    List<Album> mAlbumList;

    public Music getmCurrentMusic() {
        return mCurrentMusic;
    }

    public void setmCurrentMusic(Music mCurrentMusic) {
        this.mCurrentMusic = mCurrentMusic;
    }

    public static SongRepository getInstance() {
        if (instance == null)
            instance = new SongRepository();
        return instance;
    }

    private SongRepository() {
        musicList = new ArrayList<>();
        albums = new ArrayList<>();
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    public List<String> getMusicsAlbum() {
        for (Music music : musicList) {
            if (!albums.contains(music.getAlbum()))
                albums.add(music.getAlbum());
        }
        return albums;
    }

    public void addMusic(Music music) {
        musicList.add(music);
    }

    public void removeMusic(Music music) {
        musicList.remove(music);
    }

    public void sort() {
        Collections.sort(musicList, new Comparator<Music>() {
            @Override
            public int compare(Music music, Music t1) {
                return music.getTitle().compareTo(t1.getTitle());
            }
        });
    }

    public Music getNextMusic(int position) {
        if (position == musicList.size() - 1) {
            return musicList.get(0);
        }
        return musicList.get(position + 1 % musicList.size());
    }

    public Music getPriveMusic(int position) {
        if (position == 0) {
            return musicList.get(musicList.size() - 1);
        }
        return musicList.get(position - 1 % (musicList.size() - 1));
    }

    public Music getMusic(Long id) {
        for (Music music : musicList) {
            if (music.getId() == id)
                return music;
        }
        return null;
    }
    
}
