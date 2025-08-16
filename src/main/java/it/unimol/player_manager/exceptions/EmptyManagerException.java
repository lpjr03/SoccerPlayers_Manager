package it.unimol.player_manager.exceptions;

public class EmptyManagerException extends RuntimeException {
    public EmptyManagerException() {
        super("No players found in the manager, please add players first.");
    }
}
