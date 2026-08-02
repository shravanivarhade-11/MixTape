package com.mixtape.repository;

import com.mixtape.model.Friendship;
import com.mixtape.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Optional<Friendship> findByUserAAndUserB(User userA, User userB);
    List<Friendship> findByUserAOrUserB(User userA, User userB);
}
