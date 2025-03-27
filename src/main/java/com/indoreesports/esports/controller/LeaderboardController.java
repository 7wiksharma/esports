package com.indoreesports.esports.controller;

import com.indoreesports.esports.DTO.PlayerScoreDTO;
import com.indoreesports.esports.service.PlayerScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    @Autowired
    private PlayerScoreService playerScoreService;

    // API to get top 10 players globally
    @GetMapping("/top")
    public ResponseEntity<List<PlayerScoreDTO>> getTopPlayers() {
        return ResponseEntity.ok(playerScoreService.getTopPlayers());
    }

    // API to get top 10 players in a specific tournament
    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<PlayerScoreDTO>> getTopPlayersByTournament(@PathVariable Long tournamentId) {
        return ResponseEntity.ok(playerScoreService.getTopPlayersByTournament(tournamentId));
    }
}