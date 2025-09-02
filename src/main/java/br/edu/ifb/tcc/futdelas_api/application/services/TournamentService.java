package br.edu.ifb.tcc.futdelas_api.application.services;

import java.util.concurrent.CompletableFuture;

import br.edu.ifb.tcc.futdelas_api.infra.external.client.SofaScoreClient;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TournamentDetailResponse;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TournamentLastMatchesResponse;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TournamentStandingsResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TournamentService {
    private static final Logger log = LoggerFactory.getLogger(TournamentService.class);
    
    private final SofaScoreClient sofascoreClient;

    public TournamentService(SofaScoreClient sofascoreClient) {
        this.sofascoreClient = sofascoreClient;
    }

    public CompletableFuture<TournamentDetailResponse> searchTournamentDetails() {
        log.info("Buscando detalhes do torneio");
        
        return sofascoreClient.getTournamentDetailsAsync()
            .whenComplete((response, throwable) -> {
                if (throwable != null) {
                    log.error("Erro ao buscar torneio: {}", throwable.getMessage());
                } else {
                    log.info("Detalhes do torneio obtidos com sucesso");
                    log.debug("Resposta: {}", response);
                }
            });
    }

    public CompletableFuture<String> searchTournamentLogo() {
        log.info("Buscando logo do torneio");
        
        return sofascoreClient.getTournamentLogoAsync()
            .whenComplete((response, throwable) -> {
                if (throwable != null) {
                    log.error("Erro ao buscar torneio: {}", throwable.getMessage());
                } else {
                    log.info("Logo do torneio obtida com sucesso");
                    log.debug("Resposta: {}", response);
                }
            });
    }

    public CompletableFuture<TournamentStandingsResponse> searchTournamentStandings() {
        log.info("Buscando classificação do torneio");
        
        return sofascoreClient.getTournamentStandingsAsync()
            .whenComplete((response, throwable) -> {
                if (throwable != null) {
                    log.error("Erro ao buscar classificação do torneio: {}", throwable.getMessage());
                } else {
                    log.info("Classificação do torneio obtida com sucesso");
                    log.debug("Resposta: {}", response);
                }
            });
    }

    public CompletableFuture<TournamentLastMatchesResponse> searchTournamentLastMatches(Integer pageIndex) {
        log.info("Buscando partidas do torneio");
        
        return sofascoreClient.getTournamentLastMatchesAsync(pageIndex)
            .whenComplete((response, throwable) -> {
                if (throwable != null) {
                    log.error("Erro ao buscar partidas do torneio: {}", throwable.getMessage());
                } else {
                    log.info("Partidas do torneio obtidas com sucesso");
                    log.debug("Resposta: {}", response);
                }
            });
    }
}