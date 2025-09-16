package br.edu.ifb.tcc.futdelas_api.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifb.tcc.futdelas_api.application.services.FavoriteTeamService;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.request.FavoriteTeamRequest;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.FavoriteTeamResponse;

@RestController
@RequestMapping("/api/favorite-team")
public class FavoriteTeamController {
    
    private final FavoriteTeamService favoriteTeamService;

    public FavoriteTeamController(FavoriteTeamService favoriteTeamService) {
        this.favoriteTeamService = favoriteTeamService;
    }

    @PostMapping
    public ResponseEntity<FavoriteTeamResponse> createFavoriteTeam(@RequestBody FavoriteTeamRequest request) {
        FavoriteTeamResponse response = favoriteTeamService.createFavoriteTeam(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteFavoriteTeam(@PathVariable String userId) {
        favoriteTeamService.deleteFavoriteTeam(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{userId}")
    public ResponseEntity<Boolean> favoriteTeamExists(@PathVariable String userId){
        Boolean response = favoriteTeamService.verifyIfFavoriteTeamExists(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}