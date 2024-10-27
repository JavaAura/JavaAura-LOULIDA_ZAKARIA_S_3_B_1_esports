package org.esport.controller;

import org.esport.model.Team;
import org.esport.model.Player;
import org.esport.service.interfaces.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class TeamController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    public Team createTeam(String name) {
        LOGGER.info("Attempting to create a new team: {}", name);
        Team team = new Team();
        team.setName(name);
        return teamService.createTeam(team);
    }

    public Team updateTeam(Long id, String newName) {
        LOGGER.info("Attempting to update team with ID: {}", id);
        Optional<Team> teamOptional = teamService.getTeam(id);
        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();
            team.setName(newName);
            return teamService.updateTeam(team);
        } else {
            LOGGER.warn("Team with ID {} not found", id);
            return null;
        }
    }

    public void deleteTeam(Long id) {
        LOGGER.info("Attempting to delete team with ID: {}", id);
        teamService.deleteTeam(id);
    }

    public Optional<Team> getTeam(Long id) {
        LOGGER.info("Attempting to get team with ID: {}", id);
        return teamService.getTeam(id);
    }

    public List<Team> getAllTeams() {
        LOGGER.info("Attempting to get all teams");
        return teamService.getAllTeams();
    }

    public void addPlayerToTeam(Long teamId, Long playerId) {
        LOGGER.info("Attempting to add player {} to team {}", playerId, teamId);
        teamService.addPlayer(teamId, playerId);
    }

    public void removePlayerFromTeam(Long teamId, Long playerId) {
        LOGGER.info("Attempting to remove player {} from team {}", playerId, teamId);
        teamService.removePlayer(teamId, playerId);
    }

    public List<Team> getTeamsByTournament(Long tournamentId) {
        LOGGER.info("Attempting to get teams for tournament with ID: {}", tournamentId);
        return teamService.getTeamsByTournament(tournamentId);
    }
}
