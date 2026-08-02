# MixTape Backend (Spring Boot + MySQL)

REST API backend for MixTape, rebuilt from the original console app. Same
features — playlists, playback, history, likes — plus the **Vibe Match**
feature (friendship % + song streaks) properly persisted in a database.

## Prerequisites

- **Java 17+** — check with `java -version`
- **Maven** — check with `mvn -version` (or use the included `mvnw` wrapper if present in your IDE)
- **MySQL 8+** installed and running locally

## 1. Install MySQL (if you haven't already)

- Windows/Mac: download from [dev.mysql.com/downloads](https://dev.mysql.com/downloads/mysql/) or use the MySQL Installer
- Mac (Homebrew): `brew install mysql && brew services start mysql`
- Make sure you know your **root password** (or create a new MySQL user)

You do **not** need to manually create the `mixtape` database or any tables —
the app creates them automatically on first run.

## 2. Configure the connection

Open `src/main/resources/application.properties` and update these two lines
with your actual MySQL credentials:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

## 3. Run the app

From the `mixtape-backend` folder:

```bash
mvn spring-boot:run
```

First run will take a minute to download dependencies. Once you see:

```
Started MixtapeApplication in X seconds
```

...the API is live at `http://localhost:8080`.

(If you're using IntelliJ/Eclipse instead: just open the folder as a Maven
project and run `MixtapeApplication.java` directly.)

## 4. Try it out

Use curl, Postman, or Insomnia. A few examples:

**Add a song for a user** (creates the user automatically if new):
```bash
curl -X POST http://localhost:8080/api/songs \
  -H "Content-Type: application/json" \
  -d '{"username":"alex","title":"Neon Skyline","artist":"Wave Chaser","duration":222}'
```

**View a user's playlist:**
```bash
curl http://localhost:8080/api/users/alex/songs
```

**Play a random song:**
```bash
curl -X POST http://localhost:8080/api/users/alex/random
```

**Like a song:**
```bash
curl -X POST "http://localhost:8080/api/users/alex/songs/Neon%20Skyline/toggle-like"
```

**View liked songs / history:**
```bash
curl http://localhost:8080/api/users/alex/liked
curl http://localhost:8080/api/users/alex/history
```

**Vibe Match between two users:**
```bash
curl "http://localhost:8080/api/friendships/match?user1=alex&user2=priya"
```

**Send a song recommendation (builds the streak — the USP feature):**
```bash
curl -X POST http://localhost:8080/api/friendships/send-song \
  -H "Content-Type: application/json" \
  -d '{"fromUsername":"alex","toUsername":"priya","songTitle":"Neon Skyline"}'
```

**View all of a user's friendships (for the Vibe Match screen):**
```bash
curl http://localhost:8080/api/friendships/alex
```

## API reference

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/users?username=` | Create a user |
| PUT | `/api/users/{username}/volume?level=` | Adjust volume |
| POST | `/api/songs` | Add a song to a user's playlist |
| GET | `/api/users/{username}/songs` | Get a user's playlist |
| POST | `/api/users/{username}/random` | Play a random song |
| POST | `/api/users/{username}/songs/{title}/toggle-play` | Play/pause a song |
| POST | `/api/users/{username}/songs/{title}/toggle-like` | Like/unlike a song |
| GET | `/api/users/{username}/liked` | View liked songs |
| GET | `/api/users/{username}/history` | View play history |
| GET | `/api/friendships/match?user1=&user2=` | Compute Vibe Match % |
| POST | `/api/friendships/send-song` | Send a song, extend the streak |
| GET | `/api/friendships/{username}` | List a user's friendships |

## Project structure

```
mixtape-backend/
├── pom.xml
├── schema-reference.sql          # what Hibernate auto-creates, for reference
└── src/main/
    ├── java/com/mixtape/
    │   ├── MixtapeApplication.java
    │   ├── model/                # @Entity classes = your database tables
    │   ├── repository/           # Spring Data JPA interfaces (auto SQL)
    │   ├── service/               # business logic
    │   ├── controller/           # REST endpoints
    │   └── dto/                   # request/response shapes
    └── resources/
        └── application.properties
```

## What's next

- Connect your pixel-vintage HTML/Figma frontend to these endpoints with `fetch()`
- Add authentication (Spring Security) if this goes beyond a local demo
- Deploy: Railway, Render, or AWS all support Spring Boot + MySQL easily
