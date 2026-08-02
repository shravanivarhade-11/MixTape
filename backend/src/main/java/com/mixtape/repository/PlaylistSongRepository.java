package com.mixtape.repository;

import com.mixtape.model.PlaylistSong;
import com.mixtape.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Long> {
    List<PlaylistSong> findByUser(User user);
    List<PlaylistSong> findByUserAndLikedTrue(User user);
    Optional<PlaylistSong> findByUserAndSong_Title(User user, String songTitle);
}
