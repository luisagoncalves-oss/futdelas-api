package br.edu.ifb.tcc.futdelas_api.application.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "team_performance")
public class TeamPerformance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    
    private Integer position;
    private Integer matches;
    private Integer wins;
    
    @Column(name = "scores_for")
    private Integer scoresFor;
    
    @Column(name = "scores_against")
    private Integer scoresAgainst;
    
    private Integer losses;
    private Integer draws;
    private Integer points;
}