package br.edu.ifb.tcc.futdelas_api.application.model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class UniqueTournament {
    private String name;
    private TitleHolder titleHolder;
    private OffsetDateTime startDateTimestamp;
    private OffsetDateTime endDateTimestamp;
}
