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

    // Serialization: save manager to file
    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath))) {
            oos.writeObject(this);
        }
    }

    // Deserialization: load manager from file
    public static PlayersManager loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath))) {
            instance = (PlayersManager) ois.readObject();
            return instance;
        } catch (IOException | ClassNotFoundException e) {
            // If file does not exist or cannot be loaded, create a new instance
            instance = new PlayersManager();
            PostgreConnection driverConnection = new PostgreConnection();
            driverConnection.createDatabase("calcio");
            return instance;
        }
    }
}
