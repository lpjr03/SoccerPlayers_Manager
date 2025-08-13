package it.unimol.player_manager.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.Scanner;

import it.unimol.player_manager.app.PlayersManager;
import it.unimol.player_manager.entity.Ability;
import it.unimol.player_manager.entity.Player;
import it.unimol.player_manager.exceptions.PlayerExistsException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EnlistPlayer {

    private Scanner input;

    private PlayersManager playersManager;

    public void execute() {
        System.out.println("Enlisting a new player...");
        // Logic to enlist a new player
        System.out.print("Enter first name: ");
        String firstName = input.nextLine();
        System.out.print("Enter last name: ");
        String lastName = input.nextLine();
        System.out.print("Enter birth date (DD/MM/YYYY): ");
        LocalDate birthDate = null;
        while (birthDate == null) {
            try {
                birthDate = LocalDate.parse(input.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception e) {
                System.out.println("Invalid date format.");
                System.out.print("Enter birth date (DD/MM/YYYY): ");
            }
        }
        System.out.print("Enter nationality: ");
        String nationality = input.nextLine();

        int jerseyNumber=-1;
        while(jerseyNumber==-1) {
            System.out.print("Enter jersey number: ");
            try {
                jerseyNumber = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid jersey number. Please enter a valid number.");
            }
        }

        EnumMap<Ability, Integer> skillMap = new EnumMap<>(Ability.class);

        for (Ability ability : Ability.values()) {
            System.out.print("Enter value for " + ability + " (1-10): ");
            int value = Integer.parseInt(input.nextLine());

            if (value < 1 || value > 10) {
                System.out.println("Invalid value for " + ability + ". Player not enlisted.");
                return;
            }

            skillMap.put(ability, value);
        }

        Player player = new Player();
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setBirthDate(birthDate);
        player.setNationality(nationality);
        player.setJerseyNumber(jerseyNumber);
        player.setAbilities(skillMap);

        try {
            playersManager.addPlayer(player);
            System.out.println("Player enlisted successfully!");
        } catch (PlayerExistsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
