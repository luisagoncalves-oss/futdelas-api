package br.edu.ifb.tcc.futdelas_api.application.exception;

public class FavoriteTeamAlreadyExistsException extends RuntimeException {
    public FavoriteTeamAlreadyExistsException(String message) {
        super(message);
    }
}