package br.edu.ifb.tcc.futdelas_api.application.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Status {
    private Integer code;
    private String description;
    private String type;
}
