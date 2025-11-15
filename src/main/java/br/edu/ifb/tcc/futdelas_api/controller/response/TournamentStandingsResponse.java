package br.edu.ifb.tcc.futdelas_api.controller.response;

import java.util.List;

import br.edu.ifb.tcc.futdelas_api.model.Standing;

public class TournamentStandingsResponse {
    private List<Standing> standings;

    public List<Standing> getStandings() {
        return standings;
    }
}
