package br.edu.ifb.tcc.futdelas_api.presentation.controller.response;

import java.util.List;

import br.edu.ifb.tcc.futdelas_api.application.model.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TournamentLastMatchesResponse {
    private List<Event> events;
    private boolean hasNextPage;
}
