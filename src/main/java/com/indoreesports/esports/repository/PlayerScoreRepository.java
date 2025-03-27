package com.indoreesports.esports.repository;

import com.indoreesports.esports.entity.PlayerScore;
import com.indoreesports.esports.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerScoreRepository extends JpaRepository<PlayerScore, Long> {
    List<PlayerScore> findTop10ByOrderByScoreDesc(); // Fetch top 10 players globally

    List<PlayerScore> findTop10ByTournamentOrderByScoreDesc(Tournament tournament); // Top 10 per tournament
}