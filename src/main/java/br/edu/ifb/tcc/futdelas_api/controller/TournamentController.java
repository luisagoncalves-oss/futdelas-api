package br.edu.ifb.tcc.futdelas_api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import br.edu.ifb.tcc.futdelas_api.controller.response.TournamentDetailResponse;
import br.edu.ifb.tcc.futdelas_api.controller.response.TournamentLastMatchesResponse;
import br.edu.ifb.tcc.futdelas_api.controller.response.TournamentStandingsResponse;
import br.edu.ifb.tcc.futdelas_api.services.TournamentService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {
    
    private static final String DEFAULT_PAGE_INDEX = "0";
    private static final int MIN_PAGE_INDEX = 0;
    
    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<TournamentDetailResponse> getTournamentDetails() {
        return tournamentService.getTournamentDetails();
    }

    @GetMapping(value = "/standings", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<TournamentStandingsResponse> getTournamentStandings() {
        return tournamentService.getTournamentStandings();
    }

    @GetMapping(value = "/last-matches", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<TournamentLastMatchesResponse> getTournamentLastMatches(
            @RequestParam(value = "pageIndex", defaultValue = DEFAULT_PAGE_INDEX) Integer pageIndex) {
        
        Integer validatedPageIndex = validatePageIndex(pageIndex);
        return tournamentService.getTournamentLastMatches(validatedPageIndex);
    }

    private Integer validatePageIndex(Integer pageIndex) {
        if (pageIndex == null || pageIndex < MIN_PAGE_INDEX) {
            return MIN_PAGE_INDEX;
        }
        return pageIndex;
    }
}