package br.edu.ifb.tcc.futdelas_api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.edu.ifb.tcc.futdelas_api.controller.response.TournamentDetailResponse;
import br.edu.ifb.tcc.futdelas_api.controller.response.TournamentLastMatchesResponse;
import br.edu.ifb.tcc.futdelas_api.controller.response.TournamentStandingsResponse;
import br.edu.ifb.tcc.futdelas_api.model.Team;
import br.edu.ifb.tcc.futdelas_api.services.external.client.SofaScoreClient;
import br.edu.ifb.tcc.futdelas_api.services.utils.TeamExtractionService;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TournamentService {
    private static final Logger logger = LoggerFactory.getLogger(TournamentService.class);

    private final SofaScoreClient sofaScoreClient;
    private final TeamExtractionService teamExtractionService;

    public TournamentService(SofaScoreClient sofaScoreClient, 
                           TeamExtractionService teamExtractionService) {
        this.sofaScoreClient = sofaScoreClient;
        this.teamExtractionService = teamExtractionService;
    }

    public CompletableFuture<TournamentDetailResponse> getTournamentDetails() {
        logger.info("Buscando detalhes do torneio");
        return sofaScoreClient.getTournamentDetailsAsync();
    }

    public CompletableFuture<TournamentLastMatchesResponse> getTournamentLastMatches(Integer pageIndex) {
        logger.info("Buscando partidas do torneio - página: {}", pageIndex);
        return sofaScoreClient.getTournamentLastMatchesAsync(pageIndex);
    }

    public CompletableFuture<TournamentStandingsResponse> getTournamentStandings() {
        logger.info("Buscando classificação do torneio");
        return sofaScoreClient.getTournamentStandingsAsync();
    }

    public CompletableFuture<List<Team>> getTeamsFromStandings() {
        logger.info("Buscando times da classificação do torneio");
        
        return sofaScoreClient.getTournamentStandingsAsync()
                .thenApply(this::extractTeamsFromResponse)
                .exceptionally(throwable -> {
                    logger.error("Erro ao buscar times da classificação: {}", throwable.getMessage());
                    return Collections.emptyList();
                });
    }

    private List<Team> extractTeamsFromResponse(TournamentStandingsResponse response) {
        if (response == null || response.getStandings() == null) {
            logger.warn("Resposta da classificação vazia ou inválida");
            return Collections.emptyList();
        }

        List<Team> teams = teamExtractionService.extractTeamsFromStandings(response.getStandings());
        logger.info("Extraídos {} times da classificação", teams.size());
        return teams;
    }
}