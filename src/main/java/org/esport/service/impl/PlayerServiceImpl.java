package org.esport.service.impl;

import org.esport.dao.interfaces.PlayerDao;
import org.esport.model.Player;
import org.esport.service.interfaces.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class PlayerServiceImpl implements PlayerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private final PlayerDao playerDao;

    public PlayerServiceImpl(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public Player registerPlayer(Player player) {
        LOGGER.info("Registering a new player");
        return playerDao.register(player);
    }

    @Override
    public Player updatePlayer(Player player) {
        LOGGER.info("Updating the player with ID: {}", player.getId());
        return playerDao.update(player);
    }

    @Override
    public void deletePlayer(Long id) {
        LOGGER.info("Deleting the player with ID: {}", id);
        playerDao.delete(id);
    }

    @Override
    public Optional<Player> getPlayer(Long id) {
        LOGGER.info("Fetching the player with ID: {}", id);
        return playerDao.findById(id);
    }

    @Override
    public List<Player> getAllPlayers() {
        LOGGER.info("Fetching all players");
        return playerDao.findAll();
    }

    @Override
    public List<Player> getPlayersByTeam(Long teamId) {
        LOGGER.info("Fetching players for the team with ID: {}", teamId);
        return playerDao.findByTeam(teamId);
    }

    @Override
    public boolean existsByUsername(String username) {
        LOGGER.info("Checking if a player with the username: {} exists", username);
        return playerDao.existsByNickname(username);
    }

    @Override
    public Optional<Player> findByUsername(String username) {
        LOGGER.info("Fetching a player with the username: {}", username);
        return playerDao.findByNickname(username);
    }
}
