package it.unimol.player_manager.exceptions;

public class PlayerExistsException extends RuntimeException {
    public PlayerExistsException() {
        super("Player already exists");
    }
}
