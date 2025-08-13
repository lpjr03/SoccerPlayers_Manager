package it.unimol.player_manager.app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import it.unimol.player_manager.entity.Player;
import it.unimol.player_manager.exceptions.PlayerExistsException;
import it.unimol.player_manager.persistence.PostgreConnection;

public class PlayersManager implements Serializable {
    private static PlayersManager instance;
    private static final long serialVersionUID = 1L;
    private int nextId;
    private HashMap<Integer, Player> players;

    public PlayersManager() {
        this.nextId = 0;
        this.players = new HashMap<>();
    }

    public int addPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (playerExists(player.getJerseyNumber())) {
            throw new PlayerExistsException("Player already exists");
        }
        int id = nextId++;
        players.put(id, player);
        return id;
    }

    public Map<Integer, Player> getPlayers() {
        return Collections.unmodifiableMap(players);
    }

    private boolean playerExists(int jerseyNumber) {
        return players.values().stream().anyMatch(player -> player.getJerseyNumber() == jerseyNumber);
    }

    public boolean removePlayer(int jerseyNumber) {
        if (!playerExists(jerseyNumber)) {
            throw new IllegalArgumentException("Player does not exist");
        }
        players.values().removeIf(player -> player.getJerseyNumber() == jerseyNumber);
        return true;
    }

    public Player getPlayerByJersey(int jerseyNumber) {
        return players.values().stream()
                .filter(player -> player.getJerseyNumber() == jerseyNumber)
                .findFirst()
                .orElse(null);
    }
}
