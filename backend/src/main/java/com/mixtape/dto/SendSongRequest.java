package com.mixtape.dto;

import jakarta.validation.constraints.NotBlank;

public class SendSongRequest {

    @NotBlank
    private String fromUsername;

    @NotBlank
    private String toUsername;

    @NotBlank
    private String songTitle;

    public String getFromUsername() { return fromUsername; }
    public void setFromUsername(String fromUsername) { this.fromUsername = fromUsername; }

    public String getToUsername() { return toUsername; }
    public void setToUsername(String toUsername) { this.toUsername = toUsername; }

    public String getSongTitle() { return songTitle; }
    public void setSongTitle(String songTitle) { this.songTitle = songTitle; }
}
