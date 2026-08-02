package com.mixtape.model;

import jakarta.persistence.*;

// Links a user to a song in their playlist (equivalent to the old
// TreeNode.songs list, but persisted). One row per (user, song) pair.
@Entity
@Table(name = "playlist_songs", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "song_id"}))
public class PlaylistSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id", nullable = false)
    private Song song;

    private Boolean liked = false;

    private Boolean playing = false;

    public PlaylistSong() {}

    public PlaylistSong(User user, Song song) {
        this.user = user;
        this.song = song;
        this.liked = false;
        this.playing = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Song getSong() { return song; }
    public void setSong(Song song) { this.song = song; }

    public Boolean getLiked() { return liked; }
    public void setLiked(Boolean liked) { this.liked = liked; }

    public Boolean getPlaying() { return playing; }
    public void setPlaying(Boolean playing) { this.playing = playing; }
}
