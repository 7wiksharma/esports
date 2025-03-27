package com.indoreesports.esports.DTO;

import com.indoreesports.esports.entity.PlayerScore;
import lombok.Getter;
import lombok.Setter;

public class PlayerScoreDTO {
    @Setter
    @Getter
    private String username;
    private int score;

    public PlayerScoreDTO(PlayerScore playerScore) {
        this.username = playerScore.getUser().getUsername();
        this.score = playerScore.getScore();
    }

    // Getters and Setters
}