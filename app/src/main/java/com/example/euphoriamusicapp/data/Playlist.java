package com.example.euphoriamusicapp.data;

public class Playlist {


    private String playlistName;
    private  MusicAndPodcast song;

    public Playlist() {
    }

    public Playlist(int resourceId, String playlistName, MusicAndPodcast song) {

        this.playlistName = playlistName;
        this.song = song;
    }


    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public MusicAndPodcast getSong() {
        return song;
    }

    public void setSong(MusicAndPodcast song) {
        this.song = song;
    }
}
