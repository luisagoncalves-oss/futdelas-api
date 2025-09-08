package br.edu.ifb.tcc.futdelas_api.infra.external.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TeamDetailsResponse;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TeamNextMatchesResponse;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TournamentDetailResponse;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TournamentLastMatchesResponse;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TournamentStandingsResponse;

import java.util.concurrent.CompletableFuture;

@Component
public class SofaScoreClient {

    private final WebClient sofascoreWebClient;

    public SofaScoreClient(WebClient sofascoreWebClient) {
        this.sofascoreWebClient = sofascoreWebClient;
    }

    public CompletableFuture<TournamentDetailResponse> getTournamentDetailsAsync() {
        return sofascoreWebClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/tournaments/detail")
                .queryParam("tournamentId", 10257)
                .build())
            .retrieve()
            .bodyToMono(TournamentDetailResponse.class)
            .toFuture();
    }

    public CompletableFuture<byte[]> getTournamentLogoAsync() {
        return sofascoreWebClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/tournaments/get-logo")
                .queryParam("tournamentId", 10257)
                .build())
            .retrieve()
            .bodyToMono(byte[].class)
            .toFuture();
    }

    public CompletableFuture<TournamentStandingsResponse> getTournamentStandingsAsync(){
        return sofascoreWebClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/tournaments/get-standings")
                .queryParam("tournamentId", 10257)
                .queryParam("seasonId", 73097)
                .queryParam("type", "total")
                .build())
            .retrieve()
            .bodyToMono(TournamentStandingsResponse.class)
            .toFuture();
    }

    public CompletableFuture<TournamentLastMatchesResponse> getTournamentLastMatchesAsync(Integer pageIndex){
        return sofascoreWebClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/tournaments/get-last-matches")
                .queryParam("tournamentId", 10257)
                .queryParam("seasonId", 73097)
                .queryParam("pageIndex", pageIndex)
                .build())
            .retrieve()
            .bodyToMono(TournamentLastMatchesResponse.class)
            .toFuture();
    }

    public CompletableFuture<TeamNextMatchesResponse> getTeamNextMatchesAsync(Long teamId) {
        return sofascoreWebClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/teams/get-next-matches")
                .queryParam("teamId", teamId)
                .queryParam("pageIndex", 0)
                .build())
            .retrieve()
            .bodyToMono(TeamNextMatchesResponse.class)
            .toFuture();
    }

    public CompletableFuture<byte[]> getTeamLogoAsync(Long teamId) {
        return sofascoreWebClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/teams/get-logo")
                .queryParam("teamId", teamId)
                .build())
            .headers(headers -> {
               headers.set("User-Agent", "FutdelasApp/1.0 (Football Data Client)");
               headers.set("Accept", "image/*");
            headers.set("Connection", "close");
            })
            .retrieve()
            .bodyToMono(byte[].class)
            .toFuture();
    }

    public CompletableFuture<TeamDetailsResponse> getTeamDetailsAsync(Long teamId) {
        return sofascoreWebClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/teams/detail")
                .queryParam("teamId", teamId)
                .build())
            .retrieve()
            .bodyToMono(TeamDetailsResponse.class)
            .toFuture();
    }
    
}