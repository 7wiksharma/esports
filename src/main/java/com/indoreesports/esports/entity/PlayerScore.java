package com.indoreesports.esports.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class PlayerScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    private int score; // Player's score in the tournament


    public PlayerScore() {}


    public PlayerScore(User user, Tournament tournament, int score) {
        this.user = user;
        this.tournament = tournament;
        this.score = score;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}