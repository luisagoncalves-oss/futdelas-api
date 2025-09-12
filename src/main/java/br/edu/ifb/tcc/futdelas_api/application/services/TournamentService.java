package br.edu.ifb.tcc.futdelas_api.application.services;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.edu.ifb.tcc.futdelas_api.application.model.Standing;
import br.edu.ifb.tcc.futdelas_api.application.model.Team;
import br.edu.ifb.tcc.futdelas_api.application.model.TeamPerformance;
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

    public CompletableFuture<TournamentStandingsResponse> searchTournamentStandings() {
        log.info("Buscando classificação do torneio");

        return sofascoreClient.getTournamentStandingsAsync()
                .whenComplete(this::handleResponse);
    }

    private void handleResponse(TournamentStandingsResponse response, Throwable throwable) {
        if (throwable != null) {
            log.error("Erro ao buscar classificação do torneio: {}", throwable.getMessage());
            return;
        }
        
        if (response != null && response.getStandings() != null) {
            handleSuccessfulResponse(response);
        }
    }

    private void handleSuccessfulResponse(TournamentStandingsResponse response) {
        log.info("Classificação do torneio obtida com sucesso");

        try {
            List<Team> teams = extractTeamsFromStandings(response.getStandings());
            teamsService.syncTeamsFromAPI(teams);
        } catch (Exception e) {
            log.warn("Erro ao salvar times (não crítico): {}", e.getMessage());
        }
    }

    private List<Team> extractTeamsFromStandings(List<Standing> standings) {
        if (standings == null || standings.isEmpty()) {
            log.warn("Nenhum standing fornecido para extrair times");
            return Collections.emptyList();
        }

        List<Team> teams = standings.stream()
                .filter(Objects::nonNull)
                .flatMap(this::extractTeamsFromStanding)
                .filter(this::isValidTeam)
                .distinct()
                .collect(Collectors.toList());

        log.info("Extraídos {} times únicos dos standings", teams.size());
        return teams;
    }

    private Stream<Team> extractTeamsFromStanding(Standing standing) {
        if (standing.getTeamsPerformance() == null) {
            return Stream.empty();
        }
        
        return standing.getTeamsPerformance().stream()
                .filter(Objects::nonNull)
                .map(TeamPerformance::getTeam)
                .filter(Objects::nonNull);
    }

    private boolean isValidTeam(Team team) {
        return team != null && team.getId() != null;
    }
}