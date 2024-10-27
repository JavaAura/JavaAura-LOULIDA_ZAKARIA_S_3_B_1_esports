package org.esport.controller;

import org.esport.model.Player;
import org.esport.service.interfaces.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class PlayerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    public Player registerPlayer(String username, int age) {
        LOGGER.info("Attempting to register a new player: {}", username);
        Player player = new Player();
        player.setUsername(username);
        player.setAge(age);
        return playerService.registerPlayer(player);
    }

    public Player updatePlayer(Long id, String newUsername, int newAge) {
        LOGGER.info("Attempting to update player with ID: {}", id);
        Optional<Player> playerOptional = playerService.getPlayer(id);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            player.setUsername(newUsername);
            player.setAge(newAge);
            return playerService.updatePlayer(player);
        } else {
            LOGGER.warn("Player with ID {} not found", id);
            return null;
        }
    }

    public void deletePlayer(Long id) {
        LOGGER.info("Attempting to delete player with ID: {}", id);
        playerService.deletePlayer(id);
    }

    public Optional<Player> getPlayer(Long id) {
        LOGGER.info("Attempting to get player with ID: {}", id);
        return playerService.getPlayer(id);
    }

    public List<Player> getAllPlayers() {
        LOGGER.info("Attempting to get all players");
        return playerService.getAllPlayers();
    }

    public List<Player> getPlayersByTeam(Long teamId) {
        LOGGER.info("Attempting to get players for team with ID: {}", teamId);
        return playerService.getPlayersByTeam(teamId);
    }

    public boolean doesPlayerExistByUsername(String username) {
        LOGGER.info("Checking if a player exists with the username: {}", username);
        return playerService.existsByUsername(username);
    }

    public Optional<Player> getPlayerByUsername(String username) {
        LOGGER.info("Attempting to get player with the username: {}", username);
        return playerService.findByUsername(username);
    }
}
