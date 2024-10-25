package org.esport.dao.interfaces;

import org.esport.model.Tournament;
import org.esport.model.Team;
import java.util.List;
import java.util.Optional;

public interface TournamentDao {
    Tournament create(Tournament tournament);

    Tournament update(Tournament tournament);

    void delete(Long id);

    Optional<Tournament> findById(Long id);

    List<Tournament> findAll();

    void addTeam(Long tournamentId, Team team);

    void removeTeam(Long tournamentId, Team team);

    int calculateEstimatedDuration(Long tournamentId);
}
