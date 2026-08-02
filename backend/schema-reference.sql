-- Reference schema for MixTape.
-- You do NOT need to run this by hand — Hibernate creates these tables
-- automatically on startup (spring.jpa.hibernate.ddl-auto=update).
-- This file is here so you can see what the app is creating, or to set
-- up the schema manually if you ever turn auto-ddl off.

CREATE DATABASE IF NOT EXISTS mixtape;
USE mixtape;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    volume_level INT DEFAULT 50
);

CREATE TABLE IF NOT EXISTS songs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    artist VARCHAR(200),
    duration INT
);

CREATE TABLE IF NOT EXISTS playlist_songs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    song_id BIGINT NOT NULL,
    liked BOOLEAN DEFAULT FALSE,
    playing BOOLEAN DEFAULT FALSE,
    UNIQUE KEY uq_user_song (user_id, song_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (song_id) REFERENCES songs(id)
);

CREATE TABLE IF NOT EXISTS play_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    song_id BIGINT NOT NULL,
    played_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (song_id) REFERENCES songs(id)
);

CREATE TABLE IF NOT EXISTS friendships (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_a_id BIGINT NOT NULL,
    user_b_id BIGINT NOT NULL,
    match_percentage DOUBLE DEFAULT 0,
    streak_count INT DEFAULT 0,
    last_interaction DATE,
    UNIQUE KEY uq_friend_pair (user_a_id, user_b_id),
    FOREIGN KEY (user_a_id) REFERENCES users(id),
    FOREIGN KEY (user_b_id) REFERENCES users(id)
);
