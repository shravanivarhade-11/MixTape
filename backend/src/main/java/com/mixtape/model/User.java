package com.mixtape.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "volume_level")
    private Integer volumeLevel = 50;

    public User() {}

    public User(String username) {
        this.username = username;
        this.volumeLevel = 50;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Integer getVolumeLevel() { return volumeLevel; }
    public void setVolumeLevel(Integer volumeLevel) { this.volumeLevel = volumeLevel; }
}
