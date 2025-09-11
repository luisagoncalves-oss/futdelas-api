package br.edu.ifb.tcc.futdelas_api.application.services;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private final TeamsService teamsService;
    private final AtomicBoolean teamsSaved = new AtomicBoolean(false);

    public TournamentService(SofaScoreClient sofascoreClient, TeamsService teamsService) {
        this.sofascoreClient = sofascoreClient;
        this.teamsService = teamsService;
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

    public CompletableFuture<byte[]> searchTournamentLogo() {
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
                    handleSuccessfulResponse(response);
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

    private void handleSuccessfulResponse(TournamentStandingsResponse response) {
        log.info("Classificação do torneio obtida com sucesso");
        log.debug("Resposta: {}", response);
        
        saveTeamsIfFirstTime(response);
    }
    
    private void saveTeamsIfFirstTime(TournamentStandingsResponse response) {
        if (teamsSaved.compareAndSet(false, true)) {
            try {
                teamsService.saveTeamsFromStandings(response.getStandings());
                log.info("Times salvos com sucesso na base de dados (primeira vez)");
            } catch (Exception e) {
                log.warn("Erro ao salvar times na base de dados: {}", e.getMessage());
                teamsSaved.set(false);
            }
        } else {
            log.debug("Times já foram salvos anteriormente");
        }
    }
}