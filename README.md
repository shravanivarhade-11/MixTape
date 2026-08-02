# MixTape 🎧

A music player app with a friend-matching twist — built in stages, from a
simple Java console app to a full Spring Boot + MySQL backend, with a
pixel-art vintage UI designed in Figma.

## Features

- **Per-user playlists** — add songs, organize by user
- **Play a random song**, with play/pause control
- **Volume control** (0–100) per user
- **Listening history** — every song played is logged and viewable later
- **Like / Unlike songs** and view a dedicated Liked Songs list (♥)
- **Vibe Match** — compares two users' playlists and returns a percentage of
  songs in common, mapped to a friendship rating (Strangers → Very Close
  Friends)
- **Song streaks** — sending a friend a song recommendation builds a daily
  streak, shown alongside their Vibe Match score

## Tech Stack

| Layer | Tech |
|---|---|
| Original prototype | Java (JDK, binary search tree, no dependencies) |
| Backend | Java 17, Spring Boot 3, Spring Data JPA |
| Database | MySQL |
| Frontend / UI design | Figma (pixel-art, vintage-styled screens) |

## Project Structure

```
MixTape/
├── Code/          # Original console prototype (single-file Java, no DB)
│   └── src/abc/Main.java
├── backend/        # Spring Boot + MySQL REST API
│   ├── src/main/java/com/mixtape/
│   │   ├── model/          # Entities (User, Song, PlaylistSong, PlayHistory, Friendship)
│   │   ├── repository/     # Spring Data JPA repositories
│   │   ├── service/        # Business logic (playlists, Vibe Match, streaks)
│   │   ├── controller/     # REST endpoints
│   │   └── dto/
│   ├── pom.xml
│   ├── schema-reference.sql
│   └── README.md            # Full backend setup instructions
├── Frontend/       # UI design assets / notes (see Figma link below)
└── README.md
```

## Design

The app's UI is designed in Figma — a pixel-art, vintage-styled set of
screens (Welcome, Home, Playlist, Now Playing, Vibe Match).

