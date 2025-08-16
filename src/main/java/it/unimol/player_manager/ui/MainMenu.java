package it.unimol.player_manager.ui;

import java.io.IOException;
import java.util.Scanner;

import it.unimol.player_manager.app.PlayersManager;
import it.unimol.player_manager.persistence.SerializeManager;

/**
 * Main menu class for the Player Manager application.
 */
public class MainMenu {
    private static MainMenu instance;
    public static Scanner input;
    private PlayersManager playersManager;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private MainMenu() {
        input = new Scanner(System.in);
        this.loadPlayersManager();
    }

    /**
     * Returns the singleton instance of the MainMenu.
     *
     * @return the MainMenu instance
     */
    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    /**
     * Executes the main menu.
     */
    public void execute() {
        System.out.println("Welcome to the Player Manager!");
        boolean exit = false;
        do {
            this.showOptions();
            System.out.println("Choose an option: ");
            int option = Integer.parseInt(input.nextLine());
            exit = this.handleOption(option);
        } while (!exit);
        System.out.println("Exiting Player Manager. Goodbye!");
        this.savePlayersManager();
        input.close();
    }

    /**
     * Shows the available options in the main menu.
     */
    private void showOptions() {
        System.out.println("F1: Enlist player");
        System.out.println("F2: Remove player");
        System.out.println("F3: Print team and scores");
        System.out.println("F4: Exit");
    }

    /**
     * Handles the selected menu option.
     *
     * @param option the selected option
     * @return true if the application should exit, false otherwise
     */
    private boolean handleOption(int option) {
        switch (option) {
            case 1 -> new EnlistPlayer(input, playersManager).execute();
            case 2 -> new RemovePlayer(input, playersManager).execute();
            case 3 -> new PrintTeamAndScores(input, playersManager).execute();
            case 4 -> {
                return true;
            }
            default -> System.out.println("Invalid option. Please try again.");
        }
        return false;
    }

    /**
     * Loads the PlayersManager from a file.
     */
    private void loadPlayersManager() {
        try {
            playersManager = SerializeManager.loadFromFile("manager.sr");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        } catch (IOException e) {
            System.out.println("Error loading players manager");
        }

    }

    /**
     * Saves the current state of the PlayersManager to a file.
     */
    private void savePlayersManager() {
        try {
            SerializeManager.saveToFile("manager.sr");
        } catch (IOException e) {
            System.out.println("Error saving players manager");
        }
    }
}
