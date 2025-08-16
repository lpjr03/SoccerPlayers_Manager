package it.unimol.player_manager.app;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import it.unimol.player_manager.entity.Player;
import it.unimol.player_manager.exceptions.EmptyManagerException;
import it.unimol.player_manager.exceptions.PlayerExistsException;
import it.unimol.player_manager.exceptions.PlayerNotExistsException;
import it.unimol.player_manager.persistence.PostgreConnection;

public class PlayersManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private int nextId;
    private HashMap<Integer, Player> players;
    private static PlayersManager instance;

    private PlayersManager() {
        this.nextId = 0;
        this.players = new HashMap<>();
    }

    public static PlayersManager getInstance() {
        if (instance == null) {
            instance = new PlayersManager();
        }
        return instance;
    }

    public boolean addPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (playerExists(player.getJerseyNumber())) {
            throw new PlayerExistsException("Player already exists");
        }
        nextId++;
        int id = nextId;
        players.put(id, player);
        PostgreConnection.getInstance().insertPlayer(player);
        return true;
    }

    public Map<Integer, Player> getPlayers() {
        return Collections.unmodifiableMap(players);
    }

    private boolean playerExists(int jerseyNumber) {
        return players.values().stream().anyMatch(player -> player.getJerseyNumber() == jerseyNumber);
    }

    public boolean removePlayer(int jerseyNumber) {
        if (!playerExists(jerseyNumber)) {
            throw new PlayerNotExistsException("Player does not exist");
        }
        players.values().removeIf(player -> player.getJerseyNumber() == jerseyNumber);
        PostgreConnection.getInstance().deletePlayer(jerseyNumber);
        return true;
    }

    public Player getPlayerByJersey(int jerseyNumber) {
        if (this.players.isEmpty())
            throw new EmptyManagerException();
        return players.values().stream()
                .filter(player -> player.getJerseyNumber() == jerseyNumber)
                .findFirst()
                .orElse(null);
    }
}
