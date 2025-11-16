package br.edu.ifb.tcc.futdelas_api.controller.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.edu.ifb.tcc.futdelas_api.model.Event;

@JsonIgnoreProperties(ignoreUnknown = true) 
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
