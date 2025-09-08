package br.edu.ifb.tcc.futdelas_api.presentation.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import br.edu.ifb.tcc.futdelas_api.application.services.TournamentService;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TournamentDetailResponse;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TournamentLastMatchesResponse;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TournamentStandingsResponse;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {
    
    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<TournamentDetailResponse> getTournamentDetails() {
        return tournamentService.searchTournamentDetails();
    }

    @CrossOrigin
    @GetMapping(value = "/logo", produces = MediaType.IMAGE_PNG_VALUE)
    public CompletableFuture<byte[]> getTournamentLogo() {
        return tournamentService.searchTournamentLogo();
    }

    @GetMapping(value = "/standings", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<TournamentStandingsResponse> getTournamentStandings() {
        return tournamentService.searchTournamentStandings();
    }

    @GetMapping(value = "/last-matches", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<TournamentLastMatchesResponse> getTournamentLastMatches(Integer pageIndex) {
        return tournamentService.searchTournamentLastMatches(pageIndex);
    }
}
