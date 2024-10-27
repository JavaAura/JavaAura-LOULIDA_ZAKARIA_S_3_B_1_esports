package org.esport.presentation.menu;

import org.esport.controller.TournamentController;
import org.esport.controller.TeamController;
import org.esport.controller.GameController;
import org.esport.model.Tournament;
import org.esport.model.Team;
import org.esport.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TournamentMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentMenu.class);
    private final TournamentController tournamentController;
    private final TeamController teamController;
    private final GameController gameController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public TournamentMenu(TournamentController tournamentController, TeamController teamController,
                          GameController gameController, ConsoleLogger consoleLogger, Scanner scanner) {
        this.tournamentController = tournamentController;
        this.teamController = teamController;
        this.gameController = gameController;
        this.consoleLogger = consoleLogger;
        this.scanner = scanner;
    }

    public void displayMenu() {
        boolean continueLoop = true;
        while (continueLoop) {
            consoleLogger.displayMessage("Tournament Menu:");
            consoleLogger.displayMessage("1. Create a Tournament");
            consoleLogger.displayMessage("2. Modify a Tournament");
            consoleLogger.displayMessage("3. Delete a Tournament");
            consoleLogger.displayMessage("4. View a Tournament");
            consoleLogger.displayMessage("5. View All Tournaments");
            consoleLogger.displayMessage("6. Add a Team to a Tournament");
            consoleLogger.displayMessage("7. Remove a Team from a Tournament");
            consoleLogger.displayMessage("8. Calculate Estimated Duration of a Tournament");
            consoleLogger.displayMessage("0. Return to Main Menu");
            consoleLogger.displayMessage("Choose an option:");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    createTournament();
                    break;
                case 2:
                    modifyTournament();
                    break;
                case 3:
                    deleteTournament();
                    break;
                case 4:
                    viewTournament();
                    break;
                case 5:
                    viewAllTournaments();
                    break;
                case 6:
                    addTeamToTournament();
                    break;
                case 7:
                    removeTeamFromTournament();
                    break;
                case 8:
                    calculateTournamentDuration();
                    break;
                case 0:
                    continueLoop = false;
                    break;
                default:
                    consoleLogger.displayError("Invalid option. Please try again.");
            }
        }
    }

    private void createTournament() {
        consoleLogger.displayMessage("Creating a new Tournament");
        consoleLogger.displayMessage("Enter the tournament title:");
        String title = scanner.nextLine();

        consoleLogger.displayMessage("Enter the game ID for this tournament:");
        Long gameId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        consoleLogger.displayMessage("Enter the start date (format: dd/MM/yyyy):");
        LocalDate startDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        consoleLogger.displayMessage("Enter the end date (format: dd/MM/yyyy):");
        LocalDate endDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        consoleLogger.displayMessage("Enter the expected number of spectators:");
        int spectators = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        consoleLogger.displayMessage("Enter the average duration of a match (in minutes):");
        int matchDuration = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        consoleLogger.displayMessage("Enter the ceremony time (in minutes):");
        int ceremonyTime = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        consoleLogger.displayMessage("Enter the break time between matches (in minutes):");
        int breakTime = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Tournament newTournament = tournamentController.createTournament(title, gameId, startDate, endDate, spectators,
                matchDuration, ceremonyTime, breakTime);
        if (newTournament != null) {
            consoleLogger.displayMessage("Tournament successfully created. ID: " + newTournament.getId());
        } else {
            consoleLogger.displayError("Error creating the tournament.");
        }
    }

    private void modifyTournament() {
        consoleLogger.displayMessage("Modifying a tournament");
        consoleLogger.displayMessage("Enter the tournament ID to modify:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Tournament> tournamentOptional = tournamentController.getTournament(id);
        if (tournamentOptional.isPresent()) {
            Tournament tournament = tournamentOptional.get();
            consoleLogger.displayMessage("Enter the new title (or press Enter to keep the old one):");
            String newTitle = scanner.nextLine();
            if (newTitle.isEmpty()) {
                newTitle = tournament.getTitle();
            }

            consoleLogger.displayMessage("Enter the new start date (dd/MM/yyyy) (or press Enter to keep the old one):");
            String startDateStr = scanner.nextLine();
            LocalDate newStartDate = startDateStr.isEmpty() ? tournament.getStartDate()
                    : LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            consoleLogger.displayMessage("Enter the new end date (dd/MM/yyyy) (or press Enter to keep the old one):");
            String endDateStr = scanner.nextLine();
            LocalDate newEndDate = endDateStr.isEmpty() ? tournament.getEndDate()
                    : LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            consoleLogger.displayMessage("Enter the new number of spectators (or -1 to keep the old one):");
            int newSpectators = scanner.nextInt();
            if (newSpectators == -1) {
                newSpectators = tournament.getNumberOfSpectators();
            }
            scanner.nextLine(); // Consume the newline

            Tournament modifiedTournament = tournamentController.updateTournament(id, newTitle, newStartDate,
                    newEndDate, newSpectators);
            if (modifiedTournament != null) {
                consoleLogger.displayMessage("Tournament successfully modified.");
            } else {
                consoleLogger.displayError("Error modifying the tournament.");
            }
        } else {
            consoleLogger.displayError("Tournament not found.");
        }
    }

    private void deleteTournament() {
        consoleLogger.displayMessage("Deleting a tournament");
        consoleLogger.displayMessage("Enter the tournament ID to delete:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Tournament> tournamentOptional = tournamentController.getTournament(id);
        if (tournamentOptional.isPresent()) {
            consoleLogger.displayMessage("Are you sure you want to delete the tournament " + tournamentOptional.get().getTitle() + "? (Y/N)");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("Y")) {
                tournamentController.deleteTournament(id);
                consoleLogger.displayMessage("Tournament successfully deleted.");
            } else {
                consoleLogger.displayMessage("Deletion canceled.");
            }
        } else {
            consoleLogger.displayError("Tournament not found.");
        }
    }

    private void viewTournament() {
        consoleLogger.displayMessage("Viewing a tournament");
        consoleLogger.displayMessage("Enter the tournament ID to view:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Tournament> tournamentOptional = tournamentController.getTournament(id);
        if (tournamentOptional.isPresent()) {
            Tournament tournament = tournamentOptional.get();
            consoleLogger.displayMessage("Tournament Details:");
            consoleLogger.displayMessage("ID: " + tournament.getId());
            consoleLogger.displayMessage("Title: " + tournament.getTitle());
            consoleLogger.displayMessage("Game: " + tournament.getGame().getName());
            consoleLogger.displayMessage("Start Date: " + tournament.getStartDate());
            consoleLogger.displayMessage("End Date: " + tournament.getEndDate());
            consoleLogger.displayMessage("Spectators: " + tournament.getNumberOfSpectators());
            consoleLogger.displayMessage("Status: " + tournament.getStatus());
            consoleLogger.displayMessage("Participating Teams:");
            for (Team team : tournament.getTeams()) {
                consoleLogger.displayMessage("- " + team.getName());
            }
        } else {
            consoleLogger.displayError("Tournament not found.");
        }
    }

    private void viewAllTournaments() {
        consoleLogger.displayMessage("List of all tournaments:");
        List<Tournament> tournaments = tournamentController.getAllTournaments();
        if (!tournaments.isEmpty()) {
            for (Tournament tournament : tournaments) {
                String gameName = tournament.getGame() != null ? tournament.getGame().getName() : "N/A";
                consoleLogger.displayMessage("ID: " + tournament.getId() + ", Title: " + tournament.getTitle() + ", Game: "
                        + gameName + ", Status: " + tournament.getStatus());
            }
        } else {
            consoleLogger.displayMessage("No tournaments found.");
        }
    }

    private void addTeamToTournament() {
        consoleLogger.displayMessage("Adding a team to a tournament");
        consoleLogger.displayMessage("Enter the tournament ID:");
        Long tournamentId = scanner.nextLong();
        consoleLogger.displayMessage("Enter the team ID to add:");
        Long teamId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        try {
            tournamentController.addTeamToTournament(tournamentId, teamId);
            consoleLogger.displayMessage("Team successfully added to the tournament.");
        } catch (Exception e) {
            consoleLogger.displayError("Error adding team to the tournament: " + e.getMessage());
        }
    }

    private void removeTeamFromTournament() {
        consoleLogger.displayMessage("Removing a team from a tournament");
        consoleLogger.displayMessage("Enter the tournament ID:");
        Long tournamentId = scanner.nextLong();
        consoleLogger.displayMessage("Enter the team ID to remove:");
        Long teamId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        try {
            tournamentController.removeTeamFromTournament(tournamentId, teamId);
            consoleLogger.displayMessage("Team successfully removed from the tournament.");
        } catch (Exception e) {
            consoleLogger.displayError("Error removing team from the tournament: " + e.getMessage());
        }
    }

    private void calculateTournamentDuration() {
        consoleLogger.displayMessage("Calculating the estimated duration of a tournament");
        consoleLogger.displayMessage("Enter the tournament ID:");
        Long tournamentId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline
        Optional<Tournament> tournoiOptional = tournamentController.getTournament(tournamentId);
        if (tournoiOptional.isPresent()) {
            Tournament tournament = tournoiOptional.get();


        try {
            long estimatedDuration =
                    tournamentController.calculateEstimatedDuration(tournament);
            consoleLogger.displayMessage("Estimated duration of the tournament: " + estimatedDuration + " minutes");
        } catch (Exception e) {
            consoleLogger.displayError("Error calculating the tournament duration: " + e.getMessage());
        }

        } else {
            consoleLogger.displayError("Tournoi non trouvé après le calcul.");
        }




    }
}
