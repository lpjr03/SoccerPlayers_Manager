package it.unimol.player_manager.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.EnumMap;

import lombok.Data;

/**
 * Class representing a player in the system.
 */
@Data
public class Player implements Serializable {
    /**
     * First name of the player.
     */
    private String firstName;

    /**
     * Last name of the player.
     */
    private String lastName;

    /**
     * Birth date of the player.
     */
    private LocalDate birthDate;

    /**
     * Nationality of the player.
     */
    private String nationality;

    /**
     * Jersey number of the player.
     */
    private int jerseyNumber;

    /**
     * Abilities of the player.
     */
    private EnumMap<Ability, Integer> abilities = new EnumMap<>(Ability.class);

    /**
     * Converts the player's abilities to a JSON string.
     *
     * @return a JSON representation of the player's abilities
     */
    public String abilitiesToJson() {
        final StringBuilder json = new StringBuilder("{");
        int count = 0;
        for (var entry : abilities.entrySet()) {
            json.append("\"")
                    .append(entry.getKey().name())
                    .append("\": ")
                    .append(entry.getValue());
            if (++count < abilities.size()) {
                json.append(", ");
            }
        }
        json.append("}");
        return json.toString();
    }
}
