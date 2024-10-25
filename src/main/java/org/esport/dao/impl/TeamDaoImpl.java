package org.esport.dao.impl;

import org.esport.dao.interfaces.TeamDao;
import org.esport.model.Team;
import org.esport.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class TeamDaoImpl implements TeamDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Team create(Team team) {
        entityManager.persist(team);
        LOGGER.info("Team created with ID: {}", team.getId());
        return team;
    }

    @Override
    public Team update(Team team) {
        Team updatedTeam = entityManager.merge(team);
        LOGGER.info("Team updated with ID: {}", updatedTeam.getId());
        return updatedTeam;
    }

    @Override
    public void delete(Long id) {
        Team team = entityManager.find(Team.class, id);
        if (team != null) {
            entityManager.remove(team);
            LOGGER.info("Team deleted with ID: {}", id);
        } else {
            LOGGER.warn("Attempt to delete a nonexistent team with ID: {}", id);
        }
    }

    @Override
    public Optional<Team> findById(Long id) {
        Team team = entityManager.find(Team.class, id);
        return Optional.ofNullable(team);
    }

    @Override
    public List<Team> findAll() {
        TypedQuery<Team> query = entityManager.createQuery("SELECT t FROM Team t", Team.class);
        return query.getResultList();
    }

    @Override
    public void addPlayer(Long teamId, Player player) {
        Team team = entityManager.find(Team.class, teamId);
        if (team != null) {
            team.getPlayers().add(player);
            player.setTeam(team);
            entityManager.merge(team);
            LOGGER.info("Player added to the team with ID: {}", teamId);
        } else {
            LOGGER.warn("Attempt to add a player to a nonexistent team with ID: {}", teamId);
        }
    }

    @Override
    public void removePlayer(Long teamId, Player player) {
        Team team = entityManager.find(Team.class, teamId);
        if (team != null) {
            team.getPlayers().remove(player);
            player.setTeam(null);
            entityManager.merge(team);
            LOGGER.info("Player removed from the team with ID: {}", teamId);
        } else {
            LOGGER.warn("Attempt to remove a player from a nonexistent team with ID: {}", teamId);
        }
    }

    @Override
    public List<Team> findByTournament(Long tournamentId) {
        TypedQuery<Team> query = entityManager.createQuery(
                "SELECT t FROM Team t JOIN t.tournaments tour WHERE tour.id = :tournamentId", Team.class);
        query.setParameter("tournamentId", tournamentId);
        return query.getResultList();
    }
}
