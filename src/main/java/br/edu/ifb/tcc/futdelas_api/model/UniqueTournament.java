package br.edu.ifb.tcc.futdelas_api.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class UniqueTournament {
    private String name;
    private TitleHolder titleHolder;
    private LocalDateTime startDateTimestamp;
    private LocalDateTime endDateTimestamp;

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
    public LocalDateTime getStartDateTimestamp() {
        return startDateTimestamp;
    }
    public void setStartDateTimestamp(LocalDateTime startDateTimestamp) {
        this.startDateTimestamp = startDateTimestamp;
    }
    public LocalDateTime getEndDateTimestamp() {
        return endDateTimestamp;
    }
    public void setEndDateTimestamp(LocalDateTime endDateTimestamp) {
        this.endDateTimestamp = endDateTimestamp;
    }
}
