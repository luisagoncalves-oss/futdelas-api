package br.edu.ifb.tcc.futdelas_api.presentation.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.ifb.tcc.futdelas_api.application.services.MatchImageService;
import br.edu.ifb.tcc.futdelas_api.presentation.controller.dto.MatchData;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/match")
public class MatchImageController {

    private final MatchImageService imageService;

    public MatchImageController(MatchImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/{matchId}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<ResponseEntity<byte[]>> generateMatchImage(@PathVariable Long matchId) {
        MatchData mock = MatchData.mock();

        return imageService.generateImage(mock)
                .map(bytes -> ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(bytes));
    }
}
