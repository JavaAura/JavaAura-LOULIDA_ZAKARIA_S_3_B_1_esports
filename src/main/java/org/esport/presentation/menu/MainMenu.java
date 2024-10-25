package org.esport.presentation.menu;

import org.esport.controller.PlayerController;
import org.esport.controller.TeamController;
import org.esport.controller.TournamentController;
import org.esport.controller.GameController;
import org.esport.util.ConsoleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class MainMenu {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainMenu.class);
    private final PlayerController playerController;
    private final TeamController teamController;
    private final TournamentController tournamentController;
    private final GameController gameController;
    private final ConsoleLogger consoleLogger;
    private final Scanner scanner;

    public MainMenu(PlayerController playerController, TeamController teamController,
                    TournamentController tournamentController, GameController gameController,
                    ConsoleLogger consoleLogger) {
        this.playerController = playerController;
        this.teamController = teamController;
        this.tournamentController = tournamentController;
        this.gameController = gameController;
        this.consoleLogger = consoleLogger;
        this.scanner = new Scanner(System.in);
    }

    public void displayMainMenu() {
        boolean continueMenu = true;
        while (continueMenu) {
            consoleLogger.displayMessage("Main Menu:");
            consoleLogger.displayMessage("1. Manage players");
            consoleLogger.displayMessage("2. Manage teams");
            consoleLogger.displayMessage("3. Manage tournaments");
            consoleLogger.displayMessage("4. Manage games");
            consoleLogger.displayMessage("0. Exit");
            consoleLogger.displayMessage("Choose an option:");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    new PlayerMenu(playerController, consoleLogger, scanner).displayMenu();
                    break;
                case 2:
                    new TeamMenu(teamController, playerController, consoleLogger, scanner).displayMenu();
                    break;
                case 3:
                    new TournamentMenu(tournamentController, teamController, gameController, consoleLogger, scanner)
                            .displayMenu();
                    break;
                case 4:
                    new GameMenu(gameController, consoleLogger, scanner).displayMenu();
                    break;
                case 0:
                    continueMenu = false;
                    break;
                default:
                    consoleLogger.displayError("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}
