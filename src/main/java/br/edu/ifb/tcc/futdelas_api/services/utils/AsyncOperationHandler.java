package br.edu.ifb.tcc.futdelas_api.services.utils;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;

public class AsyncOperationHandler {
    private final Logger logger;

    public AsyncOperationHandler(Logger logger) {
        this.logger = logger;
    }

    public <T> CompletableFuture<T> executeAsyncOperation(CompletableFuture<T> future, String operation) {
        logger.info("Buscando {}", operation);

        return future.whenComplete((response, throwable) -> {
            if (throwable != null) {
                logger.error("Erro ao buscar {}: {}", operation, throwable.getMessage());
            } else {
                logger.info("{} obtidos com sucesso", operation);
                logger.debug("Resposta: {}", response);
            }
        });
    }
}
