package com.mixtape.model;

import jakarta.persistence.*;
import java.time.LocalDate;

// One row per friend pair. userA/userB order doesn't matter for the math,
// but we always store the lower user id as userA so a pair is never duplicated.
@Entity
@Table(name = "friendships", uniqueConstraints = @UniqueConstraint(columnNames = {"user_a_id", "user_b_id"}))
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_a_id", nullable = false)
    private User userA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_b_id", nullable = false)
    private User userB;

    @Column(name = "match_percentage")
    private Double matchPercentage = 0.0;

    @Column(name = "streak_count")
    private Integer streakCount = 0;

    @Column(name = "last_interaction")
    private LocalDate lastInteraction;

    public Friendship() {}

    public Friendship(User userA, User userB) {
        this.userA = userA;
        this.userB = userB;
        this.matchPercentage = 0.0;
        this.streakCount = 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUserA() { return userA; }
    public void setUserA(User userA) { this.userA = userA; }

    public User getUserB() { return userB; }
    public void setUserB(User userB) { this.userB = userB; }

    public Double getMatchPercentage() { return matchPercentage; }
    public void setMatchPercentage(Double matchPercentage) { this.matchPercentage = matchPercentage; }

    public Integer getStreakCount() { return streakCount; }
    public void setStreakCount(Integer streakCount) { this.streakCount = streakCount; }

    public LocalDate getLastInteraction() { return lastInteraction; }
    public void setLastInteraction(LocalDate lastInteraction) { this.lastInteraction = lastInteraction; }
}
