package br.edu.ifb.tcc.futdelas_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class UniqueTournament {
    private String name;
    private TitleHolder titleHolder;
    private Long startDateTimestamp;
    private Long endDateTimestamp;

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
    public Long getStartDateTimestamp() {
        return startDateTimestamp;
    }
    public void setStartDateTimestamp(Long startDateTimestamp) {
        this.startDateTimestamp = startDateTimestamp;
    }
    public Long getEndDateTimestamp() {
        return endDateTimestamp;
    }
    public void setEndDateTimestamp(Long endDateTimestamp) {
        this.endDateTimestamp = endDateTimestamp;
    }
}
