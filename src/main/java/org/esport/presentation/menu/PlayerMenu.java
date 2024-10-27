package org.esport.presentation.menu;

import org.esport.controller.PlayerController;
import org.esport.model.Player;
import org.esport.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PlayerMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerMenu.class);
    private final PlayerController playerController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public PlayerMenu(PlayerController playerController, ConsoleLogger consoleLogger, Scanner scanner) {
        this.playerController = playerController;
        this.consoleLogger = consoleLogger;
        this.scanner = scanner;
    }

    public void displayMenu() {
        boolean continueMenu = true;
        while (continueMenu) {
            consoleLogger.displayMessage("Player Menu:");
            consoleLogger.displayMessage("1. Register a player");
            consoleLogger.displayMessage("2. Modify a player");
            consoleLogger.displayMessage("3. Delete a player");
            consoleLogger.displayMessage("4. Display a player");
            consoleLogger.displayMessage("5. Display all players");
            consoleLogger.displayMessage("6. Display players from a team");
            consoleLogger.displayMessage("0. Return to main menu");
            consoleLogger.displayMessage("Choose an option:");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    registerPlayer();
                    break;
                case 2:
                    modifyPlayer();
                    break;
                case 3:
                    deletePlayer();
                    break;
                case 4:
                    displayPlayer();
                    break;
                case 5:
                    displayAllPlayers();
                    break;
                case 6:
                    displayPlayersByTeam();
                    break;
                case 0:
                    continueMenu = false;
                    break;
                default:
                    consoleLogger.displayError("Invalid option. Please try again.");
            }
        }
    }

    private void registerPlayer() {
        consoleLogger.displayMessage("Enter the player's username:");
        String username = scanner.nextLine();
        consoleLogger.displayMessage("Enter the player's age:");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Player newPlayer = playerController.registerPlayer(username, age);
        if (newPlayer != null) {
            consoleLogger.displayMessage("Player successfully registered. ID: " + newPlayer.getId());
        } else {
            consoleLogger.displayError("Failed to register the player.");
        }
    }

    private void modifyPlayer() {
        consoleLogger.displayMessage("Enter the ID of the player to modify:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        consoleLogger.displayMessage("Enter the player's new username:");
        String newUsername = scanner.nextLine();
        consoleLogger.displayMessage("Enter the player's new age:");
        int newAge = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Player modifiedPlayer = playerController.updatePlayer(id, newUsername, newAge);
        if (modifiedPlayer != null) {
            consoleLogger.displayMessage("Player successfully modified.");
        } else {
            consoleLogger.displayError("Failed to modify the player.");
        }
    }

    private void deletePlayer() {
        consoleLogger.displayMessage("Enter the ID of the player to delete:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        playerController.deletePlayer(id);
        consoleLogger.displayMessage("Player successfully deleted.");
    }

    private void displayPlayer() {
        consoleLogger.displayMessage("Enter the ID of the player to display:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Player> playerOptional = playerController.getPlayer(id);
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            consoleLogger.displayMessage("Player details:");
            consoleLogger.displayMessage("ID: " + player.getId());
            consoleLogger.displayMessage("Username: " + player.getUsername());
            consoleLogger.displayMessage("Age: " + player.getAge());
        } else {
            consoleLogger.displayError("Player not found.");
        }
    }

    private void displayAllPlayers() {
        List<Player> players = playerController.getAllPlayers();
        if (!players.isEmpty()) {
            consoleLogger.displayMessage("List of all players:");
            for (Player player : players) {
                consoleLogger.displayMessage(
                        "ID: " + player.getId() + ", Username: " + player.getUsername() + ", Age: " + player.getAge());
            }
        } else {
            consoleLogger.displayMessage("No players found.");
        }
    }

    private void displayPlayersByTeam() {
        consoleLogger.displayMessage("Enter the team ID:");
        Long teamId = scanner.nextLong();
        scanner.nextLine();

        List<Player> players = playerController.getPlayersByTeam(teamId);
        if (!players.isEmpty()) {
            consoleLogger.displayMessage("Players in the team (ID: " + teamId + "):");
            for (Player player : players) {
                consoleLogger.displayMessage(
                        "ID: " + player.getId() + ", Username: " + player.getUsername() + ", Age: " + player.getAge());
            }
        } else {
            consoleLogger.displayMessage("No players found for this team.");
        }
    }
}
