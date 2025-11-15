package br.edu.ifb.tcc.futdelas_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ifb.tcc.futdelas_api.controller.response.TeamDetailsResponse;
import br.edu.ifb.tcc.futdelas_api.services.TeamsService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/teams")
public class TeamsController {
    
    private static final String TEAM_ID_REQUIRED = "ID do time é obrigatório e deve ser maior que zero";
    private static final long MIN_TEAM_ID = 1L;
    
    private final TeamsService teamsService;

    public TeamsController(TeamsService teamsService) {
        this.teamsService = teamsService;
    }

    @GetMapping(value = "/{teamId}/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<TeamDetailsResponse> getTeamDetails(@PathVariable Long teamId) {
        validateTeamId(teamId);
        return teamsService.getTeamDetails(teamId);
    }
    

    private void validateTeamId(Long teamId) {
        if (teamId == null || teamId < MIN_TEAM_ID) {
            throw new IllegalArgumentException(TEAM_ID_REQUIRED);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
