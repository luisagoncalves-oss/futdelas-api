package br.edu.ifb.tcc.futdelas_api.model;

public class Event {
    private Long id;
    private Team homeTeam;
    private Team awayTeam;
    private Season season;
    private Status status;
    private RoundInfo roundInfo;
    private Score homeScore;
    private Score awayScore;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Team getHomeTeam() {
        return homeTeam;
    }
    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }
    public Team getAwayTeam() {
        return awayTeam;
    }
    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }
    public Season getSeason() {
        return season;
    }
    public void setSeason(Season season) {
        this.season = season;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public RoundInfo getRoundInfo() {
        return roundInfo;
    }
    public void setRoundInfo(RoundInfo roundInfo) {
        this.roundInfo = roundInfo;
    }
    public Score getHomeScore() {
        return homeScore;
    }
    public void setHomeScore(Score homeScore) {
        this.homeScore = homeScore;
    }
    public Score getAwayScore() {
        return awayScore;
    }
    public void setAwayScore(Score awayScore) {
        this.awayScore = awayScore;
    }
}
