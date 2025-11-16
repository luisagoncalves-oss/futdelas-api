package br.edu.ifb.tcc.futdelas_api.model;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class UniqueTournament {
    private String name;
    private TitleHolder titleHolder;
    private OffsetDateTime startDateTimestamp;
    private OffsetDateTime endDateTimestamp;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public TitleHolder getTitleHolder() {
        return titleHolder;
    }
    public void setTitleHolder(TitleHolder titleHolder) {
        this.titleHolder = titleHolder;
    }
    public OffsetDateTime getStartDateTimestamp() {
        return startDateTimestamp;
    }
    public void setStartDateTimestamp(OffsetDateTime startDateTimestamp) {
        this.startDateTimestamp = startDateTimestamp;
    }
    public OffsetDateTime getEndDateTimestamp() {
        return endDateTimestamp;
    }
    public void setEndDateTimestamp(OffsetDateTime endDateTimestamp) {
        this.endDateTimestamp = endDateTimestamp;
    }
}
