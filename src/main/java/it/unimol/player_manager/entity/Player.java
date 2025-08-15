package it.unimol.player_manager.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.EnumMap;

import lombok.Data;

@Data
public class Player implements Serializable {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String nationality;
    private int jerseyNumber;
    private EnumMap<Ability, Integer> abilities = new EnumMap<>(Ability.class);

    public String abilitiesToJson() {
        StringBuilder json = new StringBuilder("{");
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
