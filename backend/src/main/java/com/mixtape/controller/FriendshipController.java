package com.mixtape.controller;

import com.mixtape.dto.FriendshipView;
import com.mixtape.dto.SendSongRequest;
import com.mixtape.model.Friendship;
import com.mixtape.service.FriendshipService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    // GET /api/friendships/match?user1=alex&user2=priya
    @GetMapping("/match")
    public FriendshipView getVibeMatch(@RequestParam String user1, @RequestParam String user2) {
        Friendship friendship = friendshipService.calculateVibeMatch(user1, user2);
        return FriendshipView.of(friendship, user1);
    }

    // POST /api/friendships/send-song  { "fromUsername": "alex", "toUsername": "priya", "songTitle": "..." }
    @PostMapping("/send-song")
    public FriendshipView sendSong(@Valid @RequestBody SendSongRequest request) {
        Friendship friendship = friendshipService.sendSong(
                request.getFromUsername(), request.getToUsername(), request.getSongTitle());
        return FriendshipView.of(friendship, request.getFromUsername());
    }

    // GET /api/friendships/alex  -> all of Alex's friendships for the Vibe Match screen
    @GetMapping("/{username}")
    public List<FriendshipView> getFriendshipsForUser(@PathVariable String username) {
        return friendshipService.getFriendshipsForUser(username).stream()
                .map(f -> FriendshipView.of(f, username))
                .toList();
    }
}
