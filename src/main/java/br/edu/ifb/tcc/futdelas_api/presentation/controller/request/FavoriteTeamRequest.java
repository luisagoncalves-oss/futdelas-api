package br.edu.ifb.tcc.futdelas_api.presentation.controller.request;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FavoriteTeamRequest {
    private UUID teamId;
    private String anonymousUserId;
}
