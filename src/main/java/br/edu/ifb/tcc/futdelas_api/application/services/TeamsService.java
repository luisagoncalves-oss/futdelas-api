package br.edu.ifb.tcc.futdelas_api.application.services;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
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
    private static final int BATCH_SIZE = 5;

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

    public void saveTeamsFromStandings(List<Standing> standings) {
        if (isInvalidInput(standings)) {
            log.warn("Nenhum standing válido fornecido");
            return;
        }

        List<Team> uniqueTeams = extractUniqueTeams(standings);
        
        if (uniqueTeams.isEmpty()) {
            log.info("Nenhum team válido encontrado nos standings");
            return;
        }

        log.info("Processando {} times em lotes de {}", uniqueTeams.size(), BATCH_SIZE);
        processTeamsInBatches(uniqueTeams);
    }

    private boolean isInvalidInput(List<Standing> standings) {
        return standings == null || standings.isEmpty();
    }

    private List<Team> extractUniqueTeams(List<Standing> standings) {
        return standings.stream()
                .filter(Objects::nonNull)
                .flatMap(this::extractTeamsFromStanding)
                .filter(this::isValidTeam)
                .distinct()
                .collect(Collectors.toList());
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
        return team != null && team.getId() != null;
    }

    private void processTeamsInBatches(List<Team> teams) {
        for (int i = 0; i < teams.size(); i += BATCH_SIZE) {
            int endIndex = Math.min(i + BATCH_SIZE, teams.size());
            List<Team> batch = teams.subList(i, endIndex);
            processBatchInSeparateTransaction(batch);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, 
                   isolation = Isolation.READ_COMMITTED)
    public void processBatchInSeparateTransaction(List<Team> batch) {
        batch.forEach(this::processTeam);
    }

    private void processTeam(Team newTeam) {
        try {
            Optional<Team> existingTeamOpt = teamRepository.findById(newTeam.getId());
            
            if (existingTeamOpt.isPresent()) {
                updateExistingTeam(existingTeamOpt.get(), newTeam);
            } else {
                saveNewTeam(newTeam);
            }
        } catch (Exception e) {
            log.warn("Erro ao processar team {} ({}): {}", 
                    newTeam.getId(), newTeam.getName(), e.getMessage());
        }
    }

    private void updateExistingTeam(Team existingTeam, Team newTeam) {
        if (!hasTeamChanged(existingTeam, newTeam)) {
            return;
        }

        updateTeamFields(existingTeam, newTeam);
        teamRepository.save(existingTeam);
        log.debug("Team atualizado: {} - {}", existingTeam.getId(), existingTeam.getName());
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

    private void updateTeamFields(Team existing, Team newTeam) {
        existing.setName(newTeam.getName());
        existing.setNameCode(newTeam.getNameCode());
        existing.setTeamColors(newTeam.getTeamColors());
        existing.setManager(newTeam.getManager());
    }
}