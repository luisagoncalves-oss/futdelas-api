package br.edu.ifb.tcc.futdelas_api.controller.response;

import java.util.List;

import br.edu.ifb.tcc.futdelas_api.model.Event;

public class TournamentLastMatchesResponse {
    private List<Event> events;
    private boolean hasNextPage;
    
    public List<Event> getEvents() {
        return events;
    }
    public boolean isHasNextPage() {
        return hasNextPage;
    }
}
