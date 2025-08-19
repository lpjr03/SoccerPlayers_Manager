package it.unimol.player_manager.app;

import java.io.Serializable;

import it.unimol.player_manager.entity.Player;
import it.unimol.player_manager.exceptions.EmptyManagerException;
import it.unimol.player_manager.exceptions.PlayerExistsException;
import it.unimol.player_manager.exceptions.PlayerNotExistsException;
import it.unimol.player_manager.persistence.PostgreConnection;

/**
 * Singleton class that manages a collection of {@link Player} objects.
 * Provides methods to add, remove, and retrieve players by their jersey number.
 * Ensures that each player has a unique jersey number and persists changes
 * to a PostgreSQL database using {@link PostgreConnection}.
 * <p>
 * This class is serializable and maintains an internal mapping of player IDs
 * to {@code Player} instances.
 * </p>
 * 
 * @author leliopalmix
 * @version 1.0
 */
public final class PlayersManager implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Next available player ID.
     */
    private int nextId;

    /**
     * Map of player IDs to Player objects.
     */
    private java.util.Map<Integer, Player> players;

    /**
     * Singleton instance of PlayersManager.
     */
    private static PlayersManager instance;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private PlayersManager() {
        this.nextId = 0;
        this.players = new java.util.HashMap<>();
    }

    /**
     * Returns the singleton instance of the PlayersManager.
     *
     * @return the PlayersManager instance
     */
    public static PlayersManager getInstance() {
        if (instance == null) {
            instance = new PlayersManager();
        }
        return instance;
    }

    /**
     * Adds a new player to the manager.
     *
     * @param player the Player object to add
     * @return true if the player was added successfully, false otherwise
     */
    public boolean addPlayer(final Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (playerExists(player.getJerseyNumber())) {
            throw new PlayerExistsException();
        }
        nextId++;
        final int id = nextId;

        assert players != null : "Players map should not be null";
        players.put(id, player);
        PostgreConnection.getInstance().insertPlayer(player);
        return true;
    }

    /**
     * Checks if a player with the given jersey number exists.
     *
     * @param jerseyNumber the jersey number to check
     * @return true if the player exists, false otherwise
     */
    private boolean playerExists(final int jerseyNumber) {
        return players.values().stream().anyMatch(player -> player.getJerseyNumber() == jerseyNumber);
    }

    /**
     * Removes a player from the manager.
     *
     * @param jerseyNumber the jersey number of the player to remove
     * @return true if the player was removed successfully, false otherwise
     */
    public boolean removePlayer(final int jerseyNumber) {

        assert players != null : "Players map should not be null";
        if (!playerExists(jerseyNumber)) {
            throw new PlayerNotExistsException();
        }
        players.values().removeIf(player -> player.getJerseyNumber() == jerseyNumber);
        PostgreConnection.getInstance().deletePlayer(jerseyNumber);
        return true;
    }

    /**
     * Retrieves a player by their jersey number.
     *
     * @param jerseyNumber the jersey number of the player to retrieve
     * @return the Player object if found, null otherwise
     */
    public Player getPlayerByJersey(final int jerseyNumber) {
        if (this.players.isEmpty()) {
            throw new EmptyManagerException();
        }

        assert players != null : "Players map should not be null";
        return players.values().stream()
                .filter(player -> player.getJerseyNumber() == jerseyNumber)
                .findFirst()
                .orElse(null);
    }
}
