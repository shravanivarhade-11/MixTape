# MixTape 🎧

A console-based music player app built in Java, using a binary search tree to organize per-user playlists. Includes playback controls, listening history, a "Vibe Match" feature that compares musical taste between friends, and a liked-songs (heart) system.

> Design mockups (five screens — Welcome, Home, Playlist, Now Playing, Vibe Match) are available in Figma: *add your Figma link here*

## Features

- **Per-user playlists** stored in a binary search tree, keyed by username
- **Play a random song** from a user's playlist, with played-song tracking so repeats are avoided within a session
- **Play / Pause** toggle for any song
- **Volume control** (0–100) per user
- **Listening history** — every song played is logged and can be viewed later
- **Like / Unlike songs** and view a dedicated Liked Songs list (♥)
- **Vibe Match** — compares two users' playlists and returns a percentage of songs in common, mapped to a friendship rating (Strangers → Very Close Friends)

## Tech Stack

- Java (no external dependencies — just the JDK standard library)

## Project Structure

```
MixTape/
├── src/
│   └── abc/
│       └── Main.java     # Song, TreeNode, BinaryTree, and Main (entry point)
├── README.md
├── LICENSE
└── .gitignore
```

## Getting Started

### Prerequisites

- [Java Development Kit (JDK) 8 or later](https://adoptium.net/) installed
- Verify with:
  ```bash
  java -version
  javac -version
  ```

### Run it

Clone the repo, then from the project root:

```bash
javac -d out src/abc/Main.java
java -cp out abc.Main
```

You'll see an interactive menu in your terminal:

```
Menu:
1. Add a song to the Music Player
2. Play a random song
3. Play/Pause a particular song
4. Skip backward
5. Skip forward
6. Adjust volume control
7. History
8. Friendship feature (Vibe Match)
9. Like/Unlike a song
10. View liked songs
11. Exit
```

Start by adding a few songs for one or more usernames (option 1), then explore the rest of the menu.

## Roadmap / Ideas

- [ ] Implement real skip-backward / skip-forward logic (currently placeholders)
- [ ] Persist playlists to a file or database between runs
- [ ] Swap the console UI for the Figma-designed mobile UI (React Native / Android / iOS)
