package br.edu.ifb.tcc.futdelas_api.presentation.controller.response;

import br.edu.ifb.tcc.futdelas_api.application.model.PregameForm;
import br.edu.ifb.tcc.futdelas_api.application.model.Team;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDetailsResponse {
    private Team team;
    private PregameForm pregameForm;
}
