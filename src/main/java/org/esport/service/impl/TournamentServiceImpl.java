package org.esport.service.impl;

import org.esport.dao.interfaces.TournamentDao;
import org.esport.dao.interfaces.TeamDao;
import org.esport.model.Tournament;
import org.esport.model.Team;
import org.esport.service.interfaces.TeamService;
import org.esport.service.interfaces.TournamentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class TournamentServiceImpl implements TournamentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentServiceImpl.class);

    private final TournamentDao tournamentDao;
    private final TeamDao teamDao;

    public TournamentServiceImpl(TournamentDao tournamentDao, TeamDao teamDao) {
        this.tournamentDao = tournamentDao;
        this.teamDao = teamDao;
    }

    @Override
    public Tournament createTournament(Tournament tournament) {
        LOGGER.info("Creating a new tournament");
        return tournamentDao.create(tournament);
    }

    @Override
    public Tournament updateTournament(Tournament tournament) {
        LOGGER.info("Updating the tournament with ID: {}", tournament.getId());
        return tournamentDao.update(tournament);
    }

    @Override
    public void deleteTournament(Long id) {
        LOGGER.info("Deleting the tournament with ID: {}", id);
        tournamentDao.delete(id);
    }

    @Override
    public Optional<Tournament> getTournament(Long id) {
        LOGGER.info("Fetching the tournament with ID: {}", id);
        return tournamentDao.findById(id);
    }

    @Override
    public List<Tournament> getAllTournaments() {
        LOGGER.info("Fetching all tournaments");
        return tournamentDao.findAll();
    }

    @Override
    public void addTeam(Long tournamentId, Long teamId) {
        LOGGER.info("Adding team {} to tournament {}", teamId, tournamentId);
        Optional<Team> team = teamDao.findById(teamId);
        if (team.isPresent()) {
            tournamentDao.addTeam(tournamentId, team.get());
        } else {
            LOGGER.warn("Team with ID {} not found", teamId);
        }
    }

    @Override
    public void removeTeam(Long tournamentId, Long teamId) {
        LOGGER.info("Removing team {} from tournament {}", teamId, tournamentId);
        Optional<Team> team = teamDao.findById(teamId);
        if (team.isPresent()) {
            tournamentDao.removeTeam(tournamentId, team.get());
        } else {
            LOGGER.warn("Team with ID {} not found", teamId);
        }
    }

    @Override
    public int calculateEstimatedTournamentDuration(Long tournamentId) {
        LOGGER.info("Calculating the estimated duration for the tournament with ID: {}", tournamentId);
        return tournamentDao.calculateEstimatedDuration(tournamentId);
    }
}
