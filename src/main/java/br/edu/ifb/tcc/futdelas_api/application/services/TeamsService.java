package br.edu.ifb.tcc.futdelas_api.application.services;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.edu.ifb.tcc.futdelas_api.infra.external.client.SofaScoreClient;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TeamDetailsResponse;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TeamNextMatchesResponse;

@Service
public class TeamsService {
    private static final Logger log = LoggerFactory.getLogger(TeamsService.class);
    
    private final SofaScoreClient sofascoreClient;

    public TeamsService(SofaScoreClient sofascoreClient) {
        this.sofascoreClient = sofascoreClient;
    }

    public CompletableFuture<TeamDetailsResponse> searchTeamDetails(Long teamId) {
        log.info("Buscando detalhes do time");
        
        return sofascoreClient.getTeamDetailsAsync(teamId)
            .whenComplete((response, throwable) -> {
                if (throwable != null) {
                    log.error("Erro ao buscar time: {}", throwable.getMessage());
                } else {
                    log.info("Detalhes do time obtidos com sucesso");
                    log.debug("Resposta: {}", response);
                }
            });
    }

    public CompletableFuture<byte[]> searchTeamLogo(Long teamId) {
        log.info("Buscando logo do time");
        
        return sofascoreClient.getTeamLogoAsync(teamId)
            .handle((response, throwable) -> {
                if (throwable != null) {
                    log.error("Erro ao buscar logo do time: {}", throwable.getMessage());
                } else {
                    log.info("Logo do time obtida com sucesso");
                    return response;
                }
            });
    }

    public CompletableFuture<TeamNextMatchesResponse> searchTeamNextMatches(Long teamId) {
        log.info("Buscando próximas partidas do time");
        
        return sofascoreClient.getTeamNextMatchesAsync(teamId)
            .whenComplete((response, throwable) -> {
                if (throwable != null) {
                    log.error("Erro ao buscar próximas partidas do time: {}", throwable.getMessage());
                } else {
                    log.info("Próximas partidas do time obtidas com sucesso");
                    log.debug("Resposta: {}", response);
                }
            });
    }
}
