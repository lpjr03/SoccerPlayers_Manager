package it.unimol.player_manager.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import it.unimol.player_manager.app.PlayersManager;

public class SerializeManager {
    // Serialization: save manager to file
    public void saveToFile(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filePath))) {
            oos.writeObject(this);
        }
    }

    // Deserialization: load manager from file
    public static PlayersManager loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        PlayersManager instance;
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
