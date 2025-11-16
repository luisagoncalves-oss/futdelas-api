package br.edu.ifb.tcc.futdelas_api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.edu.ifb.tcc.futdelas_api.controller.response.TeamDetailsResponse;
import br.edu.ifb.tcc.futdelas_api.model.Team;
import br.edu.ifb.tcc.futdelas_api.services.external.client.SofaScoreClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TeamsService {
    private static final Logger logger = LoggerFactory.getLogger(TeamsService.class);

    private final SofaScoreClient sofaScoreClient;
    private final TournamentService tournamentService;

    public TeamsService(SofaScoreClient sofaScoreClient, TournamentService tournamentService) {
        this.sofaScoreClient = sofaScoreClient;
        this.tournamentService = tournamentService;
    }

    public CompletableFuture<TeamDetailsResponse> getTeamDetails(Long teamId) {
        logger.info("Buscando detalhes do time ID: {}", teamId);
        return sofaScoreClient.getTeamDetailsAsync(teamId);
    }

    public CompletableFuture<List<TeamDetailsResponse>> getAllTeams() {
        logger.info("Buscando times");
        
        return tournamentService.getTeamsFromStandings()
                .thenApply(this::convertToTeamDetailsResponses);
    }

    private List<TeamDetailsResponse> convertToTeamDetailsResponses(List<Team> teams) {
        return teams.stream()
                .map(TeamDetailsResponse::from)
                .toList();
    }
}