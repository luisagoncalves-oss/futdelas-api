package br.edu.ifb.tcc.futdelas_api.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifb.tcc.futdelas_api.application.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    
}
