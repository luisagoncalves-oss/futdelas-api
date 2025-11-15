package br.edu.ifb.tcc.futdelas_api.services.external.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import br.edu.ifb.tcc.futdelas_api.controller.response.TeamDetailsResponse;
import br.edu.ifb.tcc.futdelas_api.controller.response.TournamentDetailResponse;
import br.edu.ifb.tcc.futdelas_api.controller.response.TournamentLastMatchesResponse;
import br.edu.ifb.tcc.futdelas_api.controller.response.TournamentStandingsResponse;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Component
public class SofaScoreClient {
    private static final String TOURNAMENT_ID = "tournamentId";
    private static final String SEASON_ID = "seasonId";
    private static final String TEAM_ID = "teamId";
    private static final String PAGE_INDEX = "pageIndex";
    private static final String TYPE = "type";
    
    private static final int DEFAULT_TOURNAMENT_ID = 10257;
    private static final int DEFAULT_SEASON_ID = 73097;
    private static final String DEFAULT_TYPE = "total";
    
    private final WebClient webClient;

    public SofaScoreClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public CompletableFuture<TournamentDetailResponse> getTournamentDetailsAsync() {
        return executeGetRequest("/tournaments/detail", 
            uriBuilder -> uriBuilder.queryParam(TOURNAMENT_ID, DEFAULT_TOURNAMENT_ID),
            TournamentDetailResponse.class
        );
    }

    public CompletableFuture<TournamentStandingsResponse> getTournamentStandingsAsync() {
        return executeGetRequest("/tournaments/get-standings",
            uriBuilder -> uriBuilder
                .queryParam(TOURNAMENT_ID, DEFAULT_TOURNAMENT_ID)
                .queryParam(SEASON_ID, DEFAULT_SEASON_ID)
                .queryParam(TYPE, DEFAULT_TYPE),
            TournamentStandingsResponse.class
        );
    }

    public CompletableFuture<TournamentLastMatchesResponse> getTournamentLastMatchesAsync(Integer pageIndex) {
        validatePageIndex(pageIndex);
        
        return executeGetRequest("/tournaments/get-last-matches",
            uriBuilder -> uriBuilder
                .queryParam(TOURNAMENT_ID, DEFAULT_TOURNAMENT_ID)
                .queryParam(SEASON_ID, DEFAULT_SEASON_ID)
                .queryParam(PAGE_INDEX, pageIndex),
            TournamentLastMatchesResponse.class
        );
    }

    public CompletableFuture<TeamDetailsResponse> getTeamDetailsAsync(Long teamId) {
        validateTeamId(teamId);
        
        return executeGetRequest("/teams/detail",
            uriBuilder -> uriBuilder.queryParam(TEAM_ID, teamId),
            TeamDetailsResponse.class
        );
    }

    public CompletableFuture<byte[]> getTeamLogoAsync(Long teamId) {
        validateTeamId(teamId);
        
        return executeGetRequest("/teams/get-logo",
            uriBuilder -> uriBuilder.queryParam(TEAM_ID, teamId),
            byte[].class
        );
    }

    private <T> CompletableFuture<T> executeGetRequest(String path, 
                                                     Function<UriBuilder, UriBuilder> queryParamCustomizer,
                                                     Class<T> responseType) {
        return webClient.get()
            .uri(uriBuilder -> queryParamCustomizer.apply(uriBuilder.path(path)).build())
            .retrieve()
            .bodyToMono(responseType)
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