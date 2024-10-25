package org.esport.presentation.menu;

import org.esport.controller.GameController;
import org.esport.model.Game;
import org.esport.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GameMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMenu.class);
    private final GameController gameController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public GameMenu(GameController gameController, ConsoleLogger consoleLogger, Scanner scanner) {
        this.gameController = gameController;
        this.consoleLogger = consoleLogger;
        this.scanner = scanner;
    }

    public void displayMenu() {
        boolean continueMenu = true;
        while (continueMenu) {
            consoleLogger.displayMessage("Game Menu:");
            consoleLogger.displayMessage("1. Create a game");
            consoleLogger.displayMessage("2. Update a game");
            consoleLogger.displayMessage("3. Delete a game");
            consoleLogger.displayMessage("4. Display a game");
            consoleLogger.displayMessage("5. Display all games");
            consoleLogger.displayMessage("0. Return to main menu");
            consoleLogger.displayMessage("Choose an option:");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    createGame();
                    break;
                case 2:
                    updateGame();
                    break;
                case 3:
                    deleteGame();
                    break;
                case 4:
                    displayGame();
                    break;
                case 5:
                    displayAllGames();
                    break;
                case 0:
                    continueMenu = false;
                    break;
                default:
                    consoleLogger.displayError("Invalid option. Please try again.");
            }
        }
    }

    private void createGame() {
        consoleLogger.displayMessage("Creating a new game");
        consoleLogger.displayMessage("Enter the game name:");
        String name = scanner.nextLine();
        consoleLogger.displayMessage("Enter the game difficulty (1-10):");
        int difficulty = scanner.nextInt();
        consoleLogger.displayMessage("Enter the average match duration (in minutes):");
        int averageMatchDuration = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Game newGame = gameController.createGame(name, difficulty, averageMatchDuration);
        if (newGame != null) {
            consoleLogger.displayMessage("Game successfully created. ID: " + newGame.getId());
        } else {
            consoleLogger.displayError("Error creating the game.");
        }
    }

    private void updateGame() {
        consoleLogger.displayMessage("Updating a game");
        consoleLogger.displayMessage("Enter the ID of the game to update:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Game> gameOptional = gameController.getGame(id);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            consoleLogger.displayMessage("Game found: " + game.getName());
            consoleLogger.displayMessage("Enter the new name for the game (or press Enter to keep the current name):");
            String newName = scanner.nextLine();
            if (newName.isEmpty()) {
                newName = game.getName();
            }

            consoleLogger.displayMessage("Enter the new game difficulty (1-10) (or -1 to keep the current value):");
            int newDifficulty = scanner.nextInt();
            if (newDifficulty == -1) {
                newDifficulty = game.getDifficulty();
            }

            consoleLogger.displayMessage("Enter the new average match duration (in minutes) (or -1 to keep the current value):");
            int newAverageMatchDuration = scanner.nextInt();
            if (newAverageMatchDuration == -1) {
                newAverageMatchDuration = game.getAverageMatchDuration();
            }
            scanner.nextLine(); // Consume the newline

            Game updatedGame = gameController.updateGame(id, newName, newDifficulty, newAverageMatchDuration);
            if (updatedGame != null) {
                consoleLogger.displayMessage("Game successfully updated.");
            } else {
                consoleLogger.displayError("Error updating the game.");
            }
        } else {
            consoleLogger.displayError("Game not found.");
        }
    }

    private void deleteGame() {
        consoleLogger.displayMessage("Deleting a game");
        consoleLogger.displayMessage("Enter the ID of the game to delete:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Game> gameOptional = gameController.getGame(id);
        if (gameOptional.isPresent()) {
            consoleLogger.displayMessage("Are you sure you want to delete the game " + gameOptional.get().getName() + "? (Y/N)");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("Y")) {
                gameController.deleteGame(id);
                consoleLogger.displayMessage("Game successfully deleted.");
            } else {
                consoleLogger.displayMessage("Deletion canceled.");
            }
        } else {
            consoleLogger.displayError("Game not found.");
        }
    }

    private void displayGame() {
        consoleLogger.displayMessage("Displaying a game");
        consoleLogger.displayMessage("Enter the ID of the game to display:");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consume the newline

        Optional<Game> gameOptional = gameController.getGame(id);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            consoleLogger.displayMessage("Game details:");
            consoleLogger.displayMessage("ID: " + game.getId());
            consoleLogger.displayMessage("Name: " + game.getName());
            consoleLogger.displayMessage("Difficulty: " + game.getDifficulty());
            consoleLogger.displayMessage("Average match duration: " + game.getAverageMatchDuration() + " minutes");
        } else {
            consoleLogger.displayError("Game not found.");
        }
    }

    private void displayAllGames() {
        consoleLogger.displayMessage("List of all games:");
        List<Game> games = gameController.getAllGames();
        if (!games.isEmpty()) {
            for (Game game : games) {
                consoleLogger.displayMessage("ID: " + game.getId() + ", Name: " + game.getName() + ", Difficulty: "
                        + game.getDifficulty() + ", Average duration: " + game.getAverageMatchDuration() + " minutes");
            }
        } else {
            consoleLogger.displayMessage("No games found.");
        }
    }
}
