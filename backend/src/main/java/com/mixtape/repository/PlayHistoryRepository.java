package com.mixtape.repository;

import com.mixtape.model.PlayHistory;
import com.mixtape.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlayHistoryRepository extends JpaRepository<PlayHistory, Long> {
    List<PlayHistory> findByUserOrderByPlayedAtDesc(User user);
}
