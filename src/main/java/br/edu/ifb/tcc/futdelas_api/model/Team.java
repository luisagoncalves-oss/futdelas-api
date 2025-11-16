package br.edu.ifb.tcc.futdelas_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class Team {
    private Integer id;
    private String name;
    private String nameCode;
    private TeamColors teamColors;
    private Manager manager;

    public Team() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public TeamColors getTeamColors() {
        return teamColors;
    }

    public void setTeamColors(TeamColors teamColors) {
        this.teamColors = teamColors;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}