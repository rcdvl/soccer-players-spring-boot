package com.renan.soccerplayers.repository;

import com.renan.soccerplayers.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
