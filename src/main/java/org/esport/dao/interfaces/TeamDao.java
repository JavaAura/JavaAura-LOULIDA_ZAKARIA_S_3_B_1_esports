package org.esport.dao.interfaces;

import org.esport.model.Team;
import org.esport.model.Player;
import java.util.List;
import java.util.Optional;

public interface TeamDao {
    Team create(Team team);

    Team update(Team team);

    void delete(Long id);

    Optional<Team> findById(Long id);

    List<Team> findAll();

    void addPlayer(Long teamId, Player player);

    void removePlayer(Long teamId, Player player);

    List<Team> findByTournament(Long tournamentId);
}
