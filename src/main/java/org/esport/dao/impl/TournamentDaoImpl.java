package org.esport.dao.impl;

import org.esport.dao.interfaces.TournamentDao;
import org.esport.model.Tournament;
import org.esport.model.Team;
import org.esport.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class TournamentDaoImpl implements TournamentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tournament create(Tournament tournament) {
        entityManager.persist(tournament);
        LOGGER.info("Tournoi créé avec l'ID: {}", tournament.getId());
        return tournament;
    }

    @Override
    public Tournament update(Tournament tournament) {
        Tournament tournamentModifie = entityManager.merge(tournament);
        LOGGER.info("Tournoi modifié avec l'ID: {}", tournamentModifie.getId());
        return tournamentModifie;
    }

    @Override
    public void delete(Long id) {
        Tournament tournament = entityManager.find(Tournament.class, id);
        if (tournament != null) {
            entityManager.remove(tournament);
            LOGGER.info("Tournoi supprimé avec l'ID: {}", id);
        } else {
            LOGGER.warn("Tentative de suppression d'un tournoi inexistant avec l'ID: {}", id);
        }
    }

    @Override
    public Optional<Tournament> findById(Long id) {
        Tournament tournament = entityManager.find(Tournament.class, id);
        return Optional.ofNullable(tournament);
    }

    @Override
    public List<Tournament> findAll() {
        TypedQuery<Tournament> query = entityManager.createQuery("SELECT t FROM Tournoi t", Tournament.class);
        return query.getResultList();
    }

    @Override
    public void addTeam(Long tournoiId, Team team) {
        Tournament tournament = entityManager.find(Tournament.class, tournoiId);
        if (tournament != null) {
            tournament.getTeams().add(team);
            entityManager.merge(tournament);
            LOGGER.info("Équipe ajoutée au tournoi avec l'ID: {}", tournoiId);
        } else {
            LOGGER.warn("Tentative d'ajout d'une équipe à un tournoi inexistant avec l'ID: {}", tournoiId);
        }
    }

    @Override
    public void removeTeam(Long tournoiId, Team team) {
        Tournament tournament = entityManager.find(Tournament.class, tournoiId);
        if (tournament != null) {
            tournament.getTeams().remove(team);
            entityManager.merge(tournament);
            LOGGER.info("Équipe retirée du tournoi avec l'ID: {}", tournoiId);
        } else {
            LOGGER.warn("Tentative de retrait d'une équipe d'un tournoi inexistant avec l'ID: {}", tournoiId);
        }
    }

    @Override
    public int calculateEstimatedDuration(Long tournoiId) {
        Tournament tournament = entityManager.find(Tournament.class, tournoiId);
        if (tournament != null) {
            int nombreEquipes = tournament.getTeams().size();
            Game game = tournament.getGame();
            int dureeMoyenneMatch = game.getAverageMatchDuration();
            int tempsPauseEntreMatchs = tournament.getTimeBetweenMatches();

            int dureeEstimee = (nombreEquipes * dureeMoyenneMatch) + tempsPauseEntreMatchs;
            tournament.setEstimatedDuration(dureeEstimee);
            entityManager.merge(tournament);
            return dureeEstimee;
        }
        return 0;
    }
}
