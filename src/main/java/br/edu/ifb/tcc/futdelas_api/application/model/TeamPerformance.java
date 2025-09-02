package br.edu.ifb.tcc.futdelas_api.application.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
