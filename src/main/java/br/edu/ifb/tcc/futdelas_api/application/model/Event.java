package br.edu.ifb.tcc.futdelas_api.application.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {
    private Long id;
    private Team homeTeam;
    private Team awayTeam;
    private Season season;
    private Status status;
    private RoundInfo roundInfo;
}
