package br.edu.ifb.tcc.futdelas_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class TeamColors {
    private String primary;
    private String secondary;
    private String text;

    public TeamColors() {
    }
    
    public String getPrimary() {
        return primary;
    }
    public void setPrimary(String primary) {
        this.primary = primary;
    }
    public String getSecondary() {
        return secondary;
    }
    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}