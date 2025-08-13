package it.unimol.player_manager.ui;

import java.util.Scanner;

import it.unimol.player_manager.app.PlayersManager;
import it.unimol.player_manager.entity.Player;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemovePlayer {

    private Scanner input;

    private PlayersManager playersManager;

    public void execute() {

        int jerseyNumber = -1;

        while (jerseyNumber == -1) {

            System.out.print("Enter the jersey number of the player to remove: ");
            try {
                jerseyNumber = Integer.parseInt(input.nextLine());
                if (jerseyNumber <= 0) {
                    jerseyNumber = -1;
                    System.out.println("Invalid jersey number. Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid jersey number. Please enter a valid number.");
            }
        }
        try {
            Player removedPlayer = playersManager.getPlayerByJersey(jerseyNumber);
            if (removedPlayer == null) {
                System.out.println("Player not found.");
            } else {
                System.out.println(
                        "Removing player: " + removedPlayer.getFirstName() + " " + removedPlayer.getLastName());
                playersManager.removePlayer(jerseyNumber);
                System.out.println("Player removed successfully!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
