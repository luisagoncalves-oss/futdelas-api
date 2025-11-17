package br.edu.ifb.tcc.futdelas_api.services.external.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifb.tcc.futdelas_api.controller.response.TeamDetailsResponse;
import br.edu.ifb.tcc.futdelas_api.controller.response.TournamentDetailResponse;
import br.edu.ifb.tcc.futdelas_api.controller.response.TournamentLastMatchesResponse;
import br.edu.ifb.tcc.futdelas_api.controller.response.TournamentStandingsResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SofaScoreClient {
    private static final Logger logger = LoggerFactory.getLogger(SofaScoreClient.class);

    private static final String TOURNAMENT_ID = "tournamentId";
    private static final String SEASON_ID = "seasonId";
    private static final String TEAM_ID = "teamId";
    private static final String PAGE_INDEX = "pageIndex";
    private static final String TYPE = "type";

    private static final int DEFAULT_TOURNAMENT_ID = 10257;
    private static final int DEFAULT_SEASON_ID = 73097;
    private static final String DEFAULT_TYPE = "total";

    private final WebClient webClient;

    public SofaScoreClient(@Qualifier("sofascoreWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public CompletableFuture<TournamentDetailResponse> getTournamentDetailsAsync() {
        return executeGetRequest("/tournaments/detail",
                uriBuilder -> uriBuilder.queryParam(TOURNAMENT_ID, DEFAULT_TOURNAMENT_ID),
                TournamentDetailResponse.class);
    }

    public CompletableFuture<TournamentStandingsResponse> getTournamentStandingsAsync() {
        logger.info("Buscando classificação do campeonato");
        return executeGetRequest("/tournaments/get-standings",
                uriBuilder -> uriBuilder
                        .queryParam(TOURNAMENT_ID, DEFAULT_TOURNAMENT_ID)
                        .queryParam(SEASON_ID, DEFAULT_SEASON_ID)
                        .queryParam(TYPE, DEFAULT_TYPE),
                TournamentStandingsResponse.class);
    }

    public CompletableFuture<TournamentLastMatchesResponse> getTournamentLastMatchesAsync(Integer pageIndex) {
        validatePageIndex(pageIndex);

        return executeGetRequest("/tournaments/get-last-matches",
                uriBuilder -> uriBuilder
                        .queryParam(TOURNAMENT_ID, DEFAULT_TOURNAMENT_ID)
                        .queryParam(SEASON_ID, DEFAULT_SEASON_ID)
                        .queryParam(PAGE_INDEX, pageIndex),
                TournamentLastMatchesResponse.class);
    }

    public CompletableFuture<TeamDetailsResponse> getTeamDetailsAsync(Long teamId) {
        validateTeamId(teamId);

        return executeGetRequest("/teams/detail",
                uriBuilder -> uriBuilder.queryParam(TEAM_ID, teamId),
                TeamDetailsResponse.class).whenComplete((response, error) -> {
                    if (error != null) {
                        logger.error("Erro ao buscar detalhes do time {}: {}", teamId, error.getMessage());
                    } else {
                        logger.info("Resposta recebida para time {}: {}", teamId, response);
                    }
                });
    }

    public CompletableFuture<byte[]> getTeamLogoAsync(Long teamId) {
        validateTeamId(teamId);

        return executeGetRequest("/teams/get-logo",
                uriBuilder -> uriBuilder.queryParam(TEAM_ID, teamId),
                byte[].class);
    }

    private <T> CompletableFuture<T> executeGetRequest(String path,
            Function<UriBuilder, UriBuilder> queryParamCustomizer,
            Class<T> responseType) {
        return webClient.get()
                .uri(uriBuilder -> queryParamCustomizer.apply(uriBuilder.path(path)).build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(jsonString -> {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        T result = mapper.readValue(jsonString, responseType);
                        return Mono.just(result);
                    } catch (Exception e) {
                        logger.error("Erro ao fazer parse do JSON", e);
                        return Mono.error(e);
                    }
                })
                .toFuture();
    }

    private void validateTeamId(Long teamId) {
        if (teamId == null || teamId <= 0) {
            throw new IllegalArgumentException("ID do time deve ser um valor positivo");
        }
    }

    private void validatePageIndex(Integer pageIndex) {
        if (pageIndex == null || pageIndex < 0) {
            throw new IllegalArgumentException("Page index não pode ser negativo");
        }
    }
}