package com.mixtape.service;

import com.mixtape.model.*;
import com.mixtape.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SongService {

    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final PlayHistoryRepository playHistoryRepository;

    public SongService(UserRepository userRepository,
                        SongRepository songRepository,
                        PlaylistSongRepository playlistSongRepository,
                        PlayHistoryRepository playHistoryRepository) {
        this.userRepository = userRepository;
        this.songRepository = songRepository;
        this.playlistSongRepository = playlistSongRepository;
        this.playHistoryRepository = playHistoryRepository;
    }

    // Finds a user by username, or creates one if they don't exist yet
    // (equivalent to BinaryTree.insert auto-creating a TreeNode).
    public User getOrCreateUser(String username) {
        return userRepository.findByUsername(username)
                .orElseGet(() -> userRepository.save(new User(username)));
    }

    // Menu option 1: Add a song to the Music Player
    public PlaylistSong addSong(String username, String title, String artist, Integer duration) {
        User user = getOrCreateUser(username);
        Song song = songRepository.save(new Song(title, artist, duration));
        PlaylistSong playlistSong = new PlaylistSong(user, song);
        return playlistSongRepository.save(playlistSong);
    }

    public List<PlaylistSong> getPlaylist(String username) {
        User user = getOrCreateUser(username);
        return playlistSongRepository.findByUser(user);
    }

    // Menu option 2: Play a random song
    public PlaylistSong playRandomSong(String username) {
        User user = getOrCreateUser(username);
        List<PlaylistSong> songs = playlistSongRepository.findByUser(user);
        if (songs.isEmpty()) {
            throw new IllegalStateException("No songs available for user: " + username);
        }
        PlaylistSong chosen = songs.get(new Random().nextInt(songs.size()));
        playHistoryRepository.save(new PlayHistory(user, chosen.getSong()));
        return chosen;
    }

    // Menu option 3: Play/Pause a particular song
    public PlaylistSong togglePlayPause(String username, String songTitle) {
        User user = getOrCreateUser(username);
        PlaylistSong ps = playlistSongRepository.findByUserAndSong_Title(user, songTitle)
                .orElseThrow(() -> new IllegalArgumentException("Song not found in the user's playlist."));
        ps.setPlaying(!ps.getPlaying());
        return playlistSongRepository.save(ps);
    }

    // Menu option 6: Adjust volume control
    public User adjustVolume(String username, Integer volumeLevel) {
        if (volumeLevel < 0 || volumeLevel > 100) {
            throw new IllegalArgumentException("Volume level must be between 0 and 100.");
        }
        User user = getOrCreateUser(username);
        user.setVolumeLevel(volumeLevel);
        return userRepository.save(user);
    }

    // Menu option 7: History
    public List<PlayHistory> getHistory(String username) {
        User user = getOrCreateUser(username);
        return playHistoryRepository.findByUserOrderByPlayedAtDesc(user);
    }

    // Menu option 9: Like/Unlike a song (heart icon on the Playlist / Now Playing screens)
    public PlaylistSong toggleLike(String username, String songTitle) {
        User user = getOrCreateUser(username);
        PlaylistSong ps = playlistSongRepository.findByUserAndSong_Title(user, songTitle)
                .orElseThrow(() -> new IllegalArgumentException("Song not found in the user's playlist."));
        ps.setLiked(!ps.getLiked());
        return playlistSongRepository.save(ps);
    }

    // Menu option 10: View liked songs
    public List<PlaylistSong> getLikedSongs(String username) {
        User user = getOrCreateUser(username);
        return playlistSongRepository.findByUserAndLikedTrue(user);
    }
}
