package it.unimol.player_manager.ui;

import java.io.IOException;
import java.util.Scanner;

import it.unimol.player_manager.app.PlayersManager;
import it.unimol.player_manager.persistence.PostgreConnection;
import it.unimol.player_manager.persistence.SerializeManager;

public class MainMenu {
    private static MainMenu instance;
    public static Scanner input;
    private PlayersManager playersManager;

    private MainMenu() {
        input = new Scanner(System.in);
        try {
            playersManager = SerializeManager.loadFromFile("manager.sr");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error loading players manager");
            e.printStackTrace();
        }
    }

    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    public void execute() {
        System.out.println("Welcome to the Player Manager!");
        PostgreConnection driverConnection = PostgreConnection.getInstance();
        boolean exit = false;
        do {
            this.showOptions();
            System.out.println("Choose an option: ");
            int option = Integer.parseInt(input.nextLine());
            exit = this.handleOption(option);
        } while (!exit);
        try {
            SerializeManager.saveToFile("manager.sr");
        } catch (IOException e) {
            System.out.println("Error saving players manager");
            e.printStackTrace();
        }
        System.out.println("Exiting Player Manager. Goodbye!");

        input.close();
    }

    private void showOptions() {
        System.out.println("F1: Enlist player");
        System.out.println("F2: Remove player");
        System.out.println("F3: Print team and scores");
        System.out.println("F4: Exit");
    }

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
}
