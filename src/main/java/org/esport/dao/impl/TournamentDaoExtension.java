package org.esport.dao.impl;

import org.esport.dao.interfaces.TournamentDao;
import org.esport.model.Tournament;
import org.esport.model.Team;
import org.esport.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class TournamentDaoExtension implements TournamentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentDaoExtension.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final TournamentDaoImpl tournamentDaoImpl;

    public TournamentDaoExtension(TournamentDaoImpl tournamentDaoImpl) {
        this.tournamentDaoImpl = tournamentDaoImpl;
    }

    @Override
    public Tournament create(Tournament tournament) {
        return tournamentDaoImpl.create(tournament);
    }

    @Override
    public Tournament update(Tournament tournament) {
        return tournamentDaoImpl.update(tournament);
    }

    @Override
    public void delete(Long id) {
        tournamentDaoImpl.delete(id);
    }

    @Override
    public Optional<Tournament> findById(Long id) {
        return tournamentDaoImpl.findById(id);
    }

    @Override
    public List<Tournament> findAll() {
        return tournamentDaoImpl.findAll();
    }

    @Override
    public void addTeam(Long tournamentId, Team team) {
        tournamentDaoImpl.addTeam(tournamentId, team);
    }

    @Override
    public void removeTeam(Long tournamentId, Team team) {
        tournamentDaoImpl.removeTeam(tournamentId, team);
    }

    @Override
    public int calculateEstimatedDuration(Long tournamentId) {
        Tournament tournament = entityManager.find(Tournament.class, tournamentId);
        if (tournament != null) {
            int numberOfTeams = tournament.getTeams().size();
            Game game = tournament.getGame();
            int averageMatchDuration = game.getAverageMatchDuration();
            int gameDifficulty = game.getDifficulty();
            int breakTimeBetweenMatches = tournament.getTimeBetweenMatches();
            int ceremonyTime = tournament.getCeremonyTime();

            int estimatedDuration = (numberOfTeams * averageMatchDuration * gameDifficulty) + breakTimeBetweenMatches
                    + ceremonyTime;
            tournament.setEstimatedDuration(estimatedDuration);
            entityManager.merge(tournament);
            return estimatedDuration;
        }
        return 0;
    }
}
