package org.esport.service.impl;

import org.esport.dao.interfaces.GameDao;
import org.esport.model.Game;
import org.esport.service.interfaces.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class GameServiceImpl implements GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServiceImpl.class);

    private final GameDao gameDao;

    public GameServiceImpl(GameDao gameDao) {
        this.gameDao = gameDao;
    }


    @Override
    public Game createGame(Game game) {
        LOGGER.info("Creating a new game");
        return gameDao.create(game);
    }

    @Override
    public Game updateGame(Game game) {
        LOGGER.info("Updating the game with ID: {}", game.getId());
        return gameDao.update(game);
    }

    @Override
    public void deleteGame(Long id) {
        LOGGER.info("Deleting the game with ID: {}", id);
        gameDao.delete(id);
    }

    @Override
    public Optional<Game> getGame(Long id) {
        LOGGER.info("Fetching the game with ID: {}", id);
        return gameDao.findById(id);
    }

    @Override
    public List<Game> getAllGames() {
        LOGGER.info("Fetching all games");
        return gameDao.findAll();
    }
}
