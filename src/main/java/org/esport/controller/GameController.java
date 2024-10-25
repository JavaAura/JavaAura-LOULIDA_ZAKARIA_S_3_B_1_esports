package org.esport.controller;

import org.esport.model.Game;
import org.esport.service.interfaces.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class GameController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    public Game createGame(String name, int difficulty, int averageMatchDuration) {
        LOGGER.info("Attempting to create a new game: {}", name);
        Game game = new Game();
        game.setName(name);
        game.setDifficulty(difficulty);
        game.setAverageMatchDuration(averageMatchDuration);
        return gameService.createGame(game);
    }

    public Game updateGame(Long id, String newName, int newDifficulty, int newAverageMatchDuration) {
        LOGGER.info("Attempting to update the game with ID: {}", id);
        Optional<Game> gameOptional = gameService.getGame(id);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            game.setName(newName);
            game.setDifficulty(newDifficulty);
            game.setAverageMatchDuration(newAverageMatchDuration);
            return gameService.updateGame(game);
        } else {
            LOGGER.warn("Game with ID {} not found", id);
            return null;
        }
    }

    public void deleteGame(Long id) {
        LOGGER.info("Attempting to delete the game with ID: {}", id);
        gameService.deleteGame(id);
    }

    public Optional<Game> getGame(Long id) {
        LOGGER.info("Attempting to retrieve the game with ID: {}", id);
        return gameService.getGame(id);
    }

    public List<Game> getAllGames() {
        LOGGER.info("Attempting to retrieve all games");
        return gameService.getAllGames();
    }
}
