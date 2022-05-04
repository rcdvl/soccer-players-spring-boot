package com.renan.soccerplayers.controller;

import com.renan.soccerplayers.domain.Player;
import com.renan.soccerplayers.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayersController {

    private final PlayerRepository playerRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid Player player) {
        playerRepository.save(player);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object[]> index(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                      @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Page<Player> players = playerRepository.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(players.stream().toArray());
    }
}
