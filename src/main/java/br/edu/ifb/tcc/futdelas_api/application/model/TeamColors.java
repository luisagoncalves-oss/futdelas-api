package br.edu.ifb.tcc.futdelas_api.application.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class TeamColors {
    private String primary;
    private String secondary;
    private String text;
}