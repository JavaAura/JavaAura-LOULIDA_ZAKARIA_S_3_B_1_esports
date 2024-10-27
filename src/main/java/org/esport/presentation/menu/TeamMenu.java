package org.esport.presentation.menu;

import org.esport.controller.TeamController;
import org.esport.controller.PlayerController;
import org.esport.model.Team;
import org.esport.model.Player;
import org.esport.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TeamMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamMenu.class);
    private final TeamController teamController;
    private final PlayerController playerController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public TeamMenu(TeamController teamController, PlayerController playerController, ConsoleLogger consoleLogger,
                    Scanner scanner) {
        this.teamController = teamController;
        this.playerController = playerController;
        this.consoleLogger = consoleLogger;
        this.scanner = scanner;
    }

    public void displayMenu() {
        boolean continueMenu = true;
        while (continueMenu) {
            consoleLogger.displayMessage("Team Menu:");
            consoleLogger.displayMessage("1. Create a team");
            consoleLogger.displayMessage("2. Modify a team");
            consoleLogger.displayMessage("3. Delete a team");
            consoleLogger.displayMessage("4. Display a team");
            consoleLogger.displayMessage("5. Display all teams");
            consoleLogger.displayMessage("6. Add a player to a team");
            consoleLogger.displayMessage("7. Remove a player from a team");
            consoleLogger.displayMessage("0. Return to main menu");
            consoleLogger.displayMessage("Choose an option:");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    createTeam();
                    break;
                case 2:
                    modifyTeam();
                    break;
                case 3:
                    deleteTeam();
                    break;
                case 4:
                    displayTeam();
                    break;
                case 5:
                    displayAllTeams();
                    break;
                case 6:
                    addPlayerToTeam();
                    break;
                case 7:
                    removePlayerFromTeam();
                    break;
                case 0:
                    continueMenu = false;
                    break;
                default:
                    consoleLogger.displayError("Invalid option. Please try again.");
            }
        }
    }

    private void createTeam() {
        consoleLogger.displayMessage("Creating a new team");
        consoleLogger.displayMessage("Enter the team's name:");
        String name = scanner.nextLine();

        Team createdTeam = teamController.createTeam(name);

        if (createdTeam != null) {
            consoleLogger.displayMessage("Team successfully created. ID: " + createdTeam.getId());
        } else {
            consoleLogger.displayError("Error while creating the team.");
        }
    }

    private void modifyTeam() {
        consoleLogger.displayMessage("Modifying a team");
        consoleLogger.displayMessage("Enter the ID of the team to modify:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline
        consoleLogger.displayMessage("Enter the new name for the team:");
        String newName = scanner.nextLine();

        Team modifiedTeam = teamController.updateTeam(id, newName);
        if (modifiedTeam != null) {
            consoleLogger.displayMessage("Team successfully modified.");
        } else {
            consoleLogger.displayError("Error while modifying the team.");
        }
    }

    private void deleteTeam() {
        consoleLogger.displayMessage("Deleting a team");
        consoleLogger.displayMessage("Enter the ID of the team to delete:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        teamController.deleteTeam(id);
        consoleLogger.displayMessage("Team successfully deleted (if it existed).");
    }

    private void displayTeam() {
        consoleLogger.displayMessage("Displaying a team");
        consoleLogger.displayMessage("Enter the ID of the team to display:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Team> optionalTeam = teamController.getTeam(id);
        if (optionalTeam.isPresent()) {
            Team team = optionalTeam.get();
            consoleLogger.displayMessage("ID: " + team.getId());
            consoleLogger.displayMessage("Name: " + team.getName());
            consoleLogger.displayMessage("Rank: " + team.getRanking());
            consoleLogger.displayMessage("Players:");
            for (Player player : team.getPlayers()) {
                consoleLogger.displayMessage("  - " + player.getUsername());
            }
        } else {
            consoleLogger.displayError("Team not found.");
        }
    }

    private void displayAllTeams() {
        consoleLogger.displayMessage("List of all teams:");
        List<Team> teams = teamController.getAllTeams();
        if (!teams.isEmpty()) {
            for (Team team : teams) {
                consoleLogger.displayMessage("ID: " + team.getId() + ", Name: " + team.getName() + ", Rank: "
                        + team.getRanking());
            }
        } else {
            consoleLogger.displayMessage("No teams found.");
        }
    }

    private void addPlayerToTeam() {
        consoleLogger.displayMessage("Adding a player to a team");
        consoleLogger.displayMessage("Enter the team's ID:");
        Long teamId = scanner.nextLong();
        consoleLogger.displayMessage("Enter the player's ID to add:");
        Long playerId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        teamController.addPlayerToTeam(teamId, playerId);
        consoleLogger.displayMessage("Player successfully added to the team (if both exist).");
    }

    private void removePlayerFromTeam() {
        consoleLogger.displayMessage("Removing a player from a team");
        consoleLogger.displayMessage("Enter the team's ID:");
        Long teamId = scanner.nextLong();
        consoleLogger.displayMessage("Enter the player's ID to remove:");
        Long playerId = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        teamController.removePlayerFromTeam(teamId, playerId);
        consoleLogger.displayMessage("Player successfully removed from the team (if both exist).");
    }
}
