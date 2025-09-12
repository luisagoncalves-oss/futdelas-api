package br.edu.ifb.tcc.futdelas_api.application.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "teams")
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="team_id")
    private Long teamId;

    @Column(name = "id_api")
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(name = "name_code")
    private String nameCode;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "primary", column = @Column(name = "primary_color", length = 20)),
        @AttributeOverride(name = "secondary", column = @Column(name = "secondary_color", length = 20)),
        @AttributeOverride(name = "text", column = @Column(name = "text_color", length = 20))
    })
    private TeamColors teamColors;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "manager_id")),
        @AttributeOverride(name = "name", column = @Column(name = "manager_name", length = 100))
    })
    private Manager manager;
}