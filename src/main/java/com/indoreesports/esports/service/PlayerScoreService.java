package com.indoreesports.esports.service;

import com.indoreesports.esports.DTO.PlayerScoreDTO;
import com.indoreesports.esports.entity.Tournament;
import com.indoreesports.esports.repository.PlayerScoreRepository;
import com.indoreesports.esports.repository.TournamentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerScoreService {

    @Autowired
    private PlayerScoreRepository playerScoreRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    // Fetch top 10 players globally
    public List<PlayerScoreDTO> getTopPlayers() {
        return playerScoreRepository.findTop10ByOrderByScoreDesc()
                .stream()
                .map(PlayerScoreDTO::new)
                .collect(Collectors.toList());
    }

    // Fetch top 10 players for a specific tournament
    public List<PlayerScoreDTO> getTopPlayersByTournament(Long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found"));

        return playerScoreRepository.findTop10ByTournamentOrderByScoreDesc(tournament)
                .stream()
                .map(PlayerScoreDTO::new)
                .collect(Collectors.toList());
    }
}
