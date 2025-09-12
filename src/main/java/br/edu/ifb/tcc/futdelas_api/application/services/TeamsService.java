package br.edu.ifb.tcc.futdelas_api.application.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifb.tcc.futdelas_api.application.model.Team;
import br.edu.ifb.tcc.futdelas_api.application.repositories.TeamRepository;
import br.edu.ifb.tcc.futdelas_api.infra.external.client.SofaScoreClient;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TeamDetailsResponse;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.TeamNextMatchesResponse;

@Service
public class TeamsService {
    private static final Logger log = LoggerFactory.getLogger(TeamsService.class);

    private final SofaScoreClient sofascoreClient;
    private final TeamRepository teamRepository;

    public TeamsService(SofaScoreClient sofascoreClient, TeamRepository teamRepository) {
        this.sofascoreClient = sofascoreClient;
        this.teamRepository = teamRepository;
    }

    public CompletableFuture<TeamDetailsResponse> searchTeamDetails(Long teamId) {
        log.info("Buscando detalhes do time");

        return sofascoreClient.getTeamDetailsAsync(teamId)
                .whenComplete((response, throwable) -> {
                    if (throwable != null) {
                        log.error("Erro ao buscar time: {}", throwable.getMessage());
                    } else {
                        log.info("Detalhes do time obtidos com sucesso");
                        log.debug("Resposta: {}", response);
                    }
                });
    }

    public CompletableFuture<byte[]> searchTeamLogo(Long teamId) {
        log.info("Buscando logo do time");

        return sofascoreClient.getTeamLogoAsync(teamId)
                .handle((response, throwable) -> {
                    if (throwable != null) {
                        log.error("Erro ao buscar logo do time: {}", throwable.getMessage());
                        return null;
                    } else {
                        log.info("Logo do time obtida com sucesso");
                        return response;
                    }
                });
    }

    public CompletableFuture<TeamNextMatchesResponse> searchTeamNextMatches(Long teamId, Integer pageIndex) {
        log.info("Buscando próximas partidas do time");

        return sofascoreClient.getTeamNextMatchesAsync(teamId, pageIndex)
                .whenComplete((response, throwable) -> {
                    if (throwable != null) {
                        log.error("Erro ao buscar próximas partidas do time: {}", throwable.getMessage());
                    } else {
                        log.info("Próximas partidas do time obtidas com sucesso");
                        log.debug("Resposta: {}", response);
                    }
                });
    }

    public List<Team> findAllTeams() {
        try {
            List<Team> listOfTeams = teamRepository.findAll();
            log.info("Times recuperados com sucesso. Total: {}", listOfTeams.size());
            return listOfTeams;
        } catch (DataAccessException ex) {
            log.warn("Erro de acesso aos dados ao buscar times, retornando lista vazia: {}", ex.getMessage());
            return Collections.emptyList();
        } catch (Exception ex) {
            log.error("Erro inesperado ao listar times, retornando lista vazia: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    @Transactional
    public void syncTeamsFromAPI(List<Team> teamsFromAPI) {
        log.info("Sincronizando {} times da API externa", teamsFromAPI.size());

        teamsFromAPI.forEach(this::upsertTeam);

        log.info("Sincronização concluída com sucesso");
    }

    private void upsertTeam(Team team) {
        Optional<Team> existing = teamRepository.findByName(team.getName());
        if (existing.isPresent()) {
            Team dbTeam = existing.get();
            dbTeam.setId(team.getId());
            dbTeam.setTeamIdApi(team.getTeamIdApi());
            dbTeam.setName(team.getName());
            dbTeam.setNameCode(team.getNameCode());
            dbTeam.setTeamColors(team.getTeamColors());
            dbTeam.setManager(team.getManager());
            teamRepository.save(dbTeam);
        } else {
            teamRepository.save(team);
        }
    }

}