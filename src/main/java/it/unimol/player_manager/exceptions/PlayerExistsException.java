package it.unimol.player_manager.exceptions;

public class PlayerExistsException extends RuntimeException {
    public PlayerExistsException(String message) {
        super(message);
    }
}
