package br.edu.ifb.tcc.futdelas_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class TeamPerformance {
    private Long id;
    private Team team;
    private Integer position;
    private Integer matches;
    private Integer wins;
    private Integer scoresFor;
    private Integer scoresAgainst;
    private Integer losses;
    private Integer draws;
    private Integer points;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team = team;
    }
    public Integer getPosition() {
        return position;
    }
    public void setPosition(Integer position) {
        this.position = position;
    }
    public Integer getMatches() {
        return matches;
    }
    public void setMatches(Integer matches) {
        this.matches = matches;
    }
    public Integer getWins() {
        return wins;
    }
    public void setWins(Integer wins) {
        this.wins = wins;
    }
    public Integer getScoresFor() {
        return scoresFor;
    }
    public void setScoresFor(Integer scoresFor) {
        this.scoresFor = scoresFor;
    }
    public Integer getScoresAgainst() {
        return scoresAgainst;
    }
    public void setScoresAgainst(Integer scoresAgainst) {
        this.scoresAgainst = scoresAgainst;
    }
    public Integer getLosses() {
        return losses;
    }
    public void setLosses(Integer losses) {
        this.losses = losses;
    }
    public Integer getDraws() {
        return draws;
    }
    public void setDraws(Integer draws) {
        this.draws = draws;
    }
    public Integer getPoints() {
        return points;
    }
    public void setPoints(Integer points) {
        this.points = points;
    }

    
}