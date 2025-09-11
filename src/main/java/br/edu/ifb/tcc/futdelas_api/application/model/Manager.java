package br.edu.ifb.tcc.futdelas_api.application.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Manager {
    private Long id;
    private String name;
}