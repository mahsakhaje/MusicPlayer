package com.example.musicplayer.model;

public class Music {
    private Long id;
    private String title;
    private String artist;
    private String album;

    public String getAlbum() {
        return album;
    }

    public Music(Long id, String title, String artist, String album) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album=album;
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
