package org.esport.dao.impl;

import org.esport.dao.interfaces.GameDao;
import org.esport.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class GameDaoImpl implements GameDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Game create(Game game) {
        entityManager.persist(game);
        LOGGER.info("Game created with ID: {}", game.getId());
        return game;
    }

    @Override
    public Game update(Game game) {
        Game updatedGame = entityManager.merge(game);
        LOGGER.info("Game updated with ID: {}", updatedGame.getId());
        return updatedGame;
    }

    @Override
    public void delete(Long id) {
        Game game = entityManager.find(Game.class, id);
        if (game != null) {
            entityManager.remove(game);
            LOGGER.info("Game deleted with ID: {}", id);
        } else {
            LOGGER.warn("Attempt to delete a nonexistent game with ID: {}", id);
        }
    }

    @Override
    public Optional<Game> findById(Long id) {
        Game game = entityManager.find(Game.class, id);
        return Optional.ofNullable(game);
    }

    @Override
    public List<Game> findAll() {
        TypedQuery<Game> query = entityManager.createQuery("SELECT g FROM Game g", Game.class);
        return query.getResultList();
    }
}
