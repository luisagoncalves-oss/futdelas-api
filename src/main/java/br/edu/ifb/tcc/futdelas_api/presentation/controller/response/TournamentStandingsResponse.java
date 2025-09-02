package br.edu.ifb.tcc.futdelas_api.presentation.controller.response;

import java.util.List;

import br.edu.ifb.tcc.futdelas_api.application.model.Standing;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TournamentStandingsResponse {
    private List<Standing> standings;
}
