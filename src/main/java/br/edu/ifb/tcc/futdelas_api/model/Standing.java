package br.edu.ifb.tcc.futdelas_api.model;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class Standing {
    private Long id;
    private String type;
    private String name;
    @JsonProperty("rows")
    private List<TeamPerformance> teamsPerformance;
    private OffsetDateTime updatedAtTimestamp;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<TeamPerformance> getTeamsPerformance() {
        return teamsPerformance;
    }
    public void setTeamsPerformance(List<TeamPerformance> teamsPerformance) {
        this.teamsPerformance = teamsPerformance;
    }
    public OffsetDateTime getUpdatedAtTimestamp() {
        return updatedAtTimestamp;
    }
    public void setUpdatedAtTimestamp(OffsetDateTime updatedAtTimestamp) {
        this.updatedAtTimestamp = updatedAtTimestamp;
    }

    
}
