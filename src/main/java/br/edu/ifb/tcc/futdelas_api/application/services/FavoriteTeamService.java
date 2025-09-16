package br.edu.ifb.tcc.futdelas_api.application.services;

import org.springframework.stereotype.Service;

import br.edu.ifb.tcc.futdelas_api.application.exception.FavoriteTeamAlreadyExistsException;
import br.edu.ifb.tcc.futdelas_api.application.exception.FavoriteTeamNotFoundException;
import br.edu.ifb.tcc.futdelas_api.application.model.FavoriteTeam;
import br.edu.ifb.tcc.futdelas_api.application.repositories.FavoriteTeamRepository;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.request.FavoriteTeamRequest;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.FavoriteTeamResponse;
import jakarta.transaction.Transactional;

@Service
public class FavoriteTeamService {
    private final FavoriteTeamRepository repository;

    public FavoriteTeamService(FavoriteTeamRepository repository) {
        this.repository = repository;
    }

    public FavoriteTeamResponse createFavoriteTeam(FavoriteTeamRequest request) {
        validateFavoriteTeamNotExists(request.getAnonymousUserId());

        FavoriteTeam favoriteTeam = createFavoriteTeamEntity(request);
        FavoriteTeam savedFavoriteTeam = repository.save(favoriteTeam);

        return FavoriteTeamResponse.from(savedFavoriteTeam);
    }

    private void validateFavoriteTeamNotExists(String anonymousUserId) {
        if (repository.existsByAnonymousUserId(anonymousUserId)) {
            throw new FavoriteTeamAlreadyExistsException(
                    "Usuário já possui um time favorito cadastrado. User ID: " + anonymousUserId);
        }
    }

    private FavoriteTeam createFavoriteTeamEntity(FavoriteTeamRequest request) {
        return FavoriteTeam.builder()
                .anonymousUserId(request.getAnonymousUserId())
                .teamId(request.getTeamId())
                .build();
    }

    @Transactional
    public void deleteFavoriteTeam(String userId) {
        FavoriteTeam favoriteTeam = getFavoriteTeamOrThrow(userId);
        repository.delete(favoriteTeam);
    }

    private FavoriteTeam getFavoriteTeamOrThrow(String userId) {
        return repository.findByAnonymousUserId(userId)
                .orElseThrow(() -> new FavoriteTeamNotFoundException(
                        "Time favorito não encontrado para o usuário: " + userId));
    }

    public FavoriteTeamResponse getFavoriteTeam(String userId) {
        FavoriteTeam favoriteTeam = getFavoriteTeamOrThrow(userId);
        return FavoriteTeamResponse.from(favoriteTeam);
    }

    public boolean verifyIfFavoriteTeamExists(String anonymousUserId) {
        return repository.existsByAnonymousUserId(anonymousUserId);
    }
}
