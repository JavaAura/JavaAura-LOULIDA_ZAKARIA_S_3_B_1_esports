package org.esport.dao.impl;

import org.esport.dao.interfaces.PlayerDao;
import org.esport.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class PlayerDaoImpl implements PlayerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Player register(Player player) {
        if (entityManager == null) {
            LOGGER.error("EntityManager is null");
            throw new IllegalStateException("EntityManager is not initialized");
        }
        entityManager.persist(player);
        LOGGER.info("Player registered with ID: {}", player.getId());
        return player;
    }

    @Override
    public Player update(Player player) {
        Player updatedPlayer = entityManager.merge(player);
        LOGGER.info("Player updated with ID: {}", updatedPlayer.getId());
        return updatedPlayer;
    }

    @Override
    public void delete(Long id) {
        Player player = entityManager.find(Player.class, id);
        if (player != null) {
            entityManager.remove(player);
            LOGGER.info("Player deleted with ID: {}", id);
        } else {
            LOGGER.warn("Attempt to delete a nonexistent player with ID: {}", id);
        }
    }

    @Override
    public Optional<Player> findById(Long id) {
        Player player = entityManager.find(Player.class, id);
        return Optional.ofNullable(player);
    }

    @Override
    public List<Player> findAll() {
        TypedQuery<Player> query = entityManager.createQuery("SELECT j FROM Player j", Player.class);
        return query.getResultList();
    }

    @Override
    public List<Player> findByTeam(Long teamId) {
        TypedQuery<Player> query = entityManager.createQuery(
                "SELECT j FROM Player j WHERE j.team.id = :teamId", Player.class);
        query.setParameter("teamId", teamId);
        return query.getResultList();
    }

    @Override
    public boolean existsByNickname(String pseudo) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(j) FROM Player j WHERE j.pseudo = :pseudo", Long.class);
        query.setParameter("pseudo", pseudo);
        return query.getSingleResult() > 0;
    }

    @Override
    public Optional<Player> findByNickname(String pseudo) {
        TypedQuery<Player> query = entityManager.createQuery(
                "SELECT j FROM Player j WHERE j.pseudo = :pseudo", Player.class);
        query.setParameter("pseudo", pseudo);
        List<Player> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
}
