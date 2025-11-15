package br.edu.ifb.tcc.futdelas_api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.edu.ifb.tcc.futdelas_api.controller.response.TeamDetailsResponse;
import br.edu.ifb.tcc.futdelas_api.services.external.client.SofaScoreClient;

import java.util.concurrent.CompletableFuture;

@Service
public class TeamsService {
    private static final Logger logger = LoggerFactory.getLogger(TeamsService.class);

    private final SofaScoreClient sofaScoreClient;

    public TeamsService(SofaScoreClient sofaScoreClient) {
        this.sofaScoreClient = sofaScoreClient;
    }

    public CompletableFuture<TeamDetailsResponse> getTeamDetails(Long teamId) {
        logger.info("Buscando detalhes do time ID: {}", teamId);
        return sofaScoreClient.getTeamDetailsAsync(teamId);
    }
}