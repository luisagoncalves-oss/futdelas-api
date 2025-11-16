package br.edu.ifb.tcc.futdelas_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class TitleHolder {
    private Long id;
    private String name;
    private String nameCode;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
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
}
