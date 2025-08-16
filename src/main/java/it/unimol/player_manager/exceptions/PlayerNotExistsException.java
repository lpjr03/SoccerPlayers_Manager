package it.unimol.player_manager.exceptions;

public class PlayerNotExistsException extends RuntimeException {
    public PlayerNotExistsException() {
        super("Player does not exist");
    }
}
