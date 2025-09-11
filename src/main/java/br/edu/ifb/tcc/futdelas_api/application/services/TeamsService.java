package br.edu.ifb.tcc.futdelas_api.application.services;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifb.tcc.futdelas_api.application.model.Standing;
import br.edu.ifb.tcc.futdelas_api.application.model.Team;
import br.edu.ifb.tcc.futdelas_api.application.model.TeamPerformance;
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
    public void saveTeamsFromStandings(List<Standing> standings) {
        if (standings == null || standings.isEmpty()) {
            log.warn("Nenhum standing fornecido para salvar times");
            return;
        }

        List<Team> teamsToProcess = extractUniqueTeamsFromStandings(standings);
        
        if (teamsToProcess.isEmpty()) {
            log.info("Nenhum team válido encontrado nos standings");
            return;
        }

        log.info("Processando {} times para salvamento", teamsToProcess.size());
        
        List<Long> teamIds = teamsToProcess.stream()
                .map(Team::getId)
                .collect(Collectors.toList());
                
        List<Team> existingTeams = teamRepository.findAllById(teamIds);
        
        teamsToProcess.forEach(team -> processTeam(team, existingTeams));
        
        log.info("Processamento de times concluído");
    }
    
    private void processTeam(Team newTeam, List<Team> existingTeams) {
        try {
            existingTeams.stream()
                .filter(existing -> existing.getId().equals(newTeam.getId()))
                .findFirst()
                .ifPresentOrElse(
                    existing -> updateTeamIfNeeded(existing, newTeam),
                    () -> saveNewTeam(newTeam)
                );
        } catch (Exception e) {
            log.warn("Erro ao processar team {} ({}): {}", 
                    newTeam.getId(), newTeam.getName(), e.getMessage());
        }
    }
    
    private void updateTeamIfNeeded(Team existingTeam, Team newTeam) {
        if (hasTeamChanged(existingTeam, newTeam)) {
            updateTeamData(existingTeam, newTeam);
            teamRepository.save(existingTeam);
            log.debug("Team atualizado: {} - {}", existingTeam.getId(), existingTeam.getName());
        }
    }
    
    private void saveNewTeam(Team team) {
        teamRepository.save(team);
        log.debug("Novo team salvo: {} - {}", team.getId(), team.getName());
    }
    
    private boolean hasTeamChanged(Team existing, Team newTeam) {
        return !Objects.equals(existing.getName(), newTeam.getName()) ||
               !Objects.equals(existing.getNameCode(), newTeam.getNameCode()) ||
               !Objects.equals(existing.getTeamColors(), newTeam.getTeamColors()) ||
               !Objects.equals(existing.getManager(), newTeam.getManager());
    }
    
    private void updateTeamData(Team existing, Team newTeam) {
        existing.setName(newTeam.getName());
        existing.setNameCode(newTeam.getNameCode());
        existing.setTeamColors(newTeam.getTeamColors());
        existing.setManager(newTeam.getManager());
    }

    private List<Team> extractUniqueTeamsFromStandings(List<Standing> standings) {
        return standings.stream()
                .filter(Objects::nonNull)
                .flatMap(standing -> standing.getTeamsPerformance().stream())
                .filter(Objects::nonNull)
                .map(TeamPerformance::getTeam)
                .filter(team -> team != null && team.getId() != null)
                .distinct()
                .collect(Collectors.toList());
    }
}