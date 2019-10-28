package com.example.musicplayer.Repositories;

import com.example.musicplayer.model.Music;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.musicplayer.model.Music;

public class SongRepository {
    private static  SongRepository instance ;
    List<Music> musicList;

    public static SongRepository getInstance() {
        if (instance == null)
            instance= new SongRepository();
        return instance;
    }

    private SongRepository() {
        musicList = new ArrayList<>();
    }
    public List<Music> getMusicList(){
        return musicList;
    }
    public void addMusic(Music music){
        musicList.add(music);
    }
    public void removeMusic(Music music){
        musicList.remove(music);
    }
    public void sort(){
        Collections.sort(musicList, new Comparator<Music>() {
            @Override
            public int compare(Music music, Music t1) {
                return music.getTitle().compareTo(t1.getTitle());
            }
        });
    }
}
