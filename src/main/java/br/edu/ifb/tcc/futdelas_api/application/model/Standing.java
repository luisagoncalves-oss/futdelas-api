package br.edu.ifb.tcc.futdelas_api.application.model;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Standing {
    private Long id;
    private String type;
    private String name;
    @JsonProperty("rows")
    private List<TeamPerformance> teamsPerformance;
    private OffsetDateTime updatedAtTimestamp;
}
