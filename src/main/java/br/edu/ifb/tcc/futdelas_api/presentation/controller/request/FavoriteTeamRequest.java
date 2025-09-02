package br.edu.ifb.tcc.futdelas_api.presentation.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FavoriteTeamRequest {
    private Long teamId;
    private String anonymousUserId;
}
