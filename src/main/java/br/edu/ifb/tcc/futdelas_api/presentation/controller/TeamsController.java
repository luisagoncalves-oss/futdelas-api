package br.edu.ifb.tcc.futdelas_api.presentation.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifb.tcc.futdelas_api.application.services.TeamsService;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TeamDetailsResponse;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TeamNextMatchesResponse;

@RestController
@RequestMapping("/api/teams")
public class TeamsController {
    private final TeamsService teamsService;

    public TeamsController(TeamsService teamsService) {
        this.teamsService = teamsService;
    }

    @GetMapping(value = "/{teamId}/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<TeamDetailsResponse> getTeamDetails(@PathVariable("teamId") Long teamId) {
        return teamsService.searchTeamDetails(teamId);
    }

    @CrossOrigin 
    @GetMapping(value = "/{teamId}/logo", produces = MediaType.IMAGE_PNG_VALUE)
    public CompletableFuture<byte[]> getTeamLogo(@PathVariable("teamId") Long teamId) {
        return teamsService.searchTeamLogo(teamId);
    }

    @GetMapping(value = "/{teamId}/next-matches", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<TeamNextMatchesResponse> getTeamNextMatches(@PathVariable("teamId") Long teamId) {
        return teamsService.searchTeamNextMatches(teamId);
    }
}
