package br.edu.ifb.tcc.futdelas_api.controller.response;

import br.edu.ifb.tcc.futdelas_api.model.Team;
import br.edu.ifb.tcc.futdelas_api.model.PregameForm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class TeamDetailsResponse {
    @JsonProperty("team")
    private Team team;
    
    @JsonProperty("pregameForm")
    private PregameForm pregameForm;

    private TeamDetailsResponse() {
    }

    public static TeamDetailsResponse from(Team team) {
        TeamDetailsResponse response = new TeamDetailsResponse();
        response.team = team;
        return response;
    }

    public Team getTeam() {
        return team;
    }

    public PregameForm getPregameForm() {
        return pregameForm;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setPregameForm(PregameForm pregameForm) {
        this.pregameForm = pregameForm;
    }
}