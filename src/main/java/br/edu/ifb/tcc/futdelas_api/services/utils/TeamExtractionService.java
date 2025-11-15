package br.edu.ifb.tcc.futdelas_api.services.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.edu.ifb.tcc.futdelas_api.model.Standing;
import br.edu.ifb.tcc.futdelas_api.model.Team;
import br.edu.ifb.tcc.futdelas_api.model.TeamPerformance;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TeamExtractionService {
    private static final Logger logger = LoggerFactory.getLogger(TeamExtractionService.class);

    public List<Team> extractTeamsFromStandings(List<Standing> standings) {
        if (standings == null || standings.isEmpty()) {
            logger.warn("Nenhum standing fornecido para extrair times");
            return Collections.emptyList();
        }

        List<Team> teams = standings.stream()
                .filter(Objects::nonNull)
                .flatMap(this::extractTeamsFromStanding)
                .filter(this::isValidTeam)
                .distinct()
                .collect(Collectors.toList());

        logger.debug("Extraídos {} times únicos dos standings", teams.size());
        return teams;
    }

    private Stream<Team> extractTeamsFromStanding(Standing standing) {
        if (standing.getTeamsPerformance() == null) {
            return Stream.empty();
        }
        
        return standing.getTeamsPerformance().stream()
                .filter(Objects::nonNull)
                .map(TeamPerformance::getTeam)
                .filter(Objects::nonNull);
    }

    private boolean isValidTeam(Team team) {
        return team != null && team.getName() != null && !team.getName().isBlank();
    }
}