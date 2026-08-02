package com.mixtape.controller;

import com.mixtape.model.User;
import com.mixtape.service.SongService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final SongService songService;

    public UserController(SongService songService) {
        this.songService = songService;
    }

    // POST /api/users?username=alex
    @PostMapping
    public User createUser(@RequestParam String username) {
        return songService.getOrCreateUser(username);
    }

    // PUT /api/users/alex/volume?level=70
    @PutMapping("/{username}/volume")
    public User adjustVolume(@PathVariable String username, @RequestParam Integer level) {
        return songService.adjustVolume(username, level);
    }
}
