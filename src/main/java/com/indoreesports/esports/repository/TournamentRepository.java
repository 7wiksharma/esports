package com.indoreesports.esports.repository;

import com.indoreesports.esports.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository  extends JpaRepository<Tournament, Long> {
}
