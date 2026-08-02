package com.mixtape.controller;

import com.mixtape.dto.AddSongRequest;
import com.mixtape.model.PlayHistory;
import com.mixtape.model.PlaylistSong;
import com.mixtape.service.SongService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    // POST /api/songs   { "username": "alex", "title": "...", "artist": "...", "duration": 210 }
    @PostMapping("/songs")
    public PlaylistSong addSong(@Valid @RequestBody AddSongRequest request) {
        return songService.addSong(request.getUsername(), request.getTitle(), request.getArtist(), request.getDuration());
    }

    // GET /api/users/alex/songs
    @GetMapping("/users/{username}/songs")
    public List<PlaylistSong> getPlaylist(@PathVariable String username) {
        return songService.getPlaylist(username);
    }

    // POST /api/users/alex/random
    @PostMapping("/users/{username}/random")
    public PlaylistSong playRandomSong(@PathVariable String username) {
        return songService.playRandomSong(username);
    }

    // POST /api/users/alex/songs/Neon%20Skyline/toggle-play
    @PostMapping("/users/{username}/songs/{songTitle}/toggle-play")
    public PlaylistSong togglePlayPause(@PathVariable String username, @PathVariable String songTitle) {
        return songService.togglePlayPause(username, songTitle);
    }

    // POST /api/users/alex/songs/Neon%20Skyline/toggle-like
    @PostMapping("/users/{username}/songs/{songTitle}/toggle-like")
    public PlaylistSong toggleLike(@PathVariable String username, @PathVariable String songTitle) {
        return songService.toggleLike(username, songTitle);
    }

    // GET /api/users/alex/liked
    @GetMapping("/users/{username}/liked")
    public List<PlaylistSong> getLikedSongs(@PathVariable String username) {
        return songService.getLikedSongs(username);
    }

    // GET /api/users/alex/history
    @GetMapping("/users/{username}/history")
    public List<PlayHistory> getHistory(@PathVariable String username) {
        return songService.getHistory(username);
    }
}
