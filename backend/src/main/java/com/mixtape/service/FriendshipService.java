package com.mixtape.service;

import com.mixtape.model.*;
import com.mixtape.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FriendshipService {

    private final UserRepository userRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final FriendshipRepository friendshipRepository;

    public FriendshipService(UserRepository userRepository,
                              PlaylistSongRepository playlistSongRepository,
                              FriendshipRepository friendshipRepository) {
        this.userRepository = userRepository;
        this.playlistSongRepository = playlistSongRepository;
        this.friendshipRepository = friendshipRepository;
    }

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }

    // Always stores the friend pair with the lower user id as userA, so
    // (Alex, Priya) and (Priya, Alex) resolve to the same row.
    private Friendship getOrCreateFriendship(User u1, User u2) {
        User userA = u1.getId() < u2.getId() ? u1 : u2;
        User userB = u1.getId() < u2.getId() ? u2 : u1;
        return friendshipRepository.findByUserAAndUserB(userA, userB)
                .orElseGet(() -> friendshipRepository.save(new Friendship(userA, userB)));
    }

    // Menu option 8, rebuilt as the "Vibe Match" feature: percentage of songs
    // in common between two users' playlists, matched by song title.
    public Friendship calculateVibeMatch(String username1, String username2) {
        User user1 = getUser(username1);
        User user2 = getUser(username2);

        Set<String> titles1 = playlistSongRepository.findByUser(user1).stream()
                .map(ps -> ps.getSong().getTitle())
                .collect(Collectors.toSet());
        Set<String> titles2 = playlistSongRepository.findByUser(user2).stream()
                .map(ps -> ps.getSong().getTitle())
                .collect(Collectors.toSet());

        long commonSongs = titles1.stream().filter(titles2::contains).count();
        int smallerPlaylist = Math.min(titles1.size(), titles2.size());
        double percentage = smallerPlaylist == 0 ? 0.0 : (commonSongs * 100.0) / smallerPlaylist;

        Friendship friendship = getOrCreateFriendship(user1, user2);
        friendship.setMatchPercentage(percentage);
        return friendshipRepository.save(friendship);
    }

    public static String getFriendshipLevel(double percentage) {
        if (percentage >= 80.0) return "Very Close Friends";
        if (percentage >= 60.0) return "Good Friends";
        if (percentage >= 40.0) return "Friends";
        if (percentage >= 20.0) return "Acquaintances";
        return "Strangers";
    }

    // USP feature: sending a song recommendation to a friend extends (or starts)
    // a daily streak, shown as the flame icon on the Vibe Match screen.
    public Friendship sendSong(String fromUsername, String toUsername, String songTitle) {
        User from = getUser(fromUsername);
        User to = getUser(toUsername);
        Friendship friendship = getOrCreateFriendship(from, to);

        LocalDate today = LocalDate.now();
        LocalDate last = friendship.getLastInteraction();

        if (last == null || last.isBefore(today.minusDays(1))) {
            friendship.setStreakCount(1); // gap of 2+ days (or first ever) - streak restarts
        } else if (last.isEqual(today.minusDays(1))) {
            friendship.setStreakCount(friendship.getStreakCount() + 1); // consecutive day - streak grows
        }
        // if last == today, streak stays as-is (already logged today)

        friendship.setLastInteraction(today);
        return friendshipRepository.save(friendship);
    }

    public List<Friendship> getFriendshipsForUser(String username) {
        User user = getUser(username);
        return friendshipRepository.findByUserAOrUserB(user, user);
    }
}
