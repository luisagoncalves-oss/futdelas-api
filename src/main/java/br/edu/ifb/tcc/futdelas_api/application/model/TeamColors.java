package br.edu.ifb.tcc.futdelas_api.application.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "team_colors")
@NoArgsConstructor
public class TeamColors {
    
    @Column(name = "primary_color", length = 20)
    private String primary;
    
    @Column(name = "secondary_color", length = 20)
    private String secondary;
    
    @Column(name = "text_color", length = 20)
    private String text;
}