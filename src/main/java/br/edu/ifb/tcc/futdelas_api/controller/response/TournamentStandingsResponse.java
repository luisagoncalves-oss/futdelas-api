package br.edu.ifb.tcc.futdelas_api.controller.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.edu.ifb.tcc.futdelas_api.model.Standing;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class TournamentStandingsResponse {
    private List<Standing> standings;

    public List<Standing> getStandings() {
        return standings;
    }
}
