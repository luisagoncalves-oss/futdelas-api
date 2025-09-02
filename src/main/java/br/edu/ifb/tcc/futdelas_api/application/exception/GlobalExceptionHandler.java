package br.edu.ifb.tcc.futdelas_api.application.exception;

import br.edu.ifb.tcc.futdelas_api.presentation.controller.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FavoriteTeamAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleFavoriteTeamAlreadyExists(FavoriteTeamAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
                "FAVORITE_TEAM_ALREADY_EXISTS",
                ex.getMessage(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(FavoriteTeamNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFavoriteTeamNotFound(FavoriteTeamNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                "FAVORITE_TEAM_NOT_FOUND",
                ex.getMessage(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "Ocorreu um erro interno no servidor",
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}