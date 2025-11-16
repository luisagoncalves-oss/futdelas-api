package br.edu.ifb.tcc.futdelas_api.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.edu.ifb.tcc.futdelas_api.model.UniqueTournament;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class TournamentDetailResponse {
    private UniqueTournament uniqueTournament;

    public UniqueTournament getUniqueTournament() {
        return uniqueTournament;
    }
}
