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
}