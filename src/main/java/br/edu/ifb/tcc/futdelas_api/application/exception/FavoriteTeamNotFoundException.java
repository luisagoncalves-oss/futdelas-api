package br.edu.ifb.tcc.futdelas_api.application.exception;

public class FavoriteTeamNotFoundException extends RuntimeException {
    public FavoriteTeamNotFoundException(String message) {
        super(message);
    }
}
