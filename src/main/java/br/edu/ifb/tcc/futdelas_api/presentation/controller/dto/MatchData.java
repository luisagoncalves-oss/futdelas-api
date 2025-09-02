package br.edu.ifb.tcc.futdelas_api.presentation.controller.dto;

public class MatchData {
    public String tournament;
    public String homeTeam;
    public String awayTeam;
    public Integer homeScore;
    public Integer awayScore;
    public String status;
    public Long startTimestamp;
    public String homeTeamColor;
    public String awayTeamColor;

    public static MatchData mock() {
        MatchData m = new MatchData();
        m.tournament = "Brasileirão Série A1, Women";
        m.homeTeam = "Bahia";
        m.awayTeam = "Ferroviária";
        m.homeScore = 3;
        m.awayScore = 1;
        m.status = "Encerrada";
        m.startTimestamp = 1749391200L;
        m.homeTeamColor = "#333399";
        m.awayTeamColor = "#9c271a";
        return m;
    }
}