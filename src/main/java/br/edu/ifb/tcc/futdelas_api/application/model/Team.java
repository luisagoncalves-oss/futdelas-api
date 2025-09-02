package br.edu.ifb.tcc.futdelas_api.application.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {
    private Long id;
    private String name;
    private String nameCode;
    private TeamColors teamColors;
    private Manager manager;
}
