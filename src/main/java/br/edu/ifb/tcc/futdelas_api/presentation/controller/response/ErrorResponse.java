package br.edu.ifb.tcc.futdelas_api.presentation.controller.response;

import java.time.LocalDateTime;

public record ErrorResponse(
    String code,
    String message,
    LocalDateTime timestamp
) {}
