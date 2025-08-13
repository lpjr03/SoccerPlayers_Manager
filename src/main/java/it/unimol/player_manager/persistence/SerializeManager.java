package it.unimol.player_manager.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import it.unimol.player_manager.app.PlayersManager;

public class SerializeManager {

    private static PlayersManager manager;

    // Serialization: save manager to file
    public static void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath))) {
            oos.writeObject(manager);
        }
    }

    // Deserialization: load manager from file
    public static PlayersManager loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filePath))) {
            return (PlayersManager) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // If file does not exist or cannot be loaded, create a new instance
            manager = new PlayersManager();
            PostgreConnection driverConnection = PostgreConnection.getInstance();
            return manager;
        }
    }
}
