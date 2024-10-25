package org.esport.service.impl;

import org.esport.dao.interfaces.TeamDao;
import org.esport.dao.interfaces.PlayerDao;
import org.esport.model.Team;
import org.esport.model.Player;
import org.esport.service.interfaces.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class TeamServiceImpl implements TeamService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamServiceImpl.class);

    private final TeamDao teamDao;
    private final PlayerDao playerDao;

    public TeamServiceImpl(TeamDao teamDao, PlayerDao playerDao) {
        this.teamDao = teamDao;
        this.playerDao = playerDao;
    }

    @Override
    @Transactional
    public Team createTeam(Team team) {
        LOGGER.info("Creating a new team");
        return teamDao.create(team);
    }

    @Override
    @Transactional
    public Team updateTeam(Team team) {
        LOGGER.info("Updating the team with ID: {}", team.getId());
        return teamDao.update(team);
    }

    @Override
    @Transactional
    public void deleteTeam(Long id) {
        LOGGER.info("Deleting the team with ID: {}", id);
        teamDao.delete(id);
    }

    @Override
    public Optional<Team> getTeam(Long id) {
        LOGGER.info("Fetching the team with ID: {}", id);
        return teamDao.findById(id);
    }

    @Override
    public List<Team> getAllTeams() {
        LOGGER.info("Fetching all teams");
        return teamDao.findAll();
    }

    @Override
    @Transactional
    public void addPlayer(Long teamId, Long playerId) {
        LOGGER.info("Adding player {} to team {}", playerId, teamId);
        Optional<Team> teamOptional = teamDao.findById(teamId);
        Optional<Player> playerOptional = playerDao.findById(playerId);
        if (teamOptional.isPresent() && playerOptional.isPresent()) {
            Team team = teamOptional.get();
            Player player = playerOptional.get();
            team.getPlayers().add(player);
            player.setTeam(team);
            teamDao.update(team);
        } else {
            LOGGER.warn("Team with ID {} or Player with ID {} not found", teamId, playerId);
        }
    }

    @Override
    @Transactional
    public void removePlayer(Long teamId, Long playerId) {
        LOGGER.info("Removing player {} from team {}", playerId, teamId);
        Optional<Player> player = playerDao.findById(playerId);
        if (player.isPresent()) {
            teamDao.removePlayer(teamId, player.get());
        } else {
            LOGGER.warn("Player with ID {} not found", playerId);
        }
    }

    @Override
    public List<Team> getTeamsByTournament(Long tournamentId) {
        LOGGER.info("Fetching teams for the tournament with ID: {}", tournamentId);
        return teamDao.findByTournament(tournamentId);
    }
}
