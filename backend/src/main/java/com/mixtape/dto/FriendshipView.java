package com.mixtape.dto;

import com.mixtape.model.Friendship;
import com.mixtape.service.FriendshipService;

import java.time.LocalDate;

// What the Vibe Match screen actually needs: the other person's name,
// match %, friendship-level label, and streak count.
public class FriendshipView {
    private String friendUsername;
    private Double matchPercentage;
    private String level;
    private Integer streakCount;
    private LocalDate lastInteraction;

    public static FriendshipView of(Friendship friendship, String forUsername) {
        FriendshipView view = new FriendshipView();
        String userAName = friendship.getUserA().getUsername();
        view.friendUsername = userAName.equals(forUsername)
                ? friendship.getUserB().getUsername()
                : userAName;
        view.matchPercentage = friendship.getMatchPercentage();
        view.level = FriendshipService.getFriendshipLevel(friendship.getMatchPercentage());
        view.streakCount = friendship.getStreakCount();
        view.lastInteraction = friendship.getLastInteraction();
        return view;
    }

    public String getFriendUsername() { return friendUsername; }
    public Double getMatchPercentage() { return matchPercentage; }
    public String getLevel() { return level; }
    public Integer getStreakCount() { return streakCount; }
    public LocalDate getLastInteraction() { return lastInteraction; }
}
