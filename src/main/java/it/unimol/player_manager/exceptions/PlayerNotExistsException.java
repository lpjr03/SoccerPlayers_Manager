package it.unimol.player_manager.exceptions;

public class PlayerNotExistsException extends RuntimeException {
    public PlayerNotExistsException(String message) {
        super(message);
    }
}
