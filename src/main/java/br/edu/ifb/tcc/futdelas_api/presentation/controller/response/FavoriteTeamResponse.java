package br.edu.ifb.tcc.futdelas_api.presentation.controller.response;

import java.util.UUID;

import br.edu.ifb.tcc.futdelas_api.application.model.FavoriteTeam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteTeamResponse {
    private UUID favoriteTeamId;
    private Long teamId;
    private boolean isFavorite;

    public static FavoriteTeamResponse from(FavoriteTeam savedFavoriteTeam) {
        FavoriteTeamResponse response = new FavoriteTeamResponse();
        
        response.setFavoriteTeamId(savedFavoriteTeam.getFavoriteTeamId());
        response.setTeamId(savedFavoriteTeam.getTeamId());
        response.setFavorite(true);
        
        return response;
    }
}
