package org.esport.service.interfaces;

import org.esport.model.Tournament;

import java.util.List;
import java.util.Optional;

public interface TournamentService {
    Tournament createTournament(Tournament tournament);

    Tournament updateTournament(Tournament tournament);

    void deleteTournament(Long id);

    Optional<Tournament> getTournament(Long id);

    List<Tournament> getAllTournaments();

    void addTeam(Long tournamentId, Long teamId);

    void removeTeam(Long tournamentId, Long teamId);

    int calculateEstimatedTournamentDuration(Long tournamentId);
}
