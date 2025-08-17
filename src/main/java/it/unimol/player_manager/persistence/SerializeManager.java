package it.unimol.player_manager.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import it.unimol.player_manager.app.PlayersManager;

public class SerializeManager {

    /**
     * Singleton instance of {@link PlayersManager} used for managing player data serialization.
     */
    private static PlayersManager manager;

    // Serialization: save manager to file
    public static void saveToFile(final String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath))) {
            oos.writeObject(manager);
        }
    }

    // Deserialization: load manager from file
    public static PlayersManager loadFromFile(final String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath))) {
            return (PlayersManager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            manager = PlayersManager.getInstance();
            PostgreConnection.getInstance();
            return manager;
        }
    }
}
