package br.edu.ifb.tcc.futdelas_api.application.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favorite_teams", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"anonymous_user_id"}))
public class FavoriteTeam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "anonymous_user_id", nullable = false, unique = true)
    private String anonymousUserId;
    
    @Column(name = "team_id", nullable = false)
    private Long teamId;
    
    @Column(name = "is_favorite")
    private boolean isFavorite;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        isFavorite = true;
    }
}