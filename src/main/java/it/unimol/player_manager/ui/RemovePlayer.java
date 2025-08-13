package it.unimol.player_manager.ui;

import java.util.Scanner;

import it.unimol.player_manager.app.PlayersManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemovePlayer {

    private Scanner input;

    private PlayersManager playersManager;

    public void execute() {

        int jerseyNumber=-1;
        
        System.out.print("Enter the jersey number of the player to remove: ");
        while(jerseyNumber==-1) {
            try {
                jerseyNumber = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid jersey number. Please enter a valid number.");
            }
        }
        try {
            playersManager.removePlayer(jerseyNumber);
            System.out.println("Player removed successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
