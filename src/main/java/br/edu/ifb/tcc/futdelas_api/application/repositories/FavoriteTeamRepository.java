package br.edu.ifb.tcc.futdelas_api.application.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifb.tcc.futdelas_api.application.model.FavoriteTeam;

@Repository
public interface FavoriteTeamRepository extends JpaRepository<FavoriteTeam, Long> {
    boolean existsByAnonymousUserId(String anonymousUserId);
    Optional<FavoriteTeam> findByAnonymousUserId(String anonymousUserId);
    void deleteById(UUID id);
}
