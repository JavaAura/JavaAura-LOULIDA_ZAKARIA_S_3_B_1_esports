package org.esport.controller;

import org.esport.model.Game;
import org.esport.model.Tournament;
import org.esport.service.interfaces.TournamentService;
import org.esport.service.interfaces.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class TournamentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentController.class);
    private final TournamentService tournamentService;
    private final GameService gameService;

    public TournamentController(TournamentService tournamentService, GameService gameService) {
        this.tournamentService = tournamentService;
        this.gameService = gameService;
    }

    public Tournament createTournament(String title, Long gameId, LocalDate startDate, LocalDate endDate,
                                       int numberOfSpectators, int averageMatchDuration, int ceremonyDuration, int pauseBetweenMatches) {
        LOGGER.info("Attempting to create a new tournament: {}", title);
        Tournament tournament = new Tournament();
        tournament.setTitle(title);

        Game game = gameService.getGame(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found for ID: " + gameId));
        tournament.setGame(game);

        tournament.setStartDate(startDate);
        tournament.setEndDate(endDate);
        tournament.setNumberOfSpectators(numberOfSpectators);
        tournament.setAverageMatchDuration(averageMatchDuration);
        tournament.setCeremonyTime(ceremonyDuration);
        tournament.setTimeBetweenMatches(pauseBetweenMatches);

        // Save the tournament first to get an ID
        Tournament savedTournament = tournamentService.createTournament(tournament);

        // Calculate estimated duration using a method from TournamentDaoExtension
        int estimatedDuration = tournamentService.calculateEstimatedTournamentDuration(savedTournament.getId());
        savedTournament.setEstimatedDuration(estimatedDuration);

        // Update the tournament with the calculated estimated duration
        return tournamentService.updateTournament(savedTournament);
    }

    public Tournament updateTournament(Long id, String newTitle, LocalDate newStartDate,
                                       LocalDate newEndDate, int newNumberOfSpectators) {
        LOGGER.info("Attempting to update tournament with ID: {}", id);
        Optional<Tournament> tournamentOptional = tournamentService.getTournament(id);
        if (tournamentOptional.isPresent()) {
            Tournament tournament = tournamentOptional.get();
            tournament.setTitle(newTitle);
            tournament.setStartDate(newStartDate);
            tournament.setEndDate(newEndDate);
            tournament.setNumberOfSpectators(newNumberOfSpectators);
            return tournamentService.updateTournament(tournament);
        } else {
            LOGGER.warn("Tournament with ID {} not found", id);
            return null;
        }
    }

    public void deleteTournament(Long id) {
        LOGGER.info("Attempting to delete tournament with ID: {}", id);
        tournamentService.deleteTournament(id);
    }

    public Optional<Tournament> getTournament(Long id) {
        LOGGER.info("Attempting to get tournament with ID: {}", id);
        return tournamentService.getTournament(id);
    }

    public List<Tournament> getAllTournaments() {
        LOGGER.info("Attempting to get all tournaments");
        return tournamentService.getAllTournaments();
    }

    public void addTeamToTournament(Long tournamentId, Long teamId) {
        LOGGER.info("Attempting to add team {} to tournament {}", teamId, tournamentId);
        tournamentService.addTeam(tournamentId, teamId);
    }

    public void removeTeamFromTournament(Long tournamentId, Long teamId) {
        LOGGER.info("Attempting to remove team {} from tournament {}", teamId, tournamentId);
        tournamentService.removeTeam(tournamentId, teamId);
    }

    public int getEstimatedDurationForTournament(Long tournamentId) {
        LOGGER.info("Attempting to get estimated duration for tournament with ID: {}", tournamentId);
        return tournamentService.calculateEstimatedTournamentDuration(tournamentId);
    }

    public int calculateEstimatedDuration(Tournament tournament) {
        // This is a simplified example, adjust according to your specific requirements
        int numberOfDays = (int) ChronoUnit.DAYS.between(tournament.getStartDate(), tournament.getEndDate()) + 1;
        int matchesPerDay = 8; // Assuming 8 matches per day, adjust as needed
        int totalMatches = numberOfDays * matchesPerDay;

        return (tournament.getAverageMatchDuration() * totalMatches) +
                (tournament.getTimeBetweenMatches() * (totalMatches - 1)) +
                tournament.getCeremonyTime();
    }
}
